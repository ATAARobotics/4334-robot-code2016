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
}
