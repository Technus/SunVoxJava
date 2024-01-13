package com.github.technus.sunvoxlib.model.pattern;

import com.github.technus.sunvoxlib.model.mapping.IEvent;
import com.sun.jna.Structure;

@SuppressWarnings("DeprecatedIsStillUsed")
@Structure.FieldOrder({"note","vel","module","control","effect","value"})
public class PatternEvent extends Structure implements IEvent {
    @Deprecated
    public byte note;
    @Deprecated
    public byte vel;
    @Deprecated
    public short module;//+1 !!!
    @Deprecated
    public byte control;
    @Deprecated
    public byte effect;
    @Deprecated
    public short value;

    public PatternEvent(){

    }

    public PatternEvent(byte note, byte vel, short module, byte control, byte effect, short value) {
        this.note = note;
        this.vel = vel;
        this.module = module;
        this.control = control;
        this.effect = effect;
        this.value = value;
    }

    public int getNote() {
        return note&0xff;
    }

    public int getVelocity() {
        return vel&0xff;
    }

    public int getModuleId() {
        return module&0xffff;
    }

    public int getControlId() {
        return control&0xff;
    }

    public int getEffectId() {
        return effect&0xff;
    }

    public int getValue(){
        return value&0xffff;
    }
}
