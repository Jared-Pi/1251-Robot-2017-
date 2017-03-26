package org.usfirst.frc.team1251;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

import static java.lang.Double.NaN;

/**
 * Created by Eric Engelhart on 2/11/2017.
 */
public class TT_GRIP_Communicator {
    public static TT_GRIP_Communicator INSTANCE;
    private NetworkTable gripTable;

    private double[] defaultValues;

    public TT_GRIP_Communicator(NetworkTable gripTable){
        this.gripTable = gripTable;
        defaultValues = new double[1];
        defaultValues[0] = NaN;
        INSTANCE = this;
    }

    public double[] getAreaFromTable(String tableName){
        double[] value;
        value = gripTable.getSubTable(tableName).getNumberArray("area", defaultValues);
        return value;
    }

    public double[] getXFromTable(String tableName) {
        double[] value;
        value = gripTable.getSubTable(tableName).getNumberArray("centerX", defaultValues);
        return value;
    }

    public double[] getYFromTable(String tableName) {
        double[] value;
        value = gripTable.getSubTable(tableName).getNumberArray("centerY", defaultValues);
        return value;
    }

    public double[] getWidthFromTable(String tableName) {
        double[] value;
        value = gripTable.getSubTable(tableName).getNumberArray("width", defaultValues);
        return value;
    }

    public double[] getHeightFromTable(String tableName) {
        double[] value;
        value = gripTable.getSubTable(tableName).getNumberArray("height", defaultValues);
        return value;
    }

}
