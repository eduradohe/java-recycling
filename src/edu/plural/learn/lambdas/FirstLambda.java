package edu.plural.learn.lambdas;

import edu.plural.learn.util.StringUtils;

import java.io.File;
import java.io.FileFilter;
import java.util.Scanner;

public class FirstLambda {

    private final static String DIR_NAME = "C:/Program Files/Java/jre1.8.0_301/lib";
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

    private static String chooseFolder() {

        final Scanner s = new Scanner(System.in);

        System.out.println("(press enter for default: " + DIR_NAME + ")");
        System.out.println("Provide a folder: ");

        final String providedFolder = s.nextLine();

        return StringUtils.trimmedIsEmpty(providedFolder) ? DIR_NAME : providedFolder;
    }

    public static void main(String[] args) {

        final String chosenFolder =  chooseFolder();

        System.out.println("All JAR files in " + chosenFolder + ":");
        FileFilter filter = fetchFilterWithLambda();
        printFileNames(filter, chosenFolder);

        System.out.println("\nAll Properties files in " + chosenFolder + ":");
        filter = fetchFilterWithoutLambda();
        printFileNames(filter, chosenFolder);
    }
}