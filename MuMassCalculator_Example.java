package MuMassCalculator;

import java.awt.*;
import java.util.*;
import ij.*;
import ij.plugin.*;
import jhd.MuMassCalculator.MuMassCalculator;
import ij.gui.*;
import ij.measure.*;

import gray.AtomData.*;


public class MuMassCalculator_Example implements PlugIn, DialogListener {

	int   testCnt=0;


	@Override
	public void run(String arg)
	{
		int i,j;

		MuMassCalculator mmc= new MuMassCalculator();

		String dir = IJ.getDirectory("plugins");
		dir= dir.replace("\\","/");
		String myURL = "file:///" + dir + "jars/MuMassCalculatorDocs/index.html";
		
		Font myFont = new Font(Font.DIALOG, Font.BOLD, 12);	
		
		GenericDialog gd = new GenericDialog("Mu Mass Calculator");
		String theFormula = "Pb:1:Cr:1:O:4";
		double theMeV = .1;
		gd.addMessage("Plot Formula MuMass Spectrum", myFont);
		gd.addMessage("Formula Format = Atom1:Count1:Atom2:Count2 etc.", myFont);
		gd.addStringField("Formula: ", theFormula,26);
		gd.addMessage("Select interaction",myFont);
		String[] muMassTypes = mmc.getMuMassTypes();
		gd.addCheckbox(muMassTypes[0], true);
		for(i=1;i<muMassTypes.length;i++)
		{
			gd.addCheckbox(muMassTypes[i], false);
		}
		gd.addNumericField("Min MeV", .001);
		gd.addNumericField("Max MeV", 100000);
		gd.addCheckbox("Plot Log Attenuation", true);
		gd.addCheckbox("Plot Log MeV", true);
		gd.addCheckbox("Report edge Energies", false);
		gd.addHelp(myURL);
		gd.addMessage("_______________________",myFont);
		
		gd.addMessage("Result Quick Calculator",myFont);
		gd.addNumericField("MeV:", theMeV);
		gd.addMessage("Attenuations Appear in Table",myFont);
		gd.addDialogListener(this);
		gd.showDialog();

		if (gd.wasCanceled()) return;
		
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		String formula = gd.getNextString();
		//String muMassType = gd.getNextChoice();
		boolean[] muTypeFlag = new boolean[muMassTypes.length];
		for(i = 0;i< muMassTypes.length;i++)
		{
			muTypeFlag[i] = gd.getNextBoolean();
		}
		double minMev = gd.getNextNumber();
		double maxMev = gd.getNextNumber();
		boolean xLogPlot = gd.getNextBoolean();
		boolean yLogPlot = gd.getNextBoolean();
		boolean reportEdges = gd.getNextBoolean();

		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		//Get the tabulated energies associated with atoms in the formula
		double[] meV = mmc.getMevArray(formula,minMev,maxMev);
		if(meV==null)
		{
			IJ.showMessage("Error", formula + " Bad Formula, Element or count missing");
			return;
		}

		//count the number of muMassTypes selected++++++++++++++++++++++++++
		int cnt=0;
		for(i=0;i<muTypeFlag.length;i++)
		{
			if(muTypeFlag[i]) cnt++;
		}

		//create a 2D array to hold each spectrum
		double[][] muMass = new double[cnt][meV.length];

		//Get the mass attenuation coefficients of the formula++++++++++++++
		//for each checked flag and for each meV
		cnt=0;
		String legend="";
		for(i=0;i<muTypeFlag.length;i++)
		{
			if(muTypeFlag[i])
			{
				for(j=0;j<meV.length;j++)
				{
					muMass[cnt][j] = mmc.getMuMass(formula,meV[j],muMassTypes[i]);
				}
				legend += muMassTypes[i] + "\t";
				cnt++;
			}
		}

		//Find min and max MuMass+++++++++++++++++++++++++++++++++++++++++++++
		double muMassMax = Double.MIN_VALUE;
		double muMassMin = Double.MAX_VALUE;
		for(j=0;j<cnt;j++)
		{
			for(i=0;i<meV.length;i++)
			{
				if(muMassMax < muMass[j][i]) muMassMax = muMass[j][i];
				if(muMassMin > muMass[j][i] && muMass[j][i]!=0) muMassMin = muMass[j][i];
			}
		}		

		//Plot the  spectra+++++++++++++++++++++++++++++++++++++++++++++++++++
		//String title = formula + " Mass Attenuation";
		Plot plot = new Plot(formula + " Mass Attenuation","MeV","cm2/gm");
		plot.setFont(Font.BOLD, 16);
		plot.addLabel(0, 1, formula);

		plot.setFont(Font.BOLD, 10);
		plot.setLimits(meV[0], meV[meV.length-1], muMassMin, muMassMax);

		//give each muMassType a distinct color
		Color[] myColors = new Color[6];
		myColors[0]= Color.black;
		myColors[1]= Color.blue;
		myColors[2]= Color.red;
		myColors[3]= Color.green;
		myColors[4]= Color.cyan;
		myColors[5]= Color.magenta;

		//add the spectra to the plot
		//the circles are used to show the positions of the tabulated NIST data
		//from mmc.getMevArray(formula,minMev,maxMev);
		cnt=0;
		for(i=0;i<muTypeFlag.length;i++)
		{
			if(muTypeFlag[i])
			{
				plot.setColor(myColors[i]);
				plot.addPoints(meV,muMass[cnt],Plot.CONNECTED_CIRCLES);
				cnt++;
			}
		}

		plot.setLineWidth(1);
		if(xLogPlot)
		{
			plot.setLogScaleX();
		}
		if(yLogPlot)
		{
			plot.setLogScaleY();
		}
		plot.addLegend(legend);
		plot.show();

		//Report edges+++++++++++++++++++++++++++++++++++++++++++++++++++++
		//The imageJ plot.addLabel methods are not supported by the plot window
		//re-scale and zoom features, i.e. the labels don't move when zoomed
		//so the edge label feature has been dropped.
		if(reportEdges)
		{
			ArrayList<AtomData> fl = mmc.createFormulaList(formula);
			String[] edgeNames = mmc.getabsEdgeNames();		
			ResultsTable rt = new ResultsTable();		

			for(i=0;i< fl.size();i++)
			{
				String theAtom = mmc.getAtomSymbol(fl, i);
				rt.addValue("Name",theAtom);
				for(String edge : edgeNames)
				{	
					double edgeMev = mmc.getAtomAbsEdge(theAtom ,edge ) ;
					if(edgeMev > 0)
					{
						rt.addValue(edge, ResultsTable.d2s(edgeMev, 5));
					}
				}
				rt.addRow();
			}
			rt.deleteRow(i);
			rt.show("Absorption Edges(MeV)");
		}
	}
	
