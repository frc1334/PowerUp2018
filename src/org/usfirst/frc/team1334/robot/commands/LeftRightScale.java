package org.usfirst.frc.team1334.robot.commands;
import org.usfirst.frc.team1334.robot.Robot;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LeftRightScale extends CommandGroup {

    public LeftRightScale() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	//addParallel(new ShooterAngle(5));
    	addSequential(new AutoDriveCommand(Robot.kOppScaleFwd));
    	addSequential(new GyroTurn(90));
    	addSequential(new AutoDriveCommand(Robot.kOppScaleAcross));
    	addSequential(new GyroTurn(-90));
    	addSequential(new AutoDriveCommand(Robot.kOppScaleApproach));
    	addSequential(new GyroTurn(-38));
    	//addSequential(new AutoShooterCommand(true));
    }

}
