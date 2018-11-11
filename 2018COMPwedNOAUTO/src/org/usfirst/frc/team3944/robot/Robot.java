package org.usfirst.frc.team3944.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	private Xbox x;
	public TPARobotDrive robotDrive;
	private TPALoader loader;
	private WPI_TalonSRX loaderLeft, loaderRight, lifterMotor, climberMotor;
	private DigitalInput loaderSwitch, one, two, three, four, five, six, seven;
	private TPALifter lifter;
	private TPAClimber climber;
	private Servo s;
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {

		CameraServer.getInstance().startAutomaticCapture();
		x = new Xbox(TPARobotMap.XboxPort);
		robotDrive = new TPARobotDrive(new SpeedControllerGroup(TPARobotDrive.frontLeftMotor, TPARobotDrive.backLeftMotor),
						new SpeedControllerGroup(TPARobotDrive.frontRightMotor, TPARobotDrive.backRightMotor), x);
		loaderLeft = new WPI_TalonSRX(TPARobotMap.loaderLeftCAN_ID);
		loaderRight = new WPI_TalonSRX(TPARobotMap.loaderRightCAN_ID);
		loaderSwitch = new DigitalInput(TPARobotMap.loaderSwitchPort);
		loader = new TPALoader(loaderLeft, loaderRight, loaderSwitch, x);
		one = new DigitalInput(TPARobotMap.digitalInputOnePort);
		two = new DigitalInput(TPARobotMap.digitalInputTwoPort);
		three = new DigitalInput(TPARobotMap.digitalInputThreePort);
		four = new DigitalInput(TPARobotMap.digitalInputFourPort);
		five = new DigitalInput(TPARobotMap.digitalInputFivePort);
		six = new DigitalInput(TPARobotMap.digitalInputSixPort);
		seven = new DigitalInput(TPARobotMap.digitalInputSevenPort);
		lifterMotor = new WPI_TalonSRX(TPARobotMap.lifterCAN_ID);
		climberMotor = new WPI_TalonSRX(TPARobotMap.climberCAN_ID);
		lifter = new TPALifter(x,two,three,four,five);
		s = new Servo(1);
		climber = new TPAClimber(x,s,climberMotor);
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		robotDrive.arcadeDrive_throttle();
		loader.takeIn();
		loader.shootOut();
		lifter.runManual();
		lifter.runPIDPos();
		//climber.runServo();
		SmartDashboard.putString("DigitalInput 0: ", ""+loaderSwitch.get());
		SmartDashboard.putString("DigitalInput 1: ", ""+one.get());
		SmartDashboard.putString("DigitalInput 2: ", ""+two.get());
		SmartDashboard.putString("DigitalInput 3: ", ""+three.get());
		SmartDashboard.putString("DigitalInput 4: ", ""+four.get());
		SmartDashboard.putString("DigitalInput 5: ", ""+five.get());
		SmartDashboard.putString("DigitalInput 6: ", ""+six.get());
		SmartDashboard.putString("DigitalInput 7: ", ""+seven.get());
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}

