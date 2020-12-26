package com.github.technus.sunvoxlib.model.number;

import com.github.technus.sunvoxlib.SunVoxLib;
import com.github.technus.sunvoxlib.model.mapping.IInteger;
import com.github.technus.sunvoxlib.model.mapping.IntegerMapping;

public class NoteCommand implements IInteger {
    public static final IntegerMapping<NoteCommand> MAPPING=new IntegerMapping<>();

    public static final NoteCommand NOTHING=new NoteCommand(SunVoxLib.NOTECMD_NOTHING);
    public static final NoteCommand OFF = new NoteCommand(SunVoxLib.NOTECMD_NOTE_OFF);
    public static final NoteCommand ALL_OFF = new NoteCommand(SunVoxLib.NOTECMD_ALL_NOTES_OFF);
    public static final NoteCommand CLEAN_SYNTHS = new NoteCommand(SunVoxLib.NOTECMD_CLEAN_SYNTHS);
    public static final NoteCommand STOP = new NoteCommand(SunVoxLib.NOTECMD_STOP);
    public static final NoteCommand PLAY = new NoteCommand(SunVoxLib.NOTECMD_PLAY);
    public static final NoteCommand SET_PITCH = new NoteCommand(SunVoxLib.NOTECMD_SET_PITCH);

    private final int value;

    public NoteCommand(int value) {
        this.value = value;
        MAPPING.put(this);
    }

    public int getValue() {
        return value;
    }
}
