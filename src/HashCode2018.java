import entity.Configuration;
import entity.TaxiSolver;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.*;
public class HashCode2018 {

	private final static String[] fileNames = new String[] {
	//		"\\a_example",
			"\\b_should_be_easy",
			"\\c_no_hurry",
	//		"\\d_metropolis",
	//		"\\e_high_bonus"
		};

	public static final String Version = "_RevList";

	public static void main(String[] args) {
		System.out.println("Start");
		for(String inputFileName : fileNames){
			String inputFile = System.getProperty("user.dir") +  "\\input\\" + inputFileName + ".in";
			String outputFile = System.getProperty("user.dir") + "\\output" + inputFileName + Version + ".out";

			System.out.println("Doing file "+inputFile);

			Configuration.readConfiguration(inputFile);
			TaxiSolver solver = new TaxiSolver();
			String result = solver.theOranAlgorithm();
			try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFile))) {
				writer.write(result);
			} catch (IOException e) { e.printStackTrace(); }

			System.out.println("Done with File "+inputFile);
		}
		System.out.println("Finish");
	}
}
