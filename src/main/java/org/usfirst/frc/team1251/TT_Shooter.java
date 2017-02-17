package org.usfirst.frc.team1251;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Talon;

/**
 * Created by Nicholas Salazar on 2/10/2017.
 */

public class TT_Shooter {

    private static final int SHOOTER_RPMS = 2200;
    private static final int LOOPS_TILL_AGITATION = 40;

    private static int agitationCounter = 0;

    public static void shoot( Joystick joystick, Talon agitator, PIDController shooterPID) {
        if (joystick.getRawButton(1)) {

            shooterPID.setSetpoint(TT_Util.convertRPMsToTicks(SHOOTER_RPMS));
            if (agitationCounter > LOOPS_TILL_AGITATION){
                agitator.set(1);
            } else {

                agitationCounter++;
            }
        } else {

            agitationCounter = 0;
        }
    }
}

