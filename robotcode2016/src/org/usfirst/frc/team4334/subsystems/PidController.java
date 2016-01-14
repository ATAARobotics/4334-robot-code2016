package org.usfirst.frc.team4334.subsystems;

import org.usfirst.frc.team4334.utils.*;




public class PidController {
	//our gains, we will define these later
	private double kP;
	private double kI;
	private double kD;
	
	//episilon is the amount of error we are happy with (error no longer integrates when error falls below epsilon)
	private double epsilon;
	private double slewRate;
	private double integralLimit;
	
	private double errorSum;
	private double lastError;
	private long lastTime;
	
	private boolean zeroOnCross;
	
	public PidController(double p, double i, double d, double intLim, boolean zeroOnCross){
		kP = p;
		kI = i;
		kD = d;
		integralLimit = intLim;
		
		
		errorSum = 0;
		lastError = 0;
		//on by default
		zeroOnCross = true;
	}
	
	public void setZeroOnCross(boolean zero){
		zeroOnCross = zero;
	}
	
	public void reset(){
		errorSum = 0;
		lastError = 0;
	}
	
	public double calculate(double error){
		long dTime = System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		
		if(Math.abs(error) > epsilon){
			errorSum += error * dTime;
		} else{
			errorSum = 0;
		}
		
		//limits error sum to +integralLimit or -integralLimit
		Utils.clamp(errorSum, integralLimit);
		
		if(zeroOnCross){
			if(Math.signum(error) != Math.signum(lastError)){
				errorSum = 0;
			}
		}
		
		double changeInError = (error - lastError) / dTime;
		lastError = error;
		
		return kP * error + kI * errorSum + kD * changeInError;
	}
	
}
