package org.usfirst.frc.team1251;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * Created by Eric Engelhart on 1/27/2017.
 */

public class Robot extends IterativeRobot {
    //Define Joystick input
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

    //Define Joystick port
    private Joystick controller;
    private Joystick stick1;
    private Joystick stick2;

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

    private static final int ENC_PORT_0 = 0;
    private static final int ENC_PORT_1 = 1;
    private static final int ENC_PORT_2 = 2;
    private static final int ENC_PORT_3 = 3;

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

    //Define pneumatics
    private Doublesolenoid driveBase;
    private Doublesolenoid ballCollector;
    private Doublesolenoid gearClaw;
    private Doublesolenoid gearPivot;

    //Define encoder
    private Encoder driveEncoder;
    private Encoder shooterEncoder;

    public void robotInit() {
        //Declare joystick
        controller = new Joystick(0);
        stick1 = new Joystick(1);
        stick2 = new Joystick(2);

        //Declare Speed controllers
        leftDrive1 = new Talon(PWM_PORT_0);
        leftDrive2 = new Talon(PWM_PORT_1);
        rightDrive1 = new Talon(PWM_PORT_2);
        rightDrive2 = new Talon(PWM_PORT_3);

        //Declare encoder
        encoder = new Encoder(ENC_PORT_0, ENC_PORT_1);

    }
    public void teleopInit() {

    }
    public void teleopPeriodic() {
        if(controller.getRawButton(CONTROLLER_RIGHT_BUMPER)) {
            cim1.set(-0.7);
        }
        else {
            cim1.set(0);
        }
    }
}
