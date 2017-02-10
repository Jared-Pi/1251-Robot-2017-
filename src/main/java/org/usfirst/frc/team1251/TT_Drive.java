package org.usfirst.frc.team1251;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

/**
 * Created by Eric Engelhart on 2/10/2017.
 */
public class TT_Drive {
    private static final DoubleSolenoid.Value HIGH_GEAR = DoubleSolenoid.Value.kForward;
    private static final DoubleSolenoid.Value LOW_GEAR = DoubleSolenoid.Value.kReverse;

    private static final int DRIVE_MULTIPLIER = 1;


    public static void drive(Joystick left, Joystick right, RobotDrive base){
        base.tankDrive(left.getRawAxis(1)*DRIVE_MULTIPLIER, right.getRawAxis(1)*DRIVE_MULTIPLIER);
    }

    public static void shifter(Encoder left, Encoder right, DoubleSolenoid solenoid) {

    }
}
