package myPackage;

public class Main {

	public static void main(String[] args) {
		String fileName = "measurements.csv";
		double[][] values = DataBase.read_data(fileName);
		KMeans.execute(values);
	}

}
