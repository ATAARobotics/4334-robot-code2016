package org.usfirst.frc.team4334.flywheel;

import org.usfirst.frc.team4334.control.Loopable;


public class FlywheelBangBang implements Loopable {
	private int desiredRPM;
	private Fly fly;
	private double setRpm = 0;
	
	public void setRpm(double rpm){
		setRpm = rpm;
	}
	
	public FlywheelBangBang(Fly in){
		fly = in;
	}

	@Override
	public void update() {
		if(fly.getRpm() < setRpm){
			fly.setFlyPow(1);
		}
		else{
			fly.setFlyPow(0);
		}
	}
	
	
}
