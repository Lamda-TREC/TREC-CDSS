package com.nxmlindex.MetaMap;

import gov.nih.nlm.nls.metamap.Ev;
import gov.nih.nlm.nls.metamap.Mapping;
import gov.nih.nlm.nls.metamap.MetaMapApi;
import gov.nih.nlm.nls.metamap.MetaMapApiImpl;
import gov.nih.nlm.nls.metamap.PCM;
import gov.nih.nlm.nls.metamap.Result;
import gov.nih.nlm.nls.metamap.Utterance;

import java.util.ArrayList;
import java.util.List;

import com.nxmlindex.NxmlIndexApplication;
import com.nxmlindex.nlptools.CoreNLPTokenizer;

public class MetaMapApiConnector {

	private MetaMapApi metaMapApi;
	private String hostName;
	private String removeSemanticType[] = {"tmco","fndg","qlco","qnco","anim","bird","cnce","edac","enty","evnt","fish","ftcn","idcn","inpr","prog","pros","qlco","qnco","tmco"};
	
	private CoreNLPTokenizer token = new CoreNLPTokenizer();

	public MetaMapApiConnector(String hostName) {
		super();
		this.hostName = hostName;
	}

	public void Connection() {
		metaMapApi = new MetaMapApiImpl(hostName);
		

	}

	public List<Result> SendTextMetaMap(String text) {

		List<Result> resultList = null;

		//System.out.println("Send Text : " + text);
		resultList = metaMapApi.processCitationsFromString(text);
		
		return resultList;

	}

