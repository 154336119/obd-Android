package com.slb.ttdandroidframework.util.config;

import com.github.pires.obd.commands.ObdCommand;
import com.github.pires.obd.commands.SpeedCommand;
import com.github.pires.obd.commands.control.DistanceMILOnCommand;
import com.github.pires.obd.commands.control.DtcNumberCommand;
import com.github.pires.obd.commands.control.EquivalentRatioCommand;
import com.github.pires.obd.commands.control.ModuleVoltageCommand;
import com.github.pires.obd.commands.control.TimingAdvanceCommand;
import com.github.pires.obd.commands.control.TroubleCodesCommand;
import com.github.pires.obd.commands.control.VinCommand;
import com.github.pires.obd.commands.engine.LoadCommand;
import com.github.pires.obd.commands.engine.MassAirFlowCommand;
import com.github.pires.obd.commands.engine.OilTempCommand;
import com.github.pires.obd.commands.engine.RPMCommand;
import com.github.pires.obd.commands.engine.RuntimeCommand;
import com.github.pires.obd.commands.engine.ThrottlePositionCommand;
import com.github.pires.obd.commands.fuel.AirFuelRatioCommand;
import com.github.pires.obd.commands.fuel.ConsumptionRateCommand;
import com.github.pires.obd.commands.fuel.FindFuelTypeCommand;
import com.github.pires.obd.commands.fuel.FuelLevelCommand;
import com.github.pires.obd.commands.fuel.FuelTrimCommand;
import com.github.pires.obd.commands.fuel.WidebandAirFuelRatioCommand;
import com.github.pires.obd.commands.pressure.BarometricPressureCommand;
import com.github.pires.obd.commands.pressure.FuelPressureCommand;
import com.github.pires.obd.commands.pressure.FuelRailPressureCommand;
import com.github.pires.obd.commands.pressure.IntakeManifoldPressureCommand;
import com.github.pires.obd.commands.protocol.AdaptiveTimingCommand;
import com.github.pires.obd.commands.temperature.AirIntakeTemperatureCommand;
import com.github.pires.obd.commands.temperature.AmbientAirTemperatureCommand;
import com.github.pires.obd.commands.temperature.EngineCoolantTemperatureCommand;
import com.github.pires.obd.enums.AvailableCommandNames;
import com.github.pires.obd.enums.FuelTrim;
import com.orhanobut.logger.Logger;
import com.slb.ttdandroidframework.ui.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO put description
 */
public final class ObdConfig {
    public static final int NO_BLUETOOTH_DEVICE_SELECTED = 0;
    public static final int CANNOT_CONNECT_TO_DEVICE = 1;
    public static final int NO_DATA = 3;
    public static final int DATA_OK = 4;
    public static final int CLEAR_DTC = 5;
    public static final int OBD_COMMAND_FAILURE = 10;
    public static final int OBD_COMMAND_FAILURE_IO = 11;
    public static final int OBD_COMMAND_FAILURE_UTC = 12;
    public static final int OBD_COMMAND_FAILURE_IE = 13;
    public static final int OBD_COMMAND_FAILURE_MIS = 14;
    public static final int OBD_COMMAND_FAILURE_NODATA = 15;


