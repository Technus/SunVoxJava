package com.github.technus.sunvoxlib.model.module;

import com.github.technus.sunvoxlib.SunVoxLib;
import com.github.technus.sunvoxlib.model.Slot;

import java.io.File;

import static com.github.technus.sunvoxlib.model.SunVoxException.voidIfOk;
import static com.github.technus.sunvoxlib.model.module.ModuleInternalType.SAMPLER;

public class SamplerModule extends ModuleInternal {
    public SamplerModule(Slot slot, String name, int x, int y, int z) {
        super(slot, SAMPLER, name, x, y, z);
    }

    public SamplerModule(Slot slot, String name) {
        super(slot, name);
    }

    /**
     * Load the sample (xi, wav, aiff) to the already created Sampler module.
     * For WAV and AIFF: only uncompressed PCM format is supported.
     * To replace the whole sampler - set sample_slot to -1.
     * @param file file
     * @param sample_slot slot number inside the Sampler, or -1 if you want to replace the whole module
     */
    public void samplerLoad(File file, int sample_slot) {
        voidIfOk(SunVoxLib.sv_sampler_load(getSlot().getId(), getId(), file.getAbsolutePath(), sample_slot));
    }

    /**
     * Load the sample (xi, wav, aiff) to the already created Sampler module.
     * For WAV and AIFF: only uncompressed PCM format is supported.
     * To replace the whole sampler - set sample_slot to -1.
     * @param data byte array with file contents
     * @param sample_slot slot number inside the Sampler, or -1 if you want to replace the whole module
     */
    public void samplerLoadFromMemory(byte[] data, int sample_slot) {
        voidIfOk(SunVoxLib.sv_sampler_load_from_memory(getSlot().getId(), getId(), data, sample_slot));
    }
}