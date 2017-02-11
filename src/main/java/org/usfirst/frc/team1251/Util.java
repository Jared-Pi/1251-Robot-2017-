package org.usfirst.frc.team1251;

/**
 * Created by Jared on 2/10/2017.
 */
public class Util {

    public static double convertToRPMs(double speed){
        // divide by ticks per revolution
        speed /= 256;
        // divide by seconds per minute
        speed /= 60;
        return speed;
    }

}
