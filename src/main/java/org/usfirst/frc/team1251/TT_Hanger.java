package org.usfirst.frc.team1251;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;

import static org.usfirst.frc.team1251.Robot.CONTROLLER_START_BUTTON;

/**
 * Created by Jessa Ecle on 2/11/2017.
 */
public class TT_Hanger {
    public static boolean limitReached = false;
    public static int counter = 0;

    public static void hang(Joystick controller, Talon motor, Encoder limit){
        if(!limitReached) {
            if (controller.getRawButton(CONTROLLER_START_BUTTON)) {
                motor.set(-1.0);
                /*if(counter<2) {
                    counter++;
                }
                else {
                    if (convertTicksToRPMsHanger(limit.getRate()) <  100) {
                        limitReached = true;
                    }
                }*/
            }
            else {
                counter =0;
                motor.set(0);
            }
        }
        else {
            motor.set(0);
        }
    }
}
