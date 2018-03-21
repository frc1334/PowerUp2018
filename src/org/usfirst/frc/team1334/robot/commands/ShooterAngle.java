package org.usfirst.frc.team1334.robot.commands;

import org.usfirst.frc.team1334.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShooterAngle extends Command {
	boolean isInverted = false;//BE EXTREMELY CAREFUL WHEN INVERTING THE MOTORS AND ALWAYS TEST IN TELEOP FIRST
	boolean isOutOfPhase = true;
	int angle = 0;
	long startTime, endTime;
	boolean inRange;
	double ticks;
	int ticksperrev = 1024;
	double minimumvoltageup = 0.305;
	double minimumvoltagedown = -0.002;
	boolean isPID = true;
	
    public ShooterAngle(int Angle) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.ShooterSubsystem);
    	angle = Angle;
    	Robot.ShooterSubsystem.zeroed = false;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	while(!Robot.ShooterSubsystem.zeroed){
    		Robot.ShooterSubsystem.zeroShooterUpwards();
    		System.out.println(Robot.ShooterSubsystem.Shooter.getMotorOutputPercent());
    	}
    	endTime = System.currentTimeMillis();
    	startTime = System.currentTimeMillis();
    	Robot.ShooterSubsystem.Shooter.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Robot.DriveSubsystem.kPIDLoopIdx,
				Robot.DriveSubsystem.kTimeoutMs);
    	Robot.ShooterSubsystem.Shooter.setSensorPhase(isOutOfPhase);
    	Robot.ShooterSubsystem.Shooter.setInverted(isInverted);
    	Robot.ShooterSubsystem.Shooter.configNominalOutputForward(0, Robot.DriveSubsystem.kTimeoutMs);
		Robot.ShooterSubsystem.Shooter.configNominalOutputReverse(0, Robot.DriveSubsystem.kTimeoutMs);
		Robot.ShooterSubsystem.Shooter.configPeakOutputForward(0.6, Robot.DriveSubsystem.kTimeoutMs);
		Robot.ShooterSubsystem.Shooter.configPeakOutputReverse(-0.3, Robot.DriveSubsystem.kTimeoutMs);
		Robot.ShooterSubsystem.Shooter.configAllowableClosedloopError(10, 0, 10);
		Robot.ShooterSubsystem.Shooter.config_kF(Robot.DriveSubsystem.kPIDLoopIdx, 0.0, Robot.DriveSubsystem.kTimeoutMs);
		Robot.ShooterSubsystem.Shooter.config_kP(Robot.DriveSubsystem.kPIDLoopIdx, 8, Robot.DriveSubsystem.kTimeoutMs);
		Robot.ShooterSubsystem.Shooter.config_kI(Robot.DriveSubsystem.kPIDLoopIdx, 0.0, Robot.DriveSubsystem.kTimeoutMs);
		Robot.ShooterSubsystem.Shooter.config_kD(Robot.DriveSubsystem.kPIDLoopIdx, 0.0, Robot.DriveSubsystem.kTimeoutMs);
		int absolutePosition = Robot.ShooterSubsystem.Shooter.getSensorCollection().getPulseWidthPosition();
		absolutePosition &= 0xFFF;
		if (isInverted)
			absolutePosition *= -1;
		if (isOutOfPhase)
			absolutePosition *= -1;
		Robot.ShooterSubsystem.Shooter.setSelectedSensorPosition(absolutePosition, 0, 10);
		ticks = ticksperrev*1.0/360.0 * angle*-1.0;
		Robot.ShooterSubsystem.Shooter.set(ControlMode.Position, ticks);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println(ticks);
    	double motorout = Robot.ShooterSubsystem.Shooter.getMotorOutputPercent();
    	double error = ticks - Robot.ShooterSubsystem.Shooter.getSelectedSensorPosition(0);
    	if( motorout < minimumvoltageup && motorout > 0 || motorout > minimumvoltagedown && motorout < 0){
    		isPID = false;
    	}
    	if(isPID){
    		
    	}else{
    		if(Math.signum(error) == 1 && Math.abs(error) >= 5){
    			Robot.ShooterSubsystem.Shooter.set(ControlMode.PercentOutput, minimumvoltageup);
    			System.out.println("minimumupupup");
    		}else if(Math.signum(error) == -1 && Math.abs(error) >= 5){
    			Robot.ShooterSubsystem.Shooter.set(ControlMode.PercentOutput, minimumvoltagedown);
    			System.out.println("minimumdowndown");
    		}else{
    			Robot.ShooterSubsystem.Shooter.set(ControlMode.PercentOutput, 0.05);
    			System.out.println("minimumzerozero");
    		}
    	}
    	
    	inRange = Math.abs(ticks-Robot.ShooterSubsystem.Shooter.getSelectedSensorPosition(0)) <= 5;
    	System.out.print(inRange);
    	if(inRange){endTime = System.currentTimeMillis();}
    	else {startTime = System.currentTimeMillis();}    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (endTime - startTime > 350) { System.out.println("Finished Shooter AngleFinished Shooter AngleFinished Shooter AngleFinished Shooter AngleFinished Shooter AngleFinished Shooter AngleFinished Shooter AngleFinished Shooter AngleFinished Shooter AngleFinished Shooter AngleFinished Shooter AngleFinished Shooter AngleFinished Shooter AngleFinished Shooter AngleFinished Shooter AngleFinished Shooter AngleFinished Shooter AngleFinished Shooter AngleFinished Shooter AngleFinished Shooter AngleFinished Shooter AngleFinished Shooter AngleFinished Shooter AngleFinished Shooter AngleFinished Shooter AngleFinished Shooter AngleFinished Shooter AngleFinished Shooter AngleFinished Shooter AngleFinished Shooter Angle" + angle);
		return true; }
    return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.ShooterSubsystem.Shooter.set(ControlMode.PercentOutput, 0.05);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
