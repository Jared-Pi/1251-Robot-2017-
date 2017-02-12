package org.usfirst.frc.team1251;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Talon;

/**
 * Created by Nicholas Salazar on 2/10/2017.
 */

public class TT_Shooter {

    private static final int Shooter_RPMs = 2200;
    public static void shoot( Joystick joystick, Talon actuator, PIDController shooterPID) {
        if (joystick.getRawButton(1)) {
            shooterPID.setSetpoint(TT_Util.convertRPMsToTicks(Shooter_RPMs));

        } else {

        }
    }
}

