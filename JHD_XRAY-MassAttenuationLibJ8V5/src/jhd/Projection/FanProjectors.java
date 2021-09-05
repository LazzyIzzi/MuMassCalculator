package jhd.Projection;

import java.awt.Dimension;

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
public class FanProjectors
{

	/**Parameter block for fan beam projecting a 2D image to a sinogram
	 * The image may contain any values, most commonly x-ray cross-sections.
	 * @author John Dunsmuir
	 */
	public static class FanParams implements java.io.Serializable
	{
		/**Constructor for this serializable parameter block*/ 
		public FanParams(){};
		/** The normal maximum angular range for a CT scan of this type*/
		public static final int maxAng = 360;
		//CT params
		/**The number of scan angles between 0 and 360 degrees*/
		public int numAng;
		/**The magnification for Fan Beam CT, ignored by parallel beam functions*/
		public float magnification;
		/**The source to detector distance in pixels for Fan Beam CT, ignored by parallel beam functions*/
		public float srcToDetCM;
		/**The image pixel size in CM. the code works in CGS units*/
		public double pixSizeCM;
	}
	
	//*******************************************************************************

	/**Parameter block for fan projecting a tagged 2D image to a sinogram using a simplified conventional CT scanner simulation.
	 * @author John Dunsmuir
	 */
	public static class BremFanParams extends FanParams
	{
		/**Constructor for this serializable parameter block*/ 
		public BremFanParams(){};
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

