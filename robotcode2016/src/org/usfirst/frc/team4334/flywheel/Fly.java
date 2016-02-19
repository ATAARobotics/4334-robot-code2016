package org.usfirst.frc.team4334.flywheel;

import org.usfirst.frc.team4334.robot.Ports;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.TalonSRX;

public class Fly {
	private long lastTime;
	private static Counter hallEffect = new Counter(Ports.HALL_EFFECT);
	private double currentRpm;
	private int lastTicks;
	private static TalonSRX s1 = new TalonSRX(Ports.SHOOTER);
	private static TalonSRX s2 = new TalonSRX(Ports.SHOOTER_2);
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
		s1.set(pow);

	}

	public double getRpm() {
		double changeInTime = (System.currentTimeMillis() - lastTime);
		if(changeInTime < FlyConstants.RPM_REFRESH_TIME){
			return changeInTime;
		}
		// ticks / ms
		int currentTicks = hallEffect.get();
		double rate = (currentTicks - lastTicks) / changeInTime;
		lastTicks = currentTicks;
		// convert to rotations / s
		// add a filter 
		currentRpm = currentRpm * 0.7 + 0.3 * (rate / FlyConstants.TICKS_PER_WHEEL_ROTATION);
		// convert to rpm
		currentRpm = (currentRpm * 60) * 1000;
		System.out.println("fly speed = " + currentRpm);
		holderSpeed = currentRpm;
		return currentRpm;

	}
}
