import unittest
import os

#
# Run this test after running findAndOrganizeNewPhotos.py to check if the correct folder structure was created.
#

TARGET_DIR = "target"


class OutputContains4JpgFilesIn2Folders(unittest.TestCase):
    def testOutputContains4JpgFilesIn2Folders(self):
        expectedOutputFiles = [TARGET_DIR + '/201701/20170101_123456.jpg',
                               TARGET_DIR + '/201701/IMG-20170101-WA0000.jpg',
                               TARGET_DIR + '/201702/20170201_123456.jpg',
                               TARGET_DIR + '/201702/IMG-20170201-WA0000.jpg']
        actualOutputFiles = set(self.getFilesInTargetFolder(TARGET_DIR))
        self.assertItemsEqual(expectedOutputFiles, actualOutputFiles, 'expected \n' + ', '.join(expectedOutputFiles) + '\n does not match actual \n' + ', '.join(actualOutputFiles))


    def getFilesInTargetFolder(self, rootfolder):
        # Copied from https://stackoverflow.com/a/19308592/7290832
        file_paths = []
        for root, directories, files in os.walk(rootfolder):
            for filename in files:
                filepath = os.path.join(root, filename)
                file_paths.append(filepath)
        return file_paths


if __name__ == '__main__':
    unittest.main()
