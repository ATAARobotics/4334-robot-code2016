package org.usfirst.frc.team4334.drive;

public class Utils {
	public static double deadzone(double in, double min){
		if(Math.abs(in) < Math.abs(min)){
			return 0;
		}
		return in;
	}
}
