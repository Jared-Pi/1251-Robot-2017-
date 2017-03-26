package org.usfirst.frc.team1251;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.RobotDrive;

/**
 * Created by Eric Engelhart on 3/26/2017.
 */
public class TT_DriveUtil {
    public static TT_DriveUtil INSTANCE;
    private PIDController leftPID;
    private PIDController rightPID;
    private ADXRS450_Gyro gryo;
    private Encoder leftEnc;
    private Encoder rightEnc;
    private RobotDrive driveBase;
    private int counter = 0;
    private boolean firstRun = true;

    public TT_DriveUtil(PIDController leftPID, PIDController rightPID, ADXRS450_Gyro gryo, Encoder leftEnc, Encoder rightEnc, RobotDrive driveBase) {
        this.leftPID = leftPID;
        this.rightPID = rightPID;
        this.gryo = gryo;
        this.leftEnc = leftEnc;
        this.rightEnc = rightEnc;
        this.driveBase = driveBase;
        INSTANCE = this;
    }

    // return 1 if still driving, return 0 if done
    public int driveStraight(double RPM, double distanceMeters) {
        if (firstRun) {
            leftPID.enable();
            rightPID.enable();
            rightEnc.reset();
            leftEnc.reset();
            leftPID.setSetpoint(TT_Util.convertRPMsToTicks(RPM));
            rightPID.setSetpoint(TT_Util.convertRPMsToTicks(RPM));
            firstRun = false;
        }

        if (((rightEnc.get() * 0.000521716447110 > distanceMeters && leftEnc.get() * 0.000521716447110 > distanceMeters) && (counter == 0)) || (counter > 0 && counter < 5)) {
            leftPID.disable();
            rightPID.disable();
            driveBase.tankDrive(0.8, 0.8);
            counter++;
        }
        if (counter > 4) {
            driveBase.tankDrive(0, 0);
            firstRun = true;
            counter = 0;
            rightEnc.reset();
            leftEnc.reset();
            return 0;
        }
        return 1;
    }
}
