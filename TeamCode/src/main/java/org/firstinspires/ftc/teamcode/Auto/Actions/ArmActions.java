package org.firstinspires.ftc.teamcode.Auto.Actions;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class ArmActions {
    private DcMotor leftSlide;
    private DcMotor rightSlide;
    private CRServo intake;
    private Servo intakePivot;
    private Servo bucketPivot;
    private Servo clawPivot;
    private Servo claw;


    public ArmActions(HardwareMap hardwareMap) {
        leftSlide = hardwareMap.get(DcMotor.class, "leftSlide");
        rightSlide = hardwareMap.get(DcMotor.class, "rightSlide");
        intake = hardwareMap.get(CRServo.class, "intake");
        intakePivot = hardwareMap.get(Servo.class, "flip");
        bucketPivot = hardwareMap.get(Servo.class, "bucket_pivot");
        clawPivot = hardwareMap.get(Servo.class, "hooks");
        claw = hardwareMap.get(Servo.class, "claw");


        leftSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
/*
        leftSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
 */
        leftSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


    }

    public Action runIntake(boolean slow) {
        return new Action() {
            boolean initalized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initalized) {
                    intakePivot.setPosition(1);
                    if (!slow) {
                        intake.setPower(0.8);
                    } else {
                        intake.setPower(0.2);
                    }
                }
                return initalized;
            }
        };
    }

    ;

    public Action reverseIntake() {
        return new Action() {
            boolean initalized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                intakePivot.setPosition(0);
                if (!initalized) {
                    intake.setPower(-0.2);
                }
                return initalized;
            }
        };
    }

    ;

    public Action raiseArm(int power,int distance ) {
        return new Action() {
            private boolean initialized = false;



            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    rightSlide.setPower(power);
                    leftSlide.setPower(power);
                    initialized = true;
                }

                double pos = leftSlide.getCurrentPosition();
                packet.put("liftPos", pos);
                if (pos < distance) {
                    return true;
                } else {
                    leftSlide.setPower(0);
                    rightSlide.setPower(0);
                    return false;
                }
            }
        };
    }

    public Action deposit() {
        return new Action() {
            private boolean initialized;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    bucketPivot.setPosition(1);
                    while (leftSlide.getCurrentPosition() < 1000 && rightSlide.getCurrentPosition() < 1000) {
                        leftSlide.setPower(1);
                        rightSlide.setPower(1);
                    }
                    leftSlide.setPower(0);
                    rightSlide.setPower(0);
                    bucketPivot.setPosition(0);

                }

                return initialized;
            }
        };
    }

    public Action closeClaw() {
        return new Action() {
            private boolean initialized;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    claw.setPosition(0);
                }

                return initialized;
            }
        };
    }

    public Action closeClawBlocking() {
        return new Action() {
            private boolean initialized;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    claw.setPosition(0);
                }
                if (claw.getPosition() <= 0.1){
                    return initialized;
                } else {
                    return true;

                }
            }
        };
    }

    public Action closeClawFull() {
        return new Action() {
            private boolean initialized;
            public boolean run(@NonNull TelemetryPacket packet) {
                claw.setPosition(0);
                while (claw.getPosition() > 0.1) {
                    claw.setPosition(0);}
                return initialized;
                }
            };
        }


    public Action openClaw() {
        return new Action() {
            private boolean initialized;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    claw.setPosition(0.9);
                }

                return initialized;
            }
        };
    }

    public Action raiseClaw() {
        return new Action() {
            private boolean initialized;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                clawPivot.setPosition(.62);

                return initialized;
            }
        };
    }

    public Action lowerClaw() {
        return new Action() {
            private boolean initialized;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    clawPivot.setPosition(.43);
                }

                return initialized;
            }
        };
    }

    public Action lowerArm() {
        return new Action() {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    rightSlide.setPower(-1);
                    leftSlide.setPower(-1);
                    initialized = true;
                }

                double pos = leftSlide.getCurrentPosition();
                packet.put("liftPos", pos);
                if (pos > 20) {
                    return true;
                } else {
                    leftSlide.setPower(0);
                    rightSlide.setPower(0);
                    return false;
                }
            }
        };


    }public Action halfLowerArm() {
        return new Action() {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    rightSlide.setPower(-1);
                    leftSlide.setPower(-1);
                    initialized = true;
                }

                double pos = leftSlide.getCurrentPosition();
                packet.put("liftPos", pos);
                if (pos > 1600) {
                    return true;
                } else {
                    leftSlide.setPower(0);
                    rightSlide.setPower(0);
                    return false;
                }
            }
        };

    }
    public Action mostLowerArm() {
        return new Action() {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    rightSlide.setPower(-0.4);
                    leftSlide.setPower(-0.4);
                    initialized = true;
                }

                double pos = leftSlide.getCurrentPosition();
                packet.put("liftPos", pos);
                if (pos > 200) {
                    return true;
                } else {
                    leftSlide.setPower(0);
                    rightSlide.setPower(0);
                    return false;
                }
            }
        };

    }

    public Action smallRaiseArm() {
        return new Action() {
            private boolean initialized = false;



            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    rightSlide.setPower(0.5);
                    leftSlide.setPower(0.5);
                    initialized = true;
                }

                double pos = leftSlide.getCurrentPosition();
                packet.put("liftPos", pos);
                if (pos < 250) {
                    return true;
                } else {
                    leftSlide.setPower(0);
                    rightSlide.setPower(0);
                    return false;
                }
            }
        };
    }
}