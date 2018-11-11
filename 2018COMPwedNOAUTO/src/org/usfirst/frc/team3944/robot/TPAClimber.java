package org.usfirst.frc.team3944.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;

public class TPAClimber {
	private Xbox x;
	private Servo s;
	private WPI_TalonSRX climberMotor;
	public TPAClimber(Xbox x, Servo s, WPI_TalonSRX climberMotor){
		this.x = x;
		this.s = s;
		this.climberMotor = climberMotor;
	}
	
	public void runServo(){
		if(x.getRawButton(8) == true){
			s.set(0.8);
		}
		if(x.getRawButton(8) == false){
			s.set(0.2);
		}
	}
	
	public void runClimber(){
		if(x.getRawButton(7) == true && x.getRawButton(8) == true){
			//open servo
			s.set(.8);
			Timer.delay(.5);
			climberMotor.set(.05);
		}
		if(x.getRawButton(7) == false && x.getRawButton(8) == false){
			//lock servo
			s.set(0.2);
			climberMotor.set(0.0);
		}
	}
}
