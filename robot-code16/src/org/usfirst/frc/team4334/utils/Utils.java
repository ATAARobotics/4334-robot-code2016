package org.usfirst.frc.team4334.utils;

public class Utils {
	
	public static double deadzone(double input, double min){
		return Math.abs(input) < Math.abs(min) ?  0 : input;
	}
	
	//returns input clamped between -max and max
	public static double clamp(double input, double max){
		if(Math.abs(input) > max){
			return max * input/Math.abs(input);
		}
		return input;
	}
	
	public static double signedMod(double a, double n){
		return (a % n + n) % n;
	}
	

	public static double getAngleDifferenceDeg(double target, double source){
		double a = target - source;
		a = signedMod((a + 180), 360) - 180;
		return a;
	}
	
	public static double getAngleDifferenceRad(double target, double source){
		double a = target - source;
		return Utils.signedMod(a + Math.PI, 2 * Math.PI) - Math.PI;
	}
	
	public static double feetToMetres(double feet){
		return feet * 0.3048;
	}
	
	public static double metresToFeet(double metres){
		return metres * 3.28084;
	}
	
}
