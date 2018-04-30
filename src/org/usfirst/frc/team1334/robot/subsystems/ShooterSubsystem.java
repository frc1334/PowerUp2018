package org.usfirst.frc.team1334.robot.subsystems;

import org.usfirst.frc.team1334.robot.OI;
import org.usfirst.frc.team1334.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Solenoid;

public class ShooterSubsystem extends Subsystem{
	
	public TalonSRX Intake1 = new TalonSRX (RobotMap.Intake1);
	public TalonSRX Intake2 = new TalonSRX (RobotMap.Intake2);
	public TalonSRX Shooter = new TalonSRX (RobotMap.Shooter1);
	public boolean zeroed;
	public Solenoid Piston1 = new Solenoid (RobotMap.shooterP1_1);
	public Solenoid Piston2 = new Solenoid (RobotMap.shooterP1_2);
	
	public void intake (boolean inward, boolean outward) {
		// Intake System
		if (inward && !outward){
			Intake1.set(ControlMode.PercentOutput, -0.5);
			Intake2.set(ControlMode.PercentOutput, -0.5);
		}
		else if (outward && !inward){
			Intake1.set(ControlMode.PercentOutput, 1);
			Intake2.set(ControlMode.PercentOutput, 1);
		}
		else {
			Intake1.set(ControlMode.PercentOutput, 0);
			Intake2.set(ControlMode.PercentOutput, 0);
		}
		////System.out.println(Shooter.getSensorCollection().isFwdLimitSwitchClosed()+ "FWD");
		////System.out.println(Shooter.getSensorCollection().isRevLimitSwitchClosed()+ "REV");
	}
	
	public void driveShooter(double power){
		////System.out.println(power + "power");
		if( Shooter.getSensorCollection().isFwdLimitSwitchClosed() && power > 0 || Shooter.getSensorCollection().isRevLimitSwitchClosed() && power < 0){
			Shooter.set(ControlMode.PercentOutput, 0.05);
			////System.out.println("stalling");
			OI.RumbleOP(0.5);
		}else if((power == 0)){
			Shooter.set(ControlMode.PercentOutput, 0.05);
			////System.out.println("stalling");
			OI.RumbleOP(0.0);
		}else if(power > 0){
			Shooter.set(ControlMode.PercentOutput, power*0.6);
			////System.out.println("upupupupupuppupupuppu");
			OI.RumbleOP(0.0);
		}else if (power < 0){
			OI.RumbleOP(0.0);
			Shooter.set(ControlMode.PercentOutput, power*0.3);
		}
		////System.out.println(Shooter.getSensorCollection().isFwdLimitSwitchClosed() + "FWD");
		////System.out.println(Shooter.getSensorCollection().isRevLimitSwitchClosed() + "REV");
		////System.out.println(Shooter.getMotorOutputPercent() + "Speed");
	}
	
	public void highGoal (double delta) {

		Piston1.set(true);
		Piston2.set(true);
		if(delta>10){
			intake(false,true);
		}
		
		
	}
	
	public void lowGoal () {
		intake(false,true);
	}
	public void zeroShooterUpwards(){
		if(zeroed){
			Shooter.set(ControlMode.PercentOutput, 0);
		}else{
			Shooter.set(ControlMode.PercentOutput, 0.5);
			if(Shooter.getSensorCollection().isFwdLimitSwitchClosed()){
				zeroed = true;
			}
		}
	}
	
	public void idle(boolean intake1, boolean intake2){
		Piston1.set(false);
		Piston2.set(false);
		intake(intake1,intake2);
	}
	
	@Override
	protected void initDefaultCommand () {
		// TODO Auto-generated method stub
		
	}

}
