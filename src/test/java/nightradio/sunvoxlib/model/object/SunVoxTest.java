package nightradio.sunvoxlib.model.object;


import java.io.File;

import static nightradio.sunvoxlib.model.number.InitializationFlag.AUDIO_FLOAT32;
import static nightradio.sunvoxlib.model.object.SunVoxLibraryWrapper.getInstance;

class SunVoxTest {
    public static void main(String[] args) throws InterruptedException{
        try(SunVoxLibraryWrapper sunVox =getInstance()){
            sunVox.init(48_000, AUDIO_FLOAT32);
            try(Slot slot = new Slot(sunVox, 0)){
                slot.load(new File("C:\\Users\\danie\\Desktop\\sunvox\\b ass 1.sunvox"));
                slot.play();
                Thread.sleep(slot.getSongLengthFrames()*1000L/ sunVox.getSampleRate());
            }
        }
    }
}