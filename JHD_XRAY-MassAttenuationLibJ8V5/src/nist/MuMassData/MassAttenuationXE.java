package nist.MuMassData;
/**
 * @author John H Dunsmuir
 * @hidden
 */

import jhd.MathTools.Interpolation;


class MassAttenuationXE {

	final static double[] MeV= {1.00000E-03,1.07200E-03,1.14900E-03,1.14901E-03,1.50000E-03,2.00000E-03,3.00000E-03,4.00000E-03,4.78200E-03,
			4.78210E-03,5.00000E-03,5.10400E-03,5.10410E-03,5.27500E-03,5.45300E-03,5.45310E-03,6.00000E-03,8.00000E-03,1.00000E-02,
			1.50000E-02,2.00000E-02,3.00000E-02,3.45600E-02,3.45610E-02,4.00000E-02,5.00000E-02,6.00000E-02,8.00000E-02,1.00000E-01,
			1.50000E-01,2.00000E-01,3.00000E-01,4.00000E-01,5.00000E-01,6.00000E-01,8.00000E-01,1.00000E+00,1.02200E+00,1.25000E+00,
			1.50000E+00,2.00000E+00,2.04400E+00,3.00000E+00,4.00000E+00,5.00000E+00,6.00000E+00,7.00000E+00,8.00000E+00,9.00000E+00,
			1.00000E+01,1.10000E+01,1.20000E+01,1.30000E+01,1.40000E+01,1.50000E+01,1.60000E+01,1.80000E+01,2.00000E+01,2.20000E+01,
			2.40000E+01,2.60000E+01,2.80000E+01,3.00000E+01,4.00000E+01,5.00000E+01,6.00000E+01,8.00000E+01,1.00000E+02,1.50000E+02,
			2.00000E+02,3.00000E+02,4.00000E+02,5.00000E+02,6.00000E+02,8.00000E+02,1.00000E+03,1.50000E+03,2.00000E+03,3.00000E+03,
			4.00000E+03,5.00000E+03,6.00000E+03,8.00000E+03,1.00000E+04,1.50000E+04,2.00000E+04,3.00000E+04,4.00000E+04,5.00000E+04,
			6.00000E+04,8.00000E+04,1.00000E+05};

	final static double[] TotAttn = {9.4120E+03,8.1410E+03,7.0400E+03,7.3430E+03,4.0850E+03,2.0880E+03,
			7.7800E+02,3.7870E+02,2.4080E+02,6.9410E+02,6.3930E+02,6.0440E+02,8.1820E+02,7.5630E+02,
			6.9920E+02,8.0650E+02,6.3740E+02,3.0330E+02,1.6910E+02,5.7430E+01,2.6510E+01,8.9290E+00,
			6.1300E+00,3.3160E+01,2.2700E+01,1.2730E+01,7.8240E+00,3.6330E+00,2.0100E+00,7.2000E-01,
			3.7590E-01,1.7970E-01,1.2230E-01,9.6990E-02,8.2810E-02,6.6960E-02,5.7850E-02,5.7030E-02,
			5.0520E-02,4.5940E-02,4.0780E-02,4.0480E-02,3.6810E-02,3.5770E-02,3.5830E-02,3.6340E-02,
			3.7090E-02,3.7970E-02,3.8910E-02,3.9870E-02,4.0840E-02,4.1780E-02,4.2720E-02,4.3600E-02,
			4.4450E-02,4.5250E-02,4.6750E-02,4.8150E-02,4.9470E-02,5.0690E-02,5.1830E-02,5.2880E-02,
			5.3880E-02,5.8010E-02,6.1230E-02,6.3780E-02,6.7630E-02,7.0410E-02,7.4910E-02,7.7660E-02,
			8.0920E-02,8.2770E-02,8.4040E-02,8.4910E-02,8.6090E-02,8.6890E-02,8.8030E-02,8.8650E-02,
			8.9320E-02,8.9680E-02,8.9900E-02,9.0080E-02,9.0260E-02,9.0390E-02,9.0570E-02,9.0660E-02,
			9.0800E-02,9.0850E-02,9.0890E-02,9.0890E-02,9.0940E-02,9.0940E-02};

