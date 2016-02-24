package org.usfirst.frc.team4334.drive;

import org.usfirst.frc.team4334.robot.Robot;
import org.usfirst.frc.team4334.sensors.NavX;
import org.usfirst.frc.team4334.utils.PidController;
import org.usfirst.frc.team4334.utils.Utils;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveController {
	DriveBase drive;

	PidController master = new PidController(DriveConstants.DRIVE_KP,
			DriveConstants.DRIVE_KI, DriveConstants.DRIVE_KD,
			DriveConstants.DRIVE_INT_LIM, true);

	PidController slave = new PidController(DriveConstants.DRIVE_SLAVE_KP,
			DriveConstants.DRIVE_KI, DriveConstants.DRIVE_KD,
			DriveConstants.DRIVE_SLAVE_INT_LIM, true);

	PidController turnPid = new PidController(DriveConstants.TURN_KP,
			DriveConstants.TURN_KI, DriveConstants.TURN_KD,
			DriveConstants.TURN_INT_LIM, true);

	PidController navXStraight = new PidController(DriveConstants.STRAIGHT_KP,
			DriveConstants.STRAIGHT_KI, DriveConstants.DRIVE_KD,
			DriveConstants.DRIVE_INT_LIM, true);

	public DriveController(DriveBase dr) {
		drive = dr;
	}

	public void arcTurn(double radius, double orientation, boolean turnRight) {
		double insideSet = (2 * Math.PI * radius) * (orientation / 360)
				* DriveConstants.TICKS_PER_FEET;
		double outsideSet = (2 * Math.PI * (radius + DriveConstants.DRIVE_WIDTH))
				* (orientation / 360) * DriveConstants.TICKS_PER_FEET;
		
		double ratio = outsideSet / insideSet; //how much faster the left side needs to turn 
		
		boolean atSetpoint = false;
		long initTime = System.currentTimeMillis();
		double errorThresh = DriveConstants.DRIVE_STRAIGHT_ERROR_THRESH
				* DriveConstants.TICKS_PER_FEET;
		
		master.reset();
		slave.reset();


		int initLeft = drive.getLeftEnc();
		int initRight = drive.getRightEnc();
		double outsideError;
		double slaveError;
		while (!atSetpoint && notDisabled()) {
			if(turnRight){
				 outsideError = outsideSet - (drive.getLeftEnc() - initLeft);
				 slaveError = (drive.getLeftEnc() - initLeft)
						- (drive.getRightEnc() - initRight) * ratio;
			} else{
				 outsideError = outsideSet - (drive.getRightEnc() - initRight);
				 slaveError = (drive.getRightEnc() - initRight)
						- (drive.getLeftEnc() - initLeft) * ratio;	
			}
			System.out.println("outside error "  + outsideError);
			double driveOut = master.calculate(outsideError);
			double slaveOut = slave.calculate(slaveError);
			System.out.println("slave output " + slaveOut + " drive out " + driveOut);

			if (Math.abs(driveOut) > DriveConstants.MAX_AUTO_SPEED) {
				driveOut = DriveConstants.MAX_AUTO_SPEED * driveOut
						/ Math.abs(driveOut);
			}

			if (Math.abs(slaveOut) > DriveConstants.MAX_AUTO_SPEED) {
				slaveOut = DriveConstants.MAX_AUTO_SPEED * slaveOut
						/ Math.abs(slaveOut);
			}

			if(turnRight){
				drive.setDrive(driveOut - slaveOut, (driveOut / ratio) + slaveOut);
			} else{
				drive.setDrive((driveOut / ratio) - slaveOut, (driveOut) + slaveOut);
			}

			if (!(Math.abs(outsideError) < errorThresh)) {
				initTime = System.currentTimeMillis();
			} else {
				if (System.currentTimeMillis() - initTime > DriveConstants.SATISFY_TIME_MS) {
					atSetpoint = true;
					return;
				}
			}

			try {
				Thread.sleep(DriveConstants.THREAD_SLEEP_MS);
			} catch (Exception e) {

			}

		}
		drive.setDrive(0, 0);

		
	}

	public void driveFeet(double feet) {
		System.out.println("drivine");
		double setPoint = feet * DriveConstants.TICKS_PER_FEET;
		boolean atSetpoint = false;
		long initTime = System.currentTimeMillis();
		double errorThresh = DriveConstants.DRIVE_STRAIGHT_ERROR_THRESH
				* DriveConstants.TICKS_PER_FEET;

		master.reset();
		slave.reset();
		navXStraight.reset();

		double initHeading = NavX.getAngle();

		int initLeft = drive.getLeftEnc();
		int initRight = drive.getRightEnc();
		while (!atSetpoint && notDisabled()) {
			master.sendValuesToDashboard("master ");
			slave.sendValuesToDashboard("slave ");

			double angError = Utils.getAngleDifferenceDeg(initHeading,
					NavX.getAngle());
			System.out.println("calculated straight error is " + angError);

			double angOut = navXStraight.calculate(angError);

			double driveErr = setPoint - (drive.getLeftEnc() - initLeft);
			double slaveErr = (drive.getLeftEnc() - initLeft)
					- (drive.getRightEnc() - initRight);
			double driveOut = master.calculate(driveErr);
			double slaveOut = 0;// slave.calculate(slaveErr);

			SmartDashboard.putNumber("LEFT", drive.getLeftEnc());
			SmartDashboard.putNumber("RIGHT", drive.getRightEnc());
			SmartDashboard.putNumber("Err ", driveErr);
			SmartDashboard.putNumber("navx Err", angError);
			SmartDashboard.putNumber("navx out", angOut);
			SmartDashboard.putNumber("driveOut", driveOut);
			SmartDashboard.putNumber("error sum", master.errorSum);

			if (Math.abs(driveOut) > DriveConstants.MAX_AUTO_SPEED) {
				driveOut = DriveConstants.MAX_AUTO_SPEED * driveOut
						/ Math.abs(driveOut);
			}

			if (Math.abs(slaveOut) > DriveConstants.MAX_AUTO_SPEED) {
				slaveOut = DriveConstants.MAX_AUTO_SPEED * slaveOut
						/ Math.abs(slaveOut);
			}

			drive.setDrive(driveOut - slaveOut + angOut, driveOut + slaveOut
					- angOut);

			if (!(Math.abs(driveErr) < errorThresh)) {
				initTime = System.currentTimeMillis();
			} else {
				if (System.currentTimeMillis() - initTime > DriveConstants.SATISFY_TIME_MS) {
					atSetpoint = true;
					return;
				}
			}

			try {
				Thread.sleep(DriveConstants.THREAD_SLEEP_MS);
			} catch (Exception e) {

			}

		}
		drive.setDrive(0, 0);

	}

	private boolean notDisabled() {
		return Robot.gameState != Robot.RobotStates.DISABLED;
	}

	// need to change gyro to navx

	public void turnDegreesRel(double degrees) {
		// need to add navx here instead
		System.out.println("setting target " + NavX.getAngle() + degrees);
		turnDegreesAbsolute((NavX.getAngle() + degrees));
	}

	public void turnDegreesAbsolute(double degrees) {
		while (degrees > 360) {
			degrees -= 360;
		}
		while (degrees < 0) {
			degrees += 360;
		}
		double setPoint = degrees;
		boolean atSetpoint = false;
		long initTime = System.currentTimeMillis();

		double errorThresh = DriveConstants.TURN_ERROR_THRESH;
		int satTime = DriveConstants.SATISFY_TIME_TURN;

		turnPid.reset();
		slave.reset();

		int initLeft = drive.getLeftEnc();
		int initRight = drive.getRightEnc();

		System.out.println(NavX.getAngle());
		while (!atSetpoint && notDisabled()) {
			turnPid.sendValuesToDashboard("turn");
			double driveErr = Utils.getAngleDifferenceDeg(setPoint,
					NavX.getAngle());
			System.out.println(degrees + " turn err " + driveErr + "   set "
					+ setPoint + " actual " + NavX.getAngle());
			double slaveErr = (drive.getLeftEnc() - initLeft)
					+ (drive.getRightEnc() - initRight);
			double driveOut = turnPid.calculate(driveErr);
			double slaveOut = slave.calculate(slaveErr);

			if (Math.abs(driveOut) > DriveConstants.MAX_AUTO_TURN_SPEED) {
				driveOut = DriveConstants.MAX_AUTO_TURN_SPEED * driveOut
						/ Math.abs(driveOut);
			}

			if (Math.abs(slaveOut) > DriveConstants.MAX_AUTO_TURN_SPEED) {
				slaveOut = DriveConstants.MAX_AUTO_TURN_SPEED * slaveOut
						/ Math.abs(slaveOut);
			}

			slaveOut = 0;
			drive.setDrive(driveOut - slaveOut, -(driveOut + slaveOut));

			if (!(Math.abs(driveErr) < errorThresh)) {
				initTime = System.currentTimeMillis();
			} else {
				if (System.currentTimeMillis() - initTime > satTime) {
					return;
				}
			}

			try {
				Thread.sleep(DriveConstants.THREAD_SLEEP_MS);
			} catch (Exception e) {

			}

		}
		drive.setDrive(0, 0);
	}

	public void printEncoders() {
		SmartDashboard.putNumber("LEFT", drive.getLeftEnc());
		SmartDashboard.putNumber("RIGHT", drive.getRightEnc());
	}

	public void calc() {

	}

}
