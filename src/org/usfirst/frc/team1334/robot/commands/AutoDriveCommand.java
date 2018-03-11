package org.usfirst.frc.team1334.robot.commands;

import org.usfirst.frc.team1334.robot.Robot;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

/**
 * 
 */
public class AutoDriveCommand extends Command {
	int distance = 0;
	long startTime, endTime;
	boolean inRange;
	double ticks;
    public AutoDriveCommand(int Distance) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	distance = Distance;
    	requires(Robot.DriveSubsystem);
    }

    // Called just before this Command runs the first time
    @Override
	protected void initialize() {
    	endTime = System.currentTimeMillis();
    	startTime = System.currentTimeMillis();
    	Robot.DriveSubsystem.gShift.set(Value.kForward);
    	/* choose the sensor and sensor direction */
    	
		Robot.DriveSubsystem.Left1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Robot.DriveSubsystem.kPIDLoopIdx,
				Robot.DriveSubsystem.kTimeoutMs);
		Robot.DriveSubsystem.Right1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Robot.DriveSubsystem.kPIDLoopIdx,
				Robot.DriveSubsystem.kTimeoutMs);
		
		/* choose to ensure sensor is positive when output is positive */
		Robot.DriveSubsystem.Left1.setSensorPhase(false);
		Robot.DriveSubsystem.Right1.setSensorPhase(false);
		/* choose based on what direction you want forward/positive to be.
		 * This does not affect sensor phase. */ 
		Robot.DriveSubsystem.Left1.setInverted(!Robot.DriveSubsystem.kMotorInvert);
		Robot.DriveSubsystem.Right1.setInverted(Robot.DriveSubsystem.kMotorInvert);
		
		Robot.DriveSubsystem.Left1.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0,10,Robot.DriveSubsystem.kTimeoutMs);
		Robot.DriveSubsystem.Left1.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Robot.DriveSubsystem.kTimeoutMs);
		Robot.DriveSubsystem.Right1.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0,10,Robot.DriveSubsystem.kTimeoutMs);
		Robot.DriveSubsystem.Right1.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Robot.DriveSubsystem.kTimeoutMs);
		/* set the peak and nominal outputs, 12V means full */
		Robot.DriveSubsystem.Left1.configNominalOutputForward(0, Robot.DriveSubsystem.kTimeoutMs);
		Robot.DriveSubsystem.Left1.configNominalOutputReverse(0, Robot.DriveSubsystem.kTimeoutMs);
		Robot.DriveSubsystem.Left1.configPeakOutputForward(1, Robot.DriveSubsystem.kTimeoutMs);
		Robot.DriveSubsystem.Left1.configPeakOutputReverse(-1, Robot.DriveSubsystem.kTimeoutMs);
		Robot.DriveSubsystem.Right1.configNominalOutputForward(0, Robot.DriveSubsystem.kTimeoutMs);
		Robot.DriveSubsystem.Right1.configNominalOutputReverse(0, Robot.DriveSubsystem.kTimeoutMs);
		Robot.DriveSubsystem.Right1.configPeakOutputForward(1, Robot.DriveSubsystem.kTimeoutMs);
		Robot.DriveSubsystem.Right1.configPeakOutputReverse(-1, Robot.DriveSubsystem.kTimeoutMs);
		/*
		 * set the allowable closed-loop error, Closed-Loop output will be
		 * neutral within this range. See Table in Section 17.2.1 for native
		 * units per rotation.
		 */
		Robot.DriveSubsystem.Left1.configAllowableClosedloopError(50, Robot.DriveSubsystem.kPIDLoopIdx, Robot.DriveSubsystem.kTimeoutMs);
		Robot.DriveSubsystem.Right1.configAllowableClosedloopError(50, Robot.DriveSubsystem.kPIDLoopIdx, Robot.DriveSubsystem.kTimeoutMs);

		/* set closed loop gains in slot0, typically kF stays zero. */
		Robot.DriveSubsystem.Left1.config_kF(Robot.DriveSubsystem.kPIDLoopIdx, 0.2, Robot.DriveSubsystem.kTimeoutMs);
		Robot.DriveSubsystem.Left1.config_kP(Robot.DriveSubsystem.kPIDLoopIdx, 0.45, Robot.DriveSubsystem.kTimeoutMs);
		Robot.DriveSubsystem.Left1.config_kI(Robot.DriveSubsystem.kPIDLoopIdx, 0.0001, Robot.DriveSubsystem.kTimeoutMs);
		Robot.DriveSubsystem.Left1.config_kD(Robot.DriveSubsystem.kPIDLoopIdx, 0.002, Robot.DriveSubsystem.kTimeoutMs);
		Robot.DriveSubsystem.Left1.configMotionCruiseVelocity(1063, Robot.DriveSubsystem.kTimeoutMs);
		Robot.DriveSubsystem.Left1.configMotionAcceleration(2126, Robot.DriveSubsystem.kTimeoutMs);
		Robot.DriveSubsystem.Right1.config_kF(Robot.DriveSubsystem.kPIDLoopIdx, 0.2, Robot.DriveSubsystem.kTimeoutMs);
		Robot.DriveSubsystem.Right1.config_kP(Robot.DriveSubsystem.kPIDLoopIdx, 0.45, Robot.DriveSubsystem.kTimeoutMs);
		Robot.DriveSubsystem.Right1.config_kI(Robot.DriveSubsystem.kPIDLoopIdx, 0.0001, Robot.DriveSubsystem.kTimeoutMs);
		Robot.DriveSubsystem.Right1.config_kD(Robot.DriveSubsystem.kPIDLoopIdx, 0.002, Robot.DriveSubsystem.kTimeoutMs);
		Robot.DriveSubsystem.Right1.configMotionCruiseVelocity(1063, Robot.DriveSubsystem.kTimeoutMs);
		Robot.DriveSubsystem.Right1.configMotionAcceleration(2126, Robot.DriveSubsystem.kTimeoutMs);
		Robot.DriveSubsystem.Left2.set(ControlMode.Follower, 0);
		Robot.DriveSubsystem.Right2.set(ControlMode.Follower, 2);
		Robot.DriveSubsystem.Left1.setInverted(true);
		Robot.DriveSubsystem.Left2.setInverted(true);
		/*
		 * lets grab the 360 degree position of the MagEncoder's absolute
		 * position, and initally set the relative sensor to match.
		 */
		 
		int absolutePosition = Robot.DriveSubsystem.Left1.getSensorCollection().getPulseWidthPosition();
		int absolutePosition2 = Robot.DriveSubsystem.Right1.getSensorCollection().getPulseWidthPosition();
		/* mask out overflows, keep bottom 12 bits */
		absolutePosition &= 0xFFF;
		if (Robot.DriveSubsystem.kSensorPhase)
			absolutePosition *= -1;
		if (Robot.DriveSubsystem.kMotorInvert)
			absolutePosition *= -1;
		
		absolutePosition2 &= 0xFFF;
		if (Robot.DriveSubsystem.kSensorPhase)
			absolutePosition2 *= -1;
		if (Robot.DriveSubsystem.kMotorInvert)
			absolutePosition2 *= -1;
		/* set the quadrature (relative) sensor to match absolute */
		Robot.DriveSubsystem.Left1.setSelectedSensorPosition(absolutePosition, Robot.DriveSubsystem.kPIDLoopIdx, Robot.DriveSubsystem.kTimeoutMs);
		Robot.DriveSubsystem.Right1.setSelectedSensorPosition(absolutePosition2, Robot.DriveSubsystem.kPIDLoopIdx, Robot.DriveSubsystem.kTimeoutMs);
		//distance to ticks conversion if 128codes/rev
		ticks = distance* 73.3524416136*4;
		System.out.println(ticks +" distance " + distance);
    	Robot.DriveSubsystem.Left1.set(ControlMode.MotionMagic, ticks);
    	Robot.DriveSubsystem.Right1.set(ControlMode.MotionMagic, ticks);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
	protected void execute() {
    	/*System.currentTimeMillis();
    	Robot.DriveSubsystem.Left1.getClosedLoopError(0);
    	*/
    	System.out.println(ticks-Robot.DriveSubsystem.Left1.getSelectedSensorPosition(0));
    	inRange = Math.abs(ticks-Robot.DriveSubsystem.Left1.getSelectedSensorPosition(0) ) <= 100;
    	if(inRange){endTime = System.currentTimeMillis();}
    	else {startTime = System.currentTimeMillis();}
    }

    // Make this return true when this Command no longer needs to run execute.()
    @Override
	protected boolean isFinished() {
    	if (endTime - startTime > 200) { System.out.println("Finished Auto Drive" + distance);
    		return true; }
        return false;
    }

    // Called once after isFinished returns true
    @Override
	protected void end() {  }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
	protected void interrupted() {}
}
