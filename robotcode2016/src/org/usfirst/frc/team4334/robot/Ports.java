package org.usfirst.frc.team4334.robot;

public class Ports {
	public static final int JOYSTICK_1 = 0;
	public static final int JOYSTICK_2 = 1;
	
	public static final int DRIVE_LEFT_1= 1; 
	public static final int DRIVE_LEFT_2 = 3; //compbot = 2
	public static final int DRIVE_RIGHT_1 = 2;  //compbot = 4
	public static final int DRIVE_RIGHT_2 = 0;  //compbot = 5
	
	//Talon SRX 
	public static final int SHOOTER = 0;
	public static final int SHOOTER_2 = 1;
	public static final int ARM = 3;
	public static final int ARM_2 = 2;
	
	public static final int INTAKE = 7; //compbot = 3
	public static final int EL_TORRO = 4; //compbot = 6
	
	public static final int EL_TORRO_ULT = 4; //compbot = 2
	public static final int EL_TORRO_ULT_2 = 5; //compbot = 3
	public static final int ARM_POT = 0; 
	public static final int ARM_LIMIT_LOW = 2; //compbot = 8
	public static final int ARM_LIMIT_HIIGH = 3; //compbot = 9

	public static final int ENCODER_RIGHT = 4;
	public static final int ENCODER_RIGHT_2 = 5;
	public static final int ENCODER_LEFT = 0; //compbot = 6
	public static final int ENCODER_LEFT_2 = 1; //compbot = 7
	
	public static final int HALL_EFFECT = 8; //compbot = 1
	public static final int LIGHT_RELAY = 1;
	
	public void changePortsForPrac(){
		
	}	
}
