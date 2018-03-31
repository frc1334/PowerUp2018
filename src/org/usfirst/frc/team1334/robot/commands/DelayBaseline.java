package org.usfirst.frc.team1334.robot.commands;

import org.usfirst.frc.team1334.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DelayBaseline extends CommandGroup {

    public DelayBaseline() {
    	addParallel(new ShooterAngle(5));
    	addSequential(new AutoSpeedCommand(0,10000)); // 10s delay
    	addSequential(new AutoDriveCommand(Robot.kSideBasline));
    }
}
