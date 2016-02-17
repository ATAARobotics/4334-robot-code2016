package org.usfirst.frc.team4334.subsystems;

import edu.wpi.first.wpilibj.Victor;

public class IntakeController {
	private Victor intk;
	
	public IntakeController(int port){
		intk = new Victor(port);
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
