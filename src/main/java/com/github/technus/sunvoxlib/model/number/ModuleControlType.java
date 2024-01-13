package com.github.technus.sunvoxlib.model.number;

import com.github.technus.sunvoxlib.model.mapping.IInteger;
import com.github.technus.sunvoxlib.model.mapping.IntegerMapping;

public class ModuleControlType implements IInteger {
    public static final IntegerMapping<ModuleControlType> MAPPING=new IntegerMapping<>();

    public static final ModuleControlType SCALED=new ModuleControlType(0);
    public static final ModuleControlType ENUM=new ModuleControlType(1);

    private final int value;

    public ModuleControlType(int value) {
        this.value = value;
        MAPPING.put(this);
    }

    public int getValue() {
        return value;
    }
}
