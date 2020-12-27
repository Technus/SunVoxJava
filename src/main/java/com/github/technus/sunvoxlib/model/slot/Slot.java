package com.github.technus.sunvoxlib.model.slot;

import com.github.technus.sunvoxlib.SunVoxLib;
import com.github.technus.sunvoxlib.model.SunVox;
import com.github.technus.sunvoxlib.model.module.Module;
import com.github.technus.sunvoxlib.model.number.*;

import java.io.File;

import static com.github.technus.sunvoxlib.model.SunVoxException.intIfOk;
import static com.github.technus.sunvoxlib.model.SunVoxException.voidIfOk;

public class Slot implements AutoCloseable {
    private final SunVox engine;
    /**
     * slot number
     */
    private final int id;
    private volatile Thread owner;

    public Slot(SunVox engine, int id) {
        this.engine = engine;
        this.id = id;
        openSlot();
    }

    public SunVox getEngine() {
        return engine;
    }

    public int getId() {
        return id;
    }

    public Thread getOwner() {
        return owner;
    }

    protected void setOwner(Thread thread){
        owner=thread;
    }

    @Override
    public void close() {
        closeSlot();
    }

    public boolean holdsLock(){
        return getOwner()==Thread.currentThread();
    }

    public void throwIfDoesntHoldLock(){
        if(!holdsLock()){
            throw new RuntimeException("Needs to hold lock!");
        }
    }

    /**
     *
     * @return true if succeeded, false otherwise
     */
    public boolean lock(){
        if(getOwner()==null){
            synchronized (this){
                if(getOwner()==null){
                    setOwner(Thread.currentThread());
                    lockSlot();
                    return true;
                }
            }
        }
        return false;
    }

    public void unlock(){
        if(getOwner()!=null){
            synchronized (this){
                if(getOwner()!=null){
                    unlockSlot();
                    setOwner(null);
                }
            }
        }
    }

    //region Main

    /**
     * Open sound slot.
     * Each slot can contain one independent implementation of the SunVox engine.
     * You can use several slots simultaneously (for example, one slot for music and another for effects).
     * Max number of slots = 16.
     */
    protected void openSlot() {
        voidIfOk(SunVoxLib.sv_open_slot(getId()));
    }

    /**
     * Close sound slot.
     * Each slot can contain one independent implementation of the SunVox engine.
     * You can use several slots simultaneously (for example, one slot for music and another for effects).
     * Max number of slots = 16.
     */
    protected void closeSlot() {
        voidIfOk(SunVoxLib.sv_close_slot(getId()));
    }

    /**
     * Lock the specified slot.
     * Use lock/unlock when you simultaneously read and modify SunVox data from different threads (for the same slot).
     * Some functions (marked as "USE LOCK/UNLOCK") can't work without lock/unlock at all.
     */
    protected void lockSlot() {
        voidIfOk(SunVoxLib.sv_lock_slot(getId()));
    }

    /**
     * Unlock the specified slot.
     * Use lock/unlock when you simultaneously read and modify SunVox data from different threads (for the same slot).
     * Some functions (marked as "USE LOCK/UNLOCK") can't work without lock/unlock at all.
     */
    protected void unlockSlot() {
        voidIfOk(SunVoxLib.sv_unlock_slot(getId()));
    }

    //endregion

    //region Project

    //region Project file

    /**
     * Load SunVox project from the file
     * @param file file
     */
    public void load(File file) {
        voidIfOk(SunVoxLib.sv_load(getId(), file.getAbsolutePath()));
    }

    /**
     * Load SunVox project from the memory block
     * @param data byte array with the project (to load from memory)
     */
    public void loadFromMemory(byte[] data) {
        voidIfOk(SunVoxLib.sv_load_from_memory(getId(), data));
    }

    //endregion

    //region Project playback

    /**
     * play from the current position
     */
    public void play() {
        voidIfOk(SunVoxLib.sv_play(getId()));
    }

    /**
     * play from the beginning (line 0)
     */
    public void playFromBeginning() {
        voidIfOk(SunVoxLib.sv_play_from_beginning(getId()));
    }

    /**
     * reset all SunVox activity and switch the engine to standby mode
     */
    public void stop() {
        voidIfOk(SunVoxLib.sv_stop(getId()));
    }

    /**
     * pause the audio stream on the specified slot
     */
    public void pause() {
        voidIfOk(SunVoxLib.sv_pause(getId()));
    }

    /**
     * resume the audio stream on the specified slot
     */
    public void resume() {
        voidIfOk(SunVoxLib.sv_resume(getId()));
    }

    /**
     * Set autostop mode. When autostop is OFF, the project plays endlessly in a loop.
     * @param autostop disable,enable
     */
    public void setAutostop(AutostopStatus autostop) {
        voidIfOk(SunVoxLib.sv_set_autostop(getId(), autostop.getValue()));
    }

    /**
     * Get autostop mode. When autostop is OFF, the project plays endlessly in a loop.
     * @return auto stop status
     */
    public AutostopStatus getAutostop() {
        return AutostopStatus.MAPPING.get(intIfOk(SunVoxLib.sv_get_autostop(getId())));
    }

    /**
     * Check if the project has finished playing
     * @return play status
     */
    public PlayStatus endOfSong() {
        return PlayStatus.MAPPING.get(intIfOk(SunVoxLib.sv_end_of_song(getId())));
    }

