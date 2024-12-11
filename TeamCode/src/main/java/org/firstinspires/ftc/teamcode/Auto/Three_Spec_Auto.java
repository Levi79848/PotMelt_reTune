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
@Autonomous(name = "Three_Spec_Auto", group = "Autonomous")
public class Three_Spec_Auto extends LinearOpMode {

    @Override
    public void runOpMode() {

        Pose2d startPose = new Pose2d(-10, 63, Math.toRadians(90));
        Pose2d subPoseMid = new Pose2d(0, 35, Math.toRadians(90));
        Pose2d parkPose = new Pose2d(-50, 63, Math.toRadians(90));

        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);

        if (hardwareMap == null) {
            telemetry.addData("Error", "hardwareMap is not initialized");
            telemetry.update();
            return;
        }


        ArmActions armActions = new ArmActions(hardwareMap);
        //ArmActions  Arm = new ArmActions(hardwareMap);



        TrajectoryActionBuilder traj_1 = drive.actionBuilder(startPose)
                .strafeTo(new Vector2d(subPoseMid.position.x-10, subPoseMid.position.y-1));

        TrajectoryActionBuilder traj_1_finish = drive.actionBuilder(new Pose2d(subPoseMid.position.x-10, subPoseMid.position.y+1, Math.toRadians(90)))
                .strafeTo(new Vector2d(subPoseMid.position.x-10, subPoseMid.position.y-3));

        TrajectoryActionBuilder traj_2 = drive.actionBuilder(new Pose2d(-10, 32, Math.toRadians(90)))
                .waitSeconds(0.5)
                //.strafeTo(new Vector2d(startPose.position.x, startPose.position.y-3));
                .strafeTo(new Vector2d(-10, 40))
                .strafeTo(new Vector2d(-35, 38));

        TrajectoryActionBuilder traj_3 = drive.actionBuilder(new Pose2d(-35, 38, Math.toRadians(90)))
                .stopAndAdd(armActions.raiseClaw())
                .strafeTo(new Vector2d(-35, 15))
                .strafeToLinearHeading(new Vector2d(-50, 15), Math.toRadians(269))
                .strafeTo(new Vector2d(-50,46))
                .strafeTo(new Vector2d (-50, 40))
                .strafeTo(new Vector2d (-40, 40))
                .strafeTo(new Vector2d(-43, 57))
                .strafeTo(new Vector2d(-43,56.25))
                .stopAndAdd(armActions.closeClaw())
                .stopAndAdd(armActions.raiseArm())
                .strafeToLinearHeading(new Vector2d(-4, 42), Math.toRadians(90))
                .strafeTo(new Vector2d(-4, 38))
                .stopAndAdd(armActions.halfLowerArm())
                .stopAndAdd(armActions.openClaw());

        TrajectoryActionBuilder traj_4 = drive.actionBuilder(new Pose2d(-4, 38, Math.toRadians(90)))
                .strafeTo(new Vector2d(-8, 45))
                .stopAndAdd(armActions.raiseClaw())
                .strafeToLinearHeading(new Vector2d(-40, 63), Math.toRadians(269))
                .strafeTo(new Vector2d(-40,74))
                .strafeTo(new Vector2d(-40,73.25))
                .stopAndAdd(armActions.closeClaw())
                .stopAndAdd(armActions.raiseArm())
                .strafeToLinearHeading(new Vector2d(-5, 50), Math.toRadians(90))
                .strafeTo(new Vector2d(-5,44))
                .stopAndAdd(armActions.halfLowerArm())
                .stopAndAdd(armActions.openClaw())
                .stopAndAdd(armActions.lowerArm());
        //changes

        TrajectoryActionBuilder traj_wait = drive.actionBuilder(new Pose2d(-41, 58, Math.toRadians(180)))
                .strafeTo(new Vector2d(-41, 50))
                .waitSeconds(1);





        while (!isStopRequested() && !opModeIsActive()) {
            telemetry.update();
        }
        telemetry.update();
        waitForStart();

        Actions.runBlocking(armActions.raiseClaw());
        Actions.runBlocking(armActions.closeClaw());

        if (isStopRequested()) return;

        Action trajectory_1;
        Action trajectory_2;
        Action trajectory_3;
        Action trajectory_1_finish;
        Action trajectory_4;
        Action trajectory_wait;

        trajectory_1 = traj_1.build();
        trajectory_2 = traj_2.build();
        trajectory_3 = traj_3.build();
        trajectory_1_finish = traj_1_finish.build();
        trajectory_wait = traj_wait.build();
        trajectory_4 = traj_4.build();

        Actions.runBlocking(
                new SequentialAction(
                        new ParallelAction(
                        armActions.raiseArm(),
                        trajectory_1
                                ),
                        //trajectory_1_finish,
                        armActions.halfLowerArm(),
                        armActions.openClaw(),
                        new ParallelAction(
                                trajectory_2,
                                armActions.lowerArm()
                        ),
                        trajectory_3,
                        new ParallelAction(
                                trajectory_4,
                                armActions.lowerArm()
                        )
                        //armActions.closeClaw(),
                        //trajectory_wait,
                        //armActions.raiseArm()
                        //trajectory_4

                )
        );
    }
}