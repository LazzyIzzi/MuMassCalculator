package jhd.MuMass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import jhd.Interpolators.CubicSplineInterpolator;
import jhd.Interpolators.Interpolation;
import jhd.Interpolators.XcomPairInterpolator;

/**A class for calculating the x-ray attenuation coefficients
 * using NIST XCOM attenuation data.
 */

public class XrayCalculator {
	
	/**Nested class for containing data about atoms
	 */
	class AtomData {
		@SuppressWarnings("unused")
		private String symbol, name;
		private double atmWeight;
		private double count;
		@SuppressWarnings("unused")
		private double gmPerCC;
		@SuppressWarnings("unused")
		private int number;
	}
		

	AtomInterface theInterface;

	protected Map<String, Supplier<AtomInterface>> muMassMap = Mapper.getMuMassMap();
	
	private final String[] atomNames = { "Hydrogen", "Helium", "Lithium", "Beryllium", "Boron", "Carbon", "Nitrogen",
			"Oxygen", "Fluorine", "Neon", "Sodium", "Magnesium", "Aluminum", "Silicon", "Phosphorus", "Sulfur",
			"Chlorine", "Argon", "Potassium", "Calcium", "Scandium", "Titanium", "Vanadium", "Chromium", "Manganese",
			"Iron", "Cobalt", "Nickel", "Copper", "Zinc", "Gallium", "Germanium", "Arsenic", "Selenium", "Bromine",
			"Krypton", "Rubidium", "Strontium", "Yttrium", "Zirconium", "Niobium", "Molybdenum", "Technetium",
			"Ruthenuim", "Rhodium", "Palladium", "Silver", "Cadmium", "Indium", "Tin", "Antimony", "Telurium", "Iodine",
			"Xenon", "Cesium", "Barium", "Lanthanum", "Cerium", "Praseodymium", "Neodymium", "Promethium", "Samarium",
			"Europium", "Gadolinium", "Terbium", "Dysprosium", "Holmium", "Erbium", "Thulium", "Ytterbium", "Lutetium",
			"Hafnium", "Tantalum", "Tungsten", "Rhenium", "Osmium", "Iridium", "Platinum", "Gold", "Mercury",
			"Thallium", "Lead", "Bismuth", "Polonium", "Astatine", "Radon", "Francium", "Radium", "Actinium", "Thorium",
			"Protactinium", "Uranium", "Neptunium", "Plutonium", "Americium", "Curium", "Berkelium", "Californium",
			"Einsteinium", "Fermium" };

	private final String[] atomSymbols = { "H", "HE", "LI", "BE", "B", "C", "N", "O", "F", "NE", "NA", "MG", "AL", "SI",
			"P", "S", "CL", "AR", "K", "CA", "SC", "TI", "V", "CR", "MN", "FE", "CO", "NI", "CU", "ZN", "GA", "GE",
			"AS", "SE", "BR", "KR", "RB", "SR", "Y", "ZR", "NB", "MO", "TC", "RU", "RH", "PD", "AG", "CD", "IN", "SN",
			"SB", "TE", "I", "XE", "CS", "BA", "LA", "CE", "PR", "ND", "PM", "SM", "EU", "GD", "TB", "DY", "HO", "ER",
			"TM", "YB", "LU", "HF", "TA", "W", "RE", "OS", "IR", "PT", "AU", "HG", "TL", "PB", "BI", "PO", "AT", "RN",
			"FR", "RA", "AC", "TH", "PA", "U", "NP", "PU", "AM", "CM", "BK", "CF", "ES", "FM" };

	private final String[] atomEdgeNames = { "K", "L1", "L2", "L3", "M1", "M2", "M3", "M4", "M5", "N1", "N2" };

	private final String[] muMassTypes = { "TotAttn", "Raleigh", "Compton", "PhotoElectric", "PairProdN", "PairProdE" };


	public XrayCalculator() {
	}

	/**
	 * @return an array of absorption edge names
	 */
	public String[] getAbsEdgeNames() {
		return atomEdgeNames;
	}

	/**
	 * @param theAtom The Symbol e.g "CA" for Calcium
	 * @param theEdge "K", "L1", "L2", "L3", "M1", "M2", "M3","M4","M5", "N1", "N2"
	 * @return the absorption edge energy MeV
	 */
	public double getAtomAbsEdge(String theAtom, String theEdge) {
		theInterface = muMassMap.get(theAtom.toUpperCase()).get();
		return theInterface.getAtomAbsEdge(theEdge);
	}
	
