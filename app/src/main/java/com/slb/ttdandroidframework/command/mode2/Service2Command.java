package com.slb.ttdandroidframework.command.mode2;

import com.github.pires.obd.commands.ObdCommand;

/**
 * Created by mabin on 2018-09-07.
 * 04  PercentageObdCommand
 * 05  EngineCoolantTemperatureCommand
 * 0b  IntakeManifoldPressureCommand
 * 0c  RPMCommand
 * 0D  SpeedCommand
 * 10  MassAirFlowCommand
 * 11  ThrottlePositionCommand(PercentageObdCommand)
 */

public class Service2Command extends ObdCommand {
    float pidData04 =  0.0F;
    float pidData05 =  0.0F;
    int pidData0b =  0;
    int pidData0c =  0;
    int pidData0d =  0;
    float pidData10 =  0.0F;
    float pidData11 =  0.0F;

    public float getPidData05() {
        return  (float)(((Integer)this.buffer.get(2)).intValue() - 40);
    }


    public float getPidData04() {
        return (float)((Integer)this.buffer.get(3)).intValue() * 100.0F / 255.0F;
    }

    public int getPidData0b() {
        return ((Integer)this.buffer.get(3)).intValue();
    }

    public int getPidData0c() {
        return (((Integer)this.buffer.get(3)).intValue() * 256 + ((Integer)this.buffer.get(4)).intValue()) / 4;
    }


    public int getPidData0d() {
        return ((Integer)this.buffer.get(2)).intValue();
    }

    public float getPidData11() {
        return (float)((Integer)this.buffer.get(3)).intValue() * 100.0F / 255.0F;
    }

    public float getPidData10() {
        return   (float)(((Integer)this.buffer.get(3)).intValue() * 256 + ((Integer)this.buffer.get(4)).intValue()) / 100.0F;
    }


    public Service2Command(String cmd) {
        super(cmd);
    }

    protected void performCalculations() {
    }
    @Override
    public String getFormattedResult() {
        return "";
    }

    @Override
    public String getCalculatedResult() {
        return "calculatedResult";
    }

    @Override
    public String getName() {
        return "Command:"+cmd;
    }
}
