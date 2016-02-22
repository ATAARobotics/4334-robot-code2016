package org.usfirst.frc.team4334.robot;

import org.usfirst.frc.team4334.drive.DriveController;
import org.usfirst.frc.team4334.flywheel.FlywheelController;
import org.usfirst.frc.team4334.subsystems.IntakeController;

public class Auto {
   
	static DriveController drive;
	static IntakeController intake;
	static FlywheelController fly;
	public Auto(DriveController dr, IntakeController intk, FlywheelController fl){
		drive = dr;
		fly = fl;
		intake = intk;
	}
	
	
	public static void runDefault() {
	    System.out.println("Dank auto did dank things yay");
	}
	
	public static void runAuto2() {
	    System.out.println("DIFFERENT dank auto did things again ayyyyeeeee");
	}
	
	public static void runAutoLowBarOneBall(){
		drive.driveFeet(5);
		drive.driveFeet(-5);
	}
	
}
