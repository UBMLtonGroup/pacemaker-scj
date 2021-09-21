
//Implementation of Atrium Actuator

public class Actuator_A{
SCJPacemakerJNI ob = new SCJPacemakerJNI();

	public Actuator_A() { }

	public synchronized void Pace_ON_A() {
		    ob.writeData(1);
		    System.out.println("Pace ON A");
	}

	
	public synchronized void Pace_OFF_A() {
		  //  ob.writeData(0);
		    System.out.println("Pace OFF A");
	}
}// end main Actuator class

