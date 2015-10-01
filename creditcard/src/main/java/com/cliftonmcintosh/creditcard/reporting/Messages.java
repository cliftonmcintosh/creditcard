package com.cliftonmcintosh.creditcard.reporting;

import com.cliftonmcintosh.creditcard.errors.AccountError;
import com.cliftonmcintosh.creditcard.accounts.Account;


import java.util.Comparator;
import java.util.function.Function;

/**
 * Created by cmcintosh on 9/20/15.
 */
public final class Messages {

    private Messages() {
        throw new AssertionError("You cannot instantiate the Messages class.");
    }

    public static final Function<Account, Message> ACCOUNT_MESSAGE_FUNCTION = (Account account) -> new Message(account.getName(), "$" + account.getBalance());

    public static final Function<AccountError, Message> ERROR_MESSAGE_FUNCTION = (AccountError error) -> new Message(error.getName(), error.getDetails());

    public static final Comparator<Message> MESSAGE_HEADER_COMPARATOR = (Message message1, Message message2) -> message1.getHeader().compareTo(message2.getHeader());
}
