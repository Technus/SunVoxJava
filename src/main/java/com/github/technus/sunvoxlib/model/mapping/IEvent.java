package com.github.technus.sunvoxlib.model.mapping;

import com.github.technus.sunvoxlib.model.number.NoteCommand;

public interface IEvent {
    int getNote();

    default NoteCommand getNoteCommand(){
        return NoteCommand.MAPPING.get(getNote()&0xff);
    }

    int getVelocity();

    int getModuleId();

    int getControlId();

    int getEffectId();

    default int getCtl(){
        return ((getControlId()&0xFF)<<8)|(getEffectId()&0xff);
    }

    int getValue();

    default int getX(){
        return (getValue()>>8)&0xff;
    }

    default int getY(){
        return getValue()&0xff;
    }
}
