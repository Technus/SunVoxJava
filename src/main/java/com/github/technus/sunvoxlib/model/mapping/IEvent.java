package com.github.technus.sunvoxlib.model.mapping;

import com.github.technus.sunvoxlib.model.number.NoteCommand;
import com.github.technus.sunvoxlib.model.number.PatternEffect;

public interface IEvent {
    /**
     * Gets the note
     * 0 - nothing command
     * 1-127 - notes
     * 128 - note off command
     * 129-255 - commands
     * @return note/command
     */
    int getNote();

    /**
     * Get the note command for note
     * @return note command definition or null
     */
    default NoteCommand getNoteCommand(){
        return NoteCommand.MAPPING.get(getNote());
    }

    /**
     * Get the effect definition for effect id
     * @return effect definition or null
     */
    default PatternEffect getPatternEffect() {
        return PatternEffect.MAPPING.get(getEffectId());
    }

    /**
     * 0 - 129
     * @return 0 - 129
     */
    int getVelocity();

    int getModuleId();

    int getControlId();

    int getEffectId();

    /**
     * The combination of control and effect id
     * @return
     */
    default int getCtl(){
        return ((getControlId()&0xFF)<<8)|(getEffectId()&0xff);
    }

    /**
     * The combination of XX and YY or a 16 bit ZZZZ value
     * @return
     */
    int getValue();

    default int getX(){
        return (getValue()>>8)&0xff;
    }

    default int getY(){
        return getValue()&0xff;
    }
}
