package org.usfirst.frc.team1251;

import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * Created by Eric Engelhart on 1/27/2017.
 */

public class Robot extends IterativeRobot {
    //Define Joystick input
    private static final int LOGITECH_LEFT_JOYSTICK = 0;
    private static final int LOGITECH_RIGHT_JOYSTICK = 1;
    private static final int LOGITECH_X_BUTTON = 1;
    private static final int LOGITECH_A_BUTTON = 2;
    private static final int LOGITECH_B_BUTTON = 3;
    private static final int LOGITECH_Y_BUTTON = 4;
    private static final int LOGITECH_LEFT_TRIGGER = 7;
    private static final int LOGITECH_RIGHT_TRIGGER = 8;

    //Define Joystick port
    private Joystick controller;

    //Define PWM ports
    private static final int PWM_PORT_0 = 0;
    private static final int PWM_PORT_1 = 1;
    private static final int PWM_PORT_2 = 2;
    private static final int PWM_PORT_3 = 3;

    private static final int ENC_PORT_0 = 0;
    private static final int ENC_PORT_1 = 1;

    //Define Speed controllers
    private Talon cim0;
    private Talon cim1;
    private Talon cim2;
    private Talon cim3;

    //Define encoder
    private Encoder encoder;

    public void robotInit() {
        //Declare joystick
        controller = new Joytick(0);

        //Declare Speed controllers
        cim0 = new Talon(PWM_PORT_0);
        cim1 = new Talon(PWM_PORT_1);
        cim2 = new Talon(PWM_PORT_2);
        cim3 = new Talon(PWM_PORT_3);

        //Declare encoder
        encoder = new Encoder(ENC_PORT_0, ENC_PORT_1)

    }
    public void teleopInit() {

    }
    public void teleopPeriodic() {
        if(controller.getRawButton(LOGITECH_A_BUTTON)) {
            cim1.set(0.7);
        }
        else (
            cim1.set(0);
        )
    }
}
