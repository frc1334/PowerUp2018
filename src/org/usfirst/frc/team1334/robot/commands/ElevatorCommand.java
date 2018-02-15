package org.usfirst.frc.team1334.robot.commands;

import org.usfirst.frc.team1334.robot.Robot;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ElevatorCommand extends Command {
	int elevPosition;
	
    public ElevatorCommand(int position) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	elevPosition = position;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.ElevatorSubsystem.Elevator1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 
    			Robot.ElevatorSubsystem.kPIDLoopIdx, Robot.ElevatorSubsystem.kTimeoutMs);

		/* choose to ensure sensor is positive when output is positive */
		Robot.ElevatorSubsystem.Elevator1.setSensorPhase(Robot.ElevatorSubsystem.kSensorPhase);
		/* choose based on what direction you want forward/positive to be.
		 * This does not affect sensor phase. */ 
		Robot.ElevatorSubsystem.Elevator1.setInverted(Robot.ElevatorSubsystem.kMotorInvert);
		/* set the peak and nominal outputs, 12V means full */
		Robot.ElevatorSubsystem.Elevator1.configNominalOutputForward(0, Robot.ElevatorSubsystem.kTimeoutMs);
		Robot.ElevatorSubsystem.Elevator1.configNominalOutputReverse(0, Robot.ElevatorSubsystem.kTimeoutMs);
		Robot.ElevatorSubsystem.Elevator1.configPeakOutputForward(1, Robot.ElevatorSubsystem.kTimeoutMs);
		Robot.ElevatorSubsystem.Elevator1.configPeakOutputReverse(-1, Robot.ElevatorSubsystem.kTimeoutMs);
		/*
		 * set the allowable closed-loop error, Closed-Loop output will be
		 * neutral within this range. See Table in Section 17.2.1 for native
		 * units per rotation.
		 */
		Robot.ElevatorSubsystem.Elevator1.configAllowableClosedloopError(0, Robot.ElevatorSubsystem.kPIDLoopIdx, 
				Robot.ElevatorSubsystem.kTimeoutMs);

		/* set closed loop gains in slot0, typically kF stays zero. */
		Robot.ElevatorSubsystem.Elevator1.config_kF(Robot.ElevatorSubsystem.kPIDLoopIdx, 0.0, Robot.ElevatorSubsystem.kTimeoutMs);
		Robot.ElevatorSubsystem.Elevator1.config_kP(Robot.ElevatorSubsystem.kPIDLoopIdx, 0.1, Robot.ElevatorSubsystem.kTimeoutMs);
		Robot.ElevatorSubsystem.Elevator1.config_kI(Robot.ElevatorSubsystem.kPIDLoopIdx, 0.0, Robot.ElevatorSubsystem.kTimeoutMs);
		Robot.ElevatorSubsystem.Elevator1.config_kD(Robot.ElevatorSubsystem.kPIDLoopIdx, 0.0, Robot.ElevatorSubsystem.kTimeoutMs);
		/*
		 * lets grab the 360 degree position of the MagEncoder's absolute
		 * position, and intitally set the relative sensor to match.
		 */
		 
		int absolutePosition = Robot.ElevatorSubsystem.Elevator1.getSensorCollection().getPulseWidthPosition();
		/* mask out overflows, keep bottom 12 bits */
		absolutePosition &= 0xFFF;
		if (Robot.ElevatorSubsystem.kSensorPhase)
			absolutePosition *= -1;
		if (Robot.ElevatorSubsystem.kMotorInvert)
			absolutePosition *= -1;

		/* set the quadrature (relative) sensor to match absolute */
		Robot.ElevatorSubsystem.Elevator1.setSelectedSensorPosition(absolutePosition, Robot.ElevatorSubsystem.kPIDLoopIdx, 
				Robot.ElevatorSubsystem.kTimeoutMs);
		Robot.ElevatorSubsystem.resetElevator(Robot.ElevatorSubsystem.LowWarn.get());
    	switch (elevPosition)
    	{
    		case 0:
    			
    			break;
    			
    		case 1:
    			//if 4096 ticks /rev -> ticks * 32;
    			Robot.ElevatorSubsystem.Elevator1.set(ControlMode.Position, 10/0.015);
    			Robot.ElevatorSubsystem.Elevator2.set(ControlMode.Follower, 6);	
    			break;
    			
    		case 2:
    			Robot.ElevatorSubsystem.Elevator2.set(ControlMode.Follower, 6);
    			Robot.ElevatorSubsystem.topElevator(Robot.ElevatorSubsystem.HighWarn.get());
    			break;
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
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
