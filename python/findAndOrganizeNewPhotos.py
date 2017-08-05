import os
import fnmatch
import shutil
import re


#
# Sort a set of files into subdirectories named after the year and month as found in the files.
# See ../acceptanceTest/acceptanceTest.py
#


class FindAndOrganizeNewPhotos():
    def getJpgFiles(self, rootDir):
        jpgFiles = dict()
        for root, dirnames, filenames in os.walk(rootDir):
            for filename in fnmatch.filter(filenames, '*.[jJ][pP][gG]'):
                jpgFiles[filename] = os.path.join(root, filename)
        return jpgFiles

    def extractDateFromFileName(self, fileName):
        datePattern = r'.*(201\d\d\d\d\d)[_-].*'
        date = re.findall(datePattern, fileName)
        if len(date) > 0 and len(date[0]) == 8:
            return date[0][:6]
        else:
            return ""

    def copyNewPhotosToTargetFolder(self, fromPhoneDir, allPhotosDir, targetRootDir):
        fromPhone = self.getJpgFiles(fromPhoneDir)
        allPhotos = self.getJpgFiles(allPhotosDir)
        newPhotos = set(fromPhone.keys()) - set(allPhotos.keys())

        for foto in newPhotos:
            source = fromPhone[foto]
            targetDir = targetRootDir + "/" + self.extractDateFromFileName(source)
            try:
                os.makedirs(targetDir)
            except OSError:
                pass
            shutil.copy(fromPhone[foto], targetDir)


def createEmptyTargetFolder(targetDir):
    try:
        shutil.rmtree(targetDir)
    except OSError:
        pass
    os.mkdir(targetDir)


if __name__ == '__main__':
    fromPhoneDir = "../validate/testData/input"
    allPhotosDir = "../validate/testData/currentPhotos"
    targetDir = "../validate/target"
    createEmptyTargetFolder(targetDir)

    FindAndOrganizeNewPhotos().copyNewPhotosToTargetFolder(fromPhoneDir, allPhotosDir, targetDir)

    print "Now run acceptanceTest.py to check if it works"
