package org.usfirst.frc.team4334.subsystems;

import org.usfirst.frc.team4334.control.Loopable;
import org.usfirst.frc.team4334.utils.PidController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ArmController implements Loopable {
	static Arm arm = new Arm();

	PidController pid = new PidController(IntakeArmConst.ARM_KP,
			IntakeArmConst.ARM_KI, IntakeArmConst.ARM_KD,
			IntakeArmConst.ARM_INT_LIM, false);

	double setPoint;

	public ArmController() {
		setPoint = arm.getPot(); //Blaze up \//\ 420 420 420 
	}

	public void setUp() {
		enablePID();
		setTarget(IntakeArmConst.ARM_UP);
	}

	public void setDown() {
		enablePID();
		setTarget(IntakeArmConst.ARM_DOWN);
	}

	public double setTarget(double setp) {
		pid.reset();
		return setPoint = setp;
	}

	public boolean isOnTarget() {
		return Math.abs(getError()) < IntakeArmConst.ARM_ERR_THRESH;
	}

	public double getError() {
		return setPoint - arm.getPot();
	}

	boolean pidEnabled = true;

	public void enablePID() {
		pidEnabled = true;
	}

	public void disablePID() {
		pidEnabled = false;
	}

	public void setPow(double outPow) {
		disablePID();
		arm.setArmPow(outPow);
		setPoint = arm.getPot();
	}
	
	public void startPIDHold(){
		setPoint = arm.getPot();
		enablePID();
	}
	
	public void setSetPoint(double increment){
		double nextVal = setPoint + increment ;			
	}
	
	public void holdCurrSetpoint(){
		setPoint = arm.getPot();
	}
	

	@Override
	public void update() {
		pid.sendValuesToDashboard("arm_control_");
		SmartDashboard.putNumber("setpoint", setPoint);
		SmartDashboard.putNumber("arm_pot", arm.getPot());
		if (pidEnabled) {
			double outVal = pid.calculate(getError());
			if(setPoint > arm.getPot()){
				outVal /= 2;
			}
			arm.setArmPow(outVal);
		}
	}
}
