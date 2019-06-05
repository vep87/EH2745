package myPackage;

import java.util.ArrayList;

public class State {

	public double time;
	public String label;
	public double[] minAngle, maxAngle, minVoltage, maxVoltage;
	public ArrayList<Bus> buses = new ArrayList<Bus>();

	public State(Measurement readMeasurement) {

		// Create new state
		this.time = Double.parseDouble(readMeasurement.time);
		// Create a new bus within the state
		Bus tmpBus = new Bus();
		tmpBus.busID = readMeasurement.sub_rdfid;
		// Check if value is a voltage or an angle
		if (isVoltage(readMeasurement.name)) {
			tmpBus.addBusVoltage(Double.parseDouble(readMeasurement.value));
		} else {
			tmpBus.addBusAngle(Double.parseDouble(readMeasurement.value));
		}
		// Add bus to the array
		this.buses.add(tmpBus);
	}

	private boolean isVoltage(String value) {
		if (value.substring(value.length() - 4, value.length()).equals("VOLT")) {
			return true;
		}
		return false;
	}

	public void addData(Measurement readMeasurement) {

		// Check if bus is already in the array
		int pos = -1;
		for (int k = 0; k < buses.size(); k++) {
			if (buses.get(k).busID.equals(readMeasurement.sub_rdfid)) {
				pos = k;
				break;
			}
		}

		// Add a new bus to the array
		if (pos == -1) {
			// Create a new bus within the state
			Bus tmpBus = new Bus();
			tmpBus.busID = readMeasurement.sub_rdfid;
			// Check if value is a voltage or an angle
			if (isVoltage(readMeasurement.name)) {
				tmpBus.addBusVoltage(Double.parseDouble(readMeasurement.value));
			} else {
				tmpBus.addBusAngle(Double.parseDouble(readMeasurement.value));
			}
			// Add bus to the array
			buses.add(tmpBus);
		}

		// Add the value to an existing bus
		else {
			if (isVoltage(readMeasurement.name)) {
				buses.get(pos).addBusVoltage(
						Double.parseDouble(readMeasurement.value));
			} else {
				buses.get(pos).addBusAngle(
						Double.parseDouble(readMeasurement.value));
			}
		}
	}
	
}
