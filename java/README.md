# Java Kata version

####Build

```
mvn clean install
```

####Test
```
mvn exec:java
```
then run acceptanceTest from the validate folder:
```
python acceptanceTest.py
```

####Web site


```
mvn spring-boot:run

curl -X POST  localhost:8080/sort
```
