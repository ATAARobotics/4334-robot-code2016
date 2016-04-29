package org.usfirst.frc.team4334.robot;

import org.usfirst.frc.team4334.drive.DriveController;
import org.usfirst.frc.team4334.flywheel.FlyConstants;
import org.usfirst.frc.team4334.flywheel.FlywheelController;
import org.usfirst.frc.team4334.robot.AutoChooser.TypesForGenericCross;
import org.usfirst.frc.team4334.sensors.NavX;
import org.usfirst.frc.team4334.subsystems.Arm;
import org.usfirst.frc.team4334.subsystems.ArmController;
import org.usfirst.frc.team4334.subsystems.Intake;

public class Auto implements Runnable {

	static DriveController drive;
	static Intake intake;
	static FlywheelController fly;
	static ArmController armContrl;
	static Arm arm;

	public Auto(DriveController dr, Intake intk, FlywheelController fl,
			ArmController a) {
		drive = dr;
		fly = fl;
		intake = intk;
		armContrl = a;
		arm = new Arm();
	}

	// runs handles the selecting of the method we need to run for auto by
	// looking at the smart dashboard settings
	@Override
	public void run() {
		
		//MotionProfiledAuto.drive = DriveController.drive;
		//MotionProfiledAuto.test();
		//try{
		//Thread.sleep(5000000);
		//} catch(Exception e){
			
		//}
		// grab modes from dashboard
		AutoChooser.Position pos = Robot.autoChooser.getAutoPosition();
		AutoChooser.AutoMode mode = Robot.autoChooser.getAutoChoice();
		AutoChooser.TypesForGenericCross obs = Robot.autoChooser.getObstacle();
		NavX.resetDisplacement();
		
		if (mode == AutoChooser.AutoMode.DO_NOTHING) {

		} else if (pos == AutoChooser.Position.FIRST
				|| mode == AutoChooser.AutoMode.LOW_BAR_1_BALL) {
			lowBar1Ball();
		} else if (mode == AutoChooser.AutoMode.CHEVAL) {
			try {
				crossCheval();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else if (mode == AutoChooser.AutoMode.PORTICUL){
			try {
				System.out.println("running test");
				crossPort(0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (mode == AutoChooser.AutoMode.FORWARD_CROSS) {
			if (obs == AutoChooser.TypesForGenericCross.MOAT) {
				genericCross(16);
			} else if (obs == AutoChooser.TypesForGenericCross.ROCK) {
				genericCross(16);
			} else if (obs == AutoChooser.TypesForGenericCross.RAMPARTS) {
				genericCross(16);
			} else if (obs == AutoChooser.TypesForGenericCross.ROUGH) {
				genericCross(16);
			}
		} else if (mode == AutoChooser.AutoMode.CROSS_1_BALL) {
			if (pos == AutoChooser.Position.SECOND) {
				crossAndShoot(2);
			} else if (pos == AutoChooser.Position.THIRD) {
				crossAndShoot(3);
			} else if (pos == AutoChooser.Position.FOURTH) {
				crossAndShoot(4);
			} else if (pos == AutoChooser.Position.FITFH) {
				crossAndShoot(5);
			}
		}
		// drive.arcTurn(1, 90, true);

		// fourthPosition();
		// drive.driveFeet(3,0.3);
		//
		// runAutoLowBarOneBall();
		// TODO Auto-generated method stub

	}
	
	
	public void crossCheval() throws InterruptedException{
		try{
		drive.driveFeet(-4.35, 0.7);
		arm.lowerArmTillSwitch();
		drive.driveFeet(-10, 0.99);
		} catch(Exception e){
			
		}
		//drive.driveFeet(-5,0.99);
	}

	public void genericCross(double driveFeet) {
		try {
			double initAngle = NavX.getAngle();
			fly.setFlySpeedGenericCross();
			drive.driveFeet(driveFeet, 0.99);
			drive.turnDegreesAbsolute(initAngle);
			arm.lowerArmTillSwitch();
			intake.setIntake(1);
			Thread.sleep(1000);
			intake.setIntake(0);
			fly.setFlySpeed(0, 0);
			arm.raiseArmSynch();
			drive.driveFeet(-driveFeet+3, 0.99);
			drive.turnDegreesAbsolute(initAngle);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error during Auto.genericCross()");
		}
	}

	public void crossPort(int pos) throws InterruptedException{
		arm.lowerArmTillSwitch();
		drive.driveFeet(-13,0.7);
		drive.turnDegreesRel(180);
		drive.driveFeet(-11,0.7);
	}
	
	public void test() throws InterruptedException{

//		drive.driveFeet(-5,0.99);
//		drive.driveFeet(5,0.99);
//		drive.driveFeet(-5,0.99);
	}
	
	
	//4 ft 2 in 
	public void crossAndShoot(int pos) {
		double initY = 0;
		double initX = 0;
		if (pos == 2) {
			initX = -7.5; //-96
		} else if (pos == 3) {
			initX = -3.66; //-44
		} else if (pos == 4) {
			initX = 1.3333; //-16 
		} else if (pos == 5) {
			initX = 5.5; //-66
		}

		double shootX = 0;
		double shootY = 16;
		double initAngle = NavX.getAngle();
		try {
			arm.raiseArmTIllSwitch(); 
			fly.setFlySpeedAuto();
			drive.driveFeet(16, 0.90);
			initY += NavX.getDisplacementZ() * 3.28083;
			initX += NavX.getDisplacementX() * 3.28083;

			// calculate distance between
			double dX = shootX - initX;
			double dY = shootY - initY;
			double driveDis = Math.sqrt((dY * dY) + (dX * dX));
			System.out.println("Distance:" + driveDis);
			double turnAngRadians = Math.atan2(dX, dY);     //  original Math.tan(dX / dY);   
			double turnAngDegrees = Math.toDegrees(turnAngRadians);
			System.out.println("Degrees:" + turnAngDegrees);
			drive.turnDegreesAbsolute(initAngle + turnAngDegrees);
			arm.lowerArmSynch();
			drive.driveFeet(driveDis);
			drive.turnDegreesAbsolute(initAngle);
			Thread.sleep(420);// \//\
			intake.setIntake(1);//intake.intakeTillShoot();
			Thread.sleep(1000);
			fly.setFlySpeed(0, 0);
			//drive.driveFeet(2);
		
			//go back through defense we came initially 
			//drive.turnDegreesAbsolute(initAngle + turnAng);
			//drive.driveFeet(-driveDis);
			//drive.driveFeet(-8,0.99);
			// want to get to cords (0,25);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void fourthPosition() {
		try {
			double initAngle = NavX.getAngle();

			drive.driveFeet(15, 0.99);
			drive.turnDegreesAbsolute(initAngle - 90);
			fly.setFlySpeedBatter();
			drive.driveFeet(1, 0.5);
			drive.turnDegreesAbsolute(initAngle);
			drive.driveFeet(5.3, 0.99);
			drive.giveStraightDrivePow(0, 0);

			arm.raiseArmTIllSwitch();

			Thread.sleep(2000);
			arm.lowerArmTillSwitch();

			intake.setIntake(1);

			Thread.sleep(2000);
		} catch (Exception e) {
			System.out.println("exiting auto");
		}
	}

	public void fifthPosition() {
		try {
			double initAngle = NavX.getAngle();
			fly.setFlySpeedBatter();
			drive.driveFeet(22.5, 0.95);
			arm.raiseArmTIllSwitch();

			try {
				Thread.sleep(2000);
			} catch (Exception e) {

			}
			arm.lowerArmTillSwitch();
			drive.turnDegreesAbsolute(initAngle - 60);

			// drive.driveFeet(1,0.5);
			drive.driveFeet(0.3, 0.5);
			intake.setIntake(1);

			Thread.sleep(2000);
		} catch (Exception e) {

		}
	}

	public void secondPosition() {
		try {
			double initAngle = NavX.getAngle();
			drive.driveFeet(12, 0.9);
			drive.turnDegreesAbsolute(180);
			arm.lowerArmTillSwitch();
			intake.setIntake(-1);
			Thread.sleep(1000);
			arm.raiseArmTIllSwitch();
			drive.turnDegreesAbsolute(180);
			drive.driveFeet(-10, 0.9);
			// double displacementX = NavX.getDisplacementX() * 3.28084;
			// double displacementY = NavX.getDisplacementX() * 3.28084;
			// double xNet = 8;
			// double yNet = 32.8;
			// double deltaX = displacementX - xNet;
			// double deltaY = displacementY - yNet;
			// double turnAng = Math.tan(deltaY / deltaX);

		} catch (Exception e) {

		}
	}

	public void lowBar1Ball() {
		try {
			double initAngle = NavX.getAngle();
            fly.setFlySpeedAuto();
			arm.lowerArmSynch();
			//most important 
			System.out.println("hit auto pls");
			//prev 9 
			drive.driveFeet(19.72, 0.8);
			//new
			//drive.driveFeet(18.1, 0.8);
			arm.raiseArmSynch();
			Thread.sleep(420);//    \//\ used to be 100???
			drive.turnDegreesAbsolute(initAngle + 58.6);
			//new
			//drive.turnDegreesAbsolute(initAngle + 10.0);
			arm.raiseArmTIllSwitch();
			//new
			//drive.driveFeet(2.2756, 0.70);
			//drive.turnDegreesAbsolute(initAngle + 58.6);
			arm.lowerArmSynch();
			drive.driveFeet(7.8, 0.70);
			Thread.sleep(500);
			//new
			//drive.driveFeet(6.6870, 0.70);
			intake.setIntake(1);
			Thread.sleep(1000);
			fly.setFlySpeed(0, 0);
			arm.raiseArmSynch();
			drive.driveFeet(-7.2, 0.9);
			arm.raiseArmTIllSwitch();
			drive.turnDegreesAbsolute(initAngle + 180);
			arm.lowerArmSynch();
			drive.driveFeet(15.5, 0.9);
		} catch (Exception e) {
			System.out.println("exiting auto error");
			e.printStackTrace();
			return;
		}

	}

	public void drivePowAndFire() {
		try {
			drive.giveStraightDrivePow(0.5, 0.5);
			Thread.sleep(3000);
			intake.setIntake(1);
			Thread.sleep(2000);
		} catch (Exception e) {

		}
	}

	public void driveAndShoot(int pos) {
		fly.setFlySpeedBatter();
		if (pos == 1) {

		} else if (pos == 2) {

		} else if (pos == 3) {

		} else if (pos == 4) {

		} else if (pos == 5) {
			drive.driveFeet(7);
			drive.driveFeet(5);
		}
		intake.setIntake(1);

	}

}
