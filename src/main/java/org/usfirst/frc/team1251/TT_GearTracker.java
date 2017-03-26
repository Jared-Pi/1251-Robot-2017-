package org.usfirst.frc.team1251;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static java.lang.Double.NaN;

/**
 * Created by Eric Engelhart on 3/19/2017.
 */
public class TT_GearTracker {
    public static TT_GearTracker INSTANCE;
    final double VIEWING_LIMIT_Y = 410;
    int cameraMiddleX = 320;
    int pixelError = 2;
    double leftTurning;
    double rightTurning;
    double gearX;
    double gearY;
    public TT_GearTracker(){
        INSTANCE = this;
    }

    public boolean track(TT_GRIP_Communicator grip) {
        leftTurning = 0;
        rightTurning = 0;
        SmartDashboard.putBoolean("is tracking", true);
        double[] gears = grip.getAreaFromTable("Gear");
        double[] Xs = grip.getXFromTable("Gear");
        if (gears.length > 0 && Xs.length > 0 && gears[0] != NaN) {
            SmartDashboard.putBoolean("getting table values", true);
            int bigGearIndex = 0;
            double bigGear = gears[0];
            for (int i = 0; i < gears.length; i++) {
                if (gears[i] > bigGear) {
                    bigGear = gears[i];
                    bigGearIndex = i;
                }
            }
            gearX = Xs[bigGearIndex];
            SmartDashboard.putNumber("Gear X", gearX);
            int error = (int) (gearX - cameraMiddleX);
            if (Math.abs(error) > pixelError) {
                if (error < 0) {  // need to turn left
                    leftTurning = -Math.pow((Math.log10(Math.abs(error)) + 1), 2) * 10;
                    rightTurning = Math.pow((Math.log10(Math.abs(error)) + 1), 2) * 10;
                } else if (error > 0) { // need to turn right
                    leftTurning = Math.pow((Math.log10(error) + 1), 2) * 10;
                    rightTurning = -Math.pow((Math.log10(error) + 1), 2) * 10;
                }
                SmartDashboard.putNumber("Error", error);
            } else if (Math.abs(gearX - cameraMiddleX) < pixelError) {
                SmartDashboard.putNumber("Error", error);
                leftTurning = 0;
                rightTurning = 0;
                return true;
            }
            return false;
        } else {
            SmartDashboard.putBoolean("getting table values", false);
            return false;

        }
    }

    public int driveForward(TT_GRIP_Communicator grip) {
        leftTurning = 0;
        rightTurning = 0;
        double[] gears = grip.getAreaFromTable("Gear");
        double[] Ys = grip.getYFromTable("Gear");
        if (gears.length > 0 && Ys.length > 0 && gears[0] != NaN) {
            SmartDashboard.putBoolean("getting table values", true);
            int bigGearIndex = 0;
            double bigGear = gears[0];
            for (int i = 0; i < gears.length; i++) {
                if (gears[i] > bigGear) {
                    bigGear = gears[i];
                    bigGearIndex = i;
                }
            }
            gearY = Ys[bigGearIndex];
            if (gearY < VIEWING_LIMIT_Y) {
                rightTurning = ((Math.log10(VIEWING_LIMIT_Y - gearY) + 1) * 50) + 50;
                leftTurning = ((Math.log10(VIEWING_LIMIT_Y - gearY) + 1) * 50) + 50;

            } else {
                leftTurning = 0;
                rightTurning = 0;
                return 0;
            }
            return 1;
        }
        return 1;

    }

    public double getLeftTurning() {
        return leftTurning;
    }

    public double getRightTurning() {
        return rightTurning;
    }
}
