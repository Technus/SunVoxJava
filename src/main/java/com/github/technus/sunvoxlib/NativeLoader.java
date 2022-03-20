package com.github.technus.sunvoxlib;

import com.sun.jna.Native;
import com.sun.jna.Platform;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public final class NativeLoader {
    private static final String NATIVES_DIR = "natives";
    private static final String SUNVOX_FILE_NAME = NATIVES_DIR + File.separator + Platform.ARCH + File.separator + "sunvox";

    private NativeLoader() {
    }

    public static void loadSunVoxNatives() {
        String ext;
        final String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("windows")) {
            ext = ".dll";
        } else if (os.contains("linux")) {
            ext = ".so";
        } else if (os.contains("mac")) {
            ext = ".dylib";
        } else {
            throw new IllegalStateException("Unsupported OS");
        }
        unpackNativeLib(SUNVOX_FILE_NAME + ext);
        registerUnpackedLibrary(SunVoxLib.class, new File(SUNVOX_FILE_NAME));
    }

    private static void unpackAndLoadNativeLib(String libFileName) {
        loadUnpackedLibrary(unpackNativeLib(libFileName));
    }

    private static File unpackNativeLib(String libFileName) {
        try {
            final File unpackedLibFile = unpackedLibFile(libFileName);
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static InputStream packedLibInputStream(String libFileName) {
        return NativeLoader.class.getResourceAsStream("/" + libFileName);
    }

    private static File unpackedLibFile(String libFileName) {
        return new File(libFileName);
    }

    private static boolean unpackedLibExists(File unpackedLibrary) {
        return unpackedLibrary.isFile();
    }

    private static boolean unpackedLibraryHashCheck(InputStream packedLibInputStream, File unpackedLibFile) throws IOException {
        return DigestUtils.sha256Hex(packedLibInputStream).equals(
                DigestUtils.sha256Hex(Files.newInputStream(unpackedLibFile.toPath())));
    }

    private static void unpackLibrary(InputStream packedLibInputStream, File unpackedLibFile) throws IOException {
        FileUtils.copyInputStreamToFile(packedLibInputStream, unpackedLibFile);
    }

    private static void loadUnpackedLibrary(File unpackedLibrary) {
        System.load(unpackedLibrary.getAbsolutePath());
    }

    private static void registerUnpackedLibrary(Class<?> clazz, File unpackedLibrary) {
        Native.register(clazz, unpackedLibrary.getAbsolutePath());
    }
}
