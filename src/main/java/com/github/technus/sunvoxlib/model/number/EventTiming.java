package com.github.technus.sunvoxlib.model.number;

import com.github.technus.sunvoxlib.model.mapping.IInteger;
import com.github.technus.sunvoxlib.model.mapping.IntegerMapping;

public class EventTiming implements IInteger {
    public static final IntegerMapping<EventTiming> MAPPING =new IntegerMapping<>();

    /**
     * reset to automatic time setting (t will be ignored; default mode)
     */
    public static final EventTiming AUTOMATIC = new EventTiming(0);

    /**
     * set timestamp
     * If timestamp is zero: out_t = as quickly as possible.
     * If timestamp is nonzero: out_t = timestamp + sound latency * 2.
     */
    public static final EventTiming SET = new EventTiming(1);

    private final int value;

    public EventTiming(int value) {
        this.value = value;
        MAPPING.put(this);
    }

    public int getValue() {
        return value;
    }
}
