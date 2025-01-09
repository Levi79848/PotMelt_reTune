package org.firstinspires.ftc.teamcode.Auto;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Auto.Actions.ArmActions;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Config
@Autonomous(name = "Four_Spec", group = "Autonomous")
public class Four_Spec extends LinearOpMode {

    @Override
    public void runOpMode() {

        Pose2d startPose = new Pose2d(-10, 63, Math.toRadians(90));

        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);

        if (hardwareMap == null) {
            telemetry.addData("Error", "hardwareMap is not initialized");
            telemetry.update();
            return;
        }


        ArmActions armActions = new ArmActions(hardwareMap);
        //ArmActions  Arm = new ArmActions(hardwareMap);



        TrajectoryActionBuilder traj_1 = drive.actionBuilder(startPose)
                .strafeTo(new Vector2d(-10, 36)); //Scores first spec

        TrajectoryActionBuilder traj_2 = drive.actionBuilder(new Pose2d(-10, 40, Math.toRadians(90)))
                .setTangent(Math.toRadians(90))
                .splineToSplineHeading(new Pose2d(-34, 31, Math.toRadians(270)), Math.toRadians(270))
                .setTangent(Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-40, 13), Math.toRadians(180))
                .setTangent(Math.toRadians(180))
                .splineToConstantHeading(new Vector2d(-45, 54), Math.toRadians(270)) //push 1st sample in
                .setTangent(Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-53,5), Math.toRadians(180))
                .setTangent(Math.toRadians(180))
                .splineToConstantHeading(new Vector2d(-55, 47), Math.toRadians(270)) //push 2nd sample in
                .setTangent(Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-50, 35), Math.toRadians(0))
                .setTangent(Math.toRadians(0))
                .splineToConstantHeading(new Vector2d(-44,53), Math.toRadians(90)); //goes to pick up spec

        TrajectoryActionBuilder traj_3 = drive.actionBuilder(new Pose2d(-44, 50, Math.toRadians(270)))
                .setTangent(Math.toRadians(270))
                .splineToSplineHeading(new Pose2d(0,35.5, Math.toRadians(90)), Math.toRadians(270)); //scores 2nd spec

        TrajectoryActionBuilder traj_4 = drive.actionBuilder(new Pose2d(0, 36, Math.toRadians(90)))
                .setTangent(Math.toRadians(90))
                .splineToSplineHeading(new Pose2d(-45,61.5, Math.toRadians(270)), Math.toRadians(90)); //pick up 3rd spec

        TrajectoryActionBuilder traj_5 = drive.actionBuilder(new Pose2d(-40, 64, Math.toRadians(270)))
                .setTangent(Math.toRadians(270))
                .splineToSplineHeading(new Pose2d(-5,33.5, Math.toRadians(90)), Math.toRadians(270)); //score 3rd spec

        TrajectoryActionBuilder traj_6 = drive.actionBuilder(new Pose2d(-5, 36, Math.toRadians(90)))
                .setTangent(Math.toRadians(90))
                .splineToSplineHeading(new Pose2d(-48, 61, Math.toRadians(270)), Math.toRadians(90)); //pick up 4th spec

        TrajectoryActionBuilder traj_7 = drive.actionBuilder(new Pose2d(-45, 64, Math.toRadians(270)))
                .setTangent(Math.toRadians(270))
                .splineToSplineHeading(new Pose2d(3, 35.5, Math.toRadians(90)), Math.toRadians(270)); //score 4th spec

        TrajectoryActionBuilder traj_8 = drive.actionBuilder(new Pose2d(5, 38, Math.toRadians(90)))
                .setTangent(Math.toRadians(90))
                .splineToSplineHeading(new Pose2d(-48, 60, Math.toRadians(270)), Math.toRadians(90)); //Park

        while (!isStopRequested() && !opModeIsActive()) {
            telemetry.update();
        }
        telemetry.update();
        waitForStart();

        //Actions.runBlocking(armActions.raiseClaw());
        //Actions.runBlocking(armActions.closeClaw());

        if (isStopRequested()) return;

        Action trajectory_1;
        Action trajectory_2;
        Action trajectory_3;
        Action trajectory_4;
        Action trajectory_5;
        Action trajectory_6;
        Action trajectory_7;
        Action trajectory_8;

        trajectory_1 = traj_1.build();
        trajectory_2 = traj_2.build();
        trajectory_3 = traj_3.build();
        trajectory_4 = traj_4.build();
        trajectory_5 = traj_5.build();
        trajectory_6 = traj_6.build();
        trajectory_7 = traj_7.build();
        trajectory_8 = traj_8.build();

        Actions.runBlocking(
                new SequentialAction(
                        new ParallelAction(
                                armActions.raiseClaw(),
                                armActions.closeClaw(),
                                trajectory_1,
                                armActions.raiseArmParm(1750)
                        ),
                        armActions.halfLowerArm(), //score 1st spec
                        armActions.openClaw(),
                        new ParallelAction(
                                trajectory_2, //pushes samples
                                armActions.lowerArm()
                        ),
                        armActions.raiseClaw(),
                        armActions.closeClaw(), //1st pick up off wall
                        new ParallelAction(
                                trajectory_3,
                                armActions.raiseArm()
                        ),
                        armActions.halfLowerArm(), //score 2nd spec
                        armActions.openClaw(),
                        new ParallelAction(
                                trajectory_4, //goes to pick 3rd spec off wall
                                armActions.lowerArm()
                        ),
                        armActions.raiseClaw(),
                        armActions.closeClaw(),
                        new ParallelAction(
                                trajectory_5,
                                armActions.raiseArm()
                        ),
                        armActions.halfLowerArm(), //score 3rd spec
                        armActions.openClaw(),
                        new ParallelAction(
                                trajectory_6, //goes to pick up 4th spec
                                armActions.lowerArm()
                        ),
                        armActions.raiseClaw(),
                        armActions.closeClaw(),
                        new ParallelAction(
                                trajectory_7,
                                armActions.raiseArm()
                        ),
                        armActions.halfLowerArm(), //score 4th spec
                        armActions.openClaw(),
                        new ParallelAction(
                                trajectory_8, //park
                                armActions.lowerArm()
                        )
                )
        );
    }
}