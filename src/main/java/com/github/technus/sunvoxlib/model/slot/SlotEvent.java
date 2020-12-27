package com.github.technus.sunvoxlib.model.slot;

import com.github.technus.sunvoxlib.model.mapping.IEvent;
import com.github.technus.sunvoxlib.model.module.Module;
import com.github.technus.sunvoxlib.model.module.ModuleControl;
import com.github.technus.sunvoxlib.model.number.NoteCommand;
import com.github.technus.sunvoxlib.model.number.PatternEffect;

public class SlotEvent implements IEvent {
    private final int note, vel, value;
    private final Module module;
    private final ModuleControl control;
    private final PatternEffect effect;

    /**
     * @param note    0 - nothing; 1..127 - note number; 128 - note off
     * @param vel     velocity 1..129; 0 - default
     * @param module  0 (empty) or module number + 1 (1..65535)
     * @param control module control
     * @param effect  pattern effect
     * @param value value of the controller CC or parameter of the effect EE
     */
    public SlotEvent(int note, int vel, Module module, ModuleControl control, PatternEffect effect, int value) {
        if (note < 0 || note > 128) {
            throw new RuntimeException("Note out of range!");
        }
        if (vel < 0 || vel > 129) {
            throw new RuntimeException("Velocity out of range!");
        }
        this.note = (byte) note;
        this.vel = (byte) vel;
        this.module = module;
        this.control = control;
        this.effect = effect;
        this.value = value;
    }

    /**
     * @param note    0 - nothing; 1..127 - note number; 128 - note off
     * @param vel     velocity 1..129; 0 - default
     * @param module  0 (empty) or module number + 1 (1..65535)
     * @param control module control
     * @param effect  pattern effect
     * @param x value of the controller or parameter of the effect
     * @param y value of the controller or parameter of the effect
     */
    public SlotEvent(int note, int vel, Module module, ModuleControl control, PatternEffect effect, int x, int y) {
        if (note <= 0 || note >= 128) {
            throw new RuntimeException("Note out of range!");
        }
        if (vel <= 0 || vel >= 129) {
            throw new RuntimeException("Velocity out of range!");
        }
        this.note = (byte) note;
        this.vel = (byte) vel;
        this.module = module;
        this.control = control;
        this.effect = effect;
        this.value = ((x&0xff)<<8)|(y&0xff);
    }

    /**
     * @param note    note command
     * @param vel     velocity 1..129; 0 - default
     * @param module  0 (empty) or module number + 1 (1..65535)
     * @param control module control
     * @param effect  pattern effect
     * @param value value of the controller CC or parameter of the effect EE
     */
    public SlotEvent(NoteCommand note, byte vel, Module module, ModuleControl control, PatternEffect effect, int value) {
        this(note.getValue(),vel,module,control,effect, value);
    }

    /**
     * @param note    note command
     * @param vel     velocity 1..129; 0 - default
     * @param module  0 (empty) or module number + 1 (1..65535)
     * @param control module control
     * @param effect  pattern effect
     * @param x value of the controller or parameter of the effect
     * @param y value of the controller or parameter of the effect
     */
    public SlotEvent(NoteCommand note, byte vel, Module module, ModuleControl control, PatternEffect effect, int x,int y) {
        this(note.getValue(),vel,module,control,effect, x,y);
    }

    @Override
    public int getNote() {
        return note;
    }

    public Module getModule() {
        return module;
    }

    public ModuleControl getControl() {
        return control;
    }

    public PatternEffect getEffect() {
        return effect;
    }

    @Override
    public int getVelocity() {
        return vel;
    }

    @Override
    public int getModuleId() {
        return getModule()==null?0:getModule().getIdForEvent();
    }

    @Override
    public int getControlId() {
        return getControl()==null?0:getControl().getIdForEvent();
    }

    @Override
    public int getEffectId(){
        return getEffect()==null?0:getEffect().getIdForEvent();
    }

    @Override
    public int getValue() {
        return value;
    }
}
