package org.usfirst.frc.team4334.subsystems;

import java.util.TimerTask;

import org.usfirst.frc.team4334.utils.PidController;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class FlywheelController  {
	
	final int TICKS_PER_WHEEL_ROTATION = 1;
	final int THREAD_TIME = 100;
	final double kP = 0.05;
	final double kI = 0.01;
	final double kD = 0;
	final double intLim = 10000;
	PidController pid;
	
	private Counter hallEffect;
	private SpeedController s1;
	private double setPoint;
	private double predPow;
	private double error;
	//assuming encoder ticks once per degree and is mounted at flywheel 
	//(probably will not be the case)
	
	
	public FlywheelController(Counter hallE, SpeedController speedcntrl){
		hallEffect = hallE;
		s1 = speedcntrl;
		pid = new PidController(kP, kI, kD, intLim, false);
	}
	
	//returns the rpm of the flywheel
	
	
	double lastTime = System.currentTimeMillis();
	double lastTicks = 0;
	public double getSpeed(){
		//I've never used this
		//if we're using
		//get rate of encoder ticks (ticks/sec)

		double changeInTime = (System.currentTimeMillis() - lastTime);
		lastTime = System.currentTimeMillis();
		//ticks / ms
		double rate = (this.hallEffect.get() - lastTicks) / changeInTime;
		
		lastTicks = hallEffect.get();
		//convert to rotations / s 
		double speed = rate / TICKS_PER_WHEEL_ROTATION;
		//convert to rpm 
		speed = (speed * 60) * 1000;	
		System.out.println("fly speed = " + speed);
		return speed;
	}
	
	
	private void setFlyPow(double pow){
		//we don't want to run the flywheel backwards things will explode
		if(pow < 0){
			pow = 0; 
		}
		s1.set(pow);
	}
	
	public void setFlySpeed(double rpm){
		this.setPoint = rpm;
		this.predPow = 0;
		pid.reset();
	}
	
	
	public void setFlySpeed(double rpm, double pred){
		this.setPoint = rpm;
		this.predPow = pred;
		pid.reset();
	}

	
	private boolean pid_enabled = true;
	

	public void calculate() {
			pid.sendValuesToDashboard("flywheel_val");
			System.out.println(this.error);
			this.error = this.setPoint - this.getSpeed();
			double output = this.predPow + pid.calculate(this.error);
			//System.out.println(output);
			if(output < 0){
				output = 0;
			}
			this.setFlyPow(output);
			SmartDashboard.putNumber("fly_error ", this.error);
			SmartDashboard.putNumber("fly_out", output);
			SmartDashboard.putNumber("set_rpm", this.setPoint);
			try {
				Thread.sleep(THREAD_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
	
}
