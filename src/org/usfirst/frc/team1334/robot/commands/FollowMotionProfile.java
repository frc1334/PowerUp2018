package org.usfirst.frc.team1334.robot.commands;

import java.io.IOException;

import org.usfirst.frc.team1334.robot.ProfileHandler;
import org.usfirst.frc.team1334.robot.Robot;
import org.usfirst.frc.team1334.robot.subsystems.DriveSubsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class FollowMotionProfile extends Command {
	ProfileHandler H;
	String lfp, rfp, pn;	
	double end,start,Lerror,Rerror;
    public FollowMotionProfile(String LFP, String RFP, String ProfileName) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.DriveSubsystem);
    	lfp = LFP;
    	rfp = RFP;
    	pn = ProfileName;
    	DriveSubsystem.gShift.set(Value.kForward);
		Robot.DriveSubsystem.Left1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Robot.DriveSubsystem.kPIDLoopIdx,
				Robot.DriveSubsystem.kTimeoutMs);
		Robot.DriveSubsystem.Right1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Robot.DriveSubsystem.kPIDLoopIdx,
				Robot.DriveSubsystem.kTimeoutMs);
		Robot.DriveSubsystem.Left1.setSensorPhase(false);
		Robot.DriveSubsystem.Right1.setSensorPhase(false);
    	Robot.DriveSubsystem.Left1.configNominalOutputForward(0, Robot.DriveSubsystem.kTimeoutMs);
		Robot.DriveSubsystem.Left1.configNominalOutputReverse(0, Robot.DriveSubsystem.kTimeoutMs);
		Robot.DriveSubsystem.Left1.configPeakOutputForward(1, Robot.DriveSubsystem.kTimeoutMs);
		Robot.DriveSubsystem.Left1.configPeakOutputReverse(-1, Robot.DriveSubsystem.kTimeoutMs);
		Robot.DriveSubsystem.Right1.configNominalOutputForward(0, Robot.DriveSubsystem.kTimeoutMs);
		Robot.DriveSubsystem.Right1.configNominalOutputReverse(0, Robot.DriveSubsystem.kTimeoutMs);
		Robot.DriveSubsystem.Right1.configPeakOutputForward(1, Robot.DriveSubsystem.kTimeoutMs);
		Robot.DriveSubsystem.Right1.configPeakOutputReverse(-1, Robot.DriveSubsystem.kTimeoutMs);
    	Robot.DriveSubsystem.Left1.config_kF(Robot.DriveSubsystem.kPIDLoopIdx, 0.2, Robot.DriveSubsystem.kTimeoutMs);
		Robot.DriveSubsystem.Left1.config_kP(Robot.DriveSubsystem.kPIDLoopIdx, 0.45, Robot.DriveSubsystem.kTimeoutMs);
		Robot.DriveSubsystem.Left1.config_kI(Robot.DriveSubsystem.kPIDLoopIdx, 0.0, Robot.DriveSubsystem.kTimeoutMs);
		Robot.DriveSubsystem.Left1.config_kD(Robot.DriveSubsystem.kPIDLoopIdx, 0.02, Robot.DriveSubsystem.kTimeoutMs);
		Robot.DriveSubsystem.Left1.configMotionCruiseVelocity(2700, Robot.DriveSubsystem.kTimeoutMs);
		Robot.DriveSubsystem.Left1.configMotionAcceleration(2500, Robot.DriveSubsystem.kTimeoutMs);//Do not put the acceleration above 2500 units/s^2 otherwise the robot veers
		Robot.DriveSubsystem.Right1.config_kF(Robot.DriveSubsystem.kPIDLoopIdx, 0.2, Robot.DriveSubsystem.kTimeoutMs);
		Robot.DriveSubsystem.Right1.config_kP(Robot.DriveSubsystem.kPIDLoopIdx, 0.45, Robot.DriveSubsystem.kTimeoutMs);
		Robot.DriveSubsystem.Right1.config_kI(Robot.DriveSubsystem.kPIDLoopIdx, 0.0, Robot.DriveSubsystem.kTimeoutMs);
		Robot.DriveSubsystem.Right1.config_kD(Robot.DriveSubsystem.kPIDLoopIdx, 0.02, Robot.DriveSubsystem.kTimeoutMs);
		Robot.DriveSubsystem.Right1.configMotionCruiseVelocity(2700, Robot.DriveSubsystem.kTimeoutMs);
		Robot.DriveSubsystem.Right1.configMotionAcceleration(2500, Robot.DriveSubsystem.kTimeoutMs);//do not put the acceleration above 2500 units/s^2 otherwise the robot veers
		Robot.DriveSubsystem.Left2.set(ControlMode.Follower, 0);
		Robot.DriveSubsystem.Right2.set(ControlMode.Follower, 2);
		Robot.DriveSubsystem.Left1.setInverted(true);
		Robot.DriveSubsystem.Left2.setInverted(true);
		
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	try{
    		H = new ProfileHandler(lfp,rfp);
    	}catch(IOException E){
    		System.out.println("Profile " + pn + " Failed to Load, File not Found");
    	}
    	end = System.currentTimeMillis();
    	start = System.currentTimeMillis();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	try {
			H.HandleMP();
		} catch (InterruptedException e) {
			System.out.println("Start buffer interrupted");
		}
    	Robot.DriveSubsystem.Left1.set(ControlMode.MotionProfile, H.Set.value);
    	Robot.DriveSubsystem.Right1.set(ControlMode.MotionProfile, H.Set.value);
    	Robot.leftDistance = Robot.DriveSubsystem.Left1.getActiveTrajectoryPosition();
    	Robot.rightDistance= Robot.DriveSubsystem.Right1.getActiveTrajectoryPosition();
    	Lerror = Robot.DriveSubsystem.Left1.getActiveTrajectoryPosition()- Robot.DriveSubsystem.Left1.getSelectedSensorPosition(0);
    	Rerror = Robot.DriveSubsystem.Right1.getActiveTrajectoryPosition()- Robot.DriveSubsystem.Right1.getSelectedSensorPosition(0);
    	if(Math.abs(Lerror)<200 && Math.abs(Rerror)<200){
    		end = System.currentTimeMillis();
    	}else{
    		start = System.currentTimeMillis();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return H.Lstatus.isLast && H.Rstatus.isLast && end-start>200;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.DriveSubsystem.Left1.set(ControlMode.PercentOutput, 0);
    	Robot.DriveSubsystem.Right1.set(ControlMode.PercentOutput, 0);
    	H.Reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	H.Reset();
    }
}
