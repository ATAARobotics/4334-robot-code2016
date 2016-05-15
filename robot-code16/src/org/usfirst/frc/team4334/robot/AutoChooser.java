package org.usfirst.frc.team4334.robot;

import java.util.TimerTask;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoChooser {

	public static enum AutoMode {
		LOW_BAR_1_BALL, FORWARD_CROSS, CROSS_1_BALL, CHEVAL, PORTICUL, DO_NOTHING;
	}

	public static enum Position {
		FIRST, SECOND, THIRD, FOURTH, FITFH;
	}
	
	public static enum TypesForGenericCross{
		ROCK, MOAT, RAMPARTS, ROUGH;
	}

	AutoMode mode;
	Position position;
	TypesForGenericCross obstacle;
	private final SendableChooser chooser;
	private final SendableChooser positionChooser;
	private final SendableChooser obsChooser;

	public AutoChooser() {
		positionChooser = new SendableChooser();
		chooser = new SendableChooser();
		obsChooser = new SendableChooser();
		
		chooser.addDefault("LOW_BAR_ONE_BALL", AutoMode.LOW_BAR_1_BALL);
		chooser.addObject("DRIVE_FORWARD", AutoMode.FORWARD_CROSS);
		chooser.addObject("DRIVE_FORWARD_1_BALL", AutoMode.CROSS_1_BALL);
		chooser.addObject("CHEVAL", AutoMode.CHEVAL);
		chooser.addObject("PORTI", AutoMode.PORTICUL);
		chooser.addObject("DO NOTHING ", AutoMode.DO_NOTHING);
	
		positionChooser.addDefault("Position 1", Position.FIRST);
		positionChooser.addObject("Position 2", Position.SECOND);
		positionChooser.addObject("Position 3", Position.THIRD);
		positionChooser.addObject("Position 4", Position.FOURTH);
		positionChooser.addObject("Position 5", Position.FITFH);	
		
		obsChooser.addObject("Rock Wall", TypesForGenericCross.ROCK);
		obsChooser.addObject("Moat", TypesForGenericCross.MOAT);
		obsChooser.addObject("Ramparts", TypesForGenericCross.RAMPARTS);
		obsChooser.addObject("Rough T", TypesForGenericCross.ROUGH);
	}
	
	public void putChoosersOnDash(){
		SmartDashboard.putData("Auto_Mode_Chooser ", chooser);
		SmartDashboard.putData("Auto_Positon_Chooser ", positionChooser );
		SmartDashboard.putData("Obstacle Chooser", obsChooser);
	}

	public AutoMode getAutoChoice() {
		return (AutoMode) chooser.getSelected();
	}
	
	public Position getAutoPosition(){
		return (Position) positionChooser.getSelected();
	}
	
	public TypesForGenericCross getObstacle(){
		return (TypesForGenericCross) obsChooser.getSelected();
	}

}
