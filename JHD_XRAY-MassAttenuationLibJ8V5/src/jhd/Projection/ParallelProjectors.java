package jhd.Projection;

import java.awt.Dimension;
import java.util.HashSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import jhd.MathTools.Interpolation;
import jhd.MuMassCalculator.*;

/**
 * Create sinograms from square floating point images
 * @author John H. Dunsmuir
 *
 */
public class ParallelProjectors
{
	
	/**Parameter block for parallel projecting a 2D image to a sinogram
	 * The image may contain any values, most commonly x-ray cross-sections.
	 * @author John Dunsmuir	 *
	 */
	public static class ParallelParams implements java.io.Serializable
	{
		/**Constructor for this serializable parameter block*/ 
		public ParallelParams(){};
		/** The normal maximum angular range for a CT scan of this type*/
		public static final int maxAng = 180;
		/**The number of scan angles between 0 and 180 degrees*/
		public int numAng;
	}
	
	/**Parameter block for parallel projecting a tagged 2D image to a sinogram using a simplified conventional CT scanner simulation.
	 * @author John Dunsmuir
	 */
	public static class BremParallelParams extends ParallelParams
	{

		/**Constructor for this serializable parameter block*/ 
		public BremParallelParams(){};
		/**The conventional X-ray source anode (target) material*/
		public String target;
		/**The X-ray source accelerating potential, the upper limit on the X-ray energy*/
		public double kv;
		/**The X-ray source beam current*/
		public double ma;
		/**Divide the source spectrum into nBins discrete energies*/
		public int	nBins;
		/**The lower limit on the X-ray energy*/
		public double minKV;

		//Filter
		/**The filter material, typically Al, Cu, Sn, Mo*/
		public String filter;
		/**The filter thickness*/
		public double filterCM;
		/**The filter density*/
		public double filterGmPerCC;

		/**The image pixel size in CM. the code works in CGS units*/
		public double pixSizeCM;

		/**Array of tags corresponding to a material*/
		public int[] matlTag;
		/**Array of material names, mostly for user convenience*/
		public String[] matlName;
		/**Array of compound formulas. Must be in Atom1:Count1:Atom2:Count2 format, for example Ca:1:C:1:O:3 for CaCO3 Calcite*/
		public String[] matlFormula;
		/**Array of compound densities in gm/cc*/
		public double[] matlGmPerCC;

		//Detector
		/**The detector scintillator  formula, Must be in Atom1:Count1:Atom2:Count2 format, For example Cs:1:I:1 for CsI Cesium Iodide*/
		public String detFormula;
		/**The detector scintillator screen or detector element thickness in CM*/
		public double detCM;
		/**The detector scintillator screen or detector element density in gm/cc*/
		public double detGmPerCC;		
		/**Set to true to multiply sinogram floating point opacities by 6000 and convert result to 16-bit*/
		
	}

	//*******************************************************************************

	static class DblPoint
	{
		public DblPoint() {}
		public DblPoint(double x, double y)
		{
			super();
			this.x = x;
			this.y = y;
		}
		double x;
		double y;		
	}

	MuMassCalculator mmc = new MuMassCalculator();

	//*******************************************************************************