	final static double[] Coh = {8.4580E+00,8.3930E+00,8.3250E+00,8.3250E+00,8.0000E+00,7.4810E+00,
			6.4810E+00,5.6370E+00,5.0870E+00,5.0870E+00,4.9450E+00,4.8800E+00,4.8800E+00,4.7750E+00,
			4.6690E+00,4.6690E+00,4.3680E+00,3.4640E+00,2.8150E+00,1.8520E+00,1.3220E+00,7.5450E-01,
			6.1190E-01,6.1190E-01,4.9130E-01,3.4920E-01,2.6100E-01,1.6080E-01,1.0890E-01,5.2980E-02,
			3.1360E-02,1.4640E-02,8.4350E-03,5.4720E-03,3.8370E-03,2.1810E-03,1.4040E-03,1.3450E-03,
			9.0220E-04,6.2790E-04,3.5400E-04,3.3900E-04,1.5760E-04,8.8710E-05,5.6790E-05,3.9440E-05,
			2.8980E-05,2.2190E-05,1.7540E-05,1.4200E-05,1.1740E-05,9.8620E-06,8.4030E-06,7.2470E-06,
			6.3120E-06,5.5500E-06,4.3840E-06,3.5510E-06,2.9350E-06,2.4660E-06,2.1010E-06,1.8120E-06,
			1.5780E-06,8.8800E-07,5.6830E-07,3.9460E-07,2.2200E-07,1.4210E-07,6.3120E-08,3.5510E-08,
			1.5780E-08,8.8760E-09,5.6830E-09,3.9460E-09,2.2190E-09,1.4210E-09,6.3120E-10,3.5510E-10,
			1.5780E-10,8.8760E-11,5.6830E-11,3.9460E-11,2.2190E-11,1.4210E-11,6.3120E-12,3.5510E-12,
			1.5780E-12,8.8760E-13,5.6830E-13,3.9460E-13,2.2190E-13,1.4210E-13};

	final static double[] Incoh= {4.4170E-03,4.9690E-03,5.5820E-03,5.5820E-03,8.5220E-03,1.2850E-02,
			2.1230E-02,2.8740E-02,3.3830E-02,3.3830E-02,3.5150E-02,3.5770E-02,3.5770E-02,3.6770E-02,
			3.7770E-02,3.7770E-02,4.0710E-02,5.0270E-02,5.8480E-02,7.4400E-02,8.4860E-02,9.7290E-02,
			1.0080E-01,1.0080E-01,1.0380E-01,1.0730E-01,1.0910E-01,1.0960E-01,1.0810E-01,1.0190E-01,
			9.5550E-02,8.4950E-02,7.6880E-02,7.0640E-02,6.5590E-02,5.7840E-02,5.2110E-02,5.1560E-02,
			4.6650E-02,4.2440E-02,3.6250E-02,3.5800E-02,2.8540E-02,2.3810E-02,2.0570E-02,1.8180E-02,
			1.6340E-02,1.4880E-02,1.3670E-02,1.2670E-02,1.1820E-02,1.1080E-02,1.0440E-02,9.8760E-03,
			9.3760E-03,8.9310E-03,8.1600E-03,7.5270E-03,6.9900E-03,6.5320E-03,6.1330E-03,5.7890E-03,
			5.4810E-03,4.3540E-03,3.6350E-03,3.1320E-03,2.4700E-03,2.0500E-03,1.4600E-03,1.1440E-03,
			8.1140E-04,6.3530E-04,5.2610E-04,4.5030E-04,3.5170E-04,2.8930E-04,2.0210E-04,1.5640E-04,
			1.0880E-04,8.3940E-05,6.8620E-05,5.8210E-05,4.4840E-05,3.6610E-05,2.5300E-05,1.9450E-05,
			1.3420E-05,1.0300E-05,8.3850E-06,7.0910E-06,5.4350E-06,4.4230E-06};

