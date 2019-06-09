package myPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class Knn {

	public static void execute(DataBase learnData, DataBase testData) {

		int K = 30;
		double[][] lValues = learnData.getValues();
		double[][] tValues = testData.getValues();

		ArrayList<State> learnStatesList = learnData.getStatesList();
		ArrayList<State> testStatesList = testData.getStatesList();

		int rowsLearn = learnData.getValues().length;
		int colLearn = learnData.getValues()[0].length;
		int rowsTest = testData.getValues().length;
		int colTest = testData.getValues()[0].length;

		// a test value
		for (int i = 0; i < rowsTest; i++) {

			ArrayList<Result> resultList = new ArrayList<Result>();

			for (int j = 0; j < rowsLearn; j++) {
				double dist = 0.0;

				for (int k = 0; k < colLearn; k++) {

					dist = dist + Math.pow((lValues[j][k] - tValues[i][k]), 2);
				}

				double distance = Math.sqrt(dist);

				resultList.add(new Result(distance, learnData.getStatesList()
						.get(j).label));
			}

			Collections.sort(resultList, new DistanceComparator());

			int c1 = 0, c2 = 0, c3 = 0, c4 = 0;
			for (int h = 0; h < K; h++) {
				String c;
				c = resultList.get(h).label;
				switch (c) {
				case "cluster1":
					c1++;
					break;
				case "cluster2":
					c2++;
					break;
				case "cluster3":
					c3++;
					break;
				case "cluster4":
					c3++;
					break;
				}
			}

			if (c1 > c2) {
				if (c1 > c3) {
					if (c1 > c4) {
						testStatesList.get(i).label = "cluster1";
					} else {
						testStatesList.get(i).label = "cluster4";
					}
				} else if (c3 > c4) {
					testStatesList.get(i).label = "cluster3";
				} else {
					testStatesList.get(i).label = "cluster4";
				}
			} else if (c2 > c3) {
				if (c2 > c4) {
					testStatesList.get(i).label = "cluster2";
				} else {
					testStatesList.get(i).label = "cluster4";
				}
			} else if (c3 > c4) {
				testStatesList.get(i).label = "cluster3";
			} else {
				testStatesList.get(i).label = "cluster4";

			}

		}

		for (int x = 0; x < testStatesList.size(); x++) {
			System.out.println(testStatesList.get(x).label);
		}
		
		print_CSV_test(testData);
		
	}
	
	public static void print_CSV_test(DataBase testData) {
		PrintWriter pw;
		try {
			ArrayList<PrintWriter> pwArray = new ArrayList<PrintWriter>();
			for (int i = 0; i < 4; i++) {
				String name = "TestCluster_" + (i + 1) + ".csv";
				pw = new PrintWriter(new File(name));
				pwArray.add(pw);
			}

			for (int ii = 0; ii < 4; ii++) {
				StringBuilder sb = new StringBuilder();
				String header = "time,";
				for (int j = 0; j < (testData.getValues()[0].length); j++) {
					int mod = j % 2;
					if (j < (testData.getValues()[0].length) - 1) {
						if (mod == 0) {
							header += "ANG_" + ((j / 2) + 1) + ",";
						} else {
							header += "VOL_" + (j / 2 + 1) + ",";
						}
					} else {
						if (mod == 0) {
							header += "ANG_" + ((j / 2) + 1) + ",";
						} else {
							header += "VOL_" + (j / 2 + 1) + "\n";
						}
					}
				}
				sb.append(header);
				for (int i = 0; i < testData.getStatesList().size(); i++) {
					if (testData.getStatesList().get(i).label.equals("cluster"
							+ String.valueOf(ii + 1))) {
						String line = testData.getStatesList().get(i).time + ","
								+ testData.getStatesList().get(i).stringValues();
						sb.append(line);
					}
				}
				pw = pwArray.get(ii);
				pw.write(sb.toString());
				pw.close();
			}

			System.out.println("CSV created!");

		} catch (FileNotFoundException e) {

			System.out.println("Problem occured while writing the CSV file!!!");
			// e.printStackTrace();
		}
	}

}
