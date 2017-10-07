;============================================================
; write the two line of text to screen center
; copied from https://github.com/actraiser/dust-tutorial-c64-first-intro/blob/master/code/init_static_text.asm
;============================================================

credits    ldy #$00          ; init Y-Register with $00
.loop_text lda line1,y      ; read characters from line1 table of text...
           sta $0590,y      ; ...and store in screen ram near the center
           lda #$01         ; set color to 1
           sta $d990,y

           lda line2,y      ; read characters from line1 table of text...
           sta $05e0,y      ; ...and put 2 rows below line1
           lda #$03         ; set color to 3
           sta $d9e0,y

           iny
           cpy #$28         ; finished when all 40 cols of a line are processed
           bne .loop_text    ; loop if we are not done yet
           rts