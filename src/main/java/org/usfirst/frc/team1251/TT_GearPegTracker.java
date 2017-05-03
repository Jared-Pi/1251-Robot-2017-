package org.usfirst.frc.team1251;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Created by Eric Engelhart on 2/10/2017.
 */
public class TT_GearPegTracker {
    public static TT_GearPegTracker INSTANCE;
    final int PEG_HIGHEST_Y = 205;
    int cameraMiddleX = 300;
    double leftTurning;
    double rightTurning;

    public TT_GearPegTracker() {
        INSTANCE = this;
    }

    public boolean track(TT_GRIP_Communicator grip, int pixelError) {
        double[] gearPegs = grip.getAreaFromTable("GearPeg");
        double[] Xs = grip.getXFromTable("GearPeg");
        double[] Ys = grip.getYFromTable("GearPeg");
        double[] widths = grip.getWidthFromTable("GearPeg");
        double[] heights = grip.getHeightFromTable("GearPeg");

        fucntion:
        {
            if (gearPegs.length > 1 && Xs.length > 1 && Ys.length > 1 && widths.length > 1 && heights.length > 1) {
                SmartDashboard.putBoolean("getting table values", true);
                for (int i = 0; i < widths.length; i++) {
                    //System.out.println("THING" + i + ": " + heights[i] / widths[i]);
                    if (heights[i] / widths[i] < 1.5 && heights[i] / widths[i] > 3) {
                        gearPegs[i] = -1;
                    }
                }
                for (int i = 0; i < Ys.length; i++) {
                    if (Ys[i] > PEG_HIGHEST_Y) {
                        gearPegs[i] = -1;
                    }
                }
                double biggest = gearPegs[0];
                int biggestI = 0;
                int biggerI = 0;
                double bigger = gearPegs[1];
                for (int i = 0; i < gearPegs.length; i++) {
                    if (bigger > biggest) {
                        int temp = biggerI;
                        double tempV = bigger;
                        bigger = biggest;
                        biggerI = biggestI;
                        biggest = tempV;
                        biggestI = temp;
                    }
                    if (gearPegs[i] > biggest) {
                        bigger = biggest;
                        biggerI = biggestI;
                        biggest = gearPegs[i];
                        biggestI = i;
                    }
                }

                if (bigger < 0 || biggest < 0) {
                    break fucntion;
                }

                double leftPegX = Xs[biggerI];
                double rightPegX = Xs[biggestI];
                double pegX = (leftPegX + rightPegX) / 2;
                System.out.println(pegX);
                SmartDashboard.putNumber("PEG X", pegX);
                int error = (int) (pegX - cameraMiddleX);
                if (Math.abs(error) > pixelError) {
                    if (error < 0) {  // need to turn left
                        leftTurning = (-Math.pow((Math.log10(Math.abs(error)) + 1), 2) * 25) - 10;
                        rightTurning = Math.pow((Math.log10(Math.abs(error)) + 1), 2) * 25 + 10;
                    } else if (error > 0) { // need to turn right
                        leftTurning = Math.pow((Math.log10(error) + 1), 2) * 25 + 10;
                        rightTurning = -Math.pow((Math.log10(error) + 1), 2) * 25 - 10;
                    }
                    SmartDashboard.putNumber("Error", error);
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
        return false;
    }

    public double getLeftTurning() {
        return leftTurning;
    }

    public double getRightTurning() {
        return rightTurning;
    }

}
