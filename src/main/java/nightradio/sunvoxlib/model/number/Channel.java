package nightradio.sunvoxlib.model.number;

import nightradio.sunvoxlib.model.mapping.IInteger;
import nightradio.sunvoxlib.model.mapping.IntegerMapping;

public class Channel implements IInteger {
    public static final IntegerMapping<Channel> MAPPING=new IntegerMapping<>();

    public static final Channel LEFT=new Channel(0);
    public static final Channel RIGHT=new Channel(1);

    private final int value;

    public Channel(int value) {
        this.value = value;
        MAPPING.put(this);
    }

    public int getValue() {
        return value;
    }
}
