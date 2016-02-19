package org.usfirst.frc.team4334.drive;

import java.util.TimerTask;

import org.usfirst.frc.team4334.control.Loopable;
import org.usfirst.frc.team4334.robot.Ports;
import org.usfirst.frc.team4334.utils.Utils;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TeleopDrive {
	private DriveBase drive;

	public TeleopDrive(DriveBase d) {
		drive = d;
	}

	public DriveMode currMode = DriveMode.HALO;

	public static enum DriveMode {
		HALO, ARCADE
	}
	
	public void teleopDrive(Joystick joy) {
		teleopDrive(DriveConstants.JOY_DEADZONE,joy);
	}

	public void teleopDrive(double deadzone, Joystick joy) {
		teleopDrive(DriveConstants.JOY_DEADZONE, this.currMode, joy, 1);
	}

	public void teleopDrive(double deadzone, DriveMode desiredMode, Joystick joy, double multiplier) {
		//boolean flipped = Math.abs(joy.getRawAxis(3)) > 0.5 ? true : false;
		double x1 = Utils.deadzone(joy.getRawAxis(0),
				DriveConstants.JOY_DEADZONE);
		double y1 = -Utils.deadzone(joy.getRawAxis(1),
				DriveConstants.JOY_DEADZONE);
		double x2 = Utils.deadzone(joy.getRawAxis(4),
				DriveConstants.JOY_DEADZONE);
		double y2 = -Utils.deadzone(joy.getRawAxis(5),
				DriveConstants.JOY_DEADZONE);


		// forward on stick b, turn on stick a
		if (this.currMode == DriveMode.HALO) {
			y1 = mapToNDeg(y1, 2);
			x2 = mapToNDeg(x2, 2);
			
			double leftPow = ((double) y1 + (double) x2) * multiplier;
			double rightPow = ((double) y1 - (double) x2) * multiplier;

			drive.setDrive(leftPow, rightPow);
//			if (flipped) {
//				drive.setDrive((double) y2 + (double) x1, (double) y2
//						- (double) x1);
//			}
		}

		if (currMode == DriveMode.ARCADE) {
			drive.setDrive(y1 + x1, y1 - x1);
		}
	}

	private double mapToNDeg(double in, int n) {
		if (in != 0) {
			if (n % 2 == 0) {
				return (Math.pow(in, n) * (in / Math.abs(in)));
			} else {
				return Math.pow(in, n);
			}
		}
		return 0;
	}
}
