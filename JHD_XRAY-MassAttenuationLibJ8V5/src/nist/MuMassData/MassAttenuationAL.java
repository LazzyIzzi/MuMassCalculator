package nist.MuMassData;

import jhd.MathTools.Interpolation;
/**
 * @author John H Dunsmuir
 * @hidden
 */


class MassAttenuationAL {

	final static double[] MeV= {1.00000E-03,1.50000E-03,1.56000E-03,1.56001E-03,2.00000E-03,3.00000E-03,4.00000E-03,5.00000E-03,6.00000E-03,
			8.00000E-03,1.00000E-02,1.50000E-02,2.00000E-02,3.00000E-02,4.00000E-02,5.00000E-02,6.00000E-02,8.00000E-02,1.00000E-01,
			1.50000E-01,2.00000E-01,3.00000E-01,4.00000E-01,5.00000E-01,6.00000E-01,8.00000E-01,1.00000E+00,1.02200E+00,1.25000E+00,
			1.50000E+00,2.00000E+00,2.04400E+00,3.00000E+00,4.00000E+00,5.00000E+00,6.00000E+00,7.00000E+00,8.00000E+00,9.00000E+00,
			1.00000E+01,1.10000E+01,1.20000E+01,1.30000E+01,1.40000E+01,1.50000E+01,1.60000E+01,1.80000E+01,2.00000E+01,2.20000E+01,
			2.40000E+01,2.60000E+01,2.80000E+01,3.00000E+01,4.00000E+01,5.00000E+01,6.00000E+01,8.00000E+01,1.00000E+02,1.50000E+02,
			2.00000E+02,3.00000E+02,4.00000E+02,5.00000E+02,6.00000E+02,8.00000E+02,1.00000E+03,1.50000E+03,2.00000E+03,3.00000E+03,
			4.00000E+03,5.00000E+03,6.00000E+03,8.00000E+03,1.00000E+04,1.50000E+04,2.00000E+04,3.00000E+04,4.00000E+04,5.00000E+04,
			6.00000E+04,8.00000E+04,1.00000E+05};	
	final static double[] Coh= {2.2560E+00,2.0400E+00,2.0150E+00,2.0150E+00,1.8380E+00,1.5230E+00,1.2950E+00,1.1160E+00,9.6380E-01,7.2290E-01,5.5130E-01,3.1360E-01,2.0460E-01,1.0950E-01,6.8590E-02,4.6780E-02,3.3860E-02,2.0050E-02,1.3240E-02,6.1220E-03,3.5040E-03,1.5800E-03,8.9340E-04,5.7320E-04,3.9860E-04,2.2450E-04,1.4380E-04,1.3770E-04,9.2070E-05,6.3950E-05,3.5980E-05,3.4440E-05,1.5990E-05,8.9970E-06,5.7580E-06,4.0000E-06,2.9370E-06,2.2500E-06,1.7770E-06,1.4400E-06,1.1900E-06,9.9970E-07,8.5190E-07,7.3450E-07,6.3990E-07,5.6250E-07,4.4440E-07,3.6000E-07,2.9750E-07,2.5000E-07,2.1300E-07,1.8360E-07,1.6000E-07,8.9970E-08,5.7580E-08,4.0000E-08,2.2500E-08,1.4390E-08,6.3970E-09,3.5980E-09,1.5990E-09,8.9970E-10,5.7580E-10,3.9970E-10,2.2500E-10,1.4390E-10,6.3970E-11,3.5980E-11,1.5990E-11,8.9970E-12,5.7580E-12,3.9970E-12,2.2500E-12,1.4390E-12,6.3970E-13,3.5980E-13,1.5990E-13,8.9970E-14,5.7580E-14,3.9970E-14,2.2500E-14,1.4390E-14};
	final static double[] Incoh={1.4270E-02,2.4770E-02,2.5940E-02,2.5940E-02,3.3750E-02,4.7320E-02,5.8100E-02,6.7870E-02,7.6960E-02,9.2920E-02,1.0580E-01,1.2650E-01,1.3710E-01,1.4640E-01,1.4940E-01,1.4960E-01,1.4830E-01,1.4390E-01,1.3880E-01,1.2670E-01,1.1680E-01,1.0210E-01,9.1620E-02,8.3740E-02,7.7540E-02,6.8140E-02,6.1290E-02,6.0640E-02,5.4820E-02,4.9820E-02,4.2520E-02,4.2010E-02,3.3460E-02,2.7900E-02,2.4110E-02,2.1300E-02,1.9150E-02,1.7430E-02,1.6020E-02,1.4840E-02,1.3840E-02,1.2980E-02,1.2230E-02,1.1570E-02,1.0990E-02,1.0460E-02,9.5620E-03,8.8180E-03,8.1890E-03,7.6530E-03,7.1870E-03,6.7780E-03,6.4190E-03,5.1000E-03,4.2590E-03,3.6690E-03,2.8930E-03,2.4020E-03,1.7100E-03,1.3410E-03,9.5040E-04,7.4440E-04,6.1620E-04,5.2760E-04,4.1200E-04,3.3900E-04,2.3680E-04,1.8320E-04,1.2740E-04,9.8340E-05,8.0390E-05,6.8190E-05,5.2520E-05,4.2880E-05,2.9640E-05,2.2790E-05,1.5720E-05,1.2060E-05,9.8250E-06,8.3050E-06,6.3680E-06,5.1800E-06};
	final static double[] PE= {1.1830E+03,4.0020E+02,3.6000E+02,3.9550E+03,2.2610E+03,7.8650E+02,3.5910E+02,1.9220E+02,1.1430E+02,4.9500E+01,2.5560E+01,7.5150E+00,3.1000E+00,8.7220E-01,3.5040E-01,1.7180E-01,9.5640E-02,3.7830E-02,1.8400E-02,4.9930E-03,2.0020E-03,5.7430E-04,2.4800E-04,1.3440E-04,8.4010E-05,4.2520E-05,2.6430E-05,2.4890E-05,1.6880E-05,1.2220E-05,7.6330E-06,7.3810E-06,4.2230E-06,2.8810E-06,2.1750E-06,1.7440E-06,1.4530E-06,1.2450E-06,1.0880E-06,9.6640E-07,8.6910E-07,7.8940E-07,7.2290E-07,6.6690E-07,6.1870E-07,5.7720E-07,5.0870E-07,4.5460E-07,4.1090E-07,3.7500E-07,3.4460E-07,3.1890E-07,2.9680E-07,2.2030E-07,1.7510E-07,1.4530E-07,1.0840E-07,8.6440E-08,5.7380E-08,4.2940E-08,2.8570E-08,2.1400E-08,1.7110E-08,1.4250E-08,1.0680E-08,8.5440E-09,5.6940E-09,4.2700E-09,2.8460E-09,2.1340E-09,1.7070E-09,1.4230E-09,1.0670E-09,8.5350E-10,5.6890E-10,4.2670E-10,2.8460E-10,2.1340E-10,1.7070E-10,1.4220E-10,1.0670E-10,8.5330E-11};
	final static double[] PrProdN= {0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,3.1340E-05,1.7080E-04,6.7470E-04,7.2720E-04,1.9180E-03,3.1020E-03,4.1540E-03,5.0980E-03,5.9370E-03,6.6940E-03,7.3770E-03,7.9990E-03,8.5640E-03,9.0820E-03,9.5620E-03,1.0010E-02,1.0430E-02,1.0820E-02,1.1540E-02,1.2180E-02,1.2760E-02,1.3290E-02,1.3770E-02,1.4220E-02,1.4630E-02,1.6340E-02,1.7610E-02,1.8620E-02,2.0130E-02,2.1210E-02,2.2940E-02,2.4020E-02,2.5270E-02,2.6000E-02,2.6490E-02,2.6830E-02,2.7300E-02,2.7610E-02,2.8060E-02,2.8300E-02,2.8570E-02,2.8730E-02,2.8810E-02,2.8880E-02,2.8950E-02,2.9020E-02,2.9080E-02,2.9130E-02,2.9150E-02,2.9170E-02,2.9190E-02,2.9190E-02,2.9220E-02,2.9220E-02};
	final static double[] PrProdE= {0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,0.0000E+00,1.1710E-05,4.7810E-05,9.5240E-05,1.4630E-04,1.9720E-04,2.4660E-04,2.9420E-04,3.3930E-04,3.8210E-04,4.2250E-04,4.6090E-04,4.9730E-04,5.3190E-04,5.6490E-04,6.2630E-04,6.8230E-04,7.3390E-04,7.8140E-04,8.2560E-04,8.6670E-04,9.0530E-04,1.0660E-03,1.1910E-03,1.2920E-03,1.4460E-03,1.5610E-03,1.7550E-03,1.8810E-03,2.0390E-03,2.1360E-03,2.2050E-03,2.2560E-03,2.3280E-03,2.3750E-03,2.4480E-03,2.4910E-03,2.5380E-03,2.5650E-03,2.5820E-03,2.5940E-03,2.6090E-03,2.6200E-03,2.6340E-03,2.6430E-03,2.6520E-03,2.6560E-03,2.6580E-03,2.6600E-03,2.6630E-03,2.6650E-03};	
	final static double[] TotAttn= {1.1850E+03,4.0230E+02,3.6210E+02,3.9570E+03,2.2630E+03,7.8810E+02,3.6050E+02,1.9340E+02,1.1530E+02,5.0320E+01,2.6210E+01,7.9550E+00,3.4420E+00,1.1280E+00,5.6840E-01,3.6810E-01,2.7780E-01,2.0180E-01,1.7040E-01,1.3780E-01,1.2230E-01,1.0420E-01,9.2760E-02,8.4450E-02,7.8020E-02,6.8410E-02,6.1460E-02,6.0800E-02,5.4960E-02,5.0060E-02,4.3240E-02,4.2770E-02,3.5410E-02,3.1060E-02,2.8360E-02,2.6550E-02,2.5290E-02,2.4370E-02,2.3690E-02,2.3180E-02,2.2790E-02,2.2490E-02,2.2260E-02,2.2080E-02,2.1950E-02,2.1850E-02,2.1730E-02,2.1680E-02,2.1680E-02,2.1720E-02,2.1790E-02,2.1870E-02,2.1960E-02,2.2510E-02,2.3060E-02,2.3580E-02,2.4470E-02,2.5170E-02,2.6410E-02,2.7240E-02,2.8250E-02,2.8880E-02,2.9310E-02,2.9610E-02,3.0040E-02,3.0320E-02,3.0740E-02,3.0980E-02,3.1230E-02,3.1390E-02,3.1480E-02,3.1540E-02,3.1610E-02,3.1680E-02,3.1750E-02,3.1790E-02,3.1820E-02,3.1840E-02,3.1860E-02,3.1860E-02,3.1890E-02,3.1890E-02};

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
