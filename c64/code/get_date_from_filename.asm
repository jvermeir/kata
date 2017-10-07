; get the date part of a filename, TODO: we're not done yet...
; the filename starts at the address pointed to by $02/$03
; and ends in $
; see https://www.c64-wiki.com/wiki/Indirect-indexed_addressing

get_date   ldy #$00

.loop      lda ($02), y
           cmp #$24         ; stop at $ sign
           beq .test
           sta $0403, y
           lda #$03
           sta $d803, y
           iny
           jmp .loop

.test      cpy #19
           beq .ok
           lda #$31
           jmp .store_res
.ok        lda #$30
.store_res sta $0400
           lda #$03
           sta $d800

           rts

