package com.nagaroo.controller;

import com.nagaroo.model.Filter;
import com.nagaroo.model.Validation;
import com.nagaroo.service.StatementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BankStatement {

    public static final Logger LOGGER =  LoggerFactory.getLogger(BankStatement.class);
    @Autowired
    StatementService statementService;
    @GetMapping("/")
    public String home() { return "bankStatement"; }

    @ResponseBody
    @PostMapping(path = "/bankStatement",consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity getStatement(Filter filter, Authentication authentication){
        LOGGER.info("Request payload : {}",filter);
        Validation validation = statementService.validateFilter(filter, authentication);
        LOGGER.info("validation result : {}",validation);
        if(!validation.isValid()){
            if(!validation.isValidUser()){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(validation);
            }
            return ResponseEntity.badRequest().body(validation);
        }
        return ResponseEntity.ok().body(statementService.getFilteredStatement(filter));
    }

}
