package org.usfirst.frc.team3944.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;

public class TPALoader {

	private Xbox x;
	private WPI_TalonSRX loaderLeft, loaderRight;
	private DigitalInput loadedSwitch;
	private boolean loading;
	public TPALoader(WPI_TalonSRX loaderLeft, WPI_TalonSRX loaderRight, DigitalInput loadedSwitch, Xbox x){
		this.x = x;
		this.loaderLeft = loaderLeft;
		this.loaderRight = loaderRight;
		this.loadedSwitch = loadedSwitch;
		loading = true;
	}
	
	public void takeIn(){
		if(((x.getRawAxis(2) > .5) && loadedSwitch.get() == true) && loading == true){
			loaderLeft.set(x.getRawAxis(2) * -.75);
			loaderRight.set(x.getRawAxis(2) * .75);
		}
		
		if(((x.getRawAxis(2) < .5)  || loadedSwitch.get() == false) && loading == true){
			loaderLeft.set(0.0);
			loaderRight.set(0.0);
			loading = false;
		}
	}
	
	public void shootOut(){
		if((x.getRawAxis(3) > .5) && loading == false){
			loaderLeft.set(x.getRawAxis(3) * .9);
			loaderRight.set(x.getRawAxis(3) * -.9);
		}
		
		if((x.getRawAxis(3) < .5) && loading == false){
			loaderLeft.set(0.0);
			loaderRight.set(0.0);
			loading = true;
		}
	}
}
