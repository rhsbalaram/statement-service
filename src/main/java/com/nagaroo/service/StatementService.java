package com.nagaroo.service;

import com.nagaroo.entity.Statement;
import com.nagaroo.model.Filter;
import com.nagaroo.model.Validation;
import com.nagaroo.repository.StatementRepository;
import com.nagaroo.utils.StatementConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class StatementService {

    public static final Logger LOGGER =  LoggerFactory.getLogger(StatementService.class);

    @Autowired
    StatementRepository statementRepository;

    public List<Statement> getFilteredStatement(Filter filter){
        List<Statement> statements = statementRepository.getStatement(filter.getAccountId());
        LOGGER.info("DB result set : {}", statements);
        return statements.stream().filter(statement -> {
            //Doing date validation only if date field in not null (inbetween from date and to date)
            if(Objects.nonNull(filter.getFromdateField()) && Objects.nonNull(filter.getTodateField()) ){
                LocalDate statementDate = LocalDate.parse(statement.getDatefield(), StatementConstants.formatter);
                if(LocalDate.parse(filter.getFromdateField(), StatementConstants.formatter1).isAfter(statementDate) || LocalDate.parse(filter.getTodateField(),StatementConstants.formatter1 ).isBefore(statementDate)){
                    return false;
                }
            }
            //Doing Amount validation only if Amount field in not null (inbetween from Amount and to Amount)
            if(Objects.nonNull(filter.getFromAmount()) && Objects.nonNull(filter.getToAmount()) ) {
                BigDecimal statementAmount = new BigDecimal(statement.getAmount());
                if(new BigDecimal(filter.getFromAmount()).compareTo(statementAmount) > 0 || new BigDecimal(filter.getToAmount()).compareTo(statementAmount) < 0){
                    return false;
                }

            }
        return true;
        }

        ).collect(Collectors.toList());
    }

    public Validation validateFilter(Filter filter, Authentication authentication) {
        Validation validation = new Validation();
        validation.setValid(true);
        if (authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals(StatementConstants.USER)) &&
                (Objects.nonNull(filter.getFromAmount())
                        || Objects.nonNull(filter.getToAmount())
                        || Objects.nonNull(filter.getFromdateField())
                        || Objects.nonNull(filter.getTodateField()))) {
            validation.setValid(false);
            validation.setValidUser(false);
        }
        if (Objects.isNull(filter.getAccountId())) {
            validation.setValid(false);
            validation.setValidAccountNumber(false);
        }
        if (Objects.nonNull(filter.getFromdateField()) || Objects.nonNull(filter.getTodateField())) {
            try {
                LocalDate.parse(filter.getFromdateField(), StatementConstants.formatter1);
                LocalDate.parse(filter.getTodateField(), StatementConstants.formatter1);
            } catch (Exception e) {
                validation.setValid(false);
                validation.setValidDateField(false);
            }
        }  if(Objects.isNull(filter.getFromdateField())){
            filter.setFromdateField(StatementConstants.formatter1.format(LocalDate.now().minusMonths(3)));
        }  if(Objects.isNull(filter.getTodateField())){
            filter.setTodateField(StatementConstants.formatter1.format(LocalDate.now()));
        }
        if (Objects.nonNull(filter.getFromAmount()) && Objects.nonNull(filter.getToAmount())){
            try {
            new BigDecimal(filter.getFromAmount());
            new BigDecimal(filter.getToAmount());
        } catch (Exception e) {
            validation.setValid(false);
            validation.setValidAmount(false);
        }
    }

        return validation;
    }

}
