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
                .strafeTo(new Vector2d(-10, 40)) //Scores first spec
                .setTangent(Math.toRadians(90))
                .splineToSplineHeading(new Pose2d(-34, 31, Math.toRadians(270)), Math.toRadians(270))
                .setTangent(Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-40, 13), Math.toRadians(180))
                .setTangent(Math.toRadians(180))
                .splineToConstantHeading(new Vector2d(-45, 50), Math.toRadians(270)) //push 1st sample in
                .setTangent(Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-53,5), Math.toRadians(180))
                .setTangent(Math.toRadians(180))
                .splineToConstantHeading(new Vector2d(-55, 44), Math.toRadians(270)) //push 2nd sample in
                .setTangent(Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-50, 35), Math.toRadians(0))
                .setTangent(Math.toRadians(0))
                .splineToConstantHeading(new Vector2d(-44,50), Math.toRadians(90)) //goes to pick up spec
                .setTangent(Math.toRadians(270))
                .splineToSplineHeading(new Pose2d(0,36, Math.toRadians(90)), Math.toRadians(270)) //scores 2nd spec
                .setTangent(Math.toRadians(90))
                .splineToSplineHeading(new Pose2d(-45,64, Math.toRadians(270)), Math.toRadians(90)) //pick up 3rd spec
                .setTangent(Math.toRadians(270))
                .splineToSplineHeading(new Pose2d(-5,36.5, Math.toRadians(90)), Math.toRadians(270)) //score 3rd spec
                .setTangent(Math.toRadians(90))
                .splineToSplineHeading(new Pose2d(-65, 60, Math.toRadians(270)), Math.toRadians(90)) //pick up 4th spec
                .setTangent(Math.toRadians(270))
                .splineToSplineHeading(new Pose2d(-5, 38, Math.toRadians(90)), Math.toRadians(270)) //score 4th spec
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}