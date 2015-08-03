package com.bordafuse.BordaFuse;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class Main 
{
    public static void main( String[] args ) throws Exception
    {

    	String filepath[] = {"DFR_2015_dfr_diagpref_1000\\DFR summary","DFR_2015_Weight_OnlydiagPrefKeyword_1000\\DFR summary"};
    	int rankNum =1000;
    	LoadFiles load = new LoadFiles();
    	
    	load.init(2, filepath);
    	
    	load.filesLoader(rankNum);
    	
    	BordaFuseCaluculator bf = new BordaFuseCaluculator();
    	
    	bf.calculateBordaFuse(2);
    	
    	
    }
}
