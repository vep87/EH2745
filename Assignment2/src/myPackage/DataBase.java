package myPackage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DataBase {

	private ArrayList<State> statesList;
	private double[][] values;
	
	public DataBase(String fileName){
		this.statesList = new ArrayList<State>();
		this.values =read_data(fileName);
	}

	public double[][] read_data(String fileName) {

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
			for (int j = 0; j < this.statesList.size(); j++) {
				if (this.statesList.get(j).time == tmpTime) {
					pos = j;
					break;
				}
			}
			// Create a new state
			if (pos == -1) {
				this.statesList.add(new State(measurementsList.get(i)));
			}
			// Add values to existing state
			else {
				this.statesList.get(pos).addData(measurementsList.get(i));
			}

		}

		// Raw values
		double[][] values = new double[this.statesList.size()][2 * this.statesList.get(0).buses
				.size()];

		for (int i = 0; i < this.statesList.size(); i++) {
			for (int j = 0; j < this.statesList.get(0).buses.size(); j++) {
				values[i][2 * j] = this.statesList.get(i).buses.get(j).voltage;
				values[i][2 * j + 1] = this.statesList.get(i).buses.get(j).angle;
			}

		}
		

		return values;

	}

	public ArrayList<State> getStatesList() {
		return this.statesList;
	}
	
	public double[][] getValues() {
		return this.values;
	}
	
}
