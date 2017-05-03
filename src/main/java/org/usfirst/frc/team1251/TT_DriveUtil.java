package org.usfirst.frc.team1251;

import edu.wpi.first.wpilibj.*;

/**
 * Created by Eric Engelhart on 3/26/2017.
 */
public class TT_DriveUtil {
    private static final double metersPerTick = 0.000521716447110;
    public static TT_DriveUtil INSTANCE;
    public static boolean firstRun = true;
    // return 1 if still driving, return 0 if done
    boolean next;
    private PIDController leftPID;
    private PIDController rightPID;
    private ADXRS450_Gyro gryo;
    private Encoder leftEnc;
    private Encoder rightEnc;
    private RobotDrive driveBase;
    private int counter = 0;

    public TT_DriveUtil(PIDController leftPID, PIDController rightPID, ADXRS450_Gyro gryo, Encoder leftEnc, Encoder rightEnc, RobotDrive driveBase) {
        this.leftPID = leftPID;
        this.rightPID = rightPID;
        this.gryo = gryo;
        this.leftEnc = leftEnc;
        this.rightEnc = rightEnc;
        this.driveBase = driveBase;
        INSTANCE = this;
    }

    public int driveStraight(double RPM, double distanceInches) {
        if (firstRun) {
            resetPIDs();
            leftPID.enable();
            rightPID.enable();
            leftPID.setSetpoint(TT_Util.convertRPMsToTicks(RPM));
            rightPID.setSetpoint(TT_Util.convertRPMsToTicks(RPM));
            firstRun = false;
            System.out.println("forwards1");
        }

        if ((rightEnc.get() * metersPerTick > TT_Util.inchesToMeters(distanceInches) && leftEnc.get() * metersPerTick > TT_Util.inchesToMeters(distanceInches))) {
            next = true;
            leftPID.disable();
            rightPID.disable();
        }
        if (next && counter < 9) {
            System.out.println("forwards2");
            driveBase.tankDrive(0.7, 0.7);
            counter++;
        }
        if (counter > 8) {
            driveBase.tankDrive(0, 0);
            firstRun = true;
            next = false;
            counter = 0;
            resetPIDs();
            System.out.println("forwards");
            return 0;
        }
        return 1;
    }

    // return 1 if still driving, return 0 if done
    public int driveStraightAndCoast(double RPM, double distanceInches) {
        if (firstRun) {
            resetPIDs();
            leftPID.enable();
            rightPID.enable();
            leftPID.setSetpoint(TT_Util.convertRPMsToTicks(RPM));
            rightPID.setSetpoint(TT_Util.convertRPMsToTicks(RPM));
            firstRun = false;
        }

        if (rightEnc.get() * metersPerTick > TT_Util.inchesToMeters(distanceInches) && leftEnc.get() * metersPerTick > TT_Util.inchesToMeters(distanceInches)) {
            leftPID.disable();
            rightPID.disable();
            firstRun = true;
            counter = 0;
            resetPIDs();
            return 0;
        }
        return 1;
    }

    public int forwardsTurn(double lRPM, double rRPM, double distanceInches) {
        if (firstRun) {
            resetPIDs();
            leftPID.enable();
            rightPID.enable();
            leftPID.setSetpoint(TT_Util.convertRPMsToTicks(lRPM));
            rightPID.setSetpoint(TT_Util.convertRPMsToTicks(rRPM));
            firstRun = false;
        }

        if (rightEnc.get() * metersPerTick > TT_Util.inchesToMeters(distanceInches) || leftEnc.get() * metersPerTick > TT_Util.inchesToMeters(distanceInches)) {
            leftPID.disable();
            rightPID.disable();
            resetPIDs();
            firstRun = true;
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

        if (rightEnc.get() * metersPerTick < -TT_Util.inchesToMeters(distanceInches) && leftEnc.get() * metersPerTick < -TT_Util.inchesToMeters(distanceInches)) {
            leftPID.disable();
            rightPID.disable();
            next = true;
        }
        if (next && counter < 9) {
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
        if (Math.abs(rightEnc.get()) * metersPerTick > TT_Util.inchesToMeters(randomInches) && Math.abs(leftEnc.get()) * metersPerTick > TT_Util.inchesToMeters(randomInches)) {
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

    public int dropGear() {
        if (firstRun) {
            firstRun = false;
            counter = 0;
        }
        counter++;
        if (counter < 25) {
            Robot.gearClaw.set(DoubleSolenoid.Value.kReverse);
            return 1;
        }
        if (counter < 50) {
            Robot.gearPivot.set(-0.35);
            return 1;
        }
        firstRun = true;
        counter = 0;
        return 0;
    }

    public int resetGearCollector() {
        if (firstRun) {
            firstRun = false;
            counter = 0;
        }
        counter++;
        if (counter < 50) {
            Robot.gearClaw.set(DoubleSolenoid.Value.kForward);
            Robot.gearPivot.set(0.35);
            System.out.println("resetting");
            return 1;
        }
        firstRun = true;
        counter = 0;
        return 0;
    }
}
