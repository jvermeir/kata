;============================================================
;    some initialization and interrupt redirect setup
;============================================================

           sei         ; set interrupt disable flag
            
           jsr init_screen     ; clear the screen
           jsr init_text       ; write lines of text

           jmp *                ; infinite loop
