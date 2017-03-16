package org.usfirst.frc.team1251;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Created by Eric Engelhart on 2/10/2017.
 */
public class TT_GearTracker {

    public TT_GearTracker(){

    }

    public boolean track(){
        double[] tape = TT_GRIP_Communicator.INSTANCE.getAreaFromTable("Gear");
        if (tape.length < 2 || tape.length > 2) {
            SmartDashboard.putBoolean("Gear Tracking", false);
            return false;
        }
        double[] Xs = TT_GRIP_Communicator.INSTANCE.getXFromTable("Gear");
        if (Xs[0] > Xs[1]) {

        }
        return false;
    }

}
