package org.usfirst.frc.team1334.robot.subsystems;

import org.usfirst.frc.team1334.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
//import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ClimberSubsystem extends Subsystem {
	public TalonSRX Climb1 = new TalonSRX(RobotMap.Climb1);
	public TalonSRX Climb2 = new TalonSRX(RobotMap.Climb2);
	//  Climb 1 is left and Climb 2 is right
	public DoubleSolenoid ClimbSol = new DoubleSolenoid(RobotMap.eject1, RobotMap.eject2);
	//  Boolean variable for activating the solenoids
	
	public void solenoidInit(boolean up){
		if(up){
			ClimbSol.set(DoubleSolenoid.Value.kForward);
		}
	}
	
	public void winch(boolean left, boolean right){
		if (left){
			Climb1.set(ControlMode.PercentOutput, 1);
		}
		if (right){
			Climb2.set(ControlMode.PercentOutput, 1);
		}
	}
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
	}
}