;============================================================
; Inspired by actraiser/Dustlayer
;
; Tutorial: http://dustlayer.com/c64-coding-tutorials/2013/2/17/a-simple-c64-intro
; Dustlayer WHQ: http://dustlayer.com
;============================================================

;============================================================
; index file which loads all source code and resource files
;============================================================

;============================================================
;    specify output file
;============================================================

!cpu 6510
!to "build/hello_world.prg",cbm    ; output file

;============================================================
; BASIC loader with start address $c000
;============================================================

* = $0801                               ; BASIC start address (#2049)
!byte $0d,$08,$dc,$07,$9e,$20,$34,$39   ; BASIC loader to start at $c000...
!byte $31,$35,$32,$00,$00,$00           ; puts BASIC line 2012 SYS 49152
* = $c000     				            ; start address for 6502 code

;============================================================
;  Main routine
;============================================================

!source "code/main.asm"

;============================================================
; tables and strings of data 
;============================================================

!source "code/data_static_text.asm"

;============================================================
; one-time initialization routines
;============================================================

!source "code/init_clear_screen.asm"
!source "code/init_static_text.asm"

;============================================================
; Tests, work in progress
;============================================================

!source "code/get_date_from_filename.asm"
