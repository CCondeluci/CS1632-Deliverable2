import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.*;
import java.util.Random;

import org.*;
import java.lang.String;
import org.mockito.*;

public class CitySim9000Test {
	
	//--------------------------------------------------------------
	//                   CITYSIM9000.JAVA TESTS
	//--------------------------------------------------------------
	
	/**
	 * Ensure Random correctly generates within given range
	 */
	@Test
	public void testRandomRange() {
		Random randomGenerator = new Random();
		int high = 4;
		int low = 0;
		int random = CitySim9000.getRandom(randomGenerator,5);
		assertTrue("Error, random is too high", high >= random);
		assertTrue("Error, random is too low",  low  <= random);
	}

	/**
	 * Check that method correctly returns -1 when no
	 * argument is passed.
	 */
	@Test
	public void testNoArgs() {
		int seed = CitySim9000.getSeed(new String[]{});
		assertEquals(-1, seed);
	}
	
	/**
	 * Check that method correctly returns -1 when a 
	 * non integer value is passed
	 */
	@Test
	public void testStringArgs() {
		int seed = CitySim9000.getSeed(new String[]{"abcdef"});
		assertEquals(-1, seed);
	}
	
	/**
	 * Check that method correctly returns -1 when 
	 * more than one argument is passed.
	 */
	@Test
	public void testExtraArgs() {
		int seed = CitySim9000.getSeed(new String[]{"15", "24"});
		assertEquals(-1, seed);
	}
	
	/**
	 * Check that method correctly returns the location
	 * in the direction specified by an integer
	 * 
	 * 0,1,2,3 mapping to N,S,W,E
	 */
	@Test
	public void testDetermineDirection() {
		Location root = Mockito.mock(Location.class);
		Location north = Mockito.mock(Location.class);
		Location south = Mockito.mock(Location.class);
		Location west = Mockito.mock(Location.class);
		Location east = Mockito.mock(Location.class);
		
		Mockito.when(root.getName()).thenReturn("root");
		Mockito.when(north.getName()).thenReturn("north");
		Mockito.when(south.getName()).thenReturn("south");
		Mockito.when(west.getName()).thenReturn("west");
		Mockito.when(east.getName()).thenReturn("east");

		LocationConnections testLocCons = new LocationConnections(root, north, south, west, east);
		
		Location result0 = CitySim9000.determineDirection(0, testLocCons);
		Location result1 = CitySim9000.determineDirection(1, testLocCons);
		Location result2 = CitySim9000.determineDirection(2, testLocCons);
		Location result3 = CitySim9000.determineDirection(3, testLocCons);
		Location result4 = CitySim9000.determineDirection(4, testLocCons);
		
		assertEquals("north", result0.getName());
		assertEquals("south", result1.getName());
		assertEquals("west", result2.getName());
		assertEquals("east", result3.getName());
		assertEquals("root", result4.getName());
	}
	
	/**
	 * Check that method correctly returns an array of location 
	 * connections
	 */
	@Test
	public void testCheckSetupCity() {
		
		LocationConnections[] testLocArr = CitySim9000.setupCity();
		
		assertEquals("Outside City", testLocArr[0].root.getName());
		assertEquals("Mall", testLocArr[1].root.getName());
		assertEquals("Bookstore", testLocArr[2].root.getName());
		assertEquals("Coffee Shop", testLocArr[3].root.getName());
		assertEquals("University", testLocArr[4].root.getName());
		assertEquals("Outside City", testLocArr[5].root.getName());
	}
	
