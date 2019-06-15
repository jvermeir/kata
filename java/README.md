# Java Kata version

####Build

```
mvn clean install
```

####Test
```
mvn exec:java -Dexec.mainClass="kata.PhotoSorter" -Dexec.args="../validate/testData/input/ ../validate/target/ ../validate/testData/currentPhotos/"
```
then run acceptanceTest from the `validate` folder:
```
python acceptanceTest.py
```

####Web site


```
mvn spring-boot:run

curl -X POST  localhost:8080/sort
```
