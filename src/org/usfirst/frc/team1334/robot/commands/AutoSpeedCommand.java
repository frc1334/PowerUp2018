package org.usfirst.frc.team1334.robot.commands;

import org.usfirst.frc.team1334.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoSpeedCommand extends Command {
	
	double startTime,endTime,speed,time;
	
    public AutoSpeedCommand(double Speed, double Time) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	speed = Speed;
    	time = Time;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	startTime = System.currentTimeMillis();
    	endTime = System.currentTimeMillis();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.DriveSubsystem.Left1.set(ControlMode.PercentOutput, speed);
    	Robot.DriveSubsystem.Left2.set(ControlMode.Follower, 0);
    	Robot.DriveSubsystem.Right1.set(ControlMode.PercentOutput, speed);
    	Robot.DriveSubsystem.Right2.set(ControlMode.Follower, 2);
    	endTime = System.currentTimeMillis();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if(endTime-startTime >= time){
        	return true;
        }
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.DriveSubsystem.Left1.set(ControlMode.PercentOutput,0);
    	Robot.DriveSubsystem.Right1.set(ControlMode.PercentOutput,0);
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
