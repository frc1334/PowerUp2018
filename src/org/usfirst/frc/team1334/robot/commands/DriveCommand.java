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
		//Subsystems.ELEVATOR_SUBSYSTEM.resetElevator([bottom limit switch]);
		//Subsystems.ELEVATOR_SUBSYSTEM.elevator(OI.OelevateControl(), OI.OelevateBrake(), [top limit switch], [bottom limit switch], [encoder speed]);
		
		// This is the Elevator Brake Test for the Solenoids (arguments are temprorary because im lazy and didnt want to make buttons and stuff)
		// i would swear in these but im apparently not allowed to ;-;
		Subsystems.ELEVATOR_SUBSYSTEM.elevTest(OI.OgetIntakeGO(), OI.OgetIntakeReverse());
<<<<<<< HEAD
		Subsystems.CLIMB_SUBSYSTEM.solenoidInit(OI.DclimbEngage());
		Subsystems.CLIMB_SUBSYSTEM.winch(OI.DclimbSpinnyboiL(), OI.DclimbSpinnyboiR());
=======
>>>>>>> 95b598d579d0abf59d5856403ed5a8a005465ace
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