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

	AutoMode mode;
	Position position;
	private final SendableChooser chooser;
	private final SendableChooser positionChooser;

	public AutoChooser() {
		positionChooser = new SendableChooser();
		chooser = new SendableChooser();
		
		chooser.addDefault("LOW_BAR_ONE_BALL", AutoMode.LOW_BAR_1_BALL);
		chooser.addObject("DRIVE_FORWARD", AutoMode.FORWARD_CROSS);
		chooser.addObject("DRIVE_FORWARD_1_BALL", AutoMode.CROSS_1_BALL);
		chooser.addObject("CHEVAL (NOT IMPLEM)", AutoMode.CHEVAL);
		chooser.addObject("PORTI (NOT IMPLEM)", AutoMode.PORTICUL);
		chooser.addObject("DO NOTHING ", AutoMode.DO_NOTHING);
	
		positionChooser.addDefault("Position 1", Position.FIRST);
		positionChooser.addObject("Position 2", Position.SECOND);
		positionChooser.addObject("Position 3", Position.THIRD);
		positionChooser.addObject("Position 4", Position.FOURTH);
		positionChooser.addObject("Position 5", Position.FITFH);	
	}
	
	public void putChoosersOnDash(){
		SmartDashboard.putData("Auto_Mode_Chooser ", chooser);
		SmartDashboard.putData("Auto_Positon_Chooser ", positionChooser );
	}

	public AutoMode getAutoChoice() {
		return (AutoMode) chooser.getSelected();
	}
	
	public Position getAutoPosition(){
		return (Position) positionChooser.getSelected();
	}

}
