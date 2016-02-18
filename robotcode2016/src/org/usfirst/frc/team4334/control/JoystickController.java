package org.usfirst.frc.team4334.control;

import org.usfirst.frc.team4334.flywheel.FlywheelController;
import org.usfirst.frc.team4334.subsystems.Arm;
import org.usfirst.frc.team4334.subsystems.Intake;

import edu.wpi.first.wpilibj.Joystick;

public class JoystickController implements Loopable {
	private Joystick driver;
	private Joystick operator;
	
	private Arm arm;
	private Intake intake;
	private FlywheelController flyControl;
	
	public JoystickController(Arm a, Intake i, FlywheelController fly){
		arm = a;
		intake = i;
		flyControl = fly;
	}
	
	
	
	public enum XboxMap{
		A(1), B(2), X(3), Y(4), LT(5), RT(6);
		private int value;
		XboxMap(int val) {
		      this.value = val;
		   }
		public int mappedVal() {
		      return value;
		}
	};
	
	
	@Override
	public void update() {
		
		
		
		
		//flywheel
		//Spin-up(x for obstacle, a for batter)
		if(operator.getRawButton(XboxMap.A.mappedVal())){
			flyControl.setFlySpeedBatter();
		} 
		else if(operator.getRawButton(XboxMap.X.mappedVal())){
			flyControl.setFlySpeedObj();
		}
		
		//arm
		//shoot A
		
		//intake
		
		
	}
	
	
}
