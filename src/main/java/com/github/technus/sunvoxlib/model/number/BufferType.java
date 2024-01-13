package com.github.technus.sunvoxlib.model.number;

import com.github.technus.sunvoxlib.model.mapping.IInteger;
import com.github.technus.sunvoxlib.model.mapping.IntegerMapping;

public class BufferType implements IInteger {
    public static final IntegerMapping<BufferType> MAPPING =new IntegerMapping<>();

    public static final BufferType INT16 = new BufferType(0, Short.class, InitializationFlag.AUDIO_INT16);
    public static final BufferType FLOAT32 = new BufferType(1, Float.class, InitializationFlag.AUDIO_FLOAT32);

    private final int value;
    private final Class<? extends Number> numberClass;
    private final InitializationFlag audioInitializationFlag;

    public BufferType(int value, Class<? extends Number> numberClass, InitializationFlag audioInitializationFlag) {
        this.value = value;
        this.numberClass = numberClass;
        this.audioInitializationFlag = audioInitializationFlag;
        MAPPING.put(this);
    }

    public static BufferType forFlags(InitializationFlag... flags) {
        if(flags == null || flags.length == 0)
            throw new RuntimeException("Flags was null or empty");

        BufferType bufferType=null;

        for (BufferType type:MAPPING) {
            for (InitializationFlag flag:flags) {
                if(flag.getValue()==type.getAudioInitializationFlag().getValue()){
                    if(bufferType!=null)
                        throw new RuntimeException("Multiple buffer type flags");
                    bufferType = type;
                }
            }
        }

        if(bufferType==null)
            throw new RuntimeException("No buffer type flags");

        return bufferType;
    }

    public int getValue() {
        return value;
    }

    public Class<? extends Number> getNumberClass() {
        return numberClass;
    }

    public InitializationFlag getAudioInitializationFlag() {
        return audioInitializationFlag;
    }
}
