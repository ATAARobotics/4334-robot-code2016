package org.usfirst.frc.team4334.robot;

import java.util.TimerTask;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoChooser extends TimerTask {
    
    private enum AutoMode {
        DEFAULT;
    }
    
    private AutoMode mode;
    
    private final SendableChooser chooser;
    
    public AutoChooser() {
        chooser = new SendableChooser();
        chooser.addDefault("Default", AutoMode.DEFAULT);
        
        SmartDashboard.putData("Auto Choices", chooser);
    }
    
    public void getAutoChoice() {
        mode = (AutoMode) chooser.getSelected();
    }
    
    public void run() {
        switch(mode) {
        case DEFAULT: Auto.runDefault();
        }
    }
}
