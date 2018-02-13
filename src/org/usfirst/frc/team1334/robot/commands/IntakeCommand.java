package org.usfirst.frc.team1334.robot.commands;

import org.usfirst.frc.team1334.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeCommand extends Command {
	public int spin;
	
    public IntakeCommand(int direction) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	spin = direction;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.ElevatorSubsystem.Intake1.set(ControlMode.PercentOutput, 0);
    	Robot.ElevatorSubsystem.Intake2.set(ControlMode.Follower, 4);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	switch (spin)
    	{
    		case 0:
      		Robot.ElevatorSubsystem.intake(true, false);
      		break;
    		case 1:
    		Robot.ElevatorSubsystem.intake(false, true);
    		break;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
