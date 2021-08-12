package jhd.Projection;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import jhd.MathTools.Interpolation;
import jhd.MuMassCalculator.MuMassCalculator;

/**
 * Create sinograms from square floating point images
 * @author John H. Dunsmuir
 *
 */
public class Projector
{

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

	//*******************************************************************************

	// There are too many parameters to pass on an argument list
	/**Arguments to Bremsstrahlung projectors are passed as a parameter block
	 * @author John
	 */

	public class CtSettings
	{
		//X-ray Source
		/**The X-ray source target material*/
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

		/**The number of discrete compounds in the image*/
		public int	ctMaterialCount;
		/**The compound formula. Must be in Atom1:Count1:Atom2:Count2... format, e.g. Ca:1:C:1:O:3 for CaCO3 calcite*/
		public String[] formula;
		/**The compound density in gm/cc*/
		public double[] gmPerCC;

		//CT params
		/**The number of scan angles between 0 and 180 degrees*/
		public int numAng;
		/**Set to true to pad a image that has data in the corners with zeros so that the corners will be properly projected at all angles.*/
		public boolean padImage;
		/**The magnification for Fan Beam CT, ignored by parallel beam functions*/
		public float magnification;
		/**The source to detector distance in pixels for Fan Beam CT, ignored by parallel beam functions*/
		public float srcToDet;

		//Detector
		/**The detector scintillator  formula, Must be in Atom1:Count1:Atom2:Count2... format, e.g. Cs:1:I:1 for CsI Cesium Iodide*/
		public String detFormula;
		/**The detector scintillator screen or element thickness in CM*/
		public double detCM;
		/**The detector scintillator screen/element density in gm/cc*/
		public double detGmPerCC;		
		/**Multiply floating point opacities by 6000 and convert result to 16-bit*/
		public boolean scale16;
	}

	//*******************************************************************************

	/**
	 * @param ctSet A CtSettings Parameter block
	 * @param image A 1D reference to a float image
	 * @param width The image width
	 * @param height The image height
	 * @return A 1D reference to a sinogram width*numViews
	 */
	public float[] imageToBremsstrahlungFanBeamSinogram(CtSettings ctSet, float[] image, int width, int height)
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
		
		MuMassCalculator mmc = new MuMassCalculator();
		Projector prj = new Projector();

		//Buffer to hold a sinogram of the detected counts at one energy
		float[] sino = null;
		//buffer to sum the individual energy sinograms
        float[] bremSino = new float[(int)(width*ctSet.numAng*ctSet.magnification)];

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
				
		//The source
		double src;  //The source intensity (counts)
		double srcFilt; //The source counts after filtration
		double srcFiltDet; //The filtered source  detected counts

		//srcFiltDetIntg holds all of the filtered source detected counts 
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
 
    		//Get the source Counts			//Counts
			src = mmc.spectrumKramers(ctSet.kv, ctSet.ma, ctSet.target, meV);//get the source continuum intensity spectrum			
			//Filtered Counts
            srcFilt = src * Math.exp(-filterTau);           
            //Detected Counts without sample
            srcFiltDet = srcFilt * (1 - Math.exp(-detTau));
            //Sum the Counts, no, we don't multiply by dE=kvInc because it will cancel out later
            srcFiltDetIntg+=srcFiltDet;
                        
            //Make a copy of the tagged-segmented image
            muLinImage = image.clone();
            
            // get the attenuation of the materials
            double[] muLin = new double[ctSet.ctMaterialCount];
            for(int k =0;k<ctSet.ctMaterialCount;k++)
            {
                muLin[k] = mmc.getMuMass(ctSet.formula[k], meV, "TotAttn") * ctSet.gmPerCC[k];        	
            }
            
            // convert tags to attenuation
            for(int j=0;j<muLinImage.length;j++)
            {
            	for(int tag=1;tag<=ctSet.ctMaterialCount;tag++)
            	{
            		if((int)muLinImage[j] == tag)
            		{
            			muLinImage[j] = (float)muLin[tag-1];
            		}
            	}
            }
            
            //Create the sinogram projection of the material linear attenuations 
             sino = prj.imageToFanBeamSinogram(muLinImage, width, height, ctSet.srcToDet, ctSet.magnification, ctSet.numAng);
            
