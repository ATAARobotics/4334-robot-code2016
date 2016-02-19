package org.usfirst.frc.team4334.control;

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

	public JoystickController(Arm a, Intake i, FlywheelController fly) {
		arm = a;
		intake = i;
		flyControl = fly;
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
		// need to add ghetto shift on B

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
