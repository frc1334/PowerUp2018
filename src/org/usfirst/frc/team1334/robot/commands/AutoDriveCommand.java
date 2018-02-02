package org.usfirst.frc.team1334.robot.commands;

import org.usfirst.frc.team1334.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 * 
 */
public class AutoDriveCommand extends Command {
	int distance = 0;
	long startTime, endTime;
	boolean inRange;
	
    public AutoDriveCommand(int Distance) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	distance = Distance;
    	requires(Robot.DriveSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.DriveSubsystem.Left1.set(ControlMode.Position, distance);
    	Robot.DriveSubsystem.Right1.set(ControlMode.Position, distance);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	/*System.currentTimeMillis();
    	Robot.DriveSubsystem.Left1.getClosedLoopError(0);
    	*/
    	inRange = Math.abs(Robot.DriveSubsystem.Left1.getClosedLoopError(0)) <= 50;
    	if(inRange){endTime = System.currentTimeMillis();}
    	else {startTime = System.currentTimeMillis();}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (endTime - startTime > 500) { return true; }
        return false;
    }

    // Called once after isFinished returns true
    protected void end() { distance = 0; }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {}
}
