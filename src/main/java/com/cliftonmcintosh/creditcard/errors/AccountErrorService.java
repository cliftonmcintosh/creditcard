package com.cliftonmcintosh.creditcard.errors;

import com.google.common.collect.ImmutableSet;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by cmcintosh on 9/19/15.
 */
public class AccountErrorService implements ErrorService {

    private final Set<Error> errors;

    public AccountErrorService() {
        errors = new HashSet<>();
    }

    @Override
    public void saveError(Error error) {
        if (!errors.contains(error)) {
            errors.add(error);
        }
    }

    @Override
    public Set<Error> getErrors() {
        return ImmutableSet.copyOf(errors);
    }
}
