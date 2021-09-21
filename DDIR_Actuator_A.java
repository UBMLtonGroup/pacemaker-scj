import javax.realtime.PeriodicParameters;
import javax.realtime.PeriodicTimer;
import javax.realtime.PriorityParameters;
import javax.realtime.Timer;
import javax.realtime.RelativeTime;
import javax.realtime.AbsoluteTime;
import javax.realtime.HighResolutionTime;
import javax.realtime.Clock;


public class DDIR_Actuator_A{
SCJPacemakerJNI ob = new SCJPacemakerJNI();

	boolean PM_Actuator_A;
	public DDIR_Actuator_A() {
		PM_Actuator_A = false;
	}

	public synchronized void Pace_ON_A() {

		if (PM_Actuator_A == false) {
			PM_Actuator_A = true;
		ob.writeData(1);
			System.out.println("Pace ON A");
		}
	}

	
	public synchronized void Pace_OFF_A() {

		if (PM_Actuator_A == true) {
		PM_Actuator_A = false;
			System.out.println("Pace OFF A");
		}
	}

	
}// end main Actuator class

