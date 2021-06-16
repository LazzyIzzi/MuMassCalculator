import java.awt.*;
import ij.*;
import ij.plugin.*;
import ij.gui.*;
import ij.measure.*;
import jhd.MuMassCalculator.*;
import jhd.MathTools.*;

public class Test_MuMassCalculator implements PlugIn {

	public Test_MuMassCalculator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run(String arg)
	{
		MuMassCalculator mmc= new MuMassCalculator();
		
		String dir = IJ.getDirectory("plugins");
		dir= dir.replace("\\","/");
		String myURL = "file:///" + dir + "jars/MuMassCalculatorDocs/index.html";


		GenericDialog gd = new GenericDialog("Mu Mass Calculator");
		gd.addStringField("Formula: ", "Pb:1:Cr:1:O:4");
		gd.addChoice("Select Function",mmc.getMuMassTypes(),"GetMuMassCoeff");
		gd.addNumericField("Min MeV", .001);
		gd.addNumericField("Max MeV", 100000);
		gd.addHelp(myURL);
		gd.showDialog();

		if (gd.wasCanceled()) return;
		String formula = gd.getNextString();
		String muMassType = gd.getNextChoice();
		double minMev = gd.getNextNumber();
		double maxMev = gd.getNextNumber();

		//Get the tabulated energies associated with atoms in the formula
		double[] meV = mmc.getMevArray(formula,minMev,maxMev);
		if(meV==null)
		{
			IJ.showMessage("Error", formula + " Bad Formula, Element or count missing");
			 //IJ.log("BadFormula");
			return;
		}
		
		//Get the mass attenuation coefficients of the formula for each meV
		double[] muMass = new double[meV.length];
		for(int i=0;i<meV.length;i++)
		{
			muMass[i] = mmc.getMuMass(formula,meV[i],muMassType);
		}
		
		//Find min and max MuMass
		double muMassMax = muMass[0];
		double muMassMin = muMass[0];
		for(int i=1;i<meV.length;i++)
		{
			if(muMassMax < muMass[i]) muMassMax = muMass[i];
			if(muMassMin > muMass[i]) muMassMin = muMass[i];
		}

		//Plot the absorbance spectrum
		String title = formula + " " + muMassType;
		Plot plot = new Plot(title,"MeV","cm2/gm");
		plot.setFontSize(18);
		plot.setLimits(meV[0], meV[meV.length-1], muMassMin, muMassMax);
		plot.setLogScaleX();
		plot.setLogScaleY();
		plot.setLineWidth(2);
		plot.setColor(Color.blue);

		plot.addPoints(meV,muMass,Plot.LINE);

		// Add a label
		plot.setJustification(Plot.LEFT);
		plot.setColor(Color.black);
		double xloc = .5;
		double yloc = 0.1;
		plot.addLabel(xloc, yloc, formula + " " + muMassType);

		//Get the absorption edge names
		String[] edgeNames = mmc.getabsEdgeNames();

		double[] limits = plot.getLimits();
		double xMin = Math.log(limits[0]);
		double xMax = Math.log(limits[1]);
		double yMin = Math.log(limits[2]);
		double yMax =Math.log(limits[3]);

		//Label the Pb edges
		String theAtom = "Pb";

		ResultsTable rt = new ResultsTable();
		
		for(String edge : edgeNames)
		{	
			double edgeMev = mmc.getAtomAbsEdge(theAtom ,edge ) ;
			double logEdgeMev = Math.log(edgeMev);

			xloc = Interpolation.linTerp(xMin,xMax,logEdgeMev,0,1);

			double edgeMu = mmc.getMuMass(theAtom ,edgeMev,muMassType) ;
			double logEdgeMu = Math.log(edgeMu);

			yloc = Interpolation.linTerp(yMin,yMax,logEdgeMu,1,0);

			plot.setFontSize(10);
			plot.setColor(Color.BLUE);
			plot.addLabel(xloc, yloc, "Pb " + edge );
			plot.show();
			
			rt.addValue(edge, ResultsTable.d2s(edgeMev, 5));
		}
		rt.show("Absorption Edges(MeV)");
		
		// Same as List option in plot window
		//ResultsTable pltTable = plot.getResultsTable();
		//pltTable.show("PlotData");
	}
}
