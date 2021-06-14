package jhd.MuMassTest;

import jhd.MuMassCalculator.*;

public class MuMassTest {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double theMuMass;
		MuMassCalculator muMass = new MuMassCalculator();
		String theAtom = "FM";
		double theMeV = .04;
		
		
		theMuMass = muMass.getAtomMuMass(theAtom, theMeV, "TotAttn");
		System.out.println("Total muMass of " + theAtom + " at " + theMeV + " MeV= " + theMuMass + " cm2/gm");
		theMuMass = muMass.getAtomMuMass(theAtom, theMeV, "Raleigh");
		System.out.println("Raleigh muMass of " + theAtom + " at " + theMeV + " MeV= " + theMuMass + " cm2/gm");
		theMuMass = muMass.getAtomMuMass(theAtom, theMeV, "Compton");
		System.out.println("Compton muMass of " + theAtom + " at " + theMeV + " MeV = " + theMuMass + " cm2/gm");
		theMuMass = muMass.getAtomMuMass(theAtom, theMeV, "PhotoElectric");
		System.out.println("PhotoElectric muMass of "  + theAtom +   " at " + theMeV + " MeV= " + theMuMass + " cm2/gm");
		theMuMass = muMass.getAtomMuMass(theAtom, theMeV, "PairProdE");
		System.out.println("PairProdE muMass of " + theAtom + " at " + theMeV + " MeV= " + theMuMass + " cm2/gm");
		theMuMass = muMass.getAtomMuMass(theAtom, theMeV, "PairProdN");
		System.out.println("PairProdN muMass of " + theAtom + " at " + theMeV + " MeV= " + theMuMass + " cm2/gm");
		
		final  String[] atomSymbol = muMass.getAtomSymbols();
		
		
		  System.out.println("*************************************");
		  for(String x : atomSymbol)
		  { theMuMass = muMass.getAtomMuMass(x, theMeV, "TotAttn");
		  //System.out.println("Total muMass of " + x + " at " + theMeV + " MeV= " +theMuMass + " cm2/gm");
		  System.out.format("Total muMass of  %2s  at  %.3f MeV = %.4f cm2/gm %n" ,x,theMeV,theMuMass); }
		 		
		System.out.println("*************************************");
		theAtom = "Xe";
		for( theMeV =.03;theMeV < .035; theMeV+= .0001)
		{
			theMuMass = muMass.getAtomMuMass(theAtom, theMeV, "TotAttn");
			System.out.println("Total muMass of " + theAtom + " at " + theMeV + " MeV= " + theMuMass + " cm2/gm");			
		}
		
		String theFormula = "Ca:1:C:1:O:3";
		theMuMass = muMass.getMuMass(theFormula, theMeV, "TotAttn");
		System.out.println(theFormula + " weight =  " + muMass.getFormulaWeight(theFormula) + " gm/mol");			
		System.out.println("Total muMass of " + theFormula + " at " + theMeV + " MeV= " + theMuMass + " cm2/gm");
		theAtom = "Ca";
		System.out.println(theAtom + " weight =  " + muMass.getAtomWeight(theAtom) + " gm/mol");			
		
		
		

	}

}
