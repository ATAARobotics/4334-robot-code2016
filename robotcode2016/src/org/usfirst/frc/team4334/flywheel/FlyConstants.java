package org.usfirst.frc.team4334.flywheel;

public class FlyConstants {
	final static int TICKS_PER_WHEEL_ROTATION = 1;
	final static int THREAD_TIME = 20;
	final static int RPM_REFRESH_TIME = 20;
	//error threshold for flywheel rpm
	final static double ERROR_THRESH = 200;
	final static double kP = 0.05;
	final static double kI = 0.01;
	final static double kD = 0;
	final static double intLim = 10000;	
	
	
	//RPMS
	final static double RPM_ON_BATTER = 3000;
	final static double POW_ON_BATTER = 0.5;
	
	final static double RPM_OBST = 3000;
	final static double POW_OBST = 0.5;
	
}
