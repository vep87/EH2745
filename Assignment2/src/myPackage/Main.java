package myPackage;


public class Main {

	public static void main(String outputDirectory, String learningFileName,String testFileName ) {
		//learningFileName = "C:/Users/asus/Documents/SENSE/EH2745 Computer Applications in Power Systems/Workspace/EH2745/Assignment2/measurements.csv";
		//testFileName = "C:/Users/asus/Documents/SENSE/EH2745 Computer Applications in Power Systems/Workspace/EH2745/Assignment2/analog_values.csv";
		try{
			DataBase learn=new DataBase(learningFileName);
			DataBase test=new DataBase(testFileName);
			KMeans.execute(learn);
			
			Knn.execute(learn,test);
			KMeans.print_CSV(outputDirectory);
			Knn.print_CSV_test(test, outputDirectory);
		}
		catch (Exception e) {
			System.out.println("Error performing the algorithm");
		}
		
		
		
	}
}
