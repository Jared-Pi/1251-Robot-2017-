package org.usfirst.frc.team1251;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;

import java.io.File;

/**
 * Created by Eric Engelhart on 2/27/2017.
 */
public class TT_Auto {

    static Trajectory left;
    static Trajectory right;

    static EncoderFollower leftEncoderFollower;
    static EncoderFollower rightEncoderFollower;

    static int leftEncoderPos;
    static int rightEncoderPos;
    static final double wheel_diameter = 0.0889;
    static final double MAX_VELOCITY = 4.876; // of robot in m/s
    static final double Drive_K_P = 1.0;
    static final double Drive_K_I = 0.0;
    static final double Drive_K_D = 0.0;
    static final double Drive_K_V = 1/MAX_VELOCITY;
    static final double Drive_K_A = 0.0;


    public static void autoInit(){
        File leftFile = new File("trajectories/left.traj");
        File rightFile = new File("trajectories/right.traj");

        left = Pathfinder.readFromFile(leftFile);
        right = Pathfinder.readFromFile(rightFile);

        leftEncoderFollower = new EncoderFollower(left);
        rightEncoderFollower = new EncoderFollower(right);

        // Encoder Position is the current, cumulative position of your encoder. If you're using an SRX, this will be the
// 'getEncPosition' function.
// 550 is the amount of encoder ticks per full revolution
// Wheel Diameter is the diameter of your wheels (or pulley for a track system) in meters
        leftEncoderFollower.configureEncoder(leftEncoderPos, 550, wheel_diameter);
        rightEncoderFollower.configureEncoder(rightEncoderPos, 550, wheel_diameter);

        // The first argument is the proportional gain. Usually this will be quite high
// The second argument is the integral gain. This is unused for motion profiling
// The third argument is the derivative gain. Tweak this if you are unhappy with the tracking of the trajectory
// The fourth argument is the velocity ratio. This is 1 over the maximum velocity you provided in the
//      trajectory configuration (it translates m/s to a -1 to 1 scale that your motors can read)
// The fifth argument is your acceleration gain. Tweak this if you want to get to a higher or lower speed quicker
        leftEncoderFollower.configurePIDVA(Drive_K_P, Drive_K_I, Drive_K_D, Drive_K_V, Drive_K_A);
        rightEncoderFollower.configurePIDVA(Drive_K_P, Drive_K_I, Drive_K_D, Drive_K_V, Drive_K_A);

    }

    public static void autoPeriodic() {
        double l = leftEncoderFollower.calculate(Robot.driveEncoderLeft.get());
        double r = rightEncoderFollower.calculate(Robot.driveEncoderRight.get());

        double gyroHeading =  Robot.gyro.getAngle();  // Assuming the gyro is giving a value in degrees
        double desiredHeading = Pathfinder.r2d(leftEncoderFollower.getHeading());  // Should also be in degrees

        double angleDifference = Pathfinder.boundHalfDegrees(desiredHeading - gyroHeading);
        // boundHalfDegrees locks the values between -180 and +180, so that turn isn't overly big
        double turn = 0.8 * (-1.0/80.0) * angleDifference; // this math just shrinks the values to not overly influence the drivetrain

        Robot.driveBase.tankDrive(l + turn, r - turn);

    }
}
