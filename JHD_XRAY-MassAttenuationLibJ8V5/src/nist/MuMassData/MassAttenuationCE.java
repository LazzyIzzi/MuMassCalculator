package nist.MuMassData;
/**
 * @author John H Dunsmuir
 * @hidden
 */

import jhd.MathTools.Interpolation;


class MassAttenuationCE {

	final static double[] MeV= {1.00000E-03,1.08900E-03,1.18500E-03,1.18501E-03,1.22800E-03,1.27300E-03,1.27301E-03,1.35200E-03,1.43700E-03,
			1.43701E-03,1.50000E-03,2.00000E-03,3.00000E-03,4.00000E-03,5.00000E-03,5.72300E-03,5.72310E-03,6.00000E-03,6.16400E-03,
			6.16410E-03,6.35400E-03,6.54900E-03,6.54910E-03,8.00000E-03,1.00000E-02,1.50000E-02,2.00000E-02,3.00000E-02,4.00000E-02,
			4.04400E-02,4.04410E-02,5.00000E-02,6.00000E-02,8.00000E-02,1.00000E-01,1.50000E-01,2.00000E-01,3.00000E-01,4.00000E-01,
			5.00000E-01,6.00000E-01,8.00000E-01,1.00000E+00,1.02200E+00,1.25000E+00,1.50000E+00,2.00000E+00,2.04400E+00,3.00000E+00,
			4.00000E+00,5.00000E+00,6.00000E+00,7.00000E+00,8.00000E+00,9.00000E+00,1.00000E+01,1.10000E+01,1.20000E+01,1.30000E+01,
			1.40000E+01,1.50000E+01,1.60000E+01,1.80000E+01,2.00000E+01,2.20000E+01,2.40000E+01,2.60000E+01,2.80000E+01,3.00000E+01,
			4.00000E+01,5.00000E+01,6.00000E+01,8.00000E+01,1.00000E+02,1.50000E+02,2.00000E+02,3.00000E+02,4.00000E+02,5.00000E+02,
			6.00000E+02,8.00000E+02,1.00000E+03,1.50000E+03,2.00000E+03,3.00000E+03,4.00000E+03,5.00000E+03,6.00000E+03,8.00000E+03,
			1.00000E+04,1.50000E+04,2.00000E+04,3.00000E+04,4.00000E+04,5.00000E+04,6.00000E+04,8.00000E+04,1.00000E+05};
	final static double[] Coh= {8.9870E+00,8.8880E+00,8.7890E+00,8.7890E+00,8.7480E+00,8.7030E+00,8.7030E+00,8.6170E+00,8.5230E+00,8.5230E+00,8.4540E+00,7.9210E+00,6.9200E+00,6.0470E+00,5.3120E+00,4.8650E+00,4.8650E+00,4.7060E+00,4.6160E+00,4.6160E+00,4.5150E+00,4.4140E+00,4.4140E+00,3.7620E+00,3.0710E+00,2.0150E+00,1.4490E+00,8.4030E-01,5.4670E-01,5.3770E-01,5.3770E-01,3.8890E-01,2.9180E-01,1.8090E-01,1.2280E-01,5.9790E-02,3.5480E-02,1.6630E-02,9.5970E-03,6.2360E-03,4.3710E-03,2.4890E-03,1.6030E-03,1.5360E-03,1.0310E-03,7.1780E-04,4.0470E-04,3.8760E-04,1.8030E-04,1.0150E-04,6.4940E-05,4.5130E-05,3.3150E-05,2.5380E-05,2.0050E-05,1.6250E-05,1.3430E-05,1.1280E-05,9.6150E-06,8.2910E-06,7.2210E-06,6.3480E-06,5.0160E-06,4.0620E-06,3.3570E-06,2.8210E-06,2.4030E-06,2.0720E-06,1.8050E-06,1.0160E-06,6.4990E-07,4.5130E-07,2.5390E-07,1.6250E-07,7.2210E-08,4.0620E-08,1.8050E-08,1.0160E-08,6.4990E-09,4.5130E-09,2.5390E-09,1.6250E-09,7.2210E-10,4.0620E-10,1.8050E-10,1.0160E-10,6.4990E-11,4.5130E-11,2.5390E-11,1.6250E-11,7.2210E-12,4.0620E-12,1.8050E-12,1.0160E-12,6.4990E-13,4.5130E-13,2.5390E-13,1.6250E-13};
	final static double[] Incoh= {6.7310E-03,7.5160E-03,8.3340E-03,8.3340E-03,8.6730E-03,9.0210E-03,9.0210E-03,9.6730E-03,1.0380E-02,1.0380E-02,1.0900E-02,1.4880E-02,2.2520E-02,2.9560E-02,3.5910E-02,3.9990E-02,3.9990E-02,4.1460E-02,4.2310E-02,4.2310E-02,4.3260E-02,4.4230E-02,4.4230E-02,5.0720E-02,5.8450E-02,7.3750E-02,8.4330E-02,9.6790E-02,1.0330E-01,1.0350E-01,1.0350E-01,1.0690E-01,1.0870E-01,1.0940E-01,1.0810E-01,1.0210E-01,9.5800E-02,8.5270E-02,7.7230E-02,7.1000E-02,6.5930E-02,5.8150E-02,5.2390E-02,5.1880E-02,4.6930E-02,4.2700E-02,3.6470E-02,3.6030E-02,2.8720E-02,2.3970E-02,2.0700E-02,1.8300E-02,1.6450E-02,1.4970E-02,1.3760E-02,1.2750E-02,1.1890E-02,1.1150E-02,1.0510E-02,9.9410E-03,9.4380E-03,8.9870E-03,8.2130E-03,7.5730E-03,7.0360E-03,6.5760E-03,6.1720E-03,5.8240E-03,5.5140E-03,4.3840E-03,3.6580E-03,3.1520E-03,2.4860E-03,2.0630E-03,1.4690E-03,1.1520E-03,8.1660E-04,6.3950E-04,5.2950E-04,4.5340E-04,3.5400E-04,2.9120E-04,2.0340E-04,1.5740E-04,1.0940E-04,8.4500E-05,6.9070E-05,5.8580E-05,4.5130E-05,3.6850E-05,2.5470E-05,1.9580E-05,1.3500E-05,1.0370E-05,8.4410E-06,7.1350E-06,5.4710E-06,4.4530E-06};
	final static double[] PE= {9.7010E+03,7.9750E+03,6.5540E+03,7.5340E+03,6.9750E+03,6.4560E+03,6.8420E+03,6.0140E+03,5.2870E+03,5.5230E+03,5.0240E+03,2.5990E+03,9.7870E+02,4.7490E+02,2.6860E+02,1.8940E+02,5.4070E+02,4.8610E+02,4.5090E+02,6.1420E+02,5.6990E+02,5.2870E+02,6.1030E+02,3.6940E+02,2.0510E+02,6.9330E+01,3.1590E+01,1.0250E+01,4.5640E+00,4.4270E+00,2.5710E+01,1.4710E+01,9.0470E+00,4.1190E+00,2.2140E+00,7.0700E-01,3.1390E-01,1.0200E-01,4.7410E-02,2.6870E-02,1.7270E-02,8.9790E-03,5.6130E-03,5.3720E-03,3.6220E-03,2.5900E-03,1.5740E-03,1.5180E-03,8.3210E-04,5.4970E-04,4.0580E-04,3.1980E-04,2.6300E-04,2.2300E-04,1.9330E-04,1.7050E-04,1.5240E-04,1.3770E-04,1.2550E-04,1.1540E-04,1.0670E-04,9.9200E-05,8.6990E-05,7.7410E-05,6.9760E-05,6.3440E-05,5.8190E-05,5.3720E-05,4.9900E-05,3.6800E-05,2.9130E-05,2.4100E-05,1.7920E-05,1.4260E-05,9.4380E-06,7.0530E-06,4.6850E-06,3.5080E-06,2.8030E-06,2.3340E-06,1.7490E-06,1.3990E-06,9.3180E-07,6.9840E-07,4.6550E-07,3.4900E-07,2.7920E-07,2.3270E-07,1.7450E-07,1.3960E-07,9.3050E-08,6.9800E-08,4.6500E-08,3.4890E-08,2.7910E-08,2.3260E-08,1.7450E-08,1.3960E-08};
	final static double[] PrProdN= {0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,2.1770E-04,1.0060E-03,3.3150E-03,3.5360E-03,8.1750E-03,1.2390E-02,1.6010E-02,1.9130E-02,2.1900E-02,2.4390E-02,2.6640E-02,2.8710E-02,3.0620E-02,3.2390E-02,3.4040E-02,3.5570E-02,3.6990E-02,3.8300E-02,4.0680E-02,4.2810E-02,4.4740E-02,4.6500E-02,4.8090E-02,4.9560E-02,5.0930E-02,5.6430E-02,6.0520E-02,6.3700E-02,6.8380E-02,7.1690E-02,7.7020E-02,8.0160E-02,8.3850E-02,8.6000E-02,8.7380E-02,8.8410E-02,8.9740E-02,9.0600E-02,9.1850E-02,9.2540E-02,9.3270E-02,9.3650E-02,9.3910E-02,9.4080E-02,9.4300E-02,9.4470E-02,9.4640E-02,9.4770E-02,9.4860E-02,9.4940E-02,9.4990E-02,9.4990E-02,9.5030E-02,9.5030E-02};
	final static double[] PrProdE= {0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,1.0040E-05,4.0950E-05,8.1490E-05,1.2500E-04,1.6830E-04,2.1020E-04,2.5030E-04,2.8830E-04,3.2430E-04,3.5820E-04,3.9000E-04,4.2040E-04,4.4910E-04,4.7620E-04,5.2690E-04,5.7250E-04,6.1460E-04,6.5290E-04,6.8850E-04,7.2120E-04,7.5170E-04,8.7850E-04,9.7390E-04,1.0500E-03,1.1640E-03,1.2470E-03,1.3840E-03,1.4700E-03,1.5760E-03,1.6390E-03,1.6830E-03,1.7140E-03,1.7590E-03,1.7890E-03,1.8330E-03,1.8580E-03,1.8860E-03,1.9030E-03,1.9130E-03,1.9200E-03,1.9290E-03,1.9350E-03,1.9440E-03,1.9480E-03,1.9530E-03,1.9560E-03,1.9580E-03,1.9590E-03,1.9600E-03,1.9610E-03};
	final static double[] TotAttn= {9.7100E+03,7.9840E+03,6.5630E+03,7.5430E+03,6.9840E+03,6.4640E+03,6.8510E+03,6.0230E+03,5.2950E+03,5.5310E+03,5.0330E+03,2.6070E+03,9.8560E+02,4.8100E+02,2.7400E+02,1.9430E+02,5.4560E+02,4.9090E+02,4.5550E+02,6.1880E+02,5.7440E+02,5.3310E+02,6.1480E+02,3.7320E+02,2.0820E+02,7.1420E+01,3.3120E+01,1.1190E+01,5.2150E+00,5.0680E+00,2.6350E+01,1.5200E+01,9.4480E+00,4.4090E+00,2.4450E+00,8.6890E-01,4.4520E-01,2.0390E-01,1.3420E-01,1.0410E-01,8.7570E-02,6.9620E-02,5.9610E-02,5.8780E-02,5.1800E-02,4.7010E-02,4.1770E-02,4.1470E-02,3.7920E-02,3.7050E-02,3.7270E-02,3.7920E-02,3.8820E-02,3.9820E-02,4.0870E-02,4.1940E-02,4.3010E-02,4.4050E-02,4.5080E-02,4.6060E-02,4.6990E-02,4.7870E-02,4.9510E-02,5.1040E-02,5.2470E-02,5.3800E-02,5.5020E-02,5.6160E-02,5.7250E-02,6.1730E-02,6.5180E-02,6.7920E-02,7.2050E-02,7.5010E-02,7.9880E-02,8.2790E-02,8.6250E-02,8.8290E-02,8.9590E-02,9.0580E-02,9.1860E-02,9.2680E-02,9.3890E-02,9.4550E-02,9.5260E-02,9.5640E-02,9.5890E-02,9.6060E-02,9.6270E-02,9.6440E-02,9.6610E-02,9.6740E-02,9.6820E-02,9.6910E-02,9.6950E-02,9.6950E-02,9.6990E-02,9.6990E-02};

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
