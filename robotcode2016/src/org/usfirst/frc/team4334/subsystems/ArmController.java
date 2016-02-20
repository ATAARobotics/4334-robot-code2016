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
	
	public double setTarget(double setp){
		pid.reset();
		return setPoint = setp;
	}

	public boolean isOnTarget(){
		return Math.abs(getError()) < IntakeArmConst.ARM_ERR_THRESH;
	}

	public double getError(){
		return setPoint - arm.getPot();
	}
	
	
	boolean pidEnabled = true;
	public void enablePID(){
		pidEnabled = true;
	}
	
	public void disabledPID(){
		pidEnabled = false;
	}
	
	
	@Override
	public void update() {
		if(pidEnabled){
			double outVal = pid.calculate(getError());
			arm.setArmPow(outVal);
		}
	}	
}
