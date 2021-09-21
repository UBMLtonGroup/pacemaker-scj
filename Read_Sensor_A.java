      import javax.safetycritical.PriorityScheduler;
import javax.realtime.*;
import javax.safetycritical.*;




// Implementaion of a periodic event handler

public class Read_Sensor_A extends PeriodicEventHandler {
	SCJPacemakerJNI ob = new SCJPacemakerJNI();
	AperiodicEvent e;
	long interval;
	double res; 

	public Read_Sensor_A(AperiodicEvent e1) {
		super(new PriorityParameters(PriorityScheduler.instance()
				.getNormPriority()), new PeriodicParameters(null,
				new RelativeTime(1, 0)), new StorageConfigurationParameters(
				32768, 4096, 4096), 65536, "Read_Sensor_A");
	e=e1;
	}

	public void handleEvent() {
		AbsoluteTime now =Clock.getRealtimeClock().getTime();
		interval = now.subtract(MainPMMissionSequence.lastVentricleActivityTime).getMilliseconds();      
		if( (interval > MainPMMissionSequence.PVARP) && (interval < (MainPMMissionSequence.PaceInterval-MainPMMissionSequence.AVI)) && (MainPMMissionSequence.Activity_A_Occured==false)){
		    res = ob.readData();
		    if(res >= 0.3){
			System.out.println("Sensed Value in A");	
			System.out.println("interval A: " + interval); 
			//Save intrinsic activity Time of Atrium 
		      	MainPMMissionSequence.lastAtriumActivityTime.set(now.getMilliseconds(),now.getNanoseconds());
			MainPMMissionSequence.Activity_A_Occured = true;

			//Reset Ventricle flag
			MainPMMissionSequence.Activity_V_Occured =false;
			
		    }
		}else if((interval > (MainPMMissionSequence.PaceInterval - MainPMMissionSequence.AVI)) && MainPMMissionSequence.Activity_A_Occured == false ){
		      e.fire();		      
		     

		           
		      
		}
	}
}//end class 