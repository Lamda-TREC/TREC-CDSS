package ajou.lamda.metamap.api;

import gov.nih.nlm.nls.metamap.Ev;
import gov.nih.nlm.nls.metamap.Result;

import java.util.ArrayList;
import java.util.List;

public class test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		MetaMapApiConnector metaapi = new MetaMapApiConnector("202.30.23.64");
		
		metaapi.Connection(true);
		
		List<Result> results = metaapi.SendTextMetaMap("result");
		ArrayList<Ev> resultMappings= metaapi.AnalzeMappingMetaMap(results, 1);
		
		
		for(Ev evMap : resultMappings)
		{
			System.out.println("Concept Name : "+evMap.getConceptName());
			System.out.println("Preferred Name : "+evMap.getPreferredName());
			System.out.println("Semantic Types : "+evMap.getSemanticTypes());	
		}
		
		
		
	}

}
