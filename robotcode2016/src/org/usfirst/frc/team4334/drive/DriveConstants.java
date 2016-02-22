package org.usfirst.frc.team4334.drive;

import org.usfirst.frc.team4334.utils.PidController;

public class DriveConstants {
	public static final double JOY_DEADZONE = 0.2;
	public static final double TICKS_PER_INCH = (2 * Math.PI * 4);
	public static final double TICKS_PER_FEET = TICKS_PER_INCH * 12;

	public static final int THREAD_SLEEP_MS = 20;
	
	public static final int SATISFY_TIME_MS = 500;
	public static final int SATISFY_TIME_TURN = 500;
	
	//2 inches
	public static final double DRIVE_STRAIGHT_ERROR_THRESH = 2;
	
	//2 degrees 
	public static final double TURN_ERROR_THRESH = 2;
	
	public static final double MAX_AUTO_SPEED = 0.6;
	public static final double MAX_AUTO_TURN_SPEED = 0.6;
	//PID Constants

	
	//drive straight
	public static final double DRIVE_KP = 0.040;
	public static final double DRIVE_KI = 0.001;
	public static final double DRIVE_KD = 0.200;
	public static final double DRIVE_INT_LIM = 200;
	
	public static final double DRIVE_SLAVE_KP = 0.020;
	public static final double DRIVE_SLAVE_KI = 0;
	public static final double DRIVE_SLAVE_KD = 0.080;
	public static final double DRIVE_SLAVE_INT_LIM = 200;
	
	//gyro turn 
	public static final double TURN_KP = 0.050;
	public static final double TURN_KI = 0.0006;
	public static final double TURN_KD = 0.02;
	public static final double TURN_INT_LIM = 600;
	
	
	
}
