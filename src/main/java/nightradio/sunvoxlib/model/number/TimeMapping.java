package nightradio.sunvoxlib.model.number;

import nightradio.sunvoxlib.SunVoxLib;
import nightradio.sunvoxlib.model.mapping.IInteger;
import nightradio.sunvoxlib.model.mapping.IntegerMapping;

public class TimeMapping implements IInteger {
    public static final IntegerMapping<TimeMapping> MAPPING =new IntegerMapping<>();

    /**
     * dest[X] = BPM | ( TPL << 16 ) (speed at the beginning of line X)
     */
    public static final TimeMapping SPEED = new TimeMapping(SunVoxLib.SV_TIME_MAP_SPEED);
    /**
     * dest[X] = frame counter at the beginning of line X
     */
    public static final TimeMapping FRAME_COUNT = new TimeMapping(SunVoxLib.SV_TIME_MAP_FRAMECNT);

    private final int value;

    public TimeMapping(int value) {
        this.value = value;
        MAPPING.put(this);
    }

    public int getValue() {
        return value;
    }
}
