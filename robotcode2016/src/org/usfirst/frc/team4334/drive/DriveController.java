package org.usfirst.frc.team4334.drive;

import org.usfirst.frc.team4334.utils.PidController;

import edu.wpi.first.wpilibj.Encoder;

public class DriveController {
	DriveBase drive;
	Encoder leftEnc;
	Encoder rightEnc;
	
	PidController master = new PidController(0.4,0.0,0.0,0.0,true);
	PidController slave = new PidController(0.6, 0 , 0 , 0, true);
	
	
	public static final double TICKS_PER_FEET = 75;
	public static final int THREAD_SLEEP_MS = 20;
	public DriveController(DriveBase dr, Encoder left, Encoder right){
		drive = dr;
		leftEnc = left;
		rightEnc = right;
	}
	
	public void driveFeet(double feet){
		double setPoint = feet * TICKS_PER_FEET;
		boolean atSetpoint = false;
		long initTime = System.currentTimeMillis();
		double errorThresh = 2 * TICKS_PER_FEET;
		
		int satTime = 500;
		
		master.reset();
		slave.reset();
		
		
		while(!atSetpoint){
			double driveErr = setPoint - leftEnc.get();
			double slaveErr = leftEnc.get() - rightEnc.get();			
			double driveOut = master.calculate(driveErr);
			double slaveOut = slave.calculate(slaveErr);
			drive.setDrive(driveErr - slaveOut , driveOut - slaveOut);
			
			if(!(Math.abs(driveErr) < errorThresh)){
				initTime = System.currentTimeMillis();
			}
			else{
				if(initTime - System.currentTimeMillis() > satTime){
					return;
				}
			}
			
			try{
				Thread.sleep(THREAD_SLEEP_MS);
			}
			catch(Exception e){
			
			}
			
		}
		
	}
	
	
	
	public void calc(){
	
	}
	

}