    public static List<ObdCommand> getAllCommands(){
        ArrayList<ObdCommand> cmds = new ArrayList<>();
        // Control
        cmds.add(new ModuleVoltageCommand());
        cmds.add(new EquivalentRatioCommand());
        cmds.add(new DistanceMILOnCommand());
        cmds.add(new TimingAdvanceCommand());
        cmds.add(new VinCommand());

        // Engine
        cmds.add(new LoadCommand());
        cmds.add(new RPMCommand());
        cmds.add(new RuntimeCommand());
        cmds.add(new MassAirFlowCommand());
        cmds.add(new ThrottlePositionCommand());

        // Fuel
        cmds.add(new FindFuelTypeCommand());
        cmds.add(new ConsumptionRateCommand());
        // cmds.add(new AverageFuelEconomyObdCommand());
        //cmds.add(new FuelEconomyCommand());
        cmds.add(new FuelLevelCommand());
        // cmds.add(new FuelEconomyMAPObdCommand());
        // cmds.add(new FuelEconomyCommandedMAPObdCommand());
        cmds.add(new FuelTrimCommand(FuelTrim.LONG_TERM_BANK_1));
        cmds.add(new FuelTrimCommand(FuelTrim.LONG_TERM_BANK_2));
        cmds.add(new FuelTrimCommand(FuelTrim.SHORT_TERM_BANK_1));
        cmds.add(new FuelTrimCommand(FuelTrim.SHORT_TERM_BANK_2));
        cmds.add(new AirFuelRatioCommand());
        cmds.add(new WidebandAirFuelRatioCommand());
        cmds.add(new OilTempCommand());

        // Pressure
        cmds.add(new BarometricPressureCommand());
        cmds.add(new FuelPressureCommand());
        cmds.add(new FuelRailPressureCommand());
        cmds.add(new IntakeManifoldPressureCommand());

        // Temperature
        cmds.add(new AirIntakeTemperatureCommand());
        cmds.add(new AmbientAirTemperatureCommand());
        cmds.add(new EngineCoolantTemperatureCommand());

        // Misc
        cmds.add(new SpeedCommand());
        return cmds;
    }

    public static List<String> getAllCommandsName(){
//        for(AvailableCommandNames name : AvailableCommandNames.values()){
//            name.getValue()
//        }
        List<String> names = new ArrayList<>();
        names.add("Control Module Power Supply ");
        names.add("Command Equivalence Ratio");
        names.add("Distance traveled with MIL on");
        names.add("Timing Advance");
        names.add("Vehicle Identification Number (VIN)");

        names.add("Engine Load");
        names.add("Engine RPM");
        names.add("Engine Runtime");
        names.add("Mass Air Flow");
        names.add("Throttle Position");

        names.add("Fuel Type");
        names.add("Fuel Consumption Rate");
        names.add("Fuel Level");
        names.add("Long Term Fuel Trim Bank 1");
        names.add("Long Term Fuel Trim Bank 2");
        names.add("Short Term Fuel Trim Bank 1");
        names.add("Short Term Fuel Trim Bank 2");
        names.add("Air/Fuel Ratio");
        names.add("Wideband Air/Fuel Ratio");
        names.add("Engine oil temperature");

        names.add("Barometric Pressure");
        names.add("Fuel Pressure");
        names.add("Fuel Rail Pressure");
        names.add("Intake Manifold Pressure");

        names.add("Air Intake Temperature");
        names.add("Ambient Air Temperature");
        names.add("Engine Coolant Temperature");
        names.add("Vehicle Speed");

        return names;
    }

    public static ArrayList<ObdCommand> getCommands() {
        ArrayList<ObdCommand> cmds = new ArrayList<>();
        // Control
        cmds.add(new ModuleVoltageCommand());
        cmds.add(new EquivalentRatioCommand());
        cmds.add(new DistanceMILOnCommand());
        cmds.add(new TimingAdvanceCommand());
        cmds.add(new VinCommand());

        // Engine
        cmds.add(new LoadCommand());
        cmds.add(new RPMCommand());
        cmds.add(new RuntimeCommand());
        cmds.add(new MassAirFlowCommand());
        cmds.add(new ThrottlePositionCommand());

        return cmds;
    }

    public static ObdCommand getCommandForNameIndex(String name){
        int index = getAllCommandsName().indexOf(name);
        return getAllCommands().get(index);
    }

    public static void saveSpObdCommandName(List<ObdCommand> commandList){
        List<String> nameList = new ArrayList<>();
        for(ObdCommand obdCommand : commandList){
            nameList.add(obdCommand.getName());
        }
        SharedPreferencesUtil.putListData(BizcContant.SP_OBD, nameList);
    }

    public static List<ObdCommand> getSpCommandList(){
        List<ObdCommand> obdCommandList = new ArrayList<>();
        List<String> nameList  = SharedPreferencesUtil.getListData(BizcContant.SP_OBD, String.class);
        for(int i =0;i<nameList.size();i++){
            for(int j = 0;j<getAllCommands().size();j++){
                if(getAllCommands().get(j).getName().equals(nameList.get(i))){
                    obdCommandList.add(getAllCommands().get(j));
                }
            }
        }
        return obdCommandList;
    }
}
