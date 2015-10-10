import java.util.*;

public class Driver {

	public int drv_ID; //Driver ID
	public Location curr_Loc; //Current location
	public boolean complete; //Flag for if driver has exited city or not

	//empty constructor
	public Driver() {
		this.drv_ID = -1;
		this.curr_Loc = new Location();
		this.complete = true;
	}

	//actual constructor
	public Driver(int drv_ID, Location curr_Loc, boolean complete) {
		this.drv_ID = drv_ID;
		this.curr_Loc = curr_Loc;
		this.complete = complete;
	}
}