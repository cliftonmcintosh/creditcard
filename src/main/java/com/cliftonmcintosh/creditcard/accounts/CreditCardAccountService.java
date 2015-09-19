package com.cliftonmcintosh.creditcard.accounts;

import com.cliftonmcintosh.creditcard.errors.*;
import com.cliftonmcintosh.creditcard.errors.Error;
import com.cliftonmcintosh.creditcard.validation.AccountValidationService;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by cmcintosh on 9/19/15.
 */
public class CreditCardAccountService implements AccountService {

    private static final String ERROR_DETAILS = "Error";

    private final AccountValidationService validationService;

    private final ErrorService errorService;

    private final Set<Account> accounts;

    public CreditCardAccountService(AccountValidationService validationService, ErrorService errorService) {
        this.validationService = Preconditions.checkNotNull(validationService, "The validation service cannot be null");
        this.errorService = Preconditions.checkNotNull(errorService, "The error service cannot be null");
        accounts = new HashSet<>();
    }


    @Override
    public void createAccount(String name, String accountNumber, String limit) {
        if (validationService.validateAccountCreationRequest(name, accountNumber, limit)) {
            Account newAccount = new Account(name, accountNumber, createLimitFromInput(limit));
            if (accounts.stream().noneMatch(account -> account.getName().equals(newAccount.getName()))) {
                accounts.add(newAccount);
            }
        } else {
            errorService.saveError(new Error(name, ERROR_DETAILS));
        }
    }

    @Override
    public Set<Account> getAccounts() {
        return ImmutableSet.copyOf(accounts);
    }

    private int createLimitFromInput(String input) {
        return Integer.valueOf(input.replaceAll("\\$", ""));
    }
}
