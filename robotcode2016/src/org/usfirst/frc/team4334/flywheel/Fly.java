package org.usfirst.frc.team4334.flywheel;

import org.usfirst.frc.team4334.robot.Ports;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Counter;

public class Fly {
	private long lastTime;
	private static Counter hallEffect = new Counter(Ports.HALL_EFFECT);
	private double currentRpm;
	private double lastTicks;
	private static CANTalon s1 = new CANTalon(Ports.SHOOTER);
	private static CANTalon s2 = new CANTalon(Ports.SHOOTER_2);
	private int desiredRPM;
	private double holderSpeed;
	
	public Fly() {
		lastTime = System.currentTimeMillis();
		lastTicks = hallEffect.get();
		currentRpm = 0;
	}
	
	public void setFlyPow(double pow) {
		// we don't want to run the flywheel backwards things will explode
		if (pow < 0) {
			pow = 0;
		}
		s1.set(-pow);
		s2.set(-pow);
	}

	public double getRpm() {
		double changeInTime = (System.currentTimeMillis() - lastTime);

		System.out.println("get rpm called");
		if(changeInTime < FlyConstants.RPM_REFRESH_TIME){
			return currentRpm;
		}
		lastTime = System.currentTimeMillis();
		
		// ticks / ms
		double currentTicks = hallEffect.get();
		double rate;

	
		rate = (currentTicks - lastTicks) / changeInTime;

		if(rate <= 0){
			return 0;
		}
		lastTicks = currentTicks;
		// convert to rotations / s
		// add a filter 
			// convert to rpm
		double preCalcRPM = (rate / FlyConstants.TICKS_PER_WHEEL_ROTATION) * 60 * 1000;
		currentRpm = currentRpm * 0.6 + 0.4 *  preCalcRPM;

		holderSpeed = currentRpm;
		return currentRpm;
	}
}
