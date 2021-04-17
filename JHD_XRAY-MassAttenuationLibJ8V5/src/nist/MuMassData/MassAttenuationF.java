package nist.MuMassData;
/**
 * @author John H Dunsmuir
 * @hidden
 */

import jhd.MathTools.Interpolation;


class MassAttenuationF {

	final static double[] MeV= {1.0000E-03,1.5000E-03,2.0000E-03,3.0000E-03,4.0000E-03,5.0000E-03,6.0000E-03,8.0000E-03,1.0000E-02,1.5000E-02,2.0000E-02,3.0000E-02,4.0000E-02,5.0000E-02,6.0000E-02,8.0000E-02,1.0000E-01,1.5000E-01,2.0000E-01,3.0000E-01,4.0000E-01,5.0000E-01,6.0000E-01,8.0000E-01,1.0000E+00,1.0220E+00,1.2500E+00,1.5000E+00,2.0000E+00,2.0440E+00,3.0000E+00,4.0000E+00,5.0000E+00,6.0000E+00,7.0000E+00,8.0000E+00,9.0000E+00,1.0000E+01,1.1000E+01,1.2000E+01,1.3000E+01,1.4000E+01,1.5000E+01,1.6000E+01,1.8000E+01,2.0000E+01,2.2000E+01,2.4000E+01,2.6000E+01,2.8000E+01,3.0000E+01,4.0000E+01,5.0000E+01,6.0000E+01,8.0000E+01,1.0000E+02,1.5000E+02,2.0000E+02,3.0000E+02,4.0000E+02,5.0000E+02,6.0000E+02,8.0000E+02,1.0000E+03,1.5000E+03,2.0000E+03,3.0000E+03,4.0000E+03,5.0000E+03,6.0000E+03,8.0000E+03,1.0000E+04,1.5000E+04,2.0000E+04,3.0000E+04,4.0000E+04,5.0000E+04,6.0000E+04,8.0000E+04,1.0000E+05};
	final static double[] Coh= {1.6200E+00,1.5220E+00,1.4030E+00,1.1490E+00,9.1990E-01,7.3700E-01,5.9780E-01,4.1430E-01,3.0580E-01,1.7340E-01,1.1450E-01,6.0890E-02,3.7440E-02,2.5250E-02,1.8170E-02,1.0680E-02,7.0120E-03,3.2110E-03,1.8270E-03,8.1940E-04,4.6250E-04,2.9630E-04,2.0600E-04,1.1600E-04,7.4240E-05,7.1070E-05,4.7520E-05,3.3000E-05,1.8570E-05,1.7780E-05,8.2540E-06,4.6440E-06,2.9710E-06,2.0640E-06,1.5160E-06,1.1610E-06,9.1700E-07,7.4300E-07,6.1400E-07,5.1570E-07,4.3970E-07,3.7910E-07,3.3030E-07,2.9020E-07,2.2930E-07,1.8570E-07,1.5350E-07,1.2900E-07,1.0990E-07,9.4750E-08,8.2540E-08,4.6440E-08,2.9710E-08,2.0640E-08,1.1610E-08,7.4270E-09,3.3000E-09,1.8570E-09,8.2540E-10,4.6410E-10,2.9710E-10,2.0630E-10,1.1600E-10,7.4270E-11,3.3000E-11,1.8570E-11,8.2540E-12,4.6410E-12,2.9710E-12,2.0630E-12,1.1600E-12,7.4270E-13,3.3000E-13,1.8570E-13,8.2540E-14,4.6410E-14,2.9710E-14,2.0630E-14,1.1600E-14,7.4270E-15};
	final static double[] Incoh= {6.4320E-03,1.3540E-02,2.2150E-02,4.0990E-02,5.8990E-02,7.4620E-02,8.7550E-02,1.0630E-01,1.1840E-01,1.3490E-01,1.4340E-01,1.5090E-01,1.5240E-01,1.5140E-01,1.4920E-01,1.4370E-01,1.3800E-01,1.2540E-01,1.1530E-01,1.0050E-01,9.0210E-02,8.2420E-02,7.6270E-02,6.7040E-02,6.0290E-02,5.9660E-02,5.3920E-02,4.9010E-02,4.1810E-02,4.1300E-02,3.2900E-02,2.7440E-02,2.3700E-02,2.0950E-02,1.8830E-02,1.7140E-02,1.5750E-02,1.4590E-02,1.3610E-02,1.2760E-02,1.2030E-02,1.1380E-02,1.0800E-02,1.0290E-02,9.4020E-03,8.6690E-03,8.0510E-03,7.5250E-03,7.0660E-03,6.6660E-03,6.3110E-03,5.0150E-03,4.1870E-03,3.6070E-03,2.8450E-03,2.3610E-03,1.6820E-03,1.3180E-03,9.3450E-04,7.3190E-04,6.0580E-04,5.1860E-04,4.0510E-04,3.3310E-04,2.3280E-04,1.8010E-04,1.2520E-04,9.6680E-05,7.9060E-05,6.7040E-05,5.1640E-05,4.2160E-05,2.9140E-05,2.2400E-05,1.5450E-05,1.1860E-05,9.6580E-06,8.1650E-06,6.2600E-06,5.0940E-06};
	final static double[] PE= {5.6490E+03,1.9770E+03,9.0340E+02,2.8760E+02,1.2460E+02,6.4320E+01,3.7210E+01,1.5490E+01,7.7820E+00,2.1840E+00,8.7490E-01,2.3700E-01,9.2970E-02,4.4820E-02,2.4630E-02,9.5630E-03,4.5930E-03,1.2220E-03,4.8400E-04,1.3730E-04,5.8900E-05,3.1760E-05,1.9800E-05,9.9940E-06,6.2130E-06,5.8130E-06,3.9430E-06,2.8620E-06,1.7970E-06,1.7380E-06,1.0020E-06,6.8660E-07,5.2020E-07,4.1810E-07,3.4900E-07,2.9940E-07,2.6200E-07,2.3290E-07,2.0960E-07,1.9050E-07,1.7460E-07,1.6110E-07,1.4950E-07,1.3950E-07,1.2310E-07,1.1000E-07,9.9500E-08,9.0820E-08,8.3520E-08,7.7310E-08,7.1950E-08,5.3440E-08,4.2510E-08,3.5280E-08,2.6330E-08,2.1000E-08,1.3950E-08,1.0440E-08,6.9450E-09,5.2050E-09,4.1620E-09,3.4680E-09,2.5980E-09,2.0780E-09,1.3850E-09,1.0380E-09,6.9230E-10,5.1890E-10,4.1520E-10,3.4610E-10,2.5950E-10,2.0760E-10,1.3840E-10,1.0380E-10,6.9200E-11,5.1890E-11,4.1520E-11,3.4580E-11,2.5940E-11,2.0760E-11};
	final static double[] PrProdN= {0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,2.0620E-05,1.1410E-04,4.5460E-04,4.9010E-04,1.3000E-03,2.1080E-03,2.8290E-03,3.4740E-03,4.0510E-03,4.5680E-03,5.0370E-03,5.4650E-03,5.8550E-03,6.2100E-03,6.5390E-03,6.8470E-03,7.1350E-03,7.4050E-03,7.9020E-03,8.3460E-03,8.7490E-03,9.1130E-03,9.4490E-03,9.7600E-03,1.0050E-02,1.1220E-02,1.2120E-02,1.2820E-02,1.3880E-02,1.4660E-02,1.5940E-02,1.6720E-02,1.7650E-02,1.8190E-02,1.8540E-02,1.8800E-02,1.9150E-02,1.9370E-02,1.9690E-02,1.9870E-02,2.0060E-02,2.0160E-02,2.0230E-02,2.0270E-02,2.0330E-02,2.0370E-02,2.0420E-02,2.0450E-02,2.0480E-02,2.0500E-02,2.0510E-02,2.0510E-02,2.0520E-02,2.0530E-02};
	final static double[] PrProdE= {0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,1.1510E-05,4.7010E-05,9.3640E-05,1.4380E-04,1.9390E-04,2.4260E-04,2.8920E-04,3.3380E-04,3.7590E-04,4.1560E-04,4.5330E-04,4.8940E-04,5.2370E-04,5.5600E-04,6.1650E-04,6.7200E-04,7.2300E-04,7.7030E-04,8.1400E-04,8.5490E-04,8.9290E-04,1.0540E-03,1.1780E-03,1.2790E-03,1.4360E-03,1.5540E-03,1.7550E-03,1.8860E-03,2.0500E-03,2.1500E-03,2.2190E-03,2.2700E-03,2.3410E-03,2.3880E-03,2.4580E-03,2.4970E-03,2.5400E-03,2.5660E-03,2.5810E-03,2.5920E-03,2.6060E-03,2.6150E-03,2.6280E-03,2.6350E-03,2.6420E-03,2.6460E-03,2.6490E-03,2.6510E-03,2.6520E-03,2.6540E-03};
	final static double[] TotAttn= {5.6500E+03,1.9790E+03,9.0480E+02,2.8880E+02,1.2560E+02,6.5130E+01,3.7900E+01,1.6010E+01,8.2060E+00,2.4920E+00,1.1330E+00,4.4880E-01,2.8280E-01,2.2140E-01,1.9200E-01,1.6390E-01,1.4960E-01,1.2980E-01,1.1760E-01,1.0150E-01,9.0730E-02,8.2740E-02,7.6490E-02,6.7170E-02,6.0370E-02,5.9730E-02,5.3990E-02,4.9160E-02,4.2280E-02,4.1810E-02,3.4220E-02,2.9600E-02,2.6630E-02,2.4570E-02,2.3080E-02,2.1950E-02,2.1080E-02,2.0390E-02,1.9840E-02,1.9390E-02,1.9020E-02,1.8710E-02,1.8460E-02,1.8250E-02,1.7920E-02,1.7690E-02,1.7520E-02,1.7410E-02,1.7330E-02,1.7280E-02,1.7250E-02,1.7290E-02,1.7480E-02,1.7710E-02,1.8160E-02,1.8580E-02,1.9380E-02,1.9930E-02,2.0630E-02,2.1070E-02,2.1370E-02,2.1590E-02,2.1890E-02,2.2090E-02,2.2380E-02,2.2550E-02,2.2730E-02,2.2830E-02,2.2890E-02,2.2930E-02,2.2990E-02,2.3030E-02,2.3080E-02,2.3110E-02,2.3140E-02,2.3150E-02,2.3160E-02,2.3170E-02,2.3180E-02,2.3190E-02};

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
