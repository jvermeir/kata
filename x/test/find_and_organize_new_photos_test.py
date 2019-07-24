from kata import FindAndOrganizeNewPhotos
import unittest
import os


CURRENTPHOTOSDIRECTORY = "../../validate/testData/currentPhotos"

SOURCEDIRECTORY = "../../validate/testData/input"

OUTPUTDIRECTORY = "../target"


class FindAndOrganizeNewPhotosTest(unittest.TestCase):
    def testGetJpgFilesReturnsFiveFiles(self):
        jpg_files = FindAndOrganizeNewPhotos().get_jpg_files(SOURCEDIRECTORY)
        self.assertEqual(5, len(jpg_files))

    def testExtractDateFromFileName(self):
        self.assertEqual('201701', FindAndOrganizeNewPhotos().extract_date_from_file_name("20170101_123456.jpg"))
        self.assertEqual('201701', FindAndOrganizeNewPhotos().extract_date_from_file_name("IMG-20170101_WA0000.jpg"))
        self.assertEqual('', FindAndOrganizeNewPhotos().extract_date_from_file_name("IMG-xx170101_WA0000.jpg"))
        self.assertEqual('', FindAndOrganizeNewPhotos().extract_date_from_file_name("aap-20170101_WA0000.jpg"))
        self.assertEqual('', FindAndOrganizeNewPhotos().extract_date_from_file_name("IM-20170101_WA0000.jpg"))
        self.assertEqual('', FindAndOrganizeNewPhotos().extract_date_from_file_name("aap"))
        self.assertEqual('201702', FindAndOrganizeNewPhotos().extract_date_from_file_name('../../validate/testData/input/IMG-20170201-WA0000.jpg'))

    def testFilesAreCopiedToTargetDir(self):
        FindAndOrganizeNewPhotos().create_empty_target_folder(OUTPUTDIRECTORY)
        FindAndOrganizeNewPhotos().copy_new_photos_to_target_folder(SOURCEDIRECTORY, OUTPUTDIRECTORY)
        target_files = FindAndOrganizeNewPhotos().get_jpg_files(OUTPUTDIRECTORY)
        self.assertEqual(5, len(target_files))
        self.assertTrue(os.path.exists(OUTPUTDIRECTORY + "/201701/20170101_123456.jpg"))

    def testFilesInCurrentDirAreIgnoredIfCurrentDirIsSet(self):
        FindAndOrganizeNewPhotos().create_empty_target_folder(OUTPUTDIRECTORY)
        FindAndOrganizeNewPhotos().copy_new_photos_to_target_folder(SOURCEDIRECTORY, OUTPUTDIRECTORY, current_photos_directory=CURRENTPHOTOSDIRECTORY)
        target_files = FindAndOrganizeNewPhotos().get_jpg_files(OUTPUTDIRECTORY)
        self.assertEqual(4, len(target_files))
        self.assertFalse(os.path.exists(OUTPUTDIRECTORY + "/201701/20170102_123456.jpg"))


if __name__ == '__main__':
    unittest.main()
