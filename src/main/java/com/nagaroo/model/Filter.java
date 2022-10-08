package com.nagaroo.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Filter {
    private String accountId;
    private String fromdateField;
    private String todateField;
    private String fromAmount;
    private String toAmount;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getFromdateField() {
        return fromdateField;
    }

    public void setFromdateField(String fromdateField) {
        this.fromdateField = fromdateField;
    }

    public String getTodateField() {
        return todateField;
    }

    public void setTodateField(String todateField) {
        this.todateField = todateField;
    }

    public String getFromAmount() {
        return fromAmount;
    }

    public void setFromAmount(String fromAmount) {
        this.fromAmount = fromAmount;
    }

    public String getToAmount() {
        return toAmount;
    }

    public void setToAmount(String toAmount) {
        this.toAmount = toAmount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("accountNumber", accountId)
                .append("fromdateField", fromdateField)
                .append("todateField", todateField)
                .append("fromAmount", fromAmount)
                .append("toAmount", toAmount)
                .toString();
    }
}
