package org.usfirst.frc.team1251;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

import static org.usfirst.frc.team1251.Robot.*;

/**
 * Created by Jared on 2/11/2017.  Edited by Nicholas Salazar on 2/21/17
 */


public class TT_GearCollector {
    public static boolean isDown = false;

    public static void collectGearFloor(Joystick controller, SpeedController collectionMotor, SpeedController pivotMotor, DoubleSolenoid claw, Potentiometer pivotSensor) {
        if ( controller.getRawAxis(CONTROLLER_LEFT_AXIS) > 0.1) {
            pivotMotor.set(0.35);
        } else if ( controller.getRawAxis(CONTROLLER_LEFT_AXIS) < -0.1) {
            pivotMotor.set(-0.35);
        } else {
            pivotMotor.set(0.0);
        }

        if ( controller.getRawButton(CONTROLLER_A_BUTTON)) {
            claw.set(DoubleSolenoid.Value.kReverse);
        } else {
            claw.set(DoubleSolenoid.Value.kForward);
        }

        if ( controller.getRawButton(CONTROLLER_RIGHT_BUMPER)) {
            collectionMotor.set(1);
        }
        else if ( controller.getRawButton(CONTROLLER_RIGHT_TRIGGER)) {
            collectionMotor.set(-1);
        }
        else {
            collectionMotor.set(0);
        }
    }

    public static void collectGearFloor2(Joystick controller, SpeedController collectionMotor, SpeedController pivotMotor, DoubleSolenoid claw, Potentiometer pivotSensor) {
        if ( controller.getRawButton(CONTROLLER_LEFT_BUMPER)) {
            pivotMotor.set(0.35);
        } else if ( controller.getRawButton(CONTROLLER_LEFT_TRIGGER)) {
            pivotMotor.set(-0.35);
        } else {
            pivotMotor.set(0.0);
        }

        if ( controller.getRawButton(CONTROLLER_RIGHT_TRIGGER)) {
            claw.set(DoubleSolenoid.Value.kReverse);
        } else {
            claw.set(DoubleSolenoid.Value.kForward);
        }

        if ( controller.getPOV()==180) {
            collectionMotor.set(1);
        }
        else if ( controller.getPOV()==0) {
            collectionMotor.set(-1);
        }
        else if (controller.getRawButton(CONTROLLER_A_BUTTON)) {
            collectionMotor.set(0);
        }
    }
}
