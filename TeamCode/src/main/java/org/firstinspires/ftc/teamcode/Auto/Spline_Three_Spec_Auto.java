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
@Autonomous(name = "Spline_Three_Spec_Auto", group = "Autonomous")
public class Spline_Three_Spec_Auto extends LinearOpMode {

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
                .strafeTo(new Vector2d(subPoseMid.position.x-10, subPoseMid.position.y+5));

        TrajectoryActionBuilder traj_2 = drive.actionBuilder(new Pose2d(subPoseMid.position.x-10, subPoseMid.position.y+5, Math.toRadians(90)))
                .setReversed(false)
                .splineToLinearHeading(new Pose2d(-30,38, Math.toRadians(270)), Math.toRadians(180))
                .splineToConstantHeading(new Vector2d(-40,17), Math.toRadians(180))
                .splineToConstantHeading(new Vector2d(-45,57), Math.toRadians(90)) //pushes first sample
                .splineToConstantHeading(new Vector2d(-50, 13), Math.toRadians(180))
                .splineToConstantHeading(new Vector2d(-55,55), Math.toRadians(90)) //pushes second sample
                .setTangent(270)
                .splineToConstantHeading(new Vector2d(-51, 52), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(-46, 70), Math.toRadians(90)) //goes to pick up spec
                //.strafeTo(new Vector2d(-46,70.25))
                .stopAndAdd(armActions.closeClawBlocking());

        TrajectoryActionBuilder traj_3 = drive.actionBuilder(new Pose2d(-46, 70.25, Math.toRadians(90)))
                .waitSeconds(0.5)
                //.strafeTo(new Vector2d(-46, 60))
                .setTangent(300)
                .splineToLinearHeading(new Pose2d(-10,16,Math.toRadians(90)), Math.toRadians(270))
                .stopAndAdd(armActions.halfLowerArm())
                .stopAndAdd(armActions.openClaw());

        TrajectoryActionBuilder traj_4 = drive.actionBuilder(new Pose2d(-4, 38, Math.toRadians(90)))
                .setTangent(90)
                .splineToLinearHeading(new Pose2d(-54, 68.25, Math.toRadians(270)), Math.toRadians(90)) //goes to pick up spec2
                //.strafeTo(new Vector2d(-54, 69))
                //.strafeTo(new Vector2d(-54,68.25))
                .stopAndAdd(armActions.closeClaw());

        TrajectoryActionBuilder traj_5 = drive.actionBuilder(new Pose2d(-54, 68.25, Math.toRadians(90)))
                .waitSeconds(0.3)
                .strafeTo(new Vector2d(-54, 56))
                .setTangent(270)
                .splineToLinearHeading(new Pose2d(-15,36,Math.toRadians(90)), Math.toRadians(270));

        TrajectoryActionBuilder traj_6 = drive.actionBuilder(new Pose2d(-15, 68.25, Math.toRadians(90)))
                .setTangent(90)
                .splineToLinearHeading(new Pose2d(-54,68.25,Math.toRadians(270)), Math.toRadians(90))
                //.strafeTo(new Vector2d(-54, 69))
                //.strafeTo(new Vector2d(-54,68.25))
                .stopAndAdd(armActions.closeClaw());





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

        trajectory_1 = traj_1.build();
        trajectory_2 = traj_2.build();
        trajectory_3 = traj_3.build();
        trajectory_4 = traj_4.build();
        trajectory_5 = traj_5.build();
        trajectory_6 = traj_6.build();


        Actions.runBlocking(
                new SequentialAction(
                        new ParallelAction(
                                armActions.raiseClaw(),
                                armActions.closeClaw(),
                                armActions.raiseArm(),
                                trajectory_1
                        ),
                        armActions.halfLowerArm(),
                        armActions.openClaw(),
                        new ParallelAction(
                                armActions.lowerArm(),
                                trajectory_2
                        ),
                        new ParallelAction(
                                armActions.raiseArm(),
                                trajectory_3
                        ),
                        new ParallelAction(
                                armActions.lowerArm(),
                                trajectory_4
                        ),
                        new ParallelAction(
                                armActions.raiseArm(),
                                trajectory_5
                        ),
                        armActions.halfLowerArm(),
                        armActions.openClaw(),
                        new ParallelAction(
                                armActions.lowerArm(),
                                trajectory_6
                        )
                        /*
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
*/
                )
        );
    }
}