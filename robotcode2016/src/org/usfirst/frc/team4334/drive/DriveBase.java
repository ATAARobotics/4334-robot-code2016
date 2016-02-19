package org.usfirst.frc.team4334.drive;

import java.util.LinkedList;
import java.util.TimerTask;

import org.usfirst.frc.team4334.robot.Ports;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.TalonSRX;

public class DriveBase {

	private LinkedList<SpeedController> rightMotors;
	private LinkedList<SpeedController> leftMotors;
	
	private static Encoder leftEnc = new Encoder(
			Ports.ENCODER_LEFT, Ports.ENCODER_LEFT + 1, true,
			EncodingType.k4X);
	
	private static Encoder rightEnc = new Encoder(Ports.ENCODER_RIGHT,
			Ports.ENCODER_RIGHT + 1, true, EncodingType.k4X);
	
	public DriveBase(LinkedList<SpeedController> leftM, LinkedList<SpeedController> rightM,
			Counter leftE, Counter rightE){
		leftMotors.add(new TalonSRX(Ports.DRIVE_LEFT_1));
		leftMotors.add(new TalonSRX(Ports.DRIVE_LEFT_2));
		rightMotors.add(new TalonSRX(Ports.DRIVE_RIGHT_1));
		rightMotors.add(new TalonSRX(Ports.DRIVE_RIGHT_2));
	}
	
	public DriveBase(LinkedList<SpeedController> left,
			LinkedList<SpeedController> right) {
		rightMotors = left;
		leftMotors = right;

	}

	public void setLeftPow(double pow){
		for(SpeedController l : leftMotors){
			l.set(pow);
		}
	}
	
	public void setRightPow(double pow){
		for(SpeedController r: rightMotors){
			r.set(pow);
		}
	}
	
	public void setDrive(double leftPow, double rightPow){
		setLeftPow(leftPow);
		setRightPow(-rightPow);
	}

	public int getRightEnc(){
		return rightEnc.get();
	}
	
	public int getLeftEnc(){
		return leftEnc.get();
	}
	
}
