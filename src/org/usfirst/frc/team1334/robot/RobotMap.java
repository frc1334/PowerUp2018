package org.usfirst.frc.team1334.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {


	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;
	//talons use one set of IDs
	public static final int Left1 = 0;
	public static final int Left2 = 1;
	public static final int Right1 = 2;
	public static final int Right2 = 3;
	
	public static final int Intake1 = 4;
	public static final int Intake2 = 5;
	
	public static final int Elevator1 = 6;
	public static final int Elevator2 = 7;
	
	public static final int Climb1 = 8;
	public static final int Climb2 = 9;
	//the compressor uses another
	public static final int Compressor = 0;
	//and the PCM uses yet anoter set of IDS
	public static final int shift1 = 0;
	public static final int shift2 = 1;

	public static final int breakk1 = 2;
	public static final int breakk2 = 3;
	
	public static final int eject1 = 4;
	public static final int eject2 = 5;
	//Digital IO Ports
	public static final int lowwarn = 0;
	public static final int highwarn = 1;
	public static final int ElevA = 2;
	public static final int ElevB = 3;
	
	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
}
