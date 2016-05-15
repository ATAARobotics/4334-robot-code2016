package org.usfirst.frc.team4334.subsystems;

public class IntakeArmConst {
	public static double ARM_KP = 1.5;
	public static double ARM_KI = 0.001;
	public static double ARM_KD = 0;
	public static double ARM_INT_LIM = 0.4 / ARM_KI;
	public static double ARM_MAX_POW = 1;
	public static double ARM_ERR_THRESH = 0.2;
	
	public static double INTK_ULT_THRESH = 10;//original 15
	public static double POT_TO_DEG = 1;
	public static int EL_TORRO_INK_TIME_MS = 200;
	
	public static double ARM_UP = 4.375;
	public static double ARM_DOWN = 3.35;
	
	public static double ARM_CHEVAL = 3.35;
	
}
