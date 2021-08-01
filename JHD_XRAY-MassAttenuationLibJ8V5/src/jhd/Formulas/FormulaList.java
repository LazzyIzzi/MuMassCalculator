package jhd.Formulas;

import java.util.ArrayList;
import nist.MuMassData.*;
import gray.AtomData.*;


/*
 * The FormulaList class computes the muMass and weight of formulas using the AtomData class
 * The FormulaList class is private to this package.  Its methods are wrapped by the MuMassCalculator class
 * to simplify the user interface.
 */


/**
 * A class for converting a chemical formula into a data structure populated with relevant information for computing photon cross sections.
 * @author John H Dunsmuir
 */
public class FormulaList extends LookupNIST{



	//*********************************************************************************************************		
	/**
	 * @param formulaList A list containing atom data. See createFormulaList
	 * @param index the position in the formulalist
	 * @return The atom name e.g. "chromium"
	 */
	public String getAtomName(ArrayList<AtomData> formulaList,int index)
	{
		if(index > formulaList.size()) return null;
		else
		{
			AtomData atomData = formulaList.get(index);
			return atomData.getAtomName();
		}
	}

	//*********************************************************************************************************		
	/**
	 * @param formulaList A list containing atom data. See createFormulaList
	 * @param index the position in the formulalist
	 * @return The atom name e.g. "CR" for chromium
	 */
	public String getAtomSymbol(ArrayList<AtomData> formulaList,int index)
	{
		if(index > formulaList.size()) return null;
		else
		{
			AtomData atomData = formulaList.get(index);
			return atomData.getAtomSymbol();
		}
	}

	//*********************************************************************************************************		
	/**
	 * @param formulaList A list containing atom data. See createFormulaList
	 * @param index the position in the formulalist
	 * @return The count  for the atom at the formula list index 
	 */
	public double getAtomCount(ArrayList<AtomData> formulaList,int index)
	{
		if(index > formulaList.size()) return 0;
		else
		{
			AtomData atomData = formulaList.get(index);
			return atomData.getAtomCount();
		}
	}

	//*********************************************************************************************************		
	/**
	 * @param formulaList A list containing atom data. See createFormulaList
	 * @param index the position in the formulalist
	 * @return The atomic weight in gm/mol for the atom at the formula list index 
	 */
	public double getAtomWeight(ArrayList<AtomData> formulaList,int index)
	{
		if(index > formulaList.size()) return 0;
		else
		{
			AtomData atomData = formulaList.get(index);
			return atomData.getAtomWeight();
		}
	}

	//*********************************************************************************************************		
	/**
	 * @param formulaList A list containing atom data. See createFormulaList
	 * @param index the position in the formulalist
	 * @return Z, the atomic number for the atom at the formula list index 
	 */
	public int getAtomNumber(ArrayList<AtomData> formulaList,int index)
	{
		if(index > formulaList.size()) return 0;
		else
		{
			AtomData atomData = formulaList.get(index);
			return atomData.getAtomNumber();
		}
	}

	//*********************************************************************************************************
	
	/**
	 * @param formulaList A list containing atom data. See createFormulaList
	 * @param index the position in the formulalist
	 * @return the density in gm/cc for the atom at the formula list index 
	 */
	public double getAtomDensity(ArrayList<AtomData> formulaList,int index)
	{
		if(index > formulaList.size()) return 0;
		else
		{
			AtomData atomData = formulaList.get(index);
			return atomData.getAtomDensity();
		}
	}

	//*********************************************************************************************************
	
	/**
	 * Returns an ArrayList of AtomData Objects.
	 * @param theFormula A string representation of a chemical formula e.g. Ca:1:C:1:O:3 for CaCO3 
	 * @return  An ArrayList of AtomData Objects, null if incorrect formula
	 */
	public ArrayList<AtomData> createFormulaList(String theFormula)
	{

		if(theFormula.length()<=2) theFormula += ":1";

		String[] formula = theFormula.split(":");

		//the odd items must be strings on the atom list
		AtomData myAtomData = new AtomData();
		for(int i=0;i<formula.length;i+=2)
		{
			if( myAtomData.setAtomSymbol(formula[i]) == false)
			{
				//System.out.println("Bad Formula");
				return null;
			}	
		}

		//the even "Count" items must be positive numbers
		try{
			for(int i=0;i<formula.length;i+=2)
			{
				double count = Double.parseDouble(formula[i+1]);
				if (count < 0)
				{					//System.out.println("Bad Formula");
					return null;
				}
			}
		}
		catch(Exception e){				//System.out.println("Bad Formula");
			return null;
		}

		// everthing is OK
		ArrayList<AtomData> myList = new ArrayList<AtomData>();
		for(int i=0;i<formula.length;i+=2)
		{
			double count = Double.parseDouble(formula[i+1]);
			String symbol = formula[i];			
			AtomData myAtmDat = new AtomData();
			if(myAtmDat.setAtomSymbol(symbol))
			{
				myAtmDat.setAtomCount(count);
				myList.add(myAtmDat);
			}
		}
		return myList;
	}
}
