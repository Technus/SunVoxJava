package com.github.technus.sunvoxlib;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.github.technus.sunvoxlib.model.pattern.PatternNote;

import java.io.File;

public class SunVoxLib {
    static {
        String path = new File(new File("natives").getAbsolutePath() + File.separator +
                System.mapLibraryName("sunvox")).getAbsolutePath();
        System.out.println(path);
        Native.register(path);
    }

    public static final int GENERIC_ERROR_CODE = -1;

    public static final int NOTECMD_NOTHING = 0;
    public static final int NOTECMD_NOTE_OFF = 128;
    public static final int NOTECMD_ALL_NOTES_OFF = 129; /* send "note off" to all modules */
    public static final int NOTECMD_CLEAN_SYNTHS = 130; /* put all modules into standby state (stop and clear all internal buffers) */
    public static final int NOTECMD_STOP = 131;
    public static final int NOTECMD_PLAY = 132;
    public static final int NOTECMD_SET_PITCH = 133; /* set the pitch specified in column XXYY, where 0x0000 - highest possible pitch, 0x7800 - lowest pitch (note C0); one semitone = 0x100 */

    public static final double LOG_2 = Math.log(2);
    public static final double LOWEST_FREQUENCY = 16.333984375D;
    public static final int LOWEST_PITCH = 0x7800;
    public static final int HIGHEST_PITCH = 0x0;
    public static final double HIGHEST_FREQUENCY = pitchToFrequency(HIGHEST_PITCH);
    public static final double LOWEST_PITCH_DIV_10 = LOWEST_PITCH / 10D;
    public static final int SEMITONE_PITCH = 256;
    public static final int TONE_PITCH = SEMITONE_PITCH * 2;

    public static double pitchToFrequency(int pitch) {
        return Math.pow(2, (LOWEST_PITCH - pitch) / LOWEST_PITCH_DIV_10) * LOWEST_FREQUENCY;
    }

    public static int frequencyToPitch(double frequency) {
        return (int) Math.round(LOWEST_PITCH - (Math.log(frequency / LOWEST_FREQUENCY) / LOG_2) * LOWEST_PITCH_DIV_10);
    }

    public static final int SV_INIT_FLAG_NO_DEBUG_OUTPUT = 1 << 0;
    public static final int SV_INIT_FLAG_USER_AUDIO_CALLBACK = 1 << 1;
    public static final int SV_INIT_FLAG_OFFLINE = 1 << 1;
    public static final int SV_INIT_FLAG_AUDIO_INT16 = 1 << 2;
    public static final int SV_INIT_FLAG_AUDIO_FLOAT32 = 1 << 3;
    public static final int SV_INIT_FLAG_ONE_THREAD = 1 << 4;

    public static final int SV_TIME_MAP_SPEED = 0;
    public static final int SV_TIME_MAP_FRAMECNT = 1;

    public static final int SV_MODULE_FLAG_EXISTS = 1 << 0;
    public static final int SV_MODULE_FLAG_EFFECT = 1 << 1;
    public static final int SV_MODULE_FLAG_MUTE = 1 << 2;
    public static final int SV_MODULE_FLAG_SOLO = 1 << 3;
    public static final int SV_MODULE_FLAG_BYPASS = 1 << 4;
    public static final int SV_MODULE_INPUTS_OFF = 16;
    public static final int SV_MODULE_INPUTS_MASK = 0xff << SV_MODULE_INPUTS_OFF;
    public static final int SV_MODULE_OUTPUTS_OFF = 16 + 8;
    public static final int SV_MODULE_OUTPUTS_MASK = 0xff << SV_MODULE_OUTPUTS_OFF;

    public static int numberOfModuleInputs(int flags) {
        return (flags & SV_MODULE_INPUTS_MASK) >>> SV_MODULE_INPUTS_OFF;
    }

    public static int numberOfModuleOutputs(int flags) {
        return (flags & SV_MODULE_OUTPUTS_MASK) >>> SV_MODULE_OUTPUTS_OFF;
    }

    public static int[] unpackShorts(int packedPosition) {
        return new int[]{packedPosition & 0xFFFF, (packedPosition >>> 16) & 0xFFFF};
    }

    public static boolean isError(int returnValue) {
        return returnValue < 0;
    }

    public static final int SV_STYPE_INT16 = 0;
    public static final int SV_STYPE_INT32 = 1;
    public static final int SV_STYPE_FLOAT32 = 2;
    public static final int SV_STYPE_FLOAT64 = 3;

