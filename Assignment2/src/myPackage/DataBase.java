package myPackage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DataBase {

	public static ArrayList<State> statesList = new ArrayList<State>();

	public static double[][] read_data(String fileName) {

		BufferedReader br = null;
		String line = null;
		String splitBy = ",";

		ArrayList<Measurement> measurementsList = new ArrayList<Measurement>();

		try {
			br = new BufferedReader(new FileReader(fileName));
			while ((line = br.readLine()) != null) {
				String[] values = line.split(splitBy);
				measurementsList.add(new Measurement(values[0], values[1],
						values[2], values[3], values[4]));
			}
			br.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		for (int i = 1; i < measurementsList.size(); i++) {
			double tmpTime = Double.parseDouble(measurementsList.get(i).time);
			int pos = -1;
			// Check if a state exists for a given time
			for (int j = 0; j < statesList.size(); j++) {
				if (statesList.get(j).time == tmpTime) {
					pos = j;
					break;
				}
			}
			// Create a new state
			if (pos == -1) {
				statesList.add(new State(measurementsList.get(i)));
			}
			// Add values to existing state
			else {
				statesList.get(pos).addData(measurementsList.get(i));
			}

		}

		// Raw values
		double[][] values = new double[statesList.size()][2 * statesList.get(0).buses
				.size()];

		for (int i = 0; i < statesList.size(); i++) {
			for (int j = 0; j < statesList.get(0).buses.size(); j++) {
				values[i][2 * j] = statesList.get(i).buses.get(j).voltage;
				values[i][2 * j + 1] = statesList.get(i).buses.get(j).angle;
			}

		}
		
		
		// // Check min&max voltages and angles
		// double[] minVolts = new double[statesList.get(0).buses.size()];
		// double[] maxVolts = new double[statesList.get(0).buses.size()];
		// double[] minAngles = new double[statesList.get(0).buses.size()];
		// double[] maxAngles = new double[statesList.get(0).buses.size()];
		//
		// for (int k = 0; k < statesList.size(); k++) {
		// if (k == 0) {
		// for (int j = 0; j < statesList.get(k).buses.size(); j++) {
		// minAngles[j] = statesList.get(k).buses.get(j).angle;
		// maxAngles[j] = statesList.get(k).buses.get(j).angle;
		// minVolts[j] = statesList.get(k).buses.get(j).voltage;
		// maxVolts[j] = statesList.get(k).buses.get(j).voltage;
		// }
		// } else {
		// for (int j = 0; j < statesList.get(k).buses.size(); j++) {
		// minAngles[j] = java.lang.Math.min(minAngles[j],
		// statesList.get(k).buses.get(j).angle);
		// maxAngles[j] = java.lang.Math.max(maxAngles[j],
		// statesList.get(k).buses.get(j).angle);
		// minVolts[j] = java.lang.Math.min(minVolts[j],
		// statesList.get(k).buses.get(j).voltage);
		// maxVolts[j] = java.lang.Math.max(maxVolts[j],
		// statesList.get(k).buses.get(j).voltage);
		// }
		// }
		// }

		// double[][] normValues = new double[statesList.size()][2 * statesList
		// .get(0).buses.size()];
		// for (int i = 0; i < statesList.size(); i++) {
		// for (int j = 0; j < statesList.get(0).buses.size(); j++) {
		// if (maxVolts[j] != minVolts[j]) {
		// normValues[i][2 * j] = (values[i][2 * j] - minVolts[j])
		// / (maxVolts[j] - minVolts[j]);
		// } else {
		// normValues[i][2 * j] = 1;
		// }
		//
		// if (maxAngles[j] != minAngles[j]) {
		// normValues[i][2 * j + 1] = (values[i][2 * j + 1] - minAngles[j])
		// / (maxAngles[j] - minAngles[j]);
		// } else {
		// normValues[i][2 * j + 1] = 1;
		// }
		//
		// }
		//
		// }
		//
		// return normValues;
		return values;

	}

	public static ArrayList<State> getStatesList() {
		return statesList;
	}
}
