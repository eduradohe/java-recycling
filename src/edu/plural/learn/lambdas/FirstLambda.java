package edu.plural.learn.lambdas;

import java.io.File;
import java.io.FileFilter;

public class FirstLambda {

    private final static String DIR_NAME = "C:/Program Files/Java/jre1.8.0_291/lib";
    private final static String EXTENSION_JAR = ".jar";
    private final static String EXTENSION_PROPERTIES = ".properties";

    private static FileFilter fetchFilterWithLambda() {
        return f -> f.getName().endsWith(EXTENSION_JAR);
    }

    private static FileFilter fetchFilterWithoutLambda() {
        return new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().endsWith(EXTENSION_PROPERTIES);
            }
        };
    }

    private static void printFileNames( final FileFilter filter, final String dirName ) {

        final File dir = new File(dirName);
        final File[] files = dir.listFiles(filter);

        if ( files != null && files.length > 0 ) {
            for ( int i = 0; i < files.length; i++ ) {
                System.out.println(files[i]);
            }
        }
    }

    public static void main(String[] args) {
        FileFilter filter = fetchFilterWithLambda();
        printFileNames(filter, DIR_NAME);

        filter = fetchFilterWithoutLambda();
        printFileNames(filter, DIR_NAME);
    }
}