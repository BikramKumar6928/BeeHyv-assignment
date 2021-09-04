package com.example.assignment.services;

import com.example.assignment.enums.Symbol;
import com.example.assignment.exceptions.AssignmentException;
import com.example.assignment.models.Token;
import com.example.assignment.models.Usage;
import com.example.assignment.repository.UsageRepository;
import com.example.assignment.utils.TokenUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OperatorUsageService {

    @Autowired
    private UsageRepository usageRepository;

    @Autowired
    private TokenUtils tokenUtils;

    private static final Set<Symbol> symbolsToMaintainHistory = Set.of(Symbol.DIVIDE, Symbol.MULTIPLY, Symbol.ADD, Symbol.SUBTRACT);

    public void addExpressionToUsageHistory(String expression, String userId){
        List<Usage> userUsageList= getUsageFromDBOrCreate(userId);
        List<Token> tokenList = tokenUtils.convertExpressionToTokens(expression);
        Map<Symbol, Integer> symbolFrequency = getSymbolFrequency(tokenList);
        List<Usage> updatedUsageList = new ArrayList<>();
        for(Usage usage : userUsageList){
            Symbol currentSymbol = usage.getSymbol();
            int updatedFrequency = usage.getFrequency() + symbolFrequency.getOrDefault(currentSymbol, 0);
            usage.setFrequency(updatedFrequency);
            updatedUsageList.add(usage);
        }
        usageRepository.saveAll(updatedUsageList);
    }

    private List<Usage> getUsageFromDBOrCreate(String userId) {
        List<Usage> usageList = usageRepository.findAllByUserId(userId);
        if(!CollectionUtils.isEmpty(usageList)){
            return usageList;
        }
        List<Usage> newUsageList = new ArrayList<>();
        for (Symbol symbol : symbolsToMaintainHistory) {
            Usage usage = new Usage();
            usage.setUserId(userId);
            usage.setFrequency(0);
            usage.setSymbol(symbol);
            newUsageList.add(usage);
        }
        usageRepository.saveAll(newUsageList);
        return newUsageList;
    }

    public Usage getMaxFrequencyForUser(String userId){
        List<Usage> userUsageList= usageRepository.findAllByUserId(userId);
        Optional<Usage> usageWithMaxFrequency =  userUsageList.stream().max(Comparator.comparing(Usage::getFrequency));
        if(usageWithMaxFrequency.isEmpty()){
            throw new AssignmentException("No value has been added till now");
        }
        return usageWithMaxFrequency.get();
    }

    private Map<Symbol, Integer> getSymbolFrequency(List<Token> tokenList) {
        Map<Symbol, Integer> symbolFrequencyMap = new HashMap<>();
        for(Token token : tokenList){
            if(token.isNumber()){
                continue;
            }
            Symbol symbolByValue = Symbol.getSymbolByValue(token.getTokenAsSymbol());
            if(symbolsToMaintainHistory.contains(symbolByValue)){
                int currentFrequency = symbolFrequencyMap.getOrDefault(symbolByValue, 0);
                symbolFrequencyMap.put(symbolByValue, ++currentFrequency);
            }
        }
        return  symbolFrequencyMap;
    }


}
