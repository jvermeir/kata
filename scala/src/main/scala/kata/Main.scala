package kata

import java.io.File
import java.nio.file.{Files, Path}
import java.time.format.DateTimeFormatter

import scala.util.Try
import org.apache.commons.io.FileUtils

/**
  * Sort a set of files into subdirectories named after the year and month as found in the filename.
  * See ../validate/acceptanceTest.py
  */
object Main extends App {
  args.toList match {
    case source :: destination :: Nil => PhotoSorter.copy(source, destination)
    case source :: destination :: oldDestination :: Nil => PhotoSorter.copy(source, destination, oldDestination)
    case _ =>
      println("Usage: scala kata.PhotoSorter <directoryWithUnsortedPhotos> <directoryForSortedPhotos> [directoryForPhotosThatWereCopiedEarlier]")
      System.exit(-1)
  }
  println("Now run acceptanceTest.py to check if it worked")
}

object PhotoSorter {
  def copyFile(file: File, outputDir: String): Unit = {
    val directory = new File(s"$outputDir/${getYearMonthFromFileName(file.getName).getOrElse("")}")

    directory.mkdirs
    Files.copy(file.toPath, Path.of(directory.getAbsolutePath, file.getName))
  }

  def copy(inputDir: String, outputDir: String, currentDir: String = ""): Unit = {
    val dir = new File(outputDir)

    FileUtils.deleteDirectory(dir)
    findAllJpgFiles(inputDir)
      .filterNot(fileExists(_, currentDir))
      .foreach(copyFile(_, outputDir))
  }

  def fileExists(file: File, currentDir: String): Boolean = new File(s"$currentDir/${file.getName}").exists

  def findAllJpgFiles(inputDir: String): List[File] =
    new File(inputDir).listFiles.toList
      .filter(file => file.isFile && file.getName.matches(".*[jJ][pP][gG]"))

  def getYearMonthFromFileName(fileName: String): Option[String] = {
    val extractDate = "(IMG-)?([0-9]{6}).*".r
    val formatter = DateTimeFormatter.ofPattern("uuuuMM")
    def isValidDate(date: String) = Try(formatter.parse(date)).isSuccess

    fileName.split('/').last match {
      case extractDate(_, date) if isValidDate(date) => Some(date)
      case _ => None
    }
  }
}
