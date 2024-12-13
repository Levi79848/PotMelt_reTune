package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepApp {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        Pose2d startPose = new Pose2d(-10, 63, Math.toRadians(90));
        Pose2d subPoseMid = new Pose2d(0, 35, Math.toRadians(90));

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(startPose)
                .splineToConstantHeading(new Vector2d(subPoseMid.position.x-10, subPoseMid.position.y+6), Math.toRadians(270))
                .setReversed(false)
                .splineToSplineHeading(new Pose2d(-30,38, Math.toRadians(270)), Math.toRadians(180))
                .splineToConstantHeading(new Vector2d(-40,7), Math.toRadians(180))
                .splineToConstantHeading(new Vector2d(-45,48), Math.toRadians(90)) //pushes first sample
                .splineToConstantHeading(new Vector2d(-52, 0), Math.toRadians(180))
                .splineToConstantHeading(new Vector2d(-57,44), Math.toRadians(90)) //pushes second sample
                .setReversed(false)
                .setTangent(270)
                .splineToConstantHeading(new Vector2d(-48, 40), Math.toRadians(0))
                .setReversed(false)
                .setTangent(0)
                .splineToConstantHeading(new Vector2d(-43, 57), Math.toRadians(90)) //goes to pick up spec
                .waitSeconds(0.25)

                .waitSeconds(0.2)
                //.strafeTo(new Vector2d(-46, 60))
                //.setTangent(200)
                //.splineToConstantHeading(new Vector2d(-25, 45), Math.toRadians(300))
                //.setTangent(300)
                //.splineToSplineHeading(new Pose2d(-35,50, Math.toRadians(270)), Math.toRadians(270))
                .setTangent(300)
                .splineToLinearHeading(new Pose2d(5,37,Math.toRadians(90)), Math.toRadians(270))

                .setTangent(90)
                .splineToLinearHeading(new Pose2d(-49, 58, Math.toRadians(270)), Math.toRadians(90)) //goes to pick up spec2
                .waitSeconds(0.2)

                .waitSeconds(0.2)
                //.strafeTo(new Vector2d(-54, 56))
                .setTangent(300)
                //.splineToSplineHeading(new Pose2d(-35,50, Math.toRadians(270)), Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(0,37,Math.toRadians(90)), Math.toRadians(270))
                .waitSeconds(0.2)

                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}