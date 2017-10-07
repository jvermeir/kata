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
           lda #$31
           jmp .store_res
.ok        lda #$30
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
.fail       lda #$31
            jmp .exit
.ok         lda #$30
.exit       rts

; find out if a string starts with another string
; TODO

; find out if a string contains only digits
; TODO
