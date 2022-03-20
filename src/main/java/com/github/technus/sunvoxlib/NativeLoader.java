package com.github.technus.sunvoxlib;

import com.sun.jna.Native;
import com.sun.jna.Platform;
import lombok.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NativeLoader {
    public static final String NATIVES_DIR = "natives";

    public static void loadSunVoxNatives() {
        val libFileName = NATIVES_DIR + File.separator + Platform.ARCH + File.separator + "sunvox";
        String ext;
        val os = System.getProperty("os.name").toLowerCase();
        if (os.contains("windows")) {
            ext = ".dll";
        } else if (os.contains("linux")) {
            ext = ".so";
        } else if (os.contains("mac")) {
            ext = ".dylib";
        } else {
            throw new IllegalStateException("Unsupported OS");
        }
        unpackAndLoadNativeLib(libFileName + ext);
        registerUnpackedLibrary(SunVoxLib.class, new File(libFileName));
    }

    private static void unpackAndLoadNativeLib(String libFileName) {
        loadUnpackedLibrary(unpackNativeLib(libFileName));
    }

    private static File unpackNativeLib(String libFileName) {
        var unpackedLibFile = unpackedLibFile(libFileName);
        if (unpackedLibExists(unpackedLibFile)) {
            if (unpackedLibraryHashCheck(packedLibInputStream(libFileName), unpackedLibFile)) {
                return unpackedLibFile;
            } else {
                if (!unpackedLibFile.delete())
                    throw new RuntimeException("Failed to delete: " + unpackedLibFile.getAbsolutePath());
            }
        }
        unpackLibrary(packedLibInputStream(libFileName), unpackedLibFile);
        if (!unpackedLibraryHashCheck(packedLibInputStream(libFileName), unpackedLibFile))
            throw new RuntimeException("Failed to unpack: " + unpackedLibFile.getAbsolutePath());
        return unpackedLibFile;
    }

    @SneakyThrows
    private static InputStream packedLibInputStream(String libFileName) {
        return NativeLoader.class.getResourceAsStream("/" + libFileName);
    }

    private static File unpackedLibFile(String libFileName) {
        return new File(libFileName);
    }

    private static boolean unpackedLibExists(File unpackedLibrary) {
        return unpackedLibrary.isFile();
    }

    @SneakyThrows
    private static boolean unpackedLibraryHashCheck(InputStream packedLibInputStream, File unpackedLibFile) {
        return DigestUtils.sha256Hex(packedLibInputStream).equals(
                DigestUtils.sha256Hex(Files.newInputStream(unpackedLibFile.toPath())));
    }

    @SneakyThrows
    private static void unpackLibrary(InputStream packedLibInputStream, File unpackedLibFile) {
        FileUtils.copyInputStreamToFile(packedLibInputStream, unpackedLibFile);
    }

    private static void loadUnpackedLibrary(File unpackedLibrary) {
        System.load(unpackedLibrary.getAbsolutePath());
    }

    private static void registerUnpackedLibrary(Class<?> clazz, File unpackedLibrary) {
        Native.register(clazz, unpackedLibrary.getAbsolutePath());
    }
}
