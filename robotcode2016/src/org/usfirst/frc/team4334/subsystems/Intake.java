package org.usfirst.frc.team4334.subsystems;

import org.usfirst.frc.team4334.robot.Ports;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.Victor;

public class Intake {
private static Victor intk = new Victor(Ports.INTAKE);
private static Victor elToro = new Victor(Ports.EL_TORRO);
private static Ultrasonic ultra = new Ultrasonic(Ports.EL_TORRO_ULT_1
		, Ports.EL_TORRO_ULT_2);
	
	public void setIntake(double pow){
		intk.set(pow);
	}
	
	public void driveIn(){
		intk.set(-1);
	}
	
	public void driveOut(){
		intk.set(1);
	}
	
	public void stop(){
		intk.set(0);
	}
	
}
