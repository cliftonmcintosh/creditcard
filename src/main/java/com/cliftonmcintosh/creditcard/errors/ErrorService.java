package com.cliftonmcintosh.creditcard.errors;

import java.util.Set;

/**
 * Created by cmcintosh on 9/19/15.
 */
public interface ErrorService {

    public void saveError(AccountError error);

    public Set<AccountError> getErrors();
}
