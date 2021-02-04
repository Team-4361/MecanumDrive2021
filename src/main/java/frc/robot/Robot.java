/**---------------------------------------------------------------------------
Copyright (c) 2017-2018 FIRST. All Rights Reserved.                     
 Open Source Software - may be modified and shared by FRC teams. The code
must be accompanied by the FIRST BSD license file in the root directory of
 the project.                                                               
----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Joystick;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

/**
 * This is a demo program showing how to use Mecanum control with the RobotDrive
 * class.
 */
public class Robot extends TimedRobot {
  //talon channels.
  private static final int kFrontLeftChannel = 2;
  private static final int kRearLeftChannel = 3;
  private static final int kFrontRightChannel = 0;
  private static final int kRearRightChannel = 1;
  
  //variable used to find the power of the talons.
  private double frontLeftPower;
  private double rearLeftPower;
  private double frontRightPower;
  private double rearRightPower;

  //used to determine the deadzone of the joystick.
  private double deadZonePositive = 0.2;
  private double deadZoneNegative = -0.2;
 
  // Declaring each talon.
  private WPI_TalonSRX frontLeft = new WPI_TalonSRX(kFrontLeftChannel);
  private WPI_TalonSRX rearLeft = new WPI_TalonSRX(kRearLeftChannel);
  private WPI_TalonSRX frontRight = new WPI_TalonSRX(kFrontRightChannel);
  private WPI_TalonSRX rearRight = new WPI_TalonSRX(kRearRightChannel);

  private static final int kJoystickChannel = 0;

  private MecanumDrive m_robotDrive;
  private Joystick m_stick;

  //Declares a variable that gets the axis's values after deadzone correction.
  //DO NOT DECLARE OR CHANGE HERE.
  private double xWithDeadzone;
  private double yWithDeadzone;
  private double zWithDeadzone;
  private double gyroscopeAngle = 0.0;

  //Variable used to make the robot move at either slower or full speed. 
  private double speed = 0.5;

  /*private double leftJSX = 0.0;
  private double leftJSY = 0.0;
  private double rightJSX = 0.0;
  private double rightJSY = 0.0;
  private double speed = 1.5;

  private Joystick leftJS, rightJS;
  public JoystickTank(int lPos, int rPos)
  {
    leftJS = new Joystic(lPos);
    rightJS = new Joystick(rPos);
  }*/


  @Override
  public void robotInit()
  {

    // All motors inverted, may need to be changed
    // as strafing is inverted.
    frontLeft.setInverted(true);
    rearLeft.setInverted(true);
    frontRight.setInverted(true);
    rearRight.setInverted(true);

    // Getting the power from each talon for
    // adustments based on the motors.
    frontLeftPower = frontLeft.get();
    rearLeftPower = rearLeft.get();
    frontRightPower = frontRight.get();
    rearRightPower = rearRight.get();



    // Resetting the power for each talon, make adjustments
    // based on inconsistencies from each motor.
    frontLeft.set(frontLeftPower);
    rearLeft.set(rearLeftPower);
    frontRight.set(frontRightPower);
    rearRight.set(rearRightPower);

    m_robotDrive = new MecanumDrive(frontLeft, rearLeft, frontRight, rearRight);
    m_stick = new Joystick(kJoystickChannel);
    
  }

  /*public void run()
  {
    getStickInfo();
    controlSpeed();
  }
  
  public void getStickInfo()
  {
    rightJSX = -1 * rightJS.getAxis(Joystick.getAxis.getX());
    rightJSY = -1 * rightJS.getAxis(Joystick.getAxis.getY());
    leftJSX = -1 * leftJS.getAxis(Joystick.getAxis.getX());
    leftJSY = -1 * leftJS.getAxis(Joystick());
  }
  public void controlSpeed()
  {
    if(leftJS.getRawButton(3))
    {
      speed = 2.5;
    }
    if(leftJS.getRawButton(4))
    {
      speed = 0.75;
    }
    if(leftJS.getRawButton(2))
    {
      speed = 1.5;
    }
    if(leftJS.getRawButton(5))
    {
      speed = 3.5;
    }
  }*/
  
