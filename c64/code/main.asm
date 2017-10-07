;============================================================
; Show credits and run main program
;
; TODO: this version shows a string at the top of the display
; In the future, it might actually get the date from a string
;============================================================

!zone main

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

