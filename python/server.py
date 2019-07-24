#!flask/bin/python
from flask import *
from kata.photo_finder import *
import json

server = Flask(__name__)
photo_service = FindAndOrganizeNewPhotos()

@server.route('/')
def index():
    return "Hello, World!"


@server.route('/sort', methods = ['POST'])
def sort_photos():
    root_dir = Path(__file__).resolve().parent.parent / "validate"
    target_dir = root_dir / 'target'
    from_phone_dir = root_dir / 'testData/input'
    all_photos_dir = root_dir / 'testData/currentPhotos'

    photo_service.create_empty_target_folder(target_dir)
    photo_service.copy_new_photos_to_target_folder(from_phone_dir, target_dir, all_photos_dir)
    files = photo_service.get_jpg_files(target_dir).values()

    files_as_string = [f'{path}'.replace(f'{target_dir}','') for path in files]

    return Response(json.dumps(files_as_string), mimetype='text/json')

if __name__ == '__main__':
    server.run(debug=True)
