package jhd.MuMass;

public interface AtomInterface {
	//methods supported by the MassAttenution classes
	//only getters, all NIST data is assigned as final
	
	/**
	 * @param theEdge  "K", "L1", "L2", "L3", "M1", "M2", "M3","M4","M5", "N1", "N2"
	 * @return the MeV absorption edge energy
	 */
	public double getAtomAbsEdge(String theEdge);
	
	/**
	 * @return the edge names array  "K", "L1", "L2", "L3", "M1", "M2", "M3","M4","M5", "N1", "N2"
	 */
	public String[] getAtomAbsEdgeNames();
	
	/**Slightly different implementation of getAtomAbsEdge
	 * @param theEdgeName "K", "L1", "L2", "L3", "M1", "M2", "M3","M4","M5", "N1", "N2"
	 * @return the MeV absorption edge energy
	 */
	public double getAtomAbsEdgeValue(String theEdgeName);
	
	/**
	 * @return an array containing all of the edge values from K to N2 for the atom
	 */
	public double[] getAtomAbsEdgeValues();
	
	
	/**
	 * @return the atomic weight of the atom
	 */
	public double getAtomGmPerMol();
	
	/**
	 * @return the density of the atom in its pure state at stp.
	 */
	public double getAtomGmPerCC();
	
	/**
	 * @return the full name of the atom e.g. "Magnesium"
	 */
	public String getAtomName();
	
	/**
	 * @return the atomic number of the atom
	 */
	public int getAtomNumber();
	
	/**
	 * @return the NIST tabulated energy values
	 */
	public double[] getAtomMevValues();
	
	/**
	 * @return the NIST tabulated MuMass values corresponding to the tabulated energy values
	 */
	public double[] getAtomMuMassValues(String muMassType);
}



