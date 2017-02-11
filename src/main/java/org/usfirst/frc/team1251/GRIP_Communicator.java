package org.usfirst.frc.team1251;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * Created by Eric Engelhart on 2/11/2017.
 */
public class GRIP_Communicator {
    private NetworkTable gripTable;

    public GRIP_Communicator(NetworkTable gripTable){
        this.gripTable = gripTable;
    }
}
