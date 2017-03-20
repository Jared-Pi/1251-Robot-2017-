package org.usfirst.frc.team1251;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Created by Eric Engelhart on 3/19/2017.
 */
public class TT_GearTracker {
    int cameraMiddleX = 320;
    int pixelError = 5;
    double leftTurning;
    double rightTurning;
    public TT_GearTracker(){

    }

    public boolean track(TT_GRIP_Communicator grip) {

        double[] gears = grip.getAreaFromTable("Gear");
        double[] Xs = grip.getXFromTable("Gear");
        if (gears.length > 0) {
            SmartDashboard.putBoolean("getting table values", true);
            int bigGearIndex = 0;
            double bigGear = gears[0];
            for (int i = 0; i < gears.length; i++) {
                if (gears[i] > bigGear) {
                    bigGear = gears[i];
                    bigGearIndex = i;
                }
            }
            double gearX = Xs[bigGearIndex];
            double multiplier = 1;
            if (Math.abs(gearX - cameraMiddleX) > pixelError) {
                if (Math.abs(gearX - cameraMiddleX) < 100) {
                    multiplier = 2.5;
                }
                if (Math.abs(gearX - cameraMiddleX) < 50) {
                    multiplier = 3.2;
                }
                if (Math.abs(gearX - cameraMiddleX) < 20) {
                    multiplier = 4;
                }
                if (Math.abs(gearX - cameraMiddleX) < 15) {
                    multiplier = 5;
                }
                if (gearX - cameraMiddleX < 0) {
                    leftTurning = -((gearX - cameraMiddleX) / cameraMiddleX) * 0.8 * multiplier;
                    rightTurning = ((gearX - cameraMiddleX) / cameraMiddleX) * 0.8 * multiplier;
                } else if (gearX - cameraMiddleX > 0) {
                    leftTurning = -((gearX - cameraMiddleX) / cameraMiddleX) * 0.8 * multiplier;
                    rightTurning = ((gearX - cameraMiddleX) / cameraMiddleX) * 0.8 * multiplier;
                }
            } else if (Math.abs(gearX - cameraMiddleX) < pixelError) {
                return true;
            }
            return false;
        } else {
            SmartDashboard.putBoolean("getting table values", false);
            return false;

        }
    }

    public double getLeftTurning() {
        return leftTurning;
    }

    public double getRightTurning() {
        return rightTurning;
    }
}
