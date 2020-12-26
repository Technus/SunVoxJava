package com.github.technus.sunvoxlib.model.module;

import com.github.technus.sunvoxlib.SunVoxLib;
import com.github.technus.sunvoxlib.model.number.Channel;
import com.github.technus.sunvoxlib.model.number.ModuleFlag;
import com.github.technus.sunvoxlib.model.Slot;

import java.io.File;
import java.util.List;

import static com.github.technus.sunvoxlib.model.SunVoxException.*;

public class Module implements AutoCloseable {
    private static final int[] EMPTY =new int[0];

    private final Slot slot;
    private final int id;

    protected Module(Slot slot, String type, String name, int x, int y, int z) {
        this.slot = slot;
        this.id = newModule(type, name, x, y, z);
    }

    public Module(Slot slot, File file, int x, int y, int z) {
        this.slot = slot;
        this.id = loadModule(file, x, y, z);
    }

    public Module(Slot slot, byte[] data, int x, int y, int z) {
        this.slot = slot;
        this.id = loadModuleFromMemory(data, x, y, z);
    }

    public Module(Slot slot,String name){
        this.slot=slot;
        this.id=getSlot().findModule(name);
    }

    public Slot getSlot() {
        return slot;
    }

    public int getId() {
        return id;
    }

    @Override
    public void close() {
        removeModule();
    }

    /**
     * Create a new module.
     * USE LOCK/UNLOCK!
     * @param type string with module type
     *             example: "Sampler"
     * @param name module name
     * @param x module coordinate horizontal
     *          center of the module view = 512,512
     *          normal working area: 0,0 ... 1024,1024
     * @param y module coordinates vertical
     *          center of the module view = 512,512
     *          normal working area: 0,0 ... 1024,1024
     * @param z layer number from 0 to 7
     * @return the number of the newly created module
     */
    protected int newModule(String type, String name, int x, int y, int z) {
        getSlot().throwIfDoesntHoldLock();
        return intIfOk(SunVoxLib.sv_new_module(getSlot().getId(), type, name, x, y, z));
    }

    /**
     * Load the module or sample. Supported file formats: sunsynth, xi, wav, aiff.
     * For WAV and AIFF: only uncompressed PCM format is supported.
     * @param file file
     * @param x module coordinate horizontal
     *          center of the module view = 512,512
     *          normal working area: 0,0 ... 1024,1024
     * @param y module coordinates vertical
     *          center of the module view = 512,512
     *          normal working area: 0,0 ... 1024,1024
     * @param z layer number from 0 to 7
     * @return the number of the newly created (from file) module
     */
    protected int loadModule(File file , int x, int y, int z) {
        return intIfOk(SunVoxLib.sv_load_module(getSlot().getId(), file.getAbsolutePath() , x, y, z));
    }

    /**
     *
     * @param data byte array with file contents
     * @param x module coordinate horizontal
     *          center of the module view = 512,512
     *          normal working area: 0,0 ... 1024,1024
     * @param y module coordinates vertical
     *          center of the module view = 512,512
     *          normal working area: 0,0 ... 1024,1024
     * @param z layer number from 0 to 7
     * @return the number of the newly created module
     */
    protected int loadModuleFromMemory(byte[] data, int x, int y, int z) {
        return intIfOk(SunVoxLib.sv_load_module_from_memory(getSlot().getId(), data, x, y, z));
    }

    /**
     * Remove the specified module.
     * USE LOCK/UNLOCK!
     */
    protected void removeModule() {
        getSlot().throwIfDoesntHoldLock();
        voidIfOk(SunVoxLib.sv_remove_module(getSlot().getId(), getId()));
    }

    /**
     * Get flags of the specified module
     * @return set of flags
     */
    public List<ModuleFlag> getFlags() {
        return ModuleFlag.MAPPING.getAllMatching(intIfNotGenericError(SunVoxLib.sv_get_module_flags(getSlot().getId(), getId())));
    }

    /**
     * Get array with the input links.
     * Number of inputs = ( module_flags & {@link SunVoxLib#SV_MODULE_INPUTS_MASK} ) >> {@link SunVoxLib#SV_MODULE_INPUTS_OFF}
     * @return array with the input links
     */
    public int[] getInputs() {
        try{
            return SunVoxLib.sv_get_module_inputs(getSlot().getId(), getId()).getIntArray(0,
                    SunVoxLib.numberOfModuleInputs(SunVoxLib.sv_get_module_flags(getSlot().getId(),getId())));
        }catch (NullPointerException e){
            return EMPTY;
        }
    }

    /**
     * Get array with the output links.
     * Number of outputs = ( module_flags & {@link SunVoxLib#SV_MODULE_OUTPUTS_MASK} ) >> {@link SunVoxLib#SV_MODULE_OUTPUTS_OFF}
     * @return array with the output links
     */
    public int[] getOutputs() {
        try {
            return SunVoxLib.sv_get_module_outputs(getSlot().getId(), getId()).getIntArray(0,
                    SunVoxLib.numberOfModuleOutputs(SunVoxLib.sv_get_module_flags(getSlot().getId(), getId())));
        }catch (NullPointerException e){
            return EMPTY;
        }
    }

    /**
     * Get the module name
     * @return module name or null
     */
    public String getName() {
        return SunVoxLib.sv_get_module_name(getSlot().getId(), getId());
    }

    /**
     * Get the module coordinates (XY)
     * Normal working area: 0,0 ... 1024,1024. Center of the module view = 512,512.
     * @return module coordinates {X,Y}
     */
    public int[] getXY() {
        return SunVoxLib.unpackShorts(SunVoxLib.sv_get_module_xy(getSlot().getId(), getId()));
    }

    /**
     * Get the module color
     * @return module color 0x00BBGGRR
     */
    public int getColor() {
        return SunVoxLib.sv_get_module_color(getSlot().getId(), getId());
    }

    /**
     * Get the module relative note (transposition) and finetune (-256...0...256)
     * @return module relative note (transposition) and finetune {note,fine tune}
     */
    public int[] getFineTune() {
        return SunVoxLib.unpackShorts(SunVoxLib.sv_get_module_finetune(getSlot().getId(), getId()));
    }

    /**
     * Get the currently playing piece of sound from the output of the specified module
     * @param channel channel
     * @param dest_buf pointer to the buffer {@link Short} to store the audio fragment
     * @param samples_to_read the number of samples to read from the module's output buffer
     * @return received number of samples (may be less or equal to samples_to_read)
     */
    public int getScope2(Channel channel, short[] dest_buf, int samples_to_read) {
        return SunVoxLib.sv_get_module_scope2(getSlot().getId(), getId(), channel.getValue(), dest_buf, samples_to_read);
    }

    /**
     * Get the number of the module controllers
     * @return number of the module controllers
     */
    public int getNumberOfCtls() {
        return intIfOk(SunVoxLib.sv_get_number_of_module_ctls(getSlot().getId(), getId()));
    }
}
