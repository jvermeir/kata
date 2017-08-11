package kata;


import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class FindAndOrganizeNewPhotosTest {

    public static final String INPUT_DIRECTORY = "../validate/testData/input/";
    public static final String TARGET_DIRECTORY = "../validate/target/";
    public static final String CURRENT_PHOTOS_DIRECTORY = "../validate/testData/currentPhotos/";

    @Test
    public void testYearAndMonthAreDerivedFromFileName() {
        try {
            PhotoSorter photoSorter = new PhotoSorter("", "");
            Assert.assertEquals("201701", photoSorter.extractYearAndMonthFromFileName("resources/testData/input/20170101_123456.jpg"));
            Assert.assertEquals("201702", photoSorter.extractYearAndMonthFromFileName("20170201_123456.jpg"));
            Assert.assertEquals("201703", photoSorter.extractYearAndMonthFromFileName("resources/testData/input/IMG-20170301-WA0000.jpg"));
        } catch (IOException e) {
            Assert.fail("Unexpected exception " + e.getMessage());
        }
    }

    @Test
    public void testYearAndMonthAreEmptyIfFileNameDoesntContainADate() {
        try {
            PhotoSorter photoSorter = new PhotoSorter("", "");
            Assert.assertEquals("", photoSorter.extractYearAndMonthFromFileName(""));
            Assert.assertEquals("", photoSorter.extractYearAndMonthFromFileName("aap"));
        } catch (IOException e) {
            Assert.fail("Unexpected exception " + e.getMessage());
        }
    }

    @Test
    public void testFiveFilesAreReadFromTestInput() {
        try {
            Assert.assertEquals(5, new PhotoSorter(INPUT_DIRECTORY, "").findFiles().size());
        } catch (IOException e) {
            Assert.fail("Unexpected exception " + e.getMessage());
        }
    }

    @Test
    public void testOutputDirectoryIsEmptyWhenPhotoSorterIsCreated() {
        try {
            PhotoSorter photoSorter = new PhotoSorter("source", TARGET_DIRECTORY);
            File outputDirectory = new File(TARGET_DIRECTORY);
            Assert.assertTrue(outputDirectory.exists());
            Assert.assertEquals(0, FileUtils.listFiles(outputDirectory, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE).size());
        } catch (IOException e) {
            Assert.fail("Unexpected exception " + e.getMessage());
        }
    }

    @Test
    public void testOutputContainsCorrectFiles() {
        try {
            PhotoSorter photoSorter = new PhotoSorter(INPUT_DIRECTORY, TARGET_DIRECTORY);
            photoSorter.copyFilesToDirectories();
            // TODO: Replace ../validate with constant
            Assert.assertTrue(new File(TARGET_DIRECTORY + "201701/20170101_123456.jpg").exists());
            Assert.assertTrue(new File(TARGET_DIRECTORY + "201701/20170102_123456.jpg").exists());
            Assert.assertTrue(new File(TARGET_DIRECTORY + "201701/IMG-20170101-WA0000.jpg").exists());
            Assert.assertTrue(new File(TARGET_DIRECTORY + "201702/20170201_123456.jpg").exists());
            Assert.assertTrue(new File(TARGET_DIRECTORY + "201702/IMG-20170201-WA0000.jpg").exists());
        } catch (IOException e) {
            Assert.fail("Unexpected exception " + e.getMessage());
        }
    }

    @Test
    public void testPhotosInCurrentFolderAreNotCopied() {
        try {
            PhotoSorter photoSorter = new PhotoSorter(INPUT_DIRECTORY, TARGET_DIRECTORY, CURRENT_PHOTOS_DIRECTORY);
            photoSorter.copyFilesToDirectories();
            Assert.assertEquals(2, new File(TARGET_DIRECTORY + "/201701").listFiles().length);
        } catch (IOException e) {
            Assert.fail("Unexpected exception " + e.getMessage());
        }
    }
}
