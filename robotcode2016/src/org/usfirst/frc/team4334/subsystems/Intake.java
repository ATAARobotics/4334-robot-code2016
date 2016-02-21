package org.usfirst.frc.team4334.subsystems;

import org.usfirst.frc.team4334.robot.Ports;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Victor;

public class Intake {
private static Victor intk = new Victor(Ports.INTAKE);
private static Victor elToro = new Victor(Ports.EL_TORRO);
//private static DigitalInput pushSwitch = new DigitalInput(Ports.EL_TORRO_PUSH);
	
	public void setIntake(double pow){
		elToro.set(-pow);
		intk.set(pow);
	}
	
	public void driveIn(){
		elToro.set(-1);
		intk.set(1);
	}
	
	public void driveOut(){
		intk.set(1);
	}
	
	public void stop(){
		intk.set(0);
	}
	
	public boolean ballReady(){
		//return ultra.getRangeInches() < IntakeArmConst.INTK_ULT_THRESH;
		return false;
	}
	
	
}
