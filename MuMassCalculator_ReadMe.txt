MuMassCalc_J8Lib.jar is a java library based on XCOM that can be used to calculate
photon cross sections for scattering, photoelectric absorption and pair production,
as well as total attenuation coefficients, for any element, compound (Z ≤ 100),
at energies from 1 keV to 100 GeV.

//*********************************************************************

Acknowledgement:

XCOM: Photon Cross Sections DatabaseNIST Standard Reference Database 8 (XGAM),
M.J. Berger, J.H. Hubbell, S.M. Seltzer, J. Chang, J.S. Coursey, R. Sukumar, D.S. Zucker, and K. Olsen,
NIST, PML, Radiation Physics Division

See  https://dx.doi.org/10.18434/T48G6X  for detailed information about XCOM

Used with permission

//*********************************************************************

Notes:
MuMassCalc_J8Lib.jar does not support mixtures.
was developed with JavaSE-1.8 (64-bit) and was tested with ImageJ 1.53i
See: https://imagej.nih.gov/ij/

//*********************************************************************

Use:

MuMassCalc_J8Lib.jar contains the java source, and class files.

ImageJ	download MuMassCalc_J8Lib.jar to the ImageJ/plugins/jars folder.
	download MuMassCalculator_Example.java example to the ImageJ/plugins folder.
	Use ImageJ menu Plugins/Macros/Edit and browse to MuMassCalculator_Example.java in the plugins folder.
	In the plugin editor window Select File>Compile and Run

Eclipse	download MuMassCalc_J8Lib.jar to your eclipse-workspace folder
	Add MuMassCalc_J8Lib.jar to your project's buildpath.

	A simple example class file

	import jhd.MuMassCalculator.*;

	public class MuMassTest {

	public static void main(String[] args) {
		MuMassCalculator mmc = new MuMassCalculator();
		
		double[] result = mmc.getMevArray("CA:1:C:1:O:3", .001, .1);
		
		for(double mev : result)
		{
			System.out.println(mev);
		}
		
		double mev = mmc.getAtomMuMass("AC", .1, "Totattn");
		//double mev = mmc.getWeight()
		System.out.println(mev);

	}

}

