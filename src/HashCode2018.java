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

	private final static String[] fileNames = new String[] { "\\output\\e_high_bonus" }

	public static void main(String[] args) {

		for(String inputFile in fileNames){
			String inputFile = System.getProperty("user.dir") + inputFile + ".in";
			String outputFile = System.getProperty("user.dir") + inputFile + ".out";

			Configuration.readConfiguration(System.getProperty("user.dir") + inputFile + ".in");
			TaxiSolver solver = new TaxiSolver();
			String result = solver.IterativeSolving();
			try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(System.getProperty("user.dir") + inputFile + ".out" ))) {
				writer.write(result);
			} catch (IOException e) { e.printStackTrace(); }
		}
	}
}