	/**
	 * @param edgeVal the energy of the absorption edge
	 * @param mevArray the energy array, must be in ascending order
	 * @return the index (position) of the edge, or 
	 * the position where the edge would be inserted, or 0 if the edge is not found,
	 */
	public int getAtomAbsEdgeIndex(double edgeVal, double[] mevArray) {
		//absorption edge jumps are at the edge value + a small increment
		//e.g. Sodium K edge 0.001072,0.0010721
		int index = 0;
		for(int i=0;i<mevArray.length;i++) {
			if(mevArray[i] > edgeVal) {
				index = i;
				break;
			}
		}
		return index;
	}
	
	
	/**
	 * @param theAtom The Symbol e.g "CA" for Calcium
	 * @param theEdgeName "K", "L1", "L2", "L3", "M1", "M2", "M3","M4","M5", "N1", "N2"
	 * @return the energy of the atom's selected absorption
	 */
	public double getAtomAbsEdgeValue(String theAtom, String theEdgeName) {
		theInterface = muMassMap.get(theAtom.toUpperCase()).get();
		return theInterface.getAtomAbsEdgeValue(theEdgeName);
		
	}
	
	/**
	 * @param theAtom The Symbol e.g "CA" for Calcium
	 * @return an array of the atom's absorption edges
	 */
	public double[] getAtomAbsEdgeValues(String theAtom) {
		theInterface = muMassMap.get(theAtom.toUpperCase()).get();
		return theInterface.getAtomAbsEdgeValues();		
	}

	
	/**
	 * @param theAtom The Symbol e.g "CA" for Calcium
	 * @return the density of the atom in its common form at stp.
	 */
	public double getAtomGmPerCC(String theAtom) {
		theInterface = muMassMap.get(theAtom.toUpperCase()).get();
		return theInterface.getAtomGmPerCC();
	}


	/**
	 * @param theAtom The Symbol e.g "CA" for Calcium
	 * @return the atomic weight in grams/mol
	 */
	public double getAtomGmPerMol(String theAtom) {
		theInterface = muMassMap.get(theAtom.toUpperCase()).get();
		return theInterface.getAtomGmPerMol();
	}

	/**
	 * @param theAtom The Symbol e.g "CA" for Calcium
	 * @param theMeV  find muMass as this energy
	 * @param muMassType i.e. "TotAttn", "Raleigh", "Compton",
	 *         "PhotoElectric", "PairProdN", "PairProdE"
	 * @return the interpolated muMass at the requested MeV
	 */
	public double getAtomMuMass(String theAtom, double theMeV, String muMassType) {
		double newMuMass = 0;
		double[] mevArray, muMassArray;
		theInterface = muMassMap.get(theAtom.toUpperCase()).get();
		muMassArray = theInterface.getAtomMuMassValues(muMassType);
		mevArray = theInterface.getAtomMevValues();
		double kEdge = getAtomAbsEdgeValue(theAtom, "K");
		int index = getAtomAbsEdgeIndex(kEdge,mevArray);

		// Report 0 if out of range
		if (theMeV > mevArray[mevArray.length - 1] || theMeV < mevArray[0]) {
			newMuMass = 0;
		} else {
			switch (muMassType) {
			// log-log below K edge, Cubic above K edge
			case "TotAttn":
			case "PhotoElectric":
				String s = " at K edge";
				try {
					if (kEdge == 0) { // the atom's kEdge is below .001 MeV
						newMuMass = logLogCubicInterpolate(mevArray, muMassArray, theMeV);
					} else if (theMeV == kEdge) {
						newMuMass = muMassArray[index-1];
					} else if (theMeV < kEdge) {// below Kedge
						s = "below kEdge";
						mevArray = Arrays.copyOfRange(mevArray, 0, index);
						muMassArray = Arrays.copyOfRange(muMassArray, 0, index);
						newMuMass = logLogInterpolate(mevArray, muMassArray, theMeV);
					} else if (theMeV > kEdge) {// above Kedge
						s = "above kEdge";
						mevArray = Arrays.copyOfRange(mevArray, index-1, mevArray.length);
						muMassArray = Arrays.copyOfRange(muMassArray, index-1, muMassArray.length);
						newMuMass = logLogCubicInterpolate(mevArray, muMassArray, theMeV);
					}
				} catch (Exception e) {
					System.out.println(
							s + " theAtom=" + theAtom + " kEdge=" + kEdge + " theMeV=" + theMeV + " index=" + index);
					newMuMass = 0;
					e.printStackTrace();
				}
				break;
			case "Raleigh":
			case "Compton":
				newMuMass = logLogCubicInterpolate(mevArray, muMassArray, theMeV);
				break;

			case "PairProdN":
		    	//prune the leading zeros from the sigma arrays
				index = getPairProdIndex(muMassArray);
		    	muMassArray = Arrays.copyOfRange(muMassArray, index, muMassArray.length);
		    	mevArray = Arrays.copyOfRange(mevArray, index, mevArray.length);
				double thresholdMeV = 1.022007;
		    	if(theMeV<mevArray[0]) {
		    		newMuMass = XcomPairInterpolator.betweenThresholdAndFirstGrid(theMeV, thresholdMeV, mevArray, muMassArray);
		    	}else {
					newMuMass = pairProdInterpolate(mevArray, muMassArray, theMeV, thresholdMeV);		    		
		    	}
				break;
			case "PairProdE":
				thresholdMeV = 2.044014;
		    	//prune the leading zeros from the sigma arrays
				index = getPairProdIndex(muMassArray);
		    	muMassArray = Arrays.copyOfRange(muMassArray, index, muMassArray.length);
		    	mevArray = Arrays.copyOfRange(mevArray, index, mevArray.length);
		    	if(theMeV<mevArray[0]) {
		    		newMuMass = XcomPairInterpolator.betweenThresholdAndFirstGrid(theMeV, thresholdMeV, mevArray, muMassArray);
		    	}else {
					newMuMass = pairProdInterpolate(mevArray, muMassArray, theMeV, thresholdMeV);		    		
		    	}
				break;
			}

		}
		return newMuMass;
	}
	

