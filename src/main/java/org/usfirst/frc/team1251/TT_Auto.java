package org.usfirst.frc.team1251;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;

import java.io.File;

import static org.usfirst.frc.team1251.Robot.*;

/**
 * Created by Eric Engelhart on 2/27/2017.
 */
public class TT_Auto {

    static final double wheel_diameter = 0.0889;
    static final double MAX_VELOCITY = 4.876; // of robot in m/s
    static final double Drive_K_P = 1.0;
    static final double Drive_K_I = 0.0;
    static final double Drive_K_D = 0.0;
    static final double Drive_K_V = 1/MAX_VELOCITY;
    static final double Drive_K_A = 0.0;
    static Trajectory left;
    static Trajectory right;
    static EncoderFollower leftEncoderFollower;
    static EncoderFollower rightEncoderFollower;
    static int leftEncoderPos;
    static int rightEncoderPos;
    static int methodDone;
    static int methodNum;
    static int counter;

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
        double l = leftEncoderFollower.calculate(driveEncoderLeft.get());
        double r = rightEncoderFollower.calculate(driveEncoderRight.get());

        double gyroHeading =  Robot.gyro.getAngle();  // Assuming the gyro is giving a value in degrees
        double desiredHeading = Pathfinder.r2d(leftEncoderFollower.getHeading());  // Should also be in degrees

        double angleDifference = Pathfinder.boundHalfDegrees(desiredHeading - gyroHeading);
        // boundHalfDegrees locks the values between -180 and +180, so that turn isn't overly big
        double turn = 0.8 * (-1.0/80.0) * angleDifference; // this math just shrinks the values to not overly influence the drivetrain

