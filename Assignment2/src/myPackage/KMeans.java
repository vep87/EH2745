package myPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class KMeans {

	public static ArrayList<State> statesList = new ArrayList<State>();
	static double[][] values = null;
	static double[][] centroids = null;

	static double[][] cluster1 = null;
	static double[][] cluster2 = null;
	static double[][] cluster3 = null;
	static double[][] cluster4 = null;

	static int index1, index2, index3, index4;
	static double dist1, dist2, dist3, dist4;

	public static void execute(DataBase input) {

		statesList = input.getStatesList();
		values = input.getValues();
		initialize();
		cal_centroids();
		K_clusters();

		System.out.println("Cluster 1 = " + index1 + " states.");
		System.out.println("Cluster 2 = " + index2 + " states.");
		System.out.println("Cluster 3 = " + index3 + " states.");
		System.out.println("Cluster 4 = " + index4 + " states.");

		System.out.println("Centroids:");
		System.out
				.println(Arrays.deepToString(centroids).replace("], ", "]\n"));

		results();
		print_CSV();
	}

	public static void initialize() {

		int s = statesList.size(); // Number of states
		int m = values[0].length; // Number of measurements per state

		centroids = new double[4][m];
		cluster1 = new double[s][m];
		cluster2 = new double[s][m];
		cluster3 = new double[s][m];
		cluster4 = new double[s][m];

		int x = s / 8;
		for (int i = 0; i < m; i++) {
			centroids[0][i] = values[x][i];
			centroids[1][i] = values[3 * x][i];
			centroids[2][i] = values[5 * x][i];
			centroids[3][i] = values[7 * x][i];
		}

		index1 = 0;
		index2 = 0;
		index3 = 0;
		index4 = 0;

		for (int i = 0; i < s; i++) {
			for (int j = 0; j < m; j++) {
				dist1 += ((centroids[0][j] - values[i][j]) * (centroids[0][j] - values[i][j]));

				dist2 += ((centroids[1][j] - values[i][j]) * (centroids[1][j] - values[i][j]));

				dist3 += ((centroids[2][j] - values[i][j]) * (centroids[2][j] - values[i][j]));

				dist4 += ((centroids[3][j] - values[i][j]) * (centroids[3][j] - values[i][j]));

			}

			dist1 = Math.sqrt(dist1);
			dist2 = Math.sqrt(dist2);
			dist3 = Math.sqrt(dist3);
			dist4 = Math.sqrt(dist4);

			if (dist1 < dist2) {
				if (dist1 < dist3) {
					if (dist1 < dist4) {
						for (int j = 0; j < m; j++) {
							cluster1[index1][j] = values[i][j];
							statesList.get(i).label = "cluster1";

						}
						index1++;

					} else {
						for (int j = 0; j < m; j++) {
							cluster4[index4][j] = values[i][j];
							statesList.get(i).label = "cluster4";
						}
						index4++;
					}
				}

				else if (dist3 < dist4) {
					for (int j = 0; j < m; j++) {
						cluster3[index3][j] = values[i][j];
						statesList.get(i).label = "cluster3";
					}
					index3++;

				} else {
					for (int j = 0; j < m; j++) {
						cluster4[index4][j] = values[i][j];
						statesList.get(i).label = "cluster4";
					}
					index4++;

				}
			} else if (dist2 < dist3) {
				if (dist2 < dist4) {
					for (int j = 0; j < m; j++) {
						cluster2[index2][j] = values[i][j];
						statesList.get(i).label = "cluster2";
					}
					index2++;

				} else {
					for (int j = 0; j < m; j++) {
						cluster4[index4][j] = values[i][j];
						statesList.get(i).label = "cluster4";
					}
					index4++;

				}
			}

			else if (dist3 < dist4) {
				for (int j = 0; j < m; j++) {
					cluster3[index3][j] = values[i][j];
					statesList.get(i).label = "cluster3";
				}
				index3++;

			} else {
				for (int j = 0; j < m; j++) {
					cluster4[index4][j] = values[i][j];
					statesList.get(i).label = "cluster4";
				}
				index4++;

			}

		}
	}

	public static void cal_centroids() {

		double[][] new_centroids = new double[centroids.length][centroids[0].length];

		for (int j = 0; j < 4; j++) {
			for (int i = 0; i < index1; i++) {
				new_centroids[0][j] += (cluster1[i][j] / (index1));
				centroids[0][j] = new_centroids[0][j];
			}
		}

		for (int j = 0; j < 4; j++) {
			for (int i = 0; i < index2; i++) {
				new_centroids[1][j] += (cluster2[i][j] / (index2));
				centroids[1][j] = new_centroids[1][j];

			}
		}

		for (int j = 0; j < 4; j++) {
			for (int i = 0; i < index3; i++) {
				new_centroids[2][j] += (cluster3[i][j] / (index3));
				centroids[2][j] = new_centroids[2][j];
			}
		}

		for (int j = 0; j < 4; j++) {
			for (int i = 0; i < index4; i++) {
				new_centroids[3][j] += (cluster4[i][j] / (index4));
				centroids[3][j] = new_centroids[3][j];
			}
		}
	}

	public static void K_clusters() {

		int s = values.length;
		int m = values[0].length;

		double[][] temp_values = new double[s][m];
		double[][] old_centroids = new double[centroids.length][centroids[0].length];
		double dif1 = 0, dif2 = 0, dif3 = 0, dif4 = 0;
		double tol = 0.00001;

		while (true) {

			for (int i = 0; i < index1; i++) {
				for (int j = 0; j < m; j++) {
					temp_values[i][j] = cluster1[i][j];

				}
			}
			for (int i = 0; i < index2; i++) {
				for (int j = 0; j < m; j++) {
					temp_values[i + index1][j] = cluster2[i][j];

				}
			}
			for (int i = 0; i < index3; i++) {
				for (int j = 0; j < m; j++) {
					temp_values[i + index1 + index2][j] = cluster3[i][j];

				}
			}
			for (int i = 0; i < index4; i++) {
				for (int j = 0; j < m; j++) {
					temp_values[i + index1 + index2 + index3][j] = cluster4[i][j];

				}
			}

			index1 = 0;
			index2 = 0;
			index3 = 0;
			index4 = 0;

			for (int i = 0; i < s; i++) {
				for (int j = 0; j < m; j++) {
					dist1 += ((centroids[0][j] - values[i][j]) * (centroids[0][j] - values[i][j]));

					dist2 += ((centroids[1][j] - values[i][j]) * (centroids[1][j] - values[i][j]));

					dist3 += ((centroids[2][j] - values[i][j]) * (centroids[2][j] - values[i][j]));

					dist4 += ((centroids[3][j] - values[i][j]) * (centroids[3][j] - values[i][j]));

				}
				dist1 = Math.sqrt(dist1);
				dist2 = Math.sqrt(dist2);
				dist3 = Math.sqrt(dist3);
				dist4 = Math.sqrt(dist4);

				if (dist1 < dist2) {
					if (dist1 < dist3) {
						if (dist1 < dist4) {
							for (int j = 0; j < m; j++) {
								cluster1[index1][j] = values[i][j];
								statesList.get(i).label = "cluster1";
							}
							index1++;

						} else {
							for (int j = 0; j < m; j++) {
								cluster4[index4][j] = values[i][j];
								statesList.get(i).label = "cluster4";
							}
							index4++;

						}
					}

					else if (dist3 < dist4) {
						for (int j = 0; j < m; j++) {
							cluster3[index3][j] = values[i][j];
							statesList.get(i).label = "cluster3";
						}
						index3++;

					} else {
						for (int j = 0; j < m; j++) {
							cluster4[index4][j] = values[i][j];
							statesList.get(i).label = "cluster4";
						}
						index4++;

					}
				} else if (dist2 < dist3) {
					if (dist2 < dist4) {
						for (int j = 0; j < m; j++) {
							cluster2[index2][j] = values[i][j];
							statesList.get(i).label = "cluster2";
						}
						index2++;

					} else {
						for (int j = 0; j < m; j++) {
							cluster4[index4][j] = values[i][j];
							statesList.get(i).label = "cluster4";
						}
						index4++;

					}
				}

				else if (dist3 < dist4) {
					for (int j = 0; j < m; j++) {
						cluster3[index3][j] = values[i][j];
						statesList.get(i).label = "cluster3";
					}
					index3++;

				} else {
					for (int j = 0; j < m; j++) {
						cluster4[index4][j] = values[i][j];
						statesList.get(i).label = "cluster4";
					}
					index4++;

				}

			}

			for (int j = 0; j < m; j++) {
				old_centroids[0][j] = centroids[0][j];
				old_centroids[1][j] = centroids[1][j];
				old_centroids[2][j] = centroids[2][j];
				old_centroids[3][j] = centroids[3][j];

			}

			dif1 = 0;
			dif2 = 0;
			dif3 = 0;
			dif4 = 0;
			cal_centroids();

			for (int j = 0; j < m; j++) {
				dif1 += (Math
						.sqrt(((old_centroids[0][j] - centroids[0][j]) * (old_centroids[0][j] - centroids[0][j]))))
						/ index1;
				dif2 += (Math
						.sqrt(((old_centroids[1][j] - centroids[1][j]) * (old_centroids[1][j] - centroids[1][j]))))
						/ index2;
				dif3 += (Math
						.sqrt(((old_centroids[2][j] - centroids[2][j]) * (old_centroids[2][j] - centroids[2][j]))))
						/ index3;
				dif4 += (Math
						.sqrt(((old_centroids[3][j] - centroids[3][j]) * (old_centroids[3][j] - centroids[3][j]))))
						/ index4;

			}

			if (dif1 <= tol && dif2 <= tol && dif3 <= tol && dif4 <= tol) {
				break;
			}

		}

	}

	public static void results() {

		double[] angles = new double[statesList.size()];
		double[] volts = new double[statesList.size()];

		for (int i = 0; i < statesList.size(); i++) {
			statesList.get(i).calculations();
			angles[i] = statesList.get(i).meanAngle;
			volts[i] = statesList.get(i).meanVoltage;
		}
		System.out.println(Arrays.toString(angles));
		System.out.println(Arrays.toString(volts));

		double[] centAngles = new double[centroids.length];
		double[] centVolts = new double[centroids.length];

		for (int i = 0; i < centroids.length; i++) {
			double sumAngles = 0;
			double sumVolts = 0;
			for (int j = 0; j < values[0].length; j++) {
				if (j % 2 == 0) {
					sumVolts += centroids[i][j];
				} else {
					sumAngles += centroids[i][j];
				}
			}
			centAngles[i] = sumAngles / (values[0].length / 2);
			centVolts[i] = sumVolts / (values[0].length / 2);
		}
		System.out.println(Arrays.toString(centAngles));
		System.out.println(Arrays.toString(centVolts));
	}

	public static void print_CSV() {
		PrintWriter pw;
		try {
			ArrayList<PrintWriter> pwArray = new ArrayList<PrintWriter>();
			for (int i = 0; i < 4; i++) {
				String name = "cluster_" + (i + 1) + ".csv";
				pw = new PrintWriter(new File(name));
				pwArray.add(pw);
			}

			for (int ii = 0; ii < 4; ii++) {
				StringBuilder sb = new StringBuilder();
				String header = "time,";
				for (int j = 0; j < (values[0].length); j++) {
					int mod = j % 2;
					if (j < (values[0].length) - 1) {
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
				for (int i = 0; i < statesList.size(); i++) {
					if (statesList.get(i).label.equals("cluster"
							+ String.valueOf(ii + 1))) {
						String line = statesList.get(i).time + ","
								+ statesList.get(i).stringValues();
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
