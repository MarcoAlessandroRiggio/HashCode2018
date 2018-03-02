package entity;

import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparingDouble;


public class TaxiSolver {

	public String theOranAlgorithm(){
		Configuration configuration = Configuration.getConfiguration();
		List<Ride> rides = new ArrayList<>(configuration.getRides());

		final ArrayList<Taxi> taxis = new ArrayList<>();
		for (int i = 0; i < configuration.getVehicles(); i++) { taxis.add(new Taxi()); }

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
			
			if(rides.isEmpty()) break;
			
			taxis.stream().filter(e -> !e.IsBusy()).forEach(t->{
				Ride chosenRide = ChooseNextRide(t, rides, currStep);
				t.setNextTarget( chosenRide );
				
			});
		}
		
		StringBuilder result = new StringBuilder();
		for(Taxi taxi : taxis) { result.append(taxi.toString()).append("\n"); }
		return result.toString();
	}
	
	private Ride ChooseNextRide(Taxi taxi, List<Ride> rides, int currStep) {
		Ride newTargetRide = rides.stream().min(comparingDouble(r -> getRideScore(taxi, r, currStep))).get();
		rides.remove(newTargetRide);
		return newTargetRide;
	}

	private double getRideScore(Taxi taxi, Ride ride, int currStep) {
		double result = 0f;
		int distanceToRideStart = taxi.getCurrentPosition().getDistance(ride.getStartPosition());
		if(currStep + distanceToRideStart < ride.getStartTime()) {
			//We can get the bonus
			result += Configuration.getConfiguration().getBonus();
		}
		result += ride.getTravelDistance();
		result -= distanceToRideStart;
		return result;
	}
	
}
