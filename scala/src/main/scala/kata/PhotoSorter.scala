package kata

import java.io.File
import org.apache.commons.io.FileUtils
import java.time.format.DateTimeFormatter

import scala.util.Try

/**
  * Sort a set of files into subdirectories named after the year and month as found in the filename.
  * See ../validate/acceptanceTest.py
  */

object PhotoSorter extends App {
  if (args.length < 2) {
    println("Usage: scala kata.PhotoSorter <directoryWithUnsortedPhotos> <directoryForSortedPhotos> [directoryForPhotosThatWereCopiedEarlier]")
    System.exit(-1)
  }
  if (args.length >= 2) {
    val inputDir = args(0)
    val outputDir = args(1)
    val curentDIr =
      if (args.length == 3) args(2)
      else ""
    val photoSorter = new PhotoSorter
    photoSorter.copy(inputDir, outputDir, curentDIr)
  }
}

class PhotoSorter {

  def copyFile(file: File, outputDir: String) = {
    val folderNamedAfterTheYearAndMonth = getYearMonthFromFileName(file.getName)
    val directory = new File(outputDir + folderNamedAfterTheYearAndMonth)
    directory.mkdirs
    FileUtils.copyFile(file, new File(directory + "/" + file.getName))
  }

  def copy(inputDir: String, outputDir: String, currentDir: String = "") = {
    val dir = new File(outputDir)
    if (dir.exists) FileUtils.forceDelete(dir)
    findAllJpgFiles(inputDir).filter(!fileExists(_, currentDir)).map(
      file => copyFile(file, outputDir)
    )
  }

  def fileExists(file: File, currentDir: String): Boolean = new File(currentDir + "/" + file.getName).exists

  val formatter = DateTimeFormatter.ofPattern("uuuuMM")

  def findAllJpgFiles(inputDir: String): List[File] = {
    (new File(inputDir)).listFiles.filter(_.isFile).toList.filter { file =>
      file.getName.matches(".*[jJ][pP][gG]")
    }
  }

  def getYearMonthFromFileName(fileName: String) = {
    val baseName: String = cleanUpFileName(fileName)
    val yearMonth =
      if (fileNameStartsWithValidDate(baseName)) baseName.substring(0, 6)
      else ""
    yearMonth
  }

  private def fileNameStartsWithValidDate(baseName: String): Boolean = {
    if (baseName.length > 6) isValidDate(baseName)
    else false
  }

  private def isValidDate(baseName: String): Boolean = {
    return Try {
      val dt = baseName.substring(0, 6)
      formatter.parse(dt)
      true
    }.getOrElse(false)
  }

  private def cleanUpFileName(fileName: String) = {
    val nameParts = fileName.split('/')
    val baseName =
      if (nameParts(nameParts.length - 1).startsWith("IMG-")) nameParts(nameParts.length - 1).substring(4)
      else nameParts(0)
    baseName
  }

}
