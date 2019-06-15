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

class FindAndOrganizeNewPhotos:
    def get_jpg_files(self, root_dir):
        jpg_files = dict()
        for root, dir_names, file_names in os.walk(root_dir):
            for file_name in fnmatch.filter(file_names, '*.[jJ][pP][gG]'):
                jpg_files[file_name] = os.path.join(root, file_name)
        return jpg_files

    date_pattern: str = r'^(?:IMG-)?(\d\d\d\d\d\d\d\d)[_-].*'

    def extract_date_from_file_name(self, file_name):
        file_name_parts = file_name.split('/')
        base_name = file_name_parts[len(file_name_parts)-1]
        date = re.findall(self.date_pattern, base_name)
        if len(date) > 0 and len(date[0]) == 8:
            try:
                datetime.datetime.strptime(date[0], "%Y%m%d")
            except ValueError:
                return ""
            return date[0][:6]
        else:
            return ""

    def copy_new_photos_to_target_folder(self, source_directory, output_directory, current_photos_directory=None):
        from_phone = self.get_jpg_files(source_directory)
        currentPhotos = self.get_optional_list_of_existing_photos(current_photos_directory)
        new_photos = set(from_phone.keys()) - set(currentPhotos.keys())

        for photo in new_photos:
            source = from_phone[photo]
            target = output_directory + "/" + self.extract_date_from_file_name(source)
            try:
                os.makedirs(target)
            except OSError:
                pass
            shutil.copy(from_phone[photo], target)

    def get_optional_list_of_existing_photos(self, current_photos_directory):
        if current_photos_directory is None:
            current_photos = dict()
        else:
            current_photos = self.get_jpg_files(current_photos_directory)
        return current_photos

    def create_empty_target_folder(self, target_dir):
        try:
            shutil.rmtree(target_dir)
        except OSError:
            pass
        os.mkdir(target_dir)


if __name__ == '__main__':
    if len(sys.argv) < 3:
        sys.exit(
            "Usage: python findAndOrganizeNewPhotos.py <directory_with_unsorted_photos> <directory_for_sorted_photos> "
            "[directory_for_photos_that_were_copied_earlier]")

    from_phone_dir = sys.argv[1]
    target_dir = sys.argv[2]
    all_photos_dir = None
    if len(sys.argv) == 4:
        all_photos_dir = sys.argv[3]

    FindAndOrganizeNewPhotos().create_empty_target_folder(target_dir)

    FindAndOrganizeNewPhotos().copy_new_photos_to_target_folder(from_phone_dir, target_dir, all_photos_dir)

    print("Now run acceptanceTest.py to check if it worked")
