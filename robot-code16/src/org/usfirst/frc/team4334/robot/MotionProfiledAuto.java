package org.usfirst.frc.team4334.robot;



import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

import org.usfirst.frc.team4334.drive.DriveBase;
import org.usfirst.frc.team4334.sensors.NavX;
import org.usfirst.frc.team4334.utils.Utils;

public class MotionProfiledAuto {

	static Waypoint[] set1 = new Waypoint[] {
			new Waypoint(0, 0, 0),
			new Waypoint(3.9624, 0, 0),
			new Waypoint(4.9624, 1.0, Math.PI/8.0),
			new Waypoint(6.4008, 2.1336, Math.PI/4.0),
			//new Waypoint(3.9624,0,0),
			//new Waypoint(0, 0, 0) // Waypoint @ x=4, y=0,
														// exit angle=0			
			//new Waypoint(6, 0, 0), // Waypoint @ x=6, y=0, exit angle=0
			
			//new Waypoint(0, 0, 0) // Waypoint @ x=0, y=0, exit angle=0
			
			//Exit angles normally in radians: Convert to degrees with Pathfinder.d2r(*degrees*);
	};
	
	static Waypoint[] set2 = new Waypoint[] {
			new Waypoint(0, 0, 0),
			new Waypoint(3.9624, 0, 0),
			new Waypoint(4.9624, 1.0, Math.PI/8.0),
			new Waypoint(6.4008, 2.1336, Math.PI/4.0),
			//new Waypoint(3.9624,0,0),
			//new Waypoint(0, 0, 0) // Waypoint @ x=4, y=0,
														// exit angle=0			
			//new Waypoint(6, 0, 0), // Waypoint @ x=6, y=0, exit angle=0
			
			//new Waypoint(0, 0, 0) // Waypoint @ x=0, y=0, exit angle=0
			
			//Exit angles normally in radians: Convert to degrees with Pathfinder.d2r(*degrees*);
	};

	public static DriveBase drive;

	public static void test(){
		followTraj(set1);
	
	}
	
	public static void followTraj(Waypoint[] points) {
		Trajectory.Config config = new Trajectory.Config(
				Trajectory.FitMethod.HERMITE_CUBIC,
				Trajectory.Config.SAMPLES_HIGH, 0.05, 3.0, 2.0, 10.0);
		
		
		
		Trajectory trajectory = Pathfinder.generate(points, config);

		TankModifier modifier = new TankModifier(trajectory).modify(0.6);//plz fix

		EncoderFollower left = new EncoderFollower(modifier.getLeftTrajectory());
		EncoderFollower right = new EncoderFollower(modifier.getRightTrajectory());

		left.configureEncoder(drive.getLeftEnc(), 292, 0.2032);//need to adjust for encoder ticks/rotation at worlds
		right.configureEncoder(drive.getRightEnc(), 139, 0.2032);//need to adjust for encoder ticks/rotation at worlds
		double max_velocity = 4.572;
		left.configurePIDVA(1.0, 0.0, 0, 1.0 / max_velocity, 0.05);
		right.configurePIDVA(1.0, 0.0, 0, 1.0 / max_velocity, 0.05);

		double initAngle = NavX.getAngle();
		
		for(int i = 0; i<trajectory.length(); i++){
			double leftPow = left.calculate(drive.getLeftEnc());
			double rightPow = right.calculate(drive.getRightEnc());
			double gyro_heading = Utils.getAngleDifferenceDeg(NavX.getAngle(),initAngle);//NavX.getAngle(); // Assuming the gyro is giving a value in degrees
			double desired_heading = left.getHeading() * (180.0 / Math.PI); // Should
																		// also
			System.out.println("pred heading" + desired_heading + " legit heading " + gyro_heading);// be in
			//System.out.println(left.getHeading() + " " + left.getSegment().position + "   " + left.getSegment().velocity);
																	// degrees

			double angleDifference = Pathfinder
					.boundHalfDegrees(desired_heading - gyro_heading);
			System.out.println("Angle diff : " + angleDifference);
			double turn = 1.8 * (1.0/80.0) * angleDifference;
			leftPow = Utils.clamp(leftPow, 0.9);
			rightPow = Utils.clamp(rightPow, 0.9);
			drive.setLeftPow((leftPow + turn));
			drive.setRightPow(-(rightPow - turn));
			try{Thread.sleep(50);
			} catch(Exception e){
				
			}
		}
	}
}
	