package org.usfirst.frc.team1334.robot;

import org.usfirst.frc.team1334.robot.util.Xbox360Controller;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new DriveCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new DriveCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new DriveCommand());
	
	private static final Xbox360Controller Controller = new Xbox360Controller(0, 0.15);
	private static final Xbox360Controller Operator = new Xbox360Controller(1, 0.15);
	
	public static double getDriverSpeed() { return Controller.getRightTrigger() - Controller.getLeftTrigger(); }
	public static double getSteer() { return Controller.getLeftXAxis(); }
	public static boolean getHighGear() { return Operator.getButtonB(); }
	public static boolean getLowGear() { return Operator.getButtonA(); }
}
