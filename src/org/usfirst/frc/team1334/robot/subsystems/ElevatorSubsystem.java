package org.usfirst.frc.team1334.robot.subsystems;

import org.usfirst.frc.team1334.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ElevatorSubsystem extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
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
	
	public double kToleranceDegrees = 2.0;
	
	double direction;
	
	public TalonSRX Intake1 = new TalonSRX (RobotMap.Intake1);
	public TalonSRX Intake2 = new TalonSRX (RobotMap.Intake2);
	public TalonSRX Elevator1 = new TalonSRX (RobotMap.Elevator1);
	
	public TalonSRX Elevator2 = new TalonSRX (RobotMap.Elevator2);
	public DigitalInput LowWarn = new DigitalInput(RobotMap.lowwarn);
	public DigitalInput HighWarn = new DigitalInput(RobotMap.highwarn);
	boolean reversesensor = false;

	public static DoubleSolenoid brock = new DoubleSolenoid (RobotMap.breakk1, RobotMap.breakk2);
	int elevatorposition = 1;
	
	public void intake (boolean inward, boolean outward) {
		if (inward && !outward){
			Intake1.set(ControlMode.PercentOutput, 1);
			Intake2.set(ControlMode.PercentOutput, -1);
		}
		else if (outward && !inward){
			Intake1.set(ControlMode.PercentOutput, -1);
			Intake2.set(ControlMode.PercentOutput, 1);
		}
		else {
			Intake1.set(ControlMode.PercentOutput, 0);
			Intake2.set(ControlMode.PercentOutput, 0);
		}
	}
	
	public boolean resetElevator(boolean bottomLimit){
		if(bottomLimit){
			elevatorposition = 0;
			return true;
		}
		Elevator1.set(ControlMode.PercentOutput, -0.5);
		return false;
	}
	
	public boolean topElevator(boolean topLimit)
	{
		if (topLimit){
			elevatorposition = 3;
			return true;
		}
		Elevator1.set(ControlMode.PercentOutput, 0.5);
		return false;
	}
	
	public void elevator (double speed, boolean brake, boolean topLimit, boolean bottomLimit, double trueSpeed){
	
		speed*=-1;
		
		
		if(!bottomLimit&&speed>0 || !topLimit && speed<0){
			elevatorposition = 2;
		}
		if(!bottomLimit && speed < 0){
			elevatorposition = 1;
		}
		if(!topLimit && speed > 0){
			elevatorposition = 3;
		}
		
		if(brake){
			direction = Math.signum(speed);
			Elevator1.set(ControlMode.PercentOutput, 0.3*direction);
		}
		else if (!topLimit && speed > 10){
			Elevator1.set(ControlMode.PercentOutput, 0);
		}
		else if (!bottomLimit && speed < -10){
			Elevator1.set(ControlMode.PercentOutput, 0);
		}
		else if(elevatorposition == 1){
			//brock.set(Value.kReverse);
			direction = Math.signum(speed);
			Elevator1.set(ControlMode.PercentOutput, 0.3 * direction);
		}else if(elevatorposition == 3){
			direction = Math.signum(speed);
			Elevator1.set(ControlMode.PercentOutput, 0.1 * direction);
		}
			else{
			//brock.set(Value.kReverse);
			Elevator1.set(ControlMode.PercentOutput, speed);
		}
		
	}
	
	public void elevator(double speed,  boolean brake){
		System.out.println(Elevator1.getSelectedSensorVelocity(0));
		System.out.println(HighWarn.get()+"HighWarn");
		System.out.println(LowWarn.get()+"LowWarn");
		if(brake){
			direction = Math.signum(speed);
			Elevator1.set(ControlMode.PercentOutput, direction*0.3);
			Elevator2.set(ControlMode.Follower, 6);
		}else{
			Elevator1.set(ControlMode.PercentOutput, speed);
		}
	}
	
	// test method for kforward and kreverse
	public void elevTest (boolean active, boolean notactive){
		if (active && !notactive){
			brock.set(Value.kForward);
			Elevator1.set(ControlMode.PercentOutput, 0);
		}
		else if (notactive && !active){
			brock.set(Value.kReverse);
			Elevator1.set(ControlMode.PercentOutput, 0);
		}
	}
	
    @Override
	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	//Intake2.set(ControlMode.Follower, 4);
    	//Intake2.setInverted(true);
    	Elevator2.set(ControlMode.Follower, 6);
    	Elevator1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 50);
    
    }
}

