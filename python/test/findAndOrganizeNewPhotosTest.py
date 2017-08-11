from kata.findAndOrganizeNewPhotos import FindAndOrganizeNewPhotos
from kata.findAndOrganizeNewPhotos import createEmptyTargetFolder
import unittest
import os


CURRENTPHOTOSDIRECTORY = "../../validate/testData/currentPhotos"

SOURCEDIRECTORY = "../../validate/testData/input"

OUTPUTDIRECTORY = "../target"


class FindAndOrganizeNewPhotosTest(unittest.TestCase):
    def testGetJpgFilesReturnsFiveFiles(self):
        jpgFiles = FindAndOrganizeNewPhotos().getJpgFiles(SOURCEDIRECTORY)
        self.assertEqual(5, len(jpgFiles))

    def testExtractDateFromFileName(self):
        self.assertEqual('201701', FindAndOrganizeNewPhotos().extractDateFromFileName("20170101_123456.jpg"))
        self.assertEqual('201701', FindAndOrganizeNewPhotos().extractDateFromFileName("IMG-20170101_WA0000.jpg"))
        self.assertEqual('', FindAndOrganizeNewPhotos().extractDateFromFileName("IMG-xx170101_WA0000.jpg"))
        self.assertEqual('', FindAndOrganizeNewPhotos().extractDateFromFileName("aap-20170101_WA0000.jpg"))
        self.assertEqual('', FindAndOrganizeNewPhotos().extractDateFromFileName("IM-20170101_WA0000.jpg"))
        self.assertEqual('', FindAndOrganizeNewPhotos().extractDateFromFileName("aap"))

    def testFilesAreCopiedToTargetDir(self):
        createEmptyTargetFolder(OUTPUTDIRECTORY)
        FindAndOrganizeNewPhotos().copyNewPhotosToTargetFolder(SOURCEDIRECTORY, OUTPUTDIRECTORY)
        targetFiles = FindAndOrganizeNewPhotos().getJpgFiles(OUTPUTDIRECTORY)
        self.assertEqual(5, len(targetFiles))
        self.assertTrue(os.path.exists(OUTPUTDIRECTORY + "/20170101_123456.jpg"))

    def testFilesInCurrentDirAreIgnoredIfCurrentDirIsSet(self):
        createEmptyTargetFolder(OUTPUTDIRECTORY)
        FindAndOrganizeNewPhotos().copyNewPhotosToTargetFolder(SOURCEDIRECTORY, OUTPUTDIRECTORY, currentPhotosDirectory=CURRENTPHOTOSDIRECTORY)
        targetFiles = FindAndOrganizeNewPhotos().getJpgFiles(OUTPUTDIRECTORY)
        self.assertEqual(4, len(targetFiles))
        self.assertFalse(os.path.exists(OUTPUTDIRECTORY + "/20170102_123456.jpg"))


if __name__ == '__main__':
    unittest.main()
