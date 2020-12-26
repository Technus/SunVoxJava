package nightradio.sunvoxlib.model.number;

import nightradio.sunvoxlib.model.mapping.IInteger;
import nightradio.sunvoxlib.model.mapping.IntegerMapping;

public class ScalingMode implements IInteger {
    public static final IntegerMapping<ScalingMode> MAPPING =new IntegerMapping<>();

    /**
     * real value (0,1,2 etc.)
     */
    public static final ScalingMode REAL=new ScalingMode(0);
    /**
     * scaled for the pattern column XXYY (0x0000...0x8000)
     */
    public static final ScalingMode PATTERN_COLUMN=new ScalingMode(1);


    private final int value;

    public ScalingMode(int value) {
        this.value = value;
        MAPPING.put(this);
    }

    public int getValue() {
        return value;
    }
}
