package org.usfirst.frc.team4334.robot;

import acilibj.actuators.SuperJoystickModule;
import acilibj.actuators.TankDrivetrain;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot 
{
	Victor L1 = new Victor(RobotMap.left1Port);
	Victor L2 = new Victor(RobotMap.left2Port);
	Victor R1 = new Victor(RobotMap.right1Port);
	Victor R2 = new Victor(RobotMap.right2Port);
	
	Victor shooter = new Victor(RobotMap.shooterMotorPort);
	Victor intake = new Victor(RobotMap.intakePort);
	
    TankDrivetrain drivetrain;
    SuperJoystickModule driver = new SuperJoystickModule(RobotMap.joystick1Port), operator = new SuperJoystickModule(RobotMap.joystick2Port);
    
    public void robotInit() 
    {
    	drivetrain = new TankDrivetrain(L1, L2, R1, R2);
    }

    public void autonomousPeriodic()
    {
    	
    }

    public void teleopPeriodic() 
    {
        drivetrain.getArcade(driver.getAxisWithDeadzone(4, 0.12, false), driver.getAxisWithDeadzone(1, 0.12, true), 1, 0.7);
        
        SmartDashboard.putBoolean("dPad", driver.getDpad(3));
        
        intake.set(operator.getAxisWithDeadzone(3, 0.12, false));
  
        shooter.set(Math.abs(operator.getAxisWithDeadzone(1, 0.12, true)));
    }
   
    public void testPeriodic() 
    {
    
    }
    
    public void disabledPeriodic()
    {
    	
    }
    
}
