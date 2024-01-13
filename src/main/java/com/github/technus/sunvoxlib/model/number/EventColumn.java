package com.github.technus.sunvoxlib.model.number;

import com.github.technus.sunvoxlib.model.mapping.IInteger;
import com.github.technus.sunvoxlib.model.mapping.IntegerMapping;

public class EventColumn implements IInteger {
    public static final IntegerMapping<EventColumn> MAPPING=new IntegerMapping<>();

    public static final EventColumn NOTE=new EventColumn(0,0xff);
    public static final EventColumn VELOCITY=new EventColumn(1,0xff);
    public static final EventColumn MODULE=new EventColumn(2, 0xffff);
    public static final EventColumn CTL=new EventColumn(3, 0xffff);
    public static final EventColumn VALUE=new EventColumn(4, 0xffff);

    private final int value;
    private final int bitMask;

    public EventColumn(int value, int bitMask) {
        this.value = value;
        this.bitMask = bitMask;
        MAPPING.put(this);
    }

    public int getBitMask() {
        return bitMask;
    }

    public int getMaskedValue(int value){
        return value&bitMask;
    }

    public int getValue() {
        return value;
    }
}
