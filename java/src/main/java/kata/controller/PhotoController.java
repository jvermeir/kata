package kata.controller;

import kata.PhotoSorter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PhotoController {

    PhotoSorter photoSorter = new PhotoSorter();

    @RequestMapping("/")
    public String getPhotos() {
        return "Hello, world";
    }

    @PostMapping("/sort")
    public List<String> sort() throws IOException {
        photoSorter.copyFilesToDirectories("../validate/testData/input/", "../validate/target", "../validate/testData/currentPhotos/");
        Path current = Paths.get("").toAbsolutePath();
        List<String> result =
                Files.walk(Paths.get("../validate/target"))
                        .filter(Files::isRegularFile)
                        .map(fileName -> fileName.toString().substring(19))
                        .collect(Collectors.toList());
        return result;
    }
}
