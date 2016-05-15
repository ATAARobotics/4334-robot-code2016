package org.usfirst.frc.team4334.drive;

import org.usfirst.frc.team4334.robot.Robot;
import org.usfirst.frc.team4334.sensors.NavX;
import org.usfirst.frc.team4334.utils.PidController;
import org.usfirst.frc.team4334.utils.Utils;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveController {
	public static DriveBase drive;

	PidController master = new PidController(DriveConstants.DRIVE_KP,
			DriveConstants.DRIVE_KI, DriveConstants.DRIVE_KD,
			DriveConstants.DRIVE_INT_LIM, true);

	PidController slave = new PidController(DriveConstants.DRIVE_SLAVE_KP,
			DriveConstants.DRIVE_KI, DriveConstants.DRIVE_KD,
			DriveConstants.DRIVE_SLAVE_INT_LIM, true);

	PidController turnPid = new PidController(DriveConstants.TURN_KP,
			DriveConstants.TURN_KI, DriveConstants.TURN_KD,
			DriveConstants.TURN_INT_LIM, true);

	PidController navXStraight = new PidController(DriveConstants.STRAIGHT_KP,
			DriveConstants.STRAIGHT_KI, DriveConstants.DRIVE_KD,
			DriveConstants.DRIVE_INT_LIM, true);

	public DriveController(DriveBase dr) {
		drive = dr;
	}

	public void arcTurn(double radius, double orientation, boolean turnRight) {
		double insideSet = (2 * Math.PI * radius) * (orientation / 360)
				* DriveConstants.TICKS_PER_FEET;
		double outsideSet = (2 * Math.PI * (radius + (DriveConstants.DRIVE_WIDTH / 12)))
				* (orientation / 360) * DriveConstants.TICKS_PER_FEET;
		
		double ratio = outsideSet / insideSet; //how much faster the left side needs to turn 
		
		boolean atSetpoint = false;
		long initTime = System.currentTimeMillis();
		double errorThresh = DriveConstants.DRIVE_STRAIGHT_ERROR_THRESH
				* DriveConstants.TICKS_PER_FEET;
		
		master.reset();
		slave.reset();


		int initLeft = drive.getLeftEnc();
		int initRight = drive.getRightEnc();
		double outsideError;
		double slaveError;
		while (!atSetpoint && notDisabled()) {
			if(turnRight){
				 outsideError = outsideSet - (drive.getLeftEnc() - initLeft);
				 slaveError = (drive.getLeftEnc() - initLeft)
						- (drive.getRightEnc() - initRight) * ratio;
			} else{
				 outsideError = outsideSet - (drive.getRightEnc() - initRight);
				 slaveError = (drive.getRightEnc() - initRight)
						- (drive.getLeftEnc() - initLeft) * ratio;	
			}
			System.out.println("outside error "  + outsideError);
			System.out.println("RATIO : " + ratio);
			double driveOut = master.calculate(outsideError);
			double slaveOut = slave.calculate(slaveError);
			System.out.println("slave output " + slaveOut + " drive out " + driveOut);

			if (Math.abs(driveOut) > DriveConstants.MAX_AUTO_SPEED) {
				driveOut = DriveConstants.MAX_AUTO_SPEED * driveOut
						/ Math.abs(driveOut);
			}

			if (Math.abs(slaveOut) > DriveConstants.MAX_AUTO_SPEED) {
				slaveOut = DriveConstants.MAX_AUTO_SPEED * slaveOut
						/ Math.abs(slaveOut);
			}
		
			if(turnRight){
				drive.setDrive(driveOut + slaveOut, (driveOut / ratio) - slaveOut);
			} else{
				drive.setDrive((driveOut / ratio) - slaveOut, (driveOut) + slaveOut);
			}

			if (!(Math.abs(outsideError) < errorThresh)) {
				initTime = System.currentTimeMillis();
			} else {
				if (System.currentTimeMillis() - initTime > DriveConstants.SATISFY_TIME_MS) {
					atSetpoint = true;
					return;
				}
			}


		}
		stopDrive();
	}

	public void giveStraightDrivePow(double left, double right){
		drive.setDrive(left, right);
	}
	
	public void stopDrive(){
		drive.setDrive(0,0);
	}	

	public void driveFeet(double feet){
		driveFeet(DriveConstants.MAX_AUTO_SPEED);
	}
	
	public void driveFeet(double feet, double maxSpeed) throws InterruptedException{
	    driveFeet(feet,maxSpeed,99999);
	}
	
	public boolean driveAtRest(){
		
		double vel = drive.leftEnc.getRate() * DriveConstants.TICKS_PER_FEET;
		if(Math.abs(vel) < DriveConstants.AUTO_REST_THRESH){
		return true;
		}
		return false;
	}
	
	
	
	public void driveFeet(double feet, double maxSpeed, int expiryMS) throws InterruptedException {
		double setPoint = feet * DriveConstants.TICKS_PER_FEET;
		boolean atSetpoint = false;
		long initTime = System.currentTimeMillis();
		double errorThresh = DriveConstants.DRIVE_STRAIGHT_ERROR_THRESH
				* DriveConstants.TICKS_PER_FEET;

		master.reset();
		slave.reset();
		navXStraight.reset();

	
		
		long killTime = System.currentTimeMillis() + expiryMS;
		double initHeading = NavX.getAngle();

		double lastVal = 0;
		int initLeft = drive.getLeftEnc();
		int initRight = drive.getRightEnc();
		while (!atSetpoint && notDisabled()) {
			master.sendValuesToDashboard("drive_master_");
			navXStraight.sendValuesToDashboard("drive_slave_");
			
			double angError = Utils.getAngleDifferenceDeg(initHeading,
					NavX.getAngle());

			double angOut = navXStraight.calculate(angError);

			double driveErr = setPoint - (drive.getLeftEnc() - initLeft);
			double slaveErr = (drive.getLeftEnc() - initLeft)
					- (drive.getRightEnc() - initRight);
			double driveOut = master.calculate(driveErr);
			double slaveOut = 0;// slave.calculate(slaveErr);

		//	SmartDashboard.putNumber("LEFT", drive.getLeftEnc());
		//	SmartDashboard.putNumber("RIGHT", drive.getRightEnc());
			SmartDashboard.putNumber("drivevel ",  drive.leftEnc.getRate() * DriveConstants.TICKS_PER_FEET);
			SmartDashboard.putNumber("Err ", driveErr);
			SmartDashboard.putNumber("navx Err", angError);
			SmartDashboard.putNumber("navx out", angOut);
			SmartDashboard.putNumber("driveOut", driveOut);
//			SmartDashboard.putNumber("error sum", master.errorSum);

			if (Math.abs(driveOut) > maxSpeed) {
				driveOut = maxSpeed * driveOut
						/ Math.abs(driveOut);
			}

			if (Math.abs(slaveOut) > maxSpeed) {
				slaveOut = maxSpeed * slaveOut
						/ Math.abs(slaveOut);
			} 

			drive.setDrive(driveOut - slaveOut + angOut, driveOut + slaveOut - angOut);
			lastVal = driveOut;
	
			if(System.currentTimeMillis() > killTime){
		         atSetpoint = true;
                 drive.setDrive(0, 0);
                 return;
			}
			if (!(Math.abs(driveErr) < errorThresh)) {
				initTime = System.currentTimeMillis();
		
			} else {
				if(driveAtRest()){
					atSetpoint = true;
					drive.setDrive(0, 0);
					return;
				}
				if (System.currentTimeMillis() - initTime > DriveConstants.SATISFY_TIME_MS) {
					atSetpoint = true;

					drive.setDrive(0, 0);
					return;
				}
			}

		
			Thread.sleep(DriveConstants.THREAD_SLEEP_MS);
		
			

		}
		stopDrive();
	}

	private boolean notDisabled() {
		//System.out.println("checking if disabled " + System.currentTimeMillis());
		//System.out.println(Robot.gameState != Robot.RobotStates.AUTO);
		//System.out.println(Robot.gameState);
		return Robot.gameState == Robot.RobotStates.AUTO;
	}

	//turns the robot by the amount specified using the current navx angle
	public void turnDegreesRel(double degrees) throws InterruptedException {
		// need to add navx here instead
		// System.out.println("setting target " + NavX.getAngle() + degrees);
		turnDegreesAbsolute((NavX.getAngle() + degrees));
	}


	//turns the robot to the given angle according to the reading of the navx
	public void turnDegreesAbsolute(double degrees) throws InterruptedException {
		while (degrees > 360) {
			degrees -= 360;
		}
		while (degrees < 0) {
			degrees += 360;
		}
		double setPoint = degrees;
		boolean atSetpoint = false;
		long initTime = System.currentTimeMillis();

		double errorThresh = DriveConstants.TURN_ERROR_THRESH;
		int satTime = DriveConstants.SATISFY_TIME_TURN;

		turnPid.reset();
		slave.reset();

		int initLeft = drive.getLeftEnc();
		int initRight = drive.getRightEnc();

//		System.out.println(NavX.getAngle());
		while (!atSetpoint && notDisabled()) {
			turnPid.sendValuesToDashboard("turn");
			double driveErr = Utils.getAngleDifferenceDeg(setPoint,
					NavX.getAngle());
//			System.out.println(degrees + " turn err " + driveErr + "   set "
//					+ setPoint + " actual " + NavX.getAngle());
			double slaveErr = (drive.getLeftEnc() - initLeft)
					+ (drive.getRightEnc() - initRight);
			double driveOut = turnPid.calculate(driveErr);
			double slaveOut = 0;//slave.calculate(slaveErr);

			if (Math.abs(driveOut) > DriveConstants.MAX_AUTO_TURN_SPEED) {
				driveOut = DriveConstants.MAX_AUTO_TURN_SPEED * driveOut
						/ Math.abs(driveOut);
			}

			if (Math.abs(slaveOut) > DriveConstants.MAX_AUTO_TURN_SPEED) {
				slaveOut = DriveConstants.MAX_AUTO_TURN_SPEED * slaveOut
						/ Math.abs(slaveOut);
			}

		
			drive.setDrive(driveOut - slaveOut, -(driveOut + slaveOut));

			if (!(Math.abs(driveErr) < errorThresh)) {
				initTime = System.currentTimeMillis();
			} else {
				if(driveAtRest()){
					atSetpoint = true;
					drive.setDrive(0, 0);
					return;
				}
				if (System.currentTimeMillis() - initTime > satTime) {
					return;
				}
			}
			Thread.sleep(DriveConstants.THREAD_SLEEP_MS);		
		}
		drive.setDrive(0, 0);
	}

	PidController MPF = new PidController(0.1, 0, 0, 0, true);
	double MAX_ACCEL = 1;
	double MAX_VEL = 3;
	double kV = 0;
	double kA = 0;
	public void driveInchesMPF(double feet){
		try{
		double dT = 0.02; //repeat at 20 ms 
		double t_acc = MAX_VEL / MAX_ACCEL, t_dec = t_acc; 
		double t_max = (feet / MAX_VEL) - t_acc/2 - t_dec / 2;
		//start 0 - t_acc 
		double targetVel = 0;
		double targetAcc = 0;

		//stage 1: accel at full
		double accCycles = t_acc * 1000 / 20.0;
		targetAcc = MAX_ACCEL;
		for(int i = 0; i<accCycles; i++){
			targetVel += (MAX_ACCEL) * dT;
			Thread.sleep((long) dT * 1000);
		}
		//stage 2: no accel, max velocity
		targetVel = MAX_VEL;
		targetAcc = 0;
		double maxCycles = t_max * 1000 / 20.0;
		for(int i = 0; i<maxCycles; i++){
			Thread.sleep((long) dT * 1000);
		}

		//stage 3 negative accel
		targetAcc = -MAX_ACCEL;
		double decCycles = t_max * 1000 / 20.0;
		for(int i = 0; i<decCycles; i++){
			targetVel -= MAX_ACCEL;
			Thread.sleep((long) dT * 1000);
		}
		
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public void printEncoders() {
		SmartDashboard.putNumber("LEFT", drive.getLeftEnc());
		SmartDashboard.putNumber("RIGHT", drive.getRightEnc());
	}
}
