package org.usfirst.frc.team3944.robot;

import edu.wpi.first.wpilibj.Servo;

public class TPAClimber {
	private Xbox x;
	private Servo s;
	public TPAClimber(Xbox x, Servo s){
		this.x = x;
		this.s = s;
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
}
