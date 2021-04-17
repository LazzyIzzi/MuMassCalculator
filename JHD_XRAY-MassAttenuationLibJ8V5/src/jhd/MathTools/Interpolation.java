package jhd.MathTools;

/**
 * Special purpose static functions 
 * @author John H Dunsmuir
 *
 */
public class Interpolation {
	
	/**
	 * @param meV1 value less than newMev
	 * @param meV2 value greater than newMev
	 * @param newMeV  value for which newMuMass is sought
	 * @param muMass1 value at mev1
	 * @param muMass2 value at mev2
	 * @return the log-log interpolated newMuMass
	 */
	public static double logTerp(double meV1, double meV2, double newMeV, double muMass1, double muMass2)
	{
		double logMeV1, logMeV2, logNewMeV, logMuMass1, logMuMass2;
		double m, diff;
		
		logMeV1 = Math.log(meV1);
		logMeV2 = Math.log(meV2);
		logNewMeV = Math.log(newMeV);
		logMuMass1 = Math.log(muMass1);
		logMuMass2 = Math.log(muMass2);
	    m = (logMeV2 - logNewMeV) / (logMeV2 - logMeV1);
	    diff = logMuMass2 - logMuMass1;
	    return  Math.exp(logMuMass2 - m * diff); 
	}
	
	/**
	 * @param meV1 value less than newMev
	 * @param meV2 value greater than newMev
	 * @param newMeV  value for which newMuMass is sought
	 * @param muMass1 value at mev1
	 * @param muMass2 value at mev2
	 * @return the linearly interpolated newMuMass
	 */
	public static double linTerp(double meV1, double meV2, double newMeV, double muMass1, double muMass2)
	{
		double m, diff;
		
		m = (meV2 - newMeV) / (meV2 - meV1);
		diff = muMass2 - muMass1;
		return muMass2 - m * diff;
	}
}
