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
    public int driveStraight(double RPM, double distanceInches) {
        if (firstRun) {
            resetPIDs();
            leftPID.enable();
            rightPID.enable();
            leftPID.setSetpoint(TT_Util.convertRPMsToTicks(RPM));
            rightPID.setSetpoint(TT_Util.convertRPMsToTicks(RPM));
            firstRun = false;
        }

        if (((rightEnc.get() * 0.000521716447110 > TT_Util.inchesToMeters(distanceInches) && leftEnc.get() * 0.000521716447110 > TT_Util.inchesToMeters(distanceInches)) && (counter == 0)) || (counter > 0 && counter < 9)) {
            leftPID.disable();
            rightPID.disable();
            driveBase.tankDrive(0.7, 0.7);
            counter++;
        }
        if (counter > 8) {
            driveBase.tankDrive(0, 0);
            firstRun = true;
            counter = 0;
            resetPIDs();
            return 0;
        }
        return 1;
    }

    // return 1 if still driving, return 0 if done
    public int driveBackwards(double RPM, double distanceInches) {
        if (firstRun) {
            resetPIDs();
            leftPID.enable();
            rightPID.enable();
            leftPID.setSetpoint(-TT_Util.convertRPMsToTicks(RPM));
            rightPID.setSetpoint(-TT_Util.convertRPMsToTicks(RPM));
            firstRun = false;
        }

        if (((rightEnc.get() * 0.000521716447110 < -TT_Util.inchesToMeters(distanceInches) && leftEnc.get() * 0.000521716447110 < -TT_Util.inchesToMeters(distanceInches)) && (counter == 0)) || (counter > 0 && counter < 9)) {
            leftPID.disable();
            rightPID.disable();
            driveBase.tankDrive(-0.7, -0.7);
            counter++;
        }
        if (counter > 8) {
            driveBase.tankDrive(0, 0);
            firstRun = true;
            counter = 0;
            resetPIDs();
            return 0;
        }
        return 1;
    }

    public int turnRobot(double randomRPM, double randomInches) {
        if (firstRun) {
            resetPIDs();
            leftPID.enable();
            rightPID.enable();
            leftPID.setSetpoint(-TT_Util.convertRPMsToTicks(randomRPM));
            rightPID.setSetpoint(TT_Util.convertRPMsToTicks(randomRPM));
            firstRun = false;
        }
        if (Math.abs(rightEnc.get()) * 0.000521716447110 > TT_Util.inchesToMeters(randomInches) && Math.abs(leftEnc.get()) * 0.000521716447110 > TT_Util.inchesToMeters(randomInches)) {
            leftPID.disable();
            rightPID.disable();
            driveBase.tankDrive(0, 0);
            firstRun = true;
            resetPIDs();
            return 0;
        }
        return 1;
    }


    public int trackGear() {
        if (firstRun) {
            resetPIDs();
            leftPID.enable();
            rightPID.enable();
            firstRun = false;
        }
        boolean done = TT_GearTracker.INSTANCE.track(TT_GRIP_Communicator.INSTANCE);
        leftPID.setSetpoint(TT_Util.convertRPMsToTicks(TT_GearTracker.INSTANCE.getLeftTurning()));
        rightPID.setSetpoint(TT_Util.convertRPMsToTicks(TT_GearTracker.INSTANCE.getRightTurning()));
        if (done) {
            leftPID.disable();
            rightPID.disable();
            resetPIDs();
            firstRun = true;
            return 0;
        }
        return 1;
    }

    public int forwardTrackGear() {
        if (firstRun) {
            resetPIDs();
            leftPID.enable();
            rightPID.enable();
            firstRun = false;
        }
        int done = TT_GearTracker.INSTANCE.driveForward(TT_GRIP_Communicator.INSTANCE);
        leftPID.setSetpoint(TT_Util.convertRPMsToTicks(TT_GearTracker.INSTANCE.getLeftTurning()));
        rightPID.setSetpoint(TT_Util.convertRPMsToTicks(TT_GearTracker.INSTANCE.getRightTurning()));
        if (done == 0) {
            leftPID.disable();
            rightPID.disable();
            resetPIDs();
            firstRun = true;
        }
        return done;
    }


    public void resetPIDs() {
        leftPID.setSetpoint(0);
        rightPID.setSetpoint(0);
        leftPID.reset();
        rightPID.reset();
        rightEnc.reset();
        leftEnc.reset();
    }

    public int trackPeg(int error) {
        if (firstRun) {
            resetPIDs();
            leftPID.enable();
            rightPID.enable();
            firstRun = false;
        }
        boolean tracked = TT_GearPegTracker.INSTANCE.track(TT_GRIP_Communicator.INSTANCE, error);
        leftPID.setSetpoint(TT_GearPegTracker.INSTANCE.getLeftTurning());
        rightPID.setSetpoint(TT_GearPegTracker.INSTANCE.getRightTurning());
        if (tracked) {
            leftPID.disable();
            rightPID.disable();
            resetPIDs();
            firstRun = true;
            return 0;
        }

        return 1;
    }
}
