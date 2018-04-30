package org.usfirst.frc.team1334.robot.commands;

import org.usfirst.frc.team1334.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class AutoShooterCommand extends Command{
	double startTime,endTime;
	boolean isHigh = false;
	double duration;
	public AutoShooterCommand(boolean IsHigh, double Duration){
		duration = Duration;
		isHigh = IsHigh;
		// false = Low Goal, true = High Goal
	}
	@Override
	protected void initialize(){
		
		startTime = System.currentTimeMillis();
		endTime = System.currentTimeMillis();
	}
	protected void execute(){
		endTime = System.currentTimeMillis();
		if(endTime-startTime > duration && endTime-startTime < duration+500){
			if(isHigh){
				Robot.ShooterSubsystem.highGoal(1000);
			}else{
				Robot.ShooterSubsystem.lowGoal();
			}
		}
		if(endTime-startTime > duration+500){
			Robot.ShooterSubsystem.idle(false, false);
		}
	}
	@Override
	protected boolean isFinished() {
		return endTime-startTime>duration+500;
	}
	protected void end(){
		System.out.println("shootercommandend");
		Robot.ShooterSubsystem.idle(false, false);
	}
	protected void interrupted(){
		
	}
}
