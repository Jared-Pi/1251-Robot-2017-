package org.usfirst.frc.team1251;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;

/**
 * Created by Jared on 2/10/2017.
 */
public class TT_Util {

    static double lSpeed = 0;
    static double rSpeed = 0;

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

    public static void driveStraight(RobotDrive baseDrive, DoubleSolenoid baseShifter, Encoder lEncoder, Encoder rEncoder){
        if (lEncoder.getRate()<1.2) {
            lSpeed -= 0.01;
        }
        else if (lEncoder.getRate()>1.3) {
            lSpeed += 0.01;
        }

        if (rEncoder.getRate()<1.2) {
            rSpeed -= 0.01;
        }
        else if (rEncoder.getRate()>1.3) {
            rSpeed += 0.01;
        }

        baseShifter.set(DoubleSolenoid.Value.kReverse);
        if (lEncoder.getDistance() <= 4 && lEncoder.getDistance() <= 4) {
            baseDrive.tankDrive(lSpeed,rSpeed);
        }
        else {
            baseDrive.tankDrive(0, 0);
        }
    }

}
