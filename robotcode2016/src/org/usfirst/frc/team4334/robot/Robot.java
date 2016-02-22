package org.usfirst.frc.team4334.robot;

import org.usfirst.frc.team4334.control.JoystickController;
import org.usfirst.frc.team4334.control.MultiLooper;
import org.usfirst.frc.team4334.drive.DriveBase;
import org.usfirst.frc.team4334.drive.TeleopDrive;
import org.usfirst.frc.team4334.flywheel.Fly;
import org.usfirst.frc.team4334.flywheel.FlywheelController;
import org.usfirst.frc.team4334.subsystems.Arm;
import org.usfirst.frc.team4334.subsystems.ArmController;
import org.usfirst.frc.team4334.subsystems.Intake;


import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {



	DriveBase driveBase = new DriveBase();
	//DriveController driveControl = new DriveController(driveBase);
	TeleopDrive teleopDrive = new TeleopDrive(driveBase);
	
	Intake intake = new Intake();
	Fly fly = new Fly();
	ArmController armControl = new ArmController();
	FlywheelController flyControl = new FlywheelController(fly);

	JoystickController joyControl = new JoystickController(intake,
			flyControl, driveBase, armControl);

	public static RobotStates gameState = RobotStates.DISABLED;

	public static enum RobotStates {
		DISABLED, TELEOP, AUTO
	}

	public void robotInit() {

	}

	public void disabled() {
		Robot.gameState = RobotStates.DISABLED;
	}

	public void test() {

	}

	public void autonomousInit() {

	}

	MultiLooper autoLooper = new MultiLooper("auto ", 200);

	public void autonomousPeriodic() {

		if (isAutonomous() && isEnabled()) {
			Robot.gameState = RobotStates.AUTO;
			while (isAutonomous() && isEnabled()) {
				if (isDisabled()) {
					Robot.gameState = RobotStates.DISABLED;
					autoLooper.stop();
				}
				Timer.delay(0.02);
			}
		}

		Robot.gameState = RobotStates.DISABLED;
		Timer.delay(0.02);
	}

	public void disabledPeriodic() {
		Robot.gameState = RobotStates.DISABLED;
		while (isDisabled()) {
			Timer.delay(0.02);
		}
	}

	MultiLooper teleLooper = new MultiLooper("tele looper", 0.05);
	boolean firstRun = true;
	public void teleopInit() {
		System.out.println("adding loopables ");
		if (firstRun) {
			teleLooper.addLoopable(joyControl);
			teleLooper.addLoopable(flyControl);
			teleLooper.addLoopable(armControl);
		
			firstRun = false;
		}
		Robot.gameState = RobotStates.TELEOP;

	}

	public void teleopPeriodic() {
		System.out.println("starting loopables");
		teleLooper.start();
		while (isOperatorControl() && isEnabled()) {
			SmartDashboard.putNumber("encoder L", driveBase.getLeftEnc());
			SmartDashboard.putNumber("encoder R", driveBase.getLeftEnc());
			Timer.delay(0.02);
		}
		Robot.gameState = RobotStates.DISABLED;
		teleLooper.stop();
	}
}
