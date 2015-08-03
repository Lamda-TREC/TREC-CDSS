package ajou.lamda.metamap.api;

import gov.nih.nlm.nls.metamap.Ev;
import gov.nih.nlm.nls.metamap.Mapping;
import gov.nih.nlm.nls.metamap.MetaMapApi;
import gov.nih.nlm.nls.metamap.MetaMapApiImpl;
import gov.nih.nlm.nls.metamap.PCM;
import gov.nih.nlm.nls.metamap.Result;
import gov.nih.nlm.nls.metamap.Utterance;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.IndexServer.socketServer.Info;

public class MetaMapApiConnector {

	private MetaMapApi metaMapApi;
	private String hostName;
	private String removeSemanticType[] = { "tmco", "fndg", "qlco", "qnco",
			"anim", "bird", "cnce", "edac", "enty", "evnt", "fish", "ftcn",
			"idcn", "inpr", "prog", "pros", "qlco", "qnco", "tmco" };
	private ArrayList<Ev> resultMetaMappingList;
	private boolean isremoveSemantic = false;

	private int type;

	public MetaMapApiConnector(String hostName) {
		super();
		this.hostName = hostName;
	}

	public void Connection(boolean isremoveSemantic) {
		metaMapApi = new MetaMapApiImpl(hostName);

		this.isremoveSemantic = isremoveSemantic;
	}

	public List<Result> SendTextMetaMap(String text) {

		List<Result> resultList = null;

		System.out.println("Input Text : " + text);
		resultList = metaMapApi.processCitationsFromString(text);

		return resultList;

	}

	public ArrayList<Ev> AnalzeMappingMetaMap(List<Result> resultList, int type)
			throws Exception {

		Result result = resultList.get(0);
		

		resultMetaMappingList = new ArrayList<Ev>();
		
		for (Utterance utterance : result.getUtteranceList()) {
			System.out.println("Utterance:");
			System.out.println(" Id: " + utterance.getId());
			System.out.println(" Utterance text: " + utterance.getString());
			System.out.println(" Position: " + utterance.getPosition());

			for (PCM pcm : utterance.getPCMList()) {

				System.out.println("Mappings: "
						+ pcm.getPhrase().getPhraseText());
				if (pcm.getMappingList().size() > 0) {
					if (type == 1) {
						for (Mapping map : pcm.getMappingList()) {

							System.out.println(" Map Score: " + map.getScore());
							for (Ev mapEv : map.getEvList()) {
								

								resultMetaMappingList.add(mapEv);
							/*	
								System.out.println("   Concept Name: "
										+ mapEv.getConceptName());
								
								System.out.println("   Preferred Name: "
										+ mapEv.getPreferredName());

								System.out.println("   Semantic Types: "
										+ mapEv.getSemanticTypes());
*/
								
							}
						}
					} else if (type == 2) {
						Mapping map = pcm.getMappingList().get(0);
						System.out.println(" Map Score: " + map.getScore());
						for (Ev mapEv : map.getEvList()) {

							resultMetaMappingList.add(mapEv);
							/*
							System.out.println("   Concept Name: "
									+ mapEv.getConceptName());
							
							System.out.println("   Preferred Name: "
									+ mapEv.getPreferredName());

							System.out.println("   Semantic Types: "
									+ mapEv.getSemanticTypes());*/

						}
					}

				}
			}
		}
		
		return resultMetaMappingList;
	}
	
	
	 public void EnglishMetMapContent(String category,String id) throws Exception {

			String input = Info.CONTENT_FOLDER_PATH + category + "/text/"
					+ id + ".txt";
			String output = Info.CONTENT_FOLDER_PATH + category + "/text/"
					+ id + "_output.txt";
			BufferedReader br;
			try {
				br = new BufferedReader(new FileReader(new File(input)));
				BufferedWriter bw = new BufferedWriter(new FileWriter(output));
				bw.write("DOC	X	");
				String str = null;
				while ((str = br.readLine()) != null) {

					List<Result> results = SendTextMetaMap(str);
					if(results.size() >0)
					{
						ArrayList<Ev> resultMappings= AnalzeMappingMetaMap(results, 1);
						for(Ev evMap : resultMappings)
						{
							System.out.println("Concept Name : "+evMap.getConceptName());
							System.out.println("Preferred Name : "+evMap.getPreferredName());
							System.out.println("Semantic Types : "+evMap.getSemanticTypes());
							
							for(String semantic : evMap.getSemanticTypes())
							{
								if(semantic.contains("dsyn")||semantic.contains("diap")||semantic.contains("bpoc")||semantic.contains("sosy"))
								{
									String word = evMap.getConceptName().toLowerCase().replaceAll(", nos", "");
									String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
									word =word.replaceAll(match, " ");
									
									bw.write(word +",");
								}
							}
						}
					}
						
					
					
				
					
					
				    
				}
				bw.write("\n");
				bw.flush();
				bw.close();
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	
	
	public void analzeMapEv(Ev mapEv){
		
		
				
		
	}

	public boolean removeSemantic(String semantic) {

		boolean result = false;

		for (String removeSemantic : removeSemanticType) {
			if (removeSemantic.equals(semantic)) {
				result = true;
			}
		}

		return result;

	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public MetaMapApi getMetaMapApi() {
		return metaMapApi;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public boolean isIsremoveSemantic() {
		return isremoveSemantic;
	}

	public void setIsremoveSemantic(boolean isremoveSemantic) {
		this.isremoveSemantic = isremoveSemantic;
	}

	public ArrayList<Ev> getResultMetaMappingList() {
		return resultMetaMappingList;
	}

	
	

}
