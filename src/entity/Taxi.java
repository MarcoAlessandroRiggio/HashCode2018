package entity;

public class Taxi {
	
	private static int staticId = 1;

	private Point target;
	private Point currentPosition;
	private int stepsUntilRideDone;
	private int id;
	private String myRides;
	private int noOfRides;

	public Taxi() {
		this.id = staticId++;
		this.currentPosition = new Point(0,0);
		this.target = currentPosition;
		this.myRides = "";
		this.noOfRides = 0;
	}

	public boolean IsBusy(){
		return target != currentPosition;
	}

	public void setNextTarget(Ride ride) {
		this.target = ride.getStartPosition();
		this.myRides += " " + ride.getIndex();
		this.stepsUntilRideDone = currentPosition.getDistance(ride.getStartPosition());
		this.noOfRides++;
	}
	
	@Override
	public String toString() { return noOfRides + myRides; }

	public Point getCurrentPosition() { return currentPosition; }

	public int getStepsUntilRideDone() { return stepsUntilRideDone; }

	public Point getTarget() { return target; }

	public void decreaseStepsUntilRideDone() { this.stepsUntilRideDone--; }

	public void setCurrentPosition(Point currentPosition) {
		this.currentPosition = currentPosition;
	}
}
