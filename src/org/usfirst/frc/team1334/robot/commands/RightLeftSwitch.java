package org.usfirst.frc.team1334.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RightLeftSwitch extends CommandGroup{
	
	public RightLeftSwitch(){
		addSequential(new AutoDriveCommand((int) 206.82));
    	addSequential(new GyroTurn(-90));
    	addSequential(new AutoDriveCommand(200));
    	addSequential(new GyroTurn(-90));
    	addParallel(new AutoDriveCommand((int) 58.30));
    	addSequential(new ElevatorCommand(1));
    	addSequential(new GyroTurn(-90));
    	addSequential(new IntakeCommand(0));
	}
	
}
