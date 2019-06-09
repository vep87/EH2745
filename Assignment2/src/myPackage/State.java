package myPackage;

import java.util.ArrayList;

public class State {

	public double time;
	public ArrayList<Bus> buses = new ArrayList<Bus>();
	public double meanAngle, meanVoltage;
	public String label;

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

	public void calculations() {
		int nBuses = buses.size();
		double sumAngles = 0;
		double sumVolts = 0;
		for (int i = 0; i < nBuses; i++) {
			sumAngles += buses.get(i).angle;
			sumVolts += buses.get(i).voltage;
		}
		meanAngle = sumAngles/buses.size();
		meanVoltage = sumVolts/buses.size();
	}
	
	public String stringValues(){
		String s = "";
		for(int k=0; k<buses.size(); k++){
			s += Double.toString(buses.get(k).angle)+",";
			s += Double.toString(buses.get(k).voltage);
			if(k!=(buses.size()-1)){
				System.out.print("\t");
				s += ",";
			}
		}
		s += "\n";
		return s;
	}
}
