package kata

import org.scalatest._

class PhotoSorterSpec extends FlatSpec with Matchers {
  val inputDir = "../validate/testData/input/"
  val outputDir = "../validate/target/"
  val currentDir = "../validate/testData/currentPhotos/"

  "The year-month string " should "be parsed correctly from file names" in {
    PhotoSorter.getYearMonthFromFileName("20170101_123456.jpg") shouldEqual Some("201701")
    PhotoSorter.getYearMonthFromFileName("IMG-20170101-123456.jpg") shouldEqual Some("201701")
    PhotoSorter.getYearMonthFromFileName("x/y/IMG-20170101-123456.jpg") shouldEqual Some("201701")
    PhotoSorter.getYearMonthFromFileName("IM-20170101-123456.jpg") shouldEqual None
    PhotoSorter.getYearMonthFromFileName("aap") shouldEqual None
  }

  "The number of files found in the test input folder" should "equal 5" in {
    PhotoSorter.findAllJpgFiles(inputDir).size shouldEqual 5
  }

  "3 files " should "be copied to 201701 if the current folder is not specified" in {
    PhotoSorter.copy(inputDir, outputDir)
    PhotoSorter.findAllJpgFiles(outputDir + "201701").size shouldEqual 3
  }

  "2 files " should "be copied to 201701 if the current folder is specified" in {
    PhotoSorter.copy(inputDir, outputDir, currentDir)
    PhotoSorter.findAllJpgFiles(outputDir + "201701").size shouldEqual 2
  }

  "2 files " should "be copied to 201702" in {
    PhotoSorter.copy(inputDir, outputDir)
    PhotoSorter.findAllJpgFiles(outputDir + "201702").size shouldEqual 2
  }
}
