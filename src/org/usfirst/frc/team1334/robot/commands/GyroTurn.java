package org.usfirst.frc.team1334.robot.commands;

import org.usfirst.frc.team1334.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GyroTurn extends Command {

	double Angle;
	double Start,End;
    public GyroTurn(double angle) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.DriveSubsystem);
    	Angle = angle;
    }

    // Called just before this Command runs the first time
    protected void initialize(){
    	Robot.DriveSubsystem.Left1.setInverted(false);
    	Robot.DriveSubsystem.Left2.setInverted(false);
    	Start = System.currentTimeMillis();
    	End = System.currentTimeMillis();
    	Robot.DriveSubsystem.setSetpoint(Robot.DriveSubsystem.GyroDrive(Angle));
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	Robot.DriveSubsystem.usePIDOutput(Robot.DriveSubsystem.getPIDController().get());
    	Robot.DriveSubsystem.ArcadeDrive(0, Robot.DriveSubsystem.rotateToAngleRate);
    	if(Math.abs(Robot.DriveSubsystem.getPIDController().getError()) > 1 ){
    		Start = System.currentTimeMillis();
    	}else{
    		End = System.currentTimeMillis();
    	}
    }
    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if(End-Start > 200){
        	System.out.println("finished");
        	return true;
        }
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
