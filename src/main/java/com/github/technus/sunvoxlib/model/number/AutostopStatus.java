package com.github.technus.sunvoxlib.model.number;

import com.github.technus.sunvoxlib.model.mapping.IInteger;
import com.github.technus.sunvoxlib.model.mapping.IntegerMapping;

public class AutostopStatus implements IInteger {
    public static final IntegerMapping<AutostopStatus> MAPPING=new IntegerMapping<>();

    public static final AutostopStatus AUTO_STOP_DISABLED=new AutostopStatus(0);
    public static final AutostopStatus AUTO_STOP_ENABLED=new AutostopStatus(1);

    private final int value;

    public AutostopStatus(int value) {
        this.value = value;
        MAPPING.put(this);
    }

    public int getValue() {
        return value;
    }

}
