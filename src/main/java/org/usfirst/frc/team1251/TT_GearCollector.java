package org.usfirst.frc.team1251;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;

/**
 * Created by Jared on 2/11/2017.  Edited by Nicholas Salazar on 2/21/17
 */


public class TT_GearCollector {
    //First attempt
    public static void collectGearFloor(Joystick controller, Talon motor, DoubleSolenoid pivot, DoubleSolenoid claw, DigitalInput sensor) {
        if (controller.getRawButton(7)) { //Check how the controller is utilised, e.g. button or axis; This will be for from floor
            pivot.set(DoubleSolenoid.Value.kForward);
            claw.set(DoubleSolenoid.Value.kForward);
            if (!sensor.get()) {
                motor.set(-1);
            }
            claw.set(DoubleSolenoid.Value.kReverse);
            pivot.set(DoubleSolenoid.Value.kReverse);
        }
    }

    public static void collectGearPlayer(Joystick controller, Talon motor, DoubleSolenoid pivot, DoubleSolenoid claw, DigitalInput sensor) {
        if (controller.getRawButton(9)) {

        }
    }

}
