package org.usfirst.frc.team1251;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Created by Eric Engelhart on 1/27/2017.
 */

public class Robot extends IterativeRobot {

    //Define Joystick inputs
    public static final int CONTROLLER_LEFT_AXIS = 1;
    public static final int CONTROLLER_RIGHT_AXIS = 3;
    public static final int CONTROLLER_X_BUTTON = 1;
    public static final int CONTROLLER_A_BUTTON = 2;
    public static final int CONTROLLER_B_BUTTON = 3;
    public static final int CONTROLLER_Y_BUTTON = 4;
    public static final int CONTROLLER_LEFT_BUMPER = 5;
    public static final int CONTROLLER_RIGHT_BUMPER = 6;
    public static final int CONTROLLER_LEFT_TRIGGER = 7;
    public static final int CONTROLLER_RIGHT_TRIGGER = 8;
    public static final int CONTROLLER_SELECT_BUTTON = 9;
    public static final int CONTROLLER_START_BUTTON = 10;
    public static final int STICK_AXIS = 1;
    public static final int STICK_LEVEL = 2;
    public static final int STICK_TRIGGER = 1;
    public static final int STICK_BUTTON_2 = 2;
    public static final int STICK_BUTTON_3 = 3;
    public static final int STICK_BUTTON_4 = 4;
    public static final int STICK_BUTTON_5 = 5;
    public static final int STICK_BUTTON_6 = 6;
    public static final int STICK_BUTTON_7 = 7;
    public static final int STICK_BUTTON_8 = 8;
    public static final int STICK_BUTTON_9 = 9;

    //Define PWM ports
    private static final int PWM_PORT_0 = 0;
    private static final int PWM_PORT_1 = 1;
    private static final int PWM_PORT_2 = 2;
    private static final int PWM_PORT_3 = 3;
    private static final int PWM_PORT_4 = 4;
    private static final int PWM_PORT_5 = 5;
    private static final int PWM_PORT_6 = 6;
    private static final int PWM_PORT_7 = 7;
    private static final int PWM_PORT_8 = 8;
    private static final int PWM_PORT_9 = 9;

    //Define PCM ports
    private static final int PCM_PORT_0 = 0;
    private static final int PCM_PORT_1 = 1;
    private static final int PCM_PORT_2 = 2;
    private static final int PCM_PORT_3 = 3;
    private static final int PCM_PORT_4 = 4;
    private static final int PCM_PORT_5 = 5;
    private static final int PCM_PORT_6 = 6;
    private static final int PCM_PORT_7 = 7;

    //Define DIO ports
    private static final int DIO_PORT_0 = 0;
    private static final int DIO_PORT_1 = 1;
    private static final int DIO_PORT_2 = 2;
    private static final int DIO_PORT_3 = 3;
    private static final int DIO_PORT_4 = 4;
    private static final int DIO_PORT_5 = 5;
    private static final int DIO_PORT_6 = 6;
    private static final int DIO_PORT_7 = 7;
    private static final String GRIP_TABLE_NAME = "GRIP";
    //Define Speed controllers
    public static RobotDrive driveBase;
    //Define Solenoids
    public static DoubleSolenoid driveBaseShifter;
    //Define encoder
    public static Encoder driveEncoderLeft;
    public static Encoder driveEncoderRight;
    //Define booleans
    public static boolean lockControls = false;
    public static ADXRS450_Gyro gyro;
    //Define PIDs
    private final double drive_P = 0.00012;
    private final double drive_I = 0.0004;
    private final double drive_D = 0;
    public int autoSelect;
    //Define Joystick ports
    private Joystick controller;
    private Joystick stick;
    private Joystick rightStick;
    private Talon shooter;
    private Talon agitator;
    private Talon ballCollector;
    private Talon gearCollector;
    private Talon hanger;
    private Talon gearPivot;
    private DoubleSolenoid ballCollectorPivot;
    private DoubleSolenoid gearClaw;
    private Encoder shooterEncoder;
    private Encoder hangLimit;
    //Define Sensors
    private Potentiometer gearPot;
    //Define network table grip communicator
    private TT_GRIP_Communicator gripCommunicator;
    private PIDController leftDrive;
    private PIDController rightDrive;
    private TT_DoubleTalonPID leftDriveTalons;
    private TT_DoubleTalonPID rightDriveTalons;

