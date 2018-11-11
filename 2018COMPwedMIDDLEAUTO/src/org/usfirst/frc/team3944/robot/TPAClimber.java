package org.usfirst.frc.team3944.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;

public class TPAClimber {
	private Xbox x;
	private Servo s;
	private WPI_TalonSRX climberMotor;
	public TPAClimber(Xbox x, WPI_TalonSRX climber){
		this.x = x;
		climberMotor = climber;
		//this.s = s;
		//s.set(1.0);
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
		if(x.getRawButton(5) == true && x.getRawButton(6) == false){
			//open servo
			//s.set(.8);
			//Timer.delay(.5);
			//DOWN
			climberMotor.set(-1.0);
		}
		if(x.getRawButton(5) == false && x.getRawButton(6) == true){
			//lock servo
			//s.set(0.2);
			//UP
			climberMotor.set(0.6);
		}
		if(x.getRawButton(5) == false && x.getRawButton(6) == false){
			climberMotor.set(0.0);
		}
	}
}
