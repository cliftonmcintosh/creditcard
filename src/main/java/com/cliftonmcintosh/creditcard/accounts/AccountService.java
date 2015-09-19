package com.cliftonmcintosh.creditcard.accounts;

import java.util.Set;

/**
 * Created by cmcintosh on 9/19/15.
 */
public interface AccountService {

    void createAccount(String name, String accountNumber, String limit);

    Set<Account> getAccounts();
}
