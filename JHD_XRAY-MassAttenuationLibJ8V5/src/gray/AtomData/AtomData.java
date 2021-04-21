package gray.AtomData;

/*
 * The AtomData class holds the name ,symbol, density and atomic weight data.
 * The setSymbol method sets the current atom and looks-up its associated data.
 * Null is returned if the atomSymbol is not found in the atomSymbol array.
 * 
 * The setCount method sets the number of atoms and is used mainly by the Formulas methods.
 * The remaining atom properties are read only.
 * 
 *  The getAtomSymbols method returns the array of atom symbols and is useful for constructing user dialogs etc.
 *  Same for getAtomNames, getAtomGmPerCC, and getAtomGmPerMol.
 *  
 */

/**
 * A class for retrieving data about an atom in a formula. Reference "The Elements", Theodore Gray, Black Dog and Leventhal, 2007 ISBN-13:978-1-60376-213-7
 * @author John H Dunsmuir
 */
public class AtomData {


	final static String[] atomSymbol = {"H", "HE", "LI", "BE", "B", "C", "N", "O", "F", "NE", "NA", 
			"MG", "AL", "SI", "P", "S", "CL", "AR", "K", "CA", "SC","TI", "V", "CR", "MN", "FE", "CO", 
			"NI", "CU", "ZN", "GA", "GE", "AS", "SE", "BR", "KR", "RB", "SR", "Y", "ZR", "NB", "MO", 
			"TC","RU", "RH", "PD", "AG", "CD", "IN", "SN", "SB", "TE", "I", "XE", "CS", "BA", "LA", 
			"CE", "PR", "ND", "PM", "SM", "EU", "GD", "TB","DY", "HO", "ER", "TM", "YB", "LU", "HF", 
			"TA", "W", "RE", "OS", "IR", "PT", "AU", "HG", "TL", "PB", "BI", "PO", "AT", "RN", "FR", 
			"RA", "AC", "TH", "PA", "U", "NP", "PU", "AM", "CM", "BK", "CF", "ES", "FM"};

	final static String[] atomName = {"Hydrogen", "Helium", "Lithium", "Beryllium", "Boron", "Carbon", 
			"Nitrogen", "Oxygen", "Fluorine", "Neon", "Sodium", "Magnesium", "Aluminum", "Silicon", 
			"Phosphorus", "Sulfur", "Chlorine", "Argon", "Potassium", "Calcium", "Scandium", "Titanium", 
			"Vanadium", "Chromium", "Manganese", "Iron", "Cobalt", "Nickel", "Copper", "Zinc", "Gallium", 
			"Germanium", "Arsenic", "Selenium", "Bromine", "Krypton", "Rubidium", "Strontium", "Yttrium", 
			"Zirconium", "Niobium", "Molybdenum", "Technetium", "Ruthenuim", "Rhodium", "Palladium", 
			"Silver", "Cadmium", "Indium", "Tin", "Antimony", "Telurium", "Iodine", "Xenon", "Cesium", 
			"Barium", "Lanthanum", "Cerium", "Praseodymium", "Neodymium", "Promethium", "Samarium", "Europium", 
			"Gadolinium", "Terbium", "Dysprosium", "Holmium", "Erbium", "Thulium", "Ytterbium", "Lutetium", 
			"Hafnium", "Tantalum", "Tungsten", "Rhenium", "Osmium", "Iridium", "Platinum", "Gold", "Mercury", 
			"Thallium", "Lead", "Bismuth", "Polonium", "Astatine", "Radon", "Francium", "Radium", "Actinium", 
			"Thorium", "Protactinium", "Uranium", "Neptunium", "Plutonium", "Americium", "Curium", "Berkelium", 
			"Californium", "Einsteinium", "Fermium"};

	final static double[] atomGmPerMol	= {1.00794, 4.0026, 6.941, 9.01218, 10.8111, 12.0107, 14.0067, 15.9994, 
			18.9984, 20.1797, 22.98977, 24.305, 26.98154, 28.0855, 30.97376, 32.065, 35.453, 39.948, 
			39.0983, 40.078, 44.95591, 47.867, 50.9415, 51.9961, 54.93805, 55.845, 58.9332, 58.6934, 
			63.546, 65.409, 69.723, 72.64, 74.9216, 78.96, 79.904, 83.798, 85.4678, 87.62, 88.90585, 
			91.224, 92.90638, 95.94, 98, 101.07, 102.9055, 106.42, 107.8682, 112.411, 114.818, 118.71, 
			121.76, 127.6, 126.90447, 131.293, 132.90545, 137.327, 138.9055, 140.116, 140.90765, 144.24, 
			145, 150.36, 151.964, 157.25, 158.92534, 162.5, 164.93032, 167.259, 168.93421, 173.04, 174.967, 
			178.49, 180.9479, 183.84, 186.207, 190.23, 192.217, 195.078, 196.96655, 200.59, 204.3833, 207.2, 
			208.98038, 209, 210, 222, 223, 226, 227, 232.0381, 231.03588, 238.02891, 
			237, 244, 243, 247, 247, 251, 252, 257};

