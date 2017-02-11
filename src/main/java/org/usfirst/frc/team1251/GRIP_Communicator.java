package org.usfirst.frc.team1251;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * Created by Eric Engelhart on 2/11/2017.
 */
public class GRIP_Communicator {
    private NetworkTable gripTable;

    private double[] defaultValues;

    public GRIP_Communicator(NetworkTable gripTable){
        this.gripTable = gripTable;
        defaultValues = new double[0];
    }

    public double[] getAreaFromTable(String tableName){
        double[] value;
        value = gripTable.getSubTable(tableName).getNumberArray("area", defaultValues);
        return value;
    }

}
