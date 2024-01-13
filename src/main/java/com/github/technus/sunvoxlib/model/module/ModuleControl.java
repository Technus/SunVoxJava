package com.github.technus.sunvoxlib.model.module;

import com.github.technus.sunvoxlib.SunVoxLib;
import com.github.technus.sunvoxlib.model.number.ModuleControlType;
import com.github.technus.sunvoxlib.model.number.ScalingMode;

import static com.github.technus.sunvoxlib.model.SunVoxException.*;

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

    public int getIdForEvent(){
        return getId()+1;
    }

    /**
     * Get the name of the specified module controller
     * @return controller name or null
     */
    public String getName() {
        return stringIfNotNull(SunVoxLib.sv_get_module_ctl_name(getModule().getSlot().getId(), getModule().getId(), getId()));
    }

    /**
     * Get the value of the specified module controller
     * @param scaled scaling mode
     * @return value of the specified module controller
     */
    public int getValue(ScalingMode scaled) {
        return SunVoxLib.sv_get_module_ctl_value(getModule().getSlot().getId(), getModule().getId(), getId(), scaled.getValue());
    }

    /**
     * Get the value of the specified module controller
     * @param value for the specified module controller
     * @param scaled scaling mode
     */
    public void setValue(ScalingMode scaled,int value) {
        voidIfOk(SunVoxLib.sv_set_module_ctl_value(getModule().getSlot().getId(), getModule().getId(), getId(),value , scaled.getValue()));
    }

    public int getMinimum(ScalingMode scaled){
        return SunVoxLib.sv_get_module_ctl_min(getModule().getSlot().getId(),getModule().getId(), getId(),scaled.getValue());
    }

    public int getMaximum(ScalingMode scaled){
        return SunVoxLib.sv_get_module_ctl_max(getModule().getSlot().getId(),getModule().getId(), getId(),scaled.getValue());
    }

    public int getOffset(){
        return SunVoxLib.sv_get_module_ctl_offset(getModule().getSlot().getId(),getModule().getId(), getId());
    }

    public int getType(){
        return intIfOk(SunVoxLib.sv_get_module_ctl_type(getModule().getSlot().getId(),getModule().getId(), getId()));
    }

    public ModuleControlType getControlType(){
        return ModuleControlType.MAPPING.get(getType());
    }

    public int getGroup(){
        return intIfOk(SunVoxLib.sv_get_module_ctl_group(getModule().getSlot().getId(),getModule().getId(), getId()));
    }
}
