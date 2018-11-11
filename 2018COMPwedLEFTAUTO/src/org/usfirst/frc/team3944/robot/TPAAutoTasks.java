package org.usfirst.frc.team3944.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TPAAutoTasks implements PIDOutput{
	
	private PIDController pid;
	private static double pidOutput;
	private AHRS ahrs;
	double setpoint;
	int taskCounterA;
	private Timer t;
	
	public TPAAutoTasks(AHRS ahrs){
		this.ahrs = ahrs;
		pid = new PIDController(0.03,0.1,0.1,0.01,ahrs,this);
		pid.setInputRange(-180.0f, 180.0f);
		pid.setOutputRange(-1.0, 1.0);
		pid.setAbsoluteTolerance(1.0f);
		pid.setContinuous(true);
		this.ahrs.reset();
		taskCounterA = 0;
		TPARobotDrive.frontLeftMotor.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.QuadEncoder, 0, 0);
		TPARobotDrive.frontLeftMotor.setSensorPhase(true);
		TPARobotDrive.frontLeftMotor.setSelectedSensorPosition(0, 0, 0);
		t = new Timer();
		TPALifter.lifter.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.QuadEncoder, 0, 0);
		TPALifter.lifter.setSensorPhase(true);
		TPALifter.lifter.setSelectedSensorPosition(0, 0, 0);
		convertPos();
	}
	
	public void startTask(){
		//conditions for running no goal
		if(DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'R' &&
				DriverStation.getInstance().getGameSpecificMessage().charAt(1) == 'R'){
			if((Math.abs(TPARobotDrive.frontLeftMotor.getSelectedSensorPosition(0)) < calculateDistance(105)) &&
					(taskCounterA == 0)){
				makeStraight(.5);
				configUpDownPID(TPALifter.position2);
				TPALifter.lifter.set(ControlMode.Position, TPALifter.position2);
			}
			if((Math.abs(TPARobotDrive.frontLeftMotor.getSelectedSensorPosition(0)) >= calculateDistance(105)) &&
					(taskCounterA == 0)){
				stopAndReset1();
				configUpDownPID(TPALifter.position2);
				TPALifter.lifter.set(ControlMode.Position, TPALifter.position2);
			}
		}
		//conditions for running small goal (153 inches)
		if(DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L' &&
				DriverStation.getInstance().getGameSpecificMessage().charAt(1) != 'L'){
		if((Math.abs(TPARobotDrive.frontLeftMotor.getSelectedSensorPosition(0)) < calculateDistance(105)) &&
				(taskCounterA == 0)){
			makeStraight(.5);
			configUpDownPID(TPALifter.position2);
			TPALifter.lifter.set(ControlMode.Position, TPALifter.position2);
		}
		if((Math.abs(TPARobotDrive.frontLeftMotor.getSelectedSensorPosition(0)) >= calculateDistance(105)) &&
				(taskCounterA == 0)){
			stopAndReset1();
		}
		if(taskCounterA == 1){
			turn1(taskCounterA, -90.0f);
			configUpDownPID(TPALifter.position2);
			TPALifter.lifter.set(ControlMode.Position, TPALifter.position2);
		}
		if(ahrs.getAngle() > -90.75 && ahrs.getAngle() < -90.25 &&
				(taskCounterA == 1)){
			stopAndReset2();
		}
		if((Math.abs(TPARobotDrive.frontLeftMotor.getSelectedSensorPosition(0)) < calculateDistance(70)) &&
				(taskCounterA == 2)){
			alignmentVector(.4, -90.0f, taskCounterA);
			//change position!!!
			configUpDownPID(TPALifter.position3);
			TPALifter.lifter.set(ControlMode.Position, TPALifter.position3);
		}
		if((Math.abs(TPARobotDrive.frontLeftMotor.getSelectedSensorPosition(0)) >= calculateDistance(70)) &&
				(taskCounterA == 2)){
			stopAndReset3();
		}
		if(taskCounterA == 3){
			configUpDownPID(TPALifter.position3);
			TPALifter.lifter.set(ControlMode.Position, TPALifter.position3);
			TPALoader.loaderLeft.set(-.5);
			TPALoader.loaderRight.set(.5);
		}
		}
		//conditions for running big goal 310inches
		if((DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'R' &&
				DriverStation.getInstance().getGameSpecificMessage().charAt(1) == 'L') ||
		(DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L' &&
		DriverStation.getInstance().getGameSpecificMessage().charAt(1) == 'L')){
			if((Math.abs(TPARobotDrive.frontLeftMotor.getSelectedSensorPosition(0)) < calculateDistance(105)) &&
					(taskCounterA == 0)){
				makeStraight(.5);
				configUpDownPID(TPALifter.position2);
				TPALifter.lifter.set(ControlMode.Position, TPALifter.position2);
			}
			if((Math.abs(TPARobotDrive.frontLeftMotor.getSelectedSensorPosition(0)) >= calculateDistance(105)) &&
					(taskCounterA == 0)){
				stopAndReset1();
			}
			if(taskCounterA == 1){
				turn1(taskCounterA, -90.0f);
				configUpDownPID(TPALifter.position2);
				TPALifter.lifter.set(ControlMode.Position, TPALifter.position2);
			}
			if(ahrs.getAngle() > -90.75 && ahrs.getAngle() < -90.25 &&
					(taskCounterA == 1)){
				stopAndReset2();
			}
			if((Math.abs(TPARobotDrive.frontLeftMotor.getSelectedSensorPosition(0)) < calculateDistance(70)) &&
					(taskCounterA == 2)){
				alignmentVector(.4, -90.0f, taskCounterA);
				//change position!!!
				configUpDownPID(TPALifter.position4);
				TPALifter.lifter.set(ControlMode.Position, TPALifter.position4);
			}
			if((Math.abs(TPARobotDrive.frontLeftMotor.getSelectedSensorPosition(0)) >= calculateDistance(70)) &&
					(taskCounterA == 2)){
				stopAndReset3();
			}
			if(taskCounterA == 3){
				configUpDownPID(TPALifter.position4);
				TPALifter.lifter.set(ControlMode.Position, TPALifter.position4);
				TPALoader.loaderLeft.set(-.5);
				TPALoader.loaderRight.set(.5);
			}
		}
	}
	
	//probably wont work because of the new omni wheels
    public double calculateDistance(double inches){
   	 // bot travels 23.5 inches per 1 wheel revolution, 1440 encoder ticks per 1 wheel revolution
   	 // 1440 encoder ticks divided by wheel circumf in inches 23.5
 	     double encoderTicksPerInch = 4180 / 82;
 	     //50.9756098
   	 double targetEncValue =  (inches * encoderTicksPerInch);
   	 
   	 return targetEncValue;
   }
    
	void convertPos(){
		double convertionRate = 903; //amount of encoder clicks per inch
		TPALifter.position1 = TPALifter.position1 * convertionRate;
		TPALifter.position2 = TPALifter.position2 * convertionRate;
		TPALifter.position3 = TPALifter.position3 * convertionRate;
		TPALifter.position4 = TPALifter.position4 * convertionRate;
	}
    
	// Enables robot to drive in a straight line
    public void makeStraight(double speed){
  		
  		if(ahrs.getAngle() < 359.7 || ahrs.getAngle() > .2){ 		
    		pid.setSetpoint(0.0f);
    		double turnDegrees;
    		pid.enable();
    		turnDegrees = pidOutput;
    		Robot.robotDrive.arcadeDrive(speed, turnDegrees * -0.17);
    		SmartDashboard.putString("ahrs.getyaw(): ", "" +ahrs.getYaw());
    		SmartDashboard.putString("ahrs.getAngle(): ", "" +ahrs.getAngle());  		
    	}else{
    		Robot.robotDrive.arcadeDrive(speed, 0.0);
     		SmartDashboard.putString("ahrs.getyaw(): ", "" +ahrs.getYaw());
    		SmartDashboard.putString("ahrs.getAngle(): ", "" +ahrs.getAngle());   		
    	}
    } // End makeStraight()
    
	public void turn1(int counter, float angleOfRotation){		
		if(counter == taskCounterA){  		
			pid.setSetpoint(angleOfRotation);
			double turn1Degrees;
			pid.enable();
			turn1Degrees = pidOutput;
			Robot.robotDrive.arcadeDrive(0.0, turn1Degrees * -0.48); 		
		}
	}  // End turn
	
	//DO NOT USE FOR 0
	public void alignmentVector(double speed, float angleOfAlignment, int counter) {
	 if(angleOfAlignment < 0){
		double absoluteAngle = 360 - (Math.abs((double) angleOfAlignment)); 
	 if(ahrs.getAngle() < ((double) absoluteAngle - .25) || ahrs.getAngle() > ((double) absoluteAngle + .15)
			 && counter == taskCounterA){		
		pid.setSetpoint(angleOfAlignment);
		double alignmentDegrees;
		pid.enable();
		alignmentDegrees = pidOutput;
		Robot.robotDrive.arcadeDrive(speed, alignmentDegrees * -.27);		
	 }else{
		Robot.robotDrive.arcadeDrive(speed, 0.0);
	 }
	 }
	 
	 if(angleOfAlignment > 0){
	 if(ahrs.getAngle() < ((double) angleOfAlignment - .15) || ahrs.getAngle() > ((double) angleOfAlignment + .15)
			 && counter == taskCounterA){		
		pid.setSetpoint(angleOfAlignment);
		double alignmentDegrees;
		pid.enable();
		alignmentDegrees = pidOutput;
		Robot.robotDrive.arcadeDrive(speed, alignmentDegrees * -.27);		
	 }else{
		Robot.robotDrive.arcadeDrive(speed, 0.0);
	 }
	 }
	 
	} // End alignmentVector
	public void stopAndReset1(){
		if (taskCounterA == 0){
			Robot.robotDrive.arcadeDrive(0.0,0.0);
			TPARobotDrive.frontLeftMotor.setSelectedSensorPosition(0, 0, 0);
			SmartDashboard.putString("reseting stopAndReset1: ", "" +taskCounterA);
			taskCounterA = 1;
			Timer.delay(.1);
		}
	}
	
	public void stopAndReset2(){
		if (taskCounterA == 1){
			Robot.robotDrive.arcadeDrive(0.0,0.0);
			TPARobotDrive.frontLeftMotor.setSelectedSensorPosition(0, 0, 0);
			SmartDashboard.putString("reseting stopAndReset1: ", "" +taskCounterA);
			taskCounterA = 2;
			Timer.delay(.1);
		}
	}
	public void stopAndReset3(){
		if (taskCounterA == 2){
			Robot.robotDrive.arcadeDrive(0.0,0.0);
			TPARobotDrive.frontLeftMotor.setSelectedSensorPosition(0, 0, 0);
			SmartDashboard.putString("reseting stopAndReset1: ", "" +taskCounterA);
			taskCounterA = 3;
			Timer.delay(.1);
		}
	}
	
	public void stopAndReset4(){
		if (taskCounterA == 3){
			Robot.robotDrive.arcadeDrive(0.0,0.0);
			TPARobotDrive.frontLeftMotor.setSelectedSensorPosition(0, 0, 0);
			SmartDashboard.putString("reseting stopAndReset1: ", "" +taskCounterA);
			taskCounterA = 4;
			Timer.delay(2);
			t.start();
		}
	}
	
	public void stopAndReset5(){
		if (taskCounterA == 4){
			Robot.robotDrive.arcadeDrive(0.0,0.0);
			TPARobotDrive.frontLeftMotor.setSelectedSensorPosition(0, 0, 0);
			SmartDashboard.putString("reseting stopAndReset1: ", "" +taskCounterA);
			taskCounterA = 5;
			Timer.delay(.1);
			t.stop();
		}
	}
	
	void upPID(){
		TPALifter.lifter.configClosedloopRamp(.5, 0);
		TPALifter.lifter.config_kP(0, .23, 0);
		TPALifter.lifter.config_kI(0, 0, 0);
		TPALifter.lifter.config_kD(0, .3, 0);
		
	}
	
	void downPID(){
		TPALifter.lifter.configClosedloopRamp(.5, 0);
		TPALifter.lifter.config_kP(0, .06, 0);
		TPALifter.lifter.config_kI(0, 0, 0);
		TPALifter.lifter.config_kD(0, 0.01, 0);
		
	}
	
	void configUpDownPID(double desired){
		if(TPALifter.lifter.getSelectedSensorPosition(0) < desired){
			SmartDashboard.putString("This should run up!", "");
			upPID();
		}
		if(TPALifter.lifter.getSelectedSensorPosition(0) > desired){
			SmartDashboard.putString("This should run down!", "");
			downPID();
		}
	}
	boolean speedBoolean1 = false;
	boolean speedBoolean2 = false;
	boolean speedBoolean3 = false;
	Timer speedTimer = new Timer();
	double newSpeed = 0.0;
	/**
	 * 
	 * @param minVal	double: min speed value
	 * @param maxVal	double: max speed value
	 * @param timeSpan	double: time it takes so accelerate to full speed (seconds)
	 * @return
	 */
	public double increaseSpeed(double minVal, double maxVal, double timeSpan){
		if(speedBoolean1 == false && speedBoolean2 == false && speedBoolean3 == false){
			speedTimer.start();
			newSpeed = minVal;
			speedBoolean1 = true;
			speedBoolean2 = false;
			speedBoolean3 = false;
		}
		if(speedBoolean1 == true && speedBoolean2 == false && speedBoolean3 == false && speedTimer.get() > 0){
			newSpeed = newSpeed + ((maxVal - minVal)/(timeSpan / 100));
			if(speedTimer.get() >= (timeSpan)){
				//The speed should already be at maxVal but setting this just to make sure.
				newSpeed = maxVal;
				speedBoolean1 = true;
				speedBoolean2 = true;
				speedBoolean3 = false;
			}
		}
		if(speedBoolean1 == true && speedBoolean2 == true && speedBoolean3 == true && speedTimer.get() > (timeSpan)){
			newSpeed = 0;
		}
		return newSpeed;
	}
	@Override
	public void pidWrite(double output) {
		pidOutput = output;
	}

}