	/**
	 * @param theAtom    The Symbol e.g "CA" for Calcium
	 * @param muMassType "TotAttn", "Raleigh", "Compton", "PhotoElectric",
	 *                   "PairProdN", "PairProdE"
	 * @return the array of tabulated cm2/gram cross sections for the Atom
	 */
	public double[] getAtomMuMassArray(String theAtom, String muMassType) {
		theInterface = muMassMap.get(theAtom.toUpperCase()).get();
		return theInterface.getAtomMuMassValues(muMassType);
	}

	/**
	 * @param theAtom The Symbol e.g "CA" for Calcium
	 * @return the full name of the atom
	 */
	public String getAtomName(String theAtom) {
		theInterface = muMassMap.get(theAtom.toUpperCase()).get();
		return theInterface.getAtomName();
	}

	/**
	 * @return an array of atom names in atomic number order
	 */
	public String[] getAtomNames() {
		return atomNames;
	}

	/**
	 * @param theAtom The Symbol e.g "CA" for Calcium
	 * @return the atomic number
	 */
	public int getAtomNumber(String theAtom) {
		theInterface = muMassMap.get(theAtom.toUpperCase()).get();
		return theInterface.getAtomNumber();
	}

	/**
	 * @return an array of atom symbols, e.g. CA for Calcium, in atomic number order
	 */
	public String[] getAtomSymbols() {
		return atomSymbols;
	}
	
//	/**
//	 * @param theFormula An atom:count string representation of a chemical formula e.g.
//	 *                   Ca:1:C:1:O:3 for CaCO3
//	 * @return an array of edge energies from high to low for each atom in the formula
//	 */
//	public double[][] getFormulaAbsEdgeValues(String theFormula){
//		double[][] formulaEdges = null;
//		ArrayList<AtomData> atomData = createFormulaList(theFormula);
//		if(atomData!=null) {
//			formulaEdges = new double[atomData.size()][];
//			for(int i=0;i<atomData.size();i++) {
//				formulaEdges[i] = getAtomAbsEdgeValues(atomData.get(i).symbol);
//			}
//		}
//		return formulaEdges;			
//	}

	/**
	 * @param theFormula An atom:count string representation of a chemical formula e.g.
	 *                   Ca:1:C:1:O:3 for CaCO3
	 * @return an array atom symbols in the formula, null if formula is not valid
	 */
	public String[] getFormulaAtoms(String theFormula) {
		String[] formulaAtoms = null;
		ArrayList<AtomData> atomData = createFormulaList(theFormula);
		if (atomData != null) {
			formulaAtoms = new String[atomData.size()];
			for (int i = 0; i < atomData.size(); i++) {
				formulaAtoms[i] = atomData.get(i).symbol;
			}
		}
		return formulaAtoms;
	}

