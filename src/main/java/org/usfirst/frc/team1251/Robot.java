package org.usfirst.frc.team1251;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * Created by Eric Engelhart on 1/27/2017.
 */

public class Robot extends IterativeRobot {
    //Define Joystick inputs
    private static final int CONTROller_LEFT_BUMPER = 5;
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

    //Define Joystick ports
    private Joystick controller;
    private Joystick left;
    private Joystick right;

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
    private Encoder driveEncoder;
    private Encoder shooterEncoder;

    //Define Sensors
    private DigitalInput hangLLimit;
    private DigitalInput gearLimit;

    @Override
    public void robotInit() {
        //Declare joystick
        controller = new Joystick(0);
        left = new Joystick(1);
        right = new Joystick(2);

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
        driveEncoder = new Encoder(DIO_PORT_0, DIO_PORT_1);
        shooterEncoder = new Encoder(DIO_PORT_2, DIO_PORT_3);

        //Declare Sensors
        hangLLimit = new DigitalInput(DIO_PORT_4);
        gearLimit = new DigitalInput(DIO_PORT_5);

    }

    @Override
    public void autonomousInit(){

    }

    @Override
    public void autonomousPeriodic(){

    }

    @Override
    public void teleopInit() {

    }

    @Override
    public void teleopPeriodic() {
        //Subsystem 1, Drivebase
        TT_Drive.drive(left, right, driveBase);

        if (controller.getRawButton(CONTROLLER_RIGHT_BUMPER)) {
            driveBaseShifter.set(DoubleSolenoid.Value.kForward);
        }
        else {
            driveBaseShifter.set(DoubleSolenoid.Value.kReverse);
        }


    }
}
