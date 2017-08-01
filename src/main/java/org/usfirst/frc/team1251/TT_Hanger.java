package org.usfirst.frc.team1251;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;

import static org.usfirst.frc.team1251.Robot.*;

/**
 * Created by Jessa Ecle on 2/11/2017.
 */
public class TT_Hanger {
    public static boolean limitReached = false;

    public static void hang(Joystick controller, SpeedController motor, Encoder limit, SpeedController clawMotor){
        if(!limitReached) {

            if (controller.getRawButton(CONTROLLER_SELECT_BUTTON)) {

                motor.set(0.3);
                clawMotor.set(0.35);
            }
            else if (controller.getRawButton(CONTROLLER_START_BUTTON)) {
                motor.set(1.0);
                clawMotor.set(0.35);
            }
            else {
                motor.set(0);
                clawMotor.set(0);
            }
        }
        else {

            motor.set(0);
        }
    }

    public static void hang2(Joystick controller, SpeedController motor, Encoder limit, SpeedController clawMotor) {
        if (!limitReached) {

            if (controller.getRawButton(CONTROLLER_SELECT_BUTTON)) {

                motor.set(0.3);
                clawMotor.set(0.35);
            } else if (controller.getRawButton(CONTROLLER_START_BUTTON)) {
                motor.set(1.0);
                clawMotor.set(0.35);
            } else if (controller.getRawButton(CONTROLLER_Y_BUTTON)) {
                motor.set(0);
                clawMotor.set(0);
            }
        } else {

            motor.set(0);
        }
    }
}
