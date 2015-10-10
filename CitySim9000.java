/*************************************************************************
 * Carmen Condeluci, 10/8/15, CS1632
 *
 * This program simulates cars moving around a city as per project requirements
 *
 * Ex: java CitySim9000
 *
 *************************************************************************/

import java.util.*;

public class CitySim9000 {

	public static void main(String[] args) {

		int seed = getSeed(args);

		if(seed == -1)
			System.exit(1);

		Random randomGenerator = new Random(seed);

		LocationConnections[] locArr = setupCity();
		
		//Generate drivers and run simulation
		int firstLoc = getRandom(randomGenerator, 5) + 1;
		Driver driver0 = new Driver(0, locArr[firstLoc].root, false);
		simulateTrip(driver0, randomGenerator, locArr);

		firstLoc = getRandom(randomGenerator, 5) + 1;
		Driver driver1 = new Driver(1, locArr[firstLoc].root, false);
		simulateTrip(driver1, randomGenerator, locArr);

		firstLoc = getRandom(randomGenerator, 5) + 1;
		Driver driver2 = new Driver(2, locArr[firstLoc].root, false);
		simulateTrip(driver2, randomGenerator, locArr);

		firstLoc = getRandom(randomGenerator, 5) + 1;
		Driver driver3 = new Driver(3, locArr[firstLoc].root, false);
		simulateTrip(driver3, randomGenerator, locArr);

		firstLoc = getRandom(randomGenerator, 5) + 1;
		Driver driver4 = new Driver(4, locArr[firstLoc].root, false);
		simulateTrip(driver4, randomGenerator, locArr);

	}

	public static int simulateTrip(Driver driver, Random randomGenerator, LocationConnections[] locArr){

		int cardDir = -1; //0 for N, 1 for S, 2 for W, 3 for E

		while (driver.complete == false) {

			int curr_check = driver.curr_Loc.getID();

			cardDir = getRandom(randomGenerator, 4);

			if( curr_check != -1 ){

				for(LocationConnections i : locArr){

					if(i.root.getID() == curr_check){

						Location next = determineDirection(cardDir, i);

						if(next.getID() != -1){

							System.out.println("Driver " + driver.drv_ID + " heading from " + driver.curr_Loc.getName() + " to " + next.getName() + " via " + driver.curr_Loc.getStreetName(cardDir) + ".");
							driver.curr_Loc = next;

							if( driver.curr_Loc.getID() == 4 ){
								driver.complete = true;
								System.out.println("Driver " + driver.drv_ID + " has left the city!\n-----");
							}
						}
					}
				}		
			}
		}	
		
		return 1;
	}
	
	public static LocationConnections[] setupCity(){
		
		//Begin Specific CitySim9000 object generation

		//Generate Streets
		Street fourthAve = new Street(0, "Fourth Ave", 1, 'E', 'X');
		Street fifthAve = new Street(1, "Fifth Ave", 1, 'W', 'X');
		Street meowSt = new Street(2, "Meow St", 2, 'N', 'S');
		Street chirpSt = new Street(3, "Chirp St", 2, 'N', 'S');
		Street none = new Street(); //No street for this direction

		//Generate Locations NSWE
		Location mall = new Location(0, "Mall", none, meowSt, fourthAve, fourthAve);
		Location bookstore = new Location(1, "Bookstore", none, chirpSt, fourthAve, fourthAve);
		Location coffeeshop = new Location(2, "Coffee Shop", meowSt, none, fifthAve, fifthAve);
		Location university = new Location(3, "University", chirpSt, none, fifthAve, fifthAve);
		Location outsideCity = new Location(4, "Outside City", none, none, fifthAve, fourthAve);
		Location outsideCityE = new Location(5, "Outside City", none, none, fifthAve, fourthAve);
		Location empty = new Location(); //no location here

		//Connected landmarks organized NSWE
		LocationConnections mall_r = new LocationConnections(mall, empty, coffeeshop, empty, bookstore);
		LocationConnections bookstore_r = new LocationConnections(bookstore, empty, university, empty, outsideCity);
		LocationConnections coffeeshop_r = new LocationConnections(coffeeshop, mall, empty, outsideCity, empty);
		LocationConnections university_r = new LocationConnections(university, bookstore, empty, coffeeshop, empty);
		LocationConnections outsideCityENTER_r = new LocationConnections(outsideCityE, empty, empty, university, mall);
		LocationConnections outsideCityEXIT_r = new LocationConnections(outsideCity, empty, empty, bookstore, coffeeshop);
		
		//Place objects into array for easy access
		LocationConnections[] locArr = new LocationConnections[]{outsideCityEXIT_r, mall_r, bookstore_r, coffeeshop_r, university_r, outsideCityENTER_r};
	
		return locArr;
	}

	//function wrapper for switch statement
	public static Location determineDirection(int cardDir, LocationConnections i){

		Location next = new Location();

		switch(cardDir){
			case 0: next = i.north;
					break;
			case 1: next = i.south;
					break;
			case 2: next = i.west;
					break;
			case 3: next = i.east;
					break;
			default: next = i.root;
					 break;
		}

		return next;
	}

	//function wrapper for random int (easier to test with)
	public static int getRandom(Random randomGenerator, int range){

		int result = randomGenerator.nextInt(range);

		return result;
	} 

	//Handle command line arguments
	public static int getSeed(String[] args){

		int seed = 0;

		if( args.length == 0 ){

			System.out.println("No integer seed found!");
			seed = -1;
		}
		else if( args.length > 1 ){

			System.out.println("More than one argument detected!");
			seed = -1;
		}
		else{

			try {
				seed = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				System.out.println("Argument is not an integer!");
				seed = -1;
			}
		}

		return seed;
	}
}