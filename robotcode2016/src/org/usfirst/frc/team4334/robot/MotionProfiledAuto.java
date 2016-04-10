package org.usfirst.frc.team4334.robot;



import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

import org.usfirst.frc.team4334.drive.DriveBase;
import org.usfirst.frc.team4334.sensors.NavX;

public class MotionProfiledAuto {

	static Waypoint[] points = new Waypoint[] {
			new Waypoint(0, -2, 0), // Waypoint @ x=-4, y=-1,
														// exit angle=-45
														// degrees
			new Waypoint(-2, -2, 0), // Waypoint @ x=-2, y=-2, exit angle=0
										// radians
			new Waypoint(0, 0, 0) // Waypoint @ x=0, y=0, exit angle=0 radians
	};

	public static DriveBase drive;

	
	
	public static void test() {
		Trajectory.Config config = new Trajectory.Config(
				Trajectory.FitMethod.HERMITE_CUBIC,
				Trajectory.Config.SAMPLES_HIGH, 0.05, 0.3, 0.1, 10.0);
		
		
		
		Trajectory trajectory = Pathfinder.generate(points, config);

		TankModifier modifier = new TankModifier(trajectory).modify(0.5);

		EncoderFollower left = new EncoderFollower(modifier.getLeftTrajectory());
//		EncoderFollower right = new EncoderFollower(
//				modifier.getRightTrajectory());

		left.configureEncoder(drive.getLeftEnc(), 212, 0.2032);
		//right.configureEncoder(drive.getRightEnc(), 140, 0.2032);
		double max_velocity = 5.0;
		left.configurePIDVA(0, 0.0, 0.0, 1.0 / max_velocity, 0);

		double initAngle = NavX.getAngle();
		while (true) {
			double l = left.calculate(drive.getLeftEnc());
			double gyro_heading = NavX.getAngle() - initAngle; // Assuming the gyro is
													// giving a value in degrees
			double desired_heading = Pathfinder.r2d(left.getHeading()); // Should
																		// also
			System.out.println(left.getHeading() + "  " + gyro_heading);// be in
			System.out.println(left.getHeading() + " " + left.getSegment().position + "   " + left.getSegment().velocity);
																	// degrees

			double angleDifference = Pathfinder
					.boundHalfDegrees(desired_heading - gyro_heading);
			double turn = 0 * (-1.0 / 80.0) * angleDifference;

			drive.setLeftPow(l - turn);
			drive.setRightPow(l + turn);
			try{Thread.sleep(50);
			} catch(Exception e){
				
			}
		}
	}
}
