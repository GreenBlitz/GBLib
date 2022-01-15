package edu.greenblitz.gblib.hid.virtualHid;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class VirtualButton extends JoystickButton {
    //for now no use

    /**
     * Creates a joystick button for triggering commands.
     *
     * @param joystick     The GenericHID object that has the button (e.g. Joystick, KinectStick,
     *                     etc)
     * @param buttonNumber The button number (see {@link GenericHID#getRawButton(int) }
     */
    public VirtualButton(GenericHID joystick, int buttonNumber) {
        super(joystick, buttonNumber);
    }
}
