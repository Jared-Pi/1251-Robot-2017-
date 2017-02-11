package org.usfirst.frc.team1251;

import edu.wpi.first.wpilibj.*;

/**
 * Created by Nicholas Salazar on 2/10/2017.
 */

public class TT_Shooter {
    static int loopTimer = 0;
    public static void shoot(Encoder shooterController, Joystick joystick, Talon shooter, Talon actuator) {
        if (joystick.getRawButton(1)) {
            shooter.set(1.0);
            if (loopTimer == 5) {
                if (joystick.getRawButton(2)) {
                    actuator.set(1.0);
                } else {
                    actuator.set(0);
                }
            }
        } else {
            shooter.set(0);
            loopTimer++;
        }
    }
}

//I have no idea what I'm doing lol.
