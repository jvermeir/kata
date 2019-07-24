from kata.photo_finder import *
import unittest
import os
from pathlib import Path, PurePath


VALIDATE_DIRECTORY = Path(__file__).resolve().parent.parent.parent / "validate"

CURRENT_PHOTOS_DIRECTORY = VALIDATE_DIRECTORY / "testData" / "currentPhotos"

SOURCE_DIRECTORY = VALIDATE_DIRECTORY / "testData" / "input"

OUTPUT_DIRECTORY = VALIDATE_DIRECTORY / "target"

photo_service = FindAndOrganizeNewPhotos()

class FindAndOrganizeNewPhotosTest(unittest.TestCase):
    def testGetJpgFilesReturnsFiveFiles(self):
        jpg_files = photo_service.get_jpg_files(SOURCE_DIRECTORY)
        self.assertEqual(5, len(jpg_files))

    def testExtractDateFromFileName(self):
        self.assertEqual('201701', photo_service.extract_date_from_file_name(PurePath("20170101_123456.jpg")))
        self.assertEqual('201701', photo_service.extract_date_from_file_name(PurePath("IMG-20170101_WA0000.jpg")))
        self.assertEqual('', photo_service.extract_date_from_file_name(PurePath("IMG-xx170101_WA0000.jpg")))
        self.assertEqual('', photo_service.extract_date_from_file_name(PurePath("aap-20170101_WA0000.jpg")))
        self.assertEqual('', photo_service.extract_date_from_file_name(PurePath("IM-20170101_WA0000.jpg")))
        self.assertEqual('', photo_service.extract_date_from_file_name(PurePath("aap")))
        self.assertEqual('201702', photo_service.extract_date_from_file_name(VALIDATE_DIRECTORY / 'testData' / ' input' / 'IMG-20170201-WA0000.jpg'))

    def testFilesAreCopiedToTargetDir(self):
        photo_service.create_empty_target_folder(OUTPUT_DIRECTORY)
        photo_service.copy_new_photos_to_target_folder(SOURCE_DIRECTORY, OUTPUT_DIRECTORY)
        target_files = photo_service.get_jpg_files(OUTPUT_DIRECTORY)
        self.assertEqual(5, len(target_files))
        self.assertTrue(os.path.exists(OUTPUT_DIRECTORY / "201701/20170101_123456.jpg"))

    def testFilesInCurrentDirAreIgnoredIfCurrentDirIsSet(self):
        photo_service.create_empty_target_folder(OUTPUT_DIRECTORY)
        photo_service.copy_new_photos_to_target_folder(SOURCE_DIRECTORY, OUTPUT_DIRECTORY, current_photos_directory=CURRENT_PHOTOS_DIRECTORY)
        target_files = photo_service.get_jpg_files(OUTPUT_DIRECTORY)
        self.assertEqual(4, len(target_files))
        self.assertFalse(os.path.exists(OUTPUT_DIRECTORY / "201701" / "20170102_123456.jpg"))


if __name__ == '__main__':
    unittest.main()
