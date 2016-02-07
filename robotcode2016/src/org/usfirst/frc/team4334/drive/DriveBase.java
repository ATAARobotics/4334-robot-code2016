package org.usfirst.frc.team4334.drive;

import java.util.LinkedList;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4334.utils.*;

public class DriveBase extends TimerTask {

	private LinkedList<SpeedController> rightMotors;
	private LinkedList<SpeedController> leftMotors;
	

	public DriveBase(LinkedList<SpeedController> leftM, LinkedList<SpeedController> rightM,
			Counter leftE, Counter rightE){
		rightMotors = rightM;
		leftMotors = leftM;

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

	@Override
	public void run() {

	}
	
	
}