    /**
     * Jump to the specified position (line number on the timeline).
     * @param line_num line number on the timeline
     */
    public void rewind(int line_num) {
        voidIfOk(SunVoxLib.sv_rewind(getId(), line_num));
    }

    /**
     * Set the project volume
     * @param vol volume from 0 (min) to 256 (max 100%)
     *            negative values are ignored
     * @return previous volume
     */
    public int volume(int vol) {
        return intIfOk(SunVoxLib.sv_volume(getId(), vol));
    }

    /**
     * get current (real time) line number on the timeline
     * @return current line number (playback position) on the timeline
     */
    public int getCurrentLine() {
        return intIfOk(SunVoxLib.sv_get_current_line(getId()));
    }

    /**
     * get current line in fixed point format 27.5
     * @return current line number (playback position) on the timeline
     */
    public int getCurrentLine2() {
        return intIfOk(SunVoxLib.sv_get_current_line2(getId()));
    }

    /**
     * Get current (real time) signal level from the Output module
     * @param channel channel
     * @return current signal level (from 0 to 255)
     */
    public int getCurrentSignalLevel(Channel channel) {
        return intIfOk(SunVoxLib.sv_get_current_signal_level(getId(), channel.getValue()));
    }

    //endregion

    //region Project info

    /**
     * Get the project name
     * @return project name or null
     */
    public String getSongName() {
        return SunVoxLib.sv_get_song_name(getId());
    }

    /**
     * Get the project BPM (Beats Per Minute)
     * @return project BPM
     */
    public int getSongBpm() {
        return intIfOk(SunVoxLib.sv_get_song_bpm(getId()));
    }

    /**
     * Get the project TPL (Ticks Per Line)
     * @return project TPL
     */
    public int getSongTpl() {
        return intIfOk(SunVoxLib.sv_get_song_tpl(getId()));
    }

    /**
     * Get the the project length in frames.
     * A frame is a pair of audio signal samples (left and right channel amplitudes).
     * The sample rate 44100 Hz means, that you hear 44100 frames per second.
     * @return project length in frames
     */
    public int getSongLengthFrames() {
        return intIfOk(SunVoxLib.sv_get_song_length_frames(getId()));
    }

    /**
     * Get the the project length in lines
     * @return project length in lines
     */
    public int getSongLengthLines() {
        return intIfOk(SunVoxLib.sv_get_song_length_lines(getId()));
    }

    /**
     * Get the project time map
     * @param start_line first line to read (usually 0)
     * @param len number of lines to read
     * @param dest pointer to the buffer (size = len*sizeof({@link Integer})) for storing the map values
     * @param flags flags
     */
    public void getTimeMap(int start_line, int len, int[] dest, TimeMapping flags) {
        voidIfOk(SunVoxLib.sv_get_time_map(getId(), start_line, len, dest, flags.getValue()));
    }

    //endregion

    //endregion

    //region Events

    /**
     * Set the timestamp of events to be sent by {@link SlotTrack#sendEvent}.
     * Every event you send has a timestamp - this is the time when the event was generated (for example, when a key was pressed).
     * @param set event timing mode
     * @param t timestamp (in system ticks) for all further events
     */
    public void setEventT(EventTiming set, int t) {
        voidIfOk(SunVoxLib.sv_set_event_t(getId(), set.getValue(), t));
    }

    //endregion

    //region modules

    /**
     * Get number of modules in the project
     * @return number of modules
     */
    public int getNumberOfModules() {
        return intIfOk(SunVoxLib.sv_get_number_of_modules(getId()));
    }

    /**
     * Find a module by name
     * @param name module name
     * @return module number
     */
    public int findModule(String name) {
        return intIfOk(SunVoxLib.sv_find_module(getId(), name));
    }

    /**
     * Connect two specified modules: source --> destination.
     * USE LOCK/UNLOCK!
     * @param source source (module number)
     * @param destination destination (module number)
     */
    public void connectModule(Module source, Module destination) {
        throwIfDoesntHoldLock();
        if(source.getSlot()==this && destination.getSlot()==this){
            voidIfOk(SunVoxLib.sv_connect_module(getId(), source.getId(), destination.getId()));
        }else {
            throw new RuntimeException("Cannot connect across different Slots!");
        }
    }

    /**
     * Disconnect two specified modules: source --> destination.
     * USE LOCK/UNLOCK!
     * @param source source (module number)
     * @param destination destination (module number)
     */
    public void disconnectModule(Module source, Module destination) {
        throwIfDoesntHoldLock();
        if(source.getSlot()==this && destination.getSlot()==this) {
            voidIfOk(SunVoxLib.sv_disconnect_module(getId(), source.getId(), destination.getId()));
        }
    }

    //endregion

    //region Patterns

    /**
     * Get the number of patterns in the project
     * @return number of patterns
     */
    public int getNumberOfPatterns() {
        return intIfOk(SunVoxLib.sv_get_number_of_patterns(getId()));
    }

    /**
     * Find a pattern by name
     * @param name pattern name
     * @return pattern number
     */
    public int findPattern(String name) {
        return intIfOk(SunVoxLib.sv_find_pattern(getId(), name));
    }

    //endregion
}
