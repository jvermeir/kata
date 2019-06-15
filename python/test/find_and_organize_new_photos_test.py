from kata.find_and_organize_new_photos import find_and_organize_new_photos
from kata.find_and_organize_new_photos import create_empty_target_folder
import unittest
import os


CURRENTPHOTOSDIRECTORY = "../../validate/testData/currentPhotos"

SOURCEDIRECTORY = "../../validate/testData/input"

OUTPUTDIRECTORY = "../target"


class FindAndOrganizeNewPhotosTest(unittest.TestCase):
    def testGetJpgFilesReturnsFiveFiles(self):
        jpgFiles = find_and_organize_new_photos().get_jpg_files(SOURCEDIRECTORY)
        self.assertEqual(5, len(jpgFiles))

    def testExtractDateFromFileName(self):
        self.assertEqual('201701', find_and_organize_new_photos().extract_date_from_file_name("20170101_123456.jpg"))
        self.assertEqual('201701', find_and_organize_new_photos().extract_date_from_file_name("IMG-20170101_WA0000.jpg"))
        self.assertEqual('', find_and_organize_new_photos().extract_date_from_file_name("IMG-xx170101_WA0000.jpg"))
        self.assertEqual('', find_and_organize_new_photos().extract_date_from_file_name("aap-20170101_WA0000.jpg"))
        self.assertEqual('', find_and_organize_new_photos().extract_date_from_file_name("IM-20170101_WA0000.jpg"))
        self.assertEqual('', find_and_organize_new_photos().extract_date_from_file_name("aap"))
        self.assertEqual('201702', find_and_organize_new_photos().extract_date_from_file_name('../../validate/testData/input/IMG-20170201-WA0000.jpg'))

    def testFilesAreCopiedToTargetDir(self):
        create_empty_target_folder(OUTPUTDIRECTORY)
        find_and_organize_new_photos().copy_new_photos_to_target_folder(SOURCEDIRECTORY, OUTPUTDIRECTORY)
        targetFiles = find_and_organize_new_photos().get_jpg_files(OUTPUTDIRECTORY)
        self.assertEqual(5, len(targetFiles))
        self.assertTrue(os.path.exists(OUTPUTDIRECTORY + "/201701/20170101_123456.jpg"))

    def testFilesInCurrentDirAreIgnoredIfCurrentDirIsSet(self):
        create_empty_target_folder(OUTPUTDIRECTORY)
        find_and_organize_new_photos().copy_new_photos_to_target_folder(SOURCEDIRECTORY, OUTPUTDIRECTORY, current_photos_directory=CURRENTPHOTOSDIRECTORY)
        targetFiles = find_and_organize_new_photos().get_jpg_files(OUTPUTDIRECTORY)
        self.assertEqual(4, len(targetFiles))
        self.assertFalse(os.path.exists(OUTPUTDIRECTORY + "/201701/20170102_123456.jpg"))


if __name__ == '__main__':
    unittest.main()
