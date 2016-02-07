package org.usfirst.frc.team4334.robot;

import java.util.LinkedList;

import org.usfirst.frc.team4334.control.MultiLooper;
import org.usfirst.frc.team4334.drive.DriveBase;
import org.usfirst.frc.team4334.drive.DriveController;
import org.usfirst.frc.team4334.drive.TeleopDrive;
import org.usfirst.frc.team4334.subsystems.FlywheelController;
import org.usfirst.frc.team4334.subsystems.IntakeController;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.filters.Filter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	
	//camera
	CameraServer usbCamServ;
	
	//drive 
    DriveBase driveBase;
	TeleopDrive teleopDrive;
	
	//intake
	IntakeController intake;
	
	//fly wheel
	FlywheelController flyControl;

	//joysticks
    Joystick joyDrive = new Joystick(Ports.JOYSTICK_1);
    Joystick joyOper = new Joystick(Ports.JOYSTICK_2);
    
    //
	Victor sketchyFlywheel = new Victor(Ports.SHOOTER);
	
	
	
	
	public static RobotStates gameState = RobotStates.DISABLED;
	
	public static enum RobotStates{
		DISABLED,
		TELEOP,
		AUTO
	}
	
	AnalogGyro gyro;


   	 
	public void robotInit(){
//        usbCamServ.setQuality(50);
//        usbCamServ.setSize(50);
//        usbCamServ.startAutomaticCapture("cam0");   
//        usbCamServ = CameraServer.getInstance();

        
        
        //create our drivebase 
    	LinkedList<SpeedController> left = new LinkedList<SpeedController>();
    	LinkedList<SpeedController> right = new LinkedList<SpeedController>();
    	left.add(new Victor(Ports.DRIVE_LEFT_1));
    	right.add(new Victor(Ports.DRIVE_RIGHT_1));
     	driveBase = new DriveBase(left, right);
     	
     	//create our intake controller 
    	intake = new IntakeController(Ports.INTAKE);
    	gyro = new AnalogGyro(Ports.GYRO);

    	gyro.calibrate();

    	driveControl = new DriveController(driveBase,
    			new Encoder(Ports.ENCODER_LEFT, Ports.ENCODER_LEFT + 1,true,EncodingType.k4X)
    			, new Encoder(Ports.ENCODER_RIGHT, Ports.ENCODER_RIGHT + 1,true,EncodingType.k4X),
    			gyro);
    	//Counter flyCount = new Counter(Ports.HALL_EFFECT);
    	//flyControl = new FlywheelController(flyCount,new Victor(Ports.SHOOTER));
    	
    	teleopDrive = new TeleopDrive(driveBase,joyDrive);
    }
    
    
    public void disabled(){
    	Robot.gameState = RobotStates.DISABLED;
    }
    
    
    public void test(){
    	
    }
    

	Thread autoThread;
    public void autonomousInit(){
    	autoThread = new Thread(new Auto(driveControl));
    	
    }
    
    
    
    
 	DriveController driveControl;
	MultiLooper autoLooper = new MultiLooper("auto ", 200);
    public void autonomousPeriodic() {
    	
    	Image frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_HSL, 0);
    	int session = NIVision.IMAQdxOpenCamera("cam0",
                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        NIVision.IMAQdxConfigureGrab(session);
		NIVision.IMAQdxStartAcquisition(session);   
    	NIVision.Range r1 = new NIVision.Range(150,255);
    	NIVision.Range r2 = new NIVision.Range(67,255);
    	NIVision.Range r3 = new NIVision.Range(49,255);
    	
		boolean True = false;
    	while(!True){
	        NIVision.IMAQdxGrab(session, frame, 1);
	        r1 = new NIVision.Range((int)SmartDashboard.getNumber("r1"), (int)SmartDashboard.getNumber("r1 max"));
	        r2 = new NIVision.Range((int)SmartDashboard.getNumber("r2"), (int)SmartDashboard.getNumber("r2 max"));
	        r3 = new NIVision.Range((int)SmartDashboard.getNumber("r3"), (int)SmartDashboard.getNumber("r3 max"));
	        
	        NIVision.imaqColorThreshold(frame, frame, 255, NIVision.ColorMode.HSV, r1, r2, r3);
	    	CameraServer.getInstance().setImage(frame);
    	}
    	
    	
    	if(isAutonomous() && isEnabled()){
    		gyro.reset();
    		autoThread.start();
    		Robot.gameState = RobotStates.AUTO;

     		
        	while( isAutonomous() && isEnabled() ){
        		if(isDisabled()){
        			Robot.gameState = RobotStates.DISABLED;
        			autoLooper.stop();
        		}
        		Timer.delay(0.02);
        	} 		
    	}
 
    	Robot.gameState = RobotStates.DISABLED;
 		Timer.delay(0.02);
    }

    
    public void disabledPeriodic(){
    	Robot.gameState = RobotStates.DISABLED;
    	while(isDisabled()){
    		Timer.delay(0.02);
    	}
    }

    
    MultiLooper teleLooper = new MultiLooper("tele looper", 0.05);
 	boolean firstRun = true;
    public void teleopInit(){
    	if(firstRun){
    		teleLooper.addLoopable(new TeleopDrive(driveBase, joyDrive));
    	}
    	Robot.gameState = RobotStates.TELEOP;      
    	
    }
    
    
    public void teleopPeriodic() {
//    	double flyPow = 0;
    	if(isOperatorControl() && isEnabled()){
    		teleLooper.start();
    	}
    	while (isOperatorControl() && isEnabled()) {
    		//SmartDashboard.putNumber("left encoder ", driveControl.leftEnc.get());
    		//SmartDashboard.putNumber("right encoder ", driveControl.rightEnc.get());
         	System.out.println("gyro heading " + gyro.getAngle());
    		Timer.delay(0.02);
    	}    	
    	teleLooper.stop();
//   
//        	  
//        	driveControl.printEncoders();
//        	
//        	
//        	if(joyDrive.getRawButton(1)){
//        		intake.driveIn();
//        	}
//        	else if(joyDrive.getRawButton(2)){
//        		intake.driveOut();
//        	}
//        	else{
//        		intake.stop();
//        	}
//        	
//
//        	if(joyDrive.getRawButton(4)){
//        		if(flyPow < 1){
//        			flyPow += 0.1;
//        		}
//        		//sketchyFlywheel.set(1);
//        		while(joyDrive.getRawButton(4)){
//        			
//        		}
//        	} 
//        	else if(joyDrive.getRawButton(3)){
//        		if(flyPow > 0){
//            		flyPow -= 0.1;
//            	}
//        		while(joyDrive.getRawButton(3)){
//        			
//        		}
//        		//sketchyFlywheel.set(0.8);
//        	} else{
//        		//if(Math.abs(joyDrive.getRawAxis(0)) > 0.2){
//        		//	sketchyFlywheel.set(Math.abs(joyDrive.getRawAxis(0)));
//        		//}
//        		//else{
//        		//	sketchyFlywheel.set(0);
//        		//}
//        	
//        		//sketchyFlywheel.set(flyPow);
//        		
//        		//sketchyFlywheel.set(SmartDashboard.getNumber("power", 0));
//        		
//        	}
//        	//SmartDashboard.putNumber("speed" , sketchyFlywheel.getSpeed());
//        	//sketchyFlywheel.set(flyPow);
//      
//        	Timer.delay(0.05);
//        }
//        
    }
}
