package com.cliftonmcintosh.creditcard;


import com.cliftonmcintosh.creditcard.accounts.Account;
import com.cliftonmcintosh.creditcard.accounts.AccountService;
import com.cliftonmcintosh.creditcard.accounts.CreditCardAccountService;
import com.cliftonmcintosh.creditcard.errors.AccountErrorService;
import com.cliftonmcintosh.creditcard.errors.ErrorService;
import com.cliftonmcintosh.creditcard.validation.AccountValidationService;
import com.cliftonmcintosh.creditcard.validation.CreditCardAccountValidationService;
import com.google.common.base.Preconditions;

import java.util.Set;

public class Application  {
    
    private final AccountService accountService;


    public static void main(String[] args) {
        AccountValidationService validationService = new CreditCardAccountValidationService();
        ErrorService errorService = new AccountErrorService();
        AccountService anAccountService = new CreditCardAccountService(validationService, errorService);
        Application application = new Application(anAccountService);
        application.run(args);
    }

    public Application(AccountService accountService) {
        this.accountService = Preconditions.checkNotNull(accountService, "The account service cannot be null");
    }

    public void run(String... args) {
        
        accountService.createAccount("Aizpea", "79927398713", "$1000");
        accountService.createAccount("Astoria", "5454545454545454", "$10000");
        accountService.createAccount("Alvaro", "1234567890123456", "$10000");
        Set<Account> accounts = accountService.getAccounts();

        accounts.forEach(account -> System.out.println(String.format("Account Name: %s, Number: %s, Limit: %d, Balance: %d",
                account.getName(), account.getAccountNumber(), account.getLimit(), account.getBalance())));
    }
}
