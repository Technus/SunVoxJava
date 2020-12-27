package com.github.technus.sunvoxlib.model.pattern;

import com.sun.jna.Structure;
import com.sun.jna.Union;

@SuppressWarnings("DeprecatedIsStillUsed")
@Structure.FieldOrder({"note","vel","module","ctl","ctl_val"})
public class PatternNote extends Structure {
    @Deprecated
    public byte note;
    @Deprecated
    public byte vel;
    @Deprecated
    public short module;//+1 !!!
    @Deprecated
    public short ctl;
    @Deprecated
    public ControlValue ctl_val;

    public byte getNote() {
        return note;
    }

    public void setNote(byte note) {
        this.note = note;
    }

    public byte getVel() {
        return vel;
    }

    public void setVel(byte vel) {
        this.vel = vel;
    }

    public short getModule() {
        return module;
    }

    public void setModule(short module) {
        this.module = module;
    }

    public short getCtl() {
        return ctl;
    }

    public void setCtl(short ctl) {
        this.ctl = ctl;
    }

    public ControlValue getCtl_val() {
        return ctl_val;
    }

    public void setCtl_val(ControlValue ctl_val) {
        this.ctl_val = ctl_val;
    }

    public static class ControlValue extends Union{
        @Deprecated
        public short ctl_val;
        @Deprecated
        public EffectParameters xy;

        public short getCtl_val() {
            return ctl_val;
        }

        public void setCtl_val(short ctl_val) {
            this.ctl_val = ctl_val;
        }

        public EffectParameters getXy() {
            return xy;
        }

        public void setXy(EffectParameters xy) {
            this.xy = xy;
        }
    }

    @FieldOrder({"x","y"})
    public static class EffectParameters extends Structure{
        @Deprecated
        public byte x;
        @Deprecated
        public byte y;

        public byte getX() {
            return x;
        }

        public void setX(byte x) {
            this.x = x;
        }

        public byte getY() {
            return y;
        }

        public void setY(byte y) {
            this.y = y;
        }
    }
}
