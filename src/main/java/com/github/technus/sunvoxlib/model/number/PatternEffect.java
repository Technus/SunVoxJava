package com.github.technus.sunvoxlib.model.number;

import com.github.technus.sunvoxlib.model.mapping.IInteger;
import com.github.technus.sunvoxlib.model.mapping.IntegerMapping;

public class PatternEffect implements IInteger {
    public static final IntegerMapping<PatternEffect> MAPPING=new IntegerMapping<>();

    public static final PatternEffect NONE=new PatternEffect(0x00);
    public static final PatternEffect SLIDE_UP=new PatternEffect(0x01);
    public static final PatternEffect SLIDE_DOWN=new PatternEffect(0x02);
    public static final PatternEffect SLIDE_TO_NOTE=new PatternEffect(0x03);
    public static final PatternEffect VIBRATO=new PatternEffect(0x04);
    public static final PatternEffect PITCH_BEND_UP=new PatternEffect(0x05);
    public static final PatternEffect PITCH_BEND_DOWN=new PatternEffect(0x06);
    public static final PatternEffect SET_SAMPLE_OFFSET_PERCENT=new PatternEffect(0x07);
    public static final PatternEffect ARPEGGIO=new PatternEffect(0x08);
    public static final PatternEffect SET_SAMPLE_OFFSET=new PatternEffect(0x09);
    public static final PatternEffect SLIDE_VELOCITY=new PatternEffect(0x0A);
    public static final PatternEffect SET_PLAYING_SPEED=new PatternEffect(0x0F);
    public static final PatternEffect FINE_SLIDE_UP=new PatternEffect(0x11);
    public static final PatternEffect FINE_SLIDE_DOWN=new PatternEffect(0x12);
    public static final PatternEffect SET_FLAGS=new PatternEffect(0x13);
    public static final PatternEffect CLEAR_FLAGS=new PatternEffect(0x14);
    public static final PatternEffect TRANSPOSE_MODULE=new PatternEffect(0x15);
    public static final PatternEffect RE_TRIGGER=new PatternEffect(0x19);
    public static final PatternEffect FINE_VEL_SLIDE=new PatternEffect(0x1A);
    public static final PatternEffect CUT=new PatternEffect(0x1C);
    public static final PatternEffect DELAY=new PatternEffect(0x1D);
    public static final PatternEffect SET_BPM=new PatternEffect(0x1F);
    public static final PatternEffect NOTE_PROBABILITY=new PatternEffect(0x20);
    public static final PatternEffect NOTE_PROBABILITY_RANDOM_VELOCITY=new PatternEffect(0x21);
    public static final PatternEffect WRITE_RANDOM_TO_CONTROLLER=new PatternEffect(0x22);
    public static final PatternEffect WRITE_RANDOM_RANGE_TO_CONTROLLER=new PatternEffect(0x23);
    public static final PatternEffect NOTE_COPY_LINE=new PatternEffect(0x24);
    public static final PatternEffect NOTE_COPY_RANDOM_RANGE_LINE=new PatternEffect(0x25);
    public static final PatternEffect NOTE_COPY_TRACK=new PatternEffect(0x26);
    public static final PatternEffect NOTE_COPY_RANDOM_RANGE_TRACK=new PatternEffect(0x27);
    public static final PatternEffect NOTE_COPY_TRACK_0=new PatternEffect(0x28);
    public static final PatternEffect NOTE_COPY_RANDOM_RANGE_TRACK_0=new PatternEffect(0x29);
    public static final PatternEffect STOP=new PatternEffect(0x30);
    public static final PatternEffect JUMP=new PatternEffect(0x31);
    public static final PatternEffect JUMP_SET_MODE=new PatternEffect(0x32);
    public static final PatternEffect DELETE_PROBABILITY=new PatternEffect(0x38);
    public static final PatternEffect CYCLIC_SHIFT_DOWN=new PatternEffect(0x39);
    public static final PatternEffect GENERATE_NEW_POLY_RHYTHM=new PatternEffect(0x3A);
    public static final PatternEffect COPY_TRACK_TO_PATTERN=new PatternEffect(0x3B);
    public static final PatternEffect COPY_TRACK_FROM_PATTERN=new PatternEffect(0x3C);
    public static final PatternEffect RANDOM=new PatternEffect(0x3D);

    /**
     *
     * @param fractionOfLine clipped to valid range of 0 to 0.96875 (0x1f states)
     * @return effect
     */
    public static PatternEffect delayEvent(float fractionOfLine){
        if(fractionOfLine<0f){
            fractionOfLine=0;
        }else if (fractionOfLine>.96875f){
            fractionOfLine=1;
        }else {
            fractionOfLine=fractionOfLine/.96875f;
        }
        return new PatternEffect(0x40+Math.round(0x1f*fractionOfLine));
    }

    private final int id;

    public PatternEffect(int id) {
        this.id = id;
        MAPPING.put(this);
    }


    @Override
    public int getValue() {
        return id;
    }

    public int getIdForEvent(){
        return getValue();
    }
}
