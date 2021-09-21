import javax.safetycritical.PriorityScheduler;
import javax.realtime.*;
import javax.safetycritical.*;


// Implementaion of a periodic event handler

public class ModeChange{
static int P_Count=0;
long interval;

public void funModeChange(AbsoluteTime oldTime, AbsoluteTime newTime){
    interval = newTime.subtract(oldTime).getMilliseconds();
System.out.println("P Count :" +  P_Count + "Interval: " + interval);      
	if ( interval < MainPMMissionSequence.MSR){
	      P_Count =  P_Count + 1;
	  System.out.println("P Count :" +  P_Count + "Interval: " + interval);
	}

	if ( interval > MainPMMissionSequence.MSR){
	      P_Count =  0;
	  System.out.println("P Count = 0");
	}

	// DDDR -> DDIR   
	if (P_Count==5 && MainPMMissionSequence.CMode=="DDDR")   // x out of y algo x range (2 to7), we have considered 5 
	{
	      MainPMMissionSequence.CMode="DDIR";
	      Mission.getCurrentMission().requestTermination();
	      P_Count = 0;
	      System.out.println("Operating Mode: DDIR");
	}

	// DDIR -> DDDR   
	if (P_Count==8 && MainPMMissionSequence.CMode=="DDIR")   // x out of y algo x range (2 to7), we have considered 8 (to return back to previous mode DDDR)
	{
	      MainPMMissionSequence.CMode="DDDR";
	      Mission.getCurrentMission().requestTermination();
	      P_Count = 0;
	      System.out.println("Operating Mode: DDDR");
	}
    }
}//end class