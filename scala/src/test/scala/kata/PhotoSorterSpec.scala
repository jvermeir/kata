package kata

import org.scalatest._

class PhotoSorterSpec extends FlatSpec with Matchers {
  val inputDir = "../validate/testData/input/"
  val outputDir = "../validate/target/"
  val currentDir = "../validate/testData/currentPhotos/"

  "The year-month string " should "be parsed correctly from file names" in {
    val photoSorter = new PhotoSorter
    photoSorter.getYearMonthFromFileName("20170101_123456.jpg") shouldEqual "201701"
    photoSorter.getYearMonthFromFileName("IMG-20170101-123456.jpg") shouldEqual "201701"
    photoSorter.getYearMonthFromFileName("x/y/IMG-20170101-123456.jpg") shouldEqual "201701"
    photoSorter.getYearMonthFromFileName("IM-20170101-123456.jpg") shouldEqual ""
    photoSorter.getYearMonthFromFileName("aap") shouldEqual ""
  }

  "The number of files found in the test input folder" should "equal 5" in {
    (new PhotoSorter).findAllJpgFiles(inputDir).size shouldEqual 5
  }

  "3 files " should "be copied to 201701 if the current folder is not specified" in {
    val photoSorter = new PhotoSorter
    photoSorter.copy(inputDir, outputDir)
    photoSorter.findAllJpgFiles(outputDir + "201701").size shouldEqual 3
  }

 "2 files " should "be copied to 201701 if the current folder is specified" in {
    val photoSorter = new PhotoSorter
    photoSorter.copy(inputDir, outputDir, currentDir)
    photoSorter.findAllJpgFiles(outputDir + "201701").size shouldEqual 2
  }

  "2 files " should "be copied to 201702" in {
    val photoSorter = new PhotoSorter
    photoSorter.copy(inputDir, outputDir)
    photoSorter.findAllJpgFiles(outputDir + "201702").size shouldEqual 2
  }
}
