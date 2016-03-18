package org.usfirst.frc.team4334.flywheel;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FlyConstants {
	 static int TICKS_PER_WHEEL_ROTATION = 1;
	 static int THREAD_TIME = 20;
	 static int RPM_REFRESH_TIME = 50;
	//error threshold for flywheel rpm
	 static double ERROR_THRESH = 200;
	 static double kP = 0.001;
	 static double kI = 0.00005;
	 static double kD = 0;
	 static double intLim = 0.30 / kI;	
	
	
	//RPMS
	 static double RPM_ON_BATTER = 6700;
	 static double POW_ON_BATTER = 0.5;
	
	 static double RPM_OBST = 8200;
	 static double POW_OBST = 0.7;
 
	 public static double RPM_AUTO_LOWBAR = 8000;
	 public static double POW_AUTO_LOWBAR = 0.9;
	 
	 public static double FLASH_WAIT = 100;
	 public static double FLASH_ERR_THRESH = 500;
	 
	 static boolean  placed = false;
	 public static void debugRpms(){
		 if(!placed){
			 SmartDashboard.putNumber("RPM BAT", RPM_ON_BATTER);
			 SmartDashboard.putNumber("RPM OBST", RPM_OBST);
			 placed = true;
		 }
		 RPM_ON_BATTER =  SmartDashboard.getNumber("RPM BAT");
		 RPM_OBST = SmartDashboard.getNumber("RPM OBST");
	 }
	 
	
}
