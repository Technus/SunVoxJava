package com.github.technus.sunvoxlib.model;


import com.github.technus.sunvoxlib.model.module.Module;
import com.github.technus.sunvoxlib.model.slot.Slot;
import com.github.technus.sunvoxlib.model.slot.SlotEvent;
import com.github.technus.sunvoxlib.model.slot.SlotTrack;

import java.io.File;

import static com.github.technus.sunvoxlib.model.SunVox.getInstance;
import static com.github.technus.sunvoxlib.model.module.ModuleInternalType.*;
import static com.github.technus.sunvoxlib.model.number.InitializationFlag.AUDIO_FLOAT32;

class SunVoxTest {
    public static void main(String[] args) throws InterruptedException{
        try(SunVox sunVox =getInstance()){
            sunVox.init(48_000, AUDIO_FLOAT32);
            try(Slot slot = new Slot(sunVox, 0)){
                slot.volume(127);
                slot.lock();

                Module output = new Module(slot);
                Module genny1 = new Module(slot, ANALOG_GENERATOR,"Gen1");
                Module genny2 = new Module(slot, GENERATOR,"Gen2");
                Module vibrato = new Module(slot, VIBRATO,"Vib1");
                slot.connectModule(genny1,output);
                slot.connectModule(genny2,output);
                slot.connectModule(genny1,vibrato);
                slot.connectModule(vibrato,output);

                slot.unlock();

                SlotTrack slotTrack = new SlotTrack(slot, 0);
                SlotEvent slotEvent = new SlotEvent(30, 0, genny1,null,null,0);

                slot.play();

                slotTrack.sendEvent(slotEvent);

                Thread.sleep(1000);
                slot.stop();

                slot.load(new File("b ass 1.sunvox"));
                slot.play();
                Thread.sleep(slot.getSongLengthFrames()/ sunVox.getSampleRate()*1000L);
            }
        }
    }
}