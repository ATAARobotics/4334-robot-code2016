package org.usfirst.frc.team4334.robot;

import org.usfirst.frc.team4334.drive.DriveController;
import org.usfirst.frc.team4334.flywheel.FlyConstants;
import org.usfirst.frc.team4334.flywheel.FlywheelController;
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
		// grab modes from dashboard
		AutoChooser.Position pos = Robot.autoChooser.getAutoPosition();
		AutoChooser.AutoMode mode = Robot.autoChooser.getAutoChoice();
		NavX.resetDisplacement();
		
		if (mode == AutoChooser.AutoMode.DO_NOTHING) {

		} else if (pos == AutoChooser.Position.FIRST
				|| mode == AutoChooser.AutoMode.LOW_BAR_1_BALL) {
			lowBar1Ball();
		} else if (mode == AutoChooser.AutoMode.CHEVAL) {
			// crossChav();
		} else if (mode == AutoChooser.AutoMode.FORWARD_CROSS) {
			if (pos == AutoChooser.Position.SECOND) {
				genericCross();
			} else if (pos == AutoChooser.Position.THIRD) {
				genericCross();
			} else if (pos == AutoChooser.Position.FOURTH) {
				genericCross();
			} else if (pos == AutoChooser.Position.FITFH) {
				genericCross();
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

	public void genericCross() {
		try {
			intake.setIntake(-1);
			drive.driveFeet(15, 0.99);
			intake.setIntakTillStop(1);
			drive.driveFeet(-10, 0.99);
			arm.lowerArmASynch();
			drive.driveFeet(-2.5, 0.4);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error during Auto.genericCross()");
		}
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
			drive.driveFeet(12, 0.99);
			fly.setFlySpeedBatter();
			initY += NavX.getDisplacementZ() * 3.28083;
			initX += NavX.getDisplacementX() * 3.28083;

			// calculate distance between
			double dX = shootX - initX;
			double dY = shootY - initY;
			double driveDis = Math.sqrt(initY * initY + initX * initX);
			double turnAng = Math.tan(dX / dY);
	
			drive.turnDegreesAbsolute(initAngle + turnAng);
			arm.lowerArmASynch();
			drive.driveFeet(driveDis);
			drive.turnDegreesAbsolute(initAngle);
			intake.intakeTillShoot();
			drive.driveFeet(2);
		
			//go back through defense we came initially 
			//drive.turnDegreesAbsolute(initAngle + turnAng);
			//drive.driveFeet(-driveDis);
			//drive.driveFeet(-8,0.99);
			// want to get to cords (0,25);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void crossChav() {
		double initAngle = NavX.getAngle();
		try {
			drive.driveFeet(-4, 0.8);
			System.out.println("lowering arm");
			arm.lowerArmTillSwitch();
			drive.driveFeet(-4, 0.8);
		} catch (Exception e) {

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
			arm.lowerArmASynch();
			drive.driveFeet(2, 0.7);
			arm.lowerArmTillSwitch();
			System.out.println("hit auto");
			drive.driveFeet(9, 0.7);
			fly.setFlySpeedBatter();
			drive.turnDegreesAbsolute(initAngle + 20);
			drive.driveFeet(9.1, 0.99);
			drive.turnDegreesAbsolute(initAngle + 60);
			drive.driveFeet(4.5, 0.99);
			intake.setIntake(1);
			Thread.sleep(1000);
			fly.setFlySpeed(0, 0);
			drive.driveFeet(-5, 99);
			drive.turnDegreesAbsolute(initAngle + 180);
		} catch (Exception e) {
			System.out.println("exiting auto ");
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
