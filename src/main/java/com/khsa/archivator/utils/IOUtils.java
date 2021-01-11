package com.khsa.archivator.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class IOUtils {

    public static void fill(File file, String contents) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            byte[] bytes = contents.getBytes();
            fos.write(contents.getBytes(), 0, bytes.length);
        }
    }

    public static void rm(File file) {
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                rm(f);
            }
        }
        file.delete();
    }

    public static String read(File file) throws IOException {
        byte[] bytes = new byte[1024];
        int length;
        try (FileInputStream fis = new FileInputStream(file)) {
            length = fis.read(bytes);
        }
        return new String(Arrays.copyOf(bytes, length));
    }
}
