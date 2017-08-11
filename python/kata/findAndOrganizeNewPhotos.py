import os
import fnmatch
import shutil
import re
import sys
import datetime


#
# Sort a set of files into subdirectories named after the year and month as found in the filename.
# See ../validate/acceptanceTest.py
#

#
# TODO: copy only if file contains a valid date
# TODO: add unit tests

class FindAndOrganizeNewPhotos:
    def getJpgFiles(self, rootDir):
        jpgFiles = dict()
        for root, dirnames, filenames in os.walk(rootDir):
            for filename in fnmatch.filter(filenames, '*.[jJ][pP][gG]'):
                jpgFiles[filename] = os.path.join(root, filename)
        return jpgFiles

    datePattern = r'^(?:IMG-)?(\d\d\d\d\d\d\d\d)[_-].*'

    def extractDateFromFileName(self, fileName):
        date = re.findall(self.datePattern, fileName)
        if len(date) > 0 and len(date[0]) == 8:
            try:
                datetime.datetime.strptime(date[0], "%Y%m%d")
            except ValueError:
                return ""
            return date[0][:6]
        else:
            return ""


    def copyNewPhotosToTargetFolder(self, sourceDirectory, outputDirectory, currentPhotosDirectory=None):
        fromPhone = self.getJpgFiles(sourceDirectory)
        currentPhotos = self.getOptionalListOfExistingPhotos(currentPhotosDirectory)
        newPhotos = set(fromPhone.keys()) - set(currentPhotos.keys())

        for foto in newPhotos:
            source = fromPhone[foto]
            targetDir = outputDirectory + "/" + self.extractDateFromFileName(source)
            try:
                os.makedirs(targetDir)
            except OSError:
                pass
            shutil.copy(fromPhone[foto], targetDir)

    def getOptionalListOfExistingPhotos(self, currentPhotosDirectory):
        if currentPhotosDirectory is None:
            currentPhotos = dict()
        else:
            currentPhotos = self.getJpgFiles(currentPhotosDirectory)
        return currentPhotos


def createEmptyTargetFolder(targetDir):
    try:
        shutil.rmtree(targetDir)
    except OSError:
        pass
    os.mkdir(targetDir)


if __name__ == '__main__':
    if len(sys.argv) < 3:
        sys.exit(
            "Usage: python findAndOrganizeNewPhotos.py <directoryWithUnsortedPhotos> <directoryForSortedPhotos> [directoryForPhotosThatWereCopiedEarlier]")

    fromPhoneDir = sys.argv[1]
    targetDir = sys.argv[2]
    allPhotosDir = None
    if len(sys.argv) == 4:
        allPhotosDir = sys.argv[3]

    createEmptyTargetFolder(targetDir)

    FindAndOrganizeNewPhotos().copyNewPhotosToTargetFolder(fromPhoneDir, allPhotosDir, targetDir)

    print "Now run acceptanceTest.py to check if it worked"
