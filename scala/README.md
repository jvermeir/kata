# Scala Kata version

####Build

```
sbt clean intall
sbt assembly
```

If you see an error from Artima, make sure `assembly.sbt` contains a current version. Check on `http://repo.artima.com/releases/com/artima/supersafe/`,
select the version for the current sbt version and add that in  `~/.sbt/1.0/global.sbt`, like this

```
resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"
```

####Test
```
java -jar target/scala-2.12/photoSorter-assembly-1.0.jar
```
then run acceptanceTest from the validate folder:
```
python acceptanceTest.py
```
