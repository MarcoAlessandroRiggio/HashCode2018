import entity.Configuration;
import entity.TaxiSolver;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HashCode2018 {

	private final static String[] fileNames = new String[]{
			"\\a_example",
			"\\b_should_be_easy",
			"\\c_no_hurry",
			"\\d_metropolis",
			"\\e_high_bonus"
	};

	public static void main(String[] args) {
		System.out.println("Start");
		String rootPath = System.getProperty("user.dir");
		for (String inputFileName : fileNames) {
			String inputFile = rootPath + "\\input\\" + inputFileName + ".in";
			String outputFile = rootPath + "\\output\\" + inputFileName + ".out";

			System.out.println("Doing file " + inputFile);

			Configuration.readConfiguration(inputFile);
			TaxiSolver solver = new TaxiSolver();
			String result = solver.theOranAlgorithm();
			try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFile))) {
				writer.write(result);
			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println("Done with File " + inputFile);
		}
		System.out.println("Finish");
	}
}
