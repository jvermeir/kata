# C64 assembly version

## Getting started

Follow instructions on [Dustlayer](http://dustlayer.com/c64-coding-tutorials/2013/2/10/dust-c64-command-line-tool)
```
If you follow the instructions by the letter, you're running an executable as root. 
Live fast, die young.
```
This version of Dust tries to install Sublime 2, which has expired and won't run anymore. I've used IntelliJ instead and 
run the program from the command line:

```
dust compile
```
## Stuff

[memory map](http://sta.c64.org/cbm64mem.html) 

    $0801-$9FFF Basic area, use for programs?
    $A000-$BFFF Basic rom, parallel ram, switch %x00, %x01 or %x10: RAM area.
    $C000-$CFFF Upper ram
    $0400-$07E7 Screen area
    