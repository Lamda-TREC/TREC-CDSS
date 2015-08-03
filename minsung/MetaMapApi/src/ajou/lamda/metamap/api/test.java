package ajou.lamda.metamap.api;

import gov.nih.nlm.nls.metamap.Ev;
import gov.nih.nlm.nls.metamap.Result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.opencsv.CSVWriter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStreamWriter;

public class test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		MetaMapApiConnector metaapi = new MetaMapApiConnector("202.30.23.64");
		
		metaapi.Connection(true);
		
		String year = "2014";
		year = "2015";
		String t = "description";
//		t = "summary";
		
		File csv = new File(year + "_" + t + ".csv");
		
		String to = "C:\\Users\\Desktop\\" + year + "_" + t + "_topic.csv";
		String sum = "C:\\Users\\Desktop\\" + year + "_" + t + "_sum.csv";
				
		BufferedReader br = new BufferedReader(new FileReader(csv));
		BufferedWriter sw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(sum), "MS949"));
		BufferedWriter tow = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(to), "MS949"));
		
		sw.write(",dsyn,diap,bpoc,sosy,fndg\n");
		
		String line = "";
		int num = 1;
		while ((line = br.readLine()) != null) {
			String[] token = line.split("\n", -1);
			int ds = 0;
			int di = 0;
			int ph = 0;
			int so = 0;
			int vi = 0;
			int bp = 0;
			int q = 0;

			for(String output : token) {
				sw.write("Topic " + num + ",");
				tow.write("Topic " + num + ",");
				List<Result> results = metaapi.SendTextMetaMap(output);
				ArrayList<Ev> resultMappings= metaapi.AnalzeMappingMetaMap(results, 1);
				Hashtable query = new Hashtable();
				Hashtable dsyn = new Hashtable();
				Hashtable diap = new Hashtable();
				Hashtable bpoc = new Hashtable();
				Hashtable sosy = new Hashtable();
				Hashtable fndg = new Hashtable();
				
				for(Ev evMap : resultMappings)
				{
					String sConcept = evMap.getConceptName();
					String sPreferred = evMap.getPreferredName();
					String[] aSemantic = evMap.getSemanticTypes().toArray(new String[evMap.getSemanticTypes().size()]);

					if(Arrays.toString(aSemantic).contains("dsyn")){
						dsyn.put(sConcept, sPreferred);
						query.put(sConcept, sPreferred);
					}
					if(Arrays.toString(aSemantic).contains("diap")){
						diap.put(sConcept, sPreferred);
						query.put(sConcept, sPreferred);
					}
					if(Arrays.toString(aSemantic).contains("bpoc")){
						bpoc.put(sConcept, sPreferred);
						query.put(sConcept, sPreferred);
					}
					if(Arrays.toString(aSemantic).contains("sosy")){
						sosy.put(sConcept, sPreferred);
						query.put(sConcept, sPreferred);
					}
					if(Arrays.toString(aSemantic).contains("fndg")){
						fndg.put(sConcept, sPreferred);
						query.put(sConcept, sPreferred);
					}
				}
				sw.write(dsyn.size() + ",");
				sw.write(diap.size() + ",");
				sw.write(bpoc.size() + ",");
				sw.write(sosy.size() + ",");
				sw.write(fndg.size() + "\n");
												
				Set<String> keySet = query.keySet();
				Iterator<String> iterKey = keySet.iterator();
				while(iterKey.hasNext()) {
					String key = (String) iterKey.next();
					tow.write(key + "," );
					System.out.println(key);
				}
				num++;
				tow.write("\n");
			}
		}	
		br.close();
		sw.close();
		tow.close();
	}
}
