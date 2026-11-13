package jhd.MuMass;

import java.util.Arrays;

public class dataTester {

	public dataTester() {
	}

	public static void test(String[] args) {

		XrayCalculator mmc = new XrayCalculator();
		String theAtom = "MG";
		String theFormula = "MG:1.25:C:1:O:3";

		if (mmc.validateAtomSymbol(theAtom) == false) {
			System.out.println(theAtom + " is not recognized");
			return;
		}

		if (mmc.validateFormula(theFormula) == false) {
			System.out.println(theFormula + " contains an error");
			return;
		}

		double theMeV = 1.25;
		double[] atomMevArray;
		double[] atomMuMassArray;

		String[] atomSymbols = mmc.getAtomSymbols();
		String[] muMassTypes = mmc.getMuMassTypes();

		// check that the arrays containing the attenuation information are the same
		// size for each atom
		System.out.println("**********************************");
		System.out.print("Checking Arrays  ");
		boolean error = false;
		for (String atom : atomSymbols) {
			atomMevArray = mmc.getAtomMevArray(atom);
			for (String muMassType : muMassTypes) {
				atomMuMassArray = mmc.getAtomMuMassArray(atom, muMassType);
				if (atomMevArray.length != atomMuMassArray.length) {
					System.out.println(
							atom + ", mevlen=" + atomMevArray.length + ", muMasslen=" + atomMuMassArray.length);
					error = true;
				}
			}
		}
		if (!error)
			System.out.println(" All atoms have equal length energy and attenuation arrays");

		// Check atoms physical data 
		System.out.println("**********************************");
		System.out.println("Checking atom Prysical data");
		for (String atom : atomSymbols) {
			System.out.println(atom);
			System.out.println("Name=" + mmc.getAtomName(atom));
			System.out.println("Number=" + mmc.getAtomNumber(atom));
			System.out.println("GmPerMol=" + mmc.getAtomGmPerMol(atom));
			System.out.println("GmPerCC=" + mmc.getAtomGmPerCC(atom));
			System.out.println("Formula Weight=" + mmc.getFormulaWeight(theFormula));
				System.out.println("");
		}
		System.out.println("**********************************");
		System.out.println("Checking atom Absorption edge data");
		String[] edgeNames = mmc.getAbsEdgeNames();
		System.out.print("Atom, ");
		for(String edgeName:edgeNames) {
			System.out.print(edgeName+",");
		}
		System.out.println("");
		for (String atom : atomSymbols) {
			System.out.print(atom+ ", ");
		for (String edgeName : edgeNames) {
				System.out.print( mmc.getAtomAbsEdge(atom, edgeName));
			}
			System.out.println("");
		}

		System.out.println("Checking formula Physical data");
		String[] atoms = mmc.getFormulaAtoms(theFormula);
		edgeNames = mmc.getAbsEdgeNames();
		System.out.println("");
		
		for(String edgeName:edgeNames) {
			System.out.print(edgeName+",");
		}
		System.out.println("");
		if (atoms != null) {
			double[] edges;
			for (int i = 0; i < atoms.length; i++) {
				edges = mmc.getAtomAbsEdgeValues(atoms[i]);
				System.out.print(atoms[i] + ", ");
				for (int j = 0; j < edges.length; j++) {
					System.out.print(edges[j] + ", ");
				}
				System.out.println("");
			}
		}

		System.out.println(theAtom);
		System.out.println("TotAttn=" + mmc.getAtomMuMass(theAtom, theMeV, "TotAttn"));

		System.out.println("Raleigh=" + mmc.getAtomMuMass(theAtom, theMeV, "Raleigh"));
		System.out.println("Compton=" + mmc.getAtomMuMass(theAtom, theMeV, "Compton"));
		System.out.println("PhotoElectric=" + mmc.getAtomMuMass(theAtom, theMeV, "PhotoElectric"));
		System.out.println("PairProdN=" + mmc.getAtomMuMass(theAtom, theMeV, "PairProdN"));
		System.out.println("PairProdE=" + mmc.getAtomMuMass(theAtom, theMeV, "PairProdE"));

		double[] formulaMevArray = mmc.getFormulaMevArray(theFormula);
		double[] formulaMuMassArray = mmc.getFormulaMuMassArray(theFormula, "TotAttn");

		System.out.println("**************************");
		System.out.println(theFormula + " Spectrum at tabulated MeVs");
		System.out.println("keV       TotAttn");
		for (int i = 0; i < formulaMevArray.length; i++) {
			System.out.format("%.4f %4f%n",formulaMevArray[i] * 1000 ,formulaMuMassArray[i]);
		}
	
		String spectrumChoice = "PairProdN";

		double[] testMev = { 1.250000, 1.300000, 1.700000, 2.100000, 2.500000, 2.900000, 3.300000, 3.700000, 4.100000,
				4.500000, 4.900000, 5.300000, 5.700000, 6.100000, 6.500000, 6.900000, 7.300000, 7.700000, 8.100000,
				8.500000, 9.500000, 10.500000, 11.500000, 12.500000, 13.500000, 14.500000, 15.500000, 16.500000,
				17.500000, 18.500000, 19.500000, 21.000000, 23.000000, 25.000000, 27.000000, 29.000000, 35.000000,
				45.000000, 55.000000, 65.000000, 70.000000, 90.000000, 110.000000, 250.000000, 650.000000, 2500.000000,
				12000.000000, 35000.000000, 90000.000000 };

		formulaMuMassArray = mmc.getFormulaMuMassArray(theFormula, spectrumChoice, testMev);

		System.out.println("**************************");
		System.out.println(theFormula + " Spectrum at test MeVs");
		System.out.println("keV        " + spectrumChoice);
		for (int i = 0; i < testMev.length; i++) {
			System.out.format("%.4f %4f%n",testMev[i] * 1000 , formulaMuMassArray[i]);
		}

		System.out.println("**************************");
		System.out.println("Absorption Edge energies");
		atomSymbols = mmc.getAtomSymbols();
		System.out.println("Symbol,Name,gmPerMol,GmPerCC,atmNum,kEdge");
		Arrays.sort(atomSymbols);
		for (int i = 0; i < atomSymbols.length; i++) {
			try {

				String atomName = mmc.getAtomName(atomSymbols[i]);
				double gmPerMol = mmc.getAtomGmPerMol(atomSymbols[i]);
				double gmPerCC = mmc.getAtomGmPerCC(atomSymbols[i]);
				double atmNum = mmc.getAtomNumber(atomSymbols[i]);
				double kEdge = mmc.getAtomAbsEdgeValue(atomSymbols[i], "K");
				System.out.println(
						atomSymbols[i] + "," + atomName + "," + gmPerMol + "," + gmPerCC + "," + atmNum + "," + kEdge);
			} catch (Exception e) {
				// System.out.println("");
			}

		}

	}

}


