
//Implementation of Ventricle Actuator

public class Actuator_V{
SCJPacemakerJNI ob = new SCJPacemakerJNI();
	
	public Actuator_V() { }

	public synchronized void Pace_ON_V() {
		    System.out.println("Pace ON V");
	}

	
	public synchronized void Pace_OFF_V() {
		    System.out.println("Pace OFF V");
	}
}// end main Actuator class

