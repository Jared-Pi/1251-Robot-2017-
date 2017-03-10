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

    public static void auto(int autoSelect, RobotDrive baseDrive, DoubleSolenoid baseShifter, SpeedController pivotMotor, DoubleSolenoid claw, Gyro gyro, Encoder lEncoder, Encoder rEncoder) {
        switch(autoSelect){
            case -1:
                baseDrive.tankDrive(0, 0);
                break;

            case 1:

                break;
            case 2:
                claw.set(DoubleSolenoid.Value.kReverse);
                break;
            case 3:
                if (gyro.getAngle() < -10.5){
                    baseDrive.tankDrive(-0.4, 0.4);
                } else if (gyro.getAngle() < -1){
                    baseDrive.tankDrive(-0.3, 0.3);
                } else if (gyro.getAngle() > 10.5){
                    baseDrive.tankDrive(0.4, -0.4);
                } else if (gyro.getAngle() > 1){
                    baseDrive.tankDrive(0.3, -0.3);
                } else {
                    baseDrive.tankDrive(0, 0);
                }

        }
    }
}
