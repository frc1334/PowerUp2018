package org.usfirst.frc.team1334.robot.util;

import org.usfirst.frc.team1334.robot.subsystems.ClimberSubsystem;
import org.usfirst.frc.team1334.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team1334.robot.subsystems.ElevatorSubsystem;

public class Subsystems {

	public static DriveSubsystem DRIVE_SUBSYSTEM;
	public static ElevatorSubsystem ELEVATOR_SUBSYSTEM;
	public static ClimberSubsystem CLIMB_SUBSYSTEM;
	
	public Subsystems() {
		DRIVE_SUBSYSTEM = new DriveSubsystem();
		ELEVATOR_SUBSYSTEM = new ElevatorSubsystem();
		CLIMB_SUBSYSTEM = new ClimberSubsystem();
	}
}
