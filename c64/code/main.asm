;
; Show credits and run main program
;
; TODO: this version shows a string at the top of the display
; In the future, it might actually get the date from the string
; that is stored at label 'ok1'.
;

           jsr clear_screen
           jsr credits       ; write credits

                             ; test cases

           lda #<ok1
           sta $02
           lda #>ok1
           sta $03
           jsr get_date

           jmp *              ; infinite loop
