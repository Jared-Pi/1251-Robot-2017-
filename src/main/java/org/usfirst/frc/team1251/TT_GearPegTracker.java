package org.usfirst.frc.team1251;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static java.lang.Double.NaN;

/**
 * Created by Eric Engelhart on 2/10/2017.
 */
public class TT_GearPegTracker {
    int cameraMiddleX = 264;
    int pixelError = 3;
    double leftTurning;
    double rightTurning;

    public TT_GearPegTracker() {

    }

    public boolean track(TT_GRIP_Communicator grip) {

        double[] gearPegs = grip.getAreaFromTable("GearPeg");
        double[] Xs = grip.getXFromTable("GearPeg");
        if (gearPegs != null && gearPegs[0] != NaN) {
            SmartDashboard.putBoolean("getting table values", true);
            double leftPegX = Xs[0];
            double rightPegX = Xs[0];
            double pegX = (leftPegX + rightPegX) / 2;
            double multiplier = 1;
            if (Math.abs(pegX - cameraMiddleX) > pixelError) {
                if (Math.abs(pegX - cameraMiddleX) > 75) {
                    multiplier = 0.9;
                }
                if (Math.abs(pegX - cameraMiddleX) < 50) {
                    multiplier = 1.14 + 0.004666 * Math.abs(pegX - cameraMiddleX);
                }

                if (pegX - cameraMiddleX < 0) {
                    leftTurning = -Math.sqrt(-(pegX - cameraMiddleX) / cameraMiddleX) * multiplier;
                    rightTurning = Math.sqrt(-(pegX - cameraMiddleX) / cameraMiddleX) * multiplier;
                } else if (pegX - cameraMiddleX > 0) {
                    leftTurning = Math.sqrt(((pegX - cameraMiddleX) / cameraMiddleX)) * multiplier;
                    rightTurning = -Math.sqrt((pegX - cameraMiddleX) / cameraMiddleX) * multiplier;

                }
                if (Math.abs(pegX - cameraMiddleX) < 25) {
                    if (pegX - cameraMiddleX < 0) {
                        leftTurning = 0.40;
                        rightTurning = -0.39;
                    } else if (pegX - cameraMiddleX > 0) {
                        leftTurning = -0.40;
                        rightTurning = 0.39;

                    }
                }
                SmartDashboard.putNumber("Error", pegX - cameraMiddleX);
            } else if (Math.abs(pegX - cameraMiddleX) < pixelError) {
                SmartDashboard.putNumber("Error", Math.abs(pegX - cameraMiddleX));
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
