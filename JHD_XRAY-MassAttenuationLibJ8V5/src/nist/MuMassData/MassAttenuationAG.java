package nist.MuMassData;


import jhd.MathTools.Interpolation;
/**
 * @author John
 * @hidden
 *Look-up method for AG (Silver)
 */


class MassAttenuationAG {
	/**
	 * Table of photon energies
	 */
	final static double[] MeV= {1.00000E-03,1.50000E-03,2.00000E-03,3.00000E-03,3.35100E-03,3.35110E-03,3.43600E-03,3.52400E-03,3.52410E-03,
			3.66200E-03,3.80600E-03,3.80610E-03,4.00000E-03,5.00000E-03,6.00000E-03,8.00000E-03,1.00000E-02,1.50000E-02,2.00000E-02,
			2.55100E-02,2.55101E-02,3.00000E-02,4.00000E-02,5.00000E-02,6.00000E-02,8.00000E-02,1.00000E-01,1.50000E-01,2.00000E-01,
			3.00000E-01,4.00000E-01,5.00000E-01,6.00000E-01,8.00000E-01,1.00000E+00,1.02200E+00,1.25000E+00,1.50000E+00,2.00000E+00,
			2.04400E+00,3.00000E+00,4.00000E+00,5.00000E+00,6.00000E+00,7.00000E+00,8.00000E+00,9.00000E+00,1.00000E+01,1.10000E+01,
			1.20000E+01,1.30000E+01,1.40000E+01,1.50000E+01,1.60000E+01,1.80000E+01,2.00000E+01,2.20000E+01,2.40000E+01,2.60000E+01,
			2.80000E+01,3.00000E+01,4.00000E+01,5.00000E+01,6.00000E+01,8.00000E+01,1.00000E+02,1.50000E+02,2.00000E+02,3.00000E+02,
			4.00000E+02,5.00000E+02,6.00000E+02,8.00000E+02,1.00000E+03,1.50000E+03,2.00000E+03,3.00000E+03,4.00000E+03,5.00000E+03,
			6.00000E+03,8.00000E+03,1.00000E+04,1.50000E+04,2.00000E+04,3.00000E+04,4.00000E+04,5.00000E+04,6.00000E+04,8.00000E+04,
			1.00000E+05};
	/**
	 * Table of Coherent (Raleigh) scattering cross sections in cm2/gm
	 */
	final static double[] Coh= {7.8330E+00,7.4530E+00,7.0180E+00,6.1130E+00,5.8120E+00,5.8120E+00,5.7400E+00,5.6670E+00,5.6670E+00,5.5530E+00,5.4370E+00,5.4370E+00,5.2850E+00,4.5770E+00,3.9960E+00,3.1420E+00,2.5630E+00,1.6780E+00,1.1650E+00,8.2680E-01,8.2680E-01,6.5430E-01,4.2750E-01,3.0170E-01,2.2340E-01,1.3640E-01,9.2340E-02,4.4810E-02,2.6380E-02,1.2240E-02,7.0340E-03,4.5580E-03,3.1910E-03,1.8110E-03,1.1640E-03,1.1150E-03,7.4750E-04,5.1990E-04,2.9290E-04,2.8050E-04,1.3040E-04,7.3360E-05,4.6960E-05,3.2620E-05,2.3970E-05,1.8350E-05,1.4500E-05,1.1750E-05,9.7090E-06,8.1570E-06,6.9510E-06,5.9900E-06,5.2200E-06,4.5880E-06,3.6250E-06,2.9370E-06,2.4270E-06,2.0390E-06,1.7370E-06,1.4980E-06,1.3050E-06,7.3410E-07,4.6990E-07,3.2630E-07,1.8350E-07,1.1750E-07,5.2200E-08,2.9360E-08,1.3050E-08,7.3410E-09,4.6980E-09,3.2630E-09,1.8350E-09,1.1750E-09,5.2200E-10,2.9360E-10,1.3050E-10,7.3410E-11,4.6980E-11,3.2630E-11,1.8350E-11,1.1750E-11,5.2200E-12,2.9360E-12,1.3050E-12,7.3410E-13,4.6980E-13,3.2630E-13,1.8350E-13,1.1750E-13};
	/**
	 * Table of Incoherent (Compton) scattering cross sections in cm2/gm
	 */
	final static double[] Incoh= {4.9810E-03,9.1670E-03,1.3450E-02,2.1730E-02,2.4460E-02,2.4460E-02,2.5110E-02,2.5770E-02,2.5770E-02,2.6810E-02,2.7880E-02,2.7880E-02,2.9300E-02,3.6220E-02,4.2670E-02,5.4240E-02,6.3870E-02,8.1010E-02,9.2170E-02,1.0070E-01,1.0070E-01,1.0560E-01,1.1260E-01,1.1620E-01,1.1790E-01,1.1800E-01,1.1600E-01,1.0890E-01,1.0190E-01,9.0390E-02,8.1680E-02,7.4980E-02,6.9620E-02,6.1360E-02,5.5240E-02,5.4660E-02,4.9450E-02,4.4980E-02,3.8410E-02,3.7940E-02,3.0240E-02,2.5230E-02,2.1790E-02,1.9260E-02,1.7310E-02,1.5750E-02,1.4490E-02,1.3420E-02,1.2520E-02,1.1740E-02,1.1060E-02,1.0460E-02,9.9320E-03,9.4630E-03,8.6480E-03,7.9720E-03,7.4080E-03,6.9230E-03,6.4980E-03,6.1300E-03,5.8060E-03,4.6130E-03,3.8510E-03,3.3180E-03,2.6170E-03,2.1720E-03,1.5460E-03,1.2130E-03,8.5920E-04,6.7330E-04,5.5720E-04,4.7710E-04,3.7260E-04,3.0660E-04,2.1410E-04,1.6570E-04,1.1520E-04,8.8940E-05,7.2690E-05,6.1630E-05,4.7500E-05,3.8780E-05,2.6800E-05,2.0610E-05,1.4210E-05,1.0910E-05,8.8820E-06,7.5090E-06,5.7560E-06,4.6850E-06};
	/**
	 * Table of PhotoElectric absorption cross sections in cm2/gm
	 */
	final static double[] PE= {7.0290E+03,2.7830E+03,1.3930E+03,5.0750E+02,3.8290E+02,1.2680E+03,1.1920E+03,1.1200E+03,1.5410E+03,1.4020E+03,1.2760E+03,1.4620E+03,1.3000E+03,7.3410E+02,4.5700E+02,2.1320E+02,1.1660E+02,3.8220E+01,1.7100E+01,8.5980E+00,5.4470E+01,3.5920E+01,1.6650E+01,9.0270E+00,5.4250E+00,2.3960E+00,1.2610E+00,3.8890E-01,1.6890E-01,5.3390E-02,2.4390E-02,1.3670E-02,8.7200E-03,4.4990E-03,2.8070E-03,2.6710E-03,1.8020E-03,1.2920E-03,7.8770E-04,7.5980E-04,4.1940E-04,2.7860E-04,2.0650E-04,1.6320E-04,1.3460E-04,1.1430E-04,9.9260E-05,8.7650E-05,7.8440E-05,7.0960E-05,6.4760E-05,5.9510E-05,5.5100E-05,5.1260E-05,4.5000E-05,4.0080E-05,3.6140E-05,3.2890E-05,3.0180E-05,2.7880E-05,2.5900E-05,1.9120E-05,1.5150E-05,1.2540E-05,9.3350E-06,7.4310E-06,4.9210E-06,3.6790E-06,2.4440E-06,1.8300E-06,1.4630E-06,1.2180E-06,9.1280E-07,7.2970E-07,4.8630E-07,3.6460E-07,2.4300E-07,1.8220E-07,1.4580E-07,1.2140E-07,9.1060E-08,7.2860E-08,4.8570E-08,3.6420E-08,2.4280E-08,1.8210E-08,1.4570E-08,1.2140E-08,9.1060E-09,7.2860E-09};
	/**
	 * Table of cross-sections in cm2/gm for Pair Production in a Nuclear Field
	 */
	final static double[] PrProdN= {0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,1.6030E-04,7.5030E-04,2.6020E-03,2.7840E-03,6.7390E-03,1.0430E-02,1.3640E-02,1.6420E-02,1.8890E-02,2.1120E-02,2.3150E-02,2.5000E-02,2.6700E-02,2.8270E-02,2.9720E-02,3.1060E-02,3.2300E-02,3.3460E-02,3.5580E-02,3.7470E-02,3.9190E-02,4.0750E-02,4.2180E-02,4.3480E-02,4.4690E-02,4.9560E-02,5.3180E-02,5.6000E-02,6.0180E-02,6.3140E-02,6.7830E-02,7.0620E-02,7.3920E-02,7.5760E-02,7.6990E-02,7.7880E-02,7.9050E-02,7.9830E-02,8.0950E-02,8.1510E-02,8.2180E-02,8.2510E-02,8.2740E-02,8.2910E-02,8.3070E-02,8.3240E-02,8.3410E-02,8.3520E-02,8.3580E-02,8.3630E-02,8.3690E-02,8.3690E-02,8.3740E-02,8.3740E-02};
	/**
	 * Table of cross-sections in cm2/gm for Pair Production in an Electric Field
	 */
	final static double[] PrProdE= {0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,1.0580E-05,4.3150E-05,8.5920E-05,1.3180E-04,1.7750E-04,2.2180E-04,2.6410E-04,3.0440E-04,3.4250E-04,3.7830E-04,4.1210E-04,4.4430E-04,4.7480E-04,5.0370E-04,5.5730E-04,6.0630E-04,6.5040E-04,6.9170E-04,7.2970E-04,7.6430E-04,7.9720E-04,9.3230E-04,1.0350E-03,1.1170E-03,1.2390E-03,1.3280E-03,1.4760E-03,1.5680E-03,1.6820E-03,1.7490E-03,1.7950E-03,1.8290E-03,1.8750E-03,1.9070E-03,1.9520E-03,1.9780E-03,2.0060E-03,2.0230E-03,2.0330E-03,2.0400E-03,2.0490E-03,2.0560E-03,2.0630E-03,2.0680E-03,2.0730E-03,2.0760E-03,2.0770E-03,2.0790E-03,2.0800E-03,2.0810E-03};
	/**
	 * Table of total cross-sections in cm2/gm
	 */
	final static double[] TotAttn= {7.0370E+03,2.7910E+03,1.4010E+03,5.1360E+02,3.8870E+02,1.2740E+03,1.1980E+03,1.1260E+03,1.5470E+03,1.4080E+03,1.2820E+03,1.4680E+03,1.3050E+03,7.3880E+02,4.6110E+02,2.1640E+02,1.1930E+02,3.9980E+01,1.8360E+01,9.5250E+00,5.5390E+01,3.6680E+01,1.7190E+01,9.4450E+00,5.7660E+00,2.6510E+00,1.4700E+00,5.4260E-01,2.9720E-01,1.5600E-01,1.1310E-01,9.3210E-02,8.1530E-02,6.7670E-02,5.9210E-02,5.8440E-02,5.2160E-02,4.7540E-02,4.2090E-02,4.1760E-02,3.7540E-02,3.6060E-02,3.5770E-02,3.6010E-02,3.6540E-02,3.7230E-02,3.8010E-02,3.8830E-02,3.9650E-02,4.0470E-02,4.1270E-02,4.2040E-02,4.2760E-02,4.3480E-02,4.4830E-02,4.6090E-02,4.7290E-02,4.8400E-02,4.9440E-02,5.0400E-02,5.1320E-02,5.5130E-02,5.8080E-02,6.0440E-02,6.4050E-02,6.6650E-02,7.0860E-02,7.3410E-02,7.6460E-02,7.8180E-02,7.9340E-02,8.0190E-02,8.1300E-02,8.2050E-02,8.3120E-02,8.3650E-02,8.4300E-02,8.4630E-02,8.4840E-02,8.5010E-02,8.5170E-02,8.5330E-02,8.5500E-02,8.5610E-02,8.5660E-02,8.5720E-02,8.5770E-02,8.5770E-02,8.5830E-02,8.5830E-02};

