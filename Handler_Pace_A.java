import javax.realtime.PriorityScheduler;
import javax.realtime.*;
import javax.safetycritical.*;


// Implementaion of a periodic event handler

public class Handler_Pace_A extends AperiodicEventHandler {
	Actuator_A pm_A = new Actuator_A();
	long interval ;

	public Handler_Pace_A() {
		super(new PriorityParameters(PriorityScheduler.instance().getMaxPriority()),
		null,
		new StorageConfigurationParameters(32768, 4096, 4096),
		32768,
		"Handler_Pace_A"); //when sensor event is fire
	
	}

	public void handleEvent() {
		AbsoluteTime now =Clock.getRealtimeClock().getTime();
		interval = now.subtract(MainPMMissionSequence.lastVentricleActivityTime).getMilliseconds();      
		if(interval > (MainPMMissionSequence.PaceInterval - MainPMMissionSequence.AVI) && MainPMMissionSequence.Activity_A_Occured == false){     

		pm_A.Pace_ON_A(); //Pace ON
		try{
		RealtimeThread.sleep(new RelativeTime(MainPMMissionSequence.PacingLength,0));  //sleep
		}catch (InterruptedException ie){
		    System.out.println(ie.getMessage());
		}
		pm_A.Pace_OFF_A(); //Pace OFF

		//Set Atrium activity flag
		MainPMMissionSequence.Activity_A_Occured = true;
		
		//Reset Ventricle flag
		MainPMMissionSequence.Activity_V_Occured =false;
		
		//Save pacing Time of Atrium
		MainPMMissionSequence.lastAtriumActivityTime.set(now.getMilliseconds(),now.getNanoseconds());
		
		}
	}
}//end class
