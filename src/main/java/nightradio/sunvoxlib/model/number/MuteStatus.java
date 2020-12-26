package nightradio.sunvoxlib.model.number;

import nightradio.sunvoxlib.model.mapping.IInteger;
import nightradio.sunvoxlib.model.mapping.IntegerMapping;

public class MuteStatus implements IInteger {
    public static final IntegerMapping<MuteStatus> MAPPING=new IntegerMapping<>();

    public static final MuteStatus UNMUTED=new MuteStatus(0);
    public static final MuteStatus MUTED=new MuteStatus(1);

    private final int value;

    public MuteStatus(int value) {
        this.value = value;
        MAPPING.put(this);
    }

    public int getValue() {
        return value;
    }
}
