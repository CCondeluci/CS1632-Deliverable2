import java.util.*;

public class Location {

		public int loc_ID; //Location ID
		public String loc_Name; //Location name
		public Street str_North; //Street to the north
		public Street str_South; //Street to the south
		public Street str_West; //Street to the west
		public Street str_East; //Street to the east
		public Street[] strArr; //Array ordered NSWE

		//empty constructor
		public Location() {
			this.loc_ID = -1;
			this.loc_Name = "";
			this.str_North = new Street();
			this.str_South = new Street();
			this.str_West = new Street();
			this.str_East = new Street();
			this.strArr = new Street[]{};
		}

		//actual constructor
		public Location(int loc_ID, String loc_Name, Street str_North, Street str_South, Street str_West, Street str_East) {
			this.loc_ID = loc_ID;
			this.loc_Name = loc_Name;
			this.str_North = str_North;
			this.str_South = str_South;
			this.str_West = str_West;
			this.str_East = str_East;
			this.strArr = new Street[]{str_North, str_South, str_West, str_East};
		}

		public String getStreetName(int index){
			return this.strArr[index].getName();
		}
		
		public String getName(){
			return this.loc_Name;
		}
		
		public int getID(){
			return this.loc_ID;
		}
}