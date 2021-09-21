import javax.safetycritical.PriorityScheduler;
import javax.realtime.*;
import javax.safetycritical.*;




// Implementaion of a periodic event handler

public class Read_Sensor_V extends PeriodicEventHandler {
	SCJPacemakerJNI ob = new SCJPacemakerJNI();
	AperiodicEvent e;
	ModeChange fmc = new ModeChange();
	long interval;
	double res;
	
	public Read_Sensor_V(AperiodicEvent e1) {
		super(new PriorityParameters(PriorityScheduler.instance()
				.getNormPriority()), new PeriodicParameters(null,
				new RelativeTime(1, 0)), new StorageConfigurationParameters(
				32768, 4096, 4096), 65536, "Read_Sensor_A");
	e=e1;
	}

	public void handleEvent() {
		AbsoluteTime now =Clock.getRealtimeClock().getTime();		
      		interval = now.subtract(MainPMMissionSequence.lastAtriumActivityTime).getMilliseconds();      
		if(((interval <= MainPMMissionSequence.AVI && MainPMMissionSequence.CMode=="DDDR") || (interval <= MainPMMissionSequence.PaceInterval && MainPMMissionSequence.CMode=="DDIR")) &&(MainPMMissionSequence.Activity_V_Occured == false)) {
		    res = ob.readData();
		    if(res >= 0.9){
			System.out.println("Sensed Value in V");
			
			//Save intrinsic activity Time of Ventricle
			MainPMMissionSequence.lastVentricleActivityTime.set(Clock.getRealtimeClock().getTime().getMilliseconds(),Clock.getRealtimeClock().getTime().getNanoseconds());
			MainPMMissionSequence.Activity_V_Occured=true;

			////Reset Atrium flag
			MainPMMissionSequence.Activity_A_Occured=false;
			
			// mode update changing due to completion of one cycle (Atrium and Ventricle pacing or sensing)
			//fmc.funModeChange(MainPMMissionSequence.oldTime, now);
			MainPMMissionSequence.oldTime.set(now.getMilliseconds(),now.getNanoseconds());
		    }
		}else if(((interval > MainPMMissionSequence.AVI && MainPMMissionSequence.CMode=="DDDR")||(interval <= MainPMMissionSequence.PaceInterval && MainPMMissionSequence.CMode=="DDIR")) && (MainPMMissionSequence.Activity_V_Occured == false)){
		      e.fire();	     

		      // mode update changing due to completion of one cycle (Atrium and Ventricle pacing or sensing)
		    //  fmc.funModeChange(MainPMMissionSequence.oldTime, now);
		      MainPMMissionSequence.oldTime.set(now.getMilliseconds(),now.getNanoseconds());
		}
	}
}//end class 