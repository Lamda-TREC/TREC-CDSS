package com.IndexServer.socketServer;

import java.io.IOException;
import java.util.ArrayList;

import com.IndexServer.Bean.Content;

public class TestMain {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		
		String patharray [] = {"1. Hypothyroidism","2. Meningitis","3. Epiglottitis","4. Paroxysmal nocturnal hemoglobinuria","5. Atrial fibrillation","6. Foreign body","8. Heart failure","9. Chronic obstructive pulmonary disease","10. Creutzfeldt_Jakob disease", "11. Giardiasis", "12. Aspergilloma", "12. Aspergillosis","13. Dengue fever", "14. Community-acquired pneumonia", "15. Osteomyelitis", "16. Ectopic pregnancy","17. Iron-deficiency anemia", "18. Lyme disease", "19. Kawasaki disease", "20. Joint dislocation"};
		
		for(int i=19;i<patharray.length;i++)
		{
			TestCBIR cbir = new TestCBIR();
			ArrayList<Content> contentList = new ArrayList<Content>();
			Content content = new Content();
			content.setCategory("wiki_noRef");
			content.setDescription("");
			content.setTitle("");
			content.setId(patharray[i]);
			contentList.add(content);
			String filename = "wikiKeyword.csv";
			
			for(Content data : contentList)
			{
				//if(!data.getCategory().equals("Internet of Things"))
				//System.out.println(data.getId());
					cbir.makeCBIR(data,filename);	
			}
		}
		/*TestCBIR cbir = new TestCBIR();
		ArrayList<Content> contentList = new ArrayList<Content>();
		Content content = new Content();
		content.setCategory("wiki_noRef");
		content.setDescription("");
		content.setTitle("");
		content.setId("1. Hypothyroidism");
		contentList.add(content);
		String filename = "wikiKeyword.csv";
		
		for(Content data : contentList)
		{
			//if(!data.getCategory().equals("Internet of Things"))
			//System.out.println(data.getId());
				cbir.makeCBIR(data,filename);	
		}*/
		//ArrayList<Content> contentList = cbir.getContentList();
	/*	for(Content data : contentList)
		{
			//if(data.getCategory().equals("Web Scale"))
			cbir.MakeinformationIndex(data);
		}*/
		
		
	/*	cbir.testCBIR();
	
		cbir.testEvaluation();*/
	}

}