	final static double[] atomGmPerCC = {0.0001,0.0002,0.5350,1.8480,2.4600,2.2600,0.0013,0.0014,0.0017,0.0009,0.9680,
			1.7380,2.7000,2.3300,1.8230,1.9600,0.0032,0.0018,0.8560,1.5500,2.9850,4.5070,6.1100,7.1400,7.4700,7.8740,
			8.9000,8.9080,8.9200,7.1400,5.9040,5.3230,5.7270,4.8190,3.1200,0.0037,1.5320,2.6300,4.4720,6.5110,8.5700,
			10.2800,11.5000,12.3700,12.4500,12.0230,10.4900,8.6500,7.3100,7.3100,6.6970,6.2400,4.9400,0.0059,1.8790,
			3.5100,6.1460,6.6890,6.6400,7.0100,7.2640,7.3530,5.2440,7.9010,8.2190,8.5510,8.7950,9.0660,9.3210,6.5700,
			9.8410,13.3100,16.6500,19.2500,21.0200,22.5900,22.5600,21.0900,19.3000,13.5340,11.8500,11.3400,9.7800,9.1960,
			-1.0000,0.0097,-1.0000,5.0000,10.0700,11.7240,15.3700,19.0500,20.4500,19.8160,-1.0000,13.5100,14.7800,15.1000,
			-1.0000,-1.0000};


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

	private String symbol,name;
	private double weight;
	private double count;
	private int number;
	
	//***********************************************************************

	protected int getAtomSymbolIndex(String theAtomSymbol)
	{
		int i, symbolIndex = -1;
		
		for(i=0;i< atomSymbol.length;i++)
		{				
			if(atomSymbol[i].equalsIgnoreCase(theAtomSymbol))
			{
				symbolIndex =  i;
				break;
			}
		}		
		return symbolIndex;
	}

	//***********************************************************************

	/**
	 * Initializes tabulated data for an atom, call setSymbol prior to calling getName,getCount etc.
	 * @param symbol The symbol for an atom e.g. "AU" for gold
	 * @return boolean true if atom symbol is recognized, false if incorrect atom symbol
	 */
	public boolean setAtomSymbol(String symbol) {
		int i = getAtomSymbolIndex(symbol);
		if(i>=0 && i < atomName.length ) {
			this.symbol = symbol;
			this.name = atomName[i];
			this.weight = atomGmPerMol[i];
			this.number = i+1; 
			return true;
		}
		else return false;
	}

	//***********************************************************************
	/**
	 * Sets the number of atoms in a chemical formula
	 * @param count Sets the number of atoms in a chemical formula
	 */
	public void setAtomCount(double count) {
		this.count = count;
	}

	//***********************************************************************
	/**
	 * Returns list of recognized atom symbols in atomic number order.
	 * @return The array of recognized atom symbols
	 */
	public String[] getAtomSymbols() {
		return atomSymbol;
	}

	//***********************************************************************
	/**
	 * Returns list of tabulated atom full names in atomic number order.
	 * @return The array of recognized atom names
	 */
	public String[] getAtomNames() {
		return atomName;
	}

	//***********************************************************************
	/**
	 * Returns list of tabulated atomic weights in atomic number order.
	 * @return The array of atomic weights
	 */
	public double[] getAtomsGmPerMol() {
		return atomGmPerMol;
	}

	//***********************************************************************
	/**
	 * Returns list of tabulated densities in atomic number order.
	 * @return The array of atom STP densities
	 */
	public double[] getAtomsGmPerCC() {
		return atomGmPerCC;
	}

	//***********************************************************************
	
	/**
	 * Returns the atom selected by setSymbol
	 * @return The current atom's symbol
	 */
	public String getAtomSymbol() {
		return symbol;
	}

	//***********************************************************************
	/**
	 * Returns the full name of the atom selected by setSymbol
	 * @return The current atom's full name
	 */
	public String getAtomName() {
		return name;
	}

	//***********************************************************************
	/**
	 * Returns the count of the atom selected by setSymbol
	 * @return The current atom's count
	 */
	public double getAtomCount() {
		return count;
	}

	//***********************************************************************
	/**
	 * Returns the atomic weight of the atom selected by setSymbol
	 * @return The current atom's atomic weight in gm/mol
	 */
	public double getAtomWeight() {
		return weight;
	}

	//***********************************************************************

	/**
	 * Returns the atomic weight of theAtom
	 * @param theAtom The symbol for the atom, e.g. "AU" for gold
	 * @return The requested atom's atomic weight in gm/mol, -1 if incorrect atom symbol
	 */
	public double getAtomWeight(String theAtom) {
		if(setAtomSymbol(theAtom))
		{
			return getAtomWeight();
		}
		else
		{
			return -1;	
		}
	}
	
	//***********************************************************************
	
	/**
	 * Returns the atomic number of theAtom
	 * @param theAtom The symbol for the atom, e.g. "AU" for gold
	 * @return The atomic number Z of theAtom
	 */
	public int getAtomNum(String theAtom) {

		int atmIndex = getAtomSymbolIndex(theAtom);
		if(atmIndex < atomSymbol.length)
		{
			return atmIndex+1;
		}
		else
		{
			return -1;	
		}
	}


	//***********************************************************************
	
	/**
	 * Returns the atomic number of the atom selected by setSymbol
	 * @return The current atom's atomic number
	 */
	public int getAtomNumber() {
		return number;
	}

	//***********************************************************************
	
}
