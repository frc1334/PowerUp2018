package org.usfirst.frc.team1334.robot.commands;
import org.usfirst.frc.team1334.robot.Robot;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class RightLeftSwitch extends CommandGroup{
	
	public RightLeftSwitch(){
		addParallel(new ShooterAngle(15));
		addSequential(new AutoDriveCommand(Robot.kOppSwitchForward));
    	addSequential(new GyroTurn(-90));
    	addSequential(new AutoDriveCommand(Robot.kOppSwitchAcross));
    	addSequential(new GyroTurn(-90));
    	addSequential(new AutoDriveCommand(Robot.kOppSwitchDown));
    	addSequential(new GyroTurn(-90));
    	addSequential(new AutoDriveCommand(Robot.kOppSwitchBack));
    	//addSequential(new AutoSpeedCommand(Robot.kSwitchApproachSpeed,Robot.kOppSideSwitchApproachTime));
    	addSequential(new AutoShooterCommand(false,10));
	}
	
}
