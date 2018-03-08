package org.usfirst.frc.team1334.robot.commands;

import org.usfirst.frc.team1334.robot.OI;
import org.usfirst.frc.team1334.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveCommand extends Command {
	public DriveCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.DriveSubsystem);
		requires(Robot.ShooterSubsystem);
		requires(Robot.ClimberSubsystem);
	}
	
	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.DriveSubsystem.Left1.setInverted(false);
		Robot.DriveSubsystem.Left2.setInverted(false);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Robot.DriveSubsystem.ArcadeDrive(OI.DgetDriverSpeed(), OI.DgetSteer());
		System.out.println(Robot.DriveSubsystem.Right1.getSelectedSensorVelocity(0)+ "LeftV");
		Robot.DriveSubsystem.shiftGear(OI.DgetHighGear(),OI.DgetLowGear());
		Robot.ShooterSubsystem.intake(OI.OgetIntakeGO(), OI.OgetIntakeReverse());
		//Robot.ElevatorSubsystem.resetElevator(Robot.ElevatorSubsystem.LowWarn.get());
		//Robot.ElevatorSubsystem.topElevator(Robot.ElevatorSubsystem.HighWarn.get());
		
		//	Robot.ElevatorSubsystem.LowWarn.get(), Robot.ElevatorSubsystem.height.get());
		
		//Robot.ElevatorSubsystem.elevator(OI.OelevateControl(),OI.OelevateBrake());
		// This is the Elevator Brake Test for the Solenoids (arguments are temprorary because im lazy and didnt want to make buttons and stuff)
		// i would swear in these but im apparently not allowed to ;-;
		//Robot.ElevatorSubsystem.elevTest(OI.OgetIntakeGO(), OI.OgetIntakeReverse());
		
		Robot.ClimberSubsystem.solenoidInit(OI.DclimbEngage());
		Robot.ClimberSubsystem.winch(OI.DclimbSpinnyboiL(), OI.DclimbSpinnyboiR());

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