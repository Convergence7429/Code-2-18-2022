package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {

  static Shooter shooter = new Shooter();
  static DriveTrain drive = new DriveTrain();
  static Intake intake = new Intake();
  static Climber climber = new Climber();
  // CANSparkMax canSparkMax = new CANSparkMax(15, MotorType.kBrushless);

  

  ///////////////////////////////////////////////////////
  // Robot

  @Override
  public void robotInit() {
    drive.isDriverControlEnabled = true;
  }

  @Override
  public void robotPeriodic() {
    
  }

  ////////////////////////////////////////////////////////
  // Autonomous

  @Override
  public void autonomousInit() {
    drive.isDriverControlEnabled = false;
  }

  @Override
  public void autonomousPeriodic() {

  }

  ///////////////////////////////////////////////////////////
  // Tele-operated

  double shooterSpeed = 0.05;
  boolean canSetPosition = true; // front climber angle
  double frontClimberAnglePosition = 0.00;
  boolean shooterActivated = false;

  @Override
  public void teleopInit() {
    //System.out.println("TELEOP INIT CALLED");
    drive.driveTrainInit();
    drive.isDriverControlEnabled = true;
    shooter.shooterInit();
    shooterSpeed = 0.05;
    shooterActivated = true;
  }

  

  @Override
  public void teleopPeriodic() {

    //drive.driveTrainByControls();

    

    System.out.println("intakeMotor: " + intake.intakeMotor.get());
    System.out.println("Shooter set speed: " + shooterSpeed);
    System.out.println("Shooter velocity: " + shooter.masterShooterMotor.getSelectedSensorVelocity());
    System.out.println("Hood Position: " + shooter.hoodMotor.getEncoder().getPosition()); // also measure change in degrees if possible
    System.out.println("Shooter activated: " + shooterActivated);
    //System.out.println("Front Climber Position: " + climber.frontAngleMotor.getEncoder().getPosition());
    System.out.println("*************************************************************");
    

    if(Constants.stick.getRawButtonPressed(7)){
      shooterActivated = !shooterActivated;
    }
    if(Constants.stick.getRawButtonPressed(11)){ // adjust shooter speed
      shooterActivated = true;
      shooterSpeed -= 0.05;
    }
    if(Constants.stick.getRawButtonPressed(12)){
      shooterActivated = true;
      shooterSpeed += 0.05;
    }
    if(shooterActivated){
      shooter.masterShooterMotor.set(ControlMode.PercentOutput, shooterSpeed);
    } else {
      shooter.masterShooterMotor.set(0.0);
    }

    if(Constants.stick.getRawButton(2)){ // run indexer
      intake.indexerMotor.set(-0.4);
    } else {
      intake.indexerMotor.set(0.0);
    }

    if(Constants.stick.getRawButton(1)){ // run intake wheels
      intake.intakeMotor.set(-0.4);
    } else {
      intake.intakeMotor.set(0.0);
    }

    // if(Constants.stick.getRawButton(3)){ // adjust hood
    //   shooter.hoodMotor.set(-0.2);
    // } else if (Constants.stick.getRawButton(4)){
    //   shooter.hoodMotor.set(0.2);
    // } else {
    //   shooter.hoodMotor.set(0.0);
    // }

    //   if(Constants.stick.getRawButton(5)){ // adjust front climber angle
    //     climber.frontAngleMotor.set(0.0);
    //   //climber.frontAngleMotor.set(-0.1);
    //   //climber.frontAngleMotor.getPIDController().setReference(0.1, ControlType.kDutyCycle);
    //   climber.frontAngleMotor.getPIDController().setReference(frontClimberAnglePosition - 2, ControlType.kPosition); // setting reference position further
    //   canSetPosition = true;
    // } else if (Constants.stick.getRawButton(6)){
    //   climber.frontAngleMotor.set(0.0);
    //   //climber.frontAngleMotor.set(0.1);
    //   climber.frontAngleMotor.getPIDController().setReference(frontClimberAnglePosition + 2, ControlType.kPosition); // setting reference position further
    //   canSetPosition = true;
    // } else {
    //   climber.frontAngleMotor.set(0.0);
    //   if(canSetPosition){
    //     frontClimberAnglePosition = climber.frontAngleMotor.getEncoder().getPosition();
    //     canSetPosition = false;
    //   }
    //   climber.frontAngleMotor.getPIDController().setReference(frontClimberAnglePosition, ControlType.kPosition);
    // }

    //   // does this keep you from moving again?
    
  
  }

    
    

  /////////////////////////////////////////////////////////////
  // Test

  @Override
  public void testInit() {

  }

  @Override
  public void testPeriodic() {

  }

  //////////////////////////////////////////////////////////////
  // Disabled

  @Override
  public void disabledInit() {

  }

  @Override
  public void disabledPeriodic() {
    climber.frontAngleMotor.getPIDController().setReference(frontClimberAnglePosition, ControlType.kPosition);
  }
}