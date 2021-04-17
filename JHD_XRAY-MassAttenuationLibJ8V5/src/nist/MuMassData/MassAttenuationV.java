package nist.MuMassData;
/**
 * @author John H Dunsmuir
 * @hidden
 */

import jhd.MathTools.Interpolation;


class MassAttenuationV {

	final static double[] MeV= {1.00000E-03,1.50000E-03,2.00000E-03,3.00000E-03,4.00000E-03,5.00000E-03,5.46500E-03,5.46510E-03,6.00000E-03,
			8.00000E-03,1.00000E-02,1.50000E-02,2.00000E-02,3.00000E-02,4.00000E-02,5.00000E-02,6.00000E-02,8.00000E-02,1.00000E-01,
			1.50000E-01,2.00000E-01,3.00000E-01,4.00000E-01,5.00000E-01,6.00000E-01,8.00000E-01,1.00000E+00,1.02200E+00,1.25000E+00,
			1.50000E+00,2.00000E+00,2.04400E+00,3.00000E+00,4.00000E+00,5.00000E+00,6.00000E+00,7.00000E+00,8.00000E+00,9.00000E+00,
			1.00000E+01,1.10000E+01,1.20000E+01,1.30000E+01,1.40000E+01,1.50000E+01,1.60000E+01,1.80000E+01,2.00000E+01,2.20000E+01,
			2.40000E+01,2.60000E+01,2.80000E+01,3.00000E+01,4.00000E+01,5.00000E+01,6.00000E+01,8.00000E+01,1.00000E+02,1.50000E+02,
			2.00000E+02,3.00000E+02,4.00000E+02,5.00000E+02,6.00000E+02,8.00000E+02,1.00000E+03,1.50000E+03,2.00000E+03,3.00000E+03,
			4.00000E+03,5.00000E+03,6.00000E+03,8.00000E+03,1.00000E+04,1.50000E+04,2.00000E+04,3.00000E+04,4.00000E+04,5.00000E+04,
			6.00000E+04,8.00000E+04,1.00000E+05};
	final static double[] Coh= {3.8220E+00,3.5240E+00,3.2300E+00,2.7060E+00,2.2700E+00,1.9120E+00,1.7700E+00,1.7700E+00,1.6240E+00,1.2180E+00,9.6060E-01,6.0880E-01,4.1900E-01,2.2780E-01,1.4390E-01,9.9930E-02,7.3720E-02,4.4840E-02,3.0020E-02,1.4140E-02,8.1790E-03,3.7340E-03,2.1240E-03,1.3680E-03,9.5270E-04,5.3760E-04,3.4470E-04,3.3010E-04,2.2080E-04,1.5340E-04,8.6360E-05,8.2680E-05,3.8400E-05,2.1600E-05,1.3820E-05,9.6020E-06,7.0540E-06,5.4010E-06,4.2680E-06,3.4570E-06,2.8570E-06,2.4010E-06,2.0450E-06,1.7640E-06,1.5370E-06,1.3500E-06,1.0670E-06,8.6420E-07,7.1410E-07,6.0010E-07,5.1140E-07,4.4090E-07,3.8410E-07,2.1600E-07,1.3830E-07,9.6020E-08,5.4010E-08,3.4570E-08,1.5360E-08,8.6400E-09,3.8410E-09,2.1600E-09,1.3830E-09,9.6020E-10,5.4000E-10,3.4570E-10,1.5360E-10,8.6400E-11,3.8410E-11,2.1600E-11,1.3830E-11,9.6020E-12,5.4000E-12,3.4570E-12,1.5360E-12,8.6400E-13,3.8410E-13,2.1600E-13,1.3830E-13,9.6020E-14,5.4000E-14,3.4570E-14};
	final static double[] Incoh= {1.0710E-02,1.7970E-02,2.4340E-02,3.5950E-02,4.6530E-02,5.5960E-02,5.9980E-02,5.9980E-02,6.4330E-02,7.8140E-02,8.8630E-02,1.0580E-01,1.1640E-01,1.2770E-01,1.3190E-01,1.3310E-01,1.3290E-01,1.3040E-01,1.2670E-01,1.1690E-01,1.0830E-01,9.5090E-02,8.5540E-02,7.8280E-02,7.2500E-02,6.3780E-02,5.7380E-02,5.6780E-02,5.1330E-02,4.6670E-02,3.9840E-02,3.9340E-02,3.1350E-02,2.6150E-02,2.2590E-02,1.9970E-02,1.7950E-02,1.6330E-02,1.5010E-02,1.3910E-02,1.2970E-02,1.2160E-02,1.1460E-02,1.0840E-02,1.0290E-02,9.8040E-03,8.9600E-03,8.2620E-03,7.6750E-03,7.1710E-03,6.7350E-03,6.3530E-03,6.0150E-03,4.7800E-03,3.9900E-03,3.4380E-03,2.7110E-03,2.2510E-03,1.6030E-03,1.2570E-03,8.9050E-04,6.9750E-04,5.7740E-04,4.9440E-04,3.8610E-04,3.1760E-04,2.2180E-04,1.7170E-04,1.1940E-04,9.2150E-05,7.5340E-05,6.3880E-05,4.9230E-05,4.0190E-05,2.7770E-05,2.1350E-05,1.4730E-05,1.1310E-05,9.2070E-06,7.7820E-06,5.9680E-06,4.8550E-06};
	final static double[] PE= {6.4910E+03,2.3380E+03,1.1030E+03,3.7160E+02,1.6890E+02,9.0940E+01,7.0930E+01,5.8520E+02,4.6710E+02,2.2040E+02,1.2070E+02,3.9110E+01,1.7140E+01,5.2090E+00,2.1960E+00,1.1140E+00,6.3720E-01,2.6190E-01,1.3100E-01,3.7110E-02,1.5290E-02,4.5270E-03,1.9900E-03,1.0890E-03,6.8410E-04,3.4790E-04,2.1630E-04,2.0520E-04,1.3890E-04,1.0020E-04,6.1960E-05,5.9860E-05,3.3760E-05,2.2800E-05,1.7090E-05,1.3640E-05,1.1320E-05,9.6700E-06,8.4340E-06,7.4750E-06,6.7090E-06,6.0860E-06,5.5670E-06,5.1290E-06,4.7550E-06,4.4310E-06,3.9000E-06,3.4810E-06,3.1450E-06,2.8670E-06,2.6340E-06,2.4350E-06,2.2650E-06,1.6790E-06,1.3320E-06,1.1050E-06,8.2350E-07,6.5630E-07,4.3530E-07,3.2570E-07,2.1660E-07,1.6220E-07,1.2970E-07,1.0800E-07,8.0950E-08,6.4740E-08,4.3140E-08,3.2340E-08,2.1560E-08,1.6160E-08,1.2930E-08,1.0780E-08,8.0810E-09,6.4650E-09,4.3100E-09,3.2320E-09,2.1550E-09,1.6160E-09,1.2930E-09,1.0770E-09,8.0800E-10,6.4640E-10};
	final static double[] PrProdN= {0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,5.7950E-05,2.9970E-04,1.1590E-03,1.2480E-03,3.2250E-03,5.1700E-03,6.8900E-03,8.4240E-03,9.7880E-03,1.1020E-02,1.2130E-02,1.3130E-02,1.4040E-02,1.4880E-02,1.5660E-02,1.6380E-02,1.7060E-02,1.7690E-02,1.8840E-02,1.9870E-02,2.0810E-02,2.1660E-02,2.2440E-02,2.3150E-02,2.3810E-02,2.6500E-02,2.8530E-02,3.0100E-02,3.2440E-02,3.4130E-02,3.6850E-02,3.8500E-02,4.0450E-02,4.1600E-02,4.2350E-02,4.2890E-02,4.3610E-02,4.4080E-02,4.4770E-02,4.5140E-02,4.5550E-02,4.5760E-02,4.5900E-02,4.6000E-02,4.6120E-02,4.6200E-02,4.6310E-02,4.6360E-02,4.6440E-02,4.6460E-02,4.6480E-02,4.6490E-02,4.6520E-02,4.6530E-02};
	final static double[] PrProdE= {0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,1.0970E-05,4.4780E-05,8.9220E-05,1.3700E-04,1.8470E-04,2.3090E-04,2.7520E-04,3.1730E-04,3.5730E-04,3.9500E-04,4.3050E-04,4.6450E-04,4.9660E-04,5.2720E-04,5.8400E-04,6.3590E-04,6.8350E-04,7.2750E-04,7.6810E-04,8.0590E-04,8.4120E-04,9.8840E-04,1.1020E-03,1.1930E-03,1.3310E-03,1.4330E-03,1.6050E-03,1.7170E-03,1.8540E-03,1.9380E-03,1.9970E-03,2.0390E-03,2.1000E-03,2.1410E-03,2.2020E-03,2.2370E-03,2.2760E-03,2.2990E-03,2.3140E-03,2.3230E-03,2.3360E-03,2.3440E-03,2.3560E-03,2.3630E-03,2.3700E-03,2.3740E-03,2.3760E-03,2.3770E-03,2.3800E-03,2.3810E-03};
	final static double[] TotAttn= {6.4950E+03,2.3420E+03,1.1060E+03,3.7430E+02,1.7120E+02,9.2910E+01,7.2760E+01,5.8700E+02,4.6880E+02,2.2170E+02,1.2170E+02,3.9820E+01,1.7680E+01,5.5640E+00,2.4720E+00,1.3470E+00,8.4380E-01,4.3710E-01,2.8770E-01,1.6820E-01,1.3180E-01,1.0340E-01,8.9660E-02,8.0740E-02,7.4140E-02,6.4660E-02,5.7940E-02,5.7310E-02,5.1750E-02,4.7230E-02,4.1150E-02,4.0730E-02,3.4660E-02,3.1410E-02,2.9600E-02,2.8550E-02,2.7940E-02,2.7590E-02,2.7430E-02,2.7380E-02,2.7380E-02,2.7450E-02,2.7560E-02,2.7700E-02,2.7860E-02,2.8020E-02,2.8390E-02,2.8770E-02,2.9170E-02,2.9560E-02,2.9940E-02,3.0310E-02,3.0670E-02,3.2270E-02,3.3620E-02,3.4730E-02,3.6480E-02,3.7810E-02,4.0060E-02,4.1480E-02,4.3200E-02,4.4240E-02,4.4920E-02,4.5420E-02,4.6100E-02,4.6540E-02,4.7190E-02,4.7540E-02,4.7940E-02,4.8150E-02,4.8290E-02,4.8380E-02,4.8500E-02,4.8580E-02,4.8690E-02,4.8750E-02,4.8820E-02,4.8840E-02,4.8870E-02,4.8880E-02,4.8900E-02,4.8920E-02};

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
