import javax.realtime.PeriodicParameters;
import javax.realtime.PeriodicTimer;
import javax.realtime.PriorityParameters;
import javax.realtime.PriorityScheduler;
import javax.realtime.Timer;
import javax.realtime.RelativeTime;
import javax.safetycritical.StorageConfigurationParameters;
import javax.realtime.*;
import javax.realtime.Clock;
import javax.safetycritical.*;



// Implementaion of a periodic event handler

public class DDIR_Handler_Pace_V_Per extends PeriodicEventHandler {

	
	DDIR_Actuator_V pm_V;
	DDIR_Sensor_A pms_A ;
	DDIR_Sensor_V pms_V ;
	int Counter=0;
	
	int Pacing_Length_V ;
	int Pace_Interval_V;
	int PVARP;
	

	
	public DDIR_Handler_Pace_V_Per(int Pace_Interval,int New_Pace_Interval, int Pacing_Length, DDIR_Actuator_V pm_V_temp, DDIR_Sensor_A pms_A_temp,DDIR_Sensor_V pms_V_temp, int PVARP_temp,AsyncEventHandler handler) {
		super(new PriorityParameters(PriorityScheduler.instance()
				.getNormPriority()), new PeriodicParameters(null,
				new RelativeTime(10, 0),null, new RelativeTime(8,0),null,handler), new StorageConfigurationParameters(
				32768, 4096, 4096), 65536, "Handler_Pace_V_Per");
	Pacing_Length_V = Pacing_Length;  
	pm_V=pm_V_temp;
	pms_A = pms_A_temp;
	pms_V = pms_V_temp;
	Pace_Interval_V=Pace_Interval;
	PVARP=PVARP_temp;
	
  
	}

	public void handleEvent() {
      if (DDIR_PMMission.Phase == DDIR_PMMission.PACE_V){


	try{
  
	  //pacing when old interval 			
	  if(DDIR_PMMission.New_Pace_Interval == 0 && Counter > Pace_Interval_V){
	      	        
		if(DDIR_PMMission.Sensor_V_Reading_Valid==true && DDIR_PMMission.Intrinsic_V_Activity_Found==false){
		pms_V.Sensor_OFF_V();
 		pm_V.Pace_ON_V();
		RealtimeThread.sleep(new RelativeTime(Pacing_Length_V,0));  //sleep
		pm_V.Pace_OFF_V();
		System.out.println("Pace in V at Pace Interval, Counter :" + Counter);
		RealtimeThread.sleep(new RelativeTime(PVARP,0));  //sleep
		pms_A.Sensor_ON_A();
		Counter =0;
		}else{
		      DDIR_PMMission.Intrinsic_V_Activity_Found=false;
		}
		
	//pacing when new interval 	
	}else if(DDIR_PMMission.New_Pace_Interval > 350 && Counter > DDIR_PMMission.New_Pace_Interval){

	      	if(DDIR_PMMission.Sensor_V_Reading_Valid==true && DDIR_PMMission.Intrinsic_V_Activity_Found==false){
		pms_V.Sensor_OFF_V();
		pm_V.Pace_ON_V();
		RealtimeThread.sleep(new RelativeTime(Pacing_Length_V,0));  //sleep
		pm_V.Pace_OFF_V();
		System.out.println("Pace in V at New Pace Interval, Counter=" + Counter);
		RealtimeThread.sleep(new RelativeTime(PVARP,0));  //sleep
		pms_A.Sensor_ON_A();
		Counter =0;
		}else{	
		      DDIR_PMMission.Intrinsic_V_Activity_Found=false;
		}
		
		
	}else{
	  Counter = Counter + 10;
	    
	}

	}catch (InterruptedException ie)
	  {
	    System.out.println(ie.getMessage());
      }
DDIR_PMMission.Phase = DDIR_PMMission.SENSE_A;
    }//end counter lock

}



    
}//end class