	//*******************************************************************************
	 /**
	  * Requires a segmented 2D float image where each material has been assigned an integer tag
	 * Requires image pixel size units in CM
	 * Requires the chemical formula and density of each tagged component
	 * The tags are converted to x-ray cross-sections over a range of x-ray energies
	 * Projections are calculated using a simplified Source,Filter,Sample,Detector model
	 * The model records photon counts and converts those to measured sample attenuation
	 * Used to validate beam hardening correction methods.
	
	 * @param ctSet A BremFanParams Parameter block
	 * @param image A 1D reference to a float image
	 * @param width The image width
	 * @param height The image height
	 * @return A 1D reference to a sinogram width*numViews
	 */
	public float[] imageToBremsstrahlungFanBeamSinogram(BremFanParams ctSet, float[] image, int width, int height)
	{

		//Set up the progress bar
		JPanel fldPanel = new JPanel();
		JFrame frame = new JFrame("CT Scan Progress");		
		JProgressBar prgBar = new JProgressBar(0,ctSet.nBins+1);

		frame.setSize(400, 100);
		frame.setLocationRelativeTo(null);

		prgBar.setPreferredSize(new Dimension(350, 30));
		prgBar.setValue(0);
		prgBar.setStringPainted(true);			
		fldPanel.add(prgBar);

		frame.add(fldPanel);
		frame.setVisible(true);

		MuMassCalculator mmc = new MuMassCalculator();
		
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


			// get the attenuation of the materials in the list
			float[] muLin = new float[ctSet.matlFormula.length];
			for(int tag =1;tag<ctSet.matlFormula.length;tag++)
			{
				muLin[tag] = (float) ( mmc.getMuMass(ctSet.matlFormula[tag], meV, "TotAttn") * ctSet.matlGmPerCC[tag]);        	
			}

			//Make a copy of the tagged-segmented image
			muLinImage = image.clone();

			// convert tags to attenuation using indirect addressing
			//The Materials file can be as long as needed without penalty.
			for(int tag=1;tag<muLinImage.length;tag++)
			{
				muLinImage[tag] = muLin[(int)muLinImage[tag]];
			}

			//Create the sinogram projection of the material linear attenuation
			sino = imageToFanBeamSinogram(muLinImage, width, height, (float)ctSet.pixSizeCM, ctSet.srcToDetCM, ctSet.magnification, ctSet.numAng,false);

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
	
	/**Wrapper method that accepts the parameter block instead of arguments.
	 * @param image Reference to an NxN (square) floating point image
	 * @param width The image width in pixels
	 * @param height The image height in pixels
	 * @param fanSettings A FanParam parameter list
	 * @return a sinogram of the input image
	 */
	public float[] imageToFanBeamSinogram(float[] image,int width,int height, FanParams fanSettings, boolean showProgress)
	{
		return imageToFanBeamSinogram(image,width,height, (float)fanSettings.pixSizeCM,
				fanSettings.srcToDetCM, fanSettings.magnification, fanSettings.numAng, showProgress);
	}

	//*******************************************************************************

	/**Creates a sinogram from an image using Fan Beam projection
	 * @param image Reference to an NxN (square) floating point image
	 * @param width The image width in pixels
	 * @param height The image height in pixels
	 * @param pixSizeCM The image pixel size in centimeters
	 * @param srcToDetCM The source to detector distance in pixels
	 * @param magnification A positive number for the fan projection magnification. In the real world always greater than 1
	 * @param numAngles The number of projections between 0 and 360 degrees rotation
	 * @param showProgress Display a progress bar
	 * @return Reference to a floating point sinogram dimensions (width x magnification) x numAngles
	 */
	protected  float[] imageToFanBeamSinogramOld(float[] image,int width,int height, float pixSizeCM,
			float srcToDetCM, float magnification, int numAngles, boolean showProgress)	
	{
		JProgressBar prgBar = null;
		JFrame frame = null;
		if(showProgress)
		{
		//Set up the progress bar
		JPanel fldPanel = new JPanel();
		frame = new JFrame("CT Scan Progress");		
		prgBar = new JProgressBar(0,numAngles);

		frame.setSize(400, 100);
		frame.setLocationRelativeTo(null);

		prgBar.setPreferredSize(new Dimension(350, 30));
		prgBar.setValue(0);
		prgBar.setStringPainted(true);			
		fldPanel.add(prgBar);

		frame.add(fldPanel);
		frame.setVisible(true);
		}
		//Function always scans 360 degrees
		//Rotating the image works poorly due to interpolation at material boundaries and
		//the continued need for interpolation along ray paths.
		//This code leaves the image stationary and rotates the source and detector locations
		//around the center of the image with sub pixel accuracy. The projector then interpolates along ray paths
		//By rotating the source and detector the number of interpolations is reduced by half

		int width2 = width/2;
		int height2 = height/2;

		float srcToDetPix = srcToDetCM/(pixSizeCM*magnification);
		float srcToSampPix = srcToDetPix/magnification;
		//float sampToDetPix =srcToDetPix - srcToSampPix;

		int detWidthPix = (int) (width*magnification);
		int detR = (int)(detWidthPix/2);

		//Equation of right side ray
		float m=(srcToDetPix-srcToSampPix)/(detR-width/2);
		float b = -srcToSampPix;

		//The positions of the virtual source and detector
		//Apply offsets to move the rotation origin to the
		//center of the image
		float srcPosY = -width2;
		float detPosY = height2;
		float srcPosX =(srcPosY - b)/m;
		float detPosX = (detPosY - b)/m;
		
		//The distance increments between points
		float srcInc = (2*srcPosX)/detWidthPix;
		float detInc = (2*detPosX)/detWidthPix;
		
		//Arrays to hold the source and detector positions.
		//the source and detector must have the same number of pixels
		//One detector point for each source point 
		DblPoint[] srcPix = new DblPoint[detWidthPix];
		DblPoint[] detPix = new DblPoint[detWidthPix];
		
		//initialize the source and detector position arrays.
		int i;
		float pos;
		for(i=0,pos = -srcPosX ;i<detWidthPix; i++, pos+=srcInc)
		{
			srcPix[i] = new DblPoint(pos,srcPosY);
		}
		for(i=0,pos = -detPosX ;i<detWidthPix; i++, pos+=detInc)
		{
			detPix[i] = new DblPoint(pos,detPosY);
		}
		
		//float start = -detWidthPix/2;

		int sinoRows = numAngles;
		float[] lineIntegrals = new float[detWidthPix*sinoRows];
		double ang, angInc = 360.0/(double)numAngles;
		int sinoRow;

		DblPoint srcPt = new DblPoint();
		DblPoint detPt = new DblPoint();

		//over-sampling caused negligible improvement to resulting reconstructions
		double overSample = 1;

		for(sinoRow=0,ang=0;sinoRow<sinoRows; sinoRow++,ang+=angInc)
		{
			double theta	= Math.toRadians(ang);
			double sinTheta	= Math.sin(theta);
			double cosTheta	= Math.cos(theta);

			//for(i=0;i<width;i++)
			for(i=0;i<detWidthPix;i++)
			{			
				//get the source and detector locations pixel coordinates
				srcPt.x = (srcPix[i].x*cosTheta-srcPix[i].y*sinTheta+width2);
				srcPt.y = (srcPix[i].x*sinTheta+srcPix[i].y*cosTheta+height2);
				detPt.x = (detPix[i].x*cosTheta-detPix[i].y*sinTheta+width2);
				detPt.y = (detPix[i].x*sinTheta+detPix[i].y*cosTheta+height2);

				//Get the line sum				
				float raySum = getRaySum((float[])image,width,height, srcPt, detPt, overSample);
				lineIntegrals[i + sinoRow*detWidthPix] = raySum;
			}
		}
		
		if(showProgress)frame.dispose();
		return lineIntegrals;
	}

	//*******************************************************************************

	/**Creates a sinogram from an image using Fan Beam projection
	 * @param image Reference to an NxN (square) floating point image
	 * @param width The image width in pixels
	 * @param height The image height in pixels
	 * @param pixSizeCM The image pixel size in centimeters
	 * @param srcToDetCM The source to detector distance in pixels
	 * @param magnification A positive number for the fan projection magnification. In the real world always greater than 1
	 * @param numAngles The number of projections between 0 and 360 degrees rotation
	 * @param showProgress Display a progress bar
	 * @return Reference to a floating point sinogram dimensions (width x magnification) x numAngles
	 */
	public  float[] imageToFanBeamSinogram(float[] image,int width,int height, float pixSizeCM,
			float srcToDetCM, float magnification, int numAngles, boolean showProgress)	
	{
		JProgressBar prgBar = null;
		JFrame frame = null;
		if(showProgress)
		{
			//Set up the progress bar
			JPanel fldPanel = new JPanel();
			frame = new JFrame("CT Scan Progress");		
			prgBar = new JProgressBar(0,numAngles);

			frame.setSize(400, 100);
			frame.setLocationRelativeTo(null);

			prgBar.setPreferredSize(new Dimension(350, 30));
			prgBar.setValue(0);
			prgBar.setStringPainted(true);			
			fldPanel.add(prgBar);

			frame.add(fldPanel);
			frame.setVisible(true);
		}
		//Function always scans 360 degrees
		//Rotating the image works poorly due to interpolation at material boundaries and
		//the continued need for interpolation along ray paths.
		//This code leaves the image stationary and rotates the source and detector locations
		//around the center of the image with sub pixel accuracy. The projector then interpolates along ray paths
		//By rotating the source and detector the number of interpolations is reduced by half

		int width2 = width/2;
		int height2 = height/2;

		float srcToDetPix = srcToDetCM/(pixSizeCM*magnification);
		float srcToSampPix = srcToDetPix/magnification;
		//float sampToDetPix =srcToDetPix - srcToSampPix;

		double edgeRayAng = Math.atan(width2/srcToSampPix);
		double detR = width/2;

		//The point at which the edge ray is tangent to the edge of the circular sample
		double tangentX = detR*Math.cos(edgeRayAng);
		double tangentY = detR*Math.sin(edgeRayAng);

		//Linear equation of edge ray
		double m = (tangentY-srcToSampPix)/tangentX;
		double b = srcToSampPix;

		//The positions of the virtual source and detector
		//Apply offsets to move the rotation origin to the center of the image
		double srcPosY = width2;
		double detPosY = -height2;
		double srcPosX =(srcPosY - b)/m;
		double detPosX = (detPosY - b)/m;

		//The detector and source widths in pixels
		int detWidthPix = (int) (width*magnification);
		int srcWidthPix = detWidthPix;

		//The distance increments between points
		double srcInc = (2*srcPosX)/srcWidthPix;
		double detInc = (2*detPosX)/detWidthPix;

		//Arrays to hold the source and detector positions.
		//the source and detector must have the same number of pixels
		//One detector point for each source point 
		DblPoint[] srcPix = new DblPoint[detWidthPix];
		DblPoint[] detPix = new DblPoint[detWidthPix];

		//initialize the source and detector position arrays.
		int i;
		double pos;
		for(i=0,pos = -srcPosX ;i<detWidthPix; i++, pos+=srcInc)
		{
			srcPix[i] = new DblPoint(pos,srcPosY);
		}
		for(i=0,pos = -detPosX ;i<detWidthPix; i++, pos+=detInc)
		{
			detPix[i] = new DblPoint(pos,detPosY);
		}

		int sinoRows = numAngles;
		float[] lineIntegrals = new float[detWidthPix*sinoRows];
		double ang, angInc = 360.0/(double)numAngles;
		int sinoRow;

		DblPoint srcPt = new DblPoint();
		DblPoint detPt = new DblPoint();

		//over-sampling caused negligible improvement to resulting reconstructions
		double overSample = 1;

		for(sinoRow=0,ang=0;sinoRow<sinoRows; sinoRow++,ang+=angInc)
		{
			if(showProgress) prgBar.setValue(sinoRow);
			double theta	= Math.toRadians(ang);
			double sinTheta	= Math.sin(theta);
			double cosTheta	= Math.cos(theta);

			//for(i=0;i<width;i++)
			for(i=0;i<detWidthPix;i++)
			{			
				//get the source and detector locations pixel coordinates
				srcPt.x = (srcPix[i].x*cosTheta-srcPix[i].y*sinTheta+width2);
				srcPt.y = (srcPix[i].x*sinTheta+srcPix[i].y*cosTheta+height2);
				detPt.x = (detPix[i].x*cosTheta-detPix[i].y*sinTheta+width2);
				detPt.y = (detPix[i].x*sinTheta+detPix[i].y*cosTheta+height2);

				//Get the line sum				
				float raySum = getRaySum((float[])image,width,height, srcPt, detPt, overSample);
				lineIntegrals[i + sinoRow*detWidthPix] = raySum;
			}
		}

		if(showProgress)frame.dispose();
		return lineIntegrals;
	}


	//*******************************************************************************
	
	/**Wrapper method for the full path method that accepts the parameter block instead of arguments.
	 * @param image Reference to an NxN (square) floating point image
	 * @param width The image width in pixels
	 * @param height The image height in pixels
	 * @param fanSettings A FanParam parameter list
	 * @return a sinogram of the input image
	 */
	protected float[] imageToFanBeamSinogramFullPath(float[] image,int width,int height, FanParams fanSettings, boolean showProgress)
	{
		return imageToFanBeamSinogramFullPath(image,width,height, (float)fanSettings.pixSizeCM,
				fanSettings.srcToDetCM, fanSettings.magnification, fanSettings.numAng, showProgress);
	}

	//*******************************************************************************

	/**Creates a sinogram from an image using Fan Beam projection along the entire source to detector path
	 *   It is an older version that is significantly slower than the shortened path method but is retained
	 *   as it might become useful for treating x-ray beam path scatter.
	 * @param image Reference to an NxN (square) floating point image
	 * @param width The image width in pixels
	 * @param height The image height in pixels
	 * @param pixSizeCM The image pixel size in centimeters
	 * @param srcToDetCM The source to detector distance in pixels
	 * @param magnification A positive number for the fan projection magnification. In the real world always greater than 1
	 * @param numAngles The number of projections between 0 and 360 degrees rotation
	 * @param showProgress Display a progress bar
	 * @return Reference to a floating point sinogram dimensions (width x magnification) x numAngles
	 */
	protected  float[] imageToFanBeamSinogramFullPath(float[] image,int width,int height, float pixSizeCM,
			float srcToDetCM, float magnification, int numAngles, boolean showProgress)	
	{
		JProgressBar prgBar = null;
		JFrame frame = null;
		if(showProgress)
		{
		//Set up the progress bar
		JPanel fldPanel = new JPanel();
		frame = new JFrame("CT Scan Progress");		
		prgBar = new JProgressBar(0,numAngles);

		frame.setSize(400, 100);
		frame.setLocationRelativeTo(null);

		prgBar.setPreferredSize(new Dimension(350, 30));
		prgBar.setValue(0);
		prgBar.setStringPainted(true);			
		fldPanel.add(prgBar);

		frame.add(fldPanel);
		frame.setVisible(true);
		}
		//Function always scans 360 degrees
		//Rotating the image works poorly due to interpolation at material boundaries and
		//the continued need for interpolation along ray paths.
		//This code leaves the image stationary and rotates the source and detector locations
		//around the center of the image with sub pixel accuracy. The projector then interpolates along ray paths
		//By rotating the source and detector the number of interpolations is reduced by half

		int rotXo = width/2;
		int rotYo = height/2;

		float srcToDetPix = srcToDetCM/(pixSizeCM*magnification);
		float srcToSampPix = srcToDetPix/magnification;
		float sampToDetPix =srcToDetPix - srcToSampPix;

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
			if(showProgress) prgBar.setValue(sinoRow);
			double theta	= Math.toRadians(ang);
			double sinTheta	= Math.sin(theta);
			double cosTheta	= Math.cos(theta);

			//get the source pixel coordinates
			srcPt.x = (rotXo + srcToSampPix*sinTheta);
			srcPt.y = (rotYo -srcToSampPix*cosTheta);

			for(detPix=start,sinoCol=0;sinoCol<detWidthPix;detPix+=1,sinoCol++)
			{			
				//get the detector pixel coordinates
				detPt.x = (float)(detPix*cosTheta-sampToDetPix*sinTheta+rotXo);
				detPt.y = (float)(sampToDetPix*cosTheta+detPix*sinTheta+rotYo);

				//Get the line sum				
				float raySum = getRaySum(image,width,height, srcPt, detPt, overSample);
				lineIntegrals[sinoCol + sinoRow*detWidthPix] = raySum;
			}
		}	
		if(showProgress)frame.dispose();
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

