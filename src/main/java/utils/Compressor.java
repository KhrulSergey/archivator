package utils;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

public interface Compressor {

    public void zip(Set<File> inputFile, OutputStream outputStream);

    public void unzip(InputStream inputStream, File outputFile);
}
