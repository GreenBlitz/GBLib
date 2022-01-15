package edu.greenblitz.gblib.hid.virtualHid;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;

import java.util.HashMap;

public class VirtualJoystick {

    private HashMap<Double, HashMap<String, Double>> myFollowDriverData;
    private VirtualHID ourHID;

    private JoystickButton A;
    private JoystickButton B;
    private JoystickButton X;
    private JoystickButton Y;
    private JoystickButton L1;
    private JoystickButton R1;
    private JoystickButton START;
    private JoystickButton BACK;
    private JoystickButton L3;
    private JoystickButton R3;
    private POVButton POV_UP;
    private POVButton POV_RIGHT;
    private POVButton POV_DOWN;
    private POVButton POV_LEFT;

    public VirtualJoystick(VirtualHID ourHID, HashMap<Double, HashMap<String, Double>> myFollowDriverData) {
        this.ourHID = ourHID;
        this.myFollowDriverData = myFollowDriverData;
        A = new JoystickButton(ourHID, 1);
        B = new JoystickButton(ourHID, 2);
        X = new JoystickButton(ourHID, 3);
        Y = new JoystickButton(ourHID, 4);
        L1 = new JoystickButton(ourHID, 5);
        R1 = new JoystickButton(ourHID, 6);
        START = new JoystickButton(ourHID, 7);
        BACK = new JoystickButton(ourHID, 8);
        L3 = new JoystickButton(ourHID, 9);
        R3 = new JoystickButton(ourHID, 10);
        POV_UP = new POVButton(ourHID, 0);
        POV_RIGHT = new POVButton(ourHID, 90);
        POV_DOWN = new POVButton(ourHID, 180);
        POV_LEFT = new POVButton(ourHID, 270);
    }


}
