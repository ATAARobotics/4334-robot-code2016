package org.usfirst.frc.team4334.drive;

import java.util.LinkedList;

import org.usfirst.frc.team4334.robot.Ports;
import org.usfirst.frc.team4334.robot.Robot;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;

public class DriveBase {

	private LinkedList<SpeedController> rightMotors;
	private LinkedList<SpeedController> leftMotors;

	public static Encoder leftEnc = new Encoder(Ports.ENCODER_LEFT,
			Ports.ENCODER_LEFT_2, true, EncodingType.k4X);
//
//	private static Encoder rightEnc = new Encoder(Ports.ENCODER_RIGHT,
//			Ports.ENCODER_RIGHT_2, true, EncodingType.k4X);

	public DriveBase() {
		rightMotors = new LinkedList<SpeedController>();
		leftMotors = new LinkedList<SpeedController>();
		if (Robot.isCompBot) {
			leftMotors.add(new VictorSP(Ports.DRIVE_LEFT_1));
			leftMotors.add(new VictorSP(Ports.DRIVE_LEFT_2));
			rightMotors.add(new VictorSP(Ports.DRIVE_RIGHT_1));
			rightMotors.add(new VictorSP(Ports.DRIVE_RIGHT_2));
		} else {

			leftMotors.add(new Victor(Ports.DRIVE_LEFT_1));
			leftMotors.add(new Victor(Ports.DRIVE_LEFT_2));
			rightMotors.add(new Victor(Ports.DRIVE_RIGHT_1));
			rightMotors.add(new Victor(Ports.DRIVE_RIGHT_2));
		}
	}

	public void setLeftPow(double pow) {
		for (SpeedController l : leftMotors) {
			l.set(pow);
		}
	}

	public void setRightPow(double pow) {
		for (SpeedController r : rightMotors) {
			r.set(pow);
		}
	}

	public void setDrive(double leftPow, double rightPow) {
		setLeftPow(leftPow);
		setRightPow(-rightPow);
	}
	
	public int getRightEnc() {
		return 0;//rightEnc.get();
	}

	public int getLeftEnc() {
		return leftEnc.get();
	}

}
