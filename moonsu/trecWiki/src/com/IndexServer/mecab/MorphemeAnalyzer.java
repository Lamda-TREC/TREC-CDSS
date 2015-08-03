package com.IndexServer.mecab;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.chasen.mecab.Tagger;

import com.IndexServer.socketServer.Info;

public class MorphemeAnalyzer {

	static {
		try {
			System.loadLibrary("MeCab");
		} catch (UnsatisfiedLinkError e) {
			System.err
					.println("Cannot load the example native code.\nMake sure your LD_LIBRARY_PATH contains \'.\'\n"
							+ e);
			System.exit(1);
		}
	}

	public void koreanMorhemeContent(String targetFile) {

		String input = Info.CONTENT_FOLDER_PATH + targetFile + "text/"
				+ targetFile + ".txt";
		String output = Info.CONTENT_FOLDER_PATH + targetFile + "text/"
				+ targetFile + "_output.txt";
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(new File(input)));
			BufferedWriter bw = new BufferedWriter(new FileWriter(output));
			bw.write("DOC	X	");
			String str = null;
			Tagger tagger = new Tagger("-d /usr/local/lib/mecab/dic/mecab-ko-dic");
			while ((str = br.readLine()) != null) {

				String analyze = tagger.parse(str);
				String[] sub = analyze.split("\n");
				ArrayList<String> list = new ArrayList<String>();
				for (int j = 0; j < sub.length - 1; j++) {
					String[] morp = sub[j].split("\t");
					String word = morp[0];
					String prop = (morp[1].split(","))[0];

					if (word.equals(".")) {

					} else {
						if (prop.startsWith("NN") || prop.startsWith("SL")
								|| prop.startsWith("SH")) {
							if (!prop.equals("NNB")) {
								bw.write(word + " ");
							}
						}
					}
				}
			}
			bw.write("\n");
			bw.close();
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ArrayList<String> koreanMorhemeContentInfo(String title, String Summary) {
		
		ArrayList<String> result = new ArrayList<String>();
		String str = title + " " + Summary;
		Tagger tagger = new Tagger("-d /usr/local/lib/mecab/dic/mecab-ko-dic");
		String analyze = tagger.parse(str);
		String[] sub = analyze.split("\n");
		ArrayList<String> list = new ArrayList<String>();
		for (int j = 0; j < sub.length - 1; j++) {
			String[] morp = sub[j].split("\t");
			String word = morp[0];
			String prop = (morp[1].split(","))[0];

			if (word.equals(".")) {

			} else {
				if (prop.startsWith("NN") || prop.startsWith("SL")
						|| prop.startsWith("SH")) {
					if (!prop.equals("NNB")) {
						result.add(word);
					}
				}
			}
		}
		
		return result;

	}

}
