package nist.MuMassData;

import gray.AtomData.*;

/**
 * XCOM: Photon Cross Sections Database
 * NIST Standard Reference Database 8 (XGAM)
 * M.J. Berger, J.H. Hubbell, S.M. Seltzer, J. Chang, J.S. Coursey, R. Sukumar, D.S. Zucker, and K. Olsen
 * NIST, PML, Radiation Physics Division
 * See https://dx.doi.org/10.18434/T48G6X for detailed information about XCOM
 */
public class LookupNIST extends AtomData{

	/**
	 * XCOM: Photon Cross Sections Database
	 * NIST Standard Reference Database 8 (XGAM)
	 * M.J. Berger, J.H. Hubbell, S.M. Seltzer, J. Chang, J.S. Coursey, R. Sukumar, D.S. Zucker, and K. Olsen
	 * NIST, PML, Radiation Physics Division
	 * https://dx.doi.org/10.18434/T48G6X
	 */

	final static String[] edgeName = {"K","L1","L2","L3","M1","M2","M3","M4","M5","N1","N2","N3","N4","N5"};

	final static double[][] absEdgeValue = {{-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.001072,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.001305,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.001560,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.001839,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.002145,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.002472,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.002822,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.003203,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.003607,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.004038,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.004493,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.004966,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.005465,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.005989,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.006539,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.007112,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.007709,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.008333,0.001008,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.008979,0.001096,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.009659,0.001194,0.001043,0.001020,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.010370,0.001298,0.001142,0.001115,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.011100,0.001414,0.001248,0.001217,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.011870,0.001526,0.001359,0.001323,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.012660,0.001654,0.001476,0.001436,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.013470,0.001782,0.001596,0.001550,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.014330,0.001921,0.001727,0.001675,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.015200,0.002065,0.001864,0.001804,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.016100,0.002216,0.002007,0.001940,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.017040,0.002373,0.002155,0.002080,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.018000,0.002532,0.002307,0.002222,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.018990,0.002698,0.002465,0.002370,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.020000,0.002865,0.002625,0.002520,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.021040,0.003043,0.002793,0.002677,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.022120,0.003224,0.002967,0.002838,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.023220,0.003412,0.003146,0.003004,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.024350,0.003604,0.003330,0.003173,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.025510,0.003806,0.003524,0.003351,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.026710,0.004018,0.003727,0.003537,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.027940,0.004237,0.003938,0.003730,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.029200,0.004465,0.004156,0.003929,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.030490,0.004698,0.004380,0.004132,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.031811,0.004939,0.004612,0.004341,0.001006,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.033171,0.005188,0.004852,0.004557,0.001072,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.034561,0.005453,0.005104,0.004782,0.001149,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.035981,0.005714,0.005359,0.005012,0.001217,0.001065,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.037441,0.005989,0.005624,0.005247,0.001293,0.001137,0.001062,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.038921,0.006266,0.005891,0.005483,0.001361,0.001204,0.001123,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.040441,0.006549,0.006164,0.005723,0.001437,0.001273,0.001185,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.041991,0.006835,0.006440,0.005964,0.001511,0.001337,0.001242,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.043571,0.007126,0.006722,0.006208,0.001575,0.001403,0.001297,0.001005,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.045181,0.007428,0.007013,0.006459,0.001653,0.001471,0.001357,0.001051,0.001027,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.046831,0.007737,0.007312,0.006716,0.001723,0.001541,0.001420,0.001106,0.001080,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.048521,0.008052,0.007617,0.006977,0.001800,0.001614,0.001481,0.001161,0.001131,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.050241,0.008376,0.007930,0.007243,0.001881,0.001688,0.001544,0.001217,0.001185,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.052001,0.008708,0.008252,0.007514,0.001968,0.001768,0.001611,0.001275,0.001241,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.053791,0.009046,0.008581,0.007790,0.002047,0.001842,0.001676,0.001332,0.001295,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.055621,0.009394,0.008918,0.008071,0.002128,0.001923,0.001741,0.001391,0.001351,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.057491,0.009751,0.009264,0.008358,0.002206,0.002006,0.001812,0.001453,0.001409,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.059391,0.010120,0.009617,0.008648,0.002307,0.002090,0.001884,0.001515,0.001468,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.061331,0.010490,0.009978,0.008944,0.002398,0.002173,0.001950,0.001576,0.001528,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.063311,0.010870,0.010350,0.009244,0.002491,0.002263,0.002024,0.001639,0.001588,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.065351,0.011270,0.010740,0.009561,0.002601,0.002365,0.002108,0.001716,0.001662,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.067421,0.011680,0.011140,0.009881,0.002708,0.002469,0.002194,0.001793,0.001735,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.069531,0.012100,0.011540,0.010210,0.002820,0.002575,0.002281,0.001872,0.001809,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.071681,0.012530,0.011960,0.010540,0.002932,0.002682,0.002367,0.001949,0.001823,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.073871,0.012970,0.012390,0.010870,0.003048,0.002792,0.002457,0.002031,0.001960,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.076111,0.013420,0.012820,0.011220,0.003174,0.002909,0.002551,0.002116,0.002040,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.078391,0.013880,0.013270,0.011560,0.003296,0.003026,0.002645,0.002202,0.002122,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.080721,0.014350,0.013730,0.011920,0.003425,0.003148,0.002743,0.002291,0.002206,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.083101,0.014840,0.014210,0.012280,0.003562,0.003278,0.002847,0.002385,0.002295,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.085531,0.015350,0.014700,0.012660,0.003704,0.003416,0.002957,0.002485,0.002389,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.088001,0.015860,0.015200,0.013040,0.003851,0.003554,0.003066,0.002586,0.002484,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.090531,0.016390,0.015710,0.013420,0.003999,0.003696,0.003177,0.002688,0.002580,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.093101,0.016940,0.016240,0.013810,0.004149,0.003854,0.003302,0.002798,0.002683,-1.000000,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.095731,0.017490,0.016780,0.014210,0.004317,0.004008,0.003426,0.002909,0.002787,0.001042,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.098401,0.018050,0.017340,0.014620,0.004482,0.004159,0.003538,0.003021,0.002892,0.001097,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.101101,0.018640,0.017910,0.015030,0.004652,0.004327,0.003663,0.003136,0.003000,0.001153,-1.000000,-1.000000,-1.000000,-1.000000},
			{0.103901,0.019240,0.018480,0.015440,0.004822,0.004489,0.003792,0.003248,0.003105,0.001208,0.001058,-1.000000,-1.000000,-1.000000},
			{0.106801,0.019840,0.019080,0.015870,0.005002,0.004656,0.003909,0.003370,0.003219,0.001269,0.001080,-1.000000,-1.000000,-1.000000},
			{0.109701,0.020470,0.019690,0.016300,0.005182,0.004830,0.004046,0.003491,0.003332,0.001329,0.001168,-1.000000,-1.000000,-1.000000},
			{0.112601,0.021100,0.020310,0.016730,0.005367,0.005001,0.004174,0.003611,0.003442,0.001387,0.001224,0.001007,-1.000000,-1.000000},
			{0.115601,0.021760,0.020950,0.017170,0.005548,0.005182,0.004303,0.003728,0.003552,0.001441,0.001273,0.001045,-1.000000,-1.000000},
			{0.118701,0.022430,0.021600,0.017610,0.005723,0.005366,0.004435,0.003850,0.003666,0.001501,0.001328,0.001087,-1.000000,-1.000000},
			{0.121801,0.023100,0.022270,0.018060,0.005933,0.005541,0.004557,0.003973,0.003778,0.001559,0.001372,0.001115,-1.000000,-1.000000},
			{0.125001,0.023770,0.022940,0.018500,0.006121,0.005710,0.004667,0.004092,0.003887,0.001617,0.001412,0.001136,-1.000000,-1.000000},
			{0.128201,0.024460,0.023800,0.018930,0.006288,0.005895,0.004797,0.004227,0.003971,0.001643,0.001440,0.001154,-1.000000,-1.000000},
			{0.131601,0.025270,0.024390,0.019450,0.006556,0.006147,0.004977,0.004366,0.004132,0.001755,0.001554,0.001235,-1.000000,-1.000000},
			{0.136001,0.026110,0.025250,0.019930,0.006754,0.006359,0.005109,0.004497,0.004253,0.001799,0.001616,0.001279,-1.000000,-1.000000},
			{0.139501,0.026900,0.026020,0.020410,0.006977,0.006574,0.005252,0.004630,0.004374,0.001868,0.001680,0.001321,0.001032,-1.000000},
			{0.143101,0.027700,0.026810,0.020900,0.007205,0.006793,0.005397,0.004766,0.004498,0.001937,0.001747,0.001366,0.001069,0.001002}};

