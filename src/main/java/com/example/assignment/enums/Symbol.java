package com.example.assignment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Symbol {
    ADD('+', 1),
    SUBTRACT('-', 1),
    MULTIPLY('*', 2),
    DIVIDE('/', 2),
    OPEN_BRACKET('(', 0),
    CLOSE_BRACKET(')', 0),
    ;

    private final char value;
    private final int precedence;

    public static Symbol getSymbolByValue(char value){
        for(Symbol symbol : Symbol.values()){
            if(symbol.getValue() == value){
                return symbol;
            }
        }
        throw new RuntimeException("No operator with value " + value);
    }
}
