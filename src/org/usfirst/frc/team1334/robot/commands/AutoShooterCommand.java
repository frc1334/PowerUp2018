package org.usfirst.frc.team1334.robot.commands;

import org.usfirst.frc.team1334.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class AutoShooterCommand extends Command{
	double startTime,endTime;
	boolean isHigh = false;
	
	public AutoShooterCommand(boolean IsHigh){
		requires(Robot.ShooterSubsystem);
		isHigh = IsHigh;
	}
	@Override
	protected void initialize(){
		if(isHigh){
			Robot.ShooterSubsystem.highGoal(1000);
		}else{
			Robot.ShooterSubsystem.lowGoal();
		}
		startTime = System.currentTimeMillis();
		endTime = System.currentTimeMillis();
	}
	protected void execute(){
		endTime = System.currentTimeMillis();
	}
	@Override
	protected boolean isFinished() {
		if(endTime-startTime > 500){
			return true;
		}
		return false;
	}
	protected void end(){
		Robot.ShooterSubsystem.idle(false, false);
	}
	protected void interrupted(){
		
	}
}
