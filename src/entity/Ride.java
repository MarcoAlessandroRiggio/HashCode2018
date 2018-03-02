package entity;

import java.util.ArrayList;
import java.util.List;

public class Ride {

	private List<Point> points;
	private final int startTime;
	private final int endTime;
	private int index;

	public Point getStartPosition() {
		return points.get(0);
	}
	
	public Point getEndPosition() {
		return points.get(1);
	}

	public Ride(int index, int startTime, int endTime) {
		super();
		this.index = index;
		this.startTime = startTime;
		this.endTime = endTime;
		this.points = new ArrayList<>();
	}

	public double getTravelDistance() { return getStartPosition().getDistance(getEndPosition()); }
	
	public int getIndex() {
		return index;
	}

	public void addPoint(Point point) { this.points.add(point); }

	public List<Point> getPoints() { return points; }

	public int getStartTime() { return startTime; }
}
