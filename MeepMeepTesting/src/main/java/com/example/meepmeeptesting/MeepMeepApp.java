package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
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
                .strafeTo(new Vector2d(subPoseMid.position.x-10, subPoseMid.position.y+3))
                .setReversed(false)
                .splineToLinearHeading(new Pose2d(-30,38, Math.toRadians(270)), Math.toRadians(180))
                .splineToConstantHeading(new Vector2d(-40,15), Math.toRadians(180))
                .splineToConstantHeading(new Vector2d(-45,55), Math.toRadians(90)) //pushes first sample
                .splineToConstantHeading(new Vector2d(-45, 12), Math.toRadians(180))
                .splineToConstantHeading(new Vector2d(-55,55), Math.toRadians(90)) //pushes second sample
                .splineToConstantHeading(new Vector2d(-42, 66), Math.toRadians(90)) //goes to pick up spec
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}