package org.usfirst.frc.team1251;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

import static org.usfirst.frc.team1251.Robot.CONTROLLER_A_BUTTON;
import static org.usfirst.frc.team1251.Robot.CONTROLLER_RIGHT_BUMPER;
import static org.usfirst.frc.team1251.Robot.CONTROLLER_RIGHT_TRIGGER;

/**
 * Created by Jared on 2/11/2017.  Edited by Nicholas Salazar on 2/21/17
 */


public class TT_GearCollector {
    public static boolean isDown = false;
    public static void collectGearFloor(Joystick controller, Talon motor, Talon pivot, DoubleSolenoid claw, DigitalInput sensor, Potentiometer pivotSensor) {
        if (controller.getPOV() == 90) {
            isDown = true;
            if (pivotSensor.get() < 90) {
                pivot.set(0.3);
            }
            else {
                pivot.set(0);
            }
        }
        else if (controller.getPOV() == 270) {
            isDown = false;
            if (pivotSensor.get() < 0) {
                pivot.set(-0.3);
            }
            else {
                pivot.set(0);
            }
        }

        if (controller.getRawButton(CONTROLLER_A_BUTTON)) {
            claw.set(DoubleSolenoid.Value.kReverse);
        }
        else {
            claw.set(DoubleSolenoid.Value.kForward);
        }

        if (controller.getRawButton(CONTROLLER_RIGHT_BUMPER)) {
            if (!sensor.get()) {
                motor.set(1);
            }
            else {
                motor.set(0);
            }
        }
        else if (controller.getRawButton(CONTROLLER_RIGHT_TRIGGER)) {
            motor.set(-1);
        }
        else {
            motor.set(0);
        }
    }
}