	/**Creates a parallel beam sinogram of a tagged image using a filtered bremsstrahlung X-ray source, parallel projection, and scintillation detector.
	 * Requires a segmented 2D float image where each material has been assigned an integer tag
	 * Requires image pixel size units in CM
	 * Requires the chemical formula and density of each tagged component.
	 * The tags are converted to x-ray cross-sections over a range of x-ray energies
	 * Projections are calculated using a simplified Source,Filter,Sample,Detector model
	 * The model records photon counts and converts those to measured sample attenuation
	 * Used to validate beam hardening correction methods.
	 * @param ctSet A CtSettings Parameter block, see nested classes.
	 * @param image A 1D reference to a tagged float image
	 * @param width The image width
	 * @param height The image height
	 * @return A 1D reference to a sinogram width*numViews
	 */
	public float[] imageToBremsstrahlungParallelSinogram(BremParallelParams ctSet, float[] image, int width, int height)
	{

		//Set up the progress bar
		JPanel fldPanel = new JPanel();
		JFrame frame = new JFrame("CT Scan Progress");		
		JProgressBar prgBar = new JProgressBar(0,ctSet.nBins+1);

		frame.setSize(400, 100);
		frame.setLocationRelativeTo(null);

		prgBar.setPreferredSize(new Dimension(350, 50));
		prgBar.setValue(0);
		prgBar.setStringPainted(true);			
		fldPanel.add(prgBar);

		frame.add(fldPanel);
		frame.setVisible(true);

		//MuMassCalculator mmc = new MuMassCalculator();

		//Buffer to hold a sinogram of the detected counts at one energy
		float[] sino = null;
		//buffer to sum the individual energy sinograms
		float[] bremSino = new float[width*ctSet.numAng];

		//Swap min and max kv if necessary
		if(ctSet.minKV > ctSet.kv)
		{
			double temp = ctSet.minKV;
			ctSet.minKV = ctSet.kv;
			ctSet.kv = temp;
		}

		double kvInc = (ctSet.kv-ctSet.minKV)/ctSet.nBins;		
		int numKev = ctSet.nBins +1;

		//The Scanner absorbances
		double filterTau;
		double detTau;

		//The Spectra, Some currently unnecessary spectra commented out
		//The source
		double src;  //The source intensity (counts)
		double srcFilt; //The source counts after filtration
		double srcFiltDet; //The filtered source  detected counts

		//Integration holds all of the filtered source detected counts 
		double srcFiltDetIntg=0;

		int i=0;
		double keV;
		float[] muLinImage=null;
		for(i=0, keV = ctSet.kv; i<numKev; i++, keV-= kvInc)
		{			
			prgBar.setValue(i);

			//The Source energy
			double meV = keV / 1000;

			//The filter attenuation
			filterTau = mmc.getMuMass(ctSet.filter, meV, "TotAttn") * ctSet.filterCM * ctSet.filterGmPerCC;           
			//The detector attenuation
			detTau = mmc.getMuMass(ctSet.detFormula, meV, "TotAttn") * ctSet.detCM * ctSet.detGmPerCC;

			//Get the source properties
			//Counts
			src = mmc.spectrumKramers(ctSet.kv, ctSet.ma, ctSet.target, meV);//get the source continuum intensity spectrum			
			//Filtered Counts
			srcFilt = src * Math.exp(-filterTau);           
			//Detected Counts without sample
			srcFiltDet = srcFilt * (1 - Math.exp(-detTau));
			//Sum the Counts
			srcFiltDetIntg+=srcFiltDet;

			//Make a copy of the tagged-segmented image
			muLinImage = image.clone();

			// get the attenuation of the materials in the list
			float[] muLin = new float[ctSet.matlFormula.length];
			for(int tag =1;tag<ctSet.matlFormula.length;tag++)
			{
				muLin[tag] = (float) ( mmc.getMuMass(ctSet.matlFormula[tag], meV, "TotAttn") * ctSet.matlGmPerCC[tag]);        	
			}

			// convert tags to attenuation using indirect addressing
			//The Materials file can be as long as needed without penalty.
			for(int tag=1;tag<muLinImage.length;tag++)
			{
				muLinImage[tag] = muLin[(int)muLinImage[tag]];
			}

			//Create the sinogram projection of the material linear attenuations 
			sino = imageToParallelSinogram(muLinImage, width, height, ctSet.numAng);

			//pull the Math.exp call out of the loop
			double detCapture = (1 - Math.exp(-detTau));
			for(int j=0;j<sino.length;j++)
			{
				bremSino[j] += ((srcFilt * Math.exp(-sino[j] *ctSet.pixSizeCM)) * detCapture);   
			}
		}

		//Convert the accumulated detected counts to tau
		for(int j=0;j<bremSino.length;j++) bremSino[j] = (float)(Math.log(srcFiltDetIntg/bremSino[j]));
		frame.dispose();
		return bremSino;
	}
	
	//*******************************************************************************

