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
	double direction;
	
	public TalonSRX Intake1 = new TalonSRX (RobotMap.Intake1);
	public TalonSRX Intake2 = new TalonSRX (RobotMap.Intake2);
	public TalonSRX Elevator1 = new TalonSRX (RobotMap.Elevator1);
	public TalonSRX Elevator2 = new TalonSRX (RobotMap.Elevator2);
	public static Solenoid brock = new Solenoid (RobotMap.breakk);
	int elevatorposition = 0;
	
	public void intake (boolean inward, boolean outward) {
		if (inward && !outward){
			Intake1.set(ControlMode.PercentOutput, 1);
		}
		else if (outward && !inward){
			Intake1.set(ControlMode.PercentOutput, -1);
		}
		else {
			Intake1.set(ControlMode.PercentOutput, 0);
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
	
	public void elevator (double speed, boolean brake, boolean topLimit, boolean bottomLimit, double trueSpeed){
		
		if(bottomLimit&&speed>0 || topLimit && speed<0){
			elevatorposition = 2;
		}
		if (brake || speed == 0){
			brock.set(true);
		}
		else {
			if (topLimit && trueSpeed > 0.5){
				brock.set(true);
				elevatorposition = 3;
			}
			else if (bottomLimit && trueSpeed < -0.5){
				brock.set(true);
				elevatorposition = 1;
			}
			else if(elevatorposition == 3 || elevatorposition == 1){
				direction = Math.signum(speed);
				Elevator1.set(ControlMode.PercentOutput, 0.5 * direction);
			}else{
				Elevator1.set(ControlMode.PercentOutput, speed);
			}
		}
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	Intake2.set(ControlMode.Follower, 4);
    	Elevator2.set(ControlMode.Follower, 6);
    }
}

