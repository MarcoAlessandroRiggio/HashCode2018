package entity;

public class Taxi {
	
	private static int staticId = 1;

	private Point target;
	private Point currentPosition;
	private int etaToTarget;
	private int id;
	private String myRides;
	private int noOfRides;
	private int waitTimeLeft = 0;

	public Taxi() {
		this.id = staticId++;
		this.currentPosition = new Point(0,0);
		this.target = currentPosition;
		this.myRides = "";
		this.noOfRides = 0;
	}

	public boolean isBusy(){
		return waitTimeLeft > 0;
	}

	public void update(){
		if(target != currentPosition) {
			etaToTarget--;
			if(etaToTarget == 0) {
				currentPosition = target;
			}
		} else {
			waitTimeLeft--;
		}
	}

	public void setNextTarget(Ride ride, int currTime) {
		this.target = ride.getStartPosition();
		this.myRides += " " + ride.getIndex();
		this.etaToTarget = currentPosition.getDistance(target);
		this.noOfRides++;
		this.waitTimeLeft = ride.getStartTime() - currTime - etaToTarget;
	}
	
	@Override
	public String toString() { return noOfRides + myRides; }

	public Point getCurrentPosition() { return currentPosition; }

	public int getEtaToTarget() { return etaToTarget; }

	public Point getTarget() { return target; }

	public void setCurrentPosition(Point currentPosition) {
		this.currentPosition = currentPosition;
	}
}
