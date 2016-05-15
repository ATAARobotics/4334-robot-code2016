package org.usfirst.frc.team4334.utils;

import org.usfirst.frc.team4334.utils.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PidController {
	// our gains, we will define these later
	private double kP;
	private double kI;
	private double kD;

	// episilon is the amount of error we are happy with (error no longer
	// integrates when error falls below epsilon)
	private double epsilon;
	private double slewRate;
	private double integralLimit;

	public double errorSum;
	private double lastError;
	private long lastTime;

	private boolean zeroOnCross;

	public PidController(double p, double i, double d, double intLim,
			boolean zeroOnCross) {
		kP = p;
		kI = i;
		kD = d;
		integralLimit = intLim;

		epsilon = 0;
		errorSum = 0;
		lastError = 0;
		// on by default
		zeroOnCross = true;
	}

	
	double maxOut = 1.0;
	double minOut = -1.0;
	public void setMaxMin(double max, double min) {
		maxOut = max;
		minOut = min;
	}

	public void setZeroOnCross(boolean zero) {
		zeroOnCross = zero;
	}

	public void reset() {
		errorSum = 0;
		lastError = 0;
	}

	public double calculate(double error) {
		double dTime = (System.currentTimeMillis() - lastTime) / 10.0;
		// System.out.println("dtime = " + dTime + "  " + errorSum);

		lastTime = System.currentTimeMillis();
		double changeInError = 0;

		// limits error sum to +integralLimit or -integralLimit
		errorSum = Utils.clamp(errorSum, integralLimit);

		if (zeroOnCross) {
			if (Math.signum(error) != Math.signum(lastError)) {
				errorSum = 0;
			}
		}

		if (dTime != 0) {
			changeInError = (error - lastError) / dTime;
		} else {
			changeInError = 0;
		}
		lastError = error;
		double output = kP * error + kD * changeInError;
		if(output < 1.0 && output > -1.0){
			if (Math.abs(error) > epsilon) {
				errorSum += error * dTime;
			} else {
				errorSum = 0;
			}
		}
		output += kI * errorSum;
		return output;
	}

	boolean valuesSent = false;

	public void sendValuesToDashboard(String mod) {
		if (!valuesSent) {
			SmartDashboard.putNumber(mod + "_kP", kP);
			SmartDashboard.putNumber(mod + "_kI", kI);
			SmartDashboard.putNumber(mod + "_kD", kD);
			SmartDashboard.putNumber(mod + "_intlim", integralLimit);
			valuesSent = true;
		}
		kP = SmartDashboard.getNumber(mod + "_kP", kP);
		kI = SmartDashboard.getNumber(mod + "_kI", kI);
		kD = SmartDashboard.getNumber(mod + "_kD", kD);
		integralLimit = SmartDashboard
				.getNumber(mod + "_intlim", integralLimit);
	}

}