	//***********************************************************************

	/**
	 * Returns list of tabulated atom absorption edge names
	 * @return The array of recognized atom names
	 */
	public String[] getabsEdgeNames() {
		return edgeName;
	}

	//***********************************************************************

	/**
	 * Returns the requested absorption edge energy for theAtom
	 * @param theAtom The symbol for the atom, e.g. "AU" for gold
	 * @param theEdge K, L1, L2, L3, M1, M2, M3, M4, M5, N1, N2, N3, N4, N5
	 * @return The absorption edge energy in MeV, -1 if theAtom or theEdge are not tabulated
	 */
	public double getAtomAbsEdge(String theAtom, String theEdge)
	{
		int atmIndex = getAtomSymbolIndex(theAtom);
		int edgeIndex = getAbsEdgeIndex(theEdge);
		if(atmIndex >=0  && edgeIndex >=0 )
		{
			return absEdgeValue[atmIndex][edgeIndex];
		}
		else
		{
			return -1;
		}
	}

	//***********************************************************************

	private int getAbsEdgeIndex(String theEdgeName)
	{
		int i, edgeIndex=-1;
		for(i=0;i< edgeName.length;i++)
		{				
			if(edgeName[i].equalsIgnoreCase(theEdgeName))
			{
				edgeIndex = i;
				break;
			}
		}
		return edgeIndex;			
	}

	//***********************************************************************
	
