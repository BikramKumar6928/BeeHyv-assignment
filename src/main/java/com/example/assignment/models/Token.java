package com.example.assignment.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Token {
    private Object value;
    private boolean isNumber;

    public static Token createNumberToken(Double number){
        return createToken(number, true);
    }

    public static Token createSymbolToken(Character character){
        return createToken(character, false);
    }

    public double getTokenAsNumber(){
        return (double) value;
    }

    public char getTokenAsSymbol(){
        return (char) value;
    }

    private static Token createToken(Object number, boolean isNumber) {
        return Token.builder()
                .value(number)
                .isNumber(isNumber)
                .build();
    }
}
