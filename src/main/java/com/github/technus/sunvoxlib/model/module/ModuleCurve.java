package com.github.technus.sunvoxlib.model.module;

import com.github.technus.sunvoxlib.SunVoxLib;
import com.github.technus.sunvoxlib.model.number.AccessMode;

import static com.github.technus.sunvoxlib.model.SunVoxException.intIfOk;

public class ModuleCurve {
    private final Module module;
    private final int id;

    public ModuleCurve(Module module, int id) {
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
     * Access to the curve values of the specified module
     *
     * Available curves (Y=CURVE[X]):
     *
     * MultiSynth:
     * 0 - X = note (0..127); Y = velocity (0..1); 128 items;
     * 1 - X = velocity (0..256); Y = velocity (0..1); 257 items;
     * WaveShaper:
     * 0 - X = input (0..255); Y = output (0..1); 256 items;
     * MultiCtl:
     * 0 - X = input (0..256); Y = output (0..1); 257 items;
     * Analog Generator, Generator:
     * 0 - X = drawn waveform sample number (0..31); Y = volume (-1..1); 32 items;
     * @param data destination or source buffer
     * @param w access mode
     * @return number of items processed successfully
     */
    protected int curve(float[] data, AccessMode w) {
        return intIfOk(SunVoxLib.sv_module_curve(getModule().getSlot().getId(), getModule().getId(), getId(), data, data.length, w.getValue()));
    }

    public int read(float[] dataOut){
        return curve(dataOut,AccessMode.READ);
    }

    public int write(float[] data){
        return curve(data,AccessMode.WRITE);
    }
}