	/*
	 * These rather awkward constructs are a workaround the 65535K limit on module size
	 * The NIST SRD-8 tables contain a significant total amount of data.
	 * We treat each element separately to avoid the module size limit.
	 */

	
	/**
	 * Returns the list of tabulated photon energies for theAtom
	 * @param theAtom The element symbol for the atom e.g. "AU" for gold
	 * @return An array of tabulated photon energies in MeV between 1KeV to 100GeV, null if incorrect formula
	 */
	public double[] getAtomMevArray(String theAtom)
	{
		double[] meV;

		//ToDo: add getMevArray to other atoms
		// update this switch

		switch(theAtom.toUpperCase())
		{
		case "H":
			MassAttenuationH muMassH = new MassAttenuationH();
			meV = muMassH.getMevArray();
			break;
		case "HE":
			MassAttenuationHE muMassHE = new MassAttenuationHE();
			meV = muMassHE.getMevArray();
			break;
		case "LI":
			MassAttenuationLI muMassLI = new MassAttenuationLI();
			meV = muMassLI.getMevArray();
			break;
		case "BE":
			MassAttenuationBE muMassBE = new MassAttenuationBE();
			meV = muMassBE.getMevArray();
			break;
		case "B":
			MassAttenuationB muMassB = new MassAttenuationB();
			meV = muMassB.getMevArray();
			break;
		case "C":
			MassAttenuationC muMassC = new MassAttenuationC();
			meV = muMassC.getMevArray();
			break;
		case "N":
			MassAttenuationN muMassN = new MassAttenuationN();
			meV = muMassN.getMevArray();
			break;
		case "O":
			MassAttenuationO muMassO = new MassAttenuationO();
			meV = muMassO.getMevArray();
			break;
		case "F":
			MassAttenuationF muMassF = new MassAttenuationF();
			meV = muMassF.getMevArray();
			break;
		case "NE":
			MassAttenuationNE muMassNE = new MassAttenuationNE();
			meV = muMassNE.getMevArray();
			break;
		case "NA":
			MassAttenuationNA muMassNA = new MassAttenuationNA();
			meV = muMassNA.getMevArray();
			break;
		case "MG":
			MassAttenuationMG muMassMG = new MassAttenuationMG();
			meV = muMassMG.getMevArray();
			break;
		case "AL":
			MassAttenuationAL muMassAL = new MassAttenuationAL();
			meV = muMassAL.getMevArray();
			break;
		case "SI":
			MassAttenuationSI muMassSI = new MassAttenuationSI();
			meV = muMassSI.getMevArray();
			break;
		case "P":
			MassAttenuationP muMassP = new MassAttenuationP();
			meV = muMassP.getMevArray();
			break;
		case "S":
			MassAttenuationS muMassS = new MassAttenuationS();
			meV = muMassS.getMevArray();
			break;
		case "CL":
			MassAttenuationCL muMassCL = new MassAttenuationCL();
			meV = muMassCL.getMevArray();
			break;
		case "AR":
			MassAttenuationAR muMassAR = new MassAttenuationAR();
			meV = muMassAR.getMevArray();
			break;
		case "K":
			MassAttenuationK muMassK = new MassAttenuationK();
			meV = muMassK.getMevArray();
			break;
		case "CA":
			MassAttenuationCA muMassCA = new MassAttenuationCA();
			meV = muMassCA.getMevArray();
			break;
		case "SC":
			MassAttenuationSC muMassSC = new MassAttenuationSC();
			meV = muMassSC.getMevArray();
			break;
		case "TI":
			MassAttenuationTI muMassTI = new MassAttenuationTI();
			meV = muMassTI.getMevArray();
			break;
		case "V":
			MassAttenuationV muMassV = new MassAttenuationV();
			meV = muMassV.getMevArray();
			break;
		case "CR":
			MassAttenuationCR muMassCR = new MassAttenuationCR();
			meV = muMassCR.getMevArray();
			break;
		case "MN":
			MassAttenuationMN muMassMN = new MassAttenuationMN();
			meV = muMassMN.getMevArray();
			break;
		case "FE":
			MassAttenuationFE muMassFE = new MassAttenuationFE();
			meV = muMassFE.getMevArray();
			break;
		case "CO":
			MassAttenuationCO muMassCO = new MassAttenuationCO();
			meV = muMassCO.getMevArray();
			break;
		case "NI":
			MassAttenuationNI muMassNI = new MassAttenuationNI();
			meV = muMassNI.getMevArray();
			break;
		case "CU":
			MassAttenuationCU muMassCU = new MassAttenuationCU();
			meV = muMassCU.getMevArray();
			break;
		case "ZN":
			MassAttenuationZN muMassZN = new MassAttenuationZN();
			meV = muMassZN.getMevArray();
			break;
		case "GA":
			MassAttenuationGA muMassGA = new MassAttenuationGA();
			meV = muMassGA.getMevArray();
			break;
		case "GE":
			MassAttenuationGE muMassGE = new MassAttenuationGE();
			meV = muMassGE.getMevArray();
			break;
		case "AS":
			MassAttenuationAS muMassAS = new MassAttenuationAS();
			meV = muMassAS.getMevArray();
			break;
		case "SE":
			MassAttenuationSE muMassSE = new MassAttenuationSE();
			meV = muMassSE.getMevArray();
			break;
		case "BR":
			MassAttenuationBR muMassBR = new MassAttenuationBR();
			meV = muMassBR.getMevArray();
			break;
		case "KR":
			MassAttenuationKR muMassKR = new MassAttenuationKR();
			meV = muMassKR.getMevArray();
			break;
		case "RB":
			MassAttenuationRB muMassRB = new MassAttenuationRB();
			meV = muMassRB.getMevArray();
			break;
		case "SR":
			MassAttenuationSR muMassSR = new MassAttenuationSR();
			meV = muMassSR.getMevArray();
			break;
		case "Y":
			MassAttenuationY muMassY = new MassAttenuationY();
			meV = muMassY.getMevArray();
			break;
		case "ZR":
			MassAttenuationZR muMassZR = new MassAttenuationZR();
			meV = muMassZR.getMevArray();
			break;
		case "NB":
			MassAttenuationNB muMassNB = new MassAttenuationNB();
			meV = muMassNB.getMevArray();
			break;
		case "MO":
			MassAttenuationMO muMassMO = new MassAttenuationMO();
			meV = muMassMO.getMevArray();
			break;
		case "TC":
			MassAttenuationTC muMassTC = new MassAttenuationTC();
			meV = muMassTC.getMevArray();
			break;
		case "RU":
			MassAttenuationRU muMassRU = new MassAttenuationRU();
			meV = muMassRU.getMevArray();
			break;
		case "RH":
			MassAttenuationRH muMassRH = new MassAttenuationRH();
			meV = muMassRH.getMevArray();
			break;
		case "PD":
			MassAttenuationPD muMassPD = new MassAttenuationPD();
			meV = muMassPD.getMevArray();
			break;
		case "AG":
			MassAttenuationAG muMassAG = new MassAttenuationAG();
			meV = muMassAG.getMevArray();
			break;
		case "CD":
			MassAttenuationCD muMassCD = new MassAttenuationCD();
			meV = muMassCD.getMevArray();
			break;
		case "IN":
			MassAttenuationIN muMassIN = new MassAttenuationIN();
			meV = muMassIN.getMevArray();
			break;
		case "SN":
			MassAttenuationSN muMassSN = new MassAttenuationSN();
			meV = muMassSN.getMevArray();
			break;
		case "SB":
			MassAttenuationSB muMassSB = new MassAttenuationSB();
			meV = muMassSB.getMevArray();
			break;
		case "TE":
			MassAttenuationTE muMassTE = new MassAttenuationTE();
			meV = muMassTE.getMevArray();
			break;
		case "I":
			MassAttenuationI muMassI = new MassAttenuationI();
			meV = muMassI.getMevArray();
			break;
		case "XE":
			MassAttenuationXE muMassXE = new MassAttenuationXE();
			meV = muMassXE.getMevArray();
			break;
		case "CS":
			MassAttenuationCS muMassCS = new MassAttenuationCS();
			meV = muMassCS.getMevArray();
			break;
		case "BA":
			MassAttenuationBA muMassBA = new MassAttenuationBA();
			meV = muMassBA.getMevArray();
			break;
		case "LA":
			MassAttenuationLA muMassLA = new MassAttenuationLA();
			meV = muMassLA.getMevArray();
			break;
		case "CE":
			MassAttenuationCE muMassCE = new MassAttenuationCE();
			meV = muMassCE.getMevArray();
			break;
		case "PR":
			MassAttenuationPR muMassPR = new MassAttenuationPR();
			meV = muMassPR.getMevArray();
			break;
		case "ND":
			MassAttenuationND muMassND = new MassAttenuationND();
			meV = muMassND.getMevArray();
			break;
		case "PM":
			MassAttenuationPM muMassPM = new MassAttenuationPM();
			meV = muMassPM.getMevArray();
			break;
		case "SM":
			MassAttenuationSM muMassSM = new MassAttenuationSM();
			meV = muMassSM.getMevArray();
			break;
		case "EU":
			MassAttenuationEU muMassEU = new MassAttenuationEU();
			meV = muMassEU.getMevArray();
			break;
		case "GD":
			MassAttenuationGD muMassGD = new MassAttenuationGD();
			meV = muMassGD.getMevArray();
			break;
		case "TB":
			MassAttenuationTB muMassTB = new MassAttenuationTB();
			meV = muMassTB.getMevArray();
			break;
		case "DY":
			MassAttenuationDY muMassDY = new MassAttenuationDY();
			meV = muMassDY.getMevArray();
			break;
		case "HO":
			MassAttenuationHO muMassHO = new MassAttenuationHO();
			meV = muMassHO.getMevArray();
			break;
		case "ER":
			MassAttenuationER muMassER = new MassAttenuationER();
			meV = muMassER.getMevArray();
			break;
		case "TM":
			MassAttenuationTM muMassTM = new MassAttenuationTM();
			meV = muMassTM.getMevArray();
			break;
		case "YB":
			MassAttenuationYB muMassYB = new MassAttenuationYB();
			meV = muMassYB.getMevArray();
			break;
		case "LU":
			MassAttenuationLU muMassLU = new MassAttenuationLU();
			meV = muMassLU.getMevArray();
			break;
		case "HF":
			MassAttenuationHF muMassHF = new MassAttenuationHF();
			meV = muMassHF.getMevArray();
			break;
		case "TA":
			MassAttenuationTA muMassTA = new MassAttenuationTA();
			meV = muMassTA.getMevArray();
			break;
		case "W":
			MassAttenuationW muMassW = new MassAttenuationW();
			meV = muMassW.getMevArray();
			break;
		case "RE":
			MassAttenuationRE muMassRE = new MassAttenuationRE();
			meV = muMassRE.getMevArray();
			break;
		case "OS":
			MassAttenuationOS muMassOS = new MassAttenuationOS();
			meV = muMassOS.getMevArray();
			break;
		case "IR":
			MassAttenuationIR muMassIR = new MassAttenuationIR();
			meV = muMassIR.getMevArray();
			break;
		case "PT":
			MassAttenuationPT muMassPT = new MassAttenuationPT();
			meV = muMassPT.getMevArray();
			break;
		case "AU":
			MassAttenuationAU muMassAU = new MassAttenuationAU();
			meV = muMassAU.getMevArray();
			break;
		case "HG":
			MassAttenuationHG muMassHG = new MassAttenuationHG();
			meV = muMassHG.getMevArray();
			break;
		case "TL":
			MassAttenuationTL muMassTL = new MassAttenuationTL();
			meV = muMassTL.getMevArray();
			break;
		case "PB":
			MassAttenuationPB muMassPB = new MassAttenuationPB();
			meV = muMassPB.getMevArray();
			break;
		case "BI":
			MassAttenuationBI muMassBI = new MassAttenuationBI();
			meV = muMassBI.getMevArray();
			break;
		case "PO":
			MassAttenuationPO muMassPO = new MassAttenuationPO();
			meV = muMassPO.getMevArray();
			break;
		case "AT":
			MassAttenuationAT muMassAT = new MassAttenuationAT();
			meV = muMassAT.getMevArray();
			break;
		case "RN":
			MassAttenuationRN muMassRN = new MassAttenuationRN();
			meV = muMassRN.getMevArray();
			break;
		case "FR":
			MassAttenuationFR muMassFR = new MassAttenuationFR();
			meV = muMassFR.getMevArray();
			break;
		case "RA":
			MassAttenuationRA muMassRA = new MassAttenuationRA();
			meV = muMassRA.getMevArray();
			break;
		case "AC":
			MassAttenuationAC muMassAC = new MassAttenuationAC();
			meV = muMassAC.getMevArray();
			break;
		case "TH":
			MassAttenuationTH muMassTH = new MassAttenuationTH();
			meV = muMassTH.getMevArray();
			break;
		case "PA":
			MassAttenuationPA muMassPA = new MassAttenuationPA();
			meV = muMassPA.getMevArray();
			break;
		case "U":
			MassAttenuationU muMassU = new MassAttenuationU();
			meV = muMassU.getMevArray();
			break;
		case "NP":
			MassAttenuationNP muMassNP = new MassAttenuationNP();
			meV = muMassNP.getMevArray();
			break;
		case "PU":
			MassAttenuationPU muMassPU = new MassAttenuationPU();
			meV = muMassPU.getMevArray();
			break;
		case "AM":
			MassAttenuationAM muMassAM = new MassAttenuationAM();
			meV = muMassAM.getMevArray();
			break;
		case "CM":
			MassAttenuationCM muMassCM = new MassAttenuationCM();
			meV = muMassCM.getMevArray();
			break;
		case "BK":
			MassAttenuationBK muMassBK = new MassAttenuationBK();
			meV = muMassBK.getMevArray();
			break;
		case "CF":
			MassAttenuationCF muMassCF = new MassAttenuationCF();
			meV = muMassCF.getMevArray();
			break;
		case "ES":
			MassAttenuationES muMassES = new MassAttenuationES();
			meV = muMassES.getMevArray();
			break;
		case "FM":
			MassAttenuationFM muMassFM = new MassAttenuationFM();
			meV = muMassFM.getMevArray();
			break;
		default:
			meV = null;
			break;			
		}
		return meV;
	}


