/**
 * 
 */
package com.nxmlindex.service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.nxmlindex.util.FilesScanner;

/**
 * <pre>
 * com.nxmlindex.service
 *   |_ FilesScannerService.java
 * </pre>
 * 
 * Desc : 
 * @Company : LAMDA in Ajou Univ
 * @Author  : ChaMunsu
 * @Date    : 2015. 5. 12. 오후 5:54:32
 * @Version : 
 */
public class FilesScannerService {
	
	private BlockingQueue<String> NxmlFilesQueue;
	
	public void FilesScanNxml(String num) {

		System.out.println("Start Nxml Index");
		NxmlFilesQueue = new LinkedBlockingQueue<String>();
		System.out.println("Files Scan 설정");
		FilesScanner scanner = new FilesScanner(
				"D:\\문수\\DATA\\TRECDATA\\pmcTextXML\\pmc-text-"+num+".tar\\pmc-text-"+num,
				NxmlFilesQueue);
		System.out.println("Files Scan 시작");
		scanner.FilesScan();
		System.out.println("Files Scan 완료");

	}

	public void printNxmlFileQueue() {

		for (String path : NxmlFilesQueue) {
			
			System.out.println(path);
			
		}
	}
	

	/**
	 * @return the nxmlFilesQueue
	 */
	public BlockingQueue<String> getNxmlFilesQueue() {
		return NxmlFilesQueue;
	}

	/**
	 * @param nxmlFilesQueue
	 *            the nxmlFilesQueue to set
	 */
	public void setNxmlFilesQueue(BlockingQueue<String> nxmlFilesQueue) {
		NxmlFilesQueue = nxmlFilesQueue;
	}

}