	/**
	 * @param formula An atom:count string representation of a chemical formula e.g.
	 *                   Ca:1:C:1:O:3 for CaCO3
	 * @return an ascending order array of tabulated energies for the atoms in the formula
	 */
	public double[] getFormulaMevArray(String formula) {
		double[] formulaMevArray = null;

		ArrayList<Double> formulaMevArrayList = new ArrayList<>();
		ArrayList<AtomData> atomData = createFormulaList(formula);
		if (atomData != null) {
			// begin with the first atom
			double[] mevArray; 
			// add unique values from additional atom MeV arrays
			for (int i = 0; i < atomData.size(); i++) {
				mevArray = getAtomMevArray(atomData.get(i).symbol);
				for (int j = 0; j < mevArray.length; j++) {
					if (!formulaMevArrayList.contains(mevArray[j]))
						formulaMevArrayList.add(mevArray[j]);
				}
			}			
			// copy the values from Double to double
			formulaMevArray = new double[formulaMevArrayList.size()];
			for (int i = 0; i < formulaMevArrayList.size(); i++) {
				formulaMevArray[i] = formulaMevArrayList.get(i);
			}
			// put in ascending order
			Arrays.sort(formulaMevArray);
		}
		return formulaMevArray;
	}
	
	/**
	 * @param formula An atom:count string representation of a chemical formula e.g.
	 *                   Ca:1:C:1:O:3 for CaCO3
	 * @return an ascending order array of tabulated energies for the atoms in the formula
	 */
	public double[] getFormulaMevArray(String formula, double minMeV, double maxMeV) {
		double[] formulaMevArray = getFormulaMevArray(formula);
		ArrayList<Double> formulaMevArrayList = new ArrayList<>();
		for (double mev : formulaMevArray)
			if (mev >= minMeV && mev < maxMeV) {
				formulaMevArrayList.add(mev);
			}
		for (int i = 0; i < formulaMevArrayList.size(); i++) {
			formulaMevArray[i] = formulaMevArrayList.get(i);
		}
		return formulaMevArray;
	}
	
	/**
	 * Returns the mass attenuation coefficient for atoms and formulas
	 * @param theFormula The formula consists of an atom symbol or a list of atom symbols and their count all separated by ":",
	 * e.g. CaCO3 is represented by the string "Ca:1:C:1:O:3"
	 * @param theMeV The photon energy in MeV between 1KeV to 100GeV
	 * @param muMassType "TotAttn", "Raleigh", "Compton", "PhotoElectric", "PairProdN", "PairProdE"
	 * @return The cross-section in cm2/gm at the photon energy, -1 if incorrect formula
	 */
	public double getFormulaMuMass(String theFormula, double theMeV, String muMassType )
	{
		double muMass = 0;
		double atomMuMass =0;
		double formulaWt = 0;

		ArrayList<AtomData> myAtomData = createFormulaList(theFormula);

		if(myAtomData!=null)
		{
			formulaWt = getFormulaWeight(theFormula);
			for( AtomData atmData : myAtomData)
			{
				atomMuMass = getAtomMuMass(atmData.symbol, theMeV, muMassType);
				muMass += (atomMuMass * atmData.atmWeight * atmData.count)/ formulaWt;
			}
			return muMass;
		}
		else
		{		
			return -1;
		}
	}

	
	/**
	 * @param formula An atom:count string representation of a chemical formula e.g.
	 *                   Ca:1:C:1:O:3 for CaCO3
	 * @param muMassType "TotAttn", "Raleigh", "Compton", "PhotoElectric", "PairProdN", "PairProdE"
	 * @return The list of attenuation coefficients for the formula at each tabulated atom's tabulated energy 
	 */
	public double[] getFormulaMuMassArray(String formula, String muMassType) {
		double[] mevArray = getFormulaMevArray(formula);
		double[] muMassArray = new double[mevArray.length];
		for(int i=0;i<mevArray.length;i++ ) {
			muMassArray[i] = getFormulaMuMass(formula,mevArray[i],muMassType);
		}
		return muMassArray;
	}


	/**
	 * @param formula An atom:count string representation of a chemical formula e.g.
	 *                   Ca:1:C:1:O:3 for CaCO3
	 * @param muMassType "TotAttn", "Raleigh", "Compton", "PhotoElectric", "PairProdN", "PairProdE"
	 * @param mevArray an array of energies to compute the formula muMass
	 * @return The list of attenuation coefficients for the formula at each energy 
	 */
	public double[] getFormulaMuMassArray(String formula, String muMassType, double[]mevArray) {
		//double[] mevArray = getFormulaMevArray(formula);
		double[] muMassArray = new double[mevArray.length];
		for(int i=0;i<mevArray.length;i++ ) {
			muMassArray[i] = getFormulaMuMass(formula,mevArray[i],muMassType);
		}
		return muMassArray;
	}

