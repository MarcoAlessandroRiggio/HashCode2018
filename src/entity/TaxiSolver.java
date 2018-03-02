package entity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;


public class TaxiSolver {

	public String theOranAlgorithm(){
		Configuration configuration = Configuration.getConfiguration();
		List<Ride> rides = new ArrayList<>(configuration.getRides());

		final ArrayList<Taxi> taxis = new ArrayList<>();
		for (int i = 0; i < configuration.getVehicles(); i++) { taxis.add(new Taxi()); }

		//Start of simulation
		for(int i = 0; i < configuration.getSteps(); i++){
			int currStep = i;
			for(Taxi t : taxis) {
				if(t.getStepsUntilRideDone() == 0) {
					t.setCurrentPosition(t.getTarget());
				}
				if(t.IsBusy()) {
					t.decreaseStepsUntilRideDone();
				}
			}
			
			if(rides.isEmpty()) { break; } //Let's compromise

			
			taxis.stream().filter(e -> !e.IsBusy()).forEach(t->{
				Ride chosenRide = ChooseNextRide(t, rides, currStep);
				if(chosenRide != null) {
					t.setNextTarget( chosenRide );
				}
			});
		}
		//End of simulation

		return generateFormattedResult(taxis);
	}

	private Ride ChooseNextRide(Taxi taxi, List<Ride> rides, int currStep) {
		Ride newTargetRide = null;
		try {
			newTargetRide = rides
					.stream()
					//Sort by score
					.sorted(Comparator.comparingDouble(r -> getRideScore(taxi, r, currStep)))
					//Weed out the ride that can't be made to the destination in time.
					.filter(r -> canRideBeMadeInTime(taxi, r, currStep))
					.findFirst().get();
		}catch(NoSuchElementException exception) {
			return null;
		}

		rides.remove(newTargetRide);
		return newTargetRide;
	}

	private double getRideScore(Taxi taxi, Ride ride, int currStep) {
		double result = 0;
		int distanceToRideStart = taxi.getCurrentPosition().getDistance(ride.getStartPosition());
		if(currStep + distanceToRideStart < ride.getStartTime()) {
			//We can get the bonus
			result += Configuration.get().getBonus();
		}
		result += ride.getTravelDistance();
		result -= distanceToRideStart;
		return result;
	}

	private boolean canRideBeMadeInTime(Taxi taxi, Ride r, int currStep) {
		int distanceToDestination = taxi.getCurrentPosition().getDistance(r.getStartPosition());
		int etaDestination = distanceToDestination + currStep;
		int rideBeginTime = (etaDestination < r.getStartTime()) ? r.getStartTime() : etaDestination;
		int rideDuration = r.getStartPosition().getDistance(r.getEndPosition());

		boolean result = rideBeginTime + rideDuration < Configuration.get().getSteps()
				&&
				rideBeginTime + rideDuration <= r.getEndTime();

		return result;
	}

	private String generateFormattedResult(List<Taxi> taxis){
		StringBuilder result = new StringBuilder();
		for(Taxi taxi : taxis) {
			result.append(taxi.toString()).append("\n");
		}
		return result.toString();
	}


}
