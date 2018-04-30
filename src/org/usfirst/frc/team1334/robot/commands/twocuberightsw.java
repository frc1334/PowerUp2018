package org.usfirst.frc.team1334.robot.commands;

import org.usfirst.frc.team1334.robot.Motfilepaths;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class twocuberightsw extends CommandGroup {

    public twocuberightsw() {
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
    	System.out.println("running two cube auto right");
    	addSequential(new Shooteroff());
    	addParallel(new ShooterAngle(5));
    	addSequential(new FollowMotionProfile(Motfilepaths.CRSw_l,Motfilepaths.CRSw_r,"centerlefttwocube"));
    	addSequential(new AutoShooterCommand(false,200));
    	addParallel(new ShooterAngle(80));
    	addSequential(new AutoDriveCommand(-48));
    	addSequential(new GyroTurn(-70));
    	addParallel(new IntakeCommand(0,3000));
    	addSequential(new AutoDriveCommand(37));//sub 12 for approach distance
    	addParallel(new ShooterAngle(5));
    	addSequential(new AutoDriveCommand(-37));
    	addSequential(new GyroTurn(70));
    	addSequential(new AutoDriveCommand(49));
    	//addSequential(new AutoShooterCommand(false,200));
    }
}
