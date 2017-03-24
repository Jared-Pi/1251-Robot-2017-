package org.usfirst.frc.team1251;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static java.lang.Double.NaN;

/**
 * Created by Eric Engelhart on 3/19/2017.
 */
public class TT_GearTracker {
    int cameraMiddleX = 314;
    int pixelError = 2;
    double leftTurning;
    double rightTurning;
    public TT_GearTracker(){

    }

    public boolean track(TT_GRIP_Communicator grip) {

        double[] gears = grip.getAreaFromTable("Gear");
        double[] Xs = grip.getXFromTable("Gear");
        if (gears[0] != NaN) {
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
                if (Math.abs(gearX - cameraMiddleX) > 75) {
                    multiplier = 0.9;
                }
                if (Math.abs(gearX - cameraMiddleX) < 50) {
                    multiplier = 1.14 + 0.004666 * Math.abs(gearX - cameraMiddleX);
                }

                if (gearX - cameraMiddleX < 0) {
                    leftTurning = Math.sqrt(-(gearX - cameraMiddleX) / cameraMiddleX) * multiplier;
                    rightTurning = -Math.sqrt(-(gearX - cameraMiddleX) / cameraMiddleX) * multiplier;
                } else if (gearX - cameraMiddleX > 0) {
                    leftTurning = -Math.sqrt(((gearX - cameraMiddleX) / cameraMiddleX)) * multiplier;
                    rightTurning = Math.sqrt((gearX - cameraMiddleX) / cameraMiddleX) * multiplier;

                }
                if (Math.abs(gearX - cameraMiddleX) < 20) {
                    if (gearX - cameraMiddleX < 0) {
                        leftTurning = 0.39;
                        rightTurning = -0.38;
                    } else if (gearX - cameraMiddleX > 0) {
                        leftTurning = -0.39;
                        rightTurning = 0.38;

                    }
                }
                SmartDashboard.putNumber("Error", Math.abs(gearX - cameraMiddleX));
            } else if (Math.abs(gearX - cameraMiddleX) < pixelError) {
                SmartDashboard.putNumber("Error", Math.abs(gearX - cameraMiddleX));
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
