import javax.safetycritical.Safelet;
import javax.safetycritical.MissionSequencer;
import javax.safetycritical.annotate.Level;

// Implementaion of a Safelet

public class PMSafelet implements Safelet {

	public Level getLevel() {
		Level l = new Level(1);
		return l;
	}

	public void setup() {
	}

	public MissionSequencer getSequencer() {
		return new MainPMMissionSequence();
	}

	public void teardown() {
	}

}
