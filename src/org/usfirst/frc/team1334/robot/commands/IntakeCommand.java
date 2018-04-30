package org.usfirst.frc.team1334.robot.commands;

import org.usfirst.frc.team1334.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeCommand extends Command {
	public int spin;
	public double start, end, Time;
    public IntakeCommand(int direction, double time) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	spin = direction;
    	Time = time;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.ElevatorSubsystem.Intake1.set(ControlMode.PercentOutput, 0);
    	Robot.ElevatorSubsystem.Intake2.set(ControlMode.Follower, 4);
    	start = System.currentTimeMillis();
    	end = System.currentTimeMillis();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	switch (spin)
    	{
    		case 0://inward
      		Robot.ShooterSubsystem.intake(true, false);
      		break;
    		case 1://outward
    		Robot.ShooterSubsystem.intake(false, true);
    		break;
    	}
    	end = System.currentTimeMillis();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return end-start>Time;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.ShooterSubsystem.idle(false, false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
