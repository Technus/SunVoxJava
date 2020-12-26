package com.github.technus.sunvoxlib.model.number;

import com.github.technus.sunvoxlib.SunVoxLib;
import com.github.technus.sunvoxlib.model.mapping.IInteger;
import com.github.technus.sunvoxlib.model.mapping.IntegerMapping;

public class SampleType implements IInteger {
    public static final IntegerMapping<SampleType> MAPPING =new IntegerMapping<>();

    public static final SampleType INT16 = new SampleType(SunVoxLib.SV_STYPE_INT16, short.class);
    public static final SampleType INT32 = new SampleType(SunVoxLib.SV_STYPE_INT32, int.class);
    public static final SampleType FLOAT32 = new SampleType(SunVoxLib.SV_STYPE_FLOAT32, float.class);
    public static final SampleType FLOAT64 = new SampleType(SunVoxLib.SV_STYPE_FLOAT64, double.class);

    private final int value;
    private final Class<? extends Number> numberClass;

    public SampleType(int value, Class<? extends Number> numberClass) {
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
