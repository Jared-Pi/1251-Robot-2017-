package org.usfirst.frc.team1251;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * Created by Eric Engelhart on 1/27/2017.
 */

public class Robot extends IterativeRobot {
    //Define Joystick inputs
    private static final int CONTROLLER_LEFT_BUMPER = 5;
    private static final int CONTROLLER_RIGHT_BUMPER = 6;
    private static final int CONTROLLER__LEFT_TRIGGER = 7;
    private static final int CONTROLLER_RIGHT_TRIGGER = 8;
    private static final int STICK_AXIS_ = 1;
    private static final int STICK_TRIGGER = 1;
    private static final int STICK_BUTTON_2 = 2;
    private static final int STICK_BUTTON_3 = 3;
    private static final int STICK_BUTTON_4 = 4;
    private static final int STICK_BUTTON_5 = 5;

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

    //Define Joystick ports
    private Joystick controller;
    private Joystick leftStick;
    private Joystick rightStick;

    //Define Speed controllers
    private RobotDrive driveBase;
    private Talon shooter;
    private Talon agitator;
    private Talon ballCollector;
    private Talon gearCollector;
    private Talon hanger;

    //Define Solenoids
    private DoubleSolenoid driveBaseShifter;
    private DoubleSolenoid ballCollectorPivot;
    private DoubleSolenoid gearClaw;
    private DoubleSolenoid gearPivot;

    //Define encoder
    private Encoder driveEncoderLeft;
    private Encoder driveEncoderRight;
    private Encoder shooterEncoder;
    private Encoder hangLimit;

    //Define Sensors
    private DigitalInput gearLimit;

    //Define booleans
    public static boolean lockControls = false;

    //Define network table grip communicator
    private TT_GRIP_Communicator gripCommunicator;

    //Define PIDs
    private final double shooter_P = 0;
    private final double shooter_I = 0;
    private final double shooter_D = 0;

    private PIDController shooterPID;

    @Override
    public void robotInit() {
        //Declare joystick
        controller = new Joystick(0);
        leftStick = new Joystick(1);
        rightStick = new Joystick(2);

        //Declare Speed controllers
        driveBase = new RobotDrive(PWM_PORT_0, PWM_PORT_1, PWM_PORT_2, PWM_PORT_3);
        shooter = new Talon(PWM_PORT_4);
        agitator = new Talon(PWM_PORT_5);
        ballCollector = new Talon(PWM_PORT_6);
        gearCollector = new Talon(PWM_PORT_7);
        hanger = new Talon(PWM_PORT_8);

        //Declare Solenoids
        driveBaseShifter = new DoubleSolenoid(PCM_PORT_0, PCM_PORT_1);
        ballCollectorPivot = new DoubleSolenoid(PCM_PORT_2, PCM_PORT_3);
        gearClaw = new DoubleSolenoid(PCM_PORT_4, PCM_PORT_5);
        gearPivot = new DoubleSolenoid(PCM_PORT_6, PCM_PORT_7);

        //Declare encoder
        driveEncoderLeft = new Encoder(DIO_PORT_0, DIO_PORT_1);
        driveEncoderRight = new Encoder(DIO_PORT_2, DIO_PORT_3);
        shooterEncoder = new Encoder(DIO_PORT_4, DIO_PORT_5);
        hangLimit = new Encoder(DIO_PORT_6, DIO_PORT_7);

        //Declare Sensors
        gearLimit = new DigitalInput(DIO_PORT_5);

        gripCommunicator = new TT_GRIP_Communicator(NetworkTable.getTable(GRIP_TABLE_NAME));

        shooterPID = new PIDController(shooter_P, shooter_I, shooter_D, shooterEncoder, shooter, 1);

    }

    @Override
    public void teleopInit() {

    }

    @Override
    public void teleopPeriodic() {
        //Subsystem 1, Drivebase
        TT_Drive.drive(leftStick, rightStick, driveBase);
        TT_Drive.shifter(driveEncoderLeft, driveEncoderRight, driveBaseShifter);
        TT_Hanger.hang(controller, hanger, hangLimit);
        TT_Shooter.shoot(controller, agitator, shooterPID);
    }
}
