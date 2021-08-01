package jhd.Projection;

import jhd.MathTools.Interpolation;

/**
 * Create sinograms from square floating point images
 * @author John H. Dunsmuir
 *
 */
public class Projectors
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

	/**
	 * @param image Reference to an NxN (square) floating point image
	 * @param width The image width
	 * @param height The image height
	 * @param numAngles The number of projections between 0 and 180 degrees rotation
	 * @return Reference to a floating point sinogram dimensions width x numAngles
	 */
	public float[] imageToParallelelSinogram(float[] image,int width,int height,int numAngles)	
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

	/**
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
		
		int detWidthPix = (int) (width*magnification);		//IJ.log("detWidth=" + detWidthPix );
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
