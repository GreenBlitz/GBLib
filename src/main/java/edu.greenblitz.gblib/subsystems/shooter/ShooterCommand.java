package edu.greenblitz.gblib.subsystems.shooter;

import edu.greenblitz.gblib.base.GBCommand;

public abstract class ShooterCommand extends GBCommand {
	protected Shooter shooter;
	
	public ShooterCommand() {
		super(Shooter.getInstance());
		shooter = Shooter.getInstance();
	}
}