	//***********************************************************************

	/**
	 * Returns the requested cross section for theAtom at theMev
	 * @param theAtom The element symbol for the atom e.g. "AU" for gold
	 * @param theMeV The photon energy in MeV between 1KeV to 100GeV
	 * @param muMassType  "TotAttn", "Raleigh", "Compton", "PhotoElectric", "PairProdN", "PairProdE"
	 * @return The cross-section in cm2/gm, null if incorrect formula
	 */
	public double getAtomMuMass(String theAtom, double theMeV, String muMassType)
	{
		double theMuMass;
		switch(theAtom.toUpperCase())
		{
		case "H":
			MassAttenuationH muMassH = new MassAttenuationH();
			theMuMass = muMassH.getMuMass(theMeV, muMassType);
			break;
		case "HE":
			MassAttenuationHE muMassHE = new MassAttenuationHE();
			theMuMass = muMassHE.getMuMass(theMeV, muMassType);
			break;
		case "LI":
			MassAttenuationLI muMassLI = new MassAttenuationLI();
			theMuMass = muMassLI.getMuMass(theMeV, muMassType);
			break;
		case "BE":
			MassAttenuationBE muMassBE = new MassAttenuationBE();
			theMuMass = muMassBE.getMuMass(theMeV, muMassType);
			break;
		case "B":
			MassAttenuationB muMassB = new MassAttenuationB();
			theMuMass = muMassB.getMuMass(theMeV, muMassType);
			break;
		case "C":
			MassAttenuationC muMassC = new MassAttenuationC();
			theMuMass = muMassC.getMuMass(theMeV, muMassType);
			break;
		case "N":
			MassAttenuationN muMassN = new MassAttenuationN();
			theMuMass = muMassN.getMuMass(theMeV, muMassType);
			break;
		case "O":
			MassAttenuationO muMassO = new MassAttenuationO();
			theMuMass = muMassO.getMuMass(theMeV, muMassType);
			break;
		case "F":
			MassAttenuationF muMassF = new MassAttenuationF();
			theMuMass = muMassF.getMuMass(theMeV, muMassType);
			break;
		case "NE":
			MassAttenuationNE muMassNE = new MassAttenuationNE();
			theMuMass = muMassNE.getMuMass(theMeV, muMassType);
			break;
		case "NA":
			MassAttenuationNA muMassNA = new MassAttenuationNA();
			theMuMass = muMassNA.getMuMass(theMeV, muMassType);
			break;
		case "MG":
			MassAttenuationMG muMassMG = new MassAttenuationMG();
			theMuMass = muMassMG.getMuMass(theMeV, muMassType);
			break;
		case "AL":
			MassAttenuationAL muMassAL = new MassAttenuationAL();
			theMuMass = muMassAL.getMuMass(theMeV, muMassType);
			break;
		case "SI":
			MassAttenuationSI muMassSI = new MassAttenuationSI();
			theMuMass = muMassSI.getMuMass(theMeV, muMassType);
			break;
		case "P":
			MassAttenuationP muMassP = new MassAttenuationP();
			theMuMass = muMassP.getMuMass(theMeV, muMassType);
			break;
		case "S":
			MassAttenuationS muMassS = new MassAttenuationS();
			theMuMass = muMassS.getMuMass(theMeV, muMassType);
			break;
		case "CL":
			MassAttenuationCL muMassCL = new MassAttenuationCL();
			theMuMass = muMassCL.getMuMass(theMeV, muMassType);
			break;
		case "AR":
			MassAttenuationAR muMassAR = new MassAttenuationAR();
			theMuMass = muMassAR.getMuMass(theMeV, muMassType);
			break;
		case "K":
			MassAttenuationK muMassK = new MassAttenuationK();
			theMuMass = muMassK.getMuMass(theMeV, muMassType);
			break;
		case "CA":
			MassAttenuationCA muMassCA = new MassAttenuationCA();
			theMuMass = muMassCA.getMuMass(theMeV, muMassType);
			break;
		case "SC":
			MassAttenuationSC muMassSC = new MassAttenuationSC();
			theMuMass = muMassSC.getMuMass(theMeV, muMassType);
			break;
		case "TI":
			MassAttenuationTI muMassTI = new MassAttenuationTI();
			theMuMass = muMassTI.getMuMass(theMeV, muMassType);
			break;
		case "V":
			MassAttenuationV muMassV = new MassAttenuationV();
			theMuMass = muMassV.getMuMass(theMeV, muMassType);
			break;
		case "CR":
			MassAttenuationCR muMassCR = new MassAttenuationCR();
			theMuMass = muMassCR.getMuMass(theMeV, muMassType);
			break;
		case "MN":
			MassAttenuationMN muMassMN = new MassAttenuationMN();
			theMuMass = muMassMN.getMuMass(theMeV, muMassType);
			break;
		case "FE":
			MassAttenuationFE muMassFE = new MassAttenuationFE();
			theMuMass = muMassFE.getMuMass(theMeV, muMassType);
			break;
		case "CO":
			MassAttenuationCO muMassCO = new MassAttenuationCO();
			theMuMass = muMassCO.getMuMass(theMeV, muMassType);
			break;
		case "NI":
			MassAttenuationNI muMassNI = new MassAttenuationNI();
			theMuMass = muMassNI.getMuMass(theMeV, muMassType);
			break;
		case "CU":
			MassAttenuationCU muMassCU = new MassAttenuationCU();
			theMuMass = muMassCU.getMuMass(theMeV, muMassType);
			break;
		case "ZN":
			MassAttenuationZN muMassZN = new MassAttenuationZN();
			theMuMass = muMassZN.getMuMass(theMeV, muMassType);
			break;
		case "GA":
			MassAttenuationGA muMassGA = new MassAttenuationGA();
			theMuMass = muMassGA.getMuMass(theMeV, muMassType);
			break;
		case "GE":
			MassAttenuationGE muMassGE = new MassAttenuationGE();
			theMuMass = muMassGE.getMuMass(theMeV, muMassType);
			break;
		case "AS":
			MassAttenuationAS muMassAS = new MassAttenuationAS();
			theMuMass = muMassAS.getMuMass(theMeV, muMassType);
			break;
		case "SE":
			MassAttenuationSE muMassSE = new MassAttenuationSE();
			theMuMass = muMassSE.getMuMass(theMeV, muMassType);
			break;
		case "BR":
			MassAttenuationBR muMassBR = new MassAttenuationBR();
			theMuMass = muMassBR.getMuMass(theMeV, muMassType);
			break;
		case "KR":
			MassAttenuationKR muMassKR = new MassAttenuationKR();
			theMuMass = muMassKR.getMuMass(theMeV, muMassType);
			break;
		case "RB":
			MassAttenuationRB muMassRB = new MassAttenuationRB();
			theMuMass = muMassRB.getMuMass(theMeV, muMassType);
			break;
		case "SR":
			MassAttenuationSR muMassSR = new MassAttenuationSR();
			theMuMass = muMassSR.getMuMass(theMeV, muMassType);
			break;
		case "Y":
			MassAttenuationY muMassY = new MassAttenuationY();
			theMuMass = muMassY.getMuMass(theMeV, muMassType);
			break;
		case "ZR":
			MassAttenuationZR muMassZR = new MassAttenuationZR();
			theMuMass = muMassZR.getMuMass(theMeV, muMassType);
			break;
		case "NB":
			MassAttenuationNB muMassNB = new MassAttenuationNB();
			theMuMass = muMassNB.getMuMass(theMeV, muMassType);
			break;
		case "MO":
			MassAttenuationMO muMassMO = new MassAttenuationMO();
			theMuMass = muMassMO.getMuMass(theMeV, muMassType);
			break;
		case "TC":
			MassAttenuationTC muMassTC = new MassAttenuationTC();
			theMuMass = muMassTC.getMuMass(theMeV, muMassType);
			break;
		case "RU":
			MassAttenuationRU muMassRU = new MassAttenuationRU();
			theMuMass = muMassRU.getMuMass(theMeV, muMassType);
			break;
		case "RH":
			MassAttenuationRH muMassRH = new MassAttenuationRH();
			theMuMass = muMassRH.getMuMass(theMeV, muMassType);
			break;
		case "PD":
			MassAttenuationPD muMassPD = new MassAttenuationPD();
			theMuMass = muMassPD.getMuMass(theMeV, muMassType);
			break;
		case "AG":
			MassAttenuationAG muMassAG = new MassAttenuationAG();
			theMuMass = muMassAG.getMuMass(theMeV, muMassType);
			break;
		case "CD":
			MassAttenuationCD muMassCD = new MassAttenuationCD();
			theMuMass = muMassCD.getMuMass(theMeV, muMassType);
			break;
		case "IN":
			MassAttenuationIN muMassIN = new MassAttenuationIN();
			theMuMass = muMassIN.getMuMass(theMeV, muMassType);
			break;
		case "SN":
			MassAttenuationSN muMassSN = new MassAttenuationSN();
			theMuMass = muMassSN.getMuMass(theMeV, muMassType);
			break;
		case "SB":
			MassAttenuationSB muMassSB = new MassAttenuationSB();
			theMuMass = muMassSB.getMuMass(theMeV, muMassType);
			break;
		case "TE":
			MassAttenuationTE muMassTE = new MassAttenuationTE();
			theMuMass = muMassTE.getMuMass(theMeV, muMassType);
			break;
		case "I":
			MassAttenuationI muMassI = new MassAttenuationI();
			theMuMass = muMassI.getMuMass(theMeV, muMassType);
			break;
		case "XE":
			MassAttenuationXE muMassXE = new MassAttenuationXE();
			theMuMass = muMassXE.getMuMass(theMeV, muMassType);
			break;
		case "CS":
			MassAttenuationCS muMassCS = new MassAttenuationCS();
			theMuMass = muMassCS.getMuMass(theMeV, muMassType);
			break;
		case "BA":
			MassAttenuationBA muMassBA = new MassAttenuationBA();
			theMuMass = muMassBA.getMuMass(theMeV, muMassType);
			break;
		case "LA":
			MassAttenuationLA muMassLA = new MassAttenuationLA();
			theMuMass = muMassLA.getMuMass(theMeV, muMassType);
			break;
		case "CE":
			MassAttenuationCE muMassCE = new MassAttenuationCE();
			theMuMass = muMassCE.getMuMass(theMeV, muMassType);
			break;
		case "PR":
			MassAttenuationPR muMassPR = new MassAttenuationPR();
			theMuMass = muMassPR.getMuMass(theMeV, muMassType);
			break;
		case "ND":
			MassAttenuationND muMassND = new MassAttenuationND();
			theMuMass = muMassND.getMuMass(theMeV, muMassType);
			break;
		case "PM":
			MassAttenuationPM muMassPM = new MassAttenuationPM();
			theMuMass = muMassPM.getMuMass(theMeV, muMassType);
			break;
		case "SM":
			MassAttenuationSM muMassSM = new MassAttenuationSM();
			theMuMass = muMassSM.getMuMass(theMeV, muMassType);
			break;
		case "EU":
			MassAttenuationEU muMassEU = new MassAttenuationEU();
			theMuMass = muMassEU.getMuMass(theMeV, muMassType);
			break;
		case "GD":
			MassAttenuationGD muMassGD = new MassAttenuationGD();
			theMuMass = muMassGD.getMuMass(theMeV, muMassType);
			break;
		case "TB":
			MassAttenuationTB muMassTB = new MassAttenuationTB();
			theMuMass = muMassTB.getMuMass(theMeV, muMassType);
			break;
		case "DY":
			MassAttenuationDY muMassDY = new MassAttenuationDY();
			theMuMass = muMassDY.getMuMass(theMeV, muMassType);
			break;
		case "HO":
			MassAttenuationHO muMassHO = new MassAttenuationHO();
			theMuMass = muMassHO.getMuMass(theMeV, muMassType);
			break;
		case "ER":
			MassAttenuationER muMassER = new MassAttenuationER();
			theMuMass = muMassER.getMuMass(theMeV, muMassType);
			break;
		case "TM":
			MassAttenuationTM muMassTM = new MassAttenuationTM();
			theMuMass = muMassTM.getMuMass(theMeV, muMassType);
			break;
		case "YB":
			MassAttenuationYB muMassYB = new MassAttenuationYB();
			theMuMass = muMassYB.getMuMass(theMeV, muMassType);
			break;
		case "LU":
			MassAttenuationLU muMassLU = new MassAttenuationLU();
			theMuMass = muMassLU.getMuMass(theMeV, muMassType);
			break;
		case "HF":
			MassAttenuationHF muMassHF = new MassAttenuationHF();
			theMuMass = muMassHF.getMuMass(theMeV, muMassType);
			break;
		case "TA":
			MassAttenuationTA muMassTA = new MassAttenuationTA();
			theMuMass = muMassTA.getMuMass(theMeV, muMassType);
			break;
		case "W":
			MassAttenuationW muMassW = new MassAttenuationW();
			theMuMass = muMassW.getMuMass(theMeV, muMassType);
			break;
		case "RE":
			MassAttenuationRE muMassRE = new MassAttenuationRE();
			theMuMass = muMassRE.getMuMass(theMeV, muMassType);
			break;
		case "OS":
			MassAttenuationOS muMassOS = new MassAttenuationOS();
			theMuMass = muMassOS.getMuMass(theMeV, muMassType);
			break;
		case "IR":
			MassAttenuationIR muMassIR = new MassAttenuationIR();
			theMuMass = muMassIR.getMuMass(theMeV, muMassType);
			break;
		case "PT":
			MassAttenuationPT muMassPT = new MassAttenuationPT();
			theMuMass = muMassPT.getMuMass(theMeV, muMassType);
			break;
		case "AU":
			MassAttenuationAU muMassAU = new MassAttenuationAU();
			theMuMass = muMassAU.getMuMass(theMeV, muMassType);
			break;
		case "HG":
			MassAttenuationHG muMassHG = new MassAttenuationHG();
			theMuMass = muMassHG.getMuMass(theMeV, muMassType);
			break;
		case "TL":
			MassAttenuationTL muMassTL = new MassAttenuationTL();
			theMuMass = muMassTL.getMuMass(theMeV, muMassType);
			break;
		case "PB":
			MassAttenuationPB muMassPB = new MassAttenuationPB();
			theMuMass = muMassPB.getMuMass(theMeV, muMassType);
			break;
		case "BI":
			MassAttenuationBI muMassBI = new MassAttenuationBI();
			theMuMass = muMassBI.getMuMass(theMeV, muMassType);
			break;
		case "PO":
			MassAttenuationPO muMassPO = new MassAttenuationPO();
			theMuMass = muMassPO.getMuMass(theMeV, muMassType);
			break;
		case "AT":
			MassAttenuationAT muMassAT = new MassAttenuationAT();
			theMuMass = muMassAT.getMuMass(theMeV, muMassType);
			break;
		case "RN":
			MassAttenuationRN muMassRN = new MassAttenuationRN();
			theMuMass = muMassRN.getMuMass(theMeV, muMassType);
			break;
		case "FR":
			MassAttenuationFR muMassFR = new MassAttenuationFR();
			theMuMass = muMassFR.getMuMass(theMeV, muMassType);
			break;
		case "RA":
			MassAttenuationRA muMassRA = new MassAttenuationRA();
			theMuMass = muMassRA.getMuMass(theMeV, muMassType);
			break;
		case "AC":
			MassAttenuationAC muMassAC = new MassAttenuationAC();
			theMuMass = muMassAC.getMuMass(theMeV, muMassType);
			break;
		case "TH":
			MassAttenuationTH muMassTH = new MassAttenuationTH();
			theMuMass = muMassTH.getMuMass(theMeV, muMassType);
			break;
		case "PA":
			MassAttenuationPA muMassPA = new MassAttenuationPA();
			theMuMass = muMassPA.getMuMass(theMeV, muMassType);
			break;
		case "U":
			MassAttenuationU muMassU = new MassAttenuationU();
			theMuMass = muMassU.getMuMass(theMeV, muMassType);
			break;
		case "NP":
			MassAttenuationNP muMassNP = new MassAttenuationNP();
			theMuMass = muMassNP.getMuMass(theMeV, muMassType);
			break;
		case "PU":
			MassAttenuationPU muMassPU = new MassAttenuationPU();
			theMuMass = muMassPU.getMuMass(theMeV, muMassType);
			break;
		case "AM":
			MassAttenuationAM muMassAM = new MassAttenuationAM();
			theMuMass = muMassAM.getMuMass(theMeV, muMassType);
			break;
		case "CM":
			MassAttenuationCM muMassCM = new MassAttenuationCM();
			theMuMass = muMassCM.getMuMass(theMeV, muMassType);
			break;
		case "BK":
			MassAttenuationBK muMassBK = new MassAttenuationBK();
			theMuMass = muMassBK.getMuMass(theMeV, muMassType);
			break;
		case "CF":
			MassAttenuationCF muMassCF = new MassAttenuationCF();
			theMuMass = muMassCF.getMuMass(theMeV, muMassType);
			break;
		case "ES":
			MassAttenuationES muMassES = new MassAttenuationES();
			theMuMass = muMassES.getMuMass(theMeV, muMassType);
			break;
		case "FM":
			MassAttenuationFM muMassFM = new MassAttenuationFM();
			theMuMass = muMassFM.getMuMass(theMeV, muMassType);
			break;
		default:
			theMuMass = 0.0;
			break;			
		}
		return theMuMass;		
	}
}