	//**********************************************************************************
	
	public boolean dialogItemChanged(GenericDialog gd, AWTEvent theEvent)
	{

		@SuppressWarnings("unchecked")
		Vector<TextField> nTexts= gd.getStringFields();
		@SuppressWarnings("unchecked")
		Vector<TextField> nNumbers= gd.getNumericFields();

	
		TextField formula = nTexts.elementAt(0);
		TextField meV = nNumbers.elementAt(2);		
		Object  eventSrc = theEvent.getSource();

		if(eventSrc.equals(formula) || eventSrc.equals(meV))		
		{
			MuMassCalculator mmc= new MuMassCalculator();
			String[] mmTypes =mmc.getMuMassTypes();
			
			ResultsTable rt = ResultsTable.getResultsTable("QuickCalc");
			if(rt==null)
			{
				rt=new ResultsTable();
			}
			
			try
			{
				String myFormula = formula.getText();
				ArrayList<AtomData> fl = mmc.createFormulaList(myFormula);
				double myMeV = Double.parseDouble(meV.getText());
				if(fl != null && myMeV> 0.001 && myMeV < 100000)
				{
					rt.incrementCounter();
					rt.addValue("Formula", myFormula);
					rt.addValue("MeV", myMeV);
					try
					{
						for(int i=0;i<mmTypes.length;i++)
						{
							double muMass = mmc.getMuMass(myFormula, myMeV, mmTypes[i]); 				
							rt.addValue(mmTypes[i], muMass);
						}
					}
					catch(Exception e1)
					{
						//do Nothing
					} 
					rt.show("QuickCalc");
				}
				}
			catch(Exception e1)
			{
			}
			
		}
		return true;
	}
}
