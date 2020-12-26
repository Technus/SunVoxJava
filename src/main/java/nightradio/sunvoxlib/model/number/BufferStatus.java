package nightradio.sunvoxlib.model.number;

import nightradio.sunvoxlib.model.mapping.IInteger;
import nightradio.sunvoxlib.model.mapping.IntegerMapping;

public class BufferStatus implements IInteger {
    public static final IntegerMapping<BufferStatus> MAPPING=new IntegerMapping<>();

    public static final BufferStatus BUFFER_EMPTY=new BufferStatus(0);
    public static final BufferStatus BUFFER_FILLED=new BufferStatus(1);

    private final int value;

    public BufferStatus(int value) {
        this.value = value;
        MAPPING.put(this);
    }

    public int getValue() {
        return value;
    }

}
