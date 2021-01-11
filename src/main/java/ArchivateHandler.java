import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import utils.Compressor;
import utils.CompressorImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

import static java.util.Objects.nonNull;

public class ArchivateHandler {

    @Argument(metaVar = "paths")
    private Set<String> input;

    @Option(name = "-z")
    private boolean zip;

    @Option(name = "-u")
    private boolean unzip;

    @Option(name = "-с")
    private boolean compress;

    @Option(name = "-out")
    private String output;

    public ArchivateHandler(){}

    public boolean start(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
            return false;
        }

        Compressor compressor = new CompressorImpl();

        if (zip && !unzip) {
            if (input == null || input.equals("")) {
                System.err.println("enter a file!");
                return false;
            }
            Set<File> inputFiles = loadFiles(input);

            OutputStream outputStream = System.out;
            //Check output-File parameter
            if (nonNull(output) && !output.equals("")) {
                try {
                    outputStream = new FileOutputStream(output);
                }catch (FileNotFoundException e){
                    System.err.println("path or file is wrong for execute!");
                }
            }

            compressor.zip(inputFiles, outputStream);
        } else if (!zip && unzip) {
            compressor.unzip(inputFile, outputStream);
        } else {
            System.err.println("enter -z for zip or -u for unzip");
        }
        return true;
    }

    private TreeSet<File> loadFiles(Set<String> paths) {
        TreeSet<File> files = new TreeSet<>();
        for(String name : paths) {
            File f = new File(name);
            if(f.exists() && f.canRead()) {
                files.add(f);
            }
            else{
                System.err.println("path '" + name + "' can't be written and skipped!");
            }
        }
        return files;
    }
}
