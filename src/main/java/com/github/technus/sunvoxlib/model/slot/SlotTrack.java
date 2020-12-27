package com.github.technus.sunvoxlib.model.slot;

import com.github.technus.sunvoxlib.SunVoxLib;
import com.github.technus.sunvoxlib.model.mapping.IEvent;

import static com.github.technus.sunvoxlib.model.SunVoxException.voidIfOk;

public class SlotTrack {
    private final Slot slot;
    /**
     * track_num
     */
    private final int id;

    public SlotTrack(Slot slot, int id) {
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
     * @param event event
     */
    public void sendEvent(IEvent event) {
        voidIfOk(SunVoxLib.sv_send_event(getSlot().getId(), getId(), event.getNote(), event.getVelocity(), event.getModuleId(), event.getCtl(), event.getValue()));
    }
}
