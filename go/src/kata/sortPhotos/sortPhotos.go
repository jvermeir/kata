package main

import (
	"fmt"
	"kata/photosorter"
	"os"
)

func main() {
	args := os.Args[1:]
	if (len(args) == 2) {
		kata.SortPhotos(args[0], args[1])
	} else if (len(args) == 3) {
		kata.SortOnlyNewPhotos(args[0], args[1], args[2])
	} else {
		fmt.Fprintf(os.Stderr, "usage: sortPhotos <directoryWithUnsortedPhotos> <directoryForSortedPhotos> [directoryForPhotosThatWereCopiedEarlier]")
		os.Exit(-1)
	}
}