	/**
	 * Returns the formula weight of the formula
	 * 
	 * @param theFormula An atom:count string representation of a chemical formula e.g.
	 *                   Ca:1:C:1:O:3 for CaCO3
	 * @return the formula weight in gm/mol, -1 if incorrect formula
	 */
	public double getFormulaWeight(String theFormula) {
		double formulaWt = 0;
		ArrayList<AtomData> myFL;

		myFL = createFormulaList(theFormula);

		if (myFL != null) {
			for (AtomData fl : myFL) {
				formulaWt += fl.atmWeight * fl.count;
			}
			return formulaWt;
		} else {
			return -1;
		}
	}

	/**
	 * @param theAtom The Symbol e.g "CA" for Calcium
	 * @return array of tabulated energies for the Atom
	 */
	public double[] getAtomMevArray(String theAtom) {
		theInterface = muMassMap.get(theAtom.toUpperCase()).get();
		return theInterface.getAtomMevValues();
	}
	
	/**
	 * @param theAtom The Symbol e.g "CA" for Calcium
	 * @param minMeV  the lower energy bound
	 * @param maxMeV  the upper energy bound
	 * @return the array of tabulated energies for the Atom between the bounds
	 */
	public double[] getMev(String theAtom, double minMeV, double maxMeV) {
		double[] mev = getAtomMevArray(theAtom);
		int min = getIndex(mev, minMeV);
		int max = getIndex(mev, maxMeV);
		if (min > max) {
			int temp = min;
			min = max;
			max = temp;
		}
		if (min < 0)
			min = 0;
		if (max < 0)
			max = mev.length;
		return Arrays.copyOfRange(mev, min, max);
	}
	
