package com.nxmlindex.util;

import java.io.File;
import java.util.concurrent.BlockingQueue;

public class FilesScanner {
	
	private String path;
	private BlockingQueue<String> NxmlFilesQueue;
	
	
	
	public FilesScanner(String path, BlockingQueue<String> NxmlFilesQueue) {
		super();
		this.path = path;
		this.NxmlFilesQueue = NxmlFilesQueue;
	}



	public void FilesScan(){
		
		File dir = new File(path);
        FilesCrawler fileCrawler = new FilesCrawler(NxmlFilesQueue,
                 new NxmlFileFilter(), dir);
        fileCrawler.startFilesCrawl();

       
	}

	
	

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}



	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}



	public BlockingQueue<String> getNxmlFilesQueue() {
		return NxmlFilesQueue;
	}



	public void setNxmlFilesQueue(BlockingQueue<String> NxmlFilesQueue) {
		this.NxmlFilesQueue = NxmlFilesQueue;
	}
	
	
	

}
