package org.usfirst.frc.team4334.robot;

import org.usfirst.frc.team4334.control.JoystickController;
import org.usfirst.frc.team4334.control.MultiLooper;
import org.usfirst.frc.team4334.drive.DriveBase;
import org.usfirst.frc.team4334.drive.DriveController;
import org.usfirst.frc.team4334.drive.TeleopDrive;
import org.usfirst.frc.team4334.flywheel.Fly;
import org.usfirst.frc.team4334.flywheel.FlywheelController;
import org.usfirst.frc.team4334.sensors.NavX;
import org.usfirst.frc.team4334.subsystems.ArmController;
import org.usfirst.frc.team4334.subsystems.Intake;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	public static final boolean isCompBot = false;

	DriveBase driveBase = new DriveBase();
	DriveController driveControl = new DriveController(driveBase);
	TeleopDrive teleopDrive = new TeleopDrive(driveBase);

	Intake intake = new Intake();
	Fly fly = new Fly();
	ArmController armControl = new ArmController();
	FlywheelController flyControl = new FlywheelController(fly);

	JoystickController joyControl = new JoystickController(intake, flyControl,
			driveBase, armControl);

	public static RobotStates gameState = RobotStates.DISABLED;
	public static AutoChooser autoChooser = new AutoChooser();
	
	public static enum RobotStates {
		DISABLED, TELEOP, AUTO
	}

	public void robotInit() {

		NavX.reset();
		long initTime = System.currentTimeMillis();
		while (NavX.isCalibrating()
				|| System.currentTimeMillis() > initTime + 5000) {
			Timer.delay(0.05);
		}
		autoChooser.putChoosersOnDash();
	}

	MultiLooper autoLooper = new MultiLooper("auto ", 0.02);
	boolean firstAuto = true;

	public void autonomousInit() {
		gameState = RobotStates.AUTO;
		if (firstAuto) {
			autoLooper.addLoopable(flyControl);
			firstAuto = false;
		}
	}

	Auto auto = new Auto(driveControl, intake, flyControl, armControl);


	Thread autoThread;
	public void autonomousPeriodic() {
		if (isAutonomous() && isEnabled()) {
			autoLooper.start();

			Robot.gameState = RobotStates.AUTO;
			ranAuto = true;

			autoThread = new Thread(auto);
			autoThread.start();
			
			while (isAutonomous() && isEnabled()) {
				Timer.delay(0.02);
			}
			autoLooper.stop();
			autoThread.stop();
			ranAuto = false;
			driveControl.giveStraightDrivePow(0, 0);
		}
		Robot.gameState = RobotStates.DISABLED;
		Timer.delay(0.02);
	}

	boolean ranAuto = false;
	public void disabledInit(){
		if(ranAuto){
			autoThread.interrupt();
			ranAuto = false;
		}
	}
	
	public void disabledPeriodic() {
		Robot.gameState = RobotStates.DISABLED;
		while (isDisabled()) {
			Timer.delay(0.02);
		}
	}

	MultiLooper teleLooper = new MultiLooper("tele looper", 0.02);
	boolean firstRun = true;

	public void teleopInit() {
		System.out.println("adding loopables ");
		if (firstRun) {
			teleLooper.addLoopable(joyControl);
			teleLooper.addLoopable(flyControl);
			// teleLooper.addLoopable(armControl);

			firstRun = false;
		}
		Robot.gameState = RobotStates.TELEOP;

	}



	public void teleopPeriodic() {
		teleLooper.start();
		Robot.gameState = RobotStates.TELEOP;
		while (isOperatorControl() && isEnabled()) {
//			Relay light = new Relay(Ports.LIGHT_RELAY);
//			light.set(Relay.Value.kOn);
//			light.set(Relay.Value.kForward);
			SmartDashboard.putNumber("encoder L", driveBase.getLeftEnc());
			SmartDashboard.putNumber("encoder R", driveBase.getRightEnc());
			SmartDashboard.putNumber("navx_X_disp" , NavX.getDisplacementX());
			SmartDashboard.putNumber("navx_Y_disp" , NavX.getDisplacementY());
			SmartDashboard.putNumber("navx_Z_disp" , NavX.getDisplacementZ());
			
			Timer.delay(0.02);
		}
		Robot.gameState = RobotStates.DISABLED;
		teleLooper.stop();
	}
}
