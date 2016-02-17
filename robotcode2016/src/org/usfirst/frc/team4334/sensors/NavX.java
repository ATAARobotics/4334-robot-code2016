package org.usfirst.frc.team4334.sensors;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;

public class NavX {

    private AHRS navx;
    
    public void init() {
        navx = new AHRS(SPI.Port.kMXP);
    }
    
    public double getAngle() {
        return navx.getAngle();
    }
    
    public double getYaw() {
        return (double)navx.getYaw();
    }
    
    public double getRoll() {
        return (double)navx.getRoll();
    }
    
    public double getVelocityX() {
        return (double)navx.getVelocityX();
    }
    
    public double getVelocityY() {
        return (double)navx.getVelocityY();
    }
    
    public double getVelocityZ() {
        return (double)navx.getVelocityZ();
    }
    
    public double getAltitude() {
        return (double)navx.getAltitude();
    }
    
}
