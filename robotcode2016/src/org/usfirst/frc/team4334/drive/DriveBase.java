package org.usfirst.frc.team4334.drive;

import java.util.LinkedList;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;

public class DriveBase {
	//normal tank drivebase 
	private LinkedList<SpeedController> rightMotors;
	private LinkedList<SpeedController> leftMotors;
	
	private Counter encLeft;
	private Counter encRight;
	
	
	public static final double JOY_DEADZONE = 0.05;
	
	
	public DriveMode currMode; 
	public static enum DriveMode {
		HALO, ARCADE
	}
	
	public DriveBase(LinkedList<SpeedController> leftM, LinkedList<SpeedController> rightM,
			Counter leftE, Counter rightE){
		rightMotors = rightM;
		leftMotors = leftM;
		//default tele op drive mode 
		currMode = DriveMode.HALO;
		
		encLeft = leftE;
		encRight = rightE;
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

	
	public void teleopDrive(Joystick a, Joystick b){
		teleopDrive(a, b, JOY_DEADZONE);
	}
	
	
	public void teleopDrive(Joystick a, Joystick b, double deadzone){
		teleopDrive(a, b, JOY_DEADZONE, this.currMode);
	}
	
	public void teleopDrive(Joystick a, Joystick b, double deadzone, DriveMode desiredMode){
		double x1 = Utils.deadzone(a.getX(), JOY_DEADZONE);
		double x2 = Utils.deadzone(b.getX(), JOY_DEADZONE);
		double y1 = Utils.deadzone(a.getY(), JOY_DEADZONE);
		double y2 = Utils.deadzone(b.getY(), JOY_DEADZONE);
		
		
		
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
	
	private double mapToNDeg(double in, int n){
		return Math.pow(in,n) * in / Math.abs(in);
	}
}
