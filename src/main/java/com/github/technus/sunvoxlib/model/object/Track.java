package com.github.technus.sunvoxlib.model.object;

import com.github.technus.sunvoxlib.SunVoxLib;
import com.github.technus.sunvoxlib.model.number.NoteCommand;
import com.github.technus.sunvoxlib.model.object.module.Module;

import static com.github.technus.sunvoxlib.model.SunVoxException.voidIfOk;

public class Track {
    private final Slot slot;
    /**
     * track_num
     */
    private final int id;

    public Track(Slot slot,int id) {
        this.slot = slot;
        this.id = id;
    }

    public Slot getSlot() {
        return slot;
    }

    public int getId() {
        return id;
    }

    /**
     * Send an event (commands such as Note ON, Note OFF, controller change, etc.) to the SunVox engine for further processing.
     * @param note  0 - nothing; 1..127 - note number; 128 - note off
     * @param vel velocity 1..129; 0 - default
     * @param module 0 (empty) or module number + 1 (1..65535)
     * @param ctl CC - controller number (1..255)
     * @param effect EE - effect  (0..255)
     * @param ctl_val value of the controller CC or parameter of the effect EE
     */
    public void sendEvent(int note, int vel, Module module, int ctl, int effect, int ctl_val) {
        if(note<=0 || note>=128){
            throw new RuntimeException("Note out of range!");
        }
        voidIfOk(SunVoxLib.sv_send_event(getSlot().getId(), getId(), note, vel, module.getId()+1, (ctl<<8)|effect, ctl_val));
    }

    /**
     * Send an event (commands such as Note ON, Note OFF, controller change, etc.) to the SunVox engine for further processing.
     * @param note  0 - nothing; 1..127 - note number; 128 - note off
     * @param vel velocity 1..129; 0 - default
     * @param ctl CC - controller number (1..255)
     * @param effect EE - effect  (0..255)
     * @param ctl_val value of the controller CC or parameter of the effect EE
     */
    public void sendEvent(int note, int vel, int ctl, int effect, int ctl_val) {
        if(note<=0 || note>=128){
            throw new RuntimeException("Note out of range!");
        }
        voidIfOk(SunVoxLib.sv_send_event(getSlot().getId(), getId(), note, vel, 0, (ctl<<8)|effect, ctl_val));
    }

    /**
     * Send an event (commands such as Note ON, Note OFF, controller change, etc.) to the SunVox engine for further processing.
     * @param noteCommand note command
     * @param vel velocity 1..129; 0 - default
     * @param module 0 (empty) or module number + 1 (1..65535)
     * @param ctl CC - controller number (1..255)
     * @param effect EE - effect  (0..255)
     * @param ctl_val value of the controller CC or parameter of the effect EE
     */
    public void sendEvent(NoteCommand noteCommand, int vel, Module module, int ctl, int effect, int ctl_val) {
        voidIfOk(SunVoxLib.sv_send_event(getSlot().getId(), getId(), noteCommand.getValue(), vel, module.getId()+1, (ctl<<8)|effect, ctl_val));
    }

    /**
     * Send an event (commands such as Note ON, Note OFF, controller change, etc.) to the SunVox engine for further processing.
     * @param noteCommand note command
     * @param vel velocity 1..129; 0 - default
     * @param ctl CC - controller number (1..255)
     * @param effect EE - effect  (0..255)
     * @param ctl_val value of the controller CC or parameter of the effect EE
     */
    public void sendEvent(NoteCommand noteCommand, int vel, int ctl, int effect, int ctl_val) {
        voidIfOk(SunVoxLib.sv_send_event(getSlot().getId(), getId(), noteCommand.getValue(), vel, 0, (ctl<<8)|effect, ctl_val));
    }
}
