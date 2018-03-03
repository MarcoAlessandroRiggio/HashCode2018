package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingDouble;


public class TaxiSolver {

	public String theOranAlgorithm() {
		final Configuration configuration = Configuration.get();
		List<Ride> rides = new ArrayList<>(configuration.getRides());

		final ArrayList<Taxi> taxis = new ArrayList<>();
		for (int i = 0; i < configuration.getVehicles(); i++) taxis.add(new Taxi());

		//Start of simulation
		for (int i = 0; i < configuration.getSteps(); i++) {
			int currStep = i;


			if (rides.isEmpty()) { break; }//Let's compromise
			if (taxis.stream().allMatch(Taxi::isBusy)) { continue; } //If all taxies are busy, skip

			//Eliminate rides that have expired!
			rides = rides.stream().filter(r -> r.getEndTime() > currStep).collect(Collectors.toList());
			List<Ride> availableRides = rides;	//Can't use rides inside the stream.

			taxis.stream()
				.filter(e -> e.isBusy()==false)	//Only taxis that are not busy need new instructions
				.forEach(t->{
					Ride chosenRide = ChooseNextRide(t, availableRides, currStep);
					if(chosenRide != null) {
						t.setNextTarget( chosenRide, currStep );
						availableRides.remove(chosenRide);
					}
				});

			rides = availableRides; //availableRides doesn't have the rides we have just assigned

			System.out.println("Simulation at "+currStep+"/"+Configuration.get().getSteps());
			for(Taxi t : taxis) {
				t.updateStep();
			}
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
					.sorted(comparingDouble(r -> getRideScore(taxi, r, currStep)))
			//Weed out the ride that can't be made to the destination in time.
					.filter(r -> canRideBeMadeInTime(taxi, r, currStep))
					.findFirst().get();
		} catch (NoSuchElementException exception) { return null; }

		return newTargetRide;
	}

	private double getRideScore(Taxi taxi, Ride ride, int currStep) {
		double result = 0;
		int distanceToRideStart = taxi.getCurrentPosition().getDistance(ride.getStartPosition());
		if (currStep + distanceToRideStart < ride.getStartTime()) {
			//We can get the bonus
			result += Configuration.get().getBonus();
		}
		result += ride.getTravelDistance();
		result += distanceToRideStart;

		int timeNeededToCompleteRide = ride.getTravelDistance() + distanceToRideStart;
		result = result / timeNeededToCompleteRide;
		return result;
	}

	private boolean canRideBeMadeInTime(Taxi taxi, Ride r, int currStep) {
		//DebugEx.increaseCallsToCanRideBeMadeInTime();
		int distanceToRideStart = taxi.getCurrentPosition().getDistance(r.getStartPosition());
		int etaToRideStart = distanceToRideStart + currStep;
		int rideStartTime = (etaToRideStart < r.getStartTime()) ? r.getStartTime() : etaToRideStart;
		int rideDuration = r.getTravelDistance();

		int etaToRideEnd = rideStartTime + rideDuration;

		return etaToRideEnd <= Configuration.get().getSteps()
				&& etaToRideEnd <= r.getEndTime();
		}

	private String generateFormattedResult(List<Taxi> taxis) {
		StringBuilder result = new StringBuilder();
		for (Taxi taxi : taxis) {
			result.append(taxi.toString()).append("\n");
		}
		return result.toString();
	}


}
