package nist.MuMassData;
/**
 * @author John H Dunsmuir
 * @hidden
 */

import jhd.MathTools.Interpolation;


class MassAttenuationAU {

	final static double[] MeV= {1.00000E-03,1.50000E-03,2.00000E-03,2.20600E-03,2.20601E-03,2.24800E-03,2.29100E-03,2.29101E-03,2.50700E-03,
			2.74300E-03,2.74301E-03,3.00000E-03,3.14800E-03,3.14801E-03,3.28300E-03,3.42500E-03,3.42510E-03,4.00000E-03,5.00000E-03,
			6.00000E-03,8.00000E-03,1.00000E-02,1.19200E-02,1.19201E-02,1.27900E-02,1.37300E-02,1.37301E-02,1.40400E-02,1.43500E-02,
			1.43501E-02,1.50000E-02,2.00000E-02,3.00000E-02,4.00000E-02,5.00000E-02,6.00000E-02,8.00000E-02,8.07200E-02,8.07210E-02,
			1.00000E-01,1.50000E-01,2.00000E-01,3.00000E-01,4.00000E-01,5.00000E-01,6.00000E-01,8.00000E-01,1.00000E+00,1.02200E+00,
			1.25000E+00,1.50000E+00,2.00000E+00,2.04400E+00,3.00000E+00,4.00000E+00,5.00000E+00,6.00000E+00,7.00000E+00,8.00000E+00,
			9.00000E+00,1.00000E+01,1.10000E+01,1.20000E+01,1.30000E+01,1.40000E+01,1.50000E+01,1.60000E+01,1.80000E+01,2.00000E+01,
			2.20000E+01,2.40000E+01,2.60000E+01,2.80000E+01,3.00000E+01,4.00000E+01,5.00000E+01,6.00000E+01,8.00000E+01,1.00000E+02,
			1.50000E+02,2.00000E+02,3.00000E+02,4.00000E+02,5.00000E+02,6.00000E+02,8.00000E+02,1.00000E+03,1.50000E+03,2.00000E+03,
			3.00000E+03,4.00000E+03,5.00000E+03,6.00000E+03,8.00000E+03,1.00000E+04,1.50000E+04,2.00000E+04,3.00000E+04,4.00000E+04,
			5.00000E+04,6.00000E+04,8.00000E+04,1.00000E+05};
	final static double[] Coh= {1.2270E+01,1.1810E+01,1.1270E+01,1.1030E+01,1.1030E+01,1.0970E+01,1.0920E+01,1.0920E+01,1.0680E+01,1.0410E+01,1.0410E+01,1.0110E+01,9.9400E+00,9.9400E+00,9.7870E+00,9.6310E+00,9.6310E+00,9.0070E+00,8.0320E+00,7.1880E+00,5.8460E+00,4.8370E+00,4.0820E+00,4.0820E+00,3.7910E+00,3.5100E+00,3.5100E+00,3.4250E+00,3.3420E+00,3.3420E+00,3.1800E+00,2.2420E+00,1.3230E+00,8.7990E-01,6.2400E-01,4.6720E-01,2.9350E-01,2.8920E-01,2.8920E-01,2.0240E-01,9.9460E-02,5.9310E-02,2.8250E-02,1.6480E-02,1.0780E-02,7.5920E-03,4.3480E-03,2.8110E-03,2.6940E-03,1.8140E-03,1.2650E-03,7.1570E-04,6.8550E-04,3.1950E-04,1.8000E-04,1.1530E-04,8.0100E-05,5.8860E-05,4.5070E-05,3.5620E-05,2.8850E-05,2.3850E-05,2.0040E-05,1.7080E-05,1.4720E-05,1.2830E-05,1.1270E-05,8.9090E-06,7.2160E-06,5.9620E-06,5.0110E-06,4.2680E-06,3.6810E-06,3.2070E-06,1.8040E-06,1.1540E-06,8.0170E-07,4.5100E-07,2.8870E-07,1.2830E-07,7.2160E-08,3.2070E-08,1.8040E-08,1.1540E-08,8.0170E-09,4.5100E-09,2.8870E-09,1.2830E-09,7.2160E-10,3.2070E-10,1.8040E-10,1.1540E-10,8.0170E-11,4.5100E-11,2.8870E-11,1.2830E-11,7.2160E-12,3.2070E-12,1.8040E-12,1.1540E-12,8.0170E-13,4.5100E-13,2.8870E-13};
	final static double[] Incoh= {3.2680E-03,6.0660E-03,8.9550E-03,1.0140E-02,1.0140E-02,1.0380E-02,1.0630E-02,1.0630E-02,1.1850E-02,1.3170E-02,1.3170E-02,1.4590E-02,1.5400E-02,1.5400E-02,1.6130E-02,1.6880E-02,1.6880E-02,1.9910E-02,2.4990E-02,2.9870E-02,3.8770E-02,4.6290E-02,5.2400E-02,5.2400E-02,5.4810E-02,5.7240E-02,5.7240E-02,5.8010E-02,5.8790E-02,5.8790E-02,6.0350E-02,7.0540E-02,8.4260E-02,9.2270E-02,9.6830E-02,9.9370E-02,1.0110E-01,1.0120E-01,1.0120E-01,1.0080E-01,9.6460E-02,9.1110E-02,8.1570E-02,7.4170E-02,6.8300E-02,6.3530E-02,5.6130E-02,5.0630E-02,5.0110E-02,4.5370E-02,4.1310E-02,3.5310E-02,3.4850E-02,2.7810E-02,2.3210E-02,2.0050E-02,1.7720E-02,1.5930E-02,1.4500E-02,1.3330E-02,1.2350E-02,1.1520E-02,1.0800E-02,1.0180E-02,9.6310E-03,9.1450E-03,8.7080E-03,7.9590E-03,7.3380E-03,6.8180E-03,6.3690E-03,5.9800E-03,5.6440E-03,5.3440E-03,4.2470E-03,3.5440E-03,3.0540E-03,2.4080E-03,1.9990E-03,1.4240E-03,1.1160E-03,7.9130E-04,6.1970E-04,5.1300E-04,4.3900E-04,3.4300E-04,2.8220E-04,1.9710E-04,1.5250E-04,1.0600E-04,8.1850E-05,6.6930E-05,5.6750E-05,4.3720E-05,3.5710E-05,2.4670E-05,1.8970E-05,1.3080E-05,1.0040E-05,8.1790E-06,6.9130E-06,5.3020E-06,4.3140E-06};
	final static double[] PE= {4.6410E+03,2.0770E+03,1.1250E+03,9.0780E+02,9.8270E+02,1.4750E+03,2.2140E+03,2.3470E+03,2.2670E+03,2.1910E+03,2.5290E+03,2.0390E+03,1.8120E+03,1.9230E+03,1.7400E+03,1.5750E+03,1.6420E+03,1.1350E+03,6.5800E+02,4.1800E+02,2.0130E+02,1.1320E+02,7.1700E+01,1.8290E+02,1.5100E+02,1.2470E+02,1.7290E+02,1.6390E+02,1.5540E+02,1.7960E+02,1.6050E+02,7.6500E+01,2.6110E+01,1.2010E+01,6.5370E+00,3.9620E+00,1.7900E+00,1.7460E+00,8.5120E+00,4.8550E+00,1.6640E+00,7.7110E-01,2.6460E-01,1.2730E-01,7.3900E-02,4.8280E-02,2.5550E-02,1.6090E-02,1.5390E-02,1.0380E-02,7.3990E-03,4.4760E-03,4.3170E-03,2.3430E-03,1.5350E-03,1.1260E-03,8.8300E-04,7.2340E-04,6.1150E-04,5.2860E-04,4.6500E-04,4.1490E-04,3.7420E-04,3.4090E-04,3.1280E-04,2.8900E-04,2.6840E-04,2.3500E-04,2.0890E-04,1.8800E-04,1.7080E-04,1.5650E-04,1.4440E-04,1.3410E-04,9.8630E-05,7.7960E-05,6.4450E-05,4.7850E-05,3.8070E-05,2.5170E-05,1.8800E-05,1.2480E-05,9.3400E-06,7.4630E-06,6.2130E-06,4.6560E-06,3.7210E-06,2.4790E-06,1.8590E-06,1.2390E-06,9.2890E-07,7.4300E-07,6.1910E-07,4.6410E-07,3.7150E-07,2.4760E-07,1.8570E-07,1.2380E-07,9.2820E-08,7.4270E-08,6.1880E-08,4.6410E-08,3.7120E-08};
	final static double[] PrProdN= {0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,3.5960E-04,1.7030E-03,5.1920E-03,5.5000E-03,1.1530E-02,1.6700E-02,2.1020E-02,2.4750E-02,2.8030E-02,3.0970E-02,3.3660E-02,3.6140E-02,3.8400E-02,4.0540E-02,4.2530E-02,4.4390E-02,4.6110E-02,4.7730E-02,5.0660E-02,5.3260E-02,5.5610E-02,5.7760E-02,5.9740E-02,6.1520E-02,6.3200E-02,6.9980E-02,7.5000E-02,7.8910E-02,8.4630E-02,8.8700E-02,9.5060E-02,9.8850E-02,1.0320E-01,1.0580E-01,1.0740E-01,1.0860E-01,1.1010E-01,1.1110E-01,1.1260E-01,1.1340E-01,1.1420E-01,1.1470E-01,1.1500E-01,1.1520E-01,1.1540E-01,1.1560E-01,1.1580E-01,1.1600E-01,1.1610E-01,1.1620E-01,1.1620E-01,1.1620E-01,1.1630E-01,1.1630E-01};
	final static double[] PrProdE= {0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,9.7170E-06,3.9590E-05,7.8760E-05,1.2070E-04,1.6250E-04,2.0280E-04,2.4140E-04,2.7790E-04,3.1250E-04,3.4490E-04,3.7550E-04,4.0450E-04,4.3170E-04,4.5770E-04,5.0600E-04,5.4940E-04,5.8920E-04,6.2560E-04,6.5920E-04,6.9040E-04,7.1910E-04,8.3740E-04,9.2640E-04,9.9640E-04,1.1010E-03,1.1760E-03,1.2990E-03,1.3750E-03,1.4660E-03,1.5210E-03,1.5570E-03,1.5840E-03,1.6210E-03,1.6450E-03,1.6810E-03,1.7010E-03,1.7230E-03,1.7350E-03,1.7430E-03,1.7490E-03,1.7560E-03,1.7600E-03,1.7670E-03,1.7700E-03,1.7740E-03,1.7760E-03,1.7770E-03,1.7780E-03,1.7790E-03,1.7790E-03};
	final static double[] TotAttn= {4.6530E+03,2.0890E+03,1.1370E+03,9.1880E+02,9.9370E+02,1.4860E+03,2.2250E+03,2.3580E+03,2.2780E+03,2.2020E+03,2.5390E+03,2.0490E+03,1.8220E+03,1.9330E+03,1.7500E+03,1.5850E+03,1.6520E+03,1.1440E+03,6.6600E+02,4.2520E+02,2.0720E+02,1.1810E+02,7.5830E+01,1.8700E+02,1.5490E+02,1.2830E+02,1.7640E+02,1.6740E+02,1.5880E+02,1.8300E+02,1.6370E+02,7.8810E+01,2.7520E+01,1.2980E+01,7.2580E+00,4.5290E+00,2.1850E+00,2.1360E+00,8.9020E+00,5.1580E+00,1.8590E+00,9.2150E-01,3.7440E-01,2.1800E-01,1.5300E-01,1.1940E-01,8.6030E-02,6.9530E-02,6.8200E-02,5.7930E-02,5.1670E-02,4.5700E-02,4.5360E-02,4.2020E-02,4.1660E-02,4.2390E-02,4.3550E-02,4.4900E-02,4.6330E-02,4.7800E-02,4.9260E-02,5.0670E-02,5.2090E-02,5.3440E-02,5.4760E-02,5.5980E-02,5.7170E-02,5.9370E-02,6.1360E-02,6.3220E-02,6.4930E-02,6.6540E-02,6.8000E-02,6.9400E-02,7.5170E-02,7.9550E-02,8.3030E-02,8.8190E-02,9.1910E-02,9.7800E-02,1.0140E-01,1.0550E-01,1.0790E-01,1.0950E-01,1.1060E-01,1.1210E-01,1.1310E-01,1.1450E-01,1.1520E-01,1.1610E-01,1.1650E-01,1.1680E-01,1.1700E-01,1.1720E-01,1.1740E-01,1.1760E-01,1.1780E-01,1.1790E-01,1.1800E-01,1.1800E-01,1.1800E-01,1.1810E-01,1.1810E-01};

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