	/**
	 * Check that method correctly simulates when starting
	 * outside the city and moving to outside the city from
	 * west to east.
	 */
	@Test
	public void testSimulation() {
		
		//Make fake random number generator that always spits out 3
		Random mockGenerator = Mockito.mock(Random.class);
		Mockito.when(mockGenerator.nextInt(Mockito.anyInt())).thenReturn(3);
		
		//Make all the fake objects and fake return methods, basically fake setupCity()
		Location t_one = Mockito.mock(Location.class);
		Location t_two = Mockito.mock(Location.class);
		Location t_three = Mockito.mock(Location.class);
		Location t_four = Mockito.mock(Location.class);
		Location t_outsideEnter = Mockito.mock(Location.class);
		Location t_outsideExit = Mockito.mock(Location.class);
		Location t_empty = Mockito.mock(Location.class);
		
		Mockito.when(t_one.getName()).thenReturn("one");
		Mockito.when(t_two.getName()).thenReturn("two");
		Mockito.when(t_three.getName()).thenReturn("three");
		Mockito.when(t_four.getName()).thenReturn("four");
		Mockito.when(t_outsideEnter.getName()).thenReturn("enter");
		Mockito.when(t_outsideExit.getName()).thenReturn("exit");
		Mockito.when(t_empty.getName()).thenReturn("empty");
		
		Mockito.when(t_one.getID()).thenReturn(0);
		Mockito.when(t_two.getID()).thenReturn(1);
		Mockito.when(t_three.getID()).thenReturn(2);
		Mockito.when(t_four.getID()).thenReturn(3);
		Mockito.when(t_outsideEnter.getID()).thenReturn(5);
		Mockito.when(t_outsideExit.getID()).thenReturn(4);
		Mockito.when(t_empty.getID()).thenReturn(-1);
		
		Mockito.when(t_one.getStreetName(Mockito.anyInt())).thenReturn("street from one");
		Mockito.when(t_two.getStreetName(Mockito.anyInt())).thenReturn("street from two");
		Mockito.when(t_three.getStreetName(Mockito.anyInt())).thenReturn("street from three");
		Mockito.when(t_four.getStreetName(Mockito.anyInt())).thenReturn("street from four");
		Mockito.when(t_outsideEnter.getStreetName(Mockito.anyInt())).thenReturn("street from enter");
		Mockito.when(t_outsideExit.getStreetName(Mockito.anyInt())).thenReturn("street from exit");
		Mockito.when(t_empty.getStreetName(Mockito.anyInt())).thenReturn("street from empty");

		LocationConnections t_one_r = new LocationConnections(t_one, t_empty, t_three, t_empty, t_two);
		LocationConnections t_two_r = new LocationConnections(t_two, t_empty, t_four, t_empty, t_outsideExit);
		LocationConnections t_three_r = new LocationConnections(t_three, t_one, t_empty, t_outsideExit, t_empty);
		LocationConnections t_four_r = new LocationConnections(t_four, t_two, t_empty, t_three, t_empty);
		LocationConnections t_outsideEnter_r = new LocationConnections(t_outsideEnter, t_empty, t_empty, t_four, t_one);
		LocationConnections t_outsideExit_r = new LocationConnections(t_outsideExit, t_empty, t_empty, t_two, t_three);
		
		LocationConnections[] testLocArr = new LocationConnections[]{t_outsideExit_r, t_one_r, t_two_r, t_three_r, t_four_r, t_outsideEnter_r};
		
		//Test moving from outside the city to outside the city along one path.
		Driver testDriver = new Driver(7, t_outsideEnter, false);
		
		int testResult = CitySim9000.simulateTrip(testDriver, mockGenerator, testLocArr);
		
		assertEquals(1, testResult);
	}
	
	
	//--------------------------------------------------------------
	//                   STREET.JAVA TESTS
	//--------------------------------------------------------------
	
	/**
	 * Check that basic constructor correctly initializes
	 * a completely empty and useless street
	 */
	@Test
	public void testStreetConstructor() {
		Street testStr = new Street();
		assertEquals(-1, testStr.str_ID);
		assertEquals("", testStr.str_Name);
		assertEquals(-1, testStr.way);
		assertEquals('X', testStr.direction_One);
		assertEquals('X', testStr.direction_Two);
	}
	