        driveBase.tankDrive(l + turn, r - turn);

    }

    public static void twoGearAutoInit() {
        driveEncoderRight.setReverseDirection(true);
        //driveEncoderRight.setDistancePerPulse(0.0005142918);
        //driveEncoderLeft.setDistancePerPulse(0.0005142918);

        driveEncoderLeft.reset();
        driveEncoderRight.reset();

        Robot.leftDrive.enable();
        Robot.rightDrive.enable();

        SmartDashboard.putData("PID", Robot.leftDrive);
        SmartDashboard.putNumber("Encoder", driveEncoderLeft.getRate());

        methodDone = 1;
        methodNum = 1;
        counter = 0;
    }

    public static void twoGearAutoPeriodic(Joystick controller) {
        // remove all button A code
        if (methodNum < 2) {
            methodDone = TT_DriveUtil.INSTANCE.driveStraight(350, 75);
        } else if (methodNum < 3) {
            methodDone = TT_Util.pause(15);
        } else if (methodNum < 4) {
            methodDone = TT_DriveUtil.INSTANCE.driveBackwards(450, 48);
        } else if (methodNum < 5) {
            methodDone = TT_DriveUtil.INSTANCE.turnRobot(300, 24);
        } else if (methodNum < 6) {
            if (counter < 75) {
                methodDone = TT_DriveUtil.INSTANCE.trackGear();
            } else {
                methodDone = 0;
                counter = 0;
            }
            counter++;
        } else if (methodNum < 7) {
            methodDone = TT_DriveUtil.INSTANCE.forwardTrackGear();
        } else if (methodNum < 8) {
            methodDone = TT_DriveUtil.INSTANCE.driveStraightAndCoast(100, 10);
        } else if (methodNum < 9) {
            methodDone = TT_DriveUtil.INSTANCE.driveBackwards(450, 20);
        } else if (methodNum < 10) {
            methodDone = TT_DriveUtil.INSTANCE.turnRobot(-250, 30);
        } else if (methodNum < 11) {
            methodDone = TT_DriveUtil.INSTANCE.trackPeg(7);
        } else if (methodNum < 12) {
            methodDone = TT_DriveUtil.INSTANCE.driveStraightAndCoast(350, 48);
        } else if (methodNum < 13) {
            methodDone = 0;//TT_DriveUtil.INSTANCE.trackPeg(3);
        } else if (methodNum < 14) {
            methodDone = 0; //TT_DriveUtil.INSTANCE.driveStraightAndCoast(350, 15);
        } else {
            methodDone = 1;
            driveBase.tankDrive(0, 0);
        }
        SmartDashboard.putData("PID", leftDrive);
        SmartDashboard.putNumber("Encoder", TT_Util.convertTicksToRPMs(driveEncoderLeft.getRate()));
        SmartDashboard.putNumber("right", TT_Util.convertTicksToRPMs(driveEncoderRight.getRate()));


        SmartDashboard.putNumber("Left", leftDriveTalons.get());
        SmartDashboard.putNumber("Right", rightDriveTalons.get());
        SmartDashboard.putNumber("Right Encoder Val", driveEncoderRight.get());

        if (methodDone == 0) {
            methodNum++;
            methodDone = 1;
        }

    }

    public static void blueLoaderStationInit() {
        driveEncoderRight.setReverseDirection(true);
        //driveEncoderRight.setDistancePerPulse(0.0005142918);
        //driveEncoderLeft.setDistancePerPulse(0.0005142918);

        driveEncoderLeft.reset();
        driveEncoderRight.reset();

        Robot.leftDrive.enable();
        Robot.rightDrive.enable();

        SmartDashboard.putData("PID", Robot.leftDrive);
        SmartDashboard.putNumber("Encoder", driveEncoderLeft.getRate());

        methodDone = 1;
        methodNum = 0;
        counter = 0;
    }

    public static void blueLoaderStationPeriodic(Joystick controller) {
        if (methodDone == 0) {
            methodNum++;
        }
        if (methodNum < 1) {
            methodDone = TT_DriveUtil.INSTANCE.driveStraight(200, 30);
        } else if (methodNum < 2) {
            methodDone = TT_DriveUtil.INSTANCE.forwardsTurn(20, 100, 43);
        } else if (methodNum < 3) {
            methodDone = TT_DriveUtil.INSTANCE.driveStraightAndCoast(300, 70);
        } else if (methodNum < 4) {
            methodDone = TT_DriveUtil.INSTANCE.forwardsTurn(100, 20, 10);
        } else if (methodNum < 5) {
            /*counter++;
            if (counter < 150) {
                methodDone = TT_DriveUtil.INSTANCE.trackPeg(7);
            } else {
                leftDrive.disable();
                rightDrive.disable();
                TT_DriveUtil.INSTANCE.resetPIDs();
                TT_DriveUtil.INSTANCE.firstRun = true;
                counter = 0;
                methodNum++;
            }*/
            methodNum++;
        } else if (methodNum < 6) {
            methodDone = TT_DriveUtil.INSTANCE.driveStraightAndCoast(200, 24);
        } else if (methodNum < 7) {
            methodDone = TT_Util.pause(30);
        } else if (methodNum < 8) {
            methodDone = TT_DriveUtil.INSTANCE.dropGear();
        } else if (methodNum < 9) {
            methodDone = TT_DriveUtil.INSTANCE.driveBackwards(200, 64);
        } else if (methodNum < 10) {
            methodDone = TT_DriveUtil.INSTANCE.resetGearCollector();
        } else if (methodNum < 11) {
            methodDone = TT_DriveUtil.INSTANCE.turnRobot(-200, 16);
        } else if (methodNum < 12) {
            methodDone = TT_DriveUtil.INSTANCE.driveStraightAndCoast(300, 180);
        } else {
            driveBase.tankDrive(0, 0);
        }

        SmartDashboard.putNumber("Left", Robot.leftDriveTalons.get());
        SmartDashboard.putNumber("Right", Robot.rightDriveTalons.get());
    }

    public static void redBoilerAutoInit() {
        driveEncoderRight.setReverseDirection(true);
        //driveEncoderRight.setDistancePerPulse(0.0005142918);
        //driveEncoderLeft.setDistancePerPulse(0.0005142918);

        driveEncoderLeft.reset();
        driveEncoderRight.reset();

        Robot.leftDrive.enable();
        Robot.rightDrive.enable();

        SmartDashboard.putData("PID", Robot.leftDrive);
        SmartDashboard.putNumber("Encoder", driveEncoderLeft.getRate());

        methodDone = 1;
        methodNum = 0;
        counter = 0;
    }

    public static void redBoilerAutoPeriodic(Joystick controller) {
        if (methodDone == 0) {
            methodNum++;
        }

        if (methodNum < 1) {
            methodDone = TT_DriveUtil.INSTANCE.driveStraight(200, 30);
        } else if (methodNum < 2) {
            methodDone = TT_DriveUtil.INSTANCE.forwardsTurn(20, 100, 30);
        } else if (methodNum < 3) {
            methodDone = TT_DriveUtil.INSTANCE.driveStraightAndCoast(300, 70);
        } else if (methodNum < 4) {
            methodDone = TT_DriveUtil.INSTANCE.forwardsTurn(100, 20, 5);
        } else if (methodNum < 5) {
            /*counter++;
            if (counter < 150) {
                methodDone = TT_DriveUtil.INSTANCE.trackPeg(7);
            } else {
                leftDrive.disable();
                rightDrive.disable();
                TT_DriveUtil.INSTANCE.resetPIDs();
                TT_DriveUtil.INSTANCE.firstRun = true;
                counter = 0;
                methodNum++;
            }*/
            methodNum++;
        } else if (methodNum < 6) {
            methodDone = TT_DriveUtil.INSTANCE.driveStraightAndCoast(200, 24);
        } else if (methodNum < 7) {
            methodDone = TT_Util.pause(30);
        } else if (methodNum < 8) {
            methodDone = TT_DriveUtil.INSTANCE.dropGear();
        } else if (methodNum < 9) {
            methodDone = TT_DriveUtil.INSTANCE.driveBackwards(200, 64);
        } else if (methodNum < 10) {
            methodDone = TT_DriveUtil.INSTANCE.resetGearCollector();
        } else if (methodNum < 11) {
            methodDone = TT_DriveUtil.INSTANCE.turnRobot(-200, 10);
        } else if (methodNum < 12) {
            methodDone = TT_DriveUtil.INSTANCE.driveStraight(300, 72);
        } else if (methodNum < 13) {
            methodDone = TT_DriveUtil.INSTANCE.turnRobot(200, 4);
        } else if (methodNum < 14) {
            methodDone = TT_DriveUtil.INSTANCE.driveStraightAndCoast(300, 240);
        } else {
            driveBase.tankDrive(0, 0);
        }

        SmartDashboard.putNumber("Left", leftDriveTalons.get());
        SmartDashboard.putNumber("Right", rightDriveTalons.get());

    }

    public static void redLoaderStationInit() {
        driveEncoderRight.setReverseDirection(true);
        //driveEncoderRight.setDistancePerPulse(0.0005142918);
        //driveEncoderLeft.setDistancePerPulse(0.0005142918);

        driveEncoderLeft.reset();
        driveEncoderRight.reset();

        leftDrive.enable();
        rightDrive.enable();

        SmartDashboard.putData("PID", leftDrive);
        SmartDashboard.putNumber("Encoder", driveEncoderLeft.getRate());

        methodDone = 1;
        methodNum = 0;
        counter = 0;
    }

    public static void redLoaderStationPeriodic(Joystick controller) {
        System.out.println("NUM" + methodNum);
        if (methodDone == 0) {
            methodNum++;
        }
        if (methodNum < 1) {
            methodDone = TT_DriveUtil.INSTANCE.driveStraight(200, 30);
        } else if (methodNum < 2) {
            methodDone = TT_DriveUtil.INSTANCE.forwardsTurn(100, 20, 30);
        } else if (methodNum < 3) {
            methodDone = TT_DriveUtil.INSTANCE.driveStraightAndCoast(300, 70);
        } else if (methodNum < 4) {
            methodDone = TT_DriveUtil.INSTANCE.forwardsTurn(20, 100, 5);
        } else if (methodNum < 5) {
            /*counter++;
            if (counter < 150) {
                methodDone = TT_DriveUtil.INSTANCE.trackPeg(7);
            } else {
                leftDrive.disable();
                rightDrive.disable();
                TT_DriveUtil.INSTANCE.resetPIDs();
                TT_DriveUtil.INSTANCE.firstRun = true;
                counter = 0;
                methodNum++;
            }*/
            methodNum++;
        } else if (methodNum < 6) {
            methodDone = TT_DriveUtil.INSTANCE.driveStraightAndCoast(200, 24);
        } else if (methodNum < 7) {
            methodDone = TT_Util.pause(30);
        } else if (methodNum < 8) {
            methodDone = TT_DriveUtil.INSTANCE.dropGear();
        } else if (methodNum < 9) {
            methodDone = TT_DriveUtil.INSTANCE.driveBackwards(200, 64);
        } else if (methodNum < 10) {
            methodDone = TT_DriveUtil.INSTANCE.resetGearCollector();
        } else if (methodNum < 11) {
            methodDone = TT_DriveUtil.INSTANCE.turnRobot(200, 10);
        } else if (methodNum < 12) {
            methodDone = TT_DriveUtil.INSTANCE.driveStraight(300, 72);
        } else if (methodNum < 13) {
            methodDone = TT_DriveUtil.INSTANCE.turnRobot(-200, 8);
        } else if (methodNum < 14) {
            methodDone = TT_DriveUtil.INSTANCE.driveStraightAndCoast(300, 240);
        } else {
            driveBase.tankDrive(0, 0);
        }

        SmartDashboard.putNumber("Left", leftDriveTalons.get());
        SmartDashboard.putNumber("Right", rightDriveTalons.get());
    }

    public static void blueBoilerInit() {
        driveEncoderRight.setReverseDirection(true);
        //driveEncoderRight.setDistancePerPulse(0.0005142918);
        //driveEncoderLeft.setDistancePerPulse(0.0005142918);

        driveEncoderLeft.reset();
        driveEncoderRight.reset();

        leftDrive.enable();
        rightDrive.enable();

        SmartDashboard.putData("PID", leftDrive);
        SmartDashboard.putNumber("Encoder", driveEncoderLeft.getRate());

        methodDone = 1;
        methodNum = 0;
        counter = 0;
    }

    public static void blueBoilerPeriodic(Joystick controller) {
        System.out.println(methodNum);
        if (methodDone == 0) {
            methodNum++;
        }
        if (methodNum < 1) {
            methodDone = TT_DriveUtil.INSTANCE.driveStraight(200, 30);

        } else if (methodNum < 2) {
            methodDone = TT_DriveUtil.INSTANCE.forwardsTurn(100, 20, 30);
        } else if (methodNum < 3) {
            methodDone = TT_DriveUtil.INSTANCE.driveStraightAndCoast(300, 70);
        } else if (methodNum < 4) {
            methodDone = TT_DriveUtil.INSTANCE.forwardsTurn(20, 100, 5);
        } else if (methodNum < 5) {
            /*counter++;
            if (counter < 150) {
                methodDone = TT_DriveUtil.INSTANCE.trackPeg(7);
            } else {
                leftDrive.disable();
                rightDrive.disable();
                TT_DriveUtil.INSTANCE.resetPIDs();
                TT_DriveUtil.INSTANCE.firstRun = true;
                counter = 0;
                methodNum++;
            }*/
            methodNum++;
            methodDone = 0;
        } else if (methodNum < 6) {
            methodDone = TT_DriveUtil.INSTANCE.driveStraightAndCoast(200, 24);
        } else if (methodNum < 7) {
            methodDone = TT_Util.pause(30);
        } else if (methodNum < 8) {
            methodDone = TT_DriveUtil.INSTANCE.dropGear();
        } else if (methodNum < 9) {
            methodDone = TT_DriveUtil.INSTANCE.driveBackwards(200, 48);
        } else if (methodNum < 10) {
            methodDone = TT_DriveUtil.INSTANCE.resetGearCollector();
        } else if (methodNum < 11) {
            methodDone = TT_DriveUtil.INSTANCE.turnRobot(200, 10);
        } else if (methodNum < 12) {
            methodDone = TT_DriveUtil.INSTANCE.driveStraightAndCoast(300, 120);
        } else if (methodNum < 13) {
            methodDone = TT_DriveUtil.INSTANCE.turnRobot(-200, 10);
        } else if (methodNum < 14) {
            methodDone = TT_DriveUtil.INSTANCE.driveStraightAndCoast(300, 240);
        } else {
            leftDrive.disable();
            rightDrive.disable();
            driveBase.tankDrive(0, 0);
        }

        SmartDashboard.putNumber("Left", leftDriveTalons.get());
        SmartDashboard.putNumber("Right", rightDriveTalons.get());
    }

    public static void breakBaselinePeriodic() {
        if (methodDone == 0) {
            methodNum++;
        }

        if (methodNum < 1) {
            methodDone = TT_DriveUtil.INSTANCE.driveStraightAndCoast(300, 96);
        } else {
            leftDrive.disable();
            rightDrive.disable();
            driveBase.tankDrive(0, 0);
        }
    }

    public static void genericInit() {
        driveEncoderRight.setReverseDirection(true);
        //driveEncoderRight.setDistancePerPulse(0.0005142918);
        //driveEncoderLeft.setDistancePerPulse(0.0005142918);

        driveEncoderLeft.reset();
        driveEncoderRight.reset();

        leftDrive.enable();
        rightDrive.enable();

        SmartDashboard.putData("PID", leftDrive);
        SmartDashboard.putNumber("Encoder", driveEncoderLeft.getRate());

        methodDone = 1;
        methodNum = 0;
        counter = 0;
    }

    public static void middleGear() {
        if (methodDone == 0) {
            methodNum++;
        }

        if (methodNum < 1) {
            methodDone = TT_DriveUtil.INSTANCE.driveStraight(200, 70.5);
            gearPivot.set(-0.1);
        } else if (methodNum < 2) {
            methodDone = TT_Util.pause(50);
        } else if (methodNum < 3) {
            methodDone = TT_DriveUtil.INSTANCE.dropGear();
        } else if (methodNum < 4) {
            methodDone = TT_DriveUtil.INSTANCE.driveBackwards(300, 48);
        } else if (methodNum < 5) {
            methodDone = TT_DriveUtil.INSTANCE.resetGearCollector();
        } else {
            driveBase.tankDrive(0, 0);
        }
    }

    public static void twoGearAutoRight(Joystick controller) {
        if (methodNum < 2) {
            methodDone = TT_DriveUtil.INSTANCE.driveStraight(350, 75);
        } else if (methodNum < 3) {
            methodDone = TT_Util.pause(15);
        } else if (methodNum < 4) {
            methodDone = TT_DriveUtil.INSTANCE.driveBackwards(450, 48);
        } else if (methodNum < 5) {
            methodDone = TT_DriveUtil.INSTANCE.turnRobot(-300, 24);
        } else if (methodNum < 6) {
            if (counter < 75) {
                methodDone = TT_DriveUtil.INSTANCE.trackGear();
            } else {
                methodDone = 0;
                counter = 0;
            }
            counter++;
        } else if (methodNum < 7) {
            methodDone = TT_DriveUtil.INSTANCE.forwardTrackGear();
        } else if (methodNum < 8) {
            methodDone = TT_DriveUtil.INSTANCE.driveStraightAndCoast(100, 10);
        } else if (methodNum < 9) {
            methodDone = TT_DriveUtil.INSTANCE.driveBackwards(450, 20);
        } else if (methodNum < 10) {
            methodDone = TT_DriveUtil.INSTANCE.turnRobot(250, 30);
        } else if (methodNum < 11) {
            methodDone = TT_DriveUtil.INSTANCE.trackPeg(7);
        } else if (methodNum < 12) {
            methodDone = TT_DriveUtil.INSTANCE.driveStraightAndCoast(350, 48);
        } else if (methodNum < 13) {
            methodDone = 0;//TT_DriveUtil.INSTANCE.trackPeg(3);
        } else if (methodNum < 14) {
            methodDone = 0; //TT_DriveUtil.INSTANCE.driveStraightAndCoast(350, 15);
        } else {
            methodDone = 1;
            driveBase.tankDrive(0, 0);
        }
        SmartDashboard.putData("PID", leftDrive);
        SmartDashboard.putNumber("Encoder", TT_Util.convertTicksToRPMs(driveEncoderLeft.getRate()));
        SmartDashboard.putNumber("right", TT_Util.convertTicksToRPMs(driveEncoderRight.getRate()));


        SmartDashboard.putNumber("Left", leftDriveTalons.get());
        SmartDashboard.putNumber("Right", rightDriveTalons.get());
        SmartDashboard.putNumber("Right Encoder Val", driveEncoderRight.get());

        if (methodDone == 0) {
            methodNum++;
            methodDone = 1;
        }

    }
}
