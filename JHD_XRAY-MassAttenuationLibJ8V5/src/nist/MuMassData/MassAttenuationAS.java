package nist.MuMassData;
/**
 * @author John H Dunsmuir
 * @hidden
 */

import jhd.MathTools.Interpolation;


class MassAttenuationAS {

	final static double[] MeV= {1.00000E-03,1.15000E-03,1.32300E-03,1.32301E-03,1.34100E-03,1.35900E-03,1.35901E-03,1.50000E-03,1.52600E-03,
			1.52601E-03,2.00000E-03,3.00000E-03,4.00000E-03,5.00000E-03,6.00000E-03,8.00000E-03,1.00000E-02,1.18700E-02,1.18701E-02,
			1.50000E-02,2.00000E-02,3.00000E-02,4.00000E-02,5.00000E-02,6.00000E-02,8.00000E-02,1.00000E-01,1.50000E-01,2.00000E-01,
			3.00000E-01,4.00000E-01,5.00000E-01,6.00000E-01,8.00000E-01,1.00000E+00,1.02200E+00,1.25000E+00,1.50000E+00,2.00000E+00,
			2.04400E+00,3.00000E+00,4.00000E+00,5.00000E+00,6.00000E+00,7.00000E+00,8.00000E+00,9.00000E+00,1.00000E+01,1.10000E+01,
			1.20000E+01,1.30000E+01,1.40000E+01,1.50000E+01,1.60000E+01,1.80000E+01,2.00000E+01,2.20000E+01,2.40000E+01,2.60000E+01,
			2.80000E+01,3.00000E+01,4.00000E+01,5.00000E+01,6.00000E+01,8.00000E+01,1.00000E+02,1.50000E+02,2.00000E+02,3.00000E+02,
			4.00000E+02,5.00000E+02,6.00000E+02,8.00000E+02,1.00000E+03,1.50000E+03,2.00000E+03,3.00000E+03,4.00000E+03,5.00000E+03,
			6.00000E+03,8.00000E+03,1.00000E+04,1.50000E+04,2.00000E+04,3.00000E+04,4.00000E+04,5.00000E+04,6.00000E+04,8.00000E+04,
			1.00000E+05};
	final static double[] Coh= {5.5090E+00,5.4200E+00,5.3150E+00,5.3150E+00,5.3030E+00,5.2910E+00,5.2910E+00,5.1950E+00,5.1770E+00,5.1770E+00,4.8530E+00,4.2090E+00,3.6650E+00,3.2060E+00,2.8120E+00,2.1760E+00,1.7100E+00,1.3970E+00,1.3970E+00,1.0340E+00,7.0690E-01,3.9840E-01,2.5320E-01,1.7480E-01,1.2840E-01,7.8410E-02,5.3000E-02,2.5330E-02,1.4740E-02,6.7780E-03,3.8740E-03,2.5010E-03,1.7460E-03,9.8710E-04,6.3360E-04,6.0680E-04,4.0620E-04,2.8240E-04,1.5900E-04,1.5220E-04,7.0710E-05,3.9790E-05,2.5460E-05,1.7680E-05,1.3000E-05,9.9510E-06,7.8610E-06,6.3680E-06,5.2620E-06,4.4220E-06,3.7670E-06,3.2490E-06,2.8300E-06,2.4870E-06,1.9650E-06,1.5920E-06,1.3160E-06,1.1050E-06,9.4200E-07,8.1180E-07,7.0750E-07,3.9800E-07,2.5470E-07,1.7680E-07,9.9510E-08,6.3670E-08,2.8300E-08,1.5920E-08,7.0750E-09,3.9800E-09,2.5470E-09,1.7680E-09,9.9510E-10,6.3670E-10,2.8300E-10,1.5920E-10,7.0750E-11,3.9800E-11,2.5470E-11,1.7680E-11,9.9510E-12,6.3670E-12,2.8300E-12,1.5920E-12,7.0750E-13,3.9800E-13,2.5470E-13,1.7680E-13,9.9510E-14,6.3670E-14};
	final static double[] Incoh= {5.8110E-03,7.2850E-03,9.0670E-03,9.0670E-03,9.2500E-03,9.4370E-03,9.4370E-03,1.0900E-02,1.1170E-02,1.1170E-02,1.6040E-02,2.5610E-02,3.4040E-02,4.1520E-02,4.8290E-02,6.0230E-02,7.0360E-02,7.8370E-02,7.8370E-02,8.9380E-02,1.0170E-01,1.1530E-01,1.2150E-01,1.2420E-01,1.2490E-01,1.2350E-01,1.2060E-01,1.1230E-01,1.0460E-01,9.2190E-02,8.3110E-02,7.6140E-02,7.0580E-02,6.2130E-02,5.5920E-02,5.5330E-02,5.0040E-02,4.5500E-02,3.8850E-02,3.8370E-02,3.0580E-02,2.5500E-02,2.2030E-02,1.9480E-02,1.7510E-02,1.5930E-02,1.4650E-02,1.3570E-02,1.2650E-02,1.1870E-02,1.1180E-02,1.0580E-02,1.0040E-02,9.5650E-03,8.7370E-03,8.0620E-03,7.4870E-03,6.9960E-03,6.5690E-03,6.1970E-03,5.8680E-03,4.6630E-03,3.8930E-03,3.3530E-03,2.6450E-03,2.1950E-03,1.5630E-03,1.2260E-03,8.6890E-04,6.8040E-04,5.6330E-04,4.8230E-04,3.7670E-04,3.0990E-04,2.1640E-04,1.6750E-04,1.1650E-04,8.9860E-05,7.3500E-05,6.2330E-05,4.8020E-05,3.9210E-05,2.7100E-05,2.0830E-05,1.4360E-05,1.1030E-05,8.9780E-06,7.5920E-06,5.8210E-06,4.7360E-06};
	final static double[] PE= {2.1160E+03,1.5170E+03,1.0870E+03,4.5270E+03,4.4840E+03,4.4410E+03,6.0770E+03,5.2210E+03,4.9920E+03,5.6480E+03,2.9270E+03,1.0450E+03,4.8840E+02,2.6770E+02,1.6280E+02,7.3490E+01,3.9370E+01,2.4300E+01,1.7780E+02,9.7420E+01,4.4840E+01,1.4540E+01,6.3860E+00,3.3360E+00,1.9500E+00,8.2790E-01,4.2340E-01,1.2470E-01,5.2540E-02,1.6000E-02,7.1510E-03,3.9540E-03,2.5010E-03,1.2790E-03,7.9590E-04,7.5640E-04,5.1110E-04,3.6770E-04,2.2570E-04,2.1800E-04,1.2160E-04,8.1500E-05,6.0770E-05,4.8270E-05,3.9950E-05,3.4030E-05,2.9620E-05,2.6200E-05,2.3490E-05,2.1280E-05,1.9440E-05,1.7900E-05,1.6580E-05,1.5440E-05,1.3580E-05,1.2110E-05,1.0920E-05,9.9510E-06,9.1390E-06,8.4480E-06,7.8520E-06,5.8080E-06,4.6070E-06,3.8180E-06,2.8430E-06,2.2650E-06,1.5010E-06,1.1230E-06,7.4630E-07,5.5890E-07,4.4670E-07,3.7210E-07,2.7880E-07,2.2300E-07,1.4850E-07,1.1140E-07,7.4240E-08,5.5670E-08,4.4540E-08,3.7110E-08,2.7830E-08,2.2270E-08,1.4840E-08,1.1130E-08,7.4210E-09,5.5650E-09,4.4520E-09,3.7100E-09,2.7830E-09,2.2260E-09};
	final static double[] PrProdN= {0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,9.3080E-05,4.5920E-04,1.6940E-03,1.8180E-03,4.6070E-03,7.2940E-03,9.6620E-03,1.1740E-02,1.3590E-02,1.5260E-02,1.6780E-02,1.8160E-02,1.9410E-02,2.0560E-02,2.1610E-02,2.2590E-02,2.3510E-02,2.4370E-02,2.5940E-02,2.7340E-02,2.8610E-02,2.9770E-02,3.0830E-02,3.1800E-02,3.2700E-02,3.6350E-02,3.9070E-02,4.1190E-02,4.4350E-02,4.6580E-02,5.0140E-02,5.2260E-02,5.4740E-02,5.6160E-02,5.7090E-02,5.7760E-02,5.8650E-02,5.9230E-02,6.0070E-02,6.0530E-02,6.1020E-02,6.1280E-02,6.1440E-02,6.1560E-02,6.1720E-02,6.1800E-02,6.1940E-02,6.2010E-02,6.2080E-02,6.2130E-02,6.2150E-02,6.2170E-02,6.2190E-02,6.2200E-02};
	final static double[] PrProdE= {0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,1.0700E-05,4.3670E-05,8.6970E-05,1.3350E-04,1.7990E-04,2.2480E-04,2.6790E-04,3.0880E-04,3.4760E-04,3.8410E-04,4.1860E-04,4.5150E-04,4.8260E-04,5.1230E-04,5.6720E-04,6.1720E-04,6.6310E-04,7.0530E-04,7.4440E-04,7.8070E-04,8.1420E-04,9.5490E-04,1.0620E-03,1.1470E-03,1.2760E-03,1.3700E-03,1.5270E-03,1.6250E-03,1.7470E-03,1.8200E-03,1.8700E-03,1.9070E-03,1.9570E-03,1.9910E-03,2.0410E-03,2.0700E-03,2.1000E-03,2.1190E-03,2.1300E-03,2.1370E-03,2.1480E-03,2.1550E-03,2.1640E-03,2.1690E-03,2.1730E-03,2.1770E-03,2.1790E-03,2.1800E-03,2.1810E-03,2.1820E-03};
	final static double[] TotAttn= {2.1210E+03,1.5220E+03,1.0920E+03,4.5320E+03,4.4890E+03,4.4460E+03,6.0830E+03,5.2260E+03,4.9980E+03,5.6530E+03,2.9310E+03,1.0490E+03,4.9210E+02,2.7090E+02,1.6560E+02,7.5730E+01,4.1150E+01,2.5770E+01,1.7930E+02,9.8540E+01,4.5640E+01,1.5050E+01,6.7610E+00,3.6350E+00,2.2030E+00,1.0300E+00,5.9710E-01,2.6230E-01,1.7190E-01,1.1500E-01,9.4140E-02,8.2590E-02,7.4830E-02,6.4400E-02,5.7350E-02,5.6700E-02,5.1050E-02,4.6610E-02,4.0930E-02,4.0560E-02,3.5390E-02,3.2960E-02,3.1870E-02,3.1410E-02,3.1330E-02,3.1460E-02,3.1730E-02,3.2070E-02,3.2440E-02,3.2840E-02,3.3240E-02,3.3650E-02,3.4050E-02,3.4470E-02,3.5260E-02,3.6030E-02,3.6780E-02,3.7490E-02,3.8150E-02,3.8790E-02,3.9390E-02,4.1970E-02,4.4030E-02,4.5700E-02,4.8270E-02,5.0150E-02,5.3230E-02,5.5110E-02,5.7350E-02,5.8660E-02,5.9530E-02,6.0150E-02,6.0990E-02,6.1530E-02,6.2320E-02,6.2760E-02,6.3230E-02,6.3490E-02,6.3650E-02,6.3760E-02,6.3910E-02,6.4000E-02,6.4130E-02,6.4200E-02,6.4270E-02,6.4310E-02,6.4340E-02,6.4350E-02,6.4380E-02,6.4380E-02};

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
