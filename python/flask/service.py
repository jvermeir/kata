#!flask/bin/python
import json
import flask
from kata.FindAndOrganizeNewPhotos import FindAndOrganizeNewPhotos

app = flask.Flask(__name__)


@app.route('/')
def index():
    return "Hello, World!"


@app.route('sort', methods = ['POST'])
def sort():
    target_dir = '../../validate/target'
    from_phone_dir = '../../validate/testData/input'
    all_photos_dir = '../../validate/testData/currentPhotos'
    FindAndOrganizeNewPhotos().create_empty_target_folder(target_dir)
    FindAndOrganizeNewPhotos().copy_new_photos_to_target_folder(from_phone_dir, target_dir, all_photos_dir)
    files = FindAndOrganizeNewPhotos().get_jpg_files(target_dir)
    return json.dumps(files)


if __name__ == '__main__':
    app.run(debug=True)