	/**
	 * Check that filled constructor correctly initializes
	 * a correctly made street
	 */
	@Test
	public void testStreetConstructorFilled() {
		Street testStr = new Street(12, "test street", 1, 'N', 'S');
		assertEquals(12, testStr.str_ID);
		assertEquals("test street", testStr.str_Name);
		assertEquals(1, testStr.way);
		assertEquals('N', testStr.direction_One);
		assertEquals('S', testStr.direction_Two);
	}
	
	/**
	 * Check that street name is correctly returned
	 */
	@Test
	public void testGetStreetName() {
		Street testStr = new Street(12, "test street", 1, 'N', 'S');
		String gotName = testStr.getName();
		assertEquals("test street", gotName);
	}
	
	//--------------------------------------------------------------
	//                   LOCATION.JAVA TESTS
	//--------------------------------------------------------------
	
	/**
	 * Check that basic constructor correctly initializes
	 * a completely empty and useless location
	 */
	@Test
	public void testLocationConstructor() {
		
		Location testLoc = new Location();
		
		assertEquals(-1, testLoc.loc_ID);
		assertEquals("", testLoc.loc_Name);
		assertEquals(-1, testLoc.str_North.str_ID);
		assertEquals(-1, testLoc.str_South.str_ID);
		assertEquals(-1, testLoc.str_West.str_ID);
		assertEquals(-1, testLoc.str_East.str_ID);
		assertArrayEquals(new Street[]{}, testLoc.strArr);
	}
	
	
	/**
	 * Check that filled constructor correctly initializes
	 * a correctly made location with mock streets.
	 */
	@Test
	public void testLocationConstructorFilled() {
		Street mockNorth = Mockito.mock(Street.class);
		Street mockSouth = Mockito.mock(Street.class);
		Street mockWest = Mockito.mock(Street.class);
		Street mockEast = Mockito.mock(Street.class);
		
		Mockito.when(mockNorth.getName()).thenReturn("north");
		Mockito.when(mockSouth.getName()).thenReturn("south");
		Mockito.when(mockWest.getName()).thenReturn("west");
		Mockito.when(mockEast.getName()).thenReturn("east");
		
		Location testLoc = new Location(15, "test location", mockNorth, mockSouth, mockWest, mockEast);
		
		assertEquals(15, testLoc.loc_ID);
		assertEquals("test location", testLoc.loc_Name);
		assertEquals("north", testLoc.str_North.getName());
		assertEquals("south", testLoc.str_South.getName());
		assertEquals("west", testLoc.str_West.getName());
		assertEquals("east", testLoc.str_East.getName());
		assertArrayEquals(new Street[]{mockNorth, mockSouth, mockWest, mockEast}, testLoc.strArr);
	}
	
	/**
	 * Check that the getStreetName(index) method
	 * correctly returns the name of a street with mock
	 * streets. This also checks the array ordering of
	 * N, S, W, E.
	 */
	@Test
	public void testLocationGetStreetArrName() {
		Street mockNorth = Mockito.mock(Street.class);
		Street mockSouth = Mockito.mock(Street.class);
		Street mockWest = Mockito.mock(Street.class);
		Street mockEast = Mockito.mock(Street.class);
		
		Mockito.when(mockNorth.getName()).thenReturn("north");
		Mockito.when(mockSouth.getName()).thenReturn("south");
		Mockito.when(mockWest.getName()).thenReturn("west");
		Mockito.when(mockEast.getName()).thenReturn("east");
		
		Location testLoc = new Location(15, "test location", mockNorth, mockSouth, mockWest, mockEast);
		
		assertEquals("north", testLoc.getStreetName(0));
		assertEquals("south", testLoc.getStreetName(1));
		assertEquals("west", testLoc.getStreetName(2));
		assertEquals("east", testLoc.getStreetName(3));
	}
	