	/**Creates a parallel beam sinogram of a tagged image using a filtered bremsstrahlung X-ray source, parallel projection, and scintillation detector.
	 * Requires a segmented 2D float image where each material has been assigned an integer tag
	 * Requires image pixel size units in CM
	 * Requires the chemical formula and density of each tagged component.
	 * The tags are converted to x-ray cross-sections over a range of x-ray energies
	 * Projections are calculated using a simplified Source,Filter,Sample,Detector model
	 * The model records photon counts and converts those to measured sample attenuation
	 * Used to validate beam hardening correction methods.
	 * @param ctSet A CtSettings Parameter block, see nested classes.
	 * @param image A 1D reference to a tagged float image
	 * @param width The image width
	 * @param height The image height
	 * @return A 1D reference to a sinogram width*numViews
	 */
	public float[] imageToBremsstrahlungParallelSinogram2(BremParallelParams ctSet, float[] image, int width, int height)
	{
		//Differs from imageToBremsstrahlungParallelSinogram by creating a clone 
		//of the tag image and modifying its primitive data field to become linear attenuation.
		
		//Set up the progress bar
		JPanel fldPanel = new JPanel();
		JFrame frame = new JFrame("CT Scan Progress");		
		JProgressBar prgBar = new JProgressBar(0,ctSet.nBins+1);

		frame.setSize(400, 100);
		frame.setLocationRelativeTo(null);

		prgBar.setPreferredSize(new Dimension(350, 50));
		prgBar.setValue(0);
		prgBar.setStringPainted(true);			
		fldPanel.add(prgBar);

		frame.add(fldPanel);
		frame.setVisible(true);

		//MuMassCalculator mmc = new MuMassCalculator();

		//Buffer to hold a sinogram of the detected counts at one energy
		float[] sino = null;
		//buffer to sum the individual energy sinograms
		float[] bremSino = new float[width*ctSet.numAng];

		//Swap min and max kv if necessary
		if(ctSet.minKV > ctSet.kv)
		{
			double temp = ctSet.minKV;
			ctSet.minKV = ctSet.kv;
			ctSet.kv = temp;
		}

		double kvInc = (ctSet.kv-ctSet.minKV)/ctSet.nBins;		
		int numKev = ctSet.nBins +1;

		//The Scanner absorbances
		double filterTau;
		double detTau;

		//The Spectra, Some currently unnecessary spectra commented out
		//The source
		double src;  //The source intensity (counts)
		double srcFilt; //The source counts after filtration
		double srcFiltDet; //The filtered source  detected counts

		//Integration holds all of the filtered source detected counts 
		double srcFiltDetIntg=0;

		int i=0;
		double keV;
		float[] muLinImage=null;
		
		//Get the tags from the image
		int[] tagArr = getUniqueTags(image);
		
		for(i=0, keV = ctSet.kv; i<numKev; i++, keV-= kvInc)
		{			
			prgBar.setValue(i);

			//The Source energy
			double meV = keV / 1000;

			//The filter attenuation
			filterTau = mmc.getMuMass(ctSet.filter, meV, "TotAttn") * ctSet.filterCM * ctSet.filterGmPerCC;           
			//The detector attenuation
			detTau = mmc.getMuMass(ctSet.detFormula, meV, "TotAttn") * ctSet.detCM * ctSet.detGmPerCC;

			//Get the source properties
			//Counts
			src = mmc.spectrumKramers(ctSet.kv, ctSet.ma, ctSet.target, meV);//get the source continuum intensity spectrum			
			//Filtered Counts
			srcFilt = src * Math.exp(-filterTau);           
			//Detected Counts without sample
			srcFiltDet = srcFilt * (1 - Math.exp(-detTau));
			//Sum the Counts
			srcFiltDetIntg+=srcFiltDet;

			//Make a copy of the tagged-segmented image
			muLinImage = image.clone();
			// convert tags to attenuation using indirect addressing
			tagsToLinearAttn(muLinImage,tagArr,ctSet,meV);
			//Create the sinogram projection of the material linear attenuations 
			sino = imageToParallelSinogram(muLinImage, width, height, ctSet.numAng);
			//pull the Math.exp call out of the loop
			double detCapture = (1 - Math.exp(-detTau));
			for(int j=0;j<sino.length;j++)
			{
				bremSino[j] += ((srcFilt * Math.exp(-sino[j] *ctSet.pixSizeCM)) * detCapture);   
			}
		}

		//Convert the accumulated detected counts to tau
		for(int j=0;j<bremSino.length;j++) bremSino[j] = (float)(Math.log(srcFiltDetIntg/bremSino[j]));
		frame.dispose();
		return bremSino;
	}

