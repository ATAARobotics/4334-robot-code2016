
package org.usfirst.frc.team4334.robot;


import java.util.LinkedList;

import org.usfirst.frc.team4334.drive.DriveBase;
import org.usfirst.frc.team4334.subsystems.IntakeController;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;


public class Robot2 extends SampleRobot {
    RobotDrive myRobot;
    Joystick stick;
    DriveBase drive;
	IntakeController intake;
	Victor sketchy_flywheel = new Victor(Ports.SHOOTER);
    public void Robot2(){

    	LinkedList<SpeedController> left = new LinkedList<SpeedController>();
    	LinkedList<SpeedController> right = new LinkedList<SpeedController>();
    	left.add(new Victor(Ports.DRIVE_LEFT_1));
    	left.add(new Victor(Ports.DRIVE_LEFT_2));
    	right.add(new Victor(Ports.DRIVE_RIGHT_1));
    	right.add(new Victor(Ports.DRIVE_RIGHT_2));
    	
    	drive = new DriveBase(left,right);
    	intake = new IntakeController(Ports.INTAKE);
    	
    }

    /**
     * Drive left & right motors for 2 seconds then stop
     */
    public void autonomous() {

    }

    /**
     * Runs the motors with arcade steering.
     */
    public void operatorControl() {
        myRobot.setSafetyEnabled(true);
        Joystick joy_drive = new Joystick(Ports.JOYSTICK_1);
        while (isOperatorControl() && isEnabled()) {
        	drive.teleopDrive(joy_drive);
        	if(joy_drive.getRawButton(0)){
        		intake.driveIn();
        	}
        	if(joy_drive.getRawButton(1)){
        		intake.driveOut();
        	}
        	if(joy_drive.getRawButton(3)){
        		sketchy_flywheel.set(1);
        	} else{
        		sketchy_flywheel.set(0);
        	}
        }
    }

    /**
     * Runs during test mode
     */
    public void test() {
    }
}
