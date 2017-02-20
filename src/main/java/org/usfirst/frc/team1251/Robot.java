package org.usfirst.frc.team1251;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;

/**
 * Created by Eric Engelhart on 1/27/2017.
 */
public class Robot extends IterativeRobot {
    // only joystick button
    private static final int LOGITECH_A_BUTTON = 2;

    // joystick
    private Joystick logitech;

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

    // create talons
    private Talon talon1;
    private Talon talon2;
    private Talon talon3;
    private Talon talon4;
    private Talon talon5;
    private Talon talon6;
    private Talon talon7;
    private Talon talon8;
    private Talon talon9;
    private Talon talon10;

    public void robotInit(){
        talon1 = new Talon(PWM_PORT_0);
        talon2 = new Talon(PWM_PORT_1);
        talon3 = new Talon(PWM_PORT_2);
        talon4 = new Talon(PWM_PORT_3);
        talon5 = new Talon(PWM_PORT_4);
        talon6 = new Talon(PWM_PORT_5);
        talon7 = new Talon(PWM_PORT_6);
        talon8 = new Talon(PWM_PORT_7);
        talon9 = new Talon(PWM_PORT_8);
        talon10 = new Talon(PWM_PORT_9);

        logitech = new Joystick(0);

    }

    public void autonomousInit(){

    }

    public void teleopInit(){

    }

    public void teleopPeriodic(){
        if (logitech.getRawButton(LOGITECH_A_BUTTON)) {
            talon1.set(1);
            talon2.set(1);
            talon3.set(1);
            talon4.set(1);
            talon5.set(1);
            talon6.set(1);
            talon7.set(1);
            talon8.set(1);
            talon9.set(1);
            talon10.set(1);
        } else {
            talon1.set(0);
            talon2.set(0);
            talon3.set(0);
            talon4.set(0);
            talon5.set(0);
            talon6.set(0);
            talon7.set(0);
            talon8.set(0);
            talon9.set(0);
            talon10.set(0);
        }

    }

}