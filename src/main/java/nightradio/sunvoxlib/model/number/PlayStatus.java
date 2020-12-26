package nightradio.sunvoxlib.model.number;

import nightradio.sunvoxlib.model.mapping.IInteger;
import nightradio.sunvoxlib.model.mapping.IntegerMapping;

public class PlayStatus implements IInteger {
    public static final IntegerMapping<PlayStatus> MAPPING=new IntegerMapping<>();

    public static final PlayStatus STOPPED=new PlayStatus(0);
    public static final PlayStatus PLAYING=new PlayStatus(1);

    private final int value;

    public PlayStatus(int value) {
        this.value = value;
        MAPPING.put(this);
    }

    public int getValue() {
        return value;
    }
}
