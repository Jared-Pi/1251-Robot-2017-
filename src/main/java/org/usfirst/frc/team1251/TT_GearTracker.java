package org.usfirst.frc.team1251;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static java.lang.Double.NaN;

/**
 * Created by Eric Engelhart on 3/19/2017.
 */
public class TT_GearTracker {
    int cameraMiddleX = 264;
    int pixelError = 2;
    double leftTurning;
    double rightTurning;
    double gearX;
    public TT_GearTracker(){

    }

    public boolean track(TT_GRIP_Communicator grip) {
        leftTurning = 0;
        rightTurning = 0;
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
            int error = (int) (gearX - cameraMiddleX);
            if (Math.abs(error) > pixelError) {
                if (error < 0) {  // need to turn left
                    leftTurning = -(Math.log10(Math.abs(error)) + 1) * 10;
                    rightTurning = (Math.log10(Math.abs(error)) + 1) * 10;
                } else if (error > 0) { // need to turn right
                    leftTurning = (Math.log10(error) + 1) * 10;
                    rightTurning = -(Math.log10(error) + 1) * 10;
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

    public double getLeftTurning() {
        return leftTurning;
    }

    public double getRightTurning() {
        return rightTurning;
    }
}
