package org.usfirst.frc.team1334.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveSubsystem extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	TalonSRX Left1 = new TalonSRX(0);
	TalonSRX Left2 = new TalonSRX(1);
	TalonSRX Right1 = new TalonSRX(2);
	TalonSRX Right2 = new TalonSRX(3);

	public void setPIDDRIVE(){
		Left1.set(ControlMode.MotionMagic, 0);
		Right1.set(ControlMode.MotionMagic, 0);
	}

	public void setREGULARDRIVE(){

	}

	public void TankDrive(double left, double right){
		Left1.set(ControlMode.PercentOutput, left);
		Left2.set(ControlMode.PercentOutput, left);
		Right1.set(ControlMode.PercentOutput, right);
		Right2.set(ControlMode.PercentOutput, right);
	}

	public void ArcadeDrive(double speed, double turn){
		TankDrive(speed +turn, -speed + turn);
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		Left1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 50);
		Right1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 50);
	}

}
