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
    static int autoCounter = 0;
    static double lSpeed = 0;
    static double rSpeed = 0;

    public static void auto(int autoSelect, RobotDrive baseDrive, DoubleSolenoid baseShifter, SpeedController pivotMotor, DoubleSolenoid claw, Gyro gyro, Encoder lEncoder, Encoder rEncoder) {
        switch(autoSelect){
            case -1:
                baseDrive.tankDrive(0, 0);
                break;

            case 1:

                if (lEncoder.getRate()<1.2) {
                    lSpeed -= 0.01;
                }
                else if (lEncoder.getRate()>1.3) {
                    lSpeed += 0.01;
                }

                if (rEncoder.getRate()<1.2) {
                    rSpeed -= 0.01;
                }
                else if (rEncoder.getRate()>1.3) {
                    rSpeed += 0.01;
                }

                baseShifter.set(DoubleSolenoid.Value.kReverse);
                if (lEncoder.getDistance() <= 4 && lEncoder.getDistance() <= 4) {
                    baseDrive.tankDrive(lSpeed,rSpeed);
                }
                else {
                    baseDrive.tankDrive(0, 0);
                }
                autoCounter++;
                break;
            case 2:
                claw.set(DoubleSolenoid.Value.kReverse);
                break;

        }
    }
}
