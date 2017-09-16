#!/usr/bin/env bash

source ../kata/photoSorter.sh

INPUT_DIR=../../validate/testData/input
CURRENT_PHOTOS_DIR=../../validate/testData/currentPhotos
OUTPUT_DIR=/tmp/target

function testEqual {
    command=$1
    expected=$2
    got=$3
    debug "[test args= $command - $expected - $got]"
    if [ "$got" != "$expected" ]
    then
        echo "$command: expected $expected, got $got"
    fi
}

function testGetDateFromFileName {
    echo "testGetDateFromFileName"
    testEqual "getDateFromFileName 20170101_123456" 201701 $(getDateFromFileName 20170101_123456)
    testEqual "getDateFromFileName IMG-20170101_123456" 201701 $(getDateFromFileName IMG-20170101_123456)
    testEqual "getDateFromFileName input/data/20170101_123456" 201701 $(getDateFromFileName input/data/20170101_123456)
    testEqual "getDateFromFileName IM-20170101_123456" "" $(getDateFromFileName IM-20170101_123456)
    testEqual "getDateFromFileName aap" "" $(getDateFromFileName aap)
    testEqual "getDateFromFileName ' '" "" $(getDateFromFileName " ")
}

function testFiveFilesAreReadFromTestInput {
    echo "testFiveFilesAreReadFromTestInput"
    allJpgFiles=( $(findJpgFiles $INPUT_DIR) )
    testEqual "findFiles ../../validate/testData/input 5" 5 ${#allJpgFiles[@]}
}

function testOutputContainsCorrectFiles {
    echo "testOutputContainsCorrectFiles"
    copyJpgFiles $INPUT_DIR $OUTPUT_DIR
    outputFiles=( $(findJpgFiles $OUTPUT_DIR) )
    sortedOutput=( $(sort <<<"${outputFiles[*]}") )
    expectedOutput=( $OUTPUT_DIR/201701/20170101_123456.jpg $OUTPUT_DIR/201701/20170102_123456.jpg $OUTPUT_DIR/201701/IMG-20170101-WA0000.jpg $OUTPUT_DIR/201702/20170201_123456.jpg $OUTPUT_DIR/201702/IMG-20170201-WA0000.jpg )
    testEqual "$findJpgFiles $OUTPUT_DIR" "${expectedOutput[*]}" "${sortedOutput[*]}"
}

function testOutputContainsOnlyNewFiles {
    echo "testOutputContainsCorrectFiles"
    copyJpgFiles $INPUT_DIR $OUTPUT_DIR $CURRENT_PHOTOS_DIR
    outputFiles=( $(findJpgFiles $OUTPUT_DIR) )
    sortedOutput=( $(sort <<<"${outputFiles[*]}") )
    expectedOutput=( $OUTPUT_DIR/201701/20170101_123456.jpg $OUTPUT_DIR/201701/IMG-20170101-WA0000.jpg $OUTPUT_DIR/201702/20170201_123456.jpg $OUTPUT_DIR/201702/IMG-20170201-WA0000.jpg )
    testEqual "$findJpgFiles $OUTPUT_DIR" "${expectedOutput[*]}" "${sortedOutput[*]}"
}

testGetDateFromFileName
testFiveFilesAreReadFromTestInput
testOutputContainsCorrectFiles
testOutputContainsOnlyNewFiles