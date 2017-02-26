package org.usfirst.frc.team1251;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;

import static org.usfirst.frc.team1251.TT_Util.convertTicksToRPMs;

/**
 * Created by Jessa Ecle on 2/11/2017.
 */
public class TT_Hanger {
    public static void hang(Joystick controller, Talon motor, Encoder limit){
        boolean limitReached = false;
        int counter = 0;
        if(!limitReached) {
            if (controller.getRawButton(2)) {
                motor.set(1.0);
                if(counter<1) {
                    counter++;
                }
                else {
                    if (convertTicksToRPMs(limit.getRate()) <  50) {
                        motor.set(0);
                    }
                }
            }
        }
        else {
            motor.set(0);
        }
    }
}