	/**
	 * Check that location name is correctly returned
	 */
	@Test
	public void testGetLocationName() {
		Street mockNorth = Mockito.mock(Street.class);
		Street mockSouth = Mockito.mock(Street.class);
		Street mockWest = Mockito.mock(Street.class);
		Street mockEast = Mockito.mock(Street.class);
		
		Mockito.when(mockNorth.getName()).thenReturn("north");
		Mockito.when(mockSouth.getName()).thenReturn("south");
		Mockito.when(mockWest.getName()).thenReturn("west");
		Mockito.when(mockEast.getName()).thenReturn("east");
		
		Location testLoc = new Location(15, "test location", mockNorth, mockSouth, mockWest, mockEast);
		String gotName = testLoc.getName();
		assertEquals("test location", gotName);
	}
	
	/**
	 * Check that street name is correctly returned
	 */
	@Test
	public void testGetLocID() {
		Street mockNorth = Mockito.mock(Street.class);
		Street mockSouth = Mockito.mock(Street.class);
		Street mockWest = Mockito.mock(Street.class);
		Street mockEast = Mockito.mock(Street.class);
		
		Mockito.when(mockNorth.getName()).thenReturn("north");
		Mockito.when(mockSouth.getName()).thenReturn("south");
		Mockito.when(mockWest.getName()).thenReturn("west");
		Mockito.when(mockEast.getName()).thenReturn("east");
		
		Location testLoc = new Location(15, "test location", mockNorth, mockSouth, mockWest, mockEast);
		int gotID = testLoc.getID();
		assertEquals(15, gotID);
	}
	
	//--------------------------------------------------------------
	//               LOCATIONCONNECTIONS.JAVA TESTS
	//--------------------------------------------------------------
	
	/**
	 * Check that basic constructor correctly initializes
	 * a useless and empty location connection (hardcode)
	 */
	@Test
	public void testLocationConnectionBasicConstructor() {

		LocationConnections testLocCons = new LocationConnections();
		
		assertEquals(-1, testLocCons.root.loc_ID);
		assertEquals(-1, testLocCons.north.loc_ID);
		assertEquals(-1, testLocCons.south.loc_ID);
		assertEquals(-1, testLocCons.west.loc_ID);
		assertEquals(-1, testLocCons.east.loc_ID);
	}

	/**
	 * Check that constructor correctly initializes
	 * a location connection object with mock locations
	 */
	@Test
	public void testLocationConnectionConstructor() {
		
		Location root = Mockito.mock(Location.class);
		Location north = Mockito.mock(Location.class);
		Location south = Mockito.mock(Location.class);
		Location west = Mockito.mock(Location.class);
		Location east = Mockito.mock(Location.class);
		
		Mockito.when(root.getName()).thenReturn("root");
		Mockito.when(north.getName()).thenReturn("north");
		Mockito.when(south.getName()).thenReturn("south");
		Mockito.when(west.getName()).thenReturn("west");
		Mockito.when(east.getName()).thenReturn("east");

		LocationConnections testLocCons = new LocationConnections(root, north, south, west, east);
		
		assertEquals("root", testLocCons.root.getName());
		assertEquals("north", testLocCons.north.getName());
		assertEquals("south", testLocCons.south.getName());
		assertEquals("west", testLocCons.west.getName());
		assertEquals("east", testLocCons.east.getName());
	}
	
	//--------------------------------------------------------------
	//              		 DRIVER.JAVA TESTS
	//--------------------------------------------------------------
	
	/**
	 * Check that basic constructor correctly initializes
	 * a useless and empty driver (hardcode)
	 */
	@Test
	public void testDriverBasicConstructor() {

		Driver testDriver = new Driver();
		
		assertEquals(-1, testDriver.drv_ID);
		assertEquals(-1, testDriver.curr_Loc.loc_ID);
		assertEquals(true, testDriver.complete);
	}
	
	/**
	 * Check that constructor correctly initializes
	 * a driver object with mock locations
	 */
	@Test
	public void testDriverConstructor() {
		
		Location root = Mockito.mock(Location.class);
		Mockito.when(root.getName()).thenReturn("root");

		Driver testDriver = new Driver(15, root, false);
		
		assertEquals(15, testDriver.drv_ID);
		assertEquals("root", testDriver.curr_Loc.getName());
		assertEquals(false, testDriver.complete);
	}
}
