import javax.safetycritical.PriorityScheduler;
import javax.realtime.*;
import javax.safetycritical.*;

// Implementaion of a periodic event handler

public class Handler_Pace_V extends AperiodicEventHandler {
	Actuator_V pm_V= new Actuator_V();
	long interval ;

	public Handler_Pace_V() {
		super(new PriorityParameters(PriorityScheduler.instance().getMaxPriority()),
		null,
		new StorageConfigurationParameters(32768, 4096, 4096),
		32768,
		"Handler_Pace_V"); //when sensor event is fire
	}

	public void handleEvent() {
		AbsoluteTime now = Clock.getRealtimeClock().getTime();
		interval = now.subtract(MainPMMissionSequence.lastAtriumActivityTime).getMilliseconds();      

		if(interval > MainPMMissionSequence.AVI && MainPMMissionSequence.Activity_V_Occured == false){
		pm_V.Pace_ON_V();
		try{
		RealtimeThread.sleep(new RelativeTime(MainPMMissionSequence.PacingLength,0));  //sleep
		}catch (InterruptedException ie){
		    System.out.println(ie.getMessage());
		}
		pm_V.Pace_OFF_V();
		
	        //Set Ventricle activity flag
		MainPMMissionSequence.Activity_V_Occured = true;
		
		//Reset Atrium activity flag
		MainPMMissionSequence.Activity_A_Occured = false;
	      
		//Save pacing Time of Ventricle
		MainPMMissionSequence.lastVentricleActivityTime.set(now.getMilliseconds(),now.getNanoseconds());
		}
		
	}
}//end class
