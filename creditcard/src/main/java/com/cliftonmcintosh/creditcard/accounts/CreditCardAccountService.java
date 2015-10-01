package com.cliftonmcintosh.creditcard.accounts;

import com.cliftonmcintosh.creditcard.errors.AccountError;
import com.cliftonmcintosh.creditcard.errors.ErrorService;
import com.cliftonmcintosh.creditcard.validation.AccountValidationService;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * Created by cmcintosh on 9/19/15.
 */
public class CreditCardAccountService implements AccountService {

    private static final String ERROR_DETAILS = "error";

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
        if (accounts.stream().noneMatch(account -> account.getName().equals(name))) {
            if (validationService.validateAccountCreationRequest(name, accountNumber, limit)) {
                Account newAccount = new Account(name, accountNumber, createIntegerFromDollarPrefixedInput(limit));
                accounts.add(newAccount);
                errorService.removeErrorForName(name);
            } else {
                errorService.saveError(new AccountError(name, ERROR_DETAILS));
            }
        }
    }

    @Override
    public Set<Account> getAccounts() {
        return ImmutableSet.copyOf(accounts);
    }

    @Override
    public void chargeAccount(String accountName, String amount) {
        final BiConsumer<Account, String> doAccountUpdate = (Account account, String amt) -> account.charge(createIntegerFromDollarPrefixedInput(amt));
        if (validationService.validateAccountCreditRequest(accountName, amount)) {
            updateAccount(accountName, amount, doAccountUpdate);
        }
    }

    @Override
    public void creditAccount(String accountName, String amount) {
        final BiConsumer<Account, String> doAccountUpdate = (Account account, String amt) -> account.credit(createIntegerFromDollarPrefixedInput(amt));
        if (validationService.validateAccountCreditRequest(accountName, amount)) {
            updateAccount(accountName, amount, doAccountUpdate);
        }
    }

    private int createIntegerFromDollarPrefixedInput(String input) {
        return Integer.valueOf(input.replaceAll("\\$", ""));
    }

    private void updateAccount(String accountName, String amount, BiConsumer<Account, String> accountUpdate) {
        Optional<Account> accountByName = accounts.stream().filter(account -> account.getName().equals(accountName)).findFirst();
        if (accountByName.isPresent()) {
            accountUpdate.accept(accountByName.get(), amount);
        }
    }
}
