package nightradio.sunvoxlib.model.number;

import nightradio.sunvoxlib.SunVoxLib;
import nightradio.sunvoxlib.model.mapping.IInteger;
import nightradio.sunvoxlib.model.mapping.IntegerMapping;

public class InitializationFlag implements IInteger {
    public static final IntegerMapping<InitializationFlag> MAPPING =new IntegerMapping<>();

    /**
     * Disables debug output
     */
    public static final InitializationFlag NO_DEBUG_OUTPUT = new InitializationFlag(SunVoxLib.SV_INIT_FLAG_NO_DEBUG_OUTPUT);
    /**
     * offline mode: system-dependent audio stream will not be created
     * user must call sv_audio_callback() to get the next piece of sound stream
     */
    public static final InitializationFlag USER_AUDIO_CALLBACK = new InitializationFlag(SunVoxLib.SV_INIT_FLAG_USER_AUDIO_CALLBACK);
    /**
     * offline mode: system-dependent audio stream will not be created
     * user must call sv_audio_callback() to get the next piece of sound stream
     */
    @Deprecated
    public static final InitializationFlag OFFLINE = new InitializationFlag(SunVoxLib.SV_INIT_FLAG_OFFLINE);
    /**
     * desired sample type of the output sound stream : short
     */
    public static final InitializationFlag AUDIO_INT16 = new InitializationFlag(SunVoxLib.SV_INIT_FLAG_AUDIO_INT16);
    /**
     * desired sample type of the output sound stream : float
     * the actual sample type may be different, if {@link InitializationFlag#USER_AUDIO_CALLBACK} is not set
     */
    public static final InitializationFlag AUDIO_FLOAT32 = new InitializationFlag(SunVoxLib.SV_INIT_FLAG_AUDIO_FLOAT32);
    /**
     * audio callback and song modification are in single thread
     * use it with {@link InitializationFlag#USER_AUDIO_CALLBACK} only
     */
    public static final InitializationFlag ONE_THREAD = new InitializationFlag(SunVoxLib.SV_INIT_FLAG_ONE_THREAD);

    private final int value;

    public InitializationFlag(int value) {
        this.value = value;
        MAPPING.put(this);
    }

    public int getValue() {
        return value;
    }

    public static InitializationFlag ofAll(InitializationFlag... flags) {
        int val = 0;
        for (InitializationFlag flag : flags) {
            val |= flag.getValue();
        }
        return MAPPING.computeAbsent(val,InitializationFlag::new);
    }
}
