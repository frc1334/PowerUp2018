package org.usfirst.frc.team1334.robot.commands;

import org.usfirst.frc.team1334.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class AutoShooterCommand extends Command{
	
	int angle;
	
	public AutoShooterCommand(int angleInput){
		angle = angleInput;
		requires(Robot.ShooterSubsystem);
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
