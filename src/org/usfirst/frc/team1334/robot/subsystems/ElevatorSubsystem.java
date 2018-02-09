package org.usfirst.frc.team1334.robot.subsystems;

import org.usfirst.frc.team1334.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ElevatorSubsystem extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	public TalonSRX Intake1 = new TalonSRX (RobotMap.Intake1);
	public TalonSRX Intake2 = new TalonSRX (RobotMap.Intake2);
	public TalonSRX Elevator1 = new TalonSRX (RobotMap.Elevator1);
	public TalonSRX Elevator2 = new TalonSRX (RobotMap.Elevator2);
	public static Solenoid brock = new Solenoid (RobotMap.breakk);
	
	public void intake (boolean inward, boolean outward) {
		
	}
	
	public void elevator (double speed, boolean brake){
		if (brake){
			brock.set(true);
		}
		else {
			
		}
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	
    	Intake2.set(ControlMode.Follower, 4);
    	Elevator2.set(ControlMode.Follower, 6);
    }
}

