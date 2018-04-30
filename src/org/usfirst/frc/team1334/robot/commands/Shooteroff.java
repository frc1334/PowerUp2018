package org.usfirst.frc.team1334.robot.commands;

import org.usfirst.frc.team1334.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Shooteroff extends Command {
	double endTime,startTime;
    public Shooteroff() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.ShooterSubsystem.idle(false,false);
    	endTime = System.currentTimeMillis();
    	startTime = System.currentTimeMillis();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	endTime = System.currentTimeMillis();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(endTime-startTime) >20;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
