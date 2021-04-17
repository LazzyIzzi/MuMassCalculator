package nist.MuMassData;
/**
 * @author John H Dunsmuir
 * @hidden
 */

import jhd.MathTools.Interpolation;


class MassAttenuationDY {

	final static double[] MeV= {1.00000E-03,1.13800E-03,1.29500E-03,1.29501E-03,1.31400E-03,1.33200E-03,1.33201E-03,1.50000E-03,1.67600E-03,
			1.67601E-03,1.75700E-03,1.84200E-03,1.84201E-03,2.00000E-03,2.04700E-03,2.04701E-03,3.00000E-03,4.00000E-03,5.00000E-03,
			6.00000E-03,7.79000E-03,7.79010E-03,8.00000E-03,8.58100E-03,8.58110E-03,8.81000E-03,9.04600E-03,9.04610E-03,1.00000E-02,
			1.50000E-02,2.00000E-02,3.00000E-02,4.00000E-02,5.00000E-02,5.37900E-02,5.37910E-02,6.00000E-02,8.00000E-02,1.00000E-01,
			1.50000E-01,2.00000E-01,3.00000E-01,4.00000E-01,5.00000E-01,6.00000E-01,8.00000E-01,1.00000E+00,1.02200E+00,1.25000E+00,
			1.50000E+00,2.00000E+00,2.04400E+00,3.00000E+00,4.00000E+00,5.00000E+00,6.00000E+00,7.00000E+00,8.00000E+00,9.00000E+00,
			1.00000E+01,1.10000E+01,1.20000E+01,1.30000E+01,1.40000E+01,1.50000E+01,1.60000E+01,1.80000E+01,2.00000E+01,2.20000E+01,
			2.40000E+01,2.60000E+01,2.80000E+01,3.00000E+01,4.00000E+01,5.00000E+01,6.00000E+01,8.00000E+01,1.00000E+02,1.50000E+02,
			2.00000E+02,3.00000E+02,4.00000E+02,5.00000E+02,6.00000E+02,8.00000E+02,1.00000E+03,1.50000E+03,2.00000E+03,3.00000E+03,
			4.00000E+03,5.00000E+03,6.00000E+03,8.00000E+03,1.00000E+04,1.50000E+04,2.00000E+04,3.00000E+04,4.00000E+04,5.00000E+04,
			6.00000E+04,8.00000E+04,1.00000E+05};
	final static double[] Coh= {1.0210E+01,1.0090E+01,9.9390E+00,9.9390E+00,9.9210E+00,9.9020E+00,9.9020E+00,9.7350E+00,9.5610E+00,9.5610E+00,9.4860E+00,9.4060E+00,9.4060E+00,9.2430E+00,9.1940E+00,9.1940E+00,8.2490E+00,7.3040E+00,6.4630E+00,5.7330E+00,4.6880E+00,4.6880E+00,4.5840E+00,4.3100E+00,4.3100E+00,4.2080E+00,4.1060E+00,4.1060E+00,3.7320E+00,2.4110E+00,1.7180E+00,1.0120E+00,6.6080E-01,4.6770E-01,4.1730E-01,4.1730E-01,3.5120E-01,2.1990E-01,1.5000E-01,7.3120E-02,4.3510E-02,2.0540E-02,1.1900E-02,7.7450E-03,5.4400E-03,3.1030E-03,2.0010E-03,1.9170E-03,1.2880E-03,8.9720E-04,5.0660E-04,4.8510E-04,2.2580E-04,1.2710E-04,8.1380E-05,5.6520E-05,4.1540E-05,3.1810E-05,2.5130E-05,2.0360E-05,1.6830E-05,1.4140E-05,1.2050E-05,1.0390E-05,9.0500E-06,7.9530E-06,6.2850E-06,5.0920E-06,4.2060E-06,3.5350E-06,3.0130E-06,2.5970E-06,2.2630E-06,1.2730E-06,8.1460E-07,5.6550E-07,3.1820E-07,2.0360E-07,9.0500E-08,5.0920E-08,2.2620E-08,1.2730E-08,8.1460E-09,5.6550E-09,3.1820E-09,2.0360E-09,9.0500E-10,5.0920E-10,2.2620E-10,1.2730E-10,8.1460E-11,5.6550E-11,3.1820E-11,2.0360E-11,9.0500E-12,5.0920E-12,2.2620E-12,1.2730E-12,8.1460E-13,5.6550E-13,3.1820E-13,2.0360E-13};
	final static double[] Incoh= {5.2660E-03,6.1960E-03,7.2750E-03,7.2750E-03,7.4040E-03,7.5340E-03,7.5340E-03,8.6720E-03,9.8610E-03,9.8610E-03,1.0400E-02,1.0970E-02,1.0970E-02,1.2030E-02,1.2340E-02,1.2340E-02,1.8640E-02,2.4820E-02,3.0500E-02,3.5640E-02,4.3620E-02,4.3620E-02,4.4470E-02,4.6770E-02,4.6770E-02,4.7640E-02,4.8510E-02,4.8510E-02,5.1920E-02,6.6670E-02,7.7570E-02,9.1130E-02,9.8320E-02,1.0220E-01,1.0320E-01,1.0320E-01,1.0420E-01,1.0540E-01,1.0450E-01,9.9210E-02,9.3320E-02,8.3270E-02,7.5530E-02,6.9490E-02,6.4560E-02,5.7000E-02,5.1360E-02,5.0850E-02,4.6030E-02,4.1880E-02,3.5770E-02,3.5330E-02,2.8170E-02,2.3510E-02,2.0310E-02,1.7950E-02,1.6140E-02,1.4690E-02,1.3500E-02,1.2510E-02,1.1670E-02,1.0940E-02,1.0310E-02,9.7540E-03,9.2570E-03,8.8160E-03,8.0600E-03,7.4300E-03,6.9040E-03,6.4520E-03,6.0590E-03,5.7150E-03,5.4110E-03,4.2990E-03,3.5890E-03,3.0930E-03,2.4390E-03,2.0240E-03,1.4420E-03,1.1300E-03,8.0120E-04,6.2740E-04,5.1960E-04,4.4470E-04,3.4730E-04,2.8570E-04,1.9960E-04,1.5450E-04,1.0740E-04,8.2900E-05,6.7780E-05,5.7480E-05,4.4290E-05,3.6150E-05,2.4990E-05,1.9210E-05,1.3240E-05,1.0170E-05,8.2830E-06,7.0010E-06,5.3660E-06,4.3660E-06};
	final static double[] PE= {2.4840E+03,1.9370E+03,1.5120E+03,2.1130E+03,3.1460E+03,4.6840E+03,5.4960E+03,5.5400E+03,4.2210E+03,4.8840E+03,4.3740E+03,3.9130E+03,4.1580E+03,3.4570E+03,3.2790E+03,3.4250E+03,1.3960E+03,6.8780E+02,3.9250E+02,2.4620E+02,1.2510E+02,3.3890E+02,3.2230E+02,2.6510E+02,3.6290E+02,3.4010E+02,3.1870E+02,3.6840E+02,2.8640E+02,9.9170E+01,4.5840E+01,1.5150E+01,6.8230E+00,3.6570E+00,2.9790E+00,1.6240E+01,1.2130E+01,5.6890E+00,3.1060E+00,1.0160E+00,4.5840E-01,1.5210E-01,7.1560E-02,4.0910E-02,2.6450E-02,1.3830E-02,8.6720E-03,8.2980E-03,5.5920E-03,3.9950E-03,2.4220E-03,2.3360E-03,1.2750E-03,8.3980E-04,6.1810E-04,4.8620E-04,3.9910E-04,3.3800E-04,2.9270E-04,2.5790E-04,2.3030E-04,2.0800E-04,1.8960E-04,1.7410E-04,1.6090E-04,1.4960E-04,1.3110E-04,1.1660E-04,1.0500E-04,9.5500E-05,8.7530E-05,8.0830E-05,7.5050E-05,5.5260E-05,4.3730E-05,3.6180E-05,2.6880E-05,2.1380E-05,1.4150E-05,1.0570E-05,7.0190E-06,5.2550E-06,4.1990E-06,3.4970E-06,2.6200E-06,2.0950E-06,1.3960E-06,1.0460E-06,6.9710E-07,5.2290E-07,4.1840E-07,3.4850E-07,2.6130E-07,2.0910E-07,1.3940E-07,1.0450E-07,6.9670E-08,5.2250E-08,4.1800E-08,3.4840E-08,2.6130E-08,2.0900E-08};
	final static double[] PrProdN= {0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,2.6720E-04,1.2370E-03,3.9470E-03,4.1990E-03,9.3650E-03,1.3970E-02,1.7880E-02,2.1240E-02,2.4210E-02,2.6880E-02,2.9310E-02,3.1530E-02,3.3590E-02,3.5500E-02,3.7280E-02,3.8950E-02,4.0470E-02,4.1910E-02,4.4510E-02,4.6810E-02,4.8920E-02,5.0810E-02,5.2550E-02,5.4140E-02,5.5590E-02,6.1590E-02,6.6040E-02,6.9520E-02,7.4600E-02,7.8230E-02,8.3940E-02,8.7310E-02,9.1280E-02,9.3540E-02,9.5020E-02,9.6090E-02,9.7500E-02,9.8390E-02,9.9730E-02,1.0040E-01,1.0120E-01,1.0170E-01,1.0190E-01,1.0210E-01,1.0230E-01,1.0250E-01,1.0270E-01,1.0280E-01,1.0290E-01,1.0300E-01,1.0300E-01,1.0310E-01,1.0310E-01,1.0310E-01};
	final static double[] PrProdE= {0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,9.8470E-06,4.0140E-05,7.9900E-05,1.2250E-04,1.6490E-04,2.0590E-04,2.4510E-04,2.8230E-04,3.1740E-04,3.5060E-04,3.8170E-04,4.1140E-04,4.3950E-04,4.6580E-04,5.1510E-04,5.5960E-04,6.0070E-04,6.3820E-04,6.7260E-04,7.0450E-04,7.3410E-04,8.5680E-04,9.4910E-04,1.0220E-03,1.1310E-03,1.2100E-03,1.3400E-03,1.4210E-03,1.5190E-03,1.5780E-03,1.6180E-03,1.6480E-03,1.6880E-03,1.7150E-03,1.7550E-03,1.7780E-03,1.8030E-03,1.8180E-03,1.8270E-03,1.8330E-03,1.8420E-03,1.8470E-03,1.8540E-03,1.8590E-03,1.8630E-03,1.8650E-03,1.8670E-03,1.8680E-03,1.8690E-03,1.8700E-03};
	final static double[] TotAttn= {2.4940E+03,1.9480E+03,1.5220E+03,2.1230E+03,3.1560E+03,4.6940E+03,5.5060E+03,5.5500E+03,4.2310E+03,4.8940E+03,4.3830E+03,3.9230E+03,4.1670E+03,3.4670E+03,3.2880E+03,3.4340E+03,1.4050E+03,6.9510E+02,3.9900E+02,2.5200E+02,1.2990E+02,3.4370E+02,3.2690E+02,2.6950E+02,3.6730E+02,3.4430E+02,3.2290E+02,3.7250E+02,2.9020E+02,1.0160E+02,4.7640E+01,1.6250E+01,7.5820E+00,4.2270E+00,3.5000E+00,1.6760E+01,1.2590E+01,6.0140E+00,3.3600E+00,1.1880E+00,5.9520E-01,2.5590E-01,1.5900E-01,1.1810E-01,9.6440E-02,7.3930E-02,6.2040E-02,6.1060E-02,5.3180E-02,4.8010E-02,4.2650E-02,4.2350E-02,3.9050E-02,3.8490E-02,3.8970E-02,3.9860E-02,4.0960E-02,4.2150E-02,4.3370E-02,4.4610E-02,4.5820E-02,4.7020E-02,4.8170E-02,4.9300E-02,5.0340E-02,5.1350E-02,5.3220E-02,5.4920E-02,5.6530E-02,5.8000E-02,5.9370E-02,6.0650E-02,6.1810E-02,6.6800E-02,7.0620E-02,7.3670E-02,7.8200E-02,8.1490E-02,8.6740E-02,8.9870E-02,9.3600E-02,9.5750E-02,9.7160E-02,9.8190E-02,9.9540E-02,1.0040E-01,1.0170E-01,1.0240E-01,1.0310E-01,1.0360E-01,1.0380E-01,1.0400E-01,1.0420E-01,1.0440E-01,1.0460E-01,1.0470E-01,1.0480E-01,1.0490E-01,1.0490E-01,1.0490E-01,1.0490E-01,1.0500E-01};

	//******************************************************************************

	/**
	 * @return The array of tabulated photon energies
	 */
	public double[] getMevArray()
	{
		return MeV;
	}

	//******************************************************************************

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
