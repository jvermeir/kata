package kata

import (
	"testing"
	"os"
)

func TestFolderNameIsDerivedFromFileName(t *testing.T) {
	cases := []struct {
		in, want string
	}{
		{"20170101_123456", "201701"},
		{"IMG-20170101-123456", "201701"},
		{"", ""},
		{"aap-20170101_WA0000.jpg", ""},
		{"IM-20170101_WA0000.jpg", ""},
		{"../../validate/testData/input/IMG-20170201-WA0000.jpg", "201702"},
	}
	for _, c := range cases {
		got := GetDateFromFileName(c.in)
		if got != c.want {
			t.Errorf("GetDateFromFileName(%q) == %q, want %q", c.in, got, c.want)
		}
	}
}

func TestFindFilesReturnsFiveFiles(t *testing.T) {
	photoDir := os.Getenv("GOPATH") + "/../validate/testData/input"
	files := GetListOfFiles(photoDir)
	if (len(files) != 5) {
		t.Errorf("GetListOfFiles(%q) == %q, want %q", photoDir, len(files), 5)
	}
}

func TestOutputContainsCorrectFiles(t *testing.T) {
	sourceDir := os.Getenv("GOPATH") + "/../validate/testData/input"
	outputDir := os.Getenv("GOPATH") + "/target/"
	os.RemoveAll(outputDir)
	SortPhotos(sourceDir, outputDir)
	files := []string{outputDir + "201701/20170101_123456.jpg", outputDir + "201701/20170102_123456.jpg",
		outputDir + "201701/IMG-20170101-WA0000.jpg", outputDir + "201702/20170201_123456.jpg",
		outputDir + "201702/IMG-20170201-WA0000.jpg"}
	for _,  file := range files {
		if !TestFileExists(file) {
			t.Errorf("File %q not found", file)
		}
	}
}

func TestOutputContainsCorrectFilesIfCurrentPhotoDirIsSet(t *testing.T) {
	sourceDir := os.Getenv("GOPATH") + "/../validate/testData/input"
	outputDir := os.Getenv("GOPATH") + "/target/"
	currentDir := os.Getenv("GOPATH") + "/../validate/testData/currentPhotos"
	os.RemoveAll(outputDir)
	SortOnlyNewPhotos(sourceDir, outputDir, currentDir)
	files := []string{
		outputDir + "201701/20170101_123456.jpg",
		outputDir + "201702/20170201_123456.jpg",
		outputDir + "201701/IMG-20170101-WA0000.jpg",
		outputDir + "201702/IMG-20170201-WA0000.jpg"}
	for _,  file := range files {
		if !TestFileExists(file) {
			t.Errorf("File %q not found", file)
		}
	}
	existingFile := outputDir + "201701/20170102_123456.jpg"
	if TestFileExists(existingFile) {
		t.Errorf("File %q should not be copied", existingFile)

	}
}


