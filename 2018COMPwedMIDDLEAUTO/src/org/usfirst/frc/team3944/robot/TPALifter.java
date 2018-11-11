package org.usfirst.frc.team3944.robot;
//MIDDLE
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TPALifter {

	private Xbox x;
	public static WPI_TalonSRX lifter = new WPI_TalonSRX(TPARobotMap.lifterCAN_ID);
	double setpoint;
	public static double position1 = 0; //different
	public static double position2 = 3; //positions
	public static double position3 = 29.5; //in
	public static double position4 = 40.5; //inches
	DigitalInput switch1,switch2,switch3,switch4;
	int c;
	public TPALifter(Xbox x, DigitalInput switch1, DigitalInput switch2, DigitalInput switch3, DigitalInput switch4){
		this.x = x;
		c = 0;
		this.switch1 = switch1;
		this.switch2 = switch2;
		this.switch3 = switch3;
		this.switch4 = switch4;
		lifter.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.QuadEncoder, 0, 0);
		lifter.setSensorPhase(true);
		lifter.setInverted(true);
		lifter.setSelectedSensorPosition(0, 0, 0);
		lifter.configClosedloopRamp(.5, 0);
		lifter.configAllowableClosedloopError(0, 2000, 0);
		lifter.config_kP(0, .4, 0);
		lifter.config_kI(0, 0, 0);
		lifter.config_kD(0, 0, 0);
		setpoint = 0.0;
		convertPos();
		SmartDashboard.putNumber("Encoder: ", lifter.getSelectedSensorPosition(0));
	}
	
	public void runManual(){
		if(x.getRawButton(TPARobotMap.liftDownButton) == true &&
				x.getRawButton(TPARobotMap.lifterUpButton) == false && switch2.get() == true){
			lifter.set(-.3);
			lifter.setSensorPhase(true);
			downPID();
			setpoint = lifter.getSelectedSensorPosition(0);
		}
		if(x.getRawButton(TPARobotMap.liftDownButton) == false &&
				x.getRawButton(TPARobotMap.lifterUpButton) == true && switch4.get() == true){
			lifter.setSensorPhase(true);
			lifter.set(.9);
			upPID();
			setpoint = lifter.getSelectedSensorPosition(0);
		}
		if(x.getRawButton(TPARobotMap.liftDownButton) == false &&
				x.getRawButton(TPARobotMap.lifterUpButton) == false){
			lifter.setSensorPhase(true);
			lifter.set(0.0);
			lifter.set(ControlMode.Position, setpoint);
		}
		SmartDashboard.putNumber("Encoder: ", lifter.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Setpoint: ", setpoint);
	}
	
	public void runPIDPos(){
		if(x.getRawButton(TPARobotMap.position1Button) == true){
			setpoint = position1;
			configUpDownPID(position1);
			lifter.set(ControlMode.Position, setpoint);
			
		}
		if(x.getRawButton(TPARobotMap.position2Button) == true){
			setpoint = position2;
			configUpDownPID(setpoint);
			lifter.set(ControlMode.Position, setpoint);
		}
		if(x.getRawButton(TPARobotMap.position3Button) == true){
			setpoint = position3;
			configUpDownPID(setpoint);
			lifter.set(ControlMode.Position, setpoint);
		}
		if(x.getRawButton(TPARobotMap.position4Button) == true){
			setpoint = position4;
			configUpDownPID(setpoint);
			lifter.set(ControlMode.Position, setpoint);
		}
		SmartDashboard.putNumber("Encoder: ", lifter.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Setpoint: ", setpoint);
	}
	public void runSwitchPos(){
		SmartDashboard.putNumber("c: ", c);
		if(x.getRawButton(TPARobotMap.position1Button) == true && switch1.get() == true && c == 0){
			c = 1;
		}
		if(c == 1 && switch1.get() == true){
			configUpDownSwitch(position1, 1.0);
		}
		if(c == 1 && switch1.get() == false){
			lifter.set(0.0);
			c = 0;
		}
		if(x.getRawButton(TPARobotMap.position2Button) == true && switch2.get() == true && c == 0){
			c = 2;
		}
		if(c == 2 && switch2.get() == true){
			configUpDownSwitch(position2, 1.0);
		}
		if(c == 2 && switch2.get() == false){
			lifter.set(0.0);
			c = 0;
		}
		if(x.getRawButton(TPARobotMap.position3Button) == true && switch3.get() == true && c == 0){
			c = 3;
		}
		if( c == 3 && switch3.get() == true){
			configUpDownSwitch(position3, 1.0);
		}
		if(c == 3 && switch3.get() == false){
			lifter.set(0.0);
			c = 0;
		}
		if(x.getRawButton(TPARobotMap.position4Button) == true && switch4.get() == true && c == 0){
			c = 4;
		}
		if( c == 4 && switch4.get() == true){
			configUpDownSwitch(position4, 1.0);
		}
		if(c == 4 && switch4.get() == false){
			lifter.set(0.0);
			c = 0;
		}
	}
	void convertPos(){
		double convertionRate = 903; //amount of encoder clicks per inch
		position1 = position1 * convertionRate;
		position2 = position2 * convertionRate;
		position3 = position3 * convertionRate;
		position4 = position4 * convertionRate;
	}
	
	void upPID(){
		lifter.setSensorPhase(true);
		lifter.configClosedloopRamp(.5, 0);
		lifter.config_kP(0, .17, 0);
		lifter.config_kI(0, 0, 0);
		lifter.config_kD(0, .18, 0);
		SmartDashboard.putNumber("Encoder: ", lifter.getSelectedSensorPosition(0));
		SmartDashboard.putString("USING: ", "UP");
		
	}
	
	void downPID(){
		lifter.setSensorPhase(true);
		lifter.configClosedloopRamp(.5, 0);
		lifter.config_kP(0, .015, 0);
		lifter.config_kI(0, 0, 0);
		lifter.config_kD(0, 0.01, 0);
		SmartDashboard.putNumber("Encoder: ", lifter.getSelectedSensorPosition(0));
		SmartDashboard.putString("USING: ", "DOWN");
		
	}
	
	void configUpDownPID(double desired){
		lifter.setSensorPhase(true);
		if(lifter.getSelectedSensorPosition(0) < desired){
			SmartDashboard.putString("This should run up!", "");
			upPID();
		}
		if(lifter.getSelectedSensorPosition(0) > desired){
			SmartDashboard.putString("This should run down!", "");
			downPID();
		}
	}
	void configUpDownSwitch(double desiredPos, double desiredSpeed){
		if(lifter.getSelectedSensorPosition(0) < desiredPos){
			SmartDashboard.putString("This should run up!", "");
			lifter.set(desiredSpeed * .75);
		}
		if(lifter.getSelectedSensorPosition(0) > desiredPos){
			SmartDashboard.putString("This should run down!", "");
			lifter.set(desiredSpeed * -.4);
		}
		if(lifter.getSelectedSensorPosition(0) == desiredPos ||
				(lifter.getSelectedSensorPosition(0) < desiredPos+2000 && 
						lifter.getSelectedSensorPosition(0) > desiredPos-2000)){
			lifter.set(0.0);
			c = 0;
		}
	}
	
}