	//******************************************************************************

	/**
	 * @return The array of tabulated photon energies
	 */
	public double[] getMevArray()
	{
		return MeV;
	}

	//******************************************************************************

	/**
	 * @param theMeV The photon energy 
	 * @param muMassType Recognized types "TotalAttn" "Raleigh" "Compton" "PhotoElectric" "PairProdN" "PairProdE" 
	 * @return Mass attenuation in cm2/gm
	 */
	public double getMuMass(double theMeV, String muMassType)
	{
		double[] muArr;
		double muMass;
		double muLo = 0;
		double muHi = 0;
		double mevLo = 0;
		double mevHi = 0;

		switch(muMassType)
		{
		case "TotAttn":
			muArr = TotAttn;
			break;
		case "Raleigh":
			muArr = Coh;
			break;
		case "Compton":
			muArr = Incoh;
			break;
		case "PhotoElectric":
			muArr = PE;
			break;
		case "PairProdN":
			muArr = PrProdN;
			break;
		case "PairProdE":
			muArr = PrProdE;
			break;
		default:
			muArr = TotAttn;
			break;
		}

		//Report the muMass at 100GeV for any energy at or above 100GeV
		if(theMeV >= 100000.0)
		{
			muMass = muArr[muArr.length - 1];
		}			
		else
		{
			//Look everywhere except the last point
			for(int i = 0; i< muArr.length - 1;i++)
			{
				if(MeV[i] <= theMeV && MeV[i + 1] > theMeV)
				{
					mevLo = MeV[i];
					mevHi = MeV[i+1];
					muLo = muArr[i];
					muHi = muArr[i + 1];
					break;
				}
			}
			if(muLo ==0) muMass = 0.0;
			else muMass = Interpolation.logTerp(mevLo, mevHi, theMeV, muLo, muHi);
		}
		return muMass;
	}		
}
