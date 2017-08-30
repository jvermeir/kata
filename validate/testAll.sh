#!/usr/bin/env bash
#
# Run this script to test all solutions
#
TARGET=../validate/target
INPUT=../validate/testData/input
CURRENT=../validate/testData/currentPhotos

function cleanUp() {
    rm -rf $TARGET
    mkdir $TARGET
}

cleanUp
(cd ../javascript && node ./src/PhotoSorter.js $INPUT $TARGET $CURRENT)
python acceptanceTest.py

cleanUp
(cd ../java && mvn exec:java)
python acceptanceTest.py

cleanUp
(cd ../scala && java -jar target/scala-2.12/photoSorter-assembly-1.0.jar $INPUT $TARGET $CURRENT)
python acceptanceTest.py

cleanUp
(cd ../python && python kata/findAndOrganizeNewPhotos.py $INPUT $TARGET $CURRENT)
python acceptanceTest.py

cleanUp
(cd ../go && ./bin/sortPhotos $INPUT $TARGET $CURRENT)
python acceptanceTest.py
