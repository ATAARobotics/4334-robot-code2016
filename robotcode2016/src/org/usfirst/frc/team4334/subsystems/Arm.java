package org.usfirst.frc.team4334.subsystems;

import org.usfirst.frc.team4334.robot.Ports;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;

public class Arm {
	private static CANTalon motor = new CANTalon(Ports.ARM);
	private static CANTalon motor2 = new CANTalon(Ports.ARM_2);
	private static DigitalInput limitLow = new DigitalInput(Ports.ARM_LIMIT_LOW);
	private static DigitalInput limitHigh = new DigitalInput(Ports.ARM_LIMIT_HIIGH);
	//private static AnalogInput pot = new AnalogInput(Ports.ARM_POT);
	
	
	public boolean bottomLimPressed(){
		return !limitLow.get();
	}
	
	public boolean topLimPressed(){
		return limitHigh.get();
	}
	
	
	
	public void lowerArmTillSwitch() throws InterruptedException{
		long timLim = 4000;
		long initTime = System.currentTimeMillis();
		while(!limitLow.get() && initTime + timLim > System.currentTimeMillis()){
			setArmPow(-1);
			Thread.sleep(20);
		}
		setArmPow(0);
	}
	
	public static long ARM_TIME_CONST = 4000;
	public void raiseArmTIllSwitch() throws InterruptedException{
		long timLim = 4000;
		long initTime = System.currentTimeMillis();
		while(limitHigh.get() && initTime + timLim > System.currentTimeMillis()){
			setArmPow(1);
			Thread.sleep(20);
		}
		setArmPow(0);
	}
	
	public void lowerArmASynch(){
		Thread thread = new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					lowerArmTillSwitch();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		thread.start();
	}
	
	public void raiseArmASynch(){
		Thread thread = new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					raiseArmTIllSwitch();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		thread.start();
	}
	

	
	public void setArmPow(double pow){
	    
	    if(Math.abs(pow) > IntakeArmConst.ARM_MAX_POW){
            pow = pow > 0 ? IntakeArmConst.ARM_MAX_POW : -IntakeArmConst.ARM_MAX_POW;
        }
	    
	    if(limitLow.get()) {	    
	        if(pow < 0) {
	            pow = 0;
	        }
	    } else if(!limitHigh.get()){
	    	if(pow > 0){
	    		pow = 0;
	    	}
	    }
	    
		if(Math.abs(pow) > IntakeArmConst.ARM_MAX_POW){
			pow = pow > 0 ? IntakeArmConst.ARM_MAX_POW : -IntakeArmConst.ARM_MAX_POW;
		}
		
		motor2.set(-pow);
		motor.set(pow);
	}
	
	public double getPot(){
		return 0; // pot.getVoltage();
	}	
	
}
