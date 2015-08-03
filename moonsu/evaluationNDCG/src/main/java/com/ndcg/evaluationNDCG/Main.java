package com.ndcg.evaluationNDCG;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class Main 
{
    public static void main( String[] args ) throws Exception
    {
    	
    	int rankNum = 400;
        answerLoader ansloader = new answerLoader();
        
        ansloader.init();
        ansloader.loadAnswer();
        
        MyRankLoader myrankloader = new MyRankLoader();
        
        myrankloader.init();
        myrankloader.myRankLoader3("ranks\\DFR_400\\DFR description",rankNum);
        
        
        NDCGEvaluator ndcg = new NDCGEvaluator();
        
        ndcg.calculateINDCG(rankNum);
        
        for(int topic = 1; topic <31;topic++)
        {
        	System.out.println("Topic "+topic+" IDCG : "+info.TopicIDCG.get(topic));
        }
        
        ndcg.calculateDCG(rankNum);
        
        for(int topic = 1; topic <31;topic++)
        {
        	System.out.println("Topic "+topic+" DCG : "+info.TopicDCG.get(topic));
        }
        
        ndcg.calculatNDCG();
        
        for(int topic = 1; topic <31;topic++)
        {
        	System.out.println("Topic "+topic+" NDCG : "+info.TopicNDCG.get(topic));
        }
        
        System.out.println("평균 : " + info.AverageNDCG);
        
        
    }
}
