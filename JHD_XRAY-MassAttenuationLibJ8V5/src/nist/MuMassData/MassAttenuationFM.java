package nist.MuMassData;
/**
 * @author John H Dunsmuir
 * @hidden
 */

import jhd.MathTools.Interpolation;


class MassAttenuationFM {

	final static double[] MeV= {1.00000E-03,1.00100E-03,1.00200E-03,1.00201E-03,1.03500E-03,1.06900E-03,1.06901E-03,1.20800E-03,1.36600E-03,
			1.36601E-03,1.50000E-03,1.74700E-03,1.74701E-03,1.84000E-03,1.93700E-03,1.93701E-03,2.00000E-03,3.00000E-03,4.00000E-03,
			4.49800E-03,4.49810E-03,4.63000E-03,4.76600E-03,4.76610E-03,5.00000E-03,5.39700E-03,5.39710E-03,6.00000E-03,6.79300E-03,
			6.79310E-03,6.99600E-03,7.20500E-03,7.20510E-03,8.00000E-03,1.00000E-02,1.50000E-02,2.00000E-02,2.09000E-02,2.09001E-02,
			2.36700E-02,2.68100E-02,2.68101E-02,2.72500E-02,2.77000E-02,2.77001E-02,3.00000E-02,4.00000E-02,5.00000E-02,6.00000E-02,
			8.00000E-02,1.00000E-01,1.43100E-01,1.43101E-01,1.50000E-01,2.00000E-01,3.00000E-01,4.00000E-01,5.00000E-01,6.00000E-01,
			8.00000E-01,1.00000E+00,1.02200E+00,1.25000E+00,1.50000E+00,2.00000E+00,2.04400E+00,3.00000E+00,4.00000E+00,5.00000E+00,
			6.00000E+00,7.00000E+00,8.00000E+00,9.00000E+00,1.00000E+01,1.10000E+01,1.20000E+01,1.30000E+01,1.40000E+01,1.50000E+01,
			1.60000E+01,1.80000E+01,2.00000E+01,2.20000E+01,2.40000E+01,2.60000E+01,2.80000E+01,3.00000E+01,4.00000E+01,5.00000E+01,
			6.00000E+01,8.00000E+01,1.00000E+02,1.50000E+02,2.00000E+02,3.00000E+02,4.00000E+02,5.00000E+02,6.00000E+02,8.00000E+02,
			1.00000E+03,1.50000E+03,2.00000E+03,3.00000E+03,4.00000E+03,5.00000E+03,6.00000E+03,8.00000E+03,1.00000E+04,1.50000E+04,
			2.00000E+04,3.00000E+04,4.00000E+04,5.00000E+04,6.00000E+04,8.00000E+04,1.00000E+05};
	final static double[] Coh= {1.4970E+01,1.4970E+01,1.4970E+01,1.4970E+01,1.4930E+01,1.4900E+01,1.4900E+01,1.4740E+01,1.4550E+01,1.4550E+01,1.4380E+01,1.4070E+01,1.4070E+01,1.3940E+01,1.3810E+01,1.3810E+01,1.3730E+01,1.2390E+01,1.1110E+01,1.0510E+01,1.0510E+01,1.0360E+01,1.0210E+01,1.0210E+01,9.9530E+00,9.5330E+00,9.5330E+00,8.9430E+00,8.2430E+00,8.2430E+00,8.0760E+00,7.9100E+00,7.9100E+00,7.3270E+00,6.1250E+00,4.1880E+00,3.0520E+00,2.8980E+00,2.8980E+00,2.4780E+00,2.1030E+00,2.1030E+00,2.0580E+00,2.0140E+00,2.0140E+00,1.8090E+00,1.2110E+00,8.8120E-01,6.6730E-01,4.1980E-01,2.9140E-01,1.5970E-01,1.5970E-01,1.4720E-01,8.8450E-02,4.2610E-02,2.5200E-02,1.6650E-02,1.1820E-02,6.8350E-03,4.4510E-03,4.2680E-03,2.8880E-03,2.0240E-03,1.1510E-03,1.1030E-03,5.1670E-04,2.9190E-04,1.8720E-04,1.3020E-04,9.5710E-05,7.3320E-05,5.7950E-05,4.6940E-05,3.8810E-05,3.2610E-05,2.7800E-05,2.3960E-05,2.0880E-05,1.8350E-05,1.4500E-05,1.1750E-05,9.7090E-06,8.1580E-06,6.9520E-06,5.9940E-06,5.2240E-06,2.9370E-06,1.8800E-06,1.3060E-06,7.3460E-07,4.7010E-07,2.0890E-07,1.1750E-07,5.2240E-08,2.9370E-08,1.8800E-08,1.3060E-08,7.3460E-09,4.7010E-09,2.0890E-09,1.1750E-09,5.2240E-10,2.9370E-10,1.8800E-10,1.3060E-10,7.3460E-11,4.7010E-11,2.0890E-11,1.1750E-11,5.2240E-12,2.9370E-12,1.8800E-12,1.3060E-12,7.3460E-13,4.7010E-13};
	final static double[] Incoh= {3.9120E-03,3.9180E-03,3.9230E-03,3.9230E-03,4.1050E-03,4.2800E-03,4.2800E-03,4.9980E-03,5.8300E-03,5.8300E-03,6.5450E-03,7.8660E-03,7.8660E-03,8.3610E-03,8.8820E-03,8.8820E-03,9.2200E-03,1.4470E-02,1.9390E-02,2.1690E-02,2.1690E-02,2.2290E-02,2.2890E-02,2.2890E-02,2.3920E-02,2.5600E-02,2.5600E-02,2.8040E-02,3.1110E-02,3.1110E-02,3.1840E-02,3.2580E-02,3.2580E-02,3.5420E-02,4.1910E-02,5.5210E-02,6.4770E-02,6.6200E-02,6.6200E-02,7.0110E-02,7.3860E-02,7.3860E-02,7.4340E-02,7.4820E-02,7.4820E-02,7.7110E-02,8.4540E-02,8.9170E-02,9.2030E-02,9.4440E-02,9.4560E-02,9.1940E-02,9.1940E-02,9.1350E-02,8.6810E-02,7.8190E-02,7.1300E-02,6.5770E-02,6.1250E-02,5.4230E-02,4.8960E-02,4.8460E-02,4.3920E-02,3.9980E-02,3.4200E-02,3.3780E-02,2.6960E-02,2.2500E-02,1.9440E-02,1.7180E-02,1.5450E-02,1.4060E-02,1.2930E-02,1.1980E-02,1.1170E-02,1.0480E-02,9.8710E-03,9.3390E-03,8.8660E-03,8.4440E-03,7.7180E-03,7.1160E-03,6.6100E-03,6.1770E-03,5.8000E-03,5.4720E-03,5.1810E-03,4.1180E-03,3.4360E-03,2.9610E-03,2.3360E-03,1.9390E-03,1.3810E-03,1.0820E-03,7.6710E-04,6.0080E-04,4.9730E-04,4.2580E-04,3.3260E-04,2.7360E-04,1.9110E-04,1.4790E-04,1.0280E-04,7.9380E-05,6.4910E-05,5.5050E-05,4.2400E-05,3.4620E-05,2.3920E-05,1.8390E-05,1.2690E-05,9.7400E-06,7.9310E-06,6.7040E-06,5.1420E-06,4.1810E-06};
	final static double[] PE= {7.1180E+03,7.1070E+03,7.0950E+03,7.4020E+03,7.2480E+03,7.0970E+03,7.3060E+03,5.9560E+03,4.8560E+03,5.1670E+03,4.3050E+03,3.1550E+03,3.1930E+03,2.8640E+03,2.5700E+03,2.6160E+03,2.4430E+03,1.0020E+03,5.1740E+02,3.9280E+02,8.5520E+02,8.1270E+02,7.7250E+02,1.0620E+03,9.6690E+02,7.9950E+02,9.3370E+02,7.1610E+02,5.2000E+02,5.5120E+02,5.1190E+02,4.7550E+02,4.9520E+02,3.8200E+02,2.1940E+02,7.8870E+01,3.7740E+01,3.3680E+01,7.8000E+01,5.5790E+01,3.9890E+01,5.7600E+01,5.5190E+01,5.2890E+01,6.0930E+01,4.9850E+01,2.4080E+01,1.3530E+01,8.4040E+00,3.9400E+00,2.1810E+00,8.4260E-01,3.2490E+00,2.9050E+00,1.4170E+00,5.1910E-01,2.6090E-01,1.5620E-01,1.0420E-01,5.6570E-02,3.6000E-02,3.4480E-02,2.3350E-02,1.6600E-02,1.0020E-02,9.6550E-03,5.2050E-03,3.3890E-03,2.4740E-03,1.9320E-03,1.5770E-03,1.3290E-03,1.1470E-03,1.0070E-03,8.9690E-04,8.0810E-04,7.3500E-04,6.7370E-04,6.2190E-04,5.7720E-04,5.0450E-04,4.4790E-04,4.0270E-04,3.6560E-04,3.3470E-04,3.0870E-04,2.8650E-04,2.1020E-04,1.6600E-04,1.3710E-04,1.0170E-04,8.0810E-05,5.3380E-05,3.9870E-05,2.6450E-05,1.9800E-05,1.5810E-05,1.3170E-05,9.8640E-06,7.8840E-06,5.2520E-06,3.9380E-06,2.6230E-06,1.9670E-06,1.5740E-06,1.3110E-06,9.8330E-07,7.8660E-07,5.2420E-07,3.9330E-07,2.6210E-07,1.9660E-07,1.5730E-07,1.3110E-07,9.8290E-08,7.8630E-08};
	final static double[] PrProdN= {0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,5.0430E-04,2.5740E-03,7.3950E-03,7.7880E-03,1.4850E-02,2.0400E-02,2.5020E-02,2.8830E-02,3.2370E-02,3.5580E-02,3.8530E-02,4.1250E-02,4.3800E-02,4.6170E-02,4.8370E-02,5.0450E-02,5.2400E-02,5.4230E-02,5.7550E-02,6.0500E-02,6.3220E-02,6.5680E-02,6.7950E-02,7.0040E-02,7.1930E-02,7.9730E-02,8.5540E-02,9.0060E-02,9.6690E-02,1.0140E-01,1.0870E-01,1.1300E-01,1.1810E-01,1.2100E-01,1.2290E-01,1.2430E-01,1.2610E-01,1.2730E-01,1.2890E-01,1.2990E-01,1.3080E-01,1.3140E-01,1.3180E-01,1.3200E-01,1.3230E-01,1.3250E-01,1.3270E-01,1.3290E-01,1.3300E-01,1.3310E-01,1.3320E-01,1.3320E-01,1.3320E-01,1.3330E-01};
	final static double[] PrProdE= {0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,9.4120E-06,3.8320E-05,7.6200E-05,1.1670E-04,1.5700E-04,1.9590E-04,2.3290E-04,2.6800E-04,3.0120E-04,3.3240E-04,3.6170E-04,3.8930E-04,4.1550E-04,4.4040E-04,4.8630E-04,5.2800E-04,5.6570E-04,6.0040E-04,6.3220E-04,6.6170E-04,6.8910E-04,8.0110E-04,8.8520E-04,9.5100E-04,1.0490E-03,1.1200E-03,1.2350E-03,1.3060E-03,1.3910E-03,1.4420E-03,1.4770E-03,1.5020E-03,1.5360E-03,1.5590E-03,1.5930E-03,1.6120E-03,1.6330E-03,1.6460E-03,1.6530E-03,1.6590E-03,1.6660E-03,1.6700E-03,1.6760E-03,1.6800E-03,1.6830E-03,1.6850E-03,1.6870E-03,1.6870E-03,1.6880E-03,1.6890E-03};
	final static double[] TotAttn= {7.1330E+03,7.1220E+03,7.1100E+03,7.4170E+03,7.2630E+03,7.1120E+03,7.3210E+03,5.9700E+03,4.8700E+03,5.1820E+03,4.3200E+03,3.1690E+03,3.2070E+03,2.8780E+03,2.5830E+03,2.6300E+03,2.4570E+03,1.0150E+03,5.2860E+02,4.0340E+02,8.6570E+02,8.2310E+02,7.8270E+02,1.0720E+03,9.7690E+02,8.0900E+02,9.4320E+02,7.2500E+02,5.2830E+02,5.5940E+02,5.2000E+02,4.8340E+02,5.0310E+02,3.8940E+02,2.2560E+02,8.3110E+01,4.0850E+01,3.6650E+01,8.0960E+01,5.8340E+01,4.2070E+01,5.9780E+01,5.7330E+01,5.4980E+01,6.3010E+01,5.1730E+01,2.5370E+01,1.4500E+01,9.1640E+00,4.4540E+00,2.5670E+00,1.0940E+00,3.5010E+00,3.1430E+00,1.5920E+00,6.3990E-01,3.5740E-01,2.3860E-01,1.7730E-01,1.1760E-01,8.9410E-02,8.7210E-02,7.0670E-02,6.1180E-02,5.2760E-02,5.2320E-02,4.7540E-02,4.6610E-02,4.7190E-02,4.8200E-02,4.9650E-02,5.1240E-02,5.2900E-02,5.4550E-02,5.6210E-02,5.7820E-02,5.9370E-02,6.0880E-02,6.2320E-02,6.3710E-02,6.6280E-02,6.8610E-02,7.0810E-02,7.2830E-02,7.4730E-02,7.6490E-02,7.8100E-02,8.4870E-02,9.0030E-02,9.4110E-02,1.0020E-01,1.0450E-01,1.1140E-01,1.1550E-01,1.2030E-01,1.2310E-01,1.2490E-01,1.2620E-01,1.2800E-01,1.2910E-01,1.3070E-01,1.3160E-01,1.3250E-01,1.3310E-01,1.3350E-01,1.3370E-01,1.3400E-01,1.3420E-01,1.3440E-01,1.3460E-01,1.3470E-01,1.3480E-01,1.3490E-01,1.3490E-01,1.3490E-01,1.3500E-01};

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
			//for(int i = 0; i< muArr.length - 1;i++)
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
