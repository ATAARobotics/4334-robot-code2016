package acilibj.sensors;

import acilibj.actuators.TankDrivetrain;

/**
 * @authors Jayden Chan, Ayla Chase
 * 
 * Date Created: December 28 2015
 * Last Updated: January 03 2015
 * 
 * @version 0.1 BETA
 * 
 * Class that uses encoder values on a tank drivetrain to return the coordinates and rotational value of the robot. Also works in auto
 * mode to "autopilot" the robot to a desired coordinate.
 * 
 */

public class RoboGPS 
{
	TankDrivetrain drivetrain;
	public double roboPosX, roboPosY, roboRotation;
	
	public RoboGPS(TankDrivetrain drivetrain, double startX, double startY, double startR)
	{
		this.drivetrain = drivetrain;
		this.roboPosX = startX;
		this.roboPosY = startY;
		this.roboRotation = startR;
	}

	public void update(double leftDistance, double rightDistance)
	{ double carrierX = 0, carrierY = 0, carrierR = 0;
	
		roboPosX = roboPosX + carrierX;
		roboPosY = roboPosY + carrierY;
		roboRotation = roboRotation + carrierR;
	}
	
	public void update(double leftDistance, double rightDistance, double gyro)
	{ double carrierX = 0, carrierY = 0, carrierR = 0;
	
		roboPosX = roboPosX + carrierX;
		roboPosY = roboPosY + carrierY;
		roboRotation = roboRotation + carrierR;
	}
	
	public double convertToDistance(double encoderVal, double ticksPerInch)
	{ double inches = 0; double inchesPerTick;
		inchesPerTick = (1/ticksPerInch);
		inches = (encoderVal * inchesPerTick);
		return inches;
	}

}
