package edu.greenblitz.gblib.command;

import edu.greenblitz.gblib.command.base.GBCommand;
import edu.wpi.first.wpilibj.command.Subsystem;

public class WaitUntilFree extends GBCommand {

    private Subsystem[] systems;

    public WaitUntilFree(Subsystem... subs){
        systems = subs;
    }

    @Override
    protected boolean isFinished() {
        for (Subsystem s : systems){
            if (!s.getCurrentCommandName().equals(s.getDefaultCommandName()))
                return false;
        }
        return true;
    }
}
