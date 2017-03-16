package org.usfirst.frc.team1251;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Talon;

/**
 * Created by Eric Engelhart on 3/3/2017.
 * This class allows you to use 2 talons for a single PID loop
 */
public class TT_DoubleTalonPID implements PIDOutput {

    private Talon talon1;
    private Talon talon2;

    private boolean inverted = false;

    public TT_DoubleTalonPID(Talon talon1, Talon talon2, boolean inverted) {
        this.talon1 = talon1;
        this.talon2 = talon2;
        this.inverted = true;
    }

    @Override
    public void pidWrite(double output) {
        if (inverted) {
            talon1.set(-output);
            talon2.set(-output);
        } else {
            talon1.set(output);
            talon2.set(output);
        }

    }
}
