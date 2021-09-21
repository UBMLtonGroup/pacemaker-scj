import javax.realtime.PeriodicParameters;
import javax.realtime.PeriodicTimer;
import javax.realtime.PriorityParameters;
import javax.realtime.PriorityScheduler;
import javax.realtime.Timer;
import javax.realtime.RelativeTime;
import javax.safetycritical.StorageConfigurationParameters;
import javax.realtime.Clock;
import javax.realtime.*;
import javax.safetycritical.*;


// Implementaion of a periodic event handler

public class DDIR_Handler_Pace_A_Per extends PeriodicEventHandler {

	DDIR_Actuator_A pm_A;
	DDIR_Sensor_V pms_V; 
	DDIR_Sensor_A pms_A; 
	DDIR_ModeChange fmc = new DDIR_ModeChange();

	int Pacing_Length_A;

 
	public DDIR_Handler_Pace_A_Per(int Pace_Interval,int Pacing_Length,DDIR_Actuator_A pm_A_temp,DDIR_Sensor_V pms_V_temp,DDIR_Sensor_A pms_A_temp, AsyncEventHandler handler) {
		super(new PriorityParameters(PriorityScheduler.instance()
				.getNormPriority()), new PeriodicParameters(null,
				new RelativeTime(Pace_Interval, 0),null, new RelativeTime(8,0),null,handler), new StorageConfigurationParameters(
				32768, 4096, 4096), 65536, "Handler_A");
	Pacing_Length_A = Pacing_Length;
	
	pm_A = pm_A_temp;
	pms_V = pms_V_temp;
	pms_A = pms_A_temp;

	}

	public void handleEvent() {
	if(DDIR_PMMission.Phase == DDIR_PMMission.PACE_A){


	  if(DDIR_PMMission.Sensor_A_Reading_Valid==true){
	      pms_A.Sensor_OFF_A();
	}
  

	try{
		if( DDIR_PMMission.Intrinsic_A_Activity_Found==false){
		pm_A.Pace_ON_A();
		RealtimeThread.sleep(new RelativeTime(Pacing_Length_A,0));  //sleep
		pm_A.Pace_OFF_A();
		System.out.println("Periodic Pacing A");
		pms_V.Sensor_ON_V();
		}else{
		      DDIR_PMMission.Intrinsic_A_Activity_Found=false;
		      pms_V.Sensor_ON_V();
		}
	    

		//Record current time for pacing in atria
		
		AbsoluteTime oldTime = DDIR_PMMission.oldTime;
		AbsoluteTime newTime = Clock.getRealtimeClock().getTime();
		fmc.funModeChange(oldTime, newTime);
		long ms = newTime.getMilliseconds();
		int  ns = newTime.getNanoseconds();
		DDIR_PMMission.oldTime.set(ms,ns);			

		//DDIR_PMMission.oldTime = (AbsoluteTime) newTime.clone();





	}catch (InterruptedException ie)
	  {
	    System.out.println(ie.getMessage());
      }
DDIR_PMMission.Phase = DDIR_PMMission.SENSE_V;
}//end counter lock


  }
}
