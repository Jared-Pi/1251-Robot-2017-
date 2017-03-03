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

    public TT_DoubleTalonPID(Talon talon1, Talon talon2) {
        this.talon1 = talon1;
        this.talon2 = talon2;
    }

    @Override
    public void pidWrite(double output) {
        talon1.set(output);
        talon2.set(output);
    }
}
