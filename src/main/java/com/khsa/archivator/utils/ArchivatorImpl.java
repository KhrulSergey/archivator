package com.khsa.archivator.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class ArchivatorImpl implements Archivator {

    private static final int MAX_BUFFER_SIZE = 1024;
    private ZipOutputStream zipOutputStream;

    /**
     * Archive files to specified outputStream
     */
    @Override
    public boolean zip(Set<File> inputFiles, OutputStream outputStream, Integer compressionLevel) throws IOException {
        try {
            zipOutputStream = new ZipOutputStream(outputStream);
            if(nonNull(compressionLevel) && compressionLevel > 0 && compressionLevel < 9) {
                zipOutputStream.setLevel(compressionLevel);
            }
            for(File file : inputFiles) {
                addFileToZip(file, file.getName());
            }
            return true;
        } finally {
            zipOutputStream.close();
        }
    }

    /**
     * Unzip (dearchive) files from inputStream to specified Path
     */
    @Override
    public boolean unzip(InputStream inputStream, File outputPath) throws IOException {
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        if(isNull(zipEntry)){
            System.err.println("file is not an archive!");
            return false;
        }
        byte[] bytes = new byte[MAX_BUFFER_SIZE];
        while (zipEntry != null) {
            String currentPath = outputPath + File.separator + zipEntry.getName();
            //IsFolder?
            if(zipEntry.isDirectory() || zipEntry.getName().endsWith(File.separator)) {
                Files.createDirectories(Paths.get(currentPath));
            } else {
                File destination = new File(currentPath);
                FileOutputStream fileOutputStream = new FileOutputStream(destination);
                int length;
                while ((length = zipInputStream.read(bytes)) > 0) {
                    fileOutputStream.write(bytes, 0, length);
                }
                fileOutputStream.close();
            }
            zipEntry = zipInputStream.getNextEntry();
        }
        zipInputStream.closeEntry();
        zipInputStream.close();
        return true;
    }


    private void addFileToZip(File file, String path) throws IOException {
        if(file.isDirectory()) {
            path = path + File.separator;
            zipOutputStream.putNextEntry(new ZipEntry(path));
            File[] files = file.listFiles();
            for (File f : files) {
                addFileToZip(f, path + f.getName());
            }
            zipOutputStream.closeEntry();
        } else {
            try(FileInputStream fis = new FileInputStream(file)) {
                zipOutputStream.putNextEntry(new ZipEntry(path));
                byte[] bytes = new byte[MAX_BUFFER_SIZE];
                int length;
                while((length = fis.read(bytes)) >= 0) {
                    zipOutputStream.write(bytes, 0, length);
                }
                zipOutputStream.closeEntry();
            }
        }
    }
}
