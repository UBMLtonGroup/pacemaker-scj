// Implementaion of a Mission
import javax.safetycritical.*;
import javax.realtime.*;
import javax.safetycritical.Mission;
import javax.realtime.AbsoluteTime;
import javax.realtime.RelativeTime;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.PriorityScheduler;
import javax.realtime.MemoryArea;

public class PMMission extends Mission {

	    public volatile static boolean pre_Pace_A=false; 
	    public volatile static boolean pre_Pace_V=false; 
	    
	    public volatile static boolean Sensor_A_State=false; 
	    public volatile static boolean Sensor_V_State=false; 
	    public volatile static int PVARP=350; 
	    public volatile static boolean AV_State=false; 
	    
	   //sensor and actuator for pacemaker in Atrial Chamber 
	    Actuator_A pm_A = new Actuator_A();
	    Sensor_A pms_A = new Sensor_A();
	    
	    //sensor and actuator for pacemaker in Ventrical Chamber 
	    Actuator_V pm_V = new Actuator_V();
	    Sensor_V pms_V = new Sensor_V();


	    protected void initialize() {
		System.out.println("Start Mission");

	    // pass configuration parameters
	    int Pace_Interval=1000;
	    int AVI=150;
	    int Pacing_Length=2;
	    	    
	  
		

		Handler_Pace_A_APer A_handle = new Handler_Pace_A_APer(Pace_Interval,Pacing_Length,pm_A,pms_A);
		AperiodicEvent eA = new AperiodicEvent(A_handle);
		
		Handler_Pace_V_APer V_handle = new Handler_Pace_V_APer(Pace_Interval,AVI,Pacing_Length,pm_V,pms_A,pms_V);
		AperiodicEvent eV = new AperiodicEvent(V_handle);

		
	    AsyncEventHandler Pace_A_DMH = new AsyncEventHandler(){
		public void handleAsyncEvent(){
                   PMMission.pre_Pace_A=true;
                   
		}
	    };

	    AsyncEventHandler Pace_V_DMH = new AsyncEventHandler(){
		public void handleAsyncEvent(){
                   PMMission.pre_Pace_V=true;
                   
		}
	    };


		new Handler_Pace_A_Per(Pace_Interval,Pacing_Length,pm_A,pms_V,pms_A,Pace_A_DMH);
		new Handler_Pace_V_Per(Pace_Interval,AVI,Pacing_Length,pm_V,pms_A,pms_V,Pace_V_DMH);

		new Read_Sensor_A(eA);
		new Read_Sensor_V(eV);

	}

	protected void cleanup() {
	}

	public long missionMemorySize() {
		return 100000;
	}
}
