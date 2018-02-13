package org.usfirst.frc.team1334.robot.commands;

import org.usfirst.frc.team1334.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ElevatorCommand extends Command {
	int elevPosition;
	
    public ElevatorCommand(int position) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	elevPosition = position;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.ElevatorSubsystem.Elevator1.set(ControlMode.PercentOutput, 0);
    	Robot.ElevatorSubsystem.Elevator2.set(ControlMode.Follower, 6);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	switch (elevPosition)
    	{
    		case 0:
    			
    			break;
    			
    		case 1:
    			
    			break;
    			
    		case 2:
    			
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
