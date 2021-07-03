
/*
 * An example plugin that calls several of the functions available in MuMassCalc_J8Lib.jar
 * MuMassCalc_J8Lib.jar is derived from the NIST XCOM web application used to calculate 
 * photon cross sections for scattering, photoelectric absorption and pair production,
 * as well as total attenuation coefficients, for any element, compound (Z less than o equal to 100),
 * at energies from 1 keV to 100 GeV.
 * See: https://github.com/LazzyIzzi/MuMassCalculator
 * 
 * This plugin supports two operations:
 * 1. Interactive calculation of interpolated attenuation cross-sections in units of cm2/gm from a
 * user-supplied simplified chemical formula and the photon energy. Calculated values 
 * are posted to a results window.
 * 
 * 2.Plotting of tabulated absorption spectra for selected cross-sections for the user supplied
 * formula and energy range.  Optionally the absorption edge energies for the atoms in the formula
 * are reported in a separate results table.
 * 
 */

import java.awt.*;
import java.util.*;

import ij.plugin.*;
import ij.gui.*;
import ij.*;
import ij.measure.*;

import jhd.MuMassCalculator.MuMassCalculator;
import gray.AtomData.*;


public class MuMassCalculator_Example implements PlugIn, DialogListener {

	int   testCnt=0;
	String myDialogTitle = "Mu Mass Calculator";

	@Override
	public void run(String arg)
	{
		int i,j;

		MuMassCalculator mmc= new MuMassCalculator();

		String dir = IJ.getDirectory("plugins");
		dir= dir.replace("\\","/");
		String myURL = "file:///" + dir + "jars/MuMassCalculatorDocs/index.html";
		Font myFont = new Font(Font.DIALOG, Font.ITALIC+Font.BOLD, 14);	

		GenericDialog gd =  GUI.newNonBlockingDialog(myDialogTitle);
		
		String theFormula = "Pb:1:Cr:1:O:4";
		double theMeV = .1;
		
		gd.addMessage("Compute absorption cross-sections in cm2/gm",myFont);
		gd.addMessage("___________________________________________",myFont);
		gd.addMessage("Quick Calculator",myFont);
		gd.addMessage("Formula Format = Atom1:Count1:Atom2:Count2 etc.", myFont);
		gd.addStringField("Formula: ", theFormula,26);
		gd.addNumericField("MeV:", theMeV);
		gd.addMessage("Real time update to results table", myFont);
		
		gd.addMessage("___________________________________________",myFont);
		gd.addMessage("Plot tabulated absorption cross-sections in cm2/gm",myFont);
		gd.addMessage("Energy Range", myFont);		
		gd.addNumericField("Min MeV", .001);
		gd.addNumericField("Max MeV", 100000);
		gd.addCheckbox("Plot MeV Log scale", true);
		gd.addMessage("Cross-section",myFont);
		String[] muMassTypes = mmc.getMuMassTypes();
		gd.addCheckbox(muMassTypes[0], true);
		for(i=1;i<muMassTypes.length;i++)
		{
			gd.addCheckbox(muMassTypes[i], false);
		}
		gd.addMessage(" ",myFont);
		gd.addCheckbox("Plot cross-section cm2/gm on Log scale", true);
		gd.addMessage("Click OK to Plot", myFont);
		gd.addMessage("___________________________________________",myFont);
		gd.addMessage("Extras:",myFont);
		gd.addCheckbox("Report formula absorption edge energies", false);
		gd.addHelp(myURL);

		gd.addDialogListener(this);
		gd.showDialog();

		if (gd.wasCanceled()) return;

		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		//Quick Calc
		String formula = gd.getNextString();
		double meV = gd.getNextNumber();

		double minMev = gd.getNextNumber();
		double maxMev = gd.getNextNumber();
		boolean xLogPlot = gd.getNextBoolean();
		
		boolean[] muTypeFlag = new boolean[muMassTypes.length];
		for(i = 0;i< muMassTypes.length;i++)
		{
			muTypeFlag[i] = gd.getNextBoolean();
		}
		boolean yLogPlot = gd.getNextBoolean();
		boolean reportEdges = gd.getNextBoolean();

		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		//Get the tabulated energies associated with atoms in the formula
		double[] meVArr = mmc.getMevArray(formula,minMev,maxMev);
		if(meVArr==null)
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
		double[][] muMass = new double[cnt][meVArr.length];

		//Get the mass attenuation coefficients of the formula++++++++++++++
		//for each checked flag and for each meV
		cnt=0;
		String legend="";
		for(i=0;i<muTypeFlag.length;i++)
		{
			if(muTypeFlag[i])
			{
				for(j=0;j<meVArr.length;j++)
				{
					muMass[cnt][j] = mmc.getMuMass(formula,meVArr[j],muMassTypes[i]);
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
			for(i=0;i<meVArr.length;i++)
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
		plot.setLimits(meVArr[0], meVArr[meVArr.length-1], muMassMin, muMassMax);

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
				plot.addPoints(meVArr,muMass[cnt],Plot.CONNECTED_CIRCLES);
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
		TextField meV = nNumbers.elementAt(0);
		if(theEvent != null)
		{
			Object  eventSrc = theEvent.getSource();

			if(eventSrc.equals(formula) || eventSrc.equals(meV))		
			{
				MuMassCalculator mmc= new MuMassCalculator();
				String[] mmTypes =mmc.getMuMassTypes();

				ResultsTable rt = ResultsTable.getResultsTable("QuickCalc");
				if(rt==null)
				{
					rt=new ResultsTable();
					rt.setPrecision(5);
				}
			
				try
				{
					String myFormula = formula.getText();
					ArrayList<AtomData> fl = mmc.createFormulaList(myFormula);
					double myMeV = Double.parseDouble(meV.getText());
					if(fl != null && myMeV> 0.001 && myMeV < 100000)
					{
						//rt.addRow();
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
							rt.show("QuickCalc");
						}
						catch(Exception e1)
						{
							//do Nothing
						} 
					}
				}
				catch(Exception e1)
				{
				}

			}
		}
		//Return control to Dialog
		IJ.selectWindow(myDialogTitle);
		return true;
	}
}
