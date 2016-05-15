package org.usfirst.frc.team4334.sensors;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;

public class NavX {

    private static AHRS navx = new AHRS(SPI.Port.kMXP);
    
    public static void resetDisplacement(){
    	navx.resetDisplacement();
    }
    
    public static double getAngle() {
        return navx.getAngle();   
    }
    
    
    public static double getDisplacementX(){
    	return navx.getDisplacementX();
    }
    
    public static double getDisplacementY(){
    	return navx.getDisplacementY();
    }
    
    public static double getDisplacementZ(){
    	return navx.getDisplacementY();
    }
    
    public static boolean isCalibrating(){
    	return navx.isCalibrating();
    }
    
    public static void reset(){
    	navx.reset();
    }
    
    public static double getYaw() {
        return (double)navx.getYaw();
    }
    
    public static double getRoll() {
        return (double)navx.getRoll();
    }
    
    public static double getVelocityX() {
        return (double)navx.getVelocityX();
    }
    
    public static double getVelocityY() {
        return (double)navx.getVelocityY();
    }
    
    public static double getVelocityZ() {
        return (double)navx.getVelocityZ();
    }
    
    public static double getAltitude() {
        return (double)navx.getAltitude();
    }
    
}
