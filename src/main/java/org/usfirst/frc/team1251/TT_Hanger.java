package org.usfirst.frc.team1251;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;

import static org.usfirst.frc.team1251.Robot.CONTROLLER_START_BUTTON;
import static org.usfirst.frc.team1251.Robot.CONTROLLER_Y_BUTTON;

/**
 * Created by Jessa Ecle on 2/11/2017.
 */
public class TT_Hanger {
    public static boolean limitReached = false;
    public static int counter = 0;

    public static final int COUNTER_MAX = 15;

    public static void hang(Joystick controller, SpeedController motor, Encoder limit){
        if(!limitReached) {

            if (controller.getRawButton(CONTROLLER_START_BUTTON)) {

                motor.set(-0.3);
                /*if(counter < COUNTER_MAX) {

                    counter++;
                }
                else if (convertTicksToRPMsHanger(limit.getRate()) <  100) {

                    limitReached = true;
                }*/
            }
            else if (controller.getRawButton(CONTROLLER_Y_BUTTON)) {
                motor.set(-1.0);
            }
            else {

                counter = 0;
                motor.set(0);
            }
        }
        else {

            motor.set(0);
        }
    }
}
