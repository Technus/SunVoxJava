package com.github.technus.sunvoxlib.model;

import static com.github.technus.sunvoxlib.SunVoxLib.GENERIC_ERROR_CODE;

public class SunVoxException extends RuntimeException {
    private final int returnValue;

    public SunVoxException(int returnValue) {
        super("Failed to run native method error code: "+returnValue);
        this.returnValue = returnValue;
    }

    public SunVoxException() {
        super("Failed to run native method");
        this.returnValue = GENERIC_ERROR_CODE;
    }

    public int getReturnValue() {
        return returnValue;
    }

    public static int intIfNotGenericError(int returnValue){
        voidIfNotGenericError(returnValue);
        return returnValue;
    }

    public static int intIfOk(int returnValue){
        voidIfOk(returnValue);
        return returnValue;
    }

    public static void voidIfNotGenericError(int returnValue){
        if(returnValue==GENERIC_ERROR_CODE){
            throw new SunVoxException();
        }
    }

    public static void voidIfOk(int returnValue){
        if(returnValue<0){
            throw new SunVoxException(returnValue);
        }
    }
}
