package jhd.MuMassCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import gray.AtomData.AtomData;
import jhd.Formulas.FormulaList;
import jhd.MathTools.Interpolation;
import jhd.Projection.ParallelProjectors.ParallelParams;

/*
 * https://www.nist.gov/pml/xcom-photon-cross-sections-database
 * 
 * "A web database is provided which can be used to calculate photon cross sections for scattering, 
 * photoelectric absorption and pair production, as well as total attenuation coefficients, for any element, 
 * compound or mixture (Z <= 100), at energies from 1 keV to 100 GeV."
 * 
 * Since the web database is unavailable to researchers with limited or no internet access and not practical
 * for use in computation NIST offers a FORTRAN version.
 * 
 * "For the official NIST software version
 * https://physics.nist.gov/PhysRefData/Xcom/Text/download.html
 * The compressed files contain the data files, FORTRAN-77 source code, and compiled executables (for Dos only).
 * These are not necessarily the same versions of XCOM as the one that is available on line. They are not as fully featured
 * and are not updated as regularly.
 * 1990, 1998 copyright by the U.S. Secretary of Commerce on behalf of the United States of America. All rights reserved.
 * NIST reserves the right to charge for these data in the future."
 * 
 * This package was developed initially in 2020 with Microsoft Excel using CGI to automatically download the default x-ray cross section
 * data from the NIST XCOM website. Excel macros were used to generate the data portions of the element specific java classes.
 * 
 */

/**
 * A calculate cross sections based on tabulated NIST SRD 8 (XCOM) cross-section data and related calculations.
 * @author John H Dunsmuir
 * @version 1.0 3/30/2021
 */


/**
 * Interpolate tabulated NIST SRD 8 photon cross sections for scattering, photoelectric absorption and pair production,
 * as well as total attenuation coefficients, for any element, 
 * or compound (Z= 1 to 100), at energies from 1 KeV to 100 GeV.
 */
public class MuMassCalculator extends FormulaList
{

	public static class BeamHardenParams implements java.io.Serializable
	{
		/**Constructor for this serializable parameter block*/ 
		public BeamHardenParams(){};
		/**The conventional X-ray source anode (target) material*/
		public String target;
		/**The X-ray source accelerating potential, the upper limit on the X-ray energy*/
		public double kv;
		/**The X-ray source beam current*/
		public double ma;
		/**Divide the source spectrum into nBins discrete energies*/
		public double kvInc;
		/**The lower limit on the X-ray energy*/
		public double kvMin;

		//Filter
		/**The filter material, typically Al, Cu, Sn, Mo*/
		public String filter;
		/**The filter thickness*/
		public double filterCM;
		/**The filter density*/
		public double filterGmPerCC;

		/**The image pixel size in CM. the code works in CGS units*/
		public double pixSizeCM;

		/**compound formula. Must be in Atom1:Count1:Atom2:Count2 format, for example Ca:1:C:1:O:3 for CaCO3 Calcite*/
		public String matlFormula;
		/**compound density in gm/cc*/
		public double matlGmPerCC;
		/**matl thickness(cm)*/
		public double matlCM;

		//Detector
		/**The detector scintillator  formula, Must be in Atom1:Count1:Atom2:Count2 format, For example Cs:1:I:1 for CsI Cesium Iodide*/
		public String detFormula;
		/**The detector scintillator screen or detector element thickness in CM*/
		public double detCM;
		/**The detector scintillator screen or detector element density in gm/cc*/
		public double detGmPerCC;
		
		public String plotChoice;
	}
	
	/**
	 * Returns the formula weight of the formula
	 * @param theFormula A string representation of a chemical formula e.g. Ca:1:C:1:O:3 for CaCO3 
	 * @return the formula weight in gm/mol, -1 if incorrect formula
	 */
	public double getFormulaWeight(String theFormula)
	{
		double formulaWt = 0;
		ArrayList<AtomData> myFL;

		myFL = createFormulaList(theFormula);

		if(myFL != null)
		{
			for( AtomData fl : myFL)
			{
				formulaWt += fl.getAtomWeight() * fl.getAtomCount();
			}
			return formulaWt;
		}
		else
		{
			return -1;
		}
	}

