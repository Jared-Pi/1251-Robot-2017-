package org.usfirst.frc.team1251;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

import static java.lang.Math.abs;
import static org.usfirst.frc.team1251.Robot.*;

/**
 * Created by Eric Engelhart on 2/10/2017.
 */
public class TT_Drive {
    private static final DoubleSolenoid.Value HIGH_GEAR = DoubleSolenoid.Value.kForward;
    private static final DoubleSolenoid.Value LOW_GEAR = DoubleSolenoid.Value.kReverse;

    private static final int DRIVE_MULTIPLIER = 1;
    private static final double RPM_LIMIT = 3000.0;
    private static final int LOOPS_TO_SWITCH = 16;

    private static int lowGearLoopTimer = 0;
    private static int highGearLoopTimer = 0;

    private static final double DRIVE_MINIMUM = 0.15;
    private static double lAxis = 0;
    private static double rAxis = 0;

    public static void drive(Joystick controller, RobotDrive base) {

        if(controller.getRawAxis(CONTROLLER_LEFT_AXIS)*abs(controller.getRawAxis(CONTROLLER_LEFT_AXIS))<DRIVE_MINIMUM) {
            lAxis = controller.getRawAxis(CONTROLLER_LEFT_AXIS);
        }
        else {
            lAxis = controller.getRawAxis(CONTROLLER_LEFT_AXIS)*abs(controller.getRawAxis(CONTROLLER_LEFT_AXIS));
        }
        if(controller.getRawAxis(CONTROLLER_RIGHT_AXIS)*abs(controller.getRawAxis(CONTROLLER_RIGHT_AXIS))<DRIVE_MINIMUM) {
            rAxis = controller.getRawAxis(CONTROLLER_RIGHT_AXIS);
        }
        else {
            rAxis = controller.getRawAxis(CONTROLLER_RIGHT_AXIS)*abs(controller.getRawAxis(CONTROLLER_RIGHT_AXIS));
        }

        base.tankDrive(lAxis * DRIVE_MULTIPLIER, rAxis * DRIVE_MULTIPLIER);

        if (controller.getRawButton(CONTROLLER_RIGHT_TRIGGER)){
            Robot.driveBaseShifter.set(HIGH_GEAR);
        }
        else if (controller.getRawButton(CONTROLLER_LEFT_TRIGGER)){
            Robot.driveBaseShifter.set(LOW_GEAR);
        }
    }

    public static void shifter(Encoder left, Encoder right, DoubleSolenoid solenoid) {
        double nLeft = TT_Util.convertTicksToRPMs(left.getRate());
        double nRight = TT_Util.convertTicksToRPMs(right.getRate());

        if (solenoid.get() == LOW_GEAR) {

            if (nLeft > RPM_LIMIT && nRight > RPM_LIMIT) {

                lowGearLoopTimer = 0;
                if (highGearLoopTimer > LOOPS_TO_SWITCH) {

                    solenoid.set(HIGH_GEAR);
                } else {

                    highGearLoopTimer++;
                }
            } else {

                highGearLoopTimer = 0;
            }
        }else if (solenoid.get() == HIGH_GEAR){

            if (nLeft < RPM_LIMIT && nRight < RPM_LIMIT){

                highGearLoopTimer = 0;
                if (lowGearLoopTimer > LOOPS_TO_SWITCH){

                    solenoid.set(LOW_GEAR);
                } else {

                    lowGearLoopTimer++;
                }
            } else {

                lowGearLoopTimer = 0;
            }
        }
    }
}
