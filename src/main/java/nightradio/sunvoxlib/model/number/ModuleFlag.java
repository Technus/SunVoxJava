package nightradio.sunvoxlib.model.number;

import nightradio.sunvoxlib.SunVoxLib;
import nightradio.sunvoxlib.model.mapping.IInteger;
import nightradio.sunvoxlib.model.mapping.IntegerMapping;

public class ModuleFlag implements IInteger {
    public static final IntegerMapping<ModuleFlag> MAPPING=new IntegerMapping<>();

    public static final ModuleFlag EXISTS = new ModuleFlag(SunVoxLib.SV_MODULE_FLAG_EXISTS);
    public static final ModuleFlag EFFECT = new ModuleFlag(SunVoxLib.SV_MODULE_FLAG_EFFECT);
    public static final ModuleFlag MUTE = new ModuleFlag(SunVoxLib.SV_MODULE_FLAG_MUTE);
    public static final ModuleFlag SOLO = new ModuleFlag(SunVoxLib.SV_MODULE_FLAG_SOLO);
    public static final ModuleFlag BYPASS = new ModuleFlag(SunVoxLib.SV_MODULE_FLAG_BYPASS);
    //public static final ModuleFlag INPUTS_OFF = new ModuleFlag(SunVoxLib.SV_MODULE_INPUTS_OFF);
    //public static final ModuleFlag INPUTS_MASK = new ModuleFlag(SunVoxLib.SV_MODULE_INPUTS_MASK);
    //public static final ModuleFlag OUTPUTS_OFF = new ModuleFlag(SunVoxLib.SV_MODULE_OUTPUTS_OFF);
    //public static final ModuleFlag OUTPUTS_MASK = new ModuleFlag(SunVoxLib.SV_MODULE_OUTPUTS_MASK);
    private final int value;

    public ModuleFlag(int value) {
        this.value = value;
        MAPPING.put(this);
    }

    public int getValue() {
        return value;
    }

    public static ModuleFlag ofAll(ModuleFlag... flags) {
        int val = 0;
        for (ModuleFlag flag : flags) {
            val |= flag.value;
        }
        return MAPPING.computeAbsent(val,ModuleFlag::new);
    }
}
