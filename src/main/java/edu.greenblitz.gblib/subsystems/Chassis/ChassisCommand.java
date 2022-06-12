package edu.greenblitz.gblib.subsystems.Chassis;

import edu.greenblitz.gblib.base.GBCommand;

public abstract class ChassisCommand extends GBCommand {
	
	protected Chassis chassis;
	
	public ChassisCommand() {
		super(Chassis.getInstance());
		chassis = Chassis.getInstance();
	}
	
}
