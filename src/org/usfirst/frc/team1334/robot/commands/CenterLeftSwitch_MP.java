package org.usfirst.frc.team1334.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CenterLeftSwitch_MP extends CommandGroup {

    public CenterLeftSwitch_MP() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	addParallel(new ShooterAngle(15));
    	addSequential(new FollowMotionProfile("/home/lvuser/testcurve_left.csv","/home/lvuser/testcurve_right.csv","CenterLeftSwitchMotProf"));
    	addSequential(new AutoShooterCommand(false));
    }
}
