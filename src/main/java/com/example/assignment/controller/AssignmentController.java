package com.example.assignment.controller;

import com.example.assignment.models.Usage;
import com.example.assignment.services.ArithmeticOperationService;
import com.example.assignment.services.OperatorUsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/assignment")
public class AssignmentController {
    @Autowired
    private ArithmeticOperationService arithmeticOperationService;

    @Autowired
    private OperatorUsageService operatorUsageService;

    @PostMapping("/{userId}")
    public double addExpression(@RequestBody String expression, @PathVariable String userId){
        operatorUsageService.addExpressionToUsageHistory(expression, userId);
        return arithmeticOperationService.evaluateExpression(expression);
    }

    @GetMapping("/{userId}")
    public Usage getMaxFrequency(@PathVariable String userId){
        return operatorUsageService.getMaxFrequencyForUser(userId);
    }
}
