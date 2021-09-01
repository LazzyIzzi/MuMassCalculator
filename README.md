# MuMassCalculator
**A java library to Compute Compton, Raleigh, Photoelectric and Pair Production Cross sections**

MuMassCalculator is derived from the NIST XCOM web application used to calculate
photon cross sections for scattering, photoelectric absorption and pair production,
as well as total attenuation coefficients, for any element, compound (Z ≤ 100),
at energies from 1 keV to 100 GeV.

**Acknowledgement:**

XCOM: Photon Cross Sections DatabaseNIST Standard Reference Database 8 (XGAM),
M.J. Berger, J.H. Hubbell, S.M. Seltzer, J. Chang, J.S. Coursey, R. Sukumar, D.S. Zucker, and K. Olsen,
NIST, PML, Radiation Physics Division

See  https://dx.doi.org/10.18434/T48G6X  for detailed information about XCOM

Tabulated XCOM data is used with permission

**Notes:**

Unlike XCOM, MuMassCalculator does not support mixtures.

MuMassCalculator was developed with JavaSE-1.8 (64-bit) and tested in the Eclipse IDE and as an ImageJ PlugIn with ImageJ 1.53i

MuMassCalculator has no ImageJ dependencies.

See: https://imagej.nih.gov/ij/

**Download:**

A MuMassCalculator jar file, JavaDocs and sample ImageJ PlugIns are available at

https://drive.google.com/drive/folders/1xr8dBjpwd2bo6ZqFmwoKvBBDUoqaK0Bg?usp=sharing

MuMassCalc_J8Lib.jar contains the java source and class files.
MuMassCalculatorDocs contains JavaDocs html files


**Use:**

**_ImageJ_**

Put MuMassCalc_J8Lib.jar and MuMassCalculatorDocs to the ImageJ/plugins/jars folder.
   
Put CT_Scanner_ImageJ_Plugins.jar examples to the ImageJ/plugins folder.

**_Eclipse_**	put MuMassCalc_J8Lib.jar to your eclipse-workspace folder and add it to your project's buildpath.

An eclipse example class file


import jhd.MuMassCalculator.*;

	public class MuMassTest {

	public static void main(String[] args) {
		MuMassCalculator mmc = new MuMassCalculator();
		
		double[] result = mmc.getMevArray("CA:1:C:1:O:3", .001, .1);
		
		for(double mev : result)
		{
			System.out.println(mev);
		}
		
		double theMuMass = mmc.getAtomMuMass("AC", .1, "Totattn");
		System.out.println(theMuMass);
	}
 
