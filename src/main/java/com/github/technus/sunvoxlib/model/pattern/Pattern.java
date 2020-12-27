package com.github.technus.sunvoxlib.model.pattern;

import com.github.technus.sunvoxlib.SunVoxLib;
import com.github.technus.sunvoxlib.model.number.MuteStatus;
import com.github.technus.sunvoxlib.model.slot.Slot;

import static com.github.technus.sunvoxlib.model.SunVoxException.intIfOk;

public class Pattern {
    private final Slot slot;
    private final int id;

    public Pattern(Slot slot, int id) {
        this.slot = slot;
        this.id = id;
    }

    public Pattern(Slot slot, String name) {
        this.slot=slot;
        this.id= getSlot().findPattern(name);
    }

    public Slot getSlot() {
        return slot;
    }

    public int getId() {
        return id;
    }

    /**
     * Get the X (line number) coordinate of the pattern on the timeline
     * @return X (line number) coordinate of the pattern on the timeline
     */
    public int getX() {
        return SunVoxLib.sv_get_pattern_x(getSlot().getId(), getId());
    }

    /**
     * Get the Y coordinate of the pattern on the timeline
     * @return Y coordinate of the pattern on the timeline
     */
    public int getY() {
        return SunVoxLib.sv_get_pattern_y(getSlot().getId(), getId());
    }

    /**
     * Get the number of tracks of the specified pattern
     * @return number of tracks
     */
    public int getTracks() {
        return intIfOk(SunVoxLib.sv_get_pattern_tracks(getSlot().getId(), getId()));
    }

    /**
     * Get the number of lines of the specified pattern
     * @return number of lines
     */
    public int getLines() {
        return intIfOk(SunVoxLib.sv_get_pattern_lines(getSlot().getId(), getId()));
    }

    /**
     * Get the name of the specified pattern
     * @return pattern name or null
     */
    public String getName() {
        return SunVoxLib.sv_get_pattern_name(getSlot().getId(), getId());
    }

    /**
     * Get the contents of the pattern (array of events: notes, effects, etc.)
     * offset | description
     *        | line 0; track 0;
     * 0      | note (NN)
     * 1      | velocity (VV)
     * 2      | module number + 1 (MM); low byte
     * 3      | module number + 1 (MM); high byte
     * 4      | effect code (EE)
     * 5      | controller number + 1 (CC)
     * 6      | controller value or effect parameter (YY); low byte
     * 7      | controller value or effect parameter (XX); high byte
     *        | line 0; track 1;
     * 8      | note (NN)
     * ...    | ...
     * @return array with the contents of the pattern or null
     */
    public PatternEvent[] getData() {
        return (PatternEvent[]) SunVoxLib.sv_get_pattern_data(getSlot().getId(), getId()).toArray(new PatternEvent[
                getTracks()* getLines()]);
    }

    /**
     * Mute / unmute the specified pattern.
     * USE LOCK/UNLOCK!
     * @param mute mute status
     * @return previous state
     */
    public MuteStatus mute(MuteStatus mute) {
        getSlot().throwIfDoesntHoldLock();
        return MuteStatus.MAPPING.get(intIfOk(SunVoxLib.sv_pattern_mute(getSlot().getId(), getId(), mute.getValue())));
    }

}
