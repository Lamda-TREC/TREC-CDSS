#include <math.h>
#include <iostream>
#include <fstream>
#include <string>
#include <cstdlib>
#include <stdio.h>
#include <stdlib.h>

using namespace std;

int rDoc = 0;
int rel = 0;
double total_quess = 0;
double total_positive = 0;
const int total = 454;

void evaluate(int num, string type, string file, string dos) {
	string banPath = "answer";
	//put file path
	string brePath = "C:\\filepath";
	string second = type + "\\" + file + dos;
	string csv = ".csv";
	string anPath;
	string rePath;
	string answer;
	string result;
	int ans;
	int res;

	int mAnswer[total];

	anPath = banPath + to_string(num) + csv;
	rePath = brePath + to_string(num) + csv;

	ifstream inAnswer(anPath, ios::in);
	ifstream inResult(rePath, ios::in);

	while (getline(inAnswer, answer, '\n')){
		ans = stoi(answer);
		mAnswer[rel] = ans;
		rel++;
	}

	int i = 0;
	while (getline(inResult, result, ',')){
		//	if (i >= 3 && (i % 2 == 1)) {
		if (i >= 5 && (i % 3 == 2)) {
			res = stoi(result);
			for (int j = 0; j < rel + 1; j++){
				if (res == mAnswer[j]){
					total_positive++;
				}
			}
			total_quess++;
			if (rel + 1 == total_positive)
				rDoc = total_positive;
		}
		i++;
	}
}

int main(void)
{
	ofstream total;
	total.open("Evaluation total.csv");
	total << ",precision,recall,fmeasure,r" << endl;

	ofstream file;
	string type = "Evaluation_DFR_";
	string fName;
	string dos = "summary_";;
	string csv = ".csv";

	file.open("Evaluation " + type + dos + csv);
	cout << "Evaluation " + type + dos + csv << endl;
	file << "precision,recall,fmeasure,r precision" << endl;
	total << type + dos << ",";

	double tPre = 0;
	double tRe = 0;
	double tF = 0;
	double tR = 0;

	for (int num = 1; num < 31; num++) {
		rDoc = 0;
		total_quess = 0;
		total_positive = 0;
		rel = 0;

		//precision, recall
		double precision = 0;
		double recall = 0;
		evaluate(num, type, fName, dos);
		precision = double(total_positive) / double(total_quess);
		recall = double(total_positive) / double(rel);
		//		cout << precision << endl;
		//		cout << recall << endl;
		tPre += precision;
		tRe += recall;

		//Fmeasure
		double beta = 1;
		beta = beta*beta;
		double fmeasure;
		if (beta*precision + recall != 0) fmeasure = (1 + beta) * precision*recall / (beta*precision + recall);
		else fmeasure = 0;
		//		cout << fmeasure << endl;
		tF += fmeasure;

		//Rprecision
		double r = double(rel);
		if (rDoc == 0) rDoc = total_positive;
		if (rel > total_quess)
			r = double(total_quess);

		r = rDoc / r;
		//		cout << r << endl;
		tR += r;

		file << precision << "," << recall << "," << fmeasure << "," << r << endl;

		cout << type << dos << num << "finish" << endl;
	}
	file << tPre / 30 << "," << tRe / 30 << "," << tF / 30 << "," << tR / 30 << endl;
	file.close();
	total << tPre / 30 << "," << tRe / 30 << "," << tF / 30 << "," << tR / 30 << endl;

	total.close();
	return 0;
}