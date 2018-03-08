package org.usfirst.frc.team1334.robot.subsystems;

import org.usfirst.frc.team1334.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 *
 */
public class DriveSubsystem extends PIDSubsystem {
	
	
	public int kSlotIdx = 0;

	/*
	 * Talon SRX/ Victor SPX will supported multiple (cascaded) PID loops. For
	 * now we just want the primary one.
	 */
	public int kPIDLoopIdx = 0;

	/*
	 * set to zero to skip waiting for confirmation, set to nonzero to wait and
	 * report to DS if action fails.
	 */
	public int kTimeoutMs = 10;
	
	/* choose so that Talon does not report sensor out of phase */
	public boolean kSensorPhase = true;

	/* choose based on what direction you want to be positive,
		this does not affect motor invert. */
	public boolean kMotorInvert = false;
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	
	public double kToleranceDegrees = 2.0;
	public static float angle = 0;
	public int vMulti = 200;
	public double rotateToAngleRate;
	static int b;
	public AHRS ahrs;
	public boolean isstill = true;
	public float minimalvoltage = 0.23f;
	public double post = 0;
	public double negt = 0;
	
	public TalonSRX Left1 = new TalonSRX(RobotMap.Left1);
	public TalonSRX Left2 = new TalonSRX(RobotMap.Left2);
	public TalonSRX Right1 = new TalonSRX(RobotMap.Right1);
	public TalonSRX Right2 = new TalonSRX(RobotMap.Right2);
	public Compressor c = new Compressor(RobotMap.Compressor);
	public static DoubleSolenoid gShift = new DoubleSolenoid(RobotMap.shift1, RobotMap.shift2);

	public DriveSubsystem() {
		super("Drive", 0.01,0.0,0,0);
		super.getPIDController().setInputRange(-180.0f,  180.0f);
        super.getPIDController().setOutputRange(-1.0, 1.0);
        setAbsoluteTolerance(kToleranceDegrees);
        super.getPIDController().setContinuous(true);
	}
	
	public void CompressorControl(){
		c.setClosedLoopControl(true);
	}
	public void setPIDDRIVE(){
		Left1.set(ControlMode.Position, 0);
		Right1.set(ControlMode.Position, 0);
	}

	public void setREGULARDRIVE(){
		Left1.set(ControlMode.Velocity, 0);
		Right1.set(ControlMode.Velocity, 0);
	}

	public void TankDrive(double left, double right){
		Left1.set(ControlMode.PercentOutput, left);
		Left2.set(ControlMode.PercentOutput, left);
		Right1.set(ControlMode.PercentOutput, right);
		Right2.set(ControlMode.PercentOutput, right);
	}

	public void ArcadeDrive(double speed, double turn){
		
		TankDrive(-speed -turn, speed - turn);
		
		//System.out.println(Left1.getClosedLoopError(0)); // prints amount of error of both encoders
		//System.out.println(Right1.getClosedLoopError(0));
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		Left2.set(ControlMode.Follower, 0); // Sets Left2 Talon to follow movements of the Left1 Talon
		Right2.set(ControlMode.Follower, 2); // Sets Right2 Talon to follow movements of the Right1 Talon
		Left1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 50);
		// Arg - Feedback Device Type, PID IDX, timeout (ms)
		Right1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 50);
		c.setClosedLoopControl(true);
		// sets compressor to a closed loop control
		
		try {
			ahrs = new AHRS(SPI.Port.kMXP);
		} catch (RuntimeException ex) {
			DriverStation.reportError("Error instancing navX MXP: " + ex.getMessage(), true);
		}
		
		ResetGyroAngle();
		super.getPIDController().enable();
	}
	
	public void shiftGear (boolean up, boolean down){ // Gear shift method
		if (up) { // if high gear is activated
			gShift.set(Value.kReverse);
		}
		else if (down) {
			gShift.set(Value.kForward);
		}
	}
	
	public double GyroDrive(double turn){
	    	angle+=turn;
	    	b = (int)angle/180;
	    	angle = (float) (angle * Math.pow(-1, b));
	    	return angle;
	}
	 
	public void ResetGyroAngle(){
	    	ahrs.reset();
	    	angle = 0;
	}

	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return ahrs.pidGet();
	}

	@Override
	public void usePIDOutput(double output) {
		// TODO Auto-generated method stub
		if(output >=0.8 ){
			rotateToAngleRate = 0.8;
		}else if(output<=-0.8){
			rotateToAngleRate = -0.8;
		}else{
			rotateToAngleRate = output;
		}
		if(isstill){
			if(this.getPIDController().getError()>=1 || this.getPIDController().getError()<=-1){
				if(rotateToAngleRate<= minimalvoltage && rotateToAngleRate > 0){
					post +=1;
					rotateToAngleRate = minimalvoltage - ((1-this.getPIDController().getError())/65)+post/100;
				}else if(rotateToAngleRate>= -minimalvoltage && rotateToAngleRate <0){
					negt+=1;
					rotateToAngleRate = -minimalvoltage + ((1- this.getPIDController().getError())/65)-negt/100;
				}
				
			}else{
				post = 0;
				negt = 0;
			}
		}
	}
}