    @Override
    public void robotInit() {
        //Declare joystick
        controller = new Joystick(0);
        stick = new Joystick(1);
        rightStick = new Joystick(2);

        //Declare Speed controllers
        Talon leftTalon1 = new Talon(PWM_PORT_0);
        Talon leftTalon2 = new Talon(PWM_PORT_1);
        Talon rightTalon1 = new Talon(PWM_PORT_2);
        Talon rightTalon2 = new Talon(PWM_PORT_3);

        leftDriveTalons = new TT_DoubleTalonPID(leftTalon1, leftTalon2, true);
        rightDriveTalons = new TT_DoubleTalonPID(rightTalon1, rightTalon2, true);

        shooter = new Talon(PWM_PORT_8);
        agitator = new Talon(PWM_PORT_7);
        ballCollector = new Talon(PWM_PORT_6);
        gearCollector = new Talon(PWM_PORT_4);
        gearPivot = new Talon(PWM_PORT_5);
        hanger = new Talon(PWM_PORT_9);

        driveBase = new RobotDrive(leftTalon1, leftTalon2, rightTalon1, rightTalon2);

        //Declare Solenoids
        driveBaseShifter = new DoubleSolenoid(PCM_PORT_0, PCM_PORT_1);
        ballCollectorPivot = new DoubleSolenoid(PCM_PORT_4, PCM_PORT_5);
        gearClaw = new DoubleSolenoid(PCM_PORT_2, PCM_PORT_3);

        //Declare encoder
        driveEncoderLeft = new Encoder(DIO_PORT_0, DIO_PORT_1);
        driveEncoderRight = new Encoder(DIO_PORT_2, DIO_PORT_3);
        shooterEncoder = new Encoder(DIO_PORT_6, DIO_PORT_7);
        hangLimit = new Encoder(DIO_PORT_4, DIO_PORT_5);

        //Declare Sensors
        gearPot = new AnalogPotentiometer(0, 3600, 3);

        gripCommunicator = new TT_GRIP_Communicator(NetworkTable.getTable(GRIP_TABLE_NAME));

        // PIDs
        driveEncoderLeft.setPIDSourceType(PIDSourceType.kRate);
        driveEncoderRight.setPIDSourceType(PIDSourceType.kRate);

        leftDrive = new PIDController(drive_P, drive_I, drive_D, driveEncoderLeft, leftDriveTalons, 0.01);
        rightDrive = new PIDController(drive_P, drive_I, drive_D, driveEncoderRight, rightDriveTalons, 0.01);


        leftDrive.enable();
        rightDrive.enable();

        gyro = new ADXRS450_Gyro();
        gyro.calibrate();
        SmartDashboard.putNumber("Auto", -1);

    }

    @Override
    public void autonomousInit() {
        //leftDrive.disable();
        rightDrive.disable();
        driveEncoderRight.setReverseDirection(true);
        driveEncoderRight.setDistancePerPulse(0.0005142918);
        driveEncoderLeft.setDistancePerPulse(0.0005142918);
        driveEncoderLeft.reset();
        driveEncoderRight.reset();
        autoSelect = (int) SmartDashboard.getNumber("Auto", -1);
        SmartDashboard.putString("Autos", "1: Go past baseline\n2: Middle Gear\n3:Stay straight");
        TT_MainAuto.autoInit();

    }

    @Override
    public void autonomousPeriodic() {
        TT_MainAuto.auto(autoSelect, driveBase, driveBaseShifter, gearPivot, gearClaw, gyro, driveEncoderLeft, driveEncoderRight);

        SmartDashboard.putNumber("auto left enc", driveEncoderLeft.getDistance());
        SmartDashboard.putNumber("auto right enc", driveEncoderRight.getDistance());
        SmartDashboard.putNumber("Gyro", gyro.getAngle());
        //autoSelect = (int) SmartDashboard.getNumber("Auto", -1);
    }

    @Override
    public void teleopInit() {
        //this is due to reversed encoders in both bots
        driveEncoderRight.setReverseDirection(true);
        //it is 0.00050779561 meters per tick, as can be seen in the google sheets
        driveEncoderRight.setDistancePerPulse(0.00050779561);
        driveEncoderLeft.setDistancePerPulse(0.00050779561);
    }

    @Override
    public void teleopPeriodic() {
        //Subsystem 1, Drivebase
        TT_Drive.drive(controller, driveBase);
        //TT_Drive.shifter(driveEncoderLeft, driveEncoderRight, driveBaseShifter);
        if (stick.getRawButton(CONTROLLER_SELECT_BUTTON) || stick.getRawButton(CONTROLLER_START_BUTTON)) {
            TT_Hanger.hang(stick, hanger, hangLimit, gearPivot);
        } else {
            hanger.set(0);
            TT_GearCollector.collectGearFloor(stick, gearCollector, gearPivot, gearClaw, gearPot);
        }

        SmartDashboard.putNumber("Left encoder rate", driveEncoderLeft.getRate());
        SmartDashboard.putNumber("Right encoder rate", driveEncoderRight.getRate());

        SmartDashboard.putNumber("Pot", gearPot.get());
    }

    @Override
    public void testInit() {
        driveEncoderRight.setReverseDirection(true);
        //driveEncoderRight.setDistancePerPulse(0.0005142918);
        //driveEncoderLeft.setDistancePerPulse(0.0005142918);

        driveEncoderLeft.reset();
        driveEncoderRight.reset();

        leftDrive.enable();
        rightDrive.enable();

        SmartDashboard.putData("PID", leftDrive);
        SmartDashboard.putNumber("Encoder", driveEncoderLeft.getRate());
    }

    @Override
    public void testPeriodic() {
        if (controller.getRawButton(CONTROLLER_A_BUTTON)) {
            leftDrive.setSetpoint(TT_Util.convertRPMsToTicks(100));
        } else {
            leftDrive.setSetpoint(0);
        }
        SmartDashboard.putData("PID", leftDrive);
        SmartDashboard.putNumber("Encoder", TT_Util.convertTicksToRPMs(driveEncoderLeft.getRate()));
    }

}
