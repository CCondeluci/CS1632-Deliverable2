import java.util.*;

public class Street {

		public int str_ID; //Street ID
		public String str_Name; //Street Name
		public int way; //1 or 2 way street
		public char direction_One; //Cardinal direction: N, S, W, E, or X for undefined
		public char direction_Two; //Cardinal direction: N, S, W, E, or X for undefined

		//empty constructor
		public Street() {
			this.str_ID = -1;
			this.str_Name = "";
			this.way = -1;
			this.direction_One = 'X';
			this.direction_Two = 'X';
		}

		//actual constructor
		public Street(int str_ID, String str_Name, int way, char direction_One, char direction_Two) {
			this.str_ID = str_ID;
			this.str_Name = str_Name;
			this.way = way;
			this.direction_One = direction_One;
			this.direction_Two = direction_Two;
		}
		
		public String getName(){
			return this.str_Name;
		}
}