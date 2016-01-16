package org.usfirst.frc.team4334.drive;

import java.util.LinkedList;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4334.utils.*;

public class DriveBase {
	public static final double JOY_DEADZONE = 0.15;
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
		setRightPow(-rightPow);
	}

	public void teleopDrive(Joystick a ){
		teleopDrive(a, JOY_DEADZONE);
	}
	
	public void teleopDrive(Joystick a, double deadzone){
		teleopDrive(a, JOY_DEADZONE, this.currMode);
	}
	
	public void teleopDrive(Joystick a, double deadzone, DriveMode desiredMode){
		double x1 = Utils.deadzone(a.getRawAxis(0), JOY_DEADZONE);
		double y1 = -Utils.deadzone(a.getRawAxis(1), JOY_DEADZONE);
		double x2 = Utils.deadzone(a.getRawAxis(4), JOY_DEADZONE);
		double y2 = -Utils.deadzone(a.getRawAxis(5), JOY_DEADZONE);
		SmartDashboard.putString("x1", x1 + "");
		SmartDashboard.putString("x2", x2 + "");
		SmartDashboard.putString("y1", y1 + "");
		SmartDashboard.putString("y2", y2 + "");
		this.currMode = DriveMode.HALO;
		//forward on stick b, turn on stick a
		if(this.currMode == DriveMode.HALO){

				y1 = mapToNDeg(y1,3);
			
	
				x2 = mapToNDeg(x2,3);
			
			//left out = y2 + x1
			//right out = y2 - x1
			this.setDrive((double)y1 - (double)x2, (double)y1 + (double)x2);
		}
		
		if(this.currMode == DriveMode.ARCADE){
			//drive with joy a only
			//sthis.setDrive(y1 + x1, y1 - x1);
		}	
	}
	
	private double mapToNDeg(double in, int n){
		if(in != 0){
			if(n%2 == 0){
				return (Math.pow(in,n) * (in / Math.abs(in)));
			}
			else{
				return Math.pow(in, n);
			}
		}
		return 0;
	}
}
