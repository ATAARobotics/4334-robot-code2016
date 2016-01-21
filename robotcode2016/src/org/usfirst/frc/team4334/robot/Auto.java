package org.usfirst.frc.team4334.robot;

import org.usfirst.frc.team4334.drive.DriveController;

public class Auto implements Runnable {

	public DriveController driveController;
	public Auto(DriveController d){
		driveController = d;
	}
	
	
	@Override
	public void run() {
		driveController.driveFeet(5);
		driveController.turnDegreesAbsolute(180);
		driveController.driveFeet(5);
		driveController.turnDegreesAbsolute(180);
		
	}
	
	
}
