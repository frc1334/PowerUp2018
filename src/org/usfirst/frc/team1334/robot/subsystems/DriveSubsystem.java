package org.usfirst.frc.team1334.robot.subsystems;

import org.usfirst.frc.team1334.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveSubsystem extends Subsystem {
	public int kSlotIdx = 0;

	/*
	 * Talon SRX/ Victor SPX will supported multiple (cascaded) PID loops. For
	 * now we just want the primary one.
	 */
	public int kPIDLoopIdx = 0;

	/*
	 * set to zero to skip waiting for confirmation, set to nonzero to wait and
	 * report to DS if action fails.
	 */
	public int kTimeoutMs = 10;
	
	/* choose so that Talon does not report sensor out of phase */
	public boolean kSensorPhase = true;

	/* choose based on what direction you want to be positive,
		this does not affect motor invert. */
	public boolean kMotorInvert = false;
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public int vMulti = 200;
	public TalonSRX Left1 = new TalonSRX(RobotMap.Left1);
	public TalonSRX Left2 = new TalonSRX(RobotMap.Left2);
	public TalonSRX Right1 = new TalonSRX(RobotMap.Right1);
	public TalonSRX Right2 = new TalonSRX(RobotMap.Right2);
	public Compressor c = new Compressor(RobotMap.Compressor);
	public static DoubleSolenoid gShift = new DoubleSolenoid(RobotMap.shift1, RobotMap.shift2);

	public void CompressorControl(){
		c.setClosedLoopControl(true);
	}
	public void setPIDDRIVE(){
		Left1.set(ControlMode.Position, 0);
		Right1.set(ControlMode.Position, 0);
	}

	public void setREGULARDRIVE(){
		Left1.set(ControlMode.Velocity, 0);
		Right1.set(ControlMode.Velocity, 0);
	}

	public void TankDrive(double left, double right){
		Left1.set(ControlMode.PercentOutput, left);
		Left2.set(ControlMode.PercentOutput, left);
		Right1.set(ControlMode.PercentOutput, right);
		Right2.set(ControlMode.PercentOutput, right);
	}

	public void ArcadeDrive(double speed, double turn){
		speed *= vMulti;
		turn *= vMulti;
		TankDrive(speed +turn, -speed + turn);
		
		System.out.println(Left1.getClosedLoopError(0)); // prints amount of error of both encoders
		System.out.println(Right1.getClosedLoopError(0));
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		Left2.set(ControlMode.Follower, 0); // Sets Left2 Talon to follow movements of the Left1 Talon
		Right2.set(ControlMode.Follower, 2); // Sets Right2 Talon to follow movements of the Right1 Talon
		Left1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 50);
		// Arg - Feedback Device Type, PID IDX, timeout (ms)
		Right1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 50);
		c.setClosedLoopControl(true);
		// sets compressor to a closed loop control
	}
	
	public void shiftGear (boolean up, boolean down){ // Gear shift method
		
		if (up) { // if high gear is activated
			gShift.set(Value.kForward);
		}
		else if (down) {
			gShift.set(Value.kReverse);
		}
	}
}
