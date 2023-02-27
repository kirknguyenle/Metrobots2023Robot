// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.team3324.robot;

import frc.team3324.robot.arm.Arm;
import frc.team3324.robot.arm.commands.ControlArm;
import frc.team3324.robot.arm.commands.TelescopeArm;
import frc.team3324.robot.drivetrain.Drivetrain;
import frc.team3324.robot.drivetrain.commands.AutoBalance;
import frc.team3324.robot.drivetrain.commands.Drive;
import frc.team3324.robot.intake.Intake;
import frc.team3324.robot.intake.commands.IntakeCone;
import frc.team3324.robot.intake.commands.IntakeCube;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  // --- INITIALIZE DRIVER CONTROLLERS ---
  private final static CommandXboxController primaryDriver = new CommandXboxController(0);
  private final static CommandXboxController secondaryDriver = new CommandXboxController(1);

  // --- INITIALIZE SUBSYSTEMS ---
  private static Arm arm = new Arm();
  public static Drivetrain drivetrain = new Drivetrain();
  private static Intake intake = new Intake();

  private NetworkTableInstance nt_instance = NetworkTableInstance.getDefault();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  private void configureBindings() {
    // --- DEFAULT COMMANDS ---
    drivetrain.setDefaultCommand(new Drive(drivetrain, primaryDriver::getLeftTriggerAxis, primaryDriver::getRightTriggerAxis, primaryDriver::getLeftX));
    arm.setDefaultCommand(new ControlArm(arm, secondaryDriver::getLeftY));
    primaryDriver.y().whileTrue(new AutoBalance(drivetrain));
    // primaryDriver.rightBumper().whileTrue(new IntakeCone(intake, 0.1));
    // primaryDriver.leftBumper().whileTrue(new IntakeCone(intake, -0.1));
    secondaryDriver.rightTrigger().whileTrue(new IntakeCube(intake, 0.5));
    secondaryDriver.leftTrigger().whileTrue(new IntakeCube(intake, -0.5));
    secondaryDriver.leftBumper().whileTrue(new IntakeCone(intake, -0.5));
    secondaryDriver.rightBumper().whileTrue(new IntakeCone(intake, 0.5));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return null;
  }
}