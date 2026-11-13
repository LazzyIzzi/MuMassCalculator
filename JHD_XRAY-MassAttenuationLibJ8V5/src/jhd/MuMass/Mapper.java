package jhd.MuMass;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import jhd.nist.MuMassData.*;

public class Mapper {

	//public Mapper() {}
	
	/**
	 * @return map of classes that support the MuMassInterface keyed to the element symbol
	 */
	protected static Map<String,Supplier<AtomInterface>> getMuMassMap() {
		Map<String, Supplier<AtomInterface>> muMassMap = new HashMap<>();
		muMassMap.put("AC", MassAttenuationAC::new);
		muMassMap.put("AG", MassAttenuationAG::new);
		muMassMap.put("AL", MassAttenuationAL::new);
		muMassMap.put("AM", MassAttenuationAM::new);
		muMassMap.put("AR", MassAttenuationAR::new);
		muMassMap.put("AS", MassAttenuationAS::new);
		muMassMap.put("AT", MassAttenuationAT::new);
		muMassMap.put("AU", MassAttenuationAU::new);
		muMassMap.put("B", MassAttenuationB::new);
		muMassMap.put("BA", MassAttenuationBA::new);
		muMassMap.put("BE", MassAttenuationBE::new);
		muMassMap.put("BI", MassAttenuationBI::new);
		muMassMap.put("BK", MassAttenuationBK::new);
		muMassMap.put("BR", MassAttenuationBR::new);
		
		muMassMap.put("C", MassAttenuationC::new);
		muMassMap.put("CA", MassAttenuationCA::new);
		muMassMap.put("CD", MassAttenuationCD::new);
		muMassMap.put("CE", MassAttenuationCE::new);
		muMassMap.put("CF", MassAttenuationCF::new);
		muMassMap.put("CL", MassAttenuationCL::new);
		muMassMap.put("CM", MassAttenuationCM::new);
		muMassMap.put("CO", MassAttenuationCO::new);
		muMassMap.put("CR", MassAttenuationCR::new);
		muMassMap.put("CS", MassAttenuationCS::new);
		muMassMap.put("CU", MassAttenuationCU::new);
		
		muMassMap.put("DY", MassAttenuationDY::new);
		muMassMap.put("ER", MassAttenuationER::new);
		muMassMap.put("ES", MassAttenuationES::new);
		muMassMap.put("EU", MassAttenuationEU::new);
		muMassMap.put("F", MassAttenuationF::new);
		muMassMap.put("FE", MassAttenuationFE::new);
		muMassMap.put("FM", MassAttenuationFM::new);
		muMassMap.put("FR", MassAttenuationFR::new);
		muMassMap.put("GA", MassAttenuationGA::new);
		muMassMap.put("GD", MassAttenuationGD::new);
		muMassMap.put("GE", MassAttenuationGE::new);
		
		muMassMap.put("H", MassAttenuationH::new);
		muMassMap.put("HE", MassAttenuationHE::new);
		muMassMap.put("HF", MassAttenuationHF::new);
		muMassMap.put("HO", MassAttenuationHO::new);
		muMassMap.put("HG", MassAttenuationHG::new);
		muMassMap.put("I", MassAttenuationI::new);
		muMassMap.put("IN", MassAttenuationIN::new);
		muMassMap.put("IR", MassAttenuationIR::new);
		muMassMap.put("K", MassAttenuationK::new);
		muMassMap.put("KR", MassAttenuationKR::new);
		muMassMap.put("LA", MassAttenuationLA::new);
		muMassMap.put("LI", MassAttenuationLI::new);
		muMassMap.put("LU", MassAttenuationLU::new);

		muMassMap.put("MG", MassAttenuationMG::new);
		muMassMap.put("MN", MassAttenuationMN::new);
		muMassMap.put("MO", MassAttenuationMO::new);
		muMassMap.put("N", MassAttenuationN::new);
		muMassMap.put("NA", MassAttenuationNA::new);
		muMassMap.put("NB", MassAttenuationNB::new);
		muMassMap.put("ND", MassAttenuationND::new);
		muMassMap.put("NE", MassAttenuationNE::new);
		muMassMap.put("NI", MassAttenuationNI::new);
		muMassMap.put("NP", MassAttenuationNP::new);
		muMassMap.put("O", MassAttenuationO::new);
		muMassMap.put("OS", MassAttenuationOS::new);
		
		muMassMap.put("P", MassAttenuationP::new);
		muMassMap.put("PA", MassAttenuationPA::new);
		muMassMap.put("PB", MassAttenuationPB::new);
		muMassMap.put("PD", MassAttenuationPD::new);
		muMassMap.put("PM", MassAttenuationPM::new);
		muMassMap.put("PO", MassAttenuationPO::new);
		muMassMap.put("PR", MassAttenuationPR::new);
		muMassMap.put("PT", MassAttenuationPT::new);
		muMassMap.put("PU", MassAttenuationPU::new);

		muMassMap.put("RA", MassAttenuationRA::new);
		muMassMap.put("RB", MassAttenuationRB::new);
		muMassMap.put("RE", MassAttenuationRE::new);
		muMassMap.put("RH", MassAttenuationRH::new);
		muMassMap.put("RN", MassAttenuationRN::new);
		muMassMap.put("RU", MassAttenuationRU::new);

		muMassMap.put("S", MassAttenuationS::new);
		muMassMap.put("SB", MassAttenuationSB::new);
		muMassMap.put("SC", MassAttenuationSC::new);
		muMassMap.put("SE", MassAttenuationSE::new);
		muMassMap.put("SI", MassAttenuationSI::new);
		muMassMap.put("SM", MassAttenuationSM::new);
		muMassMap.put("SN", MassAttenuationSN::new);
		muMassMap.put("SR", MassAttenuationSR::new);

		muMassMap.put("TA", MassAttenuationTA::new);
		muMassMap.put("TB", MassAttenuationTB::new);
		muMassMap.put("TC", MassAttenuationTC::new);
		muMassMap.put("TE", MassAttenuationTE::new);
		muMassMap.put("TH", MassAttenuationTH::new);
		muMassMap.put("TI", MassAttenuationTI::new);
		muMassMap.put("TL", MassAttenuationTL::new);
		muMassMap.put("TM", MassAttenuationTM::new);

		muMassMap.put("U", MassAttenuationU::new);
		muMassMap.put("V", MassAttenuationV::new);
		muMassMap.put("W", MassAttenuationW::new);
		muMassMap.put("XE", MassAttenuationXE::new);
		muMassMap.put("Y", MassAttenuationY::new);
		muMassMap.put("YB", MassAttenuationYB::new);
		muMassMap.put("ZN", MassAttenuationZN::new);
		muMassMap.put("ZR", MassAttenuationZR::new);

		return muMassMap;		
	}
	
}