	//*********************************************************************************************************		

	
	/**
	 * Returns the mass attenuation coefficient for atoms and formulas
	 * @param theFormula The formula consists of an atom symbol or a list of atom symbols and their count all separated by ":",
	 * e.g. CaCO3 is represented by the string "Ca:1:C:1:O:3"
	 * @param theMeV The photon energy in MeV between 1KeV to 100GeV
	 * @param muMassType "TotAttn", "Raleigh", "Compton", "PhotoElectric", "PairProdN", "PairProdE". Defaults to "ToTAttn"
	 * @return The cross-section in cm2/gm at the photon energy, -1 if incorrect formula
	 */
	public double getMuMass(String theFormula, double theMeV, String muMassType )
	{
		double muMass = 0;
		double atomMuMass =0;
		double formulaWt = 0;

		FormulaList myFL = new FormulaList();
		ArrayList<AtomData> myAtomData = myFL.createFormulaList(theFormula);

		if(myAtomData!=null)
		{
			formulaWt = getFormulaWeight(theFormula);
			for( AtomData atmData : myAtomData)
			{
				atomMuMass = getAtomMuMass(atmData.getAtomSymbol(), theMeV, muMassType);
				muMass += (atomMuMass * atmData.getAtomWeight() * atmData.getAtomCount())/ formulaWt;
			}
			return muMass;
		}
		else
		{		
			return -1;
		}
	}

	//*********************************************************************************************************		

	/**
	 * Returns list of recognized cross section types
	 * @return A string array of muMass Types, i.e. "TotAttn", "Raleigh", "Compton", "PhotoElectric", "PairProdN", "PairProdE"
	 */
	public String[] getMuMassTypes()
	{
		String[] muMassTypes = {"TotAttn", "Raleigh", "Compton", "PhotoElectric", "PairProdN", "PairProdE"};
		return muMassTypes;
	}


	/**
	 * Returns the list of tabulated x-ray energies between minMev and maxMev for the formula, useful in plotting spectra with absorption edges.
	 * @param theFormula The formula consists of an atom symbol or a list of atom symbols and their count all separated by ":",
	 * e.g. CaCO3 is represented by the string "Ca:1:C:1:O:3"
	 * @param minMev The minimum energy of the returned MeV range
	 * @param maxMev The maximum energy of the returned MeV range
	 * @return A array of tabulated photon energies between mevMin and mevMax for the elements in the formula, null if incorrect formula
	 */
	public double[] getMevArray(String theFormula, double minMev,double maxMev)
	{
		int minIndex, maxIndex;

		double[] mevArr = getMevArray(theFormula);
		if(mevArr == null) return null;
		
		for(minIndex = 0; minIndex < mevArr.length;minIndex++)
		{
			if(mevArr[minIndex] > minMev ) break;
		}
		for(maxIndex = 0; maxIndex < mevArr.length;maxIndex++)
		{
			if(mevArr[maxIndex] > maxMev ) break;
		}
		return Arrays.copyOfRange(mevArr,minIndex,maxIndex);
	}

	//*********************************************************************************************************		

	/**
	 * Returns the list of tabulated x-ray energies for the formula, useful in plotting spectra with absorption edges.
	 * @param theFormula The formula consists of an atom symbol or a list of atom symbols and their count all separated by ":",
	 * e.g. CaCO3 is represented by the string "Ca:1:C:1:O:3"
	 * @return A array of all tabulated photon energies for the elements in the formula, null if incorrect formula
	 */	
	public double[] getMevArray(String theFormula)
	{
		//This is so easy to do in C or VB, there must be an easier way than this in Java
		//Arrays.concatenate() ?  Arrays.removeDuplicates() ?
		double[] dumArr;
		int formulaArrLen = 0;
		int i,j;

		FormulaList myFL = new FormulaList();
		ArrayList<AtomData> myAtomData = myFL.createFormulaList(theFormula);

		if(myAtomData!=null)
		{
			//get the total length of the atom mev arrays
			for( AtomData atmData : myAtomData)
			{
				dumArr = getAtomMevArray(atmData.getAtomSymbol());				
				formulaArrLen += dumArr.length;
			}

			//allocate an array to hold all of the photon energies
			//There is no ReDim or realloc
			double[] tempMevArray= new double[formulaArrLen];

			//Concatenate the energies into the tempMevArray			
			j=0;
			for( AtomData atmData : myAtomData)
			{
				dumArr = getAtomMevArray(atmData.getAtomSymbol());
				for(i=0;i< dumArr.length;i++)
				{
					tempMevArray[j]= dumArr[i];
					j++;
				}
			}

			//Remove duplicates from the tempMevArray			
			Arrays.sort(tempMevArray);	//finally, a cool Java method

			//Close a Java gets to dynamic resizing
			List<Double> arList = new ArrayList<>();
			arList.add(tempMevArray[0]);
			for(i=1;i<tempMevArray.length;i++)
			{
				if(tempMevArray[i]!= tempMevArray[i-1]) arList.add(tempMevArray[i]);
			}

			//copy the List to the double[] type list, there is no Java implicit conversion (by design)
			double[] formulaMevArray = new double[arList.size()];			
			for(i=0;i<arList.size();i++)
			{			
				formulaMevArray[i] = arList.get(i);
			}
			return formulaMevArray;	//ta daaa!!		
		}
		else
		{		
			return null;
		}		
	}
	
