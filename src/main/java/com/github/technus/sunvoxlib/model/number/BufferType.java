package com.github.technus.sunvoxlib.model.number;

import com.github.technus.sunvoxlib.model.mapping.IInteger;
import com.github.technus.sunvoxlib.model.mapping.IntegerMapping;

public class BufferType implements IInteger {
    public static final IntegerMapping<BufferType> MAPPING =new IntegerMapping<>();

    public static final BufferType INT16 = new BufferType(0, Short.class);
    public static final BufferType FLOAT32 = new BufferType(1, Float.class);

    private final int value;
    private final Class<? extends Number> numberClass;

    public BufferType(int value, Class<? extends Number> numberClass) {
        this.value = value;
        this.numberClass = numberClass;
        MAPPING.put(this);
    }

    public int getValue() {
        return value;
    }

    public Class<? extends Number> getNumberClass() {
        return numberClass;
    }
}
