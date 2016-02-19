package org.usfirst.frc.team4334.subsystems;

import edu.wpi.first.wpilibj.Victor;

public class Intake {
private Victor intk;
	
	public Intake(int port){
		intk = new Victor(port);
	}
	
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
