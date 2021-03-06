import javax.realtime.PriorityScheduler;
import javax.realtime.*;
import javax.safetycritical.*;


// Implementaion of a periodic event handler

public class DDDR_Handler_Pace_A extends AperiodicEventHandler {
	Actuator_A pm_A = new Actuator_A();
	long interval ;

	public DDDR_Handler_Pace_A() {
		super(new PriorityParameters(PriorityScheduler.instance().getMaxPriority()),
		null,
		new StorageConfigurationParameters(32768, 4096, 4096),
		32768,
		"DDDR_Handler_Pace_A"); //when sensor event is fire
	
	}

	public void handleEvent() {
		AbsoluteTime now =Clock.getRealtimeClock().getTime();
		//interval = now.subtract(DDDR_PMMission.lastVentricleActivityTime).getMilliseconds();      
		//if(interval > (DDDR_PMMission.PaceInterval - DDDR_PMMission.AVI)){     		
		//interval = now.subtract(DDDR_PMMission.lastAtriumActivityTime).getMilliseconds();      
		//if(interval > (DDDR_PMMission.PVARP + DDDR_PMMission.AVI)){     		
		
		interval = now.subtract(DDDR_PMMission.lastAtriumActivityTime).getMilliseconds();      
		if(interval > (DDDR_PMMission.PaceInterval - DDDR_PMMission.AVI)){     		
		
		pm_A.Pace_ON_A(); //Pace ON
		try{
		RealtimeThread.sleep(new RelativeTime(DDDR_PMMission.PacingLength,0));  //sleep
		}catch (InterruptedException ie){
		    System.out.println(ie.getMessage());
		}
		pm_A.Pace_OFF_A(); //Pace OFF

		//Reset Ventricle flag
		DDDR_PMMission.Activity_V_Occured =false;
		
		//Save pacing Time of Atrium
		DDDR_PMMission.lastAtriumActivityTime.set(now.getMilliseconds(),now.getNanoseconds());
		
		}
	}
}//end class
