import java.util.*;

public class LocationConnections {

		public Location root; //root location
		public Location north; //location to the north
		public Location south; //location to the south
		public Location west; //location to the west
		public Location east; //location to the east

		public LocationConnections(){
			this.root = new Location();
			this.north = new Location();
			this.south = new Location();
			this.west = new Location();
			this.east = new Location();
		}

		public LocationConnections(Location root, Location north, Location south, Location west, Location east){
			this.root = root;
			this.north = north;
			this.south = south;
			this.west = west;
			this.east = east;
		}
}