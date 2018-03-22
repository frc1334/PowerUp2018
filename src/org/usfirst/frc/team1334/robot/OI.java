package org.usfirst.frc.team1334.robot;

import org.usfirst.frc.team1334.robot.util.Xbox360Controller;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  //private static final Xbox360Controller [Name] = new Xbox360Controller(port,deadzone);
	private static final Xbox360Controller Driver = new Xbox360Controller(0, 0.15);
	private static final Xbox360Controller Operator = new Xbox360Controller(1, 0.15);
	
	// Driver Controls
	public static double DgetDriverSpeed() { return Driver.getRightTrigger() - Driver.getLeftTrigger(); }
	public static double DgetSteer() { return Driver.getLeftXAxis(); }
	public static boolean DgetHighGear() { return Driver.getButtonB(); }
	public static boolean DgetLowGear() { return Driver.getButtonA(); }
	public static boolean DclimbEngage () { return Driver.getButtonX(); }
	public static boolean OclimbSpinnyboiR () { return Operator.getButtonRB(); }
	public static boolean DgetIntakeReverse () { return Driver.getButtonLB(); }
	public static boolean DgetIntakeGO () { return Driver.getButtonRB(); }
	
	// Operator Controls
	public static boolean OelevateBrake () { return Operator.getButtonX(); }
	public static double OelevateControl() { return Operator.getLeftYAxis();}
	public static boolean DclimbDisengage () { return Driver.getButtonY(); }
	public static boolean OclimbSpinnyboiL () { return Operator.getButtonLB(); }
	public static boolean OHIGH() {return Operator.getButtonA();}
	public static boolean OLOW() {return Operator.getButtonB();}
	public static void RumbleOP (double Amount) { Operator.setRumble(RumbleType.kLeftRumble, Amount); Operator.setRumble(RumbleType.kRightRumble, Amount);}
}
