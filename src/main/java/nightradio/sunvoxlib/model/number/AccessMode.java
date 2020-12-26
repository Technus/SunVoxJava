package nightradio.sunvoxlib.model.number;

import nightradio.sunvoxlib.model.mapping.IInteger;
import nightradio.sunvoxlib.model.mapping.IntegerMapping;

public class AccessMode implements IInteger {
    public static final IntegerMapping<AccessMode> MAPPING=new IntegerMapping<>();

    public static final AccessMode READ=new AccessMode(0);
    public static final AccessMode WRITE=new AccessMode(1);

    private final int value;

    public AccessMode(int value) {
        this.value = value;
        MAPPING.put(this);
    }

    public int getValue() {
        return value;
    }

}
