package org.usfirst.frc.team4334.subsystems;

import org.usfirst.frc.team4334.utils.PidController;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class FlywheelController implements Runnable {
	
	final int TICKS_PER_WHEEL_ROTATION = 2;
	final int THREAD_TIME = 20;
	final double kP = 0.001;
	final double kI = 0.001;
	final double kD = 0;
	final double intLim = 1000;
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
	
	public double getSpeed(){
		//I've never used this
		//if we're using
		//get rate of encoder ticks (ticks/sec)
		double rate = this.hallEffect.getRate();
		//convert to rotations / s 
		double speed = rate / TICKS_PER_WHEEL_ROTATION;
		//convert to rpm 
		speed = speed * 60;	
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
	@Override
	public void run() {
		SmartDashboard.putNumber("fly_kP", this.kP);
		SmartDashboard.putNumber("fly_kD", this.kD);
		SmartDashboard.putNumber("fly_kI", this.kI);
		SmartDashboard.putNumber("fly_int_lim", this.intLim);
		SmartDashboard.putNumber("fly_speed ", this.getSpeed());
		SmartDashboard.putNumber("fly_error ", this.error);
		while(pid_enabled){
			this.error = this.setPoint - this.getSpeed();
			double output = this.predPow + pid.calculate(this.error);
			if(output > 0){
				output = 0;
			}
			this.setFlyPow(output);
			try {
				Thread.sleep(THREAD_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
