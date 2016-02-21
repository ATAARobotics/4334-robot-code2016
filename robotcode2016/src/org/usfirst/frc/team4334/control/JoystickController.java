package org.usfirst.frc.team4334.control;

import org.usfirst.frc.team4334.drive.DriveBase;
import org.usfirst.frc.team4334.drive.TeleopDrive;
import org.usfirst.frc.team4334.flywheel.FlywheelController;
import org.usfirst.frc.team4334.robot.Ports;
import org.usfirst.frc.team4334.subsystems.Arm;
import org.usfirst.frc.team4334.subsystems.ArmController;
import org.usfirst.frc.team4334.subsystems.Intake;
import org.usfirst.frc.team4334.utils.Utils;

import edu.wpi.first.wpilibj.Joystick;

public class JoystickController implements Loopable {
	private static Joystick driver = new Joystick(Ports.JOYSTICK_1);
	private static Joystick operator = new Joystick(Ports.JOYSTICK_2);
	
	private Intake intake;
	private FlywheelController flyControl;
	private DriveBase drive;
	private TeleopDrive teleDrive;
	private ArmController armControl;
	private boolean ghettoShiftFlag = false;
	private boolean driverPre = false;

	public JoystickController(Intake i, FlywheelController fly, DriveBase d,
			ArmController armContrl) {
		System.out.println("joystick constructor called");
		intake = i;
		flyControl = fly;
		drive = d;
		teleDrive = new TeleopDrive(drive);
		armControl = armContrl;
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
		System.out.println("update joy");
		// DRIVER

		// Toggle ghetto shift mode
		if (!driverPre) {
			if (driver.getRawButton(XboxMap.B.mappedVal()) 
					&& !ghettoShiftFlag) {
				ghettoShiftFlag = true;
				driverPre = true;
			} else if (driver.getRawButton(XboxMap.B.mappedVal())
					&& ghettoShiftFlag) {
				ghettoShiftFlag = false;
				driverPre = true;
			}
		}
		if (!driver.getRawButton(XboxMap.B.mappedVal())) {
			driverPre = false;
		}

		if (ghettoShiftFlag) {
			teleDrive.teleopDrive(driver, 0.5);
		} else {
			teleDrive.teleopDrive(driver);
		}

		// flywheel
		// Spin-up(x for obstacle, a for batter)
		if (operator.getRawButton(XboxMap.A.mappedVal())) {
			flyControl.setFlySpeedBatter();
		} else if (operator.getRawButton(XboxMap.X.mappedVal())) {
			flyControl.setFlySpeedObj();
		}

		if (operator.getRawButton(XboxMap.RB.mappedVal())) {
			armControl.setUp();
		} else if (operator.getRawButton(XboxMap.LB.mappedVal())) {
			armControl.setDown();
		} else {
			double opLeftT = operator.getRawAxis(XboxMap.TL.mappedVal());
			double opRightT = operator.getRawAxis(XboxMap.TR.mappedVal());
			
			opLeftT = Utils.deadzone(opLeftT, JoyConstants.ARM_DEADZONE);
			opRightT = Utils.deadzone(opRightT, JoyConstants.ARM_DEADZONE);
			//armControl.setPow(opLeftT - opRightT);
		}

		// arm
		// shoot A

		// intake
		if (driver.getRawButton(XboxMap.A.mappedVal())) {
			intake.setIntake(1);
		} else {
			intake.setIntake(operator.getRawAxis(XboxMap.SLY.mappedVal()));
		}

	}
}
