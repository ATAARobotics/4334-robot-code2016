package org.usfirst.frc.team4334.robot;

import java.util.TimerTask;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoChooser extends TimerTask {
    
    private enum AutoMode {
        DEFAULT,
        ONE_BALL_LOW_BAR;
    }
    
    AutoMode mode;
    
    private final SendableChooser chooser;
    
    public AutoChooser() {
        chooser = new SendableChooser();
        chooser.addDefault("Default", AutoMode.DEFAULT);
        
        SmartDashboard.putData("Auto Choices", chooser);
    }
    
    public void getAutoChoice() {
        mode = (AutoMode)chooser.getSelected();
    }
    
    public void run() {
        switch(mode) {
        case DEFAULT: Auto.runDefault(); break;
        case ONE_BALL_LOW_BAR: Auto.runAutoLowBarOneBall(); break;
        default:break;
        }
    }
}
