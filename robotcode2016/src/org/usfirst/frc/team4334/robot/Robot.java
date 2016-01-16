package org.usfirst.frc.team4334.robot;

import java.util.LinkedList;

import org.usfirst.frc.team4334.drive.DriveBase;
import org.usfirst.frc.team4334.subsystems.IntakeController;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends SampleRobot {

    Joystick stick;
    DriveBase drive;
	IntakeController intake;
	
	//temporary until we have sensors 
	Victor sketchyFlywheel = new Victor(Ports.SHOOTER);
	
	CameraServer usbCamServ;
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

    
    protected void robotInit(){
       	usbCamServ = CameraServer.getInstance();
        usbCamServ.setQuality(50);
        usbCamServ.setSize(50);
        usbCamServ.startAutomaticCapture("cam0");
    }
    
    
    public void autonomous() {

    }

    public void operatorControl() {

        Joystick joyDrive = new Joystick(Ports.JOYSTICK_1);
        Joystick joyOper = new Joystick(Ports.JOYSTICK_2);
   
        
        SmartDashboard.putString("TEST", "TESTING");
        
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
