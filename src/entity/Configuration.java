package entity;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class Configuration {

	private static Configuration configuration;

	private Integer row;
	private Integer column;
	private Integer vehicles;
	private Integer numberOfRides;
	private Integer bonus;
	private Integer steps;
	private List<Ride> rides;

	public static Configuration get() {
		if(configuration == null)
			configuration = new Configuration();
		return configuration;
	}

	private Configuration() {
		super();
		this.rides = new ArrayList<>();
	}

	public void addRide(int index, List<Integer> line) {
		Ride ride = new Ride(index, line.get(4), line.get(5));
		ride.addPoint(new Point(line.get(0), line.get(1)));
		ride.addPoint(new Point(line.get(2), line.get(3)));
		rides.add(ride);
	}

	public void addGlobalVaribles(List<Integer> convertedLine) {
		this.row = convertedLine.get(0);
		this.column = convertedLine.get(1);
		this.vehicles = convertedLine.get(2);
		this.numberOfRides = convertedLine.get(3);
		this.bonus = convertedLine.get(4);
		this.steps = convertedLine.get(5);
	}

	public Integer getRow() { return row; }

	public Integer getColumn() { return column; }

	public Integer getVehicles() { return vehicles; }

	public Integer getNumberOfRides() { return numberOfRides; }

	public Integer getBonus() { return bonus; }

	public Integer getSteps() { return steps; }

	public List<Ride> getRides() { return rides; }

	public static void readConfiguration(String inputFile) {
		configuration = new Configuration();
		boolean readingFirstLine = true;
		try {
			int i = 0;
			for(String line : Files.lines(Paths.get(inputFile)).collect(Collectors.toList())) {
				List<Integer> convertedLine = getPastedLine(line);
				if (readingFirstLine) {
					readingFirstLine = false;
					configuration.addGlobalVaribles(convertedLine);
				} else
					configuration.addRide(i++, convertedLine);
			}
		} catch (Throwable e) { e.printStackTrace(); }
	}

	private static List<Integer> getPastedLine(String line) {
		return stream(line.split("\\s+"))
				.map(Integer::parseInt)
				.collect(Collectors.toList());
	}

}
