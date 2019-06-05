package myPackage;

public class Bus {
	public String busID;
	public double voltage;
	public double angle;

	Bus() {
		busID 	= "";
		voltage = 0;
		angle 	= 0;
	}
	
	public void addBusID(String busID){
		this.busID = busID;
	}

	public void addBusVoltage(double voltage){
		this.voltage = voltage;
	}
	
	public void addBusAngle(double angle){
		this.angle = angle;
	}
}
