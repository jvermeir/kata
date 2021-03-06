package kata;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.logging.Logger;

/**
 * Sort a set of files into subdirectories named after the year and month as found in the filename.
 * See ../validate/acceptanceTest.py
 */

public class PhotoSorter {
    private static final Logger LOGGER = Logger.getLogger(PhotoSorter.class.getName());

    private void createOutputDirectory(String outputDirectory) throws IOException {
        FileUtils.deleteDirectory(new File(outputDirectory));
        new File(outputDirectory).mkdirs();
    }

    protected String extractYearAndMonthFromFileName(String fileNameWithDateAndTimeStamp) {
        String[] fileNameParts = fileNameWithDateAndTimeStamp.split("/");
        String fileName = fileNameParts[fileNameParts.length - 1];
        if (fileName.startsWith("IMG-")) {
            fileName = fileName.substring(4);
        }
        String[] nameParts = fileName.split("_");
        String date = nameParts[0];
        String dateAsString = date.substring(0, Math.min(6, date.length()));
        if (isValidDate(dateAsString)) {
            return dateAsString;
        } else {
            return "";
        }
    }

    private boolean isValidDate(String dateAsString) {
        try {
            new SimpleDateFormat("yyyyMM").parse(dateAsString);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    protected Collection<File> findFiles(String sourceDirectory, String currentPhotosDirectory) {
        return FileUtils.listFiles(new File(sourceDirectory), new NewFileFilter(currentPhotosDirectory), TrueFileFilter.INSTANCE);
    }

    public void copyFilesToDirectories(String sourceDirectory, String outputDirectory) throws IOException {
        copyFilesToDirectories(sourceDirectory, outputDirectory,"");
    }

    public void copyFilesToDirectories(String sourceDirectory, String outputDirectory, String currentPhotosDirectory) throws IOException {
        createOutputDirectory(outputDirectory);
        Collection<File> files = findFiles(sourceDirectory, currentPhotosDirectory);
        for (File file : files) {
            String yearAndMonth = extractYearAndMonthFromFileName(file.getName());
            String outputDirForCurrentFile = outputDirectory + "/" + yearAndMonth + "/";
            new File(outputDirForCurrentFile).mkdirs();
            try {
                FileUtils.copyFile(file, new File(outputDirForCurrentFile + file.getName()));
            } catch (IOException e) {
                LOGGER.info("Error copying file " + file);
            }
        }
    }

    public static void main(String args[]) throws Exception {
        if (args.length < 2) {
            System.err.println("Usage java kata.PhotoSorter <directoryWithUnsortedPhotos> <directoryForSortedPhotos> [directoryForPhotosThatWereCopiedEarlier]");
            System.exit(-1);
        }
        String directoryWithUnsortedPhotos = args[0];
        String directoryForSortedPhotos = args[1];
        String directoryForPhotosThatWereCopiedEarlier = "";
        if (args.length > 2) {
            directoryForPhotosThatWereCopiedEarlier = args[2];
        }
        PhotoSorter photoSorter = new PhotoSorter();
        photoSorter.copyFilesToDirectories(directoryWithUnsortedPhotos, directoryForSortedPhotos, directoryForPhotosThatWereCopiedEarlier);
        System.out.println("Now run acceptanceTest.py to check if it worked");
    }
}

class NewFileFilter implements IOFileFilter {

    private final String currentFilesDirectory;

    public NewFileFilter(String currentFilesDirectory) {
        this.currentFilesDirectory = currentFilesDirectory;
    }

    @Override
    public boolean accept(File file) {
        String fullName = currentFilesDirectory + "/" + file.getName();
        return fullName.toUpperCase().endsWith("JPG") && !new File(fullName).exists();
    }

    @Override
    public boolean accept(File file, String s) {
        return false;
    }
}