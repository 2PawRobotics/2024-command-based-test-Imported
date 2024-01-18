// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import frc.robot.RobotContainer;
import edu.wpi.first.math.filter.SlewRateLimiter;

public class DriveSubsystem extends SubsystemBase {
  
  CANSparkMax m_frontRight = new CANSparkMax(2, MotorType.kBrushed);
  CANSparkMax m_frontLeft = new CANSparkMax(1, MotorType.kBrushed);
  CANSparkMax m_backRight = new CANSparkMax(4, MotorType.kBrushed);
  CANSparkMax m_backLeft = new CANSparkMax(3, MotorType.kBrushed);
  
  SlewRateLimiter slewRate = new SlewRateLimiter(10);

  public void DriveInit() {
    
   m_backRight.follow(m_frontRight);
   m_backLeft.follow(m_frontLeft);

   m_frontLeft.setInverted(false);
   m_frontRight.setInverted(true);

  }

  /**
   * Example command factory method.
   *
   *  a command
   */
  public void DriveTeleOp() {
    
    double motorSpeed = RobotContainer.XCont.getLeftY();
    motorSpeed = Deadzone(motorSpeed);
    motorSpeed = slewRate.calculate(motorSpeed);

    double turnSpeed = RobotContainer.XCont.getRightX();
    turnSpeed = Deadzone(turnSpeed)*0.75;

    m_frontLeft.set(motorSpeed - turnSpeed);
    m_frontRight.set(motorSpeed + turnSpeed);
    
  }

         
  public double Deadzone (double value) {

    if(Math.abs(value) > 0.075){

      return value;

    }
    else{

      return 0;

    }
  }

  
}
