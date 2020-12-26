package com.github.technus.sunvoxlib.model.object;

import com.github.technus.sunvoxlib.SunVoxLib;
import com.github.technus.sunvoxlib.model.number.*;

import static com.github.technus.sunvoxlib.model.SunVoxException.intIfOk;
import static com.github.technus.sunvoxlib.model.SunVoxException.voidIfOk;

public class SunVox implements AutoCloseable {
    protected static SunVox INSTANCE = new SunVox();

    public static SunVox getInstance() {
        return INSTANCE;
    }

    @Override
    public void close() {
        deinit();
    }

    //region Main

    /**
     * Global sound system initialization
     * @param freq desired sample rate (Hz)
     *            min - 44100
     *            the actual rate may be different, if SV_INIT_FLAG_OFFLINE is not set
     * @param flags set of flags {@link InitializationFlag}
     * @return SunVox engine version
     */
    public EngineVersion init(int freq, InitializationFlag... flags) {
        return init(null,freq,flags);
    }

    /**
     * Global sound system initialization
     * @param config string with additional configuration in the following format: "option_name=value|option_name=value"
     *               or NULL for auto config
     *               example: "buffer=1024|audiodriver=alsa|audiodevice=hw:0,0"
     * @param freq desired sample rate (Hz)
     *            min - 44100
     *            the actual rate may be different, if SV_INIT_FLAG_OFFLINE is not set
     * @param flags set of flags {@link InitializationFlag}
     * @return SunVox engine version
     */
    public EngineVersion init(String config, int freq, InitializationFlag... flags) {
        return init(config,freq,2,flags);
    }

    /**
     * Global sound system initialization
     * @param config string with additional configuration in the following format: "option_name=value|option_name=value"
     *               or NULL for auto config
     *               example: "buffer=1024|audiodriver=alsa|audiodevice=hw:0,0"
     * @param freq desired sample rate (Hz)
     *            min - 44100
     *            the actual rate may be different, if SV_INIT_FLAG_OFFLINE is not set
     * @param channels only 2 supported now
     * @param flags set of flags {@link InitializationFlag}
     * @return SunVox engine version
     */
    protected EngineVersion init(String config, int freq, int channels, InitializationFlag... flags) {
        return new EngineVersion(intIfOk(SunVoxLib.sv_init(config,freq,channels,InitializationFlag.ofAll(flags).getValue())));
    }

    /**
     * Global sound system deinitialization
     */
    protected void deinit() {
        voidIfOk(SunVoxLib.sv_deinit());
    }

    /**
     * Get current sampling rate (it may differ from the frequency specified in {@link #init})
     * @return sampling rate
     */
    public int getSampleRate() {
        return intIfOk(SunVoxLib.sv_get_sample_rate());
    }

    /**
     * Handle input ON/OFF requests to enable/disable input ports of the sound card (for example, after the Input module creation).
     * Call it from the main thread only, where the SunVox sound stream is not locked.
     */
    public void updateInput() {
        voidIfOk(SunVoxLib.sv_update_input());
    }

    /**
     * get the next piece of SunVox audio from the Output module
     * @param buf output buffer of type {@link Short} (if {@link InitializationFlag#AUDIO_INT16} is set in {@link #init})
     *            or {@link Float} (if {@link InitializationFlag#AUDIO_FLOAT32} is set in {@link #init})
     *            stereo data will be interleaved in this buffer: LRLR... (LR is a single frame (Left+Right))
     * @param frames number of frames in destination buffer
     * @param latency audio latency (in frames)
     * @param out_time buffer output time (in system ticks)
     * @return buffer status
     */
    public BufferStatus audioCallback(byte[] buf, int frames, int latency, int out_time) {
        return BufferStatus.MAPPING.get(intIfOk(SunVoxLib.sv_audio_callback(buf, frames, latency, out_time)));
    }

    /**
     * send some data to the Input module and receive the filtered data from the Output module
     * @param buf output buffer of type {@link Short} (if {@link InitializationFlag#AUDIO_INT16} is set in {@link #init})
     *            or {@link Float} (if {@link InitializationFlag#AUDIO_FLOAT32} is set in {@link #init})
     *            stereo data will be interleaved in this buffer: LRLR... (LR is a single frame (Left+Right))
     * @param frames number of frames in destination buffer
     * @param latency audio latency (in frames)
     * @param out_time buffer output time (in system ticks)
     * @param in_type bufferType
     * @param in_channels number of input channels
     * @param in_buf input buffer
     *               stereo data must be interleaved in this buffer: LRLR...
     * @return buffer status
     */
    public BufferStatus audioCallback2(byte[] buf, int frames, int latency, int out_time, BufferType in_type, int in_channels, byte[] in_buf) {
        return BufferStatus.MAPPING.get(intIfOk(SunVoxLib.sv_audio_callback2(buf, frames, latency, out_time, in_type.getValue(), in_channels, in_buf)));
    }

    //endregion

    //region Other

    /**
     * SunVox engine uses system-provided time space, measured in system tick (don't confuse it with the project ticks).
     * These ticks are required for parameters of functions such as {@link #audioCallback} {@link #audioCallback2} and {@link Slot#setEventT}.
     * @return current tick counter (from 0 to 0xFFFFFFFF)
     */
    public int getTicks() {
        return SunVoxLib.sv_get_ticks();
    }

    /**
     * SunVox engine uses system-provided time space, measured in system tick (don't confuse it with the project ticks).
     * These ticks are required for parameters of functions such as {@link #audioCallback} {@link #audioCallback2} and {@link Slot#setEventT}.
     * @return number of system ticks per second
     */
    public int getTicksPerSecond() {
        return SunVoxLib.sv_get_ticks_per_second();
    }

    /**
     * Get the latest messages from the log
     * @param size max number of bytes to read
     * @return latest log messages
     */
    public String getLog(int size) {
        return SunVoxLib.sv_get_log(size);
    }

    //endregion
}
