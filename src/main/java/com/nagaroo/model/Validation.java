package com.nagaroo.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Validation {
    private boolean isValid =  true;
    private boolean validAccountNumber = true;
    private boolean validDateField = true;
    private boolean validAmount = true;
    private boolean validUser = true;

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public boolean isValidAccountNumber() {
        return validAccountNumber;
    }

    public void setValidAccountNumber(boolean validAccountNumber) {
        this.validAccountNumber = validAccountNumber;
    }

    public boolean isValidDateField() {
        return validDateField;
    }

    public void setValidDateField(boolean validDateField) {
        this.validDateField = validDateField;
    }

    public boolean isValidAmount() {
        return validAmount;
    }

    public void setValidAmount(boolean validAmount) {
        this.validAmount = validAmount;
    }

    public boolean isValidUser() {
        return validUser;
    }

    public void setValidUser(boolean validUser) {
        this.validUser = validUser;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("isValid", isValid)
                .append("validAccountNumber", validAccountNumber)
                .append("validDateField", validDateField)
                .append("validAmount", validAmount)
                .append("validUser", validUser)
                .toString();
    }
}
