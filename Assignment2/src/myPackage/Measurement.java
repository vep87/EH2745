package myPackage;

public class Measurement {
	String rdfid;
	String name;
	String time;
	String value;
	String sub_rdfid;

	Measurement(String rdfid, String name, String time, String value,
			String sub_rdfid) {
		this.rdfid = rdfid;
		this.name = name;
		this.time = time;
		this.value = value;
		this.sub_rdfid = sub_rdfid;
	}

}
