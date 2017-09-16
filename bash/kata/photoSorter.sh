#!/usr/bin/env bash

DEBUG_ENABLED="0"

function debug {
    if [ $DEBUG_ENABLED = "1" ]
    then
        echo "debug: $1"
    fi
}

function getDateFromFileName {
    fileName="$(echo -e "${1}" | sed -e 's/^[[:space:]]*//')"
    result=""
    if [[ ! $fileName == "" ]]
    then
        fileName=`basename $fileName`
        if [[ $fileName == IMG-* ]]
        then
            fileName=${fileName:4}
        fi
        if [[ ${#fileName} -gt 6 ]]
        then
            fileName=${fileName:0:6}
            if date -jf "%Y%m" "${fileName:0:4}${fileName:4:2}" &> /dev/null
            then
                result=$fileName
            fi
        fi
    fi
    echo $result
}

function findJpgFiles {
    inputFolder=$1
    files=($(find -E $inputFolder -type f -regex "^.*jpg$"))
    echo "${files[@]}"
}

function getCurrentPhotos {
    currentFolder=$1
    if [ "$currentFolder" = "undefined" ]
    then
        currentPhotos=( )
    else
        currentPhotos=( $(findJpgFiles $currentFolder ) )
    fi
    echo "${currentPhotos[@]}"
}

function copyJpgFiles {
    inputFolder=$1
    outputFolder=$2
    currentPhotoFolder=${3:-undefined}
    currentPhotos=`getCurrentPhotos $currentPhotoFolder`
    rm -rf $outputFolder
    mkdir -p $outputFolder
    jpgFiles=( $(findJpgFiles $inputFolder) )
    if [ "$currentPhotos" != "" ]
    then
        for i in "${currentPhotos[@]}"; do
            fileName=$inputFolder/${i##*/}
            jpgFiles=( ${jpgFiles[@]//*$fileName*} )
        done
    fi
    for file in "${jpgFiles[@]}"
    do
        yearMonth=`getDateFromFileName $file`
        targetDir="$outputFolder/$yearMonth"
        mkdir -p $targetDir
        cp $file $targetDir
    done
}

echo "The number of arguments is: $#"
if [ "$#" -lt 2 ]; then
    echo "Usage: photoSorter <directoryWithUnsortedPhotos> <directoryForSortedPhotos> [directoryForPhotosThatWereCopiedEarlier]"
    exit -1
fi
inputFolder=${1:-undefined}
outputFolder=${2:-undefined}
currentPhotoFolder=${3:-undefined}
copyJpgFiles $inputFolder $outputFolder $currentPhotoFolder
