package com.nagaroo.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Statement")
public class Statement {

	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "account_id")
	private String accountId;
	@Column(name = "datefield")
	private String datefield;
	@Column(name = "amount")
	private String amount;

	public Long getId() {
		return id;
	}

	public Statement setId(Long id) {
		this.id = id;
		return this;
	}

	public String getAccountId() {
		return accountId;
	}

	public Statement setAccountId(String accountId) {
		this.accountId = accountId;
		return this;
	}

	public String getDatefield() {
		return datefield;
	}

	public Statement setDatefield(String datefield) {
		this.datefield = datefield;
		return this;
	}

	public String getAmount() {
		return amount;
	}

	public Statement setAmount(String amount) {
		this.amount = amount;
		return this;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", id)
				.append("accountId", accountId)
				.append("datefield", datefield)
				.append("amount", amount)
				.toString();
	}
}