package entity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


public class TaxiSolver {

	public String theOranAlgorithm(){
		Configuration configuration = Configuration.get();
		List<Ride> rides = new ArrayList<>(configuration.getRides());

		final ArrayList<Taxi> taxis = new ArrayList<>();
		for (int i = 0; i < configuration.getVehicles(); i++) { taxis.add(new Taxi()); }

		//Start of simulation
		for(int i = 0; i < configuration.getSteps(); i++){
			int currStep = i;
			for(Taxi t : taxis) {
				t.update();
			}
			
			if(rides.isEmpty()) { break; } //Let's compromise

			//Eliminate rides that have expired!
			List<Ride> availableRides = rides.stream().filter(r -> r.getEndTime() > currStep).collect(Collectors.toList());
			
			taxis.stream().filter(e -> e.IsBusy()==false).forEach(t->{
				Ride chosenRide = ChooseNextRide(t, availableRides, currStep);
				if(chosenRide != null) {
					t.setNextTarget( chosenRide, currStep );
					rides.remove(chosenRide);
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
					.filter(r -> canRideBeMadeInTime(taxi, r, currStep))
					.sorted(Comparator.comparingDouble(r -> getRideScore(taxi, r, currStep)))
//					.sorted((r1, r2) -> Double.compare(getRideScore(taxi, r2, currStep), getRideScore(taxi, r1, currStep)) )
					//Weed out the ride that can't be made to the destination in time.
					.findFirst().get();
		}catch(NoSuchElementException exception) {
			return null;
		}

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
		DebugEx.increaseCallsToCanRideBeMadeInTime();
		int distanceToDestination = taxi.getCurrentPosition().getDistance(r.getStartPosition());
		int etaDestination = distanceToDestination + currStep;
		int rideBeginTime = (etaDestination < r.getStartTime()) ? r.getStartTime() : etaDestination;
		int rideDuration = r.getTravelDistance();

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
