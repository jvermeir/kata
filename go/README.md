# Go version of Kata problem

## Pre-reqs
Set your GOPATH variable like this

```
export GOPATH=`PWD`
```
To build and test I've used Sebastien Binets 
make utility [https://github.com/sbinet/mk/blob/master/main.go](https://github.com/sbinet/mk/blob/master/main.go)

Just do 
```
go get github.com/sbinet/mk
```
and copy the resulting mk executable to a folder that's in the PATH on your system. 

Then do
```
mk
```
to compile all modules and run unit tests. 

Finally test the program: 

```
./bin/sortPhotos ../validate/testData/input /tmp/target/ ../validate/testData/currentPhotos
``` 