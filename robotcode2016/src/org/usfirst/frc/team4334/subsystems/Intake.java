package org.usfirst.frc.team4334.subsystems;

import org.usfirst.frc.team4334.robot.Ports;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.Victor;

public class Intake {
	private static Victor intk = new Victor(Ports.INTAKE);
	private static Victor elToro = new Victor(Ports.EL_TORRO);
	private static Ultrasonic ult = new Ultrasonic(Ports.EL_TORRO_ULT,
			Ports.EL_TORRO_ULT_2);

	public Intake(){
		ult.setAutomaticMode(true);
	}
	
	
	public void setIntake(double pow) {
		elToro.set(pow);
		intk.set(pow);
	}

	public void setIntakTillStop(double pow) {
		System.out.println("checking if ball is ready ");
		if (ballReady()) {
			System.out.println("ball is ready stopping ");
			if (pow < 0) {
				elToro.set(pow);
			} else {
				elToro.set(0);
			}

		} else {
			System.out.println("ball is not ready ");
			elToro.set(pow);
		}
		intk.set(pow);
	}

	public void driveIn() {
		elToro.set(-1);
		intk.set(1);
	}

	public void driveOut() {
		intk.set(1);
	}

	public void stop() {
		intk.set(0);
	}

	public boolean ballReady() {
		System.out.println("ult range " + ult.getRangeInches());
		return (ult.getRangeInches() < IntakeArmConst.INTK_ULT_THRESH);
	}

}
