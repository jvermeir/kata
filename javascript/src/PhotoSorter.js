function PhotoSorter() {
}

var fs = require('fs-extra');
var mkdirp = require('mkdirp');

PhotoSorter.prototype.getBaseName = function (fileName) {
    var parts = fileName.split('/');
    var baseName = parts[parts.length - 1];
    if (baseName.startsWith('IMG-')) {
        baseName = baseName.substring(4);
    }
    return baseName;
};

PhotoSorter.prototype.parseDateFromFileName = function (fileName) {
    var baseName = this.getBaseName(fileName);
    if (baseName.length > 7) {
        var dateString = baseName.split(/[_-]/)[0];
        var theDate = new Date(dateString.substring(0, 4), dateString.substring(4, 2), dateString.substring(6, 2));
        if (isNaN(theDate)) {
            return "";
        } else {
            return dateString.substring(0, 6);
        }
    }
    return "";
};

PhotoSorter.prototype.getListOfJpgFiles = function (inputDir) {
    if (inputDir === undefined) {
        return [];
    } else {
        return fs.readdirSync(inputDir).filter(function (f) {
            var jpgPattern = /.*[jJpPgG]$/;
            var jpgFiles = jpgPattern.exec(f);
            return (jpgFiles!==null && jpgFiles.length > 0);
        });
    }
};

PhotoSorter.prototype.copyFiles = function (inputDir, outputDir, currentDir) {
    fs.removeSync(outputDir);
    currentFiles = this.getListOfJpgFiles(currentDir);
    jpgFiles = this.getListOfJpgFiles(inputDir).filter(function (f) {
        return !currentFiles.includes(f);
    });
    for (i in jpgFiles) {
        var file = jpgFiles[i];
        var folderName = this.parseDateFromFileName(file);
        var outputFolder = outputDir + "/" + folderName;
        fs.ensureDirSync(outputFolder);
        var fullInputFileName = inputDir + "/" + file;
        var fullOutputFileName = outputFolder + "/" + file;
        fs.createReadStream(fullInputFileName).pipe(fs.createWriteStream(fullOutputFileName));
    }
};

if (process.argv.length<4) {
    console.log("Usage: node src/PhotoSorter.js <directoryWithUnsortedPhotos> <directoryForSortedPhotos> [directoryForPhotosThatWereCopiedEarlier]");
    process.exit(-1)
}
var directoryWithUnsortedPhotos = process.argv[2];
var directoryForSortedPhotos = process.argv[3];
if (process.argv.length>4) {
    var directoryForPhotosThatWereCopiedEarlier = process.argv[4]
} else {
    var directoryForPhotosThatWereCopiedEarlier = undefined;
}
var photoSorter = new PhotoSorter();
photoSorter.copyFiles(directoryWithUnsortedPhotos, directoryForSortedPhotos, directoryForPhotosThatWereCopiedEarlier);

module.exports = PhotoSorter;
