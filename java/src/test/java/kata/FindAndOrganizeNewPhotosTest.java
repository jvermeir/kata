package kata;


import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.internal.runners.statements.Fail;

import java.io.File;
import java.io.IOException;

public class FindAndOrganizeNewPhotosTest {

    public static final String INPUT_DIRECTORY = "../validate/testData/input/";
    public static final String TARGET_DIRECTORY = "../validate/target/";
    public static final String CURRENT_PHOTOS_DIRECTORY = "../validate/testData/currentPhotos/";

    @Test
    public void testYearAndMonthAreDerivedFromFileName() {
        PhotoSorter photoSorter = new PhotoSorter();
        Assert.assertEquals("201701", photoSorter.extractYearAndMonthFromFileName("resources/testData/input/20170101_123456.jpg"));
        Assert.assertEquals("201702", photoSorter.extractYearAndMonthFromFileName("20170201_123456.jpg"));
        Assert.assertEquals("201703", photoSorter.extractYearAndMonthFromFileName("resources/testData/input/IMG-20170301-WA0000.jpg"));
    }

    @Test
    public void testYearAndMonthAreEmptyIfFileNameDoesntContainADate() {
        PhotoSorter photoSorter = new PhotoSorter();
        Assert.assertEquals("", photoSorter.extractYearAndMonthFromFileName(""));
        Assert.assertEquals("", photoSorter.extractYearAndMonthFromFileName("aap"));
    }

    @Test
    public void testFiveFilesAreReadFromTestInput() {
        Assert.assertEquals(5, new PhotoSorter().findFiles(INPUT_DIRECTORY, "").size());
    }

    @Test
    public void testOutputContainsCorrectFiles() {
        PhotoSorter photoSorter = new PhotoSorter();
        try {
            photoSorter.copyFilesToDirectories(INPUT_DIRECTORY, TARGET_DIRECTORY);
        } catch (IOException e) {
            Assert.fail("Failed to create " + TARGET_DIRECTORY);
        }
        Assert.assertTrue(new File(TARGET_DIRECTORY + "201701/20170101_123456.jpg").exists());
        Assert.assertTrue(new File(TARGET_DIRECTORY + "201701/20170102_123456.jpg").exists());
        Assert.assertTrue(new File(TARGET_DIRECTORY + "201701/IMG-20170101-WA0000.jpg").exists());
        Assert.assertTrue(new File(TARGET_DIRECTORY + "201702/20170201_123456.jpg").exists());
        Assert.assertTrue(new File(TARGET_DIRECTORY + "201702/IMG-20170201-WA0000.jpg").exists());
    }

    @Test
    public void testPhotosInCurrentFolderAreNotCopied() {
        PhotoSorter photoSorter = new PhotoSorter();
        try {
            photoSorter.copyFilesToDirectories(INPUT_DIRECTORY, TARGET_DIRECTORY, CURRENT_PHOTOS_DIRECTORY);
        } catch (IOException e) {
            Assert.fail("Failed to create " + TARGET_DIRECTORY);
        }
        Assert.assertEquals(2, new File(TARGET_DIRECTORY + "/201701").listFiles().length);
    }
}
