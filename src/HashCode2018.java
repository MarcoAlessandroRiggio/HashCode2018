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

	private final static String inputFile = "C:\\Users\\Oran\\Downloads\\e_high_bonus.in";
	private final static String outputFile = "C:\\Users\\Oran\\Documents\\Hashcode\\e_high_bonus.out";

	public static void main(String[] args) {
		Configuration.readConfiguration(inputFile);
		TaxiSolver solver = new TaxiSolver();
		String result = solver.IterativeSolving();
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFile ))) {
			writer.write(result);
		} catch (IOException e) { e.printStackTrace(); }
	}

}

