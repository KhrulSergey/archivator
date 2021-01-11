package com.khsa.archivator.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

public interface Archivator {
    /**
     * Archive files to specified outputStream
     */
    public boolean zip(Set<File> inputFiles, OutputStream outputStream, Integer compressionLevel) throws IOException;

    /**
     * Unzip (dearchive) files from inputStream to specified Path
     */
    public boolean unzip(InputStream inputStream, File outputPath) throws IOException;
}