  @Override
  public void teleopPeriodic()
  {
    // Use the joystick X axis for lateral movement, Y axis for forward
    // movement, and Z axis for rotation.

    //Checking for deadzones on the X axis.
    if(m_stick.getX()<=deadZonePositive && m_stick.getX()>=deadZoneNegative)
    {
      xWithDeadzone = 0.0;
    }
    else if(m_stick.getX()>deadZonePositive)
    {
      xWithDeadzone = m_stick.getX()-deadZonePositive;
    }
    else if(m_stick.getX()<deadZoneNegative)
    {
      xWithDeadzone = m_stick.getX()-deadZoneNegative;
    }

    //Checking for deadzones on the Y axis.
    if(m_stick.getY()<=deadZonePositive && m_stick.getY()>=deadZoneNegative)
    {
      yWithDeadzone = 0.0;
    }
    else if(m_stick.getY()>deadZonePositive)
    {
      yWithDeadzone = m_stick.getY()-deadZonePositive;
    }
    else if(m_stick.getY()<deadZoneNegative)
    {
      yWithDeadzone = m_stick.getY()-deadZoneNegative;
    }

    //Checking for deadzones on the Z axis.
    if(m_stick.getZ()<=deadZonePositive && m_stick.getZ()>=deadZoneNegative)
    {
      zWithDeadzone = 0.0;
    }
    else if(m_stick.getZ()>deadZonePositive)
    {
      zWithDeadzone = m_stick.getZ()-deadZonePositive;
    }
    else if(m_stick.getZ()<deadZoneNegative)
    {
      zWithDeadzone = m_stick.getZ()-deadZoneNegative;
    }

    if(m_stick.getPOV() == 0 && speed<=.8)
    {
      speed += .2;
    }
    else if(m_stick.getPOV() == 180 && speed>=.6)
    {
      speed -= .2;
    }
    xWithDeadzone = xWithDeadzone*speed;
    yWithDeadzone = yWithDeadzone*speed;
    if (m_stick.getX()<-0.2)
    { 
      frontRight.setInverted(false);
      rearRight.setInverted(false);
    }
    else if (m_stick.getX()>0.2)
    {
      frontRight.setInverted(true);
      rearRight.setInverted(true); 
    }
    
    //No gyroscope as of 12/15/18. gyroscopeAngle is set to 0.0
    m_robotDrive.driveCartesian(xWithDeadzone, yWithDeadzone, zWithDeadzone, gyroscopeAngle);
  
    /*if(rightJSY > 0.5)
    {
      frontRight.set((rightJSY - 0.5)/2*speed);
      rearRight.set((rightJSY - 0.5)/2*speed);
    }
    else if(rightJSY < -0.5)
    {
      frontRight.set((rightJSY + 0.5)/2*speed);
      rearRight.set((rightJSY + 0.5)/2*speed);
    }
    else
    {
      frontRight.set(0.0);
      rearRight.set(0.0);
    }

    if(leftJSY > 0.5)
    {
      frontLeft.set((leftJSY - 0.5)/2*speed);
      rearLeft.set((leftJSY - 0.5)/2*speed);
    }
    else if(leftJSY < -0.5)
    {
      frontLeft.set((leftJSY + 0.5)/2*speed);
      rearLeft.set((leftJSY + 0.5)/2*speed);
    }
    else
    {
      frontLeft.set(0.0);
      rearLeft.set(0.0);
    }

    if(rightJSX < -0.5)
    {
      frontRight.set((rightJSX + 0.5)/2*speed);
      rearRight.set(-1 * (rightJSX + 0.5)/2*speed);
    }
    else if(rightJSX > 0.5)
    {
      frontRight.set((rightJSX - 0.5)/2*speed);
      rearRight.set(-1 * (rightJSX - 0.5)/2*speed);
    }

    if(leftJSX < -0.5)
    {
      frontLeft.set((leftJSX + 0.5)/2*speed);
      rearLeft.set(-1 * (leftJSX + 0.5)/2*speed);
    }
    else if(rightJSX > 0.5)
    {
      frontLeft.set((leftJSX - 0.5)/2*speed);
      rearLeft.set(-1 * (leftJSX - 0.5)/2*speed);
    }*/
  }
}
