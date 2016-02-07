package org.usfirst.frc.team4334.sensors;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;

public class ComplementaryFilter {
	AnalogGyro gyro = new AnalogGyro(0);
	
	BuiltInAccelerometer accel = new BuiltInAccelerometer();
	double accelAngle = 0;
	public void filter(){
		double x_acc = accel.getX() * 9.81;
		double y_acc = accel.getY() * 9.81;
		
		accelAngle += x_acc;	
		double angle = 0.98 * gyro.getAngle() + (0.02)*(x_acc);

	}
	
	
}
