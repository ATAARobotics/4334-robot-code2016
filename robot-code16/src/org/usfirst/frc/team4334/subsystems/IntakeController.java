package org.usfirst.frc.team4334.subsystems;

import org.usfirst.frc.team4334.control.Loopable;

public class IntakeController implements Loopable{

	Intake intake; 
	public IntakeController(Intake in){
		intake = in;
	}
	
	public void shoot(){
		
	}
	
	int ballCount = 0;
	boolean waiting = false;
	long initTime = 0;
	public void update(){
		if(waiting){
			if(initTime + IntakeArmConst.EL_TORRO_INK_TIME_MS > System.currentTimeMillis()){
				intake.stop();
				waiting = false;
				return;
			} else{
				return;
			}
		}
		if(intake.ballReady()){
			ballCount++;
			//intake the next stage for a constant time
			intake.setIntake(1);
			waiting = true;
			initTime = System.currentTimeMillis();
			return;
		}
		if(ballCount >= 1){
			intake.stop();
		}
	}
}
