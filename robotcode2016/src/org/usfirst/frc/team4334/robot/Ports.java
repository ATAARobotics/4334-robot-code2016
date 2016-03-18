package org.usfirst.frc.team4334.robot;

public class Ports {
	public static final int JOYSTICK_1 = 0;
	public static final int JOYSTICK_2 = 1;
	
	public static final int DRIVE_LEFT_1= 1;
	public static final int DRIVE_LEFT_2 = 2;
	public static final int DRIVE_RIGHT_1 = 4; //actual = 3
	public static final int DRIVE_RIGHT_2 = 5; //actual = 4
	
	//Talon SRX 
	public static final int SHOOTER = 0;
	public static final int SHOOTER_2 = 1;
	public static final int ARM = 3;
	public static final int ARM_2 = 2;
	
	public static final int INTAKE = 3;//actual 2; prac = 5
	public static final int EL_TORRO = 6;//actual 5; prac = 4
	
	public static final int EL_TORRO_ULT = 2;//prac3; //actual 3
	public static final int EL_TORRO_ULT_2 = 3;//prac2; //actual 2
	public static final int ARM_POT = 0;
	public static final int ARM_LIMIT_LOW = 8;
	public static final int ARM_LIMIT_HIIGH = 9;

	public static final int ENCODER_RIGHT = 4;
	public static final int ENCODER_RIGHT_2 = 5;
	public static final int ENCODER_LEFT = 6;
	public static final int ENCODER_LEFT_2 = 7;
	
	public static final int HALL_EFFECT = 1;
	public static final int LIGHT_RELAY = 1;
	
	public void changePortsForPrac(){
		//
	}	
}
