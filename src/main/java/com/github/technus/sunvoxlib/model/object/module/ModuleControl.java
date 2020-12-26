package com.github.technus.sunvoxlib.model.object.module;

import com.github.technus.sunvoxlib.SunVoxLib;
import com.github.technus.sunvoxlib.model.number.ScalingMode;

public class ModuleControl {
    private final Module module;
    private final int id;

    public ModuleControl(Module module, int id) {
        this.module = module;
        this.id = id;
    }

    public Module getModule() {
        return module;
    }

    public int getId() {
        return id;
    }

    /**
     * Get the name of the specified module controller
     * @return controller name or null
     */
    public String getName() {
        return SunVoxLib.sv_get_module_ctl_name(getModule().getSlot().getId(), getModule().getId(), getId());
    }

    /**
     * Get the value of the specified module controller
     * @param scaled scaling mode
     * @return value of the specified module controller
     */
    public int getValue(ScalingMode scaled) {
        return SunVoxLib.sv_get_module_ctl_value(getModule().getSlot().getId(), getModule().getId(), getId(), scaled.getValue());
    }
}
