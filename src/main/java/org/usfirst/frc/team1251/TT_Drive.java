package org.usfirst.frc.team1251;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

/**
 * Created by Eric Engelhart on 2/10/2017.
 */
public class TT_Drive {
    public static void drive(Joystick left, Joystick right, RobotDrive base){
        base.tankDrive(left.getRawAxis(1), right.getRawAxis(1));
    }

}
