package com.github.technus.sunvoxlib.model.number;

public class EngineVersion {
    private final int value;

    public EngineVersion(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public int getMajor(){
        return (value>>>16)&0xFF;
    }

    public int getMinor(){
        return (value>>>8)&0xFF;
    }

    public int getPatch(){
        return value&0xFF;
    }

    public String getName(){
        return getMajor()+"."+getMinor()+"."+getPatch();
    }
}
