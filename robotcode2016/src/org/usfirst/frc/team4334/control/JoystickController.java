package org.usfirst.frc.team4334.control;

import org.usfirst.frc.team4334.drive.DriveBase;
import org.usfirst.frc.team4334.drive.DriveConstants;
import org.usfirst.frc.team4334.drive.TeleopDrive;
import org.usfirst.frc.team4334.flywheel.FlywheelController;
import org.usfirst.frc.team4334.subsystems.Arm;
import org.usfirst.frc.team4334.subsystems.Intake;
import org.usfirst.frc.team4334.utils.Utils;

import edu.wpi.first.wpilibj.Joystick;

public class JoystickController implements Loopable {
	private Joystick driver;
	private Joystick operator;

	private Arm arm;
	private Intake intake;
	private FlywheelController flyControl;
	private DriveBase drive;
	private TeleopDrive teleDrive;
	
	private boolean ghettoShiftFlag;
	private boolean driverPre = false;

	public JoystickController(Arm a, Intake i, FlywheelController fly, DriveBase d) {
		arm = a;
		intake = i;
		flyControl = fly;
		drive = d;
		teleDrive = new TeleopDrive(drive);
	}

	public enum XboxMap {
		// buttons
		A(1), B(2), X(3), Y(4), LB(5), RB(6),
		// triggers
		TL(2), TR(3),
		// sticks
		SLX(0), SLY(1), SRX(4), SRY(5);
		private int value;

		XboxMap(int val) {
			this.value = val;
		}

		public int mappedVal() {
			return value;
		}
	};

	@Override
	public void update() {

		// DRIVER
	    
	    //Toggle ghetto shift mode
	    if(!driverPre){
	        if(driver.getRawButton(XboxMap.B.mappedVal()) && !ghettoShiftFlag) {
	            ghettoShiftFlag = true;
	            driverPre = true;
	        }
	        else if(driver.getRawButton(XboxMap.B.mappedVal()) && ghettoShiftFlag) {
	            ghettoShiftFlag = false;
	            driverPre = true;
	        }
	        else if(!driver.getRawButton(XboxMap.B.mappedVal())) {
	            driverPre = false;
	        }
	    }
	    
	    if(ghettoShiftFlag) {
	        teleDrive.teleopDrive(DriveConstants.JOY_DEADZONE, TeleopDrive.DriveMode.HALO, driver, 0.5);
	    }
	    else if(!ghettoShiftFlag) {
	        teleDrive.teleopDrive(driver);
	    }

		// flywheel
		// Spin-up(x for obstacle, a for batter)
		if (operator.getRawButton(XboxMap.A.mappedVal())) {
			flyControl.setFlySpeedBatter();
		} else if (operator.getRawButton(XboxMap.X.mappedVal())) {
			flyControl.setFlySpeedObj();
		}

		// arm
		// shoot A

		// intake
		if(driver.getRawButton(XboxMap.A.mappedVal())){
			intake.setIntake(1);
		} else{
			intake.setIntake(operator.getRawAxis(XboxMap.SLY.mappedVal()));
		}
		
		double opLeftT = operator.getRawAxis(XboxMap.TL.mappedVal()) - 0.5;
		double opRightT = operator.getRawAxis(XboxMap.TR.mappedVal()) - 0.5;
		opLeftT = Utils.deadzone(opLeftT, JoyConstants.ARM_DEADZONE);
		opRightT = Utils.deadzone(opRightT, JoyConstants.ARM_DEADZONE);
		arm.setArmPow(opLeftT - opRightT);

	}

}
