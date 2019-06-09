package myPackage;

public class Main {

	public static void main(String[] args) {
		String learningFileName = "measurements.csv";
		String testFileName = "analog_values.csv";
		
		DataBase learn=new DataBase(learningFileName);
		DataBase test=new DataBase(testFileName);

		KMeans.execute(learn);
		Knn.execute(learn,test);
		
	}
}
