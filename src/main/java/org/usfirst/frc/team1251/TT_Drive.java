package org.usfirst.frc.team1251;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

import static org.usfirst.frc.team1251.Util.convertToRPMs;

/**
 * Created by Eric Engelhart on 2/10/2017.
 */
public class TT_Drive {
    private static final DoubleSolenoid.Value HIGH_GEAR = DoubleSolenoid.Value.kForward;
    private static final DoubleSolenoid.Value LOW_GEAR = DoubleSolenoid.Value.kReverse;

    private static final int DRIVE_MULTIPLIER = 1;
    private static final double RPM_LIMIT = 3000;
    private static final int loopsToSwitch = 16;

    public static void drive(Joystick left, Joystick right, RobotDrive base) {
        base.tankDrive(left.getRawAxis(1)*DRIVE_MULTIPLIER, right.getRawAxis(1)*DRIVE_MULTIPLIER);
    }

    public static void shifter(Encoder left, Encoder right, DoubleSolenoid solenoid) {
        double nLeft = convertToRPMs(left.getRate());
        double nRight = convertToRPMs(right.getRate());


        if (true) {
            if (nLeft > RPM_LIMIT && nRight > RPM_LIMIT) {
                solenoid.set(HIGH_GEAR);
            } else {
                solenoid.set(LOW_GEAR);
            }
        }
            else{

            }
    }
}
