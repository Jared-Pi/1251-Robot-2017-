package org.usfirst.frc.team1251;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 * Created by Jared on 3/9/2017.
 */
public class TT_MainAuto {

    private static final double BASELINE_DISTANCE = 2.3;

    public static int currentReturnVal = 1;
    public static int currentMethodNum = 0;

    public static void auto(int autoSelect, RobotDrive baseDrive, DoubleSolenoid baseShifter, SpeedController pivotMotor, DoubleSolenoid claw, Gyro gyro, Encoder lEncoder, Encoder rEncoder) {
        switch(autoSelect){
            case -1:
                baseDrive.tankDrive(0, 0);
                break;

            case 0:
                //Baseline breaking auto
                if (currentReturnVal == 0){
                    currentMethodNum++;
                }
                if (currentMethodNum < 1) {
                    currentReturnVal = TT_Util.driveStraight(baseDrive, baseShifter, lEncoder, rEncoder, BASELINE_DISTANCE);
                } else {
                    baseDrive.tankDrive(0, 0);
                }
                break;

            case 1:
                // left gear auto here
                break;

            case 2:
                //middle gear auto here
                break;

            case 3:
                // right gear auto here
                break;

        }
    }
}
