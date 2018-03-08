package org.usfirst.frc.team1334.robot.subsystems;

import org.usfirst.frc.team1334.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class ShooterSubsystem extends Subsystem{
	
	public TalonSRX Intake1 = new TalonSRX (RobotMap.Intake1);
	public TalonSRX Intake2 = new TalonSRX (RobotMap.Intake2);
	
	public DoubleSolenoid Piston = new DoubleSolenoid (RobotMap.Piston1, RobotMap.Piston2);
	
	public void intake (boolean inward, boolean outward) {
		// Intake System
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
	
	public void highGoal () {
		intake(false,true);
		Piston.set(Value.kForward);
	}
	
	public void lowGoal () {
		intake(false,true);
	}
	
	@Override
	protected void initDefaultCommand () {
		// TODO Auto-generated method stub
		
	}

}
