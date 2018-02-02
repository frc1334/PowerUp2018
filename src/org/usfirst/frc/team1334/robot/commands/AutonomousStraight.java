package org.usfirst.frc.team1334.robot.commands;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonomousStraight extends CommandGroup {

    public AutonomousStraight() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	addSequential(new AutoDriveCommand(5000));
    }
}