    public static native int sv_init(String config, int freq, int channels, int flags);

    public static native int sv_deinit();

    public static native int sv_get_sample_rate();

    public static native int sv_update_input();

    public static native int sv_audio_callback(byte[] buf, int frames, int latency, int out_time);

    public static native int sv_audio_callback2(byte[] buf, int frames, int latency, int out_time, int in_type, int in_channels, byte[] in_buf);

    public static native int sv_open_slot(int slot);

    public static native int sv_close_slot(int slot);

    public static native int sv_lock_slot(int slot);

    public static native int sv_unlock_slot(int slot);

    public static native int sv_load(int slot, String name);

    public static native int sv_load_from_memory(int slot, byte[] data);

    public static native int sv_play(int slot);

    public static native int sv_play_from_beginning(int slot);

    public static native int sv_stop(int slot);

    public static native int sv_pause(int slot);

    public static native int sv_resume(int slot);

    public static native int sv_set_autostop(int slot, int autostop);

    public static native int sv_get_autostop(int slot);

    public static native int sv_end_of_song(int slot);

    public static native int sv_rewind(int slot, int line_num);

    public static native int sv_volume(int slot, int vol);

    public static native int sv_set_event_t(int slot, int set, int t);

    public static native int sv_send_event(int slot, int track_num, int note, int vel, int module, int ctl, int ctl_val);

    public static native int sv_get_current_line(int slot);

    public static native int sv_get_current_line2(int slot);

    public static native int sv_get_current_signal_level(int slot, int channel);

    public static native String sv_get_song_name(int slot);

    public static native int sv_get_song_bpm(int slot);

    public static native int sv_get_song_tpl(int slot);

    public static native int sv_get_song_length_frames(int slot);

    public static native int sv_get_song_length_lines(int slot);

    public static native int sv_get_time_map(int slot, int start_line, int len, int[] dest, int flags);

    public static native int sv_new_module(int slot, String type, String name, int x, int y, int z);

    public static native int sv_remove_module(int slot, int mod_num);

    public static native int sv_connect_module(int slot, int source, int destination);

    public static native int sv_disconnect_module(int slot, int source, int destination);

    public static native int sv_load_module(int slot, String name, int x, int y, int z);

    public static native int sv_load_module_from_memory(int slot, byte[] data, int x, int y, int z);

    public static native int sv_sampler_load(int slot, int sampler_module, String name, int sample_slot);

    public static native int sv_sampler_load_from_memory(int slot, int sampler_module, byte[] data, int sample_slot);

    public static native int sv_get_number_of_modules(int slot);

    public static native int sv_find_module(int slot, String name);

    public static native int sv_get_module_flags(int slot, int mod_num);

    public static native Pointer sv_get_module_inputs(int slot, int mod_num);

    public static native Pointer sv_get_module_outputs(int slot, int mod_num);

    public static native String sv_get_module_name(int slot, int mod_num);

    public static native int sv_get_module_xy(int slot, int mod_num);

    public static native int sv_get_module_color(int slot, int mod_num);

    public static native int sv_get_module_finetune(int slot, int mod_num);

    public static native int sv_get_module_scope2(int slot, int mod_num, int channel, short[] dest_buf, int samples_to_read);

    public static native int sv_module_curve(int slot, int mod_num, int curve_num, float[] data, int len, int w);

    public static native int sv_get_number_of_module_ctls(int slot, int mod_num);

    public static native String sv_get_module_ctl_name(int slot, int mod_num, int ctl_num);

    public static native int sv_get_module_ctl_value(int slot, int mod_num, int ctl_num, int scaled);

    public static native int sv_get_number_of_patterns(int slot);

    public static native int sv_find_pattern(int slot, String name);

    public static native int sv_get_pattern_x(int slot, int pat_num);

    public static native int sv_get_pattern_y(int slot, int pat_num);

    public static native int sv_get_pattern_tracks(int slot, int pat_num);

    public static native int sv_get_pattern_lines(int slot, int pat_num);

    public static native String sv_get_pattern_name(int slot, int pat_num);

    public static native PatternNote/*Array*/ sv_get_pattern_data(int slot, int pat_num);

    public static native int sv_pattern_mute(int slot, int pat_num, int mute);

    public static native int sv_get_ticks();

    public static native int sv_get_ticks_per_second();

    public static native String sv_get_log(int size);
}
