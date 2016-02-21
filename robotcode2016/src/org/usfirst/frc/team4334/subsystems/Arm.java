package org.usfirst.frc.team4334.subsystems;

import org.usfirst.frc.team4334.robot.Ports;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;

public class Arm {
	private static CANTalon motor = new CANTalon(Ports.ARM);
	private static CANTalon motor2 = new CANTalon(Ports.ARM_2);
	private static AnalogInput pot = new AnalogInput(Ports.ARM_POT);
	
	public void setArmPow(double pow){
		if(Math.abs(pow) > IntakeArmConst.ARM_MAX_POW){
			pow = pow > 0 ? IntakeArmConst.ARM_MAX_POW : -IntakeArmConst.ARM_MAX_POW;
		}
		motor2.set(-pow);
		motor.set(pow);
	}
	
	public double getPot(){
		return pot.getVoltage();
	}	
	
}
