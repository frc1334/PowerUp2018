package org.usfirst.frc.team1334.robot.util;

import org.usfirst.frc.team1334.robot.subsystems.DriveSubsystem;

public class Subsystems {

	public static DriveSubsystem DRIVE_SUBSYSTEM;
	
	public Subsystems() {
		DRIVE_SUBSYSTEM = new DriveSubsystem();
	}
}