	/**
	 * Returns the photon energy(s) that will produce the observed linear attenuation coefficient for the formula
	 * @param formula The formula consists of an atom symbol or a list of atom symbols and their count all separated by ":",
	 * e.g. CaCO3 is represented by the string "Ca:1:C:1:O:3"
	 * @param linearCoeff The experimentally observed linear attenuation coefficient in cm-1
	 * @param gmPerCC The experimentally observed density in gm/cm3
	 * @param muMassType "TotAttn", "Raleigh", "Compton", "PhotoElectric", "PairProdN", "PairProdE"
	 * @return An array of photon energies that satisfy the observed linear coefficient, null if no solutions.
	 */
	public double[] getMeVfromMuLin(String formula, double linearCoeff, double gmPerCC, String muMassType)
	{
		int i,solnCnt;
		double muMass1, muMass2, mev1, mev2,result;
		double[] muMassList,mevSoln;
		List<Double> mevList = new ArrayList<>();

		//Get tabulated MeV
		double[] mevArr  = getFormulaMevArray(formula);
		if(mevArr==null) return null;

		//Compute the muMass
		double muMass = linearCoeff/gmPerCC;
		if(muMass ==0) return null;
		
	    //Convert the energies to mass attenuation
		muMassList = new double[mevArr.length];
		for(i=0;i<muMassList.length;i++)
		{
			muMassList[i]= getFormulaMuMass(formula,mevArr[i],muMassType);
		}
		
		solnCnt=0;
		for(i=0;i<mevArr.length-1;i++)
		{
			if(Math.abs(mevArr[i + 1] - mevArr[i]) > 0.001) // Then 'skip edges
			{
				if( muMass < muMassList[i] && muMass > muMassList[i + 1] ||
						muMass < muMassList[i + 1] && muMass > muMassList[i])
				{
					muMass1 = muMassList[i];
					muMass2 = muMassList[i + 1];
					mev1 = mevArr[i];
					mev2 = mevArr[i + 1];

					result = Interpolation.logTerp(muMass1, muMass2, muMass, mev1, mev2);
					mevList.add(result);
					solnCnt++;
				}
			}
		}
		if(solnCnt>0)
		{
			//Allocate results array
			mevSoln = new double[mevList.size()];
			for(i=0;i<mevList.size();i++)
			{			
					mevSoln[i] = mevList.get(i);
			}
			return mevSoln;	//ta daaa!!					
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Returns the photon energy(s) that will produce the observed linear attenuation coefficient ratio of two formulas
	 * @param formula1 The formula for the first component e.g. "AU" for gold or "Ca:1:C:1:O:3" for Calcite CaCO3
	 * @param linearCoeff1 The experimentally observed linear attenuation coefficient for the first component in cm-1
	 * @param density1 The experimentally observed density for the first component in gm/cm3
	 * @param formula2  same as formula1
	 * @param linearCoeff2 same as linearCoeff1
	 * @param density2 same as density1
	 * @param muMassType "TotAttn", "Raleigh", "Compton", "PhotoElectric", "PairProdN", "PairProdE"
	 * @return An array of photon energies that satisfy the observed linear coefficient ratio, null if no solutions.
	 * Use with caution!! best results when comparing high average Z component to low average Z
	 */
	public double[] getMeVfromMuLinRatio(String formula1, double linearCoeff1, double density1,
			String formula2, double linearCoeff2, double density2, String muMassType)
	{
        double mu1,mu2,muR1,muR2,minMeV,maxMeV,muMass1,muMass2,testMuRatio,result;
        double[] mevArr,muMassRatio,mevSoln;
        int i,solnCnt;
        
        if(!formula1.contains(":"))      //add ":1" to single atoms bug fix 10-24-2022       	 
        {
        	formula1 = formula1 +":1";
        }
        if(!formula2.contains(":"))
        {
        	formula2 = formula2 +":1";
        }
		
        //Combine formula atoms
		String formula = formula1 + ":" + formula2;
		
		//Get get tabulated MeV
		//mevArr = getMevArray(formula);
		mevArr = getFormulaMevArray(formula);
		if(mevArr==null) return null;
		
		//Compute the muMass Ratio
		muMass1 = linearCoeff1 / density1;
		muMass2 = linearCoeff2 / density2;
		if(muMass1==0 || muMass2==0) return null;
		testMuRatio = muMass1 / muMass2;

	    //Convert the energies to mass attenuation ratios
	    muMassRatio = new double[mevArr.length];
	    for(i=0;i<muMassRatio.length;i++)
	    {
//	        muMass1 = getMuMass(formula1, mevArr[i], muMassType);
//	        muMass2 = getMuMass(formula2, mevArr[i], muMassType);
	        muMass1 = getFormulaMuMass(formula1, mevArr[i], muMassType);
	        muMass2 = getFormulaMuMass(formula2, mevArr[i], muMassType);
	        muMassRatio[i] = muMass1 / muMass2;
	    }

	    //Find the index of the lower of the two attenuations that bracket the testMuRatio
		List<Integer> indexList = new ArrayList<>();
	    solnCnt=0;		
		for(i=0;i<mevArr.length-1;i++)
		{
	        if( Math.abs(mevArr[i + 1] - mevArr[i]) > 0.001) //Then ' skip edges
	        {
	        	if ((testMuRatio <= muMassRatio[i] && testMuRatio > muMassRatio[i + 1]) ||
	        			(testMuRatio > muMassRatio[i] && testMuRatio <= muMassRatio[i + 1]))
	        	{
	        		indexList.add(i);
	        		solnCnt++;
	        	}
	        }
		}
		// if bracketing energies were found interpolate to find result
		if(solnCnt>0)
		{
			List<Double> mevList = new ArrayList<>();
	        for(i = 0;i<indexList.size();i++)
	        {
	                minMeV = mevArr[indexList.get(i)];
	                maxMeV = mevArr[indexList.get(i) + 1];
	                
	                mu1 = getFormulaMuMass(formula1, minMeV, muMassType);
	                mu2 = getFormulaMuMass(formula2, minMeV, muMassType);
	                muR1 = mu1 / mu2;
	                
	                mu1 = getFormulaMuMass(formula1, maxMeV, muMassType);
	                mu2 = getFormulaMuMass(formula2, maxMeV, muMassType);
	                muR2 = mu1 / mu2;
	                
	                result = Interpolation.logTerp(muR1, muR2, testMuRatio, minMeV, maxMeV);                
	                mevList.add(result);
	        }

			//Allocate results array
			mevSoln = new double[mevList.size()];
			for(i=0;i<mevList.size();i++)
			{			
				mevSoln[i] = mevList.get(i);
			}
			return mevSoln;	//ta daaa!!					
		}
		else
		{
			return null;
		}
	}


	/**
	 * @param theAtom    The Symbol e.g "CA" for Calcium
	 * @param muMassType "TotAttn", "Raleigh", "Compton", "PhotoElectric",
	 *                   "PairProdN", "PairProdE"
	 * @param minMeV     the lower energy bound
	 * @param maxMeV     the upper energy bound
	 * @return the array of tabulated cm2/gram cross sections for the Atom between
	 *         the bounds
	 */
	public double[] getMuMassArray(String theAtom, String muMassType, double minMeV, double maxMeV) {
		double[] mev = getAtomMevArray(theAtom);
		int min = getIndex(mev, minMeV);
		int max = getIndex(mev, maxMeV);
		if (min > max) {
			int temp = min;
			min = max;
			max = temp;
		}
		if (min < 0)
			min = 0;
		if (max < 0)
			max = mev.length;
		double[] muMass = getAtomMuMassArray(theAtom, muMassType);
		return Arrays.copyOfRange(muMass, min, max);
	}

	/**
	 * Returns list of recognized cross section types
	 * 
	 * @return A string array of muMass Types, i.e. "TotAttn", "Raleigh", "Compton",
	 *         "PhotoElectric", "PairProdN", "PairProdE"
	 */
	public String[] getMuMassTypes() {
		return muMassTypes;
	}
	
	/**
	 * @return the version of MassAttenuation
	 */
	public String getVersion() {
		return "v2.0.0";
	}

	/**
	 * Converts observed linear attenuation coefficient to concentration.
	 * 
	 * @param linearCoeff    The experimentally observed linear attenuation
	 *                       coefficient of a known material at a "known" photon
	 *                       energy
	 * @param massCoeff      The calculated mass attenuation coefficient of the
	 *                       known material at the "known" energy
	 * @param formulaGmPerCC The density of the pure material
	 * @return The concentration in mol/m3
	 */
	public double muLinToMolPerM3(double linearCoeff, double massCoeff, double formulaGmPerCC) {
		return 1000000 * linearCoeff / massCoeff / formulaGmPerCC;
	}

	/**
	 * Estimates the output of a conventional x-ray source (reflection target) using
	 * the Kramers equation.
	 * 
	 * @param tubeVoltageKV    The excitation potential, AKA x-ray tube voltage KV
	 * @param tubeCurrentMa    The x-ray source beam current
	 * @param targetAtomSymbol The x-ray source target material, usually W Tungsten
	 * @param meV              The energy at which the tube output is to be
	 *                         estimated
	 * @return The estimated photon intensity.
	 */
	public double spectrumKramers(double tubeVoltageKV, double tubeCurrentMa, String targetAtomSymbol, double meV) {
		int targAtmNum;
		double wav, keV, wavMin;
		final double hc = 12.3984197;

		targAtmNum = getAtomNumber(targetAtomSymbol);
		keV = meV * 1000;
		wav = hc / keV;
		wavMin = hc / tubeVoltageKV;

		if (keV >= tubeVoltageKV) {
			return 0.0;
		} else {
			return tubeCurrentMa * targAtmNum * (wav / wavMin - 1) / (wav * wav);
		}
	}
	
	/**
	 * @param theSymbol An atom symbol e.g. "CA" for Calcium
	 * @return true if the formula is valid
	 */
	public boolean validateAtomSymbol(String theSymbol) {
		String[] atomSymbols = getAtomSymbols();
		int index = Arrays.binarySearch(atomSymbols, theSymbol);
		if(index >0) return true;
		else return true;
			
	}

	
	/**
	 * @param theFormula An atom:count string representation of a chemical formula e.g.
	 *                   Ca:1:C:1:O:3 for CaCO3
	 * @return true if the formula is valid
	 */
	public boolean validateFormula(String theFormula) {
		if(createFormulaList(theFormula) == null) {
			return false;
		}
		else {
			return true;
		}		
	}

	/**
	 * Returns an ArrayList of AtomData Objects.
	 * 
	 * @param theFormula An atom:count string representation of a chemical formula e.g.
	 *                   Ca:1:C:1:O:3 for CaCO3
	 * @return An ArrayList of AtomData Objects, null if incorrect formula
	 */
	private ArrayList<AtomData> createFormulaList(String theFormula) {
		
		//append a 1 to single elements
		if (theFormula.length() <= 2)
		theFormula += ":1";

		// divide the formula into atoms and counts and
		String[] formula = theFormula.split(":");
		if(formula.length % 2 !=0) {
		return null;
	}
		String[] atoms = new String[formula.length/2];
		double[] count = new double[formula.length/2];		
		try {
			for (int i = 0,j=0; i < formula.length; i += 2) {
				atoms[j] = formula[i].toUpperCase();
				//ensure that the counts are numbers
				count[j] = Double.parseDouble(formula[i + 1]);
				j++;
			}
		} catch (NumberFormatException e) {
			return null;
		}
				
		//check that the atom is listed
		for (int i = 0; i < atoms.length; i += 2) {
			atoms[i] = atoms[i].toUpperCase();
			if (Arrays.asList(atomSymbols).contains(atoms[i]) == false) {
				return null;
			}
		}
		//count must be positive
		for (int i = 0; i < count.length;i++) {			
			if (count[i]<0) {
				return null;
			}
		}
				
		// everything is OK so far
		ArrayList<AtomData> myList = new ArrayList<AtomData>();
		for (int i = 0; i < atoms.length; i++) {
			AtomData myAtmDat = new AtomData();
			myAtmDat.count = count[i];
			myAtmDat.symbol = atoms[i];
			myAtmDat.atmWeight = getAtomGmPerMol(atoms[i]);
			myAtmDat.name = getAtomName(atoms[i]);
			myAtmDat.number = getAtomNumber(atoms[i]);
			myAtmDat.gmPerCC = getAtomGmPerCC(atoms[i]);
			myList.add(myAtmDat);
		}
		return myList;
	}

	private int getIndex(double[] arr, double val) {
		int index = -1;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] > val) {
				index = i;
				break;
			}
		}
		return index;
	}

	/**
	 * @param muMassArray
	 * @return the index of the beginning of non-zero data
	 */
	private int getPairProdIndex(double[] muMassArray) {
		int index=-1;
		for(int i=0;i<muMassArray.length;i++) {
			if(muMassArray[i]!=0) {
				index = i;
				break;
			}
		}
		return index;		
	}

	private double logLogCubicInterpolate(double[] mevArray, double[] muMassArray, double theMeV) {
		double[] logMevArray, logMuMassArray;
		logMuMassArray = new double[muMassArray.length];
		logMevArray = new double[mevArray.length];

		for (int i = 0; i < mevArray.length; i++) {
			logMuMassArray[i] = Math.log(muMassArray[i]);
			logMevArray[i] = Math.log(mevArray[i]);
		}

		CubicSplineInterpolator csi = new CubicSplineInterpolator(logMevArray, logMuMassArray);
		double logNewMuMass = csi.interpolate(Math.log(theMeV));
		return Math.exp(logNewMuMass);
	}
	
	/**
	 * @param mevArray  The energy range containing theMeV
	 * @param muMassArray The muMass range containing the muMass values corresponding to the MeV range
	 * @param theMeV The energy interpolate the new muMass
	 * @return the loglog interpolated value
	 */
	private double logLogInterpolate(double[] mevArray, double[] muMassArray, double theMeV) {
		double logMeV1, logMeV2, logNewMeV, logMuMass1, logMuMass2;
		double m, diff;

		// out of range
		if (theMeV < mevArray[0] || theMeV > mevArray[mevArray.length - 1])
			return 0;

		int index = getIndex(mevArray,theMeV);

		logMeV1 = Math.log(mevArray[index - 1]);
		logMeV2 = Math.log(mevArray[index]);
		logNewMeV = Math.log(theMeV);
		logMuMass1 = Math.log(muMassArray[index - 1]);
		logMuMass2 = Math.log(muMassArray[index]);
		m = (logMeV2 - logNewMeV) / (logMeV2 - logMeV1);
		diff = logMuMass2 - logMuMass1;
		return Math.exp(logMuMass2 - m * diff);
	}

	/**A special method only for cubic spline interpolating pair production values.<br>
	 * For other cases, use the lz.Interpolators.cubicSplineInterpolator 
	 * @param mevArray  The energy range containing theMeV
	 * @param muMassArray The muMass range containing the muMass values corresponding to the MeV range
	 * @param theMeV The energy interpolate the new muMass
	 * @param thresholdMeV 1.022 for nuclear field and 2.044 for electric field
	 * @return the interpolated muMass
	 */
	private double pairProdInterpolate(double[] mevArray, double[] muMassArray, double theMeV, double thresholdMeV) {
//		int index = getPairProdIndex(muMassArray);
		double[] logMuMassArray, logMevArray;
		double factor, newMuMass;

//		// chop off the data below the threshold
//		muMassArray = Arrays.copyOfRange(muMassArray, index, muMassArray.length);
//		mevArray = Arrays.copyOfRange(mevArray, index, mevArray.length);

		// out of range
		if (theMeV < mevArray[0] || theMeV > mevArray[mevArray.length - 1]) {
			return 0;
		} else {
			logMuMassArray = new double[muMassArray.length];
			logMevArray = new double[muMassArray.length];
			for (int i = 0; i < mevArray.length; i++) {
				factor = Math.pow(1.0 - thresholdMeV / mevArray[i], 3);

				logMuMassArray[i] = Math.log(factor * muMassArray[i]);
				logMevArray[i] = Math.log(mevArray[i]);
			}
			CubicSplineInterpolator csi = new CubicSplineInterpolator(logMevArray, logMuMassArray);

			newMuMass = csi.interpolate(Math.log(theMeV));
			factor = Math.pow(1.0 - thresholdMeV / theMeV, 3);
			newMuMass = Math.exp(newMuMass);
			newMuMass = newMuMass / factor;

			return newMuMass;
		}
	}

}
