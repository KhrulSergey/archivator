package com.khsa.archivator;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import com.khsa.archivator.utils.Archivator;
import com.khsa.archivator.utils.ArchivatorImpl;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static java.util.Objects.nonNull;

public class ArchivateHandler {

    @Argument(metaVar = "paths", required = false)
    private List<String> input;

    @Option(name = "-clevel")
    private Integer compressionLevel;

    public ArchivateHandler() {
    }

    public boolean start(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
            return false;
        }

        Archivator archivator = new ArchivatorImpl();
        try {
            if (nonNull(input)) {
                Set<File> inputFiles = loadFiles(new HashSet<>(input));
                OutputStream outputStream = System.out;
                return archivator.zip(inputFiles, outputStream, compressionLevel);
            } else {
                File workingDir = new File(System.getProperty("user.dir"));
                InputStream inputStream = new FileInputStream("C:\\Users\\Khsa\\IdeaProjects\\arvhiver\\archivator\\src\\test\\resources\\archiverTestDestination\\archiverTestFolder1.zip");
//                InputStream inputStream = System.in;
                return archivator.unzip(inputStream, workingDir);
            }
        }catch (IOException e){
            System.err.println("Unexpected IOException was handled. Try one more time.");
            return false;
        }
    }

    private TreeSet<File> loadFiles(Set<String> paths) {
        TreeSet<File> files = new TreeSet<>();
        for (String name : paths) {
            File f = new File(name);
            if (f.exists() && f.canRead()) {
                files.add(f);
            } else {
                System.err.println("path '" + name + "' can't be written and skipped!");
            }
        }
        return files;
    }
}
