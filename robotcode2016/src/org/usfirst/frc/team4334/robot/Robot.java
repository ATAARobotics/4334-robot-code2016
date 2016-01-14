package org.usfirst.frc.team4334.robot;

import java.util.LinkedList;

import org.usfirst.frc.team4334.drive.DriveBase;
import org.usfirst.frc.team4334.subsystems.IntakeController;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;

public class Robot extends SampleRobot {
    RobotDrive myRobot;
    Joystick stick;
    DriveBase drive;
	IntakeController intake;
	
	//temporary until we have sensors 
	Victor sketchyFlywheel = new Victor(Ports.SHOOTER);
	
    public Robot(){
    	LinkedList<SpeedController> left = new LinkedList<SpeedController>();
    	LinkedList<SpeedController> right = new LinkedList<SpeedController>();
    	left.add(new Victor(Ports.DRIVE_LEFT_1));
    	left.add(new Victor(Ports.DRIVE_LEFT_2));
    	right.add(new Victor(Ports.DRIVE_RIGHT_1));
    	right.add(new Victor(Ports.DRIVE_RIGHT_2));
    	drive = new DriveBase(left,right);
    	intake = new IntakeController(Ports.INTAKE);
    }

    public void autonomous() {

    }

    public void operatorControl() {
        myRobot.setSafetyEnabled(true);
        Joystick joyDrive = new Joystick(Ports.JOYSTICK_1);
        Joystick joyOper = new Joystick(Ports.JOYSTICK_1);
        
        while (isOperatorControl() && isEnabled()) {
        	drive.teleopDrive(joyDrive);
        	if(joyDrive.getRawButton(1)){
        		intake.driveIn();
        	}
        	else if(joyDrive.getRawButton(2)){
        		intake.driveOut();
        	}
        	else{
        		intake.stop();
        	}
        	if(joyDrive.getRawButton(3)){
        		sketchyFlywheel.set(1);
        	} 
        	else if(joyDrive.getRawButton(4)){
        		sketchyFlywheel.set(0.5);
        	} else{
        		sketchyFlywheel.set(0);
        	}
        }
        
    }

    public void test() {
    	
    }
}
