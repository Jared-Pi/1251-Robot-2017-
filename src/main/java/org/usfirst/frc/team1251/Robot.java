package org.usfirst.frc.team1251;

import edu.wpi.first.wpilibj.*;

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
    private Joystick stick1;
    private Joystick stick2;

    //Define Speed controllers
    private Talon leftDrive1;
    private Talon leftDrive2;
    private Talon rightDrive1;
    private Talon rightDrive2;
    private Talon shooter;
    private Talon agitator;
    private Talon ballCollector;
    private Talon gearCollector;
    private Talon hanger;

    //Define Solenoids
    private DoubleSolenoid driveBase;
    private DoubleSolenoid ballCollectorPivot;
    private DoubleSolenoid gearClaw;
    private DoubleSolenoid gearPivot;

    //Define encoder
    private Encoder driveEncoder;
    private Encoder shooterEncoder;

    //Define Sensors
    private DigitalInput hangLLimit;
    private DigitalInput gearLimit;


    public void robotInit() {

    }
    public void teleopInit() {

    }
    public void teleopPeriodic() {

    }
}
