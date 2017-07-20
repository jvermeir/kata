import os
import fnmatch
import shutil
import re

#
# TODO: this is pretty bad, needs work in naming things, extracting constants, split the copyPhotos method, maybe? 
#

class FindAndOrganizeNewPhotos():
    def getJpgFiles(self, rootDir):
        newFiles = dict()
        for root, dirnames, filenames in os.walk(rootDir):
            for filename in fnmatch.filter(filenames, '*.[jJ][pP][gG]'):
                newFiles[filename] = os.path.join(root, filename)
        return newFiles

    def findDate(self, fileName):
        # 20160319_195252.jpg
        # IMG-20170525-WA0000.jpg
        pattern = r'.*(201\d\d\d\d\d)[_-].*'
        date = re.findall(pattern, fileName)
        if len(date) > 0 and len(date[0]) == 8:
            return date[0][:6]
        else:
            return ""

    def copyPhotos(self):
        fromPhoneDir = "acceptanceTest/testData/input"
        fromPhone = self.getJpgFiles(fromPhoneDir)

        allPhotosDir = "acceptanceTest/testData/currentPhotos"
        allPhotos = self.getJpgFiles(allPhotosDir)

        newPhotos = set(fromPhone.keys()) - set(allPhotos.keys())

        photosByDateDir = "target"
        for foto in newPhotos:
            source = fromPhone[foto]
            targetDir = photosByDateDir + "/" + self.findDate(source)
            if not os.path.exists(targetDir):
                os.makedirs(targetDir)
            shutil.copy(fromPhone[foto], targetDir)


shutil.rmtree("target")
os.mkdir("target")
finder = FindAndOrganizeNewPhotos()
finder.copyPhotos()
