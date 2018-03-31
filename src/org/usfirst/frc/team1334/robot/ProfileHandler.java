package org.usfirst.frc.team1334.robot;
import com.ctre.phoenix.*;
import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motion.TrajectoryPoint.TrajectoryDuration;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Notifier;

import java.io.*;
import java.util.*;

import org.usfirst.frc.team1334.robot.commands.AutoDriveCommand;

public class ProfileHandler {
	
	class PeriodicRunnable implements Runnable{
		public void run(){
			Robot.DriveSubsystem.Left1.processMotionProfileBuffer();
			Robot.DriveSubsystem.Right1.processMotionProfileBuffer();
		}
	}
	public SetValueMotionProfile Set = SetValueMotionProfile.Disable;
	public MotionProfileStatus Rstatus = new MotionProfileStatus();
	public MotionProfileStatus Lstatus = new MotionProfileStatus();
	Notifier _n = new Notifier(new PeriodicRunnable());
	public int state = 0;
	String LeftFilePath,RightFilePath;
	Double feetToTicks = AutoDriveCommand.inchestoticks*12;
	Vector <Vector<Double> > LMotionProfile;
	Vector <Vector<Double> > RMotionProfile;
	int looptimeout = -1;
	public ProfileHandler(String leftfilepath, String rightfilepath) throws IOException{
		LMotionProfile = new Vector<Vector<Double> >();
		RMotionProfile = new Vector<Vector<Double> >();
		LeftFilePath = leftfilepath;
		RightFilePath = rightfilepath;
		BufferedReader Lbr = null;
		BufferedReader Rbr = null;
		try{
			Lbr = new BufferedReader(new FileReader(LeftFilePath));
			Rbr = new BufferedReader(new FileReader(RightFilePath));
		}catch(IOException E){
			System.out.println("File not Found");
			
		}
		
		while(true){
			String Lline = null;
			String Rline = null;
			try{
				Lline = 
						Lbr.readLine();
				Rline = 
						Rbr.readLine();
			}catch(IOException E){
				System.out.println("Read Failed");
			}
			if(Lline!=null && Rline != null){
				String[] Lsplits = Lline.split(", ");
				Vector<Double> Lpoint = new Vector<Double>();
				Lpoint.addElement(Double.parseDouble(Lsplits[0]));//distance
				Lpoint.addElement(Double.parseDouble(Lsplits[1]));//velocity
				Lpoint.addElement(Double.parseDouble(Lsplits[2]));//time
				LMotionProfile.addElement(Lpoint);
				String[] Rsplits = Lline.split(", ");
				Vector<Double> Rpoint = new Vector<Double>();
				Rpoint.addElement(Double.parseDouble(Rsplits[0]));//distance
				Rpoint.addElement(Double.parseDouble(Rsplits[1]));//velocity
				Rpoint.addElement(Double.parseDouble(Rsplits[2]));//time
				RMotionProfile.addElement(Rpoint);
			}else{
				break;
			}
		}
		if(Lbr!=null){
			Lbr.close();
		}
		if(Rbr!=null){
			Rbr.close();
		}
		
		Robot.DriveSubsystem.Left1.changeMotionControlFramePeriod(5);
		Robot.DriveSubsystem.Right1.changeMotionControlFramePeriod(5);
		_n.startPeriodic(0.005);
		
		
	}
	
	public void startFilling() throws InterruptedException{
		Robot.DriveSubsystem.Left1.clearMotionProfileHasUnderrun(0);
		Robot.DriveSubsystem.Right1.clearMotionProfileHasUnderrun(0);
		TrajectoryPoint Lpoint = new TrajectoryPoint();
		TrajectoryPoint Rpoint = new TrajectoryPoint();
		for(int i = 0; i < LMotionProfile.size();i++){
			TrajectoryDuration retval = TrajectoryDuration.Trajectory_Duration_0ms;
			Lpoint.position = LMotionProfile.get(i).get(0)*feetToTicks;
			Lpoint.velocity = LMotionProfile.get(i).get(1)*feetToTicks/10;
			Lpoint.profileSlotSelect0 = 0;
			retval = retval.valueOf(LMotionProfile.get(i).get(2).intValue());
			Lpoint.timeDur = retval;
			Lpoint.zeroPos = false;
			Lpoint.isLastPoint= false;
			Rpoint.position = RMotionProfile.get(i).get(0)*feetToTicks;
			Rpoint.velocity = RMotionProfile.get(i).get(1)*feetToTicks/10;
			Rpoint.profileSlotSelect0 = 0;
			retval = retval.valueOf(RMotionProfile.get(i).get(2).intValue());
			Rpoint.timeDur = retval;
			Rpoint.zeroPos = false;
			Rpoint.isLastPoint = false;
			if(i == 0){
				Lpoint.zeroPos = true;
				Rpoint.zeroPos = true;
			}
			if((i+1) == LMotionProfile.size()){
				Lpoint.isLastPoint = true;
				Rpoint.isLastPoint = true;
			}
			Robot.DriveSubsystem.Left1.pushMotionProfileTrajectory(Lpoint);
			Robot.DriveSubsystem.Right1.pushMotionProfileTrajectory(Rpoint);
		}
	}

	public void Reset(){
		Robot.DriveSubsystem.Left1.clearMotionProfileTrajectories();
		Robot.DriveSubsystem.Right1.clearMotionProfileTrajectories();
		
	}
	
	public void HandleMP() throws InterruptedException{
		System.out.println(RMotionProfile.isEmpty() + "isempty");
		if(looptimeout < 0){
			
		}else{
			if(looptimeout == 0){
				Robot.DriveSubsystem.Left1.set(ControlMode.Disabled, 0);
				Robot.DriveSubsystem.Right1.set(ControlMode.Disabled, 0);
			}else{
				--looptimeout;
			}
		}
		
		if(Robot.DriveSubsystem.Right1.getControlMode() != ControlMode.MotionProfile || Robot.DriveSubsystem.Left1.getControlMode() != ControlMode.MotionProfile){
			state = 0;
			looptimeout = -1;
		}else{
			switch(state){
			case 0:
				Set = SetValueMotionProfile.Disable;
				startFilling();
				state = 1;
				looptimeout = 10;
				break;
			case 1:
				if(Rstatus.btmBufferCnt > 5 && Lstatus.btmBufferCnt > 5){
					Set = SetValueMotionProfile.Enable;
					state = 2;
					looptimeout = 10;
				}
			case 2:
				if(!Lstatus.hasUnderrun || !Rstatus.hasUnderrun){
					looptimeout = 10;
				}
				if(Lstatus.activePointValid && Rstatus.activePointValid && Lstatus.isLast && Rstatus.isLast){
					Set = SetValueMotionProfile.Hold;
					state = 0;
					looptimeout = -1;
				}
				
			}
		}
		Robot.DriveSubsystem.Right1.getMotionProfileStatus(Rstatus);
		Robot.DriveSubsystem.Left1.getMotionProfileStatus(Lstatus);
		System.out.println("MLeft " + "Stat: "+ Lstatus + "Dist: " + Math.round(Robot.DriveSubsystem.Left1.getActiveTrajectoryPosition() * 100)/100 + "Vel: "+ Math.round(Robot.DriveSubsystem.Left1.getActiveTrajectoryVelocity()*100)/100);
		System.out.println("MRight " + "Stat: "+ Rstatus + "Dist: " + Math.round(Robot.DriveSubsystem.Right1.getActiveTrajectoryPosition() * 100)/100 + "Vel: "+ Math.round(Robot.DriveSubsystem.Right1.getActiveTrajectoryVelocity()*100)/100);
	}
	
}
