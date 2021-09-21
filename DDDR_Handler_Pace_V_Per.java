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

public class DDDR_Handler_Pace_V_Per extends PeriodicEventHandler {

	DDDR_ModeChange fmc = new DDDR_ModeChange();
	DDDR_Actuator_V pm_V;
	DDDR_Sensor_A pms_A ;
	DDDR_Sensor_V pms_V ;
	int Counter=0;
	
	int Pacing_Length_V ;
	int Pace_Interval_V;
	int New_Pace_Interval_V;
	int PVARP;
	int AV_Delay;

	
	public DDDR_Handler_Pace_V_Per(int Pace_Interval,int New_Pace_Interval, int AVI, int Pacing_Length, DDDR_Actuator_V pm_V_temp, DDDR_Sensor_A pms_A_temp,DDDR_Sensor_V pms_V_temp, int PVARP_temp,AsyncEventHandler handler) {
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
	AV_Delay=AVI;
  
	}

	public void handleEvent() {
	if (DDDR_PMMission.Phase == DDDR_PMMission.PACE_V){
	try{

	  if(DDDR_PMMission.Intrinsic_V_Activity_Found==false && Counter > AV_Delay ){
		pms_V.Sensor_OFF_V();
		pm_V.Pace_ON_V();
		RealtimeThread.sleep(new RelativeTime(Pacing_Length_V,0));  //sleep
		pm_V.Pace_OFF_V();
		System.out.println("Pace in V, counter=" +Counter);
		RealtimeThread.sleep(new RelativeTime(PVARP,0));  //sleep
		pms_A.Sensor_ON_A();
		DDDR_PMMission.Phase = DDDR_PMMission.SENSE_A;
		Counter =0;

		//Record current time for intrinsic activity in Ventricle for Mode switching 
		AbsoluteTime oldTime = DDDR_PMMission.oldTime;
		AbsoluteTime newTime = Clock.getRealtimeClock().getTime();
		fmc.funModeChange(oldTime, newTime);
		long ms = newTime.getMilliseconds();
		int  ns = newTime.getNanoseconds();
		DDDR_PMMission.oldTime.set(ms,ns);		

	}else{
	  Counter = Counter + 10;
		System.out.println("Pace in V, counter=" +Counter + DDDR_PMMission.Intrinsic_V_Activity_Found);
	}

	}catch (InterruptedException ie)
	  {
	    System.out.println(ie.getMessage());
	}

	}//end counter lock

}
    
}//end class

