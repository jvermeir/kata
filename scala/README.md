# Scala Kata version

####Build

```
sbt clean intall
sbt assembly
```

####Test
```
java -jar target/scala-2.12/photoSorter-assembly-1.0.jar
```
then run acceptanceTest from the validate folder:
```
python acceptanceTest.py
```
