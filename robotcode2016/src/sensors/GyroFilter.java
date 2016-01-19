package sensors;

import org.usfirst.frc.team4334.utils.Utils;

public class GyroFilter {

	static double getAngleDifferenceDeg(double target, double source){
		double a = target - source;
		return Utils.signedMod(a + 180, 360) - 180;
	}
	
	static double getAngleDifferenceRad(double target, double source){
		double a = target - source;
		return Utils.signedMod(a + Math.PI, 2 * Math.PI) - Math.PI;
	}
	
}
