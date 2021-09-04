package com.example.assignment.services;

import com.example.assignment.models.Token;
import com.example.assignment.utils.TokenUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

class ArithmeticOperationServiceTest {

    @Mock
    private TokenUtils tokenUtils;

    @InjectMocks
    private ArithmeticOperationService arithmeticOperationService;

    @Test
    public void calculateExpression(){
        String expression = "5*(3+(1-5))/5";
        TokenUtils tokenUtils = new TokenUtils();
        List<Token> tokenList = tokenUtils.convertExpressionToTokens(expression);
        double answer = arithmeticOperationService.evaluateExpression(expression);
        Assertions.assertEquals(-1.0, answer);
    }

}