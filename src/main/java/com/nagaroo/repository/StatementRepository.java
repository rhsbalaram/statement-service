package com.nagaroo.repository;

import com.nagaroo.entity.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Base64;
import java.util.List;
@Repository
public class StatementRepository {
   @Autowired
    JdbcTemplate jdbcTemplate;

   public List<Statement> getStatement(String accountNumber){
       List<Statement> statementList = jdbcTemplate.query("Select * from Statement where account_id = ?", new Object[]{accountNumber}, (rs, rownum) -> {
           return new Statement()
                   .setId(rs.getLong("id"))
                   .setAccountId(Base64.getEncoder().encodeToString(rs.getString("account_id").getBytes()))
                   .setDatefield(rs.getString("datefield"))
                   .setAmount(rs.getString("amount"));
       });
       return statementList;
   }
}