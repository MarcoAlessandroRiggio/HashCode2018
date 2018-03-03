package entity;

public class Taxi {
	
	private static int staticId = 1;

	private Point target;
	private Point currentPosition;
	private int distanceToTarget;
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
		return distanceToTarget > 0 || waitTimeLeft > 0;
	}

	public void updateStep(){
        if(distanceToTarget > 0) {
            distanceToTarget--;
            if(distanceToTarget == 0) {
                currentPosition = target;
            }
        } else if(waitTimeLeft > 0 ){
            waitTimeLeft--;
        }
	}

	public void setNextTarget(Ride ride, int currTime) {
		assert currentPosition == target;
		assert distanceToTarget == 0;

		this.target = ride.getStartPosition();
		this.myRides += " " + ride.getIndex();
		this.distanceToTarget = currentPosition.getDistance(target) + 1;
		this.noOfRides++;
		this.waitTimeLeft = ride.getStartTime() - currTime - distanceToTarget;
	}
	
	@Override
	public String toString() { return noOfRides + myRides; }

	public Point getCurrentPosition() { return currentPosition; }

	public int getEtaToTarget() { return distanceToTarget; }

	public Point getTarget() { return target; }

	public void setCurrentPosition(Point currentPosition) {
		this.currentPosition = currentPosition;
	}
}
