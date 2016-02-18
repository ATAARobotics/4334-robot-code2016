package flywheel;

import org.usfirst.frc.team4334.control.Loopable;

public class FlywheelBangBang {
	private int desiredRPM;
	
	
	public FlywheelBangBang(int desiredRPM){
		this.desiredRPM = desiredRPM;
	}
	
	public int getStatus(int currentRPM){
		if(currentRPM > desiredRPM){
			//return 1 if currentRPM is more than desired
			return 1;
		}
		else if(currentRPM < desiredRPM){
			//return -1 if currentRPM is less than desired
			return -1;
		}
		else{
			//return if equal
			return 0;
		}
		
	}
	
}
