;============================================================
; write the two line of text to screen center
;============================================================


init_text  ldx #$00          ; init X-Register with $00
loop_text  lda line1,x      ; read characters from line1 table of text...
           sta $0590,x      ; ...and store in screen ram near the center
           lda #$01         ; set color to 1
           sta $d990,x
           lda line2,x      ; read characters from line1 table of text...
           sta $05e0,x      ; ...and put 2 rows below line1
           lda #$03         ; set color to 3
           sta $d9e0,x

           inx 
           cpx #$28         ; finished when all 40 cols of a line are processed
           bne loop_text    ; loop if we are not done yet
           rts