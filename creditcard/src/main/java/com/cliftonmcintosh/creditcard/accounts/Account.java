package com.cliftonmcintosh.creditcard.accounts;

import com.google.common.base.Preconditions;

/**
 * Created by cmcintosh on 9/19/15.
 */
public class Account {

    private final String name;

    private final String accountNumber;

    private final int limit;

    private int balance;

    public Account(String name, String accountNumber, int limit) {
        Preconditions.checkArgument(name != null && !name.trim().isEmpty(), "The name cannot be empty.");
        Preconditions.checkArgument(accountNumber != null && !accountNumber.trim().isEmpty(), "The accountNumber cannot be empty.");
        Preconditions.checkArgument(limit >= 0, "The account limit cannot be less than zero.");
        this.name = name;
        this.accountNumber = accountNumber;
        this.limit = limit;
        this.balance = 0;
    }

    public String getName() {
        return name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public int getLimit() {
        return limit;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void charge(int charge) {
        if (charge > 0) {
            balance = (balance + charge) <= limit ? (balance + charge) : balance;
        }
    }

    public void credit(int credit) {
        if (credit > 0) {
            balance -= credit;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Account)) return false;

        Account account = (Account) other;
        return name.equals(account.name);
    }

    @Override
    public int hashCode() {
        return 31 * name.hashCode();
    }
}
