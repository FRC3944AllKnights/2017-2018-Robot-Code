package org.usfirst.frc.team3944.robot;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
 
// Main Class Body
public class TPARobotDrive extends DifferentialDrive {
	// Declare Throttle Variables used for arcadeDrive
	private double t_yAxis;
	private double t_zAxis;
	 
	// This is an object declaration that sets a named location in memory. It is a joystick 
	// object of type TPAJoystick. Or a reference variable of type TPAJoystick. 
	// "final" tells the compiler that subclass cannot override.
	private final Xbox x;
	// Instantiation of public static CANTalon Object
	public static WPI_TalonSRX frontLeftMotor = new WPI_TalonSRX(1);
	public static WPI_TalonSRX frontRightMotor = new WPI_TalonSRX(5);
	
	// Constructor
	public TPARobotDrive(WPI_TalonSRX frontLeftMotor, WPI_TalonSRX frontRightMotor, Xbox x) {
		super(frontLeftMotor, frontRightMotor);
	    // named the same so you need the this, otherwise you won't need the this.
		this.x=x;
	}
	
	public void arcadeDrive_throttle() {
			
		t_yAxis = x.getRawAxis(1) * -.7; 
		t_zAxis = x.getRawAxis(4) * .7; 
		
		arcadeDrive(t_yAxis, t_zAxis);
	}	
	
//	public void tankDrive_throttle(){
//		tankDrive(x.getRawAxis(1) * -.7, x.getRawAxis(5) * -.7);
//	}
}