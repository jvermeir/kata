package kata

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import java.io.File

class TestPhotoSorter : StringSpec({
    val inputDirectory = "../validate/testData/input/"
    val targetDirectory = "../validate/target/"
    val currentPhotosDirectory = "../validate/testData/currentPhotos/"

    "YearMonth should be derived correctly from file name" {
        val photoSorter = PhotoSorter(inputDirectory, targetDirectory, currentPhotosDirectory)
        photoSorter.extractYearAndMonthFromFileName("resources/testData/input/20170101_123456.jpg") shouldBe ("201701")
        photoSorter.extractYearAndMonthFromFileName("20170201_123456.jpg") shouldBe ("201702")
        photoSorter.extractYearAndMonthFromFileName("resources/testData/input/IMG-20170301-WA0000.jpg") shouldBe ("201703")
        photoSorter.extractYearAndMonthFromFileName("IM-20170101_123456") shouldBe ("")
        photoSorter.extractYearAndMonthFromFileName("aap") shouldBe ("")
        photoSorter.extractYearAndMonthFromFileName(" ") shouldBe ("")
    }

    "Five files are read from test input folder" {
        val photoSorter = PhotoSorter(inputDirectory, targetDirectory, currentPhotosDirectory)
        photoSorter.findJpgFiles("../validate/testData/input").size shouldBe 5
    }

    "Output folder contains correct files if current files are ignored" {
        File(targetDirectory).deleteRecursively()
        val photoSorter = PhotoSorter(inputDirectory, targetDirectory)
        photoSorter.copyFilesToDirectories()
        val outputFiles = photoSorter.findJpgFiles(targetDirectory)
        val expectedOutput= setOf( targetDirectory + "201701/20170101_123456.jpg", targetDirectory + "201701/20170102_123456.jpg",
                targetDirectory + "201701/IMG-20170101-WA0000.jpg", targetDirectory + "201702/20170201_123456.jpg",
                targetDirectory + "201702/IMG-20170201-WA0000.jpg" )
        expectedOutput.minus(outputFiles) shouldBe emptySet()
        expectedOutput.size shouldBe outputFiles.size
    }

    "Output folder contains correct files if current files are included" {
        File(targetDirectory).deleteRecursively()
        val photoSorter = PhotoSorter(inputDirectory, targetDirectory, currentPhotosDirectory)
        photoSorter.copyFilesToDirectories()
        val outputFiles = photoSorter.findJpgFiles(targetDirectory)
        val expectedOutput= setOf( targetDirectory + "201701/20170101_123456.jpg",
                targetDirectory + "201701/IMG-20170101-WA0000.jpg", targetDirectory + "201702/20170201_123456.jpg",
                targetDirectory + "201702/IMG-20170201-WA0000.jpg" )
        expectedOutput.minus(outputFiles) shouldBe emptySet()
        expectedOutput.size shouldBe outputFiles.size
    }
})
