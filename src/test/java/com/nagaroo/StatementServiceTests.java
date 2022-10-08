package com.nagaroo;
import static org.junit.jupiter.api.Assertions.*;

import com.nagaroo.controller.AuthController;
import com.nagaroo.controller.BankStatement;
import com.nagaroo.model.Filter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collection;

@SpringBootTest(properties = "spring.jmx.enabled=true")
@ExtendWith(SpringExtension.class)
class StatementServiceTests {

	@Autowired
	BankStatement bankStatement;

	@Autowired
	AuthController authController;

	@Mock
	Authentication authentication;

	@BeforeEach
	public void setup() {
		//if we don't call below, we will get NullPointerException
		MockitoAnnotations.initMocks(this);
	}
	@Test
	void contextLoads() {
	}

	@Test
	public void testAdminPositiveScenario(){
		Filter filter = new Filter();
		filter.setAccountId("101");
		filter.setTodateField("2022-10-01");
		filter.setFromdateField("2022-08-01");
		filter.setFromAmount("100");
		filter.setToAmount("300");
		Mockito.doReturn(getmockAuthorities("ADMIN")).when(authentication).getAuthorities();
		ResponseEntity statement = bankStatement.getStatement(filter, authentication);
		assertNotNull(statement);
	}

	@Test
	public void testAdminNegativeScenario(){
		Filter filter = new Filter();
		filter.setAccountId("101");
		filter.setTodateField("022-10-01");
		filter.setFromdateField("022-01-01");
		filter.setFromAmount("A");
		filter.setToAmount("B");
		Mockito.doReturn(getmockAuthorities("ADMIN")).when(authentication).getAuthorities();
		ResponseEntity statement = bankStatement.getStatement(filter, authentication);
		assertNotNull(statement);
	}


	@Test
	public void testAdminValidationScenario(){
		Filter filter = new Filter();
		filter.setAccountId(null);
		filter.setTodateField(null);
		filter.setFromdateField(null);
		filter.setFromAmount(null);
		filter.setToAmount(null);
		Mockito.doReturn(getmockAuthorities("ADMIN")).when(authentication).getAuthorities();
		ResponseEntity statement = bankStatement.getStatement(filter, authentication);
		assertNotNull(statement);
	}

	@Test
	public void testUserPositiveScenario(){
		Filter filter = new Filter();
		filter.setAccountId("101");
		filter.setTodateField(null);
		filter.setFromdateField(null);
		filter.setFromAmount(null);
		filter.setToAmount(null);
		Mockito.doReturn(getmockAuthorities("USER")).when(authentication).getAuthorities();
		ResponseEntity statement = bankStatement.getStatement(filter, authentication);
		assertNotNull(statement);
	}

	@Test
	public void testUserValidationScenario(){
		Filter filter = new Filter();
		filter.setAccountId("101");
		filter.setTodateField("2022-10-01");
		filter.setFromdateField("2022-01-01");
		filter.setFromAmount("0");
		filter.setToAmount("1000");
		Mockito.doReturn(getmockAuthorities("USER")).when(authentication).getAuthorities();
		ResponseEntity statement = bankStatement.getStatement(filter, authentication);
		assertNotNull(statement);
	}

	@Test
	public void testStatement(){
		bankStatement.home();
	}

	@Test
	public void testAuthPage(){
		authController.loginExpire();
		authController.loginInvalid();

	}
	
	public Collection<SimpleGrantedAuthority> getmockAuthorities (String role){
		SimpleGrantedAuthority admin = new SimpleGrantedAuthority(role);
		Collection<SimpleGrantedAuthority> collections = new ArrayList<SimpleGrantedAuthority>(){{add(admin);}};
		return collections;
	}
}
