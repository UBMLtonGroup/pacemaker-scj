import javax.realtime.PeriodicParameters;
import javax.realtime.PeriodicTimer;
import javax.realtime.PriorityParameters;
import javax.realtime.PriorityScheduler;
import javax.realtime.Timer;
import javax.realtime.RelativeTime;
import javax.safetycritical.StorageConfigurationParameters;
import javax.realtime.*;
import javax.safetycritical.*;
import java.util.Random;



// Implementaion of a periodic event handler

public class DDDR_MotionSensor_Handler extends PeriodicEventHandler {
static boolean RM_flag = false ;

	public DDDR_MotionSensor_Handler() {
		super(new PriorityParameters(PriorityScheduler.instance()
				.getNormPriority()), new PeriodicParameters(null,
				new RelativeTime(30000, 0)), new StorageConfigurationParameters(
				32768, 4096, 4096), 65536, "MotionSensor_Handler");
	}

	public void handleEvent() {
	  try{
	    double S_value = Motion_Sensor_AV();  
	    if( RM_flag == false  && S_value > 0.5 ){  // different activities have diffrent threshold so for medium we have 0.5 
	      for(int i=1; i< (int)Math.ceil((120-60)/DDDR_PMMission.Slop);i++){
		DDDR_PMMission.New_Pace_Interval =  (int)Math.ceil(60000/(60 + DDDR_PMMission.Slop*i));
		RealtimeThread.sleep(new RelativeTime(DDDR_PMMission.reactionTime/(int)Math.ceil((120-60)/DDDR_PMMission.Slop),0));  //sleep
		System.out.println("New Pacing Interval in DDDR:" + DDDR_PMMission.New_Pace_Interval);
	      }
	    RM_flag = true;
	    }else if(RM_flag == true  && S_value <= 0.5){
	      for(int i=1; i< Math.ceil((120-60)/DDDR_PMMission.Slop);i++){
		DDDR_PMMission.New_Pace_Interval =  (int)Math.ceil(60000/(120 - DDDR_PMMission.Slop*i));
		 RealtimeThread.sleep(new RelativeTime((int)Math.ceil(DDDR_PMMission.recoveryTime/Math.ceil((120-60)/DDDR_PMMission.Slop)),0));  //sleep
		 System.out.println("New Pacing Interval in DDDR:" + DDDR_PMMission.New_Pace_Interval);
	      }
	      RM_flag = false;
	    }

	  }catch (InterruptedException ie){
	    System.out.println(ie.getMessage());
	   }
}

	public double Motion_Sensor_AV() {
	//Sensor Reading Value 
	// Prototype Implementation  		
	double Sensor_Value=0;
	Random randomGenerator = new Random();
	Sensor_Value = randomGenerator.nextDouble();
	  return Sensor_Value;
	}

}// end class








