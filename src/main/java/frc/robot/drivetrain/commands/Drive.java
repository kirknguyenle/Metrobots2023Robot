// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.drivetrain.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.RobotContainer;
import frc.robot.drivetrain.Drivetrain;
import frc.robot.util.Constants;

public class Drive extends CommandBase {
  Drivetrain drivetrain;
  double leftTrigger;
  double rightTrigger;
  DoubleSupplier turnSpeedSupplier;

  double throttle = 0.0;

  /** Creates a new Drive. */
  public Drive(Drivetrain drivetrain, double leftTrigger, double rightTrigger, DoubleSupplier turnSpeedSupplier) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
    this.drivetrain = drivetrain;
    this.leftTrigger = leftTrigger;
    this.rightTrigger = rightTrigger;
    this.turnSpeedSupplier = turnSpeedSupplier;
  }

  // Called when the command is initially scheduled.
  public void initialize(Drivetrain drivetrain) {
    addRequirements(drivetrain);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double turnSpeed = turnSpeedSupplier.getAsDouble();
    
    throttle = rightTrigger - leftTrigger;

    if (turnSpeed < Constants.Drivetrain.CONTROLLER_DEADZONE && turnSpeed > -Constants.Drivetrain.CONTROLLER_DEADZONE) {
      turnSpeed = 0;
    }

    drivetrain.curvatureDrive(throttle, -turnSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // we dont wanna stop driving during a match, so this is always false
    return false;
  }
}
