package org.usfirst.frc.team4334.robot;

import org.usfirst.frc.team4334.drive.DriveController;
import org.usfirst.frc.team4334.flywheel.FlywheelController;
import org.usfirst.frc.team4334.subsystems.ArmController;
import org.usfirst.frc.team4334.subsystems.Intake;

public class Auto implements Runnable{
   
	static DriveController drive;
	static Intake intake;
	static FlywheelController fly;
	static ArmController arm;
	public Auto(DriveController dr, Intake intk, FlywheelController fl, ArmController a){
		drive = dr;
		fly = fl;
		intake = intk;
		arm = a;
	}
	
	
	public static void runDefault() {
		
	}
	
	public static void runAuto2() {
		
	}
	
	public static void runAutoLowBarOneBall(){
		System.out.println("running auto ");
		
//		fly.setFlySpeedBatter();
//		
//		drive.driveFeet(13);
//		drive.turnDegreesRel(20);
//		drive.driveFeet(3);
//		drive.turnDegreesRel(47);
//		drive.driveFeet(9);
//		
//		intake.driveIn();
	}


	@Override
	public void run() {
		drive.arcTurn(4, 90, true);
		runAutoLowBarOneBall();
		// TODO Auto-generated method stub
		
	}
	
}
