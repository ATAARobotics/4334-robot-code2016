package org.usfirst.frc.team4334.robot;

import java.util.LinkedList;

import org.usfirst.frc.team4334.drive.DriveBase;
import org.usfirst.frc.team4334.drive.DriveController;
import org.usfirst.frc.team4334.subsystems.IntakeController;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
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
    	Encoder leftEnc = new Encoder(Ports.ENCODER_LEFT, Ports.ENCODER_LEFT + 1);
    	Encoder rightEnc = new Encoder(Ports.ENCODER_RIGHT, Ports.ENCODER_RIGHT + 1);
    	drive = new DriveBase(left, right);
    	intake = new IntakeController(Ports.INTAKE);
    	
 
     
    	
    }

    AnalogGyro gyro;
    protected void robotInit(){
       	CameraServer usbCamServ = CameraServer.getInstance();
        usbCamServ.setQuality(50);
        usbCamServ.setSize(50);
        usbCamServ.startAutomaticCapture("cam0");   
        
        gyro = new AnalogGyro(0);
        gyro.calibrate();
        
        
    }
    
    
    public void autonomous() {
    	DriveController driveControl = new DriveController(drive,
    			new Encoder(Ports.ENCODER_LEFT, Ports.ENCODER_LEFT + 1)
    			, new Encoder(Ports.ENCODER_RIGHT, Ports.ENCODER_RIGHT + 1));
    	driveControl.driveFeet(5);
    	driveControl.driveFeet(-5);
    	
    }

    public void operatorControl() {

        Joystick joyDrive = new Joystick(Ports.JOYSTICK_1);
        Joystick joyOper = new Joystick(Ports.JOYSTICK_2);
   
        
        double flyPow = 0;
        while (isOperatorControl() && isEnabled()) {
        	//drive.teleopDrive(joyDrive);
        	
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
        		if(flyPow < 1){
        		flyPow += 0.1;
        		}
        		//sketchyFlywheel.set(1);
        		while(joyDrive.getRawButton(3)){
        			
        		}
        	} 
        	else if(joyDrive.getRawButton(4)){
        		if(flyPow > 0){
            		flyPow -= 0.1;
            	}
        		while(joyDrive.getRawButton(4)){
        			
        		}
        		//sketchyFlywheel.set(0.8);
        	} else{
        		//if(Math.abs(joyDrive.getRawAxis(0)) > 0.2){
        		//	sketchyFlywheel.set(Math.abs(joyDrive.getRawAxis(0)));
        		//}
        		//else{
        		//	sketchyFlywheel.set(0);
        		//}
        		//SmartDashboard.putNumber("speed" , sketchyFlywheel.getSpeed());
        		//sketchyFlywheel.set(SmartDashboard.getNumber("power", 0));
        		
        	}
        	sketchyFlywheel.set(flyPow);
        	SmartDashboard.getNumber("test", 0);
        	Timer.delay(0.05);
        }
        
    }

    public void test() {
    	//SmartDashboard.putData("gyro heading",  "" + gyro.getAngle());
    }
}
