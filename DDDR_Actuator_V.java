import javax.realtime.PeriodicParameters;
import javax.realtime.PeriodicTimer;
import javax.realtime.PriorityParameters;
import javax.realtime.Timer;
import javax.realtime.RelativeTime;
import javax.realtime.AbsoluteTime;
import javax.realtime.HighResolutionTime;
import javax.realtime.Clock;


public class DDDR_Actuator_V{
SCJPacemakerJNI ob = new SCJPacemakerJNI();
	boolean PM_Actuator_V;
	public DDDR_Actuator_V(){
		PM_Actuator_V = false;
	}

	public synchronized void Pace_ON_V() {
		if (PM_Actuator_V == false) {
			PM_Actuator_V = true;
ob.writeData(1);
			System.out.println("Pace ON V");
		}
	}

	public synchronized void Pace_OFF_V() {
		if (PM_Actuator_V == true) {
			PM_Actuator_V = false;
			System.out.println("Pace OFF V");
		}
	}

}// end main Actuator_V class

