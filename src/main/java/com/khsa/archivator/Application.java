package com.khsa.archivator;

public class Application {

    public static void main(String[] args) {
        ArchivateHandler archivateHandler = new ArchivateHandler();
        System.out.println("Starting archivator app...");
        boolean result = archivateHandler.start(args);
        System.out.println("Process ended " + (result ? "successful" : "with errors") + "!");
    }

}