	public void AnalzeMappingMetaMap(List<Result> resultList, int type)
			throws Exception {

		Result result = resultList.get(0);

		for (Utterance utterance : result.getUtteranceList()) {
			/*System.out.println("Utterance:");
			System.out.println(" Id: " + utterance.getId());
			System.out.println(" Utterance text: " + utterance.getString());
			System.out.println(" Position: " + utterance.getPosition());*/

			for (PCM pcm : utterance.getPCMList()) {

				/*System.out.println("Mappings: "
						+ pcm.getPhrase().getPhraseText());*/
				if (pcm.getMappingList().size() > 0) {
					// for (Mapping map : pcm.getMappingList()) {
					Mapping map = pcm.getMappingList().get(0);
					//System.out.println(" Map Score: " + map.getScore());
					for (Ev mapEv : map.getEvList()) {
						//String semantic;
						switch (type) {
						case 0:
							for (String semantic : mapEv.getSemanticTypes()) {
							
							semantic = semantic.replaceAll("[^\\x00-\\x7F]", "");
							semantic = token.lemma(semantic);
								if (!removeSemantic(semantic)) {

									if (NxmlIndexApplication.DiaHashMap.size() != 0) {
										if (NxmlIndexApplication.DiaHashMap
												.containsKey(semantic)) {
											int count = NxmlIndexApplication.DiaHashMap
													.get(semantic);
											count++;
											NxmlIndexApplication.DiaHashMap
													.put(semantic, count);
										} else {
											NxmlIndexApplication.DiaHashMap
													.put(semantic, 1);
										}
									} else {
										NxmlIndexApplication.DiaHashMap.put(
												semantic, 1);
									}
								}
							}
							break;
						case 1:
							for (String semantic : mapEv.getSemanticTypes()) {
							//semantic = mapEv.getConceptName().toLowerCase();

							semantic = semantic.replaceAll("[^\\x00-\\x7F]", "");
							semantic = token.lemma(semantic);
								if (!removeSemantic(semantic)) {
									if (NxmlIndexApplication.TestHashMap
											.size() != 0) {
										if (NxmlIndexApplication.TestHashMap
												.containsKey(semantic)) {
											int count = NxmlIndexApplication.TestHashMap
													.get(semantic);
											count++;
											NxmlIndexApplication.TestHashMap
													.put(semantic, count);
										} else {
											NxmlIndexApplication.TestHashMap
													.put(semantic, 1);
										}
									} else {
										NxmlIndexApplication.TestHashMap.put(
												semantic, 1);
									}
								}
							}
							break;
						case 2:
							for (String semantic : mapEv.getSemanticTypes()) {
							//semantic = mapEv.getConceptName().toLowerCase();

							semantic = semantic.replaceAll("[^\\x00-\\x7F]", "");
							semantic = token.lemma(semantic);
								if (!removeSemantic(semantic)) {
									if (NxmlIndexApplication.TreatHashMap
											.size() != 0) {
										if (NxmlIndexApplication.TreatHashMap
												.containsKey(semantic)) {
											int count = NxmlIndexApplication.TreatHashMap
													.get(semantic);
											count++;
											NxmlIndexApplication.TreatHashMap
													.put(semantic, count);
										} else {
											NxmlIndexApplication.TreatHashMap
													.put(semantic, 1);
										}
									} else {
										NxmlIndexApplication.TreatHashMap.put(
												semantic, 1);
									}
								}
							}
							break;

						default:
							break;
						}

					/*	System.out.println("   Concept Name: "
								+ mapEv.getConceptName());

						System.out.println("   Semantic Types: "
								+ mapEv.getSemanticTypes());*/

					}
				//	 }
				}
			}
		}
	}
	
	
	public void AnalzeMappingMetaMap2(List<Result> resultList, String pmcid)
			throws Exception {

		Result result = resultList.get(0);

		for (Utterance utterance : result.getUtteranceList()) {
			/*System.out.println("Utterance:");
			System.out.println(" Id: " + utterance.getId());
			System.out.println(" Utterance text: " + utterance.getString());
			System.out.println(" Position: " + utterance.getPosition());*/

			for (PCM pcm : utterance.getPCMList()) {

				/*System.out.println("Mappings: "
						+ pcm.getPhrase().getPhraseText());*/
				if (pcm.getMappingList().size() > 0) {
					// for (Mapping map : pcm.getMappingList()) {
					Mapping map = pcm.getMappingList().get(0);
					//System.out.println(" Map Score: " + map.getScore());
					for (Ev mapEv : map.getEvList()) {
						//String semantic;
						
						for (String semantic : mapEv.getSemanticTypes()) {
						
						/*	semantic = semantic.replaceAll("[^\\x00-\\x7F]", "");
							semantic = token.lemma(semantic);*/
								if (!removeSemantic(semantic)) {

									if (NxmlIndexApplication.DocumentHashMap.get(pmcid).size() != 0) {
										if (NxmlIndexApplication.DocumentHashMap.get(pmcid)
												.containsKey(semantic)) {
											int count = NxmlIndexApplication.DocumentHashMap.get(pmcid)
													.get(semantic);
											count++;
											NxmlIndexApplication.DocumentHashMap.get(pmcid)
													.put(semantic, count);
										} else {
											NxmlIndexApplication.DocumentHashMap.get(pmcid)
													.put(semantic, 1);
										}
									} else {
										NxmlIndexApplication.DocumentHashMap.get(pmcid).put(
												semantic, 1);
									}
								}
								
								
								
						}
						
						/*switch (type) {
						case 0:
							for (String semantic : mapEv.getSemanticTypes()) {
							
							semantic = semantic.replaceAll("[^\\x00-\\x7F]", "");
							semantic = token.lemma(semantic);
								if (!removeSemantic(semantic)) {

									if (NxmlIndexApplication.DiaMetaMapHashMap.size() != 0) {
										if (NxmlIndexApplication.DiaMetaMapHashMap
												.containsKey(semantic)) {
											int count = NxmlIndexApplication.DiaMetaMapHashMap
													.get(semantic);
											count++;
											NxmlIndexApplication.DiaMetaMapHashMap
													.put(semantic, count);
										} else {
											NxmlIndexApplication.DiaMetaMapHashMap
													.put(semantic, 1);
										}
									} else {
										NxmlIndexApplication.DiaMetaMapHashMap.put(
												semantic, 1);
									}
								}
							}
							break;
						case 1:
							for (String semantic : mapEv.getSemanticTypes()) {
							//semantic = mapEv.getConceptName().toLowerCase();

							semantic = semantic.replaceAll("[^\\x00-\\x7F]", "");
							semantic = token.lemma(semantic);
								if (!removeSemantic(semantic)) {
									if (NxmlIndexApplication.TestMetaMapHashMap
											.size() != 0) {
										if (NxmlIndexApplication.TestMetaMapHashMap
												.containsKey(semantic)) {
											int count = NxmlIndexApplication.TestMetaMapHashMap
													.get(semantic);
											count++;
											NxmlIndexApplication.TestMetaMapHashMap
													.put(semantic, count);
										} else {
											NxmlIndexApplication.TestMetaMapHashMap
													.put(semantic, 1);
										}
									} else {
										NxmlIndexApplication.TestMetaMapHashMap.put(
												semantic, 1);
									}
								}
							}
							break;
						case 2:
							for (String semantic : mapEv.getSemanticTypes()) {
							//semantic = mapEv.getConceptName().toLowerCase();

							semantic = semantic.replaceAll("[^\\x00-\\x7F]", "");
							semantic = token.lemma(semantic);
								if (!removeSemantic(semantic)) {
									if (NxmlIndexApplication.TreatMetaMapHashMap
											.size() != 0) {
										if (NxmlIndexApplication.TreatMetaMapHashMap
												.containsKey(semantic)) {
											int count = NxmlIndexApplication.TreatMetaMapHashMap
													.get(semantic);
											count++;
											NxmlIndexApplication.TreatMetaMapHashMap
													.put(semantic, count);
										} else {
											NxmlIndexApplication.TreatMetaMapHashMap
													.put(semantic, 1);
										}
									} else {
										NxmlIndexApplication.TreatMetaMapHashMap.put(
												semantic, 1);
									}
								}
							}
							break;

						default:
							break;
						}*/

					/*	System.out.println("   Concept Name: "
								+ mapEv.getConceptName());

						System.out.println("   Semantic Types: "
								+ mapEv.getSemanticTypes());*/

					}
				//	 }
				}
			}
		}
	}
	
	
	public ArrayList<String> AnalzeMappingMetaMap3(List<Result> resultList, String pmcid)
			throws Exception {

		Result result = resultList.get(0);
		ArrayList<String> metamapResult = new ArrayList<String>();

		for (Utterance utterance : result.getUtteranceList()) {
			

			for (PCM pcm : utterance.getPCMList()) {

			
				if (pcm.getMappingList().size() > 0) {
					// for (Mapping map : pcm.getMappingList()) {
					Mapping map = pcm.getMappingList().get(0);
					//System.out.println(" Map Score: " + map.getScore());
					for (Ev mapEv : map.getEvList()) {
						//String semantic;
						
						for (String semantic : mapEv.getSemanticTypes()) {
						
							if(!metamapResult.contains(semantic))
							{
								metamapResult.add(semantic);
							}
								
								
						}
						
					
					}
				}
			}
		}
		
		return metamapResult;
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

}
