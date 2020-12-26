package com.github.technus.sunvoxlib.model.object.pattern;

import com.sun.jna.Structure;
import com.sun.jna.Union;

@Structure.FieldOrder({"note","vel","module","ctl","ctl_val"})
public class PatternNote extends Structure {
    private byte note;
    private byte vel;
    private short module;//+1 !!!
    private short ctl;
    private ControlValue ctl_val;

    public static class ControlValue extends Union{
        private short ctl_val;
        private EffectParameters xy;
    }

    public static class EffectParameters extends Structure{
        private byte x;
        private byte y;
    }
}
