package com.example.assignment.utils;

import com.example.assignment.models.Token;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TokenUtils {

    public List<Token> convertExpressionToTokens(String expression) {
        int number = 0;
        List<Token> tokenList = new ArrayList<>();
        for(char character : expression.toCharArray()){
            if(character >= '0' && character <= '9'){
                number = number*10+ character - '0';
            }
            else{
                if(number != 0){
                    tokenList.add(Token.createNumberToken((double) number));
                    number = 0;
                }
                tokenList.add(Token.createSymbolToken(character));
            }
        }
        if(number != 0){
            tokenList.add(Token.createNumberToken((double) number));
        }
        return tokenList;
    }
}
