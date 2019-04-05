package kata

import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*

/**
 * Sort a set of files into subdirectories named after the year and month as found in the filename.
 * See ../validate/acceptanceTest.py
 */
class PhotoSorter (val directoryWithUnsortedPhotos:String, val directoryForSortedPhotos:String, val directoryForPhotosThatWereCopiedEarlier:String? = null) {

    fun copyFilesToDirectories() {
        val photos = findJpgFiles(directoryWithUnsortedPhotos)
        val currentPhotos = getCurrentPhotos()
        val newPhotos = photos.filter { fileName -> !currentPhotos.contains(fileName.split("/").last()) }
        newPhotos.forEach{photo -> copyFile(photo)}
    }

    fun copyFile(file: String) {
        val yearMonth = extractYearAndMonthFromFileName(file)
        val targetDir = Path.of(directoryForSortedPhotos + "/" + yearMonth)
        targetDir.toFile().mkdirs()

        val targetPath = Path.of(targetDir.toFile().absolutePath, Path.of(file).toFile().name)
        Files.copy(Path.of(file), targetPath)
    }

    fun getCurrentPhotos(): List<String> {
        if (directoryForPhotosThatWereCopiedEarlier == null) {
            return listOf<String>()
        } else {
            return findJpgFiles(directoryForPhotosThatWereCopiedEarlier)
                    .map { fileName -> fileName.split("/").last() }
        }
    }

    fun extractYearAndMonthFromFileName(fileName: String): String {
        val parts = fileName.split("/")
        var namePart = parts.last()
        if (namePart.startsWith("IMG-")) namePart = namePart.removePrefix("IMG-")
        if (namePart.length>6) {
            namePart = namePart.substring(0,6) + "01"

            val formatter = DateTimeFormatter.ofPattern("yyyyMMdd", Locale.ENGLISH)
            try {
                LocalDate.parse(namePart, formatter)
                return namePart.substring(0, 6)
            } catch (e: DateTimeParseException) {
                return ""
            }
        }

        return ""
    }

    fun findJpgFiles(folderName: String): List<String> {
        return File(folderName).walk()
                .filter{file -> file.name.toLowerCase().endsWith(".jpg") }
                .map { file -> file.toString()}
                .toList()
    }
}

fun main(args: Array<String>) {
    if (args.size < 2) {
        System.err.println("Usage: java kata.PhotoSorter <directoryWithUnsortedPhotos> <directoryForSortedPhotos> [directoryForPhotosThatWereCopiedEarlier]")
        System.exit(-1)
    }
    val directoryWithUnsortedPhotos = args[0]
    val directoryForSortedPhotos = args[1]
    val directoryForPhotosThatWereCopiedEarlier = if (args.size > 2) args[2] else null

    val photoSorter = PhotoSorter(directoryWithUnsortedPhotos, directoryForSortedPhotos, directoryForPhotosThatWereCopiedEarlier)
    photoSorter.copyFilesToDirectories()
    println("Now run acceptanceTest.py to check if it worked")
}

