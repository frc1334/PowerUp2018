
package org.usfirst.frc.team1334.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1334.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team1334.robot.subsystems.ElevatorSubsystem;
import org.usfirst.frc.team1334.robot.util.Subsystems;


import org.usfirst.frc.team1334.robot.subsystems.ClimberSubsystem;
import org.usfirst.frc.team1334.robot.subsystems.ShooterSubsystem;
import org.usfirst.frc.team1334.robot.OI;
import org.usfirst.frc.team1334.robot.commands.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	public static boolean isClose = false;
	public static final DriveSubsystem DriveSubsystem = new DriveSubsystem();
	public static final ElevatorSubsystem ElevatorSubsystem = new ElevatorSubsystem();
	public static final ShooterSubsystem ShooterSubsystem = new ShooterSubsystem();
	public static final ClimberSubsystem ClimberSubsystem = new ClimberSubsystem();
	public static int kCloseSwitchFwd = 148;
	public static int kCloseSwitchApproach = 19;
	public static int kOppSwitchForward = 288;
	public static int kOppSwitchAcross = 227;
	public static int kOppSwitchDown = 61;
	public static int kOppSwitchBack = 16;
	public static int kCloseScaleFwd = 253;
	public static int kOppScaleFwd = 219;
	public static int kOppScaleAcross = 227;
	public static int kOppScaleApproach = 23;
	public static int kSideBasline = 148;
	public static int kCenterLeaveWall = 30;
	public static int kCenterLeft = 65;
	public static int kCenterRight = 55;
	public static int kCenterApproach = 70;
	String gameData;
	public static OI oi;
	Command autonomousCommand;
	Command driveCommand = new DriveCommand();
	SendableChooser<String> chooser = new SendableChooser<>();
	public boolean SwitchState;
	public boolean ScaleState;
	// true for right, false for left
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI();
		
		
		//chooser.addDefault("Default program", new );
		//chooser.addObject("Experimental Auto", new );
		// chooser.addObject("My Auto", new MyAutoCommand());
		chooser.addDefault("CenterSwitch", "CenterSwitch");
		chooser.addObject("CenterBase", "CenterBase");
		chooser.addObject("RightScale", "RightScale");
		chooser.addObject("RightSwitch", "RightSwitch");
		chooser.addObject("LeftScale", "LeftScale");
		chooser.addObject("LeftSwitch", "LeftSwitch");
		chooser.addObject("Baseline", "Baseline");
		
		SmartDashboard.putData("Auto mode", chooser);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		//autonomousCommand = chooser.getSelected();
		Robot.DriveSubsystem.ResetGyroAngle();
		
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new DriveCommand(); break; }
		 */
		//String computation goes here
		
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		if(gameData!=null){
			gameData = DriverStation.getInstance().getGameSpecificMessage();
			if(gameData.charAt(0) == 'L'){
				//Put left auto code here
				SwitchState = true;
			}else if(gameData.charAt(0) == 'R'){
				//Put right auto code here
				SwitchState = false;
			}
			if(gameData.charAt(1) == 'L'){
				ScaleState = true;
			}else if(gameData.charAt(1) == 'R'){
				ScaleState = false;
			}
			// schedule the autonomous command (example)
			String Selected = chooser.getSelected();
			switch(Selected){
			case "CenterSwitch":
				if(SwitchState){
					autonomousCommand = new CenterLeftSwitch();
				}else{
					autonomousCommand = new CenterRightSwitch();
				}
				break;
			case "CenterBase":
				autonomousCommand = new CenterBaseline();
				break;
			case "RightScale":
				if(ScaleState){
					autonomousCommand = new RightRightScale();
				}else{
					autonomousCommand = new RightLeftScale();
				}
				break;
			case "RightSwitch":
				if(SwitchState){
					autonomousCommand = new RightLeftSwitch();
				}else{
					autonomousCommand = new RightRightSwitch();
				}
				break;
			case "LeftScale":
				if(ScaleState){
					autonomousCommand = new LeftRightScale();
				}else{
					autonomousCommand = new LeftLeftScale();
				}
				break;
			case "LeftSwitch":
				if(SwitchState){
					autonomousCommand = new LeftLeftSwitch();
				}else{
					autonomousCommand = new LeftRightSwitch();
				}
				break;
			case "Baseline":
				autonomousCommand = new Baseline();
				break;
			}
			if(autonomousCommand!=null){
				autonomousCommand.start();
			}
		}
		//autonomousCommand
		
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		driveCommand.start();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
