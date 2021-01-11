package com.khsa.archivator;


import com.khsa.archivator.utils.Archivator;
import com.khsa.archivator.utils.ArchivatorImpl;
import com.khsa.archivator.utils.IOUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;


public class ArchivatorTest {
    private static final String FOLDER_DESTINATION = "archTestDestination";
    private static final String FOLDER_SOURCE = "archTestSource";
    private static final String FOLDER1 = "archTestFolder1";
    private static final String FOLDER2 = "archTestFolder2";
    private static final String FOLDER3 = "archTestFolder3";
    private static final String FILE1 = FOLDER1 + File.separator + "archTestFile1.txt";
    private static final String FILE2 = FOLDER1 + File.separator + FOLDER2 + File.separator + "archTestFile2.txt";
    private static final String FILE3 = FOLDER1 + File.separator + FOLDER2 + File.separator + FOLDER3 + File.separator + "archTestFile3.txt";
    private static final String FILE1_CONTENT = "This is file 1 content";
    private static final String FILE2_CONTENT = "This is file 2 content";
    private static final String FILE3_CONTENT = "This is file 3 content";
    private static File source;
    private static File destination;
    private static File folder1;
    private static File folder2;
    private static File folder3;

    @BeforeAll
    private static void init() throws IOException {
        destination = new File(FOLDER_DESTINATION);
        destination.mkdir();

        source = new File(FOLDER_SOURCE);
        folder1 = new File(FOLDER_SOURCE + File.separator + FOLDER1);
        folder2 = new File(FOLDER_SOURCE + File.separator + FOLDER1 + File.separator + FOLDER2);
        folder3 = new File(FOLDER_SOURCE + File.separator + FOLDER1 + File.separator + FOLDER2
                + File.separator + FOLDER3);
        folder3.mkdirs();

        File file1 = new File(FOLDER_SOURCE + File.separator + FILE1);
        File file2 = new File(FOLDER_SOURCE + File.separator + FILE2);
        File file3 = new File(FOLDER_SOURCE + File.separator + FILE3);

        IOUtils.fill(file1, FILE1_CONTENT);
        IOUtils.fill(file2, FILE2_CONTENT);
        IOUtils.fill(file3, FILE3_CONTENT);
    }

    @Test
    public void zipAndUnzipTest() throws Exception {
        assertTrue(source.exists());
        assertTrue(destination.exists());

        Archivator archivator = new ArchivatorImpl();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        TreeSet<File> files = new TreeSet<>();
        files.add(source);
        archivator.zip(files, baos, null);
        //Check if zip package was created
        assertTrue(baos.size() > 0);

        ByteArrayInputStream bios = new ByteArrayInputStream(baos.toByteArray());
        archivator.unzip(bios, destination);

        //Check content from 'destination' folder with source folder
        assertTrue(destination.listFiles()[0].list().length == source.list().length);
        assertEquals(destination.list()[0], FOLDER_SOURCE);

        //Get and check file1 'archTestFile1' from  'archTestDestination/archTestSource/archTestFolder1/archTestFile1'
        File file1_dest = destination.listFiles()[0].listFiles()[0].listFiles()[0];
        assertFalse(file1_dest.isDirectory());
        assertEquals(file1_dest.getName(), new File(FILE1).getName());
        assertEquals(IOUtils.read(file1_dest), FILE1_CONTENT);
    }


    @AfterAll
    private static void cleanUp() {
        IOUtils.rm(source);
        IOUtils.rm(destination);
        assertTrue(!source.exists());
        assertTrue(!destination.exists());
    }
}
