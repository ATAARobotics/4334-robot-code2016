package org.usfirst.frc.team4334.subsystems;

import org.usfirst.frc.team4334.robot.Ports;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

public class Arm {
	private TalonSRX motor = new TalonSRX(Ports.ARM);
	private AnalogPotentiometer pot = new AnalogPotentiometer(Ports.ARM_POT);
	
	public void setArmPow(double pow){
		if(Math.abs(pow) > IntakeArmConst.ARM_MAX_POW){
			pow = pow > 0 ? IntakeArmConst.ARM_MAX_POW : -IntakeArmConst.ARM_MAX_POW;
		}
		motor.set(pow);
	}
	
	public double getPot(){
		return pot.get();
	}
	
	
	
	
	
	
	
	
	
}
