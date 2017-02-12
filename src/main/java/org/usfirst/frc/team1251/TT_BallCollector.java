package org.usfirst.frc.team1251;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;

/**
 * Created by Jared on 2/11/2017.
 */
public class TT_BallCollector {
    public static void collectBall(Joystick controller, Talon motor, DoubleSolenoid solenoid) {
        if (controller.getRawButton(6)) {
            motor.set(1.0);
        }
        else if(controller.getRawButton(8)) {
            motor.set(-1.0);
        }
        else {
            motor.set(0);
        }
    }
}
