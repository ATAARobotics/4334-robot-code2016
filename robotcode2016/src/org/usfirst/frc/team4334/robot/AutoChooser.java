package org.usfirst.frc.team4334.robot;

import java.util.TimerTask;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoChooser {

	public static enum AutoMode {
		LOW_BAR_1_BALL, FORWARD_CROSS, CROSS_1_BALL, CHEVAL, PORTICUL;
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
		chooser.addDefault("DRIVE_FORWARD", AutoMode.FORWARD_CROSS);
		chooser.addDefault("DRIVE_FORWARD_1_BALL", AutoMode.CROSS_1_BALL);
		chooser.addDefault("CHEVAL (NOT IMPLEM)", AutoMode.CHEVAL);
		chooser.addDefault("PORTI (NOT IMPLEM)", AutoMode.PORTICUL);
		chooser.addDefault("DO NOTHING ", AutoMode.PORTICUL);

		
		
		positionChooser.addDefault("Position 1", Position.FIRST);
		positionChooser.addDefault("Position 2", Position.SECOND);
		positionChooser.addDefault("Position 3", Position.THIRD);
		positionChooser.addDefault("Position 4", Position.FOURTH);
		positionChooser.addDefault("Position 5", Position.FITFH);
		
	
		
	}
	
	public void putChoosersOnDash(){
		SmartDashboard.putData("Auto Mode Chooser ", chooser);
		SmartDashboard.putData("Auto Positon Chooser ", positionChooser );
	}

	public AutoMode getAutoChoice() {
		return (AutoMode) chooser.getSelected();
	}
	
	public Position getAutoPosition(){
		return (Position) positionChooser.getSelected();
	}

}
