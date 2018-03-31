package org.usfirst.frc.team1334.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1334.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team1334.robot.subsystems.ElevatorSubsystem;
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
	Compressor C = new Compressor(0);
	public static double leftDistance = 0;
	public static double rightDistance = 0;
	public static boolean isClose = false;
	public static final DriveSubsystem DriveSubsystem = new DriveSubsystem();
	public static final ElevatorSubsystem ElevatorSubsystem = new ElevatorSubsystem();
	public static final ShooterSubsystem ShooterSubsystem = new ShooterSubsystem();
	public static final ClimberSubsystem ClimberSubsystem = new ClimberSubsystem();
	public static double kSwitchApproachSpeed = 0.8;//in percent output
	public static double kCenterSwitchApproachTime = 1000;
	public static double kSideSwitchApproachTime = 300;//in milliseconds
	public static double kOppSideSwitchApproachTime = 300;
	public static int kCloseSwitchFwd = 148;
	public static int kCloseSwitchApproach = 25;
	public static int kOppSwitchForward = 218;
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
	public static String gameData = " ";
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
		Robot.DriveSubsystem.Left1.configPeakCurrentDuration(500, 10);
		Robot.DriveSubsystem.Left2.configPeakCurrentDuration(500, 10);
		Robot.DriveSubsystem.Right1.configPeakCurrentDuration(500, 10);
		Robot.DriveSubsystem.Right2.configPeakCurrentDuration(500, 10);
		Robot.DriveSubsystem.Left1.configPeakCurrentLimit(40, 10);
		Robot.DriveSubsystem.Left2.configPeakCurrentLimit(40, 10);
		Robot.DriveSubsystem.Right1.configPeakCurrentLimit(40, 10);
		Robot.DriveSubsystem.Right2.configPeakCurrentLimit(40, 10);
		Robot.DriveSubsystem.Left1.configContinuousCurrentLimit(30, 10);
		Robot.DriveSubsystem.Left2.configContinuousCurrentLimit(30, 10);
		Robot.DriveSubsystem.Right1.configContinuousCurrentLimit(30, 10);
		Robot.DriveSubsystem.Right2.configContinuousCurrentLimit(30, 10);
		oi = new OI();
		C.setClosedLoopControl(true);
		SmartDashboard.putNumber("LeftSet", leftDistance);
		SmartDashboard.putNumber("RightSet", rightDistance);
		SmartDashboard.putNumber("LeftGet", Robot.DriveSubsystem.Left1.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("RightGet", Robot.DriveSubsystem.Right1.getSelectedSensorPosition(0));
		//chooser.addDefault("Default program", new );
		//chooser.addObject("Experimental Auto", new );
		// chooser.addObject("My Auto", new MyAutoCommand());
		chooser.addDefault("Baseline", "Baseline");		
		chooser.addObject("CenterBaseline", "CenterBase");
		chooser.addObject("RightSwitch", "RightSwitch");
		chooser.addObject("LeftSwitch", "LeftSwitch");
		chooser.addObject("CenterSwitch", "CenterSwitch");
		chooser.addObject("RightScale", "RightScale");
		chooser.addObject("LeftScale", "LeftScale");
		chooser.addObject("Test Auto", "Test");
		chooser.addObject("No Auto", "null");
		chooser.addObject("Delay Baseline", "DelayBase");
		chooser.addObject("Test MP", "Motion");
		SmartDashboard.putData("Auto mode", chooser);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		gameData = " ";
		OI.RumbleOP(0);
	}

	@Override
	public void disabledPeriodic() {
		
		Scheduler.getInstance().run();
		gameData = " ";
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
		Robot.DriveSubsystem.Left1.enableCurrentLimit(false);
		Robot.DriveSubsystem.Left2.enableCurrentLimit(false);
		Robot.DriveSubsystem.Right2.enableCurrentLimit(false);
		Robot.DriveSubsystem.Right1.enableCurrentLimit(false);

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
		SmartDashboard.putNumber("LeftSet", leftDistance);
		SmartDashboard.putNumber("RightSet", rightDistance);
		SmartDashboard.putNumber("LeftGet", Robot.DriveSubsystem.Left1.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("RightGet", Robot.DriveSubsystem.Right1.getSelectedSensorPosition(0));
		
		if(gameData==" "){
			
			gameData = DriverStation.getInstance().getGameSpecificMessage();
			if(gameData.charAt(0) == 'L'){
				//Put left auto code here
				SwitchState = true;
				System.out.println("LEFTSWITCH");
			}else if(gameData.charAt(0) == 'R'){
				//Put right auto code here
				SwitchState = false;
				System.out.println("RIGHTSWITCH");
			}
			if(gameData.charAt(1) == 'L'){
				ScaleState = true;
				System.out.println("LEFTSCALE");
			}else if(gameData.charAt(1) == 'R'){
				ScaleState = false;
				System.out.println("RIGHTSCALE");
			}
			// schedule the autonomous command (example)
			String Selected = chooser.getSelected();
			switch(Selected){
			case "CenterSwitch":
				if(SwitchState){
					autonomousCommand = new CenterLeftSwitch();
					System.out.println("CLS");
					System.out.println("CLS");
					System.out.println("CLS");
					System.out.println("CLS");
				}else{
					autonomousCommand = new CenterRightSwitch();
					System.out.println("CRS");
					System.out.println("CRS");
					System.out.println("CRS");
					System.out.println("CRS");
				}
				break;
			case "CenterBase":
				autonomousCommand = new CenterBaseline();
				System.out.println("CBL");
				System.out.println("CBL");
				System.out.println("CBL");
				System.out.println("CBL");
				break;
			case "RightScale":
				if(ScaleState){
					autonomousCommand = new RightLeftScale();
					System.out.println("RLSc");
					System.out.println("RLSc");
					System.out.println("RLSc");
					System.out.println("RLSc");
				}else{
					autonomousCommand = new RightRightScale();
					System.out.println("RRSc");
					System.out.println("RRSc");
					System.out.println("RRSc");
					System.out.println("RRSc");
				}
				break;
			case "RightSwitch":
				if(SwitchState){
					autonomousCommand = new RightLeftSwitch();
					System.out.println("RLS");
					System.out.println("RLS");
					System.out.println("RLS");
					System.out.println("RLS");
				}else{
					autonomousCommand = new RightRightSwitch();
					System.out.println("RRS");
					System.out.println("RRS");
					System.out.println("RRS");
					System.out.println("RRS");
				}
				break;
			case "LeftScale":
				if(ScaleState){
					autonomousCommand = new LeftLeftScale();
					System.out.println("LLSc");
					System.out.println("LLSc");
					System.out.println("LLSc");
					System.out.println("LLSc");
				}else{
					autonomousCommand = new LeftRightScale();
					System.out.println("LRSc");
					System.out.println("LRSc");
					System.out.println("LRSc");
					System.out.println("LRSc");
				}
				break;
			case "LeftSwitch":
				if(SwitchState){
					autonomousCommand = new LeftLeftSwitch();
					System.out.println("LLS");
					System.out.println("LLS");
					System.out.println("LLS");
					System.out.println("LLS");
				}else{
					autonomousCommand = new LeftRightSwitch();
					System.out.println("LRS");
					System.out.println("LRS");
					System.out.println("LRS");
					System.out.println("LRS");
				}
				break;
			case "Baseline":
				autonomousCommand = new Baseline();
				System.out.println("Base");
				System.out.println("Base");
				System.out.println("Base");
				System.out.println("Base");
				break;
				
			case "Test":
				autonomousCommand = new TestForward();
				break;
			case "DelayBase":
				autonomousCommand = new DelayBaseline();
				break;
			case "Motion":
				autonomousCommand = new TestMP();
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
		Robot.DriveSubsystem.Left1.enableCurrentLimit(true);
		Robot.DriveSubsystem.Left2.enableCurrentLimit(true);
		Robot.DriveSubsystem.Right2.enableCurrentLimit(true);
		Robot.DriveSubsystem.Right1.enableCurrentLimit(true);
		gameData = " ";
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
