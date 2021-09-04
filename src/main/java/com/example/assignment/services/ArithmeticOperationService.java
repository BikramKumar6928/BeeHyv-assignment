package com.example.assignment.services;

import com.example.assignment.enums.Symbol;
import com.example.assignment.exceptions.AssignmentException;
import com.example.assignment.models.Token;
import com.example.assignment.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Service
public class ArithmeticOperationService {

    @Autowired
    private TokenUtils tokenUtils;

    public double evaluateExpression(String expression){
        List<Token> expressionTokenList = tokenUtils.convertExpressionToTokens(expression);
        return evaluateExpression(expressionTokenList);
    }

    private double evaluateExpression(List<Token> expressionTokenList) {
        Stack<Token> symbolStack = new Stack<>();
        Stack<Token> numberStack = new Stack<>();
        for(Token token : expressionTokenList){
            if (token.isNumber()) {
                numberStack.add(token);
                continue;
            }
            if(isCharacterEquals(token, Symbol.OPEN_BRACKET.getValue())){
                symbolStack.push(token);
                continue;
            }
            if(isCharacterEquals(token, Symbol.CLOSE_BRACKET.getValue())){
                while(!symbolStack.empty() && symbolStack.peek().getTokenAsSymbol() != Symbol.OPEN_BRACKET.getValue()){
                    popAndEvaluate(symbolStack, numberStack);
                }
                if(!symbolStack.isEmpty()){
                    symbolStack.pop();
                }
                continue;
            }
            Symbol tokenSymbol = Symbol.getSymbolByValue(token.getTokenAsSymbol());
            while(!symbolStack.empty() &&
                    Symbol.getSymbolByValue(symbolStack.peek().getTokenAsSymbol()).getPrecedence() >=
                    tokenSymbol.getPrecedence()){
                popAndEvaluate(symbolStack, numberStack);
            }
            symbolStack.push(token);
        }
        popAndEvaluate(symbolStack, numberStack);
        return numberStack.peek().getTokenAsNumber();
    }

    private void popAndEvaluate(Stack<Token> symbolStack, Stack<Token> numberStack) {
        double rightValue = numberStack.pop().getTokenAsNumber();
        double leftValue = numberStack.pop().getTokenAsNumber();
        Symbol symbol = Symbol.getSymbolByValue(symbolStack.pop().getTokenAsSymbol());
        double binaryExpressionValue = evaluateBinaryExpression(leftValue, rightValue, symbol);
        numberStack.push(Token.createNumberToken(binaryExpressionValue));
    }

    private double evaluateBinaryExpression(double leftValue, double rightValue, Symbol symbol) {
        switch (symbol){
            case ADD:
                return leftValue + rightValue;
            case SUBTRACT:
                return leftValue - rightValue;
            case MULTIPLY:
                return leftValue * rightValue;
            case DIVIDE:
                return leftValue / rightValue;
            default:
                throw new AssignmentException("Not implemented for symbol: " + symbol);
        }
    }

    private boolean isCharacterEquals(Token token, char c) {
        return Character.valueOf(c).equals(token.getValue());
    }



}
