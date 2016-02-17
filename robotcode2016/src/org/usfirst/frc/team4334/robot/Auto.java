package org.usfirst.frc.team4334.robot;

import java.util.TimerTask;

import org.usfirst.frc.team4334.drive.DriveController;

public class Auto extends TimerTask {

	public DriveController driveController;
	public Auto(DriveController d){
		driveController = d;
	}
	
	

	public void run() {
		driveController.turnDegreesRel(180);
	}
	
	
}
