package org.usfirst.frc.team4334.subsystems;

import org.usfirst.frc.team4334.control.Loopable;
import org.usfirst.frc.team4334.utils.PidController;

public class ArmController implements Loopable {
	Arm arm = new Arm();
	

	
	PidController pid = new PidController(IntakeArmConst.ARM_KP,
			IntakeArmConst.ARM_KI,
			IntakeArmConst.ARM_KD,
			IntakeArmConst.ARM_INT_LIM,
			false);

	double setPoint;
	
	public ArmController(){
		setPoint = arm.getPot();
	}
	

	public boolean isOnTarget(){
		return Math.abs(getError()) < IntakeArmConst.ARM_ERR_THRESH;
	}
	
	
	public double getError(){
		return setPoint - arm.getPot();
	}
	
	@Override
	public void update() {
		double outVal = pid.calculate(getError());
		arm.setArmPow(outVal);
	}
	
	
	
	
	
}