            //Sum The sinogram as counts
            double detCaptured = (1 - Math.exp(-detTau));
            for(int j=0;j<sino.length;j++)
            {
            	bremSino[j] += ((srcFilt * Math.exp(-sino[j] *ctSet.pixSizeCM)) * detCaptured);   
            }
 		}
		
        //Convert the accumulated detected counts to tau
        for(int j=0;j<bremSino.length;j++) bremSino[j] = (float)(Math.log(srcFiltDetIntg/bremSino[j]));
		frame.dispose();
		return bremSino;
	}
	
	//*******************************************************************************

	/**Creates a sinogram of a tagged image using a filtered bremsstrahlung X-ray source, parallel projection, and scintillation detector.
	 * @param ctSet A CtSettings Parameter block, see nested classes.
	 * @param image A 1D reference to a float image
	 * @param width The image width
	 * @param height The image height
	 * @return A 1D reference to a sinogram width*numViews
	 */
	public float[] imageToBremsstrahlungParallelSinogram(CtSettings ctSet, float[] image, int width, int height)
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

		MuMassCalculator mmc = new MuMassCalculator();
		Projector prj = new Projector();

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

			// get the attenuation of the materials
			double[] muLin = new double[ctSet.ctMaterialCount];
			for(int k =0;k<ctSet.ctMaterialCount;k++)
			{
				// muLin[k] = mmc.getMuMass(ctSet.ctMaterials[k].formula, meV, "TotAttn") * ctSet.ctMaterials[k].gmPerCC;        	
				muLin[k] = mmc.getMuMass(ctSet.formula[k], meV, "TotAttn") * ctSet.gmPerCC[k];        	
			}

			// convert tags to attenuation
			for(int j=0;j<muLinImage.length;j++)
			{
				for(int tag=1;tag<=ctSet.ctMaterialCount;tag++)
				{
					if((int)muLinImage[j] == tag)
					{
						muLinImage[j] = (float)muLin[tag-1];
					}
				}
			}

			//Create the sinogram projection of the material linear attenuations 
			sino = prj.imageToParallelSinogram(muLinImage, width, height, ctSet.numAng);

			//pull the Math.exp call out of the loop
			double detCapture = (1 - Math.exp(-detTau));
			for(int j=0;j<sino.length;j++)
			{
				//float temp = sino[j];
				//convert from pixel-1 to cm-1
				//temp *= ctSet.pixSizeCM;
				//The source attenuated by the filter and the sample
				//temp = (float)(srcFilt * Math.exp(-temp));
				//The source attenuated by the filter and the sample detected counts
				//temp = (float)(temp * (1 - Math.exp(-detTau)));

				//Add up the detected counts, same as commented temp above but harder to read
				//bremSino[j] += ((srcFilt * Math.exp(-sino[j] *ctSet.pixSizeCM)) * (1 - Math.exp(-detTau)));   
				bremSino[j] += ((srcFilt * Math.exp(-sino[j] *ctSet.pixSizeCM)) * detCapture);   
			}
		}

		//Convert the accumulated detected counts to tau
		for(int j=0;j<bremSino.length;j++) bremSino[j] = (float)(Math.log(srcFiltDetIntg/bremSino[j]));
		frame.dispose();
		return bremSino;
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

	/**Creates a sinogram from an image using Fan Beam projection
	 * @param image Reference to an NxN (square) floating point image
	 * @param width The image width in pixels
	 * @param height The image height in pixels
	 * @param srcToDet The source to detector distance in pixels
	 * @param magnification A positive number for the fan projection magnification. In the real world always greater than 1
	 * @param numAngles The number of projections between 0 and 360 degrees rotation
	 * @return Reference to a floating point sinogram dimensions width x numAngles
	 */
	public float[] imageToFanBeamSinogram(float[] image,int width,int height, 
			float srcToDet, float magnification, int numAngles)	
	{
		//Function always scans 180 degrees
		//Rotating the image works poorly, bad edge interpolation.
		//This code moves the source and detector locations
		//with sub pixel accuracy and interpolates along ray paths

		int rotXo = width/2;
		int rotYo = height/2;

		float srcToSamp = srcToDet/magnification;
		float sampToDet =srcToDet - srcToSamp;

		int detWidthPix = (int) (width*magnification);
		float start = -detWidthPix/2;

		int sinoRows = numAngles;
		double angInc = 360.0/(double)numAngles;
		int sinoCol,sinoRow;

		//Do the projection
		DblPoint srcPt = new DblPoint();
		DblPoint detPt = new DblPoint();
		float[] lineIntegrals = new float[detWidthPix*sinoRows];
		float detPix;

		//over-sampling caused negligible improvement to resulting reconstructions
		double overSample = 1;

		double ang;
		for(ang=0.0,sinoRow=0;sinoRow<numAngles;ang+=angInc,sinoRow++)
		{
			double theta	= Math.toRadians(ang);
			double sinTheta	= Math.sin(theta);
			double cosTheta	= Math.cos(theta);

			//get the source pixel coordinates
			srcPt.x = (rotXo + srcToSamp*sinTheta);
			srcPt.y = (rotYo -srcToSamp*cosTheta);

			for(detPix=start,sinoCol=0;sinoCol<detWidthPix;detPix+=1,sinoCol++)
			{			
				//get the detector pixel coordinates
				detPt.x = (float)(detPix*cosTheta-sampToDet*sinTheta+rotXo);
				detPt.y = (float)(sampToDet*cosTheta+detPix*sinTheta+rotYo);

				// I tried to shorten the source to detector lines but the math was slower
				// than summing all of the zero pixels outside of the sample.
				// I need a clever trick.
				// For very long source to detector distances with small samples
				// consider using imageToParallelelSinogram.

				//Get the line sum				
				float raySum = getRaySum(image,width,height, srcPt, detPt, overSample);;
				lineIntegrals[sinoCol + sinoRow*detWidthPix] = raySum;
			}
		}	
		return lineIntegrals;
	}

	//*******************************************************************************

	/**
	 * @param image Reference to a floating point image
	 * @param width The image width
	 * @param height The image height
	 * @param p1 The location of the parallel ray source
	 * @param p2 The location of the detector element	
	 * @param overSample Recommended 1: Values greater than 1 give slightly more accurate ray sums
	 * @return The value of the line integral along the ray 
	 */
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
