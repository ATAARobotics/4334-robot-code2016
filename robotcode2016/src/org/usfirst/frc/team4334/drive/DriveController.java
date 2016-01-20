package org.usfirst.frc.team4334.drive;

import org.usfirst.frc.team4334.utils.PidController;
import org.usfirst.frc.team4334.utils.Utils;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveController {
	DriveBase drive;
	Encoder leftEnc;
	Encoder rightEnc;
	AnalogGyro gyro;
	
	PidController master = new PidController(0.05,0.0015,0.001,200,true);
	PidController slave = new PidController(0.05, 0, 0, 1000, true);
	PidController turnPid = new PidController(0.05,0,0,200,true);
	
	public void editPidValues(){
		double kP = SmartDashboard.getNumber("master_kP", 0.05);
		double kI = SmartDashboard.getNumber("master_kI", 0.0015);
		double kD = SmartDashboard.getNumber("master_kD", 0.001);

		master = new PidController(kP, kI, kD,200,true);
		
	}
	
	
	
	
	public static final double TICKS_PER_FEET = 75;
	public static final int THREAD_SLEEP_MS = 20;
	public DriveController(DriveBase dr, Encoder left, Encoder right, AnalogGyro g){
		drive = dr;
		leftEnc = left;
		rightEnc = right;
		gyro = g;
	}
	
	public void driveFeet(double feet){
		double setPoint = feet * TICKS_PER_FEET;
		boolean atSetpoint = false;
		long initTime = System.currentTimeMillis();
		double errorThresh = 2 * 0.083333 * TICKS_PER_FEET;
		
		int satTime = 2000;
		
		master.reset();
		slave.reset();
		
		int initLeft = leftEnc.get();
		int initRight = rightEnc.get();
		while(!atSetpoint){
			editPidValues();
			double driveErr = setPoint - (leftEnc.get() - initLeft);
			double slaveErr = (leftEnc.get() - initLeft) - (rightEnc.get() - initRight);			
			double driveOut = master.calculate(driveErr);
			double slaveOut = slave.calculate(slaveErr);
			
			SmartDashboard.putNumber("LEFT", leftEnc.get());
			SmartDashboard.putNumber("RIGHT", rightEnc.get());
			SmartDashboard.putNumber("Err ", driveErr);
			SmartDashboard.putNumber("Slave Err", slaveErr);
			SmartDashboard.putNumber("driveOut" , driveOut);
			SmartDashboard.putNumber("error sum", master.errorSum );
			System.out.println(driveErr);
			
			if(Math.abs(driveOut) > 0.6){
				driveOut = 0.6 * driveOut / Math.abs(driveOut);
			}
			
			if(Math.abs(slaveOut) > 0.6){
				slaveOut = 0.6 * slaveOut / Math.abs(slaveOut);
			}
			
			drive.setDrive(driveOut - slaveOut, driveOut + slaveOut );
			
			if(!(Math.abs(driveErr) < errorThresh)){
				initTime = System.currentTimeMillis();
			}
			else{
				if( System.currentTimeMillis() - initTime > satTime){
					atSetpoint = true;
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
	
	
	public void turnDegreesAbsolute(double degrees){
		double setPoint = degrees % 360;
		boolean atSetpoint = false;
		long initTime = System.currentTimeMillis();
		double errorThresh = 2;
		
		int satTime = 2000;
		
		gyro.reset();
		slave.reset();
		
		int initLeft = leftEnc.get();
		int initRight = rightEnc.get();
		while(!atSetpoint){

			double driveErr = Utils.getAngleDifferenceDeg(setPoint,(gyro.getAngle() % 360));
			double slaveErr = (leftEnc.get() - initLeft) + (rightEnc.get() - initRight);			
			double driveOut = turnPid.calculate(driveErr);
			double slaveOut = slave.calculate(slaveErr);
			
			SmartDashboard.putNumber("LEFT", leftEnc.get());
			SmartDashboard.putNumber("RIGHT", rightEnc.get());
			SmartDashboard.putNumber("Err ", driveErr);
			SmartDashboard.putNumber("Slave Err", slaveErr);
			SmartDashboard.putNumber("driveOut" , driveOut);
			SmartDashboard.putNumber("error sum", master.errorSum );
			System.out.println(driveErr);
			
			if(Math.abs(driveOut) > 0.6){
				driveOut = 0.6 * driveOut / Math.abs(driveOut);
			}
			
			if(Math.abs(slaveOut) > 0.6){
				slaveOut = 0.6 * slaveOut / Math.abs(slaveOut);
			}
			
			slaveOut = 0;
			drive.setDrive(driveOut, -(driveOut) );
			
			if(!(Math.abs(driveErr) < errorThresh)){
				initTime = System.currentTimeMillis();
			}
			else{
				if( System.currentTimeMillis() - initTime > satTime){
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
	
	
	
	
	public void printEncoders(){
		SmartDashboard.putNumber("LEFT", leftEnc.get());
		SmartDashboard.putNumber("RIGHT", rightEnc.get());
	}
	
	public void calc(){
	
	}
	

}
