package org.usfirst.frc.team4334.robot;

import org.usfirst.frc.team4334.drive.DriveController;
import org.usfirst.frc.team4334.flywheel.FlyConstants;
import org.usfirst.frc.team4334.flywheel.FlywheelController;
import org.usfirst.frc.team4334.sensors.NavX;
import org.usfirst.frc.team4334.subsystems.ArmController;
import org.usfirst.frc.team4334.subsystems.Intake;

public class Auto implements Runnable {

	static DriveController drive;
	static Intake intake;
	static FlywheelController fly;
	static ArmController arm;

	public Auto(DriveController dr, Intake intk, FlywheelController fl,
			ArmController a) {
		drive = dr;
		fly = fl;
		intake = intk;
		arm = a;
	}

	public static void runDefault() {

	}

	public static void runAuto2() {

	}

	public static void runAutoLowBarOneBall() {
		System.out.println("running auto ");

		// fly.setFlySpeedBatter();
		//
		// drive.driveFeet(13);
		// drive.turnDegreesRel(20);
		// drive.driveFeet(3);
		// drive.turnDegreesRel(47);
		// drive.driveFeet(9);
		//
		// intake.driveIn();
	}

	@Override
	public void run() {

		AutoChooser.Position pos = Robot.autoChooser.getAutoPosition();
		AutoChooser.AutoMode mode = Robot.autoChooser.getAutoChoice();

	
		if (pos == AutoChooser.Position.FIRST
				|| mode == AutoChooser.AutoMode.LOW_BAR_1_BALL) {
			lowBar1Ball();
		}
		// drive.arcTurn(1, 90, true);

		// fourthPosition();
		// drive.driveFeet(3,0.3);
		//
		// runAutoLowBarOneBall();
		// TODO Auto-generated method stub

	}
	
	public void crossChav(){
		double initAngle = NavX.getAngle();
		try{
			drive.driveFeet(10, 0.8);
		} catch (Exception e){
			
		}
	}
	
	

	public void fourthPosition() {
		try {
			double initAngle = NavX.getAngle();
			fly.setFlySpeedBatter();
			drive.driveFeet(15, 0.8);
			drive.turnDegreesAbsolute(initAngle - 90);
			drive.driveFeet(1, 0.5);
			drive.turnDegreesAbsolute(initAngle);
			drive.driveFeet(5.3, 0.99);
			drive.giveStraightDrivePow(0, 0);

			arm.setPow(-1);

			Thread.sleep(2000);

			arm.setPow(0);
			intake.setIntake(1);

			Thread.sleep(2000);
		} catch (Exception e) {
			System.out.println("exiting auto");
		}
	}

	public void fifthPosition() {
		try {
			double initAngle = NavX.getAngle();
			fly.setFlySpeedBatter();
			drive.driveFeet(22.5, 0.95);
			arm.setPow(-1);

			try {
				Thread.sleep(2000);
			} catch (Exception e) {

			}
			arm.setPow(0);
			drive.turnDegreesAbsolute(initAngle - 60);

			// drive.driveFeet(1,0.5);
			drive.driveFeet(0.3, 0.5);
			intake.setIntake(1);

			Thread.sleep(2000);
		} catch (Exception e) {

		}
	}

	public void lowBar1Ball() {
		try {
			double initAngle = NavX.getAngle();
			fly.setFlySpeedBatter();
			drive.driveFeet(11, 0.65);
			drive.turnDegreesAbsolute(initAngle + 20);
			drive.driveFeet(9.1, 0.9);
			drive.turnDegreesAbsolute(initAngle + 60);
			drive.driveFeet(3.5, 0.9);
			intake.setIntake(1);
			fly.setFlySpeed(0, 0);
			drive.driveFeet(-5, 95);
			drive.turnDegreesAbsolute(initAngle + 180);
		} catch (Exception e) {
			System.out.println("exiting auto ");
			e.printStackTrace();
			return;
		}

	}

	public void drivePowAndFire() {
		try {
			drive.giveStraightDrivePow(0.5, 0.5);
			Thread.sleep(3000);
			intake.setIntake(1);
			Thread.sleep(2000);
		} catch (Exception e) {

		}
	}

	public void driveAndShoot(int pos) {
		fly.setFlySpeedBatter();
		if (pos == 1) {

		} else if (pos == 2) {

		} else if (pos == 3) {

		} else if (pos == 4) {

		} else if (pos == 5) {
			drive.driveFeet(7);
			drive.driveFeet(5);
		}
		intake.setIntake(1);

	}

}
