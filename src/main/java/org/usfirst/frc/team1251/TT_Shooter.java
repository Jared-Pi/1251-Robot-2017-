package org.usfirst.frc.team1251;

import edu.wpi.first.wpilibj.*;

/**
 * Created by Nicholas Salazar on 2/10/2017.
 */

public class TT_Shooter {
    public static void shoot(Encoder shooterController, Joystick joystick, Talon shooter, Talon actuator) {
        boolean ifPressed = joystick.getRawButton(1);
        boolean ifPressedActuator = joystick.getRawButton(2);
        int loopTimer = 0;
        if (loopTimer == 2.0){
            if (ifPressed) {
                shooter.set(1.0);
                if (shooterController.get() == 5) {
                    actuator.set(1.0);
                }
            }
        }

    }
}

//I have no idea what I'm doing lol.
