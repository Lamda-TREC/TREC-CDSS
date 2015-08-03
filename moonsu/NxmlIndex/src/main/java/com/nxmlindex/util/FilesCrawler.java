package com.nxmlindex.util;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class FilesCrawler{
	private BlockingQueue<String> filePathQueue;
	private ConcurrentSkipListSet<File> indexedFiles = new ConcurrentSkipListSet();
	private FileFilter fileFilter;
	private File root;
	private ExecutorService exec = Executors.newCachedThreadPool();

	
	public FilesCrawler(BlockingQueue<String> NxmlFilesQueue, final NxmlFileFilter nxmlFileFilter,
			File root) {
		this.filePathQueue = NxmlFilesQueue;
		this.root = root;
		this.fileFilter = new FileFilter() {
            public boolean accept(File f) {
                return f.isDirectory() || nxmlFileFilter.accept(f);
            }
        };
	}

	

	public void startFilesCrawl() {

		submitCrawlTask(root);

	}

	private void submitCrawlTask(File currentfile) {
		CrawlTask crawlTask = new CrawlTask(currentfile);
		crawlTask.startTaskCrawl();
	}

	private class CrawlTask {
		private final File file;

		CrawlTask(File file) {

			this.file = file;
		}

		public void startTaskCrawl() {
			if (Thread.currentThread().isInterrupted())
				return;

			File[] entries = file.listFiles(fileFilter);

			if (entries != null) {
				for (File entry : entries)
					if (entry.isDirectory())
						submitCrawlTask(entry);
					else if (entry != null && !indexedFiles.contains(entry)) {
						indexedFiles.add(entry);
						try {
							filePathQueue.put(entry.getAbsolutePath());
							DevideData.pmcList.add(entry.getName().split("\\.")[0]);
							System.out.println("Files Path : "+ entry.getAbsolutePath()+" "+entry.getName().split("\\.")[0]);
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
						}
					}
			}
		}
	}

}