	//********************************************************************************************
	
	private boolean tagsToLinearAttn(float[] imageData, int[] tagArr, BremParallelParams ctSet, double meV)
	{
		//This routine uses double indirect addressing for fast conversion of 
		//tag values to the linear attenuation of each tag's formula and density
		//It does not require that the tags in the materials data in ctSet
		//correspond to an array index.
		//MuMassCalculator mmc = new MuMassCalculator();
		
		
		//check if tagArr is bigger than the tagList
		//if it is, it is probably not a tag image
		if(tagArr.length > ctSet.matlTag.length)
		{
			System.out.println("Error: There are more tags in the input image than\n"
								+ "there are tags in the materials list.");

//			IJ.showMessage("Error", "There are more tags in the input image than\n"
//					+ "there are tags in the materials list.");
			return false;
		}
		
		// Find the position of each tag in the TagData list
		int[] tagIndex = new int[tagArr.length];
		for(int j=0;j<tagArr.length;j++)
		{
			tagIndex[j]=-1;//-1 indicates a match was not found, zero is a valid tag index
			for(int i=0;i< ctSet.matlTag.length;i++)
			{
				if(tagArr[j]==ctSet.matlTag[i])
				{
					tagIndex[j] = i;
				}
			}
		}
				
		//Get biggest matlTag
		int  maxTag = Integer.MIN_VALUE;
		for(int i = 0 ; i<tagArr.length;i++)
		{
			if(tagArr[i] > maxTag) maxTag = tagArr[i];
		}
		
		//Create an array to hold the linear attenuation values
		float[] muLinArr = new float[maxTag+1];
		
		//Set the muLin look-up values for each tag
		for(int i=0;i<tagIndex.length;i++)
		{
			if(tagIndex[i]>0)
			{
				String 	formula= ctSet.matlFormula[tagIndex[i]];	
				double gmPerCC = ctSet.matlGmPerCC[tagIndex[i]];
				double muLin = mmc.getMuMass(formula, meV, "TotAttn")*gmPerCC;			
				muLinArr[tagArr[i]]= (float) muLin;
			}
			else
			{
				System.out.println("Tag " +tagArr[i]+ " was not found in the materials list");
				//IJ.log("Tag " +tagArr[i]+ " was not found in the materials list");
			}
		}
		
		//Convert the image to linear Attenuation
		for(int i=0;i<imageData.length;i++)
		{
			try
			{
				imageData[i] = muLinArr[(int)imageData[i]];
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return true;
	}
	
	//********************************************************************************************

	/**Scans an float image for unique pixel tag values
	 * @param imageData
	 * @return a list of integer tag values used in the image
	 */
	private int[] getUniqueTags( float[] imageData)
	{
		// from https://www.javatpoint.com/find-unique-elements-in-array-java

		HashSet<Float> hashset = new HashSet<>();   
		for (int i = 0; i < imageData.length; i++)   
		{   
			if (!hashset.contains(imageData[i]))   
			{   
				hashset.add(imageData[i]);   
			}   
		}

		//Convert hash to float array
		Float[] tagList = hashset.toArray(new Float[hashset.size()]);
		int[] tagArr = new int[hashset.size()];
		int i=0;
		//Convert to primitive array
		for(Float fObj : tagList)
		{
			tagArr[i]=fObj.intValue();
			i++;
		}

		//print hash set that contains distinct element  
		return tagArr;
	}
		

	//*******************************************************************************

	/**Creates a sinogram from an image using parallel projection
	 * @param image Reference to an NxN (square) floating point image
	 * @param width The image width
	 * @param height The image height
	 * @param numAngles The number of projections between 0 and 180 degrees rotation
	 * @return Reference to a floating point sinogram dimensions width x numAngles
	 */
	public float[] imageToParallelSinogram(float[] image,int width,int height,int numAngles)	
	{
		//Function always scans 180 degrees
		//Rotating the image works poorly, bad edge interpolation.
		//This code moves the source and detector locations
		//with sub pixel accuracy and interpolates along ray paths
		int width2 = width/2;
		int height2 = height/2;
		int sinoRows = numAngles;
		double angInc = 180.0/(double)numAngles;
		int i,sinoRow;


		// initialize the source and detector arrays.
		DblPoint[] srcPix = new DblPoint[width];
		DblPoint[] detPix = new DblPoint[width];
		//float[][] detPixIntg = new float[width][sinoRows];
		float[] lineIntegrals = new float[width*sinoRows];

		//offset coordinate system to center of image
		for(i=0;i<width;i++)
		{
			srcPix[i] = new DblPoint(i-width2,0-height2);
			detPix[i] = new DblPoint(i-width2,height-height2);
		}		

		//Do the projection
		DblPoint srcPt = new DblPoint();
		DblPoint detPt = new DblPoint();

		//over-sampling caused negligible improvement to resulting reconstructions
		double overSample = 1;

		double ang;
		//Pre-computing  sine and cosine tables before calling
		//imageToParallelSinogram is about 10% slower than re-computing
		//them for each energy
		for(sinoRow=0,ang=0;sinoRow<sinoRows; sinoRow++,ang+=angInc)
		{
			double theta	= Math.toRadians(ang);
			double sinTheta	= Math.sin(theta);
			double cosTheta	= Math.cos(theta);

			for(i=0;i<width;i++)
			{			
				//get the source and detector locations pixel coordinates
				srcPt.x = (srcPix[i].x*cosTheta-srcPix[i].y*sinTheta+width2);
				srcPt.y = (srcPix[i].x*sinTheta+srcPix[i].y*cosTheta+height2);
				detPt.x = (detPix[i].x*cosTheta-detPix[i].y*sinTheta+width2);
				detPt.y = (detPix[i].x*sinTheta+detPix[i].y*cosTheta+height2);

				//Get the line sum				
				float raySum = getRaySum(image,width,height, srcPt, detPt, overSample);
				lineIntegrals[i + sinoRow*width] = raySum;
			}
		}		
		return lineIntegrals;
	}

	//*******************************************************************************

	//Unchanged
	private static float getRaySum(float[] image, int width, int height,
			DblPoint p1, DblPoint p2, double overSample)
	{
		float raySum = 0;

		int numPts;
		double xLen = p2.x - p1.x;
		double yLen = p2.y - p1.y;

		//The number of samples;
		numPts = (int)Math.round(Math.sqrt(xLen*xLen +  yLen*yLen));
		numPts*=overSample;
		double xInc = numPts>0?xLen/numPts:0;
		double yInc = numPts>0?yLen/numPts:0;

		int xLeft,yTop;
		float topLeft=0,topRight=0,btmLeft=0,btmRight=0;
		double top,btm;
		double y=p1.y,x=p1.x;
		for(int i = 0; i< numPts ; i++)
		{
			xLeft = (int) x;
			yTop = (int) y;

			if(x>=0 && x+1 < width && y>=0 && y+1 <height)
			{
				topLeft = image[xLeft + yTop*width];
				topRight = image[xLeft+1 + yTop*width];
				btmLeft = image[xLeft + (yTop+1)*width];
				btmRight = image[xLeft +1 + (yTop+1)*width];

				top = Interpolation.linTerp(xLeft,xLeft+1,x, topLeft,topRight);
				btm = Interpolation.linTerp(xLeft,xLeft+1,x, btmLeft,btmRight);
				raySum += Interpolation.linTerp(yTop,yTop+1,y, top,btm);							
			}
			x+= xInc;
			y+= yInc;
		}
		return raySum; 
	}
}

