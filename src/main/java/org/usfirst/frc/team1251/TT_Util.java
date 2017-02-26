package org.usfirst.frc.team1251;

/**
 * Created by Jared on 2/10/2017.
 */
public class TT_Util {

    public static double convertTicksToRPMsHanger(double speed){
        // divide by ticks per revolution
        speed /= 1024;
        // divide by seconds per minute
        speed /= 60;
        return speed;
    }
    
    public static double convertTicksToRPMs(double speed){
        // divide by ticks per revolution
        speed /= 125;
        // divide by seconds per minute
        speed /= 60;
        return speed;
    }
    public static double convertRPMsToTicks(double speed){
        // multiply by ticks per revolution
        speed *= 125;
        // multiply by seconds per minute
        speed *= 60;
        return  speed;
    }

}
