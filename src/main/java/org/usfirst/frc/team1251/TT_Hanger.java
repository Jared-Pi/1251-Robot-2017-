package org.usfirst.frc.team1251;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;

import static org.usfirst.frc.team1251.Robot.lockControls;

/**
 * Created by Jessa Ecle on 2/11/2017.
 */
public class TT_Hanger {
    public static void hang(Joystick controller, Talon motor, DigitalInput sensor){
        if(!sensor.get()) {
            if (controller.getRawButton(2)) {
                motor.set(1.0);
                lockControls = true;
            } else {
                motor.set(0);
                lockControls = false;
            }
        }
        else{
            motor.set(0);
            lockControls = true;
        }
    }
}
