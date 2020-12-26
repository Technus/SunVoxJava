package nightradio.sunvoxlib.model.object.module;

import nightradio.sunvoxlib.model.mapping.IString;
import nightradio.sunvoxlib.model.mapping.StringMapping;

public class ModuleInternalType implements IString {
    public static final StringMapping<ModuleInternalType> MAPPING=new StringMapping<>();

    public static final ModuleInternalType ANALOG_GENERATOR=new ModuleInternalType("Analog generator");
    public static final ModuleInternalType DRUM_SYNTH=new ModuleInternalType("DrumSynth");
    public static final ModuleInternalType FREQUENCY_MODULATOR=new ModuleInternalType("FM");
    public static final ModuleInternalType GENERATOR=new ModuleInternalType("Generator");
    public static final ModuleInternalType INPUT=new ModuleInternalType("Input");
    public static final ModuleInternalType KICKER=new ModuleInternalType("Kicker");
    public static final ModuleInternalType VORBIS_PLAYER=new ModuleInternalType("Vorbis player");
    public static final ModuleInternalType SAMPLER=new ModuleInternalType("Sampler");
    public static final ModuleInternalType SPECTRA_VOICE=new ModuleInternalType("SpectraVoice");

    public static final ModuleInternalType AMPLIFIER=new ModuleInternalType("Amplifier");
    public static final ModuleInternalType COMPRESSOR=new ModuleInternalType("Compressor");
    public static final ModuleInternalType DC_BLOCKER=new ModuleInternalType("DC Blocker");
    public static final ModuleInternalType DELAY=new ModuleInternalType("Delay");
    public static final ModuleInternalType DISTORTION=new ModuleInternalType("Distortion");
    public static final ModuleInternalType ECHO=new ModuleInternalType("Echo");
    public static final ModuleInternalType EQUALIZER=new ModuleInternalType("EQ");
    public static final ModuleInternalType FILTER=new ModuleInternalType("Filter");
    public static final ModuleInternalType FILTER_PRO=new ModuleInternalType("Filter Pro");
    public static final ModuleInternalType FLANGER=new ModuleInternalType("Flanger");
    public static final ModuleInternalType LOW_FREQUENCY_OSCILLATOR=new ModuleInternalType("LFO");
    public static final ModuleInternalType LOOP=new ModuleInternalType("Loop");
    public static final ModuleInternalType MODULATOR=new ModuleInternalType("Modulator");
    public static final ModuleInternalType PITCH_SHIFTER=new ModuleInternalType("Pitch shifter");
    public static final ModuleInternalType REVERB=new ModuleInternalType("Reverb");
    public static final ModuleInternalType VOCAL_FILTER=new ModuleInternalType("Vocal filter");
    public static final ModuleInternalType VIBRATO=new ModuleInternalType("Vibrato");
    public static final ModuleInternalType WAVE_SHAPER=new ModuleInternalType("WaveShaper");

    public static final ModuleInternalType ENVELOPE=new ModuleInternalType("ADSR");
    public static final ModuleInternalType CONTROL_TO_NOTE=new ModuleInternalType("Ctl2Note");
    public static final ModuleInternalType FEEDBACK=new ModuleInternalType("Feedback");
    public static final ModuleInternalType GLIDE=new ModuleInternalType("Glide");
    public static final ModuleInternalType GPIO=new ModuleInternalType("GPIO");
    public static final ModuleInternalType META_MODULE=new ModuleInternalType("MetaModule");
    public static final ModuleInternalType MULTI_CONTROL=new ModuleInternalType("MultiCtl");
    public static final ModuleInternalType MULTI_SYNTH=new ModuleInternalType("MultiSynth");
    public static final ModuleInternalType PITCH_TO_CONTROL=new ModuleInternalType("Pitch2Ctl");
    public static final ModuleInternalType PITCH_DETECTOR=new ModuleInternalType("Pitch Detector");
    public static final ModuleInternalType SOUND_TO_CONTROL=new ModuleInternalType("Sound2Ctl");
    public static final ModuleInternalType VELOCITY_TO_CONTROL=new ModuleInternalType("Velocity2Ctl");

    private final String type;

    public ModuleInternalType(String type) {
        this.type = type;
        MAPPING.put(this);
    }

    @Override
    public String getValue() {
        return type;
    }
}
