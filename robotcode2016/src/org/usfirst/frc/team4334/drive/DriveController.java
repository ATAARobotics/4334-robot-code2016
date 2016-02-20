package org.usfirst.frc.team4334.drive;

import org.usfirst.frc.team4334.robot.Robot;
import org.usfirst.frc.team4334.utils.PidController;
import org.usfirst.frc.team4334.utils.Utils;
import org.usfirst.frc.team4334.sensors.NavX;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotBase;
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

	public DriveController(DriveBase dr) {
		drive = dr;
	}

	public void driveFeet(double feet) {
		double setPoint = feet * DriveConstants.TICKS_PER_FEET;
		boolean atSetpoint = false;
		long initTime = System.currentTimeMillis();
		double errorThresh = DriveConstants.DRIVE_STRAIGHT_ERROR_THRESH
				* DriveConstants.TICKS_PER_FEET;

		master.reset();
		slave.reset();

		int initLeft = drive.getLeftEnc();
		int initRight = drive.getRightEnc();
		while (!atSetpoint && notDisabled()) {
			master.sendValuesToDashboard("master ");
			slave.sendValuesToDashboard("slave ");

			double driveErr = setPoint - (drive.getLeftEnc() - initLeft);
			double slaveErr = (drive.getLeftEnc() - initLeft)
					- (drive.getRightEnc() - initRight);
			double driveOut = master.calculate(driveErr);
			double slaveOut = slave.calculate(slaveErr);

			SmartDashboard.putNumber("LEFT", drive.getLeftEnc());
			SmartDashboard.putNumber("RIGHT", drive.getRightEnc());
			SmartDashboard.putNumber("Err ", driveErr);
			SmartDashboard.putNumber("Slave Err", slaveErr);
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

			drive.setDrive(driveOut - slaveOut, driveOut + slaveOut);

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
		// turnDegreesAbsolute(gyro.getAngle() + degrees);
	}

	public void turnDegreesAbsolute(double degrees) {
		double setPoint = degrees;
		boolean atSetpoint = false;
		long initTime = System.currentTimeMillis();

		double errorThresh = DriveConstants.TURN_ERROR_THRESH;
		int satTime = DriveConstants.SATISFY_TIME_TURN;

		turnPid.reset();
		slave.reset();

		int initLeft = drive.getLeftEnc();
		int initRight = drive.getRightEnc();
		while (!atSetpoint && notDisabled()) {
			turnPid.sendValuesToDashboard("turn");
			double driveErr = Utils.getAngleDifferenceDeg(setPoint,
					NavX.getAngle());
			// System.out.println("turn err " + driveErr + "   set " + setPoint
			// + " actual " + gyro.getAngle());
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
