package com.github.technus.sunvoxlib.model.pattern;

import com.github.technus.sunvoxlib.SunVoxLib;
import com.github.technus.sunvoxlib.model.mapping.IEvent;
import com.github.technus.sunvoxlib.model.number.EventColumn;
import com.github.technus.sunvoxlib.model.number.MuteStatus;
import com.github.technus.sunvoxlib.model.slot.Slot;

import static com.github.technus.sunvoxlib.model.SunVoxException.*;

public class Pattern implements AutoCloseable{
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

    public Pattern(Slot slot, int clone, int x, int y, int tracks, int lines, int iconSeed, String name)
    {
        this.slot=slot;
        this.id= newPattern(clone, x, y, tracks, lines, iconSeed, name);
    }

    public Slot getSlot() {
        return slot;
    }

    public int getId() {
        return id;
    }

    @Override
    public void close() {
        removePattern();
    }

    /**
     * Add pattern to project
     * @return the number of the newly created module
     */
    protected int newPattern(int clone, int x, int y, int tracks, int lines, int iconSeed, String name) {
        getSlot().throwIfDoesNotHoldLock();
        return intIfOk(SunVoxLib.sv_new_pattern(getSlot().getId(),clone,x,y,tracks,lines,iconSeed,name));
    }

    /**
     * Add pattern to project
     */
    protected void removePattern() {
        getSlot().throwIfDoesNotHoldLock();
        voidIfOk(SunVoxLib.sv_remove_pattern(getSlot().getId(),getId()));
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

    public int[] getXY(){
        return new int[]{getX(),getY()};
    }

    public void setXY(int x, int y){
        getSlot().throwIfDoesNotHoldLock();
        voidIfOk(SunVoxLib.sv_set_pattern_xy(getSlot().getId(),getId(),x,y));
    }

    public void setXY(int[] xy){
        setXY(xy[0],xy[1]);
    }

    public void setX(int x){
        setXY(x,getY());
    }

    public void setY(int y){
        setXY(getX(),y);
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
     * Set the number of tracks of the specified pattern
     */
    public void setTracks(int tracks){
        setSize(tracks,getLines());
    }

    /**
     * Set the number of lines of the specified pattern
     */
    public void setLines(int lines){
        setSize(getTracks(),lines);
    }

    /**
     * Set the number of lines and tracks of the specified pattern
     */
    public void setSize(int tracks,int lines){
        getSlot().throwIfDoesNotHoldLock();
        voidIfOk(SunVoxLib.sv_set_pattern_size(getSlot().getId(),getId(),tracks,lines));
    }

    /**
     * Get the name of the specified pattern
     * @return pattern name or null
     */
    public String getName() {
        return stringIfNotNull(SunVoxLib.sv_get_pattern_name(getSlot().getId(), getId()));
    }

    /**
     * Set the name of the specified pattern
     */
    public void setName(String name) {
        getSlot().throwIfDoesNotHoldLock();
        voidIfOk(SunVoxLib.sv_set_pattern_name(getSlot().getId(), getId(),name));
    }

    /**
     * Get the contents of the pattern (array of events: notes, effects, etc.)
     *    containing notes (events) in the following order:
     *      line 0: note for track 0, note for track 1, ... note for track X;
     *      line 1: note for track 0, note for track 1, ... note for track X;
     *      ...
     *      line X: ...
     * With The struct layout
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
        return (PatternEvent[]) SunVoxLib.sv_get_pattern_data(getSlot().getId(), getId())
                .toArray(new PatternEvent[getTracks() * getLines()]);
    }

    public void setEvent(int track, int line, IEvent event){
        voidIfOk(SunVoxLib.sv_set_pattern_event(getSlot().getId(),getId(),track,line,
                event.getNote(), event.getVelocity(), event.getModuleId(), event.getCtl(), event.getValue()));
    }

    public int getEventColumn(int track, int line, EventColumn column){
        return column.getMaskedValue(intIfOk(SunVoxLib.sv_get_pattern_event(getSlot().getId(),getId(),track,line,column.getValue())));
    }

    /**
     * Mute / unmute the specified pattern.
     * USE LOCK/UNLOCK!
     * @param mute mute status
     * @return previous state
     */
    public int mute(int mute) {
        getSlot().throwIfDoesNotHoldLock();
        return intIfOk(SunVoxLib.sv_pattern_mute(getSlot().getId(), getId(), mute));
    }

    /**
     * Mute / unmute the specified pattern.
     * USE LOCK/UNLOCK!
     * @param mute mute status
     * @return previous state
     */
    public MuteStatus mute(MuteStatus mute) {
        return MuteStatus.MAPPING.get(mute(mute.getValue()));
    }
}
