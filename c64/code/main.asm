;
; Show credits and run main program
;
; TODO: this version shows a string at the top of the display
; In the future, it might actually get the date from a string
;

!macro load16BitsIn2Bytes .source, .targetLow, .targetHigh {
            lda #<.source
            sta .targetLow
            lda #>.source
            sta .targetHigh
}

            jsr clear_screen
            jsr credits

; test cases

            +load16BitsIn2Bytes ok1, $02, $03
            jsr get_date


!zone main
; experiment, copy ok1 string to $0630
            +load16BitsIn2Bytes $0630, $05, $06
            jsr cleanup
            +load16BitsIn2Bytes $0658, $05, $06
            jsr cleanup
            +load16BitsIn2Bytes ok1, $02, $03
            +load16BitsIn2Bytes $0630, $05, $06
            jsr copy
            ; compare 0630 with ok1, this should succeed (output a 0 in the first column of the second line)
            jsr compare
            sta $0428

            ; copy ok2 string to 0658
            +load16BitsIn2Bytes ok2, $02, $03
            +load16BitsIn2Bytes $0658, $05, $06
            jsr copy
            ; compare 0630 with 0658, this should fail (output a 1 in the second column of the second line)
            +load16BitsIn2Bytes $0630, $02, $03
            +load16BitsIn2Bytes $0658, $05, $06
            jsr compare
            sta $0429

            jmp *


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

