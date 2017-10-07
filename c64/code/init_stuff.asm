;============================================================
; Some sample code that shows text on the display
;
; copied from https://github.com/actraiser/dust-tutorial-c64-first-intro/blob/master/code/init_static_text.asm
;============================================================

!zone init_stuff

; write the two line of text to screen center
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

; Clear the screen and set text color to 3
clear_screen     ldx #$00     ; set X to zero (black color code)
                 stx $d021    ; set background color
                 stx $d020    ; set border color

.clear           lda #$20     ; #$20 is the spacebar Screen Code
                 sta $0400,x  ; fill four areas with 256 spacebar characters
                 sta $0500,x 
                 sta $0600,x 
                 sta $06e8,x 
                 lda #$03     ; make sure all output is visible
                 sta $d800,x  
                 sta $d900,x
                 sta $da00,x
                 sta $dae8,x
                 inx
                 bne .clear

                 rts