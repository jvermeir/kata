;============================================================
; Functions to manipulate string or areas of the display
;============================================================

; get the date part of a filename, TODO: we're not done yet...
; the filename starts at the address pointed to by $02/$03
; and ends in $
; see https://www.c64-wiki.com/wiki/Indirect-indexed_addressing

!zone get_date

get_date   ldy #$00

.loop      lda ($02), y
           cmp #"$"
           beq .test
           sta $0403, y
           iny
           jmp .loop

.test      cpy #19
           beq .ok
           lda #"1"
           jmp .store_res
.ok        lda #"0"
.store_res sta $0400

           rts

; copy empty string to area starting in 05/06
!zone cleanup
cleanup     +load16BitsIn2Bytes empty, $02, $03
            jsr copy
            rts

; copy string starting in 02/03 to area starting in 05/06
!zone copy
copy        ldy #$00
.loop       lda ($02), y
            cmp #"$"
            sta ($05), y
            beq .exit
            iny
            jmp .loop
.exit       rts

; compare string starting in 02/03 with string starting in 05/06
!zone compare
compare     ldy #$00
.loop       lda ($02), y
            cmp #"$"
            beq .ok
            cmp ($05), y
            bne .fail
            iny
            jmp .loop
.fail       lda #"1"
            jmp .exit
.ok         lda #"0"
.exit       rts

; find out if string in 02/03 starts with string in 05/06
!zone startsWith
startsWith  ldy #$00
.loop       lda ($05), y
            cmp #"$"
            beq .ok
            cmp ($02), y
            bne .fail
            lda ($02), y
            cmp #"$"
            beq .fail
            iny
            jmp .loop
.fail       lda #"1"
            jmp .exit
.ok         lda #"0"
.exit       rts

; find out if a string in 02/03 contains only digits
!zone digitsOnly
digitsOnly  ldy #$00
    +load16BitsIn2Bytes $0680, $05, $06
.loop       lda ($02), y
            sta ($05), y
            cmp #"$"
            beq .ok
            cmp #"0"
            bmi .fail
            cmp #"9"
            bpl .fail
            iny
            jmp .loop
.fail       lda #"1"
            jmp .exit
.ok         lda #"0"
.exit       rts

; copy a substring of a string in 02/03 to 05/06
; TODO