	final static double[] PE = {9.4030E+03,8.1330E+03,7.0320E+03,7.3340E+03,4.0770E+03,2.0800E+03,
			7.7150E+02,3.7300E+02,2.3570E+02,6.8900E+02,6.3440E+02,5.9950E+02,8.1330E+02,7.5150E+02,
			6.9450E+02,8.0180E+02,6.3300E+02,2.9980E+02,1.6620E+02,5.5500E+01,2.5100E+01,8.0780E+00,
			5.4170E+00,3.2440E+01,2.2110E+01,1.2270E+01,7.4540E+00,3.3630E+00,1.7930E+00,5.6510E-01,
			2.4900E-01,8.0090E-02,3.6990E-02,2.0880E-02,1.3380E-02,6.9400E-03,4.3350E-03,4.1260E-03,
			2.7820E-03,1.9910E-03,1.2110E-03,1.1690E-03,6.4220E-04,4.2500E-04,3.1420E-04,2.4780E-04,
			2.0400E-04,1.7310E-04,1.5010E-04,1.3240E-04,1.1840E-04,1.0710E-04,9.7660E-05,8.9720E-05,
			8.3020E-05,7.7200E-05,6.7700E-05,6.0320E-05,5.4350E-05,4.9450E-05,4.5350E-05,4.1880E-05,
			3.8910E-05,2.8700E-05,2.2720E-05,1.8810E-05,1.3990E-05,1.1130E-05,7.3710E-06,5.5090E-06,
			3.6590E-06,2.7390E-06,2.1890E-06,1.8230E-06,1.3660E-06,1.0920E-06,7.2750E-07,5.4540E-07,
			3.6360E-07,2.7260E-07,2.1810E-07,1.8170E-07,1.3630E-07,1.0900E-07,7.2660E-08,5.4490E-08,
			3.6330E-08,2.7250E-08,2.1800E-08,1.8170E-08,1.3620E-08,1.0900E-08};

	final static double[] PrProdN= {0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,
			0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,
			0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,
			0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,
			0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,
			1.9140E-04,8.8530E-04,2.9710E-03,3.1730E-03,7.4580E-03,1.1410E-02,1.4810E-02,1.7740E-02,
			2.0350E-02,2.2690E-02,2.4820E-02,2.6770E-02,2.8570E-02,3.0230E-02,3.1780E-02,3.3210E-02,
			3.4530E-02,3.5760E-02,3.7990E-02,3.9990E-02,4.1810E-02,4.3460E-02,4.4970E-02,4.6330E-02,
			4.7610E-02,5.2750E-02,5.6600E-02,5.9580E-02,6.3990E-02,6.7110E-02,7.2060E-02,7.5040E-02,
			7.8530E-02,8.0500E-02,8.1830E-02,8.2750E-02,8.3990E-02,8.4810E-02,8.6000E-02,8.6650E-02,
			8.7330E-02,8.7700E-02,8.7930E-02,8.8110E-02,8.8300E-02,8.8440E-02,8.8620E-02,8.8710E-02,
			8.8850E-02,8.8890E-02,8.8940E-02,8.8940E-02,8.8990E-02,8.8990E-02};

	final static double[] PrProdE = {0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,
			0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,
			0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,
			0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,
			0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,
			0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,9.9810E-06,4.0700E-05,8.1000E-05,1.2430E-04,
			1.6740E-04,2.0910E-04,2.4890E-04,2.8680E-04,3.2260E-04,3.5630E-04,3.8810E-04,4.1830E-04,
			4.4690E-04,4.7380E-04,5.2430E-04,5.7020E-04,6.1190E-04,6.5040E-04,6.8570E-04,7.1830E-04,
			7.4900E-04,8.7520E-04,9.7100E-04,1.0470E-03,1.1610E-03,1.2440E-03,1.3810E-03,1.4670E-03,
			1.5720E-03,1.6360E-03,1.6790E-03,1.7100E-03,1.7540E-03,1.7830E-03,1.8260E-03,1.8500E-03,
			1.8760E-03,1.8920E-03,1.9010E-03,1.9080E-03,1.9170E-03,1.9220E-03,1.9300E-03,1.9340E-03,
			1.9380E-03,1.9410E-03,1.9430E-03,1.9440E-03,1.9450E-03,1.9460E-03};

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

		if(theMeV >= 100000.0)
		{
			muMass = muArr[muArr.length - 1];
		}			
		else
		{
			//Look everwhere except the last point
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
			//System.out.println(muLo + " " + muHi);
			else muMass = Interpolation.logTerp(mevLo, mevHi, theMeV, muLo, muHi);
		}
		return muMass;
	}
}
