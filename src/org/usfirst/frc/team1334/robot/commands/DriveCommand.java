package org.usfirst.frc.team1334.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team1334.robot.OI;
import org.usfirst.frc.team1334.robot.util.Subsystems;

/**
 *
 */
public class DriveCommand extends Command {
	public DriveCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(Subsystems.DRIVE_SUBSYSTEM);
		requires(Subsystems.ELEVATOR_SUBSYSTEM);
		requires(Subsystems.CLIMB_SUBSYSTEM);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Subsystems.DRIVE_SUBSYSTEM.ArcadeDrive(OI.DgetDriverSpeed(), OI.DgetSteer());
		Subsystems.DRIVE_SUBSYSTEM.shiftGear(OI.DgetHighGear(),OI.DgetLowGear());
		Subsystems.ELEVATOR_SUBSYSTEM.intake(OI.OgetIntakeGO(), OI.OgetIntakeReverse());
		
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
