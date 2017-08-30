package kata

import (
	"regexp"
	"strings"
	"io/ioutil"
	"os"
)

func GetDateFromFileName(fileName string) string {
	fileName = removeLeadingIMGPart(extractBaseName(fileName))
	r := regexp.MustCompile(`^(?P<YearMonth>\d{6})(?P<Day>\d{2})[-_].*`)
	yearMonth := r.FindStringSubmatch(fileName)
	if len(yearMonth)>1 {
		return yearMonth[1]
	}
	return ""
}

func GetListOfFiles(directoryName string) []string {
	files, _ := ioutil.ReadDir(directoryName)
	result := make([]string, 0)
	for _, file := range files {
		if strings.HasSuffix(strings.ToUpper(file.Name()), "JPG") {
			result = append(result, file.Name())
		}
	}
	return result
}

func extractBaseName(fileName string) string {
	indexOfSlash := strings.LastIndex(fileName,"/") + 1
	return fileName[indexOfSlash:]
}

func removeLeadingIMGPart(fileName string) string {
	if strings.HasPrefix(fileName, "IMG-") {
		return fileName[4:]
	}
	return fileName
}

func SortPhotos(sourceDir string, outputBaseDir string) {
	SortOnlyNewPhotos(sourceDir, outputBaseDir, "")
}

func SortOnlyNewPhotos(sourceDir string, outputBaseDir string, currentPhotoDir string) {
	newPhotos := GetListOfFiles(sourceDir)
	for _, photo := range newPhotos {
		copyPhotoIfItsNew(photo, currentPhotoDir, outputBaseDir)
	}
}

func isPhotoInCurrentPhotoDir(photo string, currentPhotoDir string) bool {
	return TestFileExists(currentPhotoDir + "/" + extractBaseName(photo))
}

func copyPhotoIfItsNew(photo string, currentPhotoDir string, outputBaseDir string) {
	if !isPhotoInCurrentPhotoDir(photo, currentPhotoDir) {
		outputDir := outputBaseDir + "/" + GetDateFromFileName(photo)
		err := os.MkdirAll(outputDir, 0744)
		check(err)
		dat, _ := ioutil.ReadFile(photo)
		err = ioutil.WriteFile(outputDir + "/" + extractBaseName(photo), dat, 0644)
		check(err)
	}

}
func TestFileExists(fileName string) bool {
	if _, err := os.Stat(fileName); err == nil {
		return true
	} else {
		return false
	}
}

func check(err error) {
	if err != nil {
		println(err)
	}
}
