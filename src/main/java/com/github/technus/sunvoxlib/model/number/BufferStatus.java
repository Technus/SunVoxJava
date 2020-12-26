package com.github.technus.sunvoxlib.model.number;

import com.github.technus.sunvoxlib.model.mapping.IInteger;
import com.github.technus.sunvoxlib.model.mapping.IntegerMapping;

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
