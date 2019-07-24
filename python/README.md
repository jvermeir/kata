# Kata Python version

## Requirements

- python 3.7
- pycodestyle 
- flask

### Flask
    pip install virtualenv
    virtualenv flask
    cd flask
    source bin/activate
    pip install flask
    
In PyCharm I had to install Flask by clicking the warning that appears when
I added `import flask` to `server.py`.      

## Build

Well, it's Python...

## Test

```
python kata/photo_finder.py ../validate/testData/input/ ../validate/target/ ../validate/testData/currentPhotos/
```

website:

run server process:

```
python server.py
```

test

```
curl -X POST localhost:5000/sort
```

This should result in a json document like this:

```
["/201701/IMG-20170101-WA0000.jpg", "/201701/20170101_123456.jpg", "/201702/20170201_123456.jpg", "/201702/IMG-20170201-WA0000.jpg"]
```

Use pycodestyle to check.

```
pip install pycodestyle
```

```
pycodestyle .
```

(ignoring line length warnings that are based on 1980's terminals)

## Links

see `https://blog.miguelgrinberg.com/post/designing-a-restful-api-with-python-and-flask` for a Flask tutorial. 
