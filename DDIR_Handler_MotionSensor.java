import javax.realtime.PeriodicParameters;
import javax.realtime.PeriodicTimer;
import javax.realtime.PriorityParameters;
import javax.realtime.PriorityScheduler;
import javax.realtime.Timer;
import javax.realtime.RelativeTime;
import javax.safetycritical.StorageConfigurationParameters;
import javax.realtime.*;
import javax.safetycritical.*;

// Implementaion of a periodic event handler

public class DDIR_Handler_MotionSensor extends PeriodicEventHandler {
static boolean RM_flag = false ;
double S_value=0.0;

	public DDIR_Handler_MotionSensor() {
		super(new PriorityParameters(PriorityScheduler.instance()
				.getNormPriority()), new PeriodicParameters(null,
				new RelativeTime(30000, 0)), new StorageConfigurationParameters(
				32768, 4096, 4096), 65536, "DDIR_Handler_MotionSensor");

	}

	public void handleEvent() {
	  try{

	    if( RM_flag == false  && S_value > 0.5 ){  // different activities have diffrent threshold so for medium we have 0.5 
	      for(int i=1; i< (int)Math.ceil((120-60)/DDIR_PMMission.Slop);i++){
		DDIR_PMMission.PaceInterval =  (int)Math.ceil(60000/(60 + DDIR_PMMission.Slop*i));
		RealtimeThread.sleep(new RelativeTime(DDIR_PMMission.reactionTime/(int)Math.ceil((120-60)/DDIR_PMMission.Slop),0));  //sleep
		System.out.println("Update Pacing Interval :" + DDIR_PMMission.PaceInterval);
	      }
	    RM_flag = true;
	    }else if(RM_flag == true  && S_value <= 0.5){
	      for(int i=1; i< Math.ceil((120-60)/DDIR_PMMission.Slop);i++){
		DDIR_PMMission.PaceInterval =  (int)Math.ceil(60000/(120 - DDIR_PMMission.Slop*i));
		 RealtimeThread.sleep(new RelativeTime((int)Math.ceil(DDIR_PMMission.recoveryTime/Math.ceil((120-60)/DDIR_PMMission.Slop)),0));  //sleep
		 System.out.println("Update Pacing Interval :" + DDIR_PMMission.PaceInterval);
	      }
	      RM_flag = false;
	    }

	  }catch (InterruptedException ie){
	    System.out.println(ie.getMessage());
	   }
}

}// end class