	//*********************************************************************************************************		

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
        int i,j;
		
        //Combine formula atoms
		String formula = formula1 + ":" + formula2;
		
		//Get get tabulated MeV
		mevArr = getMevArray(formula);
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
	        muMass1 = getMuMass(formula1, mevArr[i], muMassType);
	        muMass2 = getMuMass(formula2, mevArr[i], muMassType);
	        muMassRatio[i] = muMass1 / muMass2;
	    }

	    //Find the index of the lower of the two attenuations that bracket the testMuRatio
		List<Integer> indexList = new ArrayList<>();
	    j=0;		
		for(i=0;i<mevArr.length-1;i++)
		{
	        if( Math.abs(mevArr[i + 1] - mevArr[i]) > 0.001) //Then ' skip edges
	        {
	        	if ((testMuRatio <= muMassRatio[i] && testMuRatio > muMassRatio[i + 1]) ||
	        			(testMuRatio > muMassRatio[i] && testMuRatio <= muMassRatio[i + 1]))
	        	{
	        		indexList.add(i);
	        		j++;
	        	}
	        }
		}
		// if bracketing energies were found interpolate to find result
		if(j>0)
		{
			List<Double> mevList = new ArrayList<>();
	        for(i = 0;i<indexList.size();i++)
	        {
	                minMeV = mevArr[indexList.get(i)];
	                maxMeV = mevArr[indexList.get(i) + 1];
	                
	                mu1 = getMuMass(formula1, minMeV, muMassType);
	                mu2 = getMuMass(formula2, minMeV, muMassType);
	                muR1 = mu1 / mu2;
	                
	                mu1 = getMuMass(formula1, maxMeV, muMassType);
	                mu2 = getMuMass(formula2, maxMeV, muMassType);
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

	//*********************************************************************************************************	
	
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
		int i,j;
		double muMass,muMass1, muMass2, mev1, mev2,result;
		double[] mevArr,muMassList,mevSoln;
		List<Double> mevList = new ArrayList<>();

		//Get tabulated MeV
		mevArr = getMevArray(formula);
		if(mevArr==null) return null;

		//Compute the muMass
		muMass = linearCoeff/gmPerCC;
		if(muMass ==0) return null;
		
	    //Convert the energies to mass attenuation
		muMassList = new double[mevArr.length];
		for(i=0;i<muMassList.length;i++)
		{
			muMassList[i]= getMuMass(formula,mevArr[i],muMassType);
		}
		
		j=0;
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
					j++;
				}
			}
		}
		if(j>0)
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

	//*********************************************************************************************************	
	
	/**
	 * Converts observed linear attenuation coefficient to concentration.
	 * @param linearCoeff The experimentally observed linear attenuation coefficient of a known material at a "known" photon energy
	 * @param massCoeff The calculated mass attenuation coefficient of the known material at the "known" energy
	 * @param formulaGmPerCC The density of the pure material
	 * @return The concentration in mol/m3 
	 */
	public double muLinToMolPerM3(double linearCoeff, double massCoeff, double formulaGmPerCC)
	{
		return 1000000*linearCoeff/massCoeff/formulaGmPerCC;
	}

	//*********************************************************************************************************	

	/**
	 * Estimates the output of a conventional x-ray source (reflection target) using the Kramers equation.
	 * @param tubeVoltageKV The excitation potential, AKA x-ray tube voltage KV
	 * @param tubeCurrentMa	The x-ray source beam current
	 * @param targetAtomSymbol The x-ray source target material, usually W Tungsten
	 * @param meV The energy at which the tube output is to be estimated
	 * @return The estimated photon intensity.
	 */
	public double spectrumKramers( double tubeVoltageKV, double tubeCurrentMa, String targetAtomSymbol, double meV)
	{
		int targAtmNum;
		double wav,keV, wavMin;
		final double hc = 12.3984197;
		
		targAtmNum= getAtomNum(targetAtomSymbol);
		keV = meV*1000;
		wav = hc/keV;
		wavMin=hc/tubeVoltageKV;
		
		
		if(keV >= tubeVoltageKV)
		{
			return 0.0;
		}
		else
		{
			return tubeCurrentMa * targAtmNum * (wav / wavMin - 1) / (wav*wav);
		}			
	}
	
}
