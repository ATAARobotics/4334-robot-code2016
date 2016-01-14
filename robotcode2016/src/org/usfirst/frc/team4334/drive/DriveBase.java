package org.usfirst.frc.team4334.drive;

import java.util.LinkedList;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import org.usfirst.frc.team4334.utils.*;

public class DriveBase {
	public static final double JOY_DEADZONE = 0.05;
	//normal tank drivebase 
	private LinkedList<SpeedController> rightMotors;
	private LinkedList<SpeedController> leftMotors;
	
	private Counter encLeft;
	private Counter encRight;
	
	public DriveMode currMode = DriveMode.HALO;; 
	public static enum DriveMode {
		HALO, ARCADE
	}
	
	public DriveBase(LinkedList<SpeedController> leftM, LinkedList<SpeedController> rightM,
			Counter leftE, Counter rightE){
		rightMotors = rightM;
		leftMotors = leftM;
		encLeft = leftE;
		encRight = rightE;
	}
	
	public DriveBase(LinkedList<SpeedController> left,
			LinkedList<SpeedController> right) {
		rightMotors = left;
		leftMotors = right;
		encLeft = null;
		encRight = null;
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
		setRightPow(rightPow);
	}

	public void teleopDrive(Joystick a ){
		teleopDrive(a, JOY_DEADZONE);
	}
	
	public void teleopDrive(Joystick a, double deadzone){
		teleopDrive(a, JOY_DEADZONE, this.currMode);
	}
	
	public void teleopDrive(Joystick a, double deadzone, DriveMode desiredMode){
		double x1 = Utils.deadzone(a.getRawAxis(1), JOY_DEADZONE);
		double y1 = Utils.deadzone(a.getRawAxis(2), JOY_DEADZONE);
		double x2 = Utils.deadzone(a.getRawAxis(3), JOY_DEADZONE);
		double y2 = Utils.deadzone(a.getRawAxis(4), JOY_DEADZONE);
		
		//forward on stick b, turn on stick a
		if(this.currMode == DriveMode.HALO){
			//left out = y2 + x1
			//right out = y2 - x1
			this.setDrive(y2 + x1, y2 - x1);
		}
		
		if(this.currMode == DriveMode.ARCADE){
			//drive with joy a only
			this.setDrive(y1 + x1, y1 - x1);
		}	
	}
	
	private double mapToNDeg(double in, int n, double max){
		return (Math.pow(in,n) * in / Math.abs(in)) / Math.pow(Math.abs(max), n-1);
	}
}
