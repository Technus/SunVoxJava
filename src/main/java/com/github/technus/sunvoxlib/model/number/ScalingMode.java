package com.github.technus.sunvoxlib.model.number;

import com.github.technus.sunvoxlib.model.mapping.IInteger;
import com.github.technus.sunvoxlib.model.mapping.IntegerMapping;

public class ScalingMode implements IInteger {
    public static final IntegerMapping<ScalingMode> MAPPING =new IntegerMapping<>();

    /**
     * real value (0,1,2 etc.) as it is stored inside the controller
     * but the value displayed in the program interface may be different - you can use scaled=2 to get the displayed value;
     */
    public static final ScalingMode REAL=new ScalingMode(0);
    /**
     * scaled (0x0000...0x8000) if the controller type = 0, or the real value if the controller type = 1 (enum);
     * this value can be used in the pattern column XXYY;
     */
    public static final ScalingMode SCALED=new ScalingMode(1);
    /**
     * final value displayed in the program interface -
     * in most cases it is identical to the real value, and sometimes it has an additional offset;
     */
    public static final ScalingMode FINAL=new ScalingMode(2);


    private final int value;

    public ScalingMode(int value) {
        this.value = value;
        MAPPING.put(this);
    }

    public int getValue() {
        return value;
    }
}
