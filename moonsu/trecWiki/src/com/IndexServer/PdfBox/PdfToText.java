package com.IndexServer.PdfBox;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import com.IndexServer.socketServer.Info;

public class PdfToText{

	public PdfToText() {
		super();
	}


	public boolean extractingText(String category,String id) {

		boolean result = true;
		String input = Info.CONTENT_FOLDER_PATH+category+"/"+id+".pdf";
		make_dir(Info.CONTENT_FOLDER_PATH+category+"/text");
		String extractTxtFile = Info.CONTENT_FOLDER_PATH+category+"/text/"+id+ ".txt";

		PDDocument pdDocument = null;
		try {

			pdDocument = PDDocument.load(input);

		} catch (IOException e) {
			e.printStackTrace();
		}

		
		
	
		FileOutputStream fileOutputStream = openOutputStream(new File(
				extractTxtFile));
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
				fileOutputStream);

		BufferedWriter bufferedWriter = null;
		bufferedWriter = new BufferedWriter(outputStreamWriter);

		PDFTextStripper stripper = null;
		try {

			stripper = new PDFTextStripper();

		} catch (IOException e) {

			System.out
					.println("TextExtraction-extractingText ERROR: PDFTextStripper");
		}

		stripper.setStartPage(1);
		// stripper.setEndPage(11);
		try {

			stripper.writeText(pdDocument, bufferedWriter);

		} catch (IOException e) {

			System.out
					.println("TextExtraction-extractingText ERROR: text");
		}

		try {

			pdDocument.close();

		} catch (IOException e) {

			System.out
					.println("TextExtraction-extractingText ERROR: PDDocument close");
		}

		IOUtils.closeQuietly(bufferedWriter);

		return result;
	}

	public FileOutputStream openOutputStream(File file) {

		FileOutputStream fileOutputStream = null;

		try {

			fileOutputStream = new FileOutputStream(file);

		} catch (Exception e) {

			System.out.println("TextExtraction-openOutputStream ERROR: "
					+ e.getMessage());
		}

		return fileOutputStream;
	}
	
	public void make_dir(String dir) {
		File desti = new File(dir);

		if (!desti.exists()) {
			desti.mkdirs();
		} else {

		}
	}
}
