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
     * @param len number of items to read/write
     * @param w access mode
     * @return number of items processed successfully
     */
    protected int curve(float[] data, int len, AccessMode w) {
        return intIfOk(SunVoxLib.sv_module_curve(getModule().getSlot().getId(), getModule().getId(), getId(), data, len, w.getValue()));
    }

    public int read(float[] dataOut,int len){
        return curve(dataOut,len,AccessMode.READ);
    }

    public int write(float[] data,int len){
        return curve(data,len,AccessMode.WRITE);
    }
}
