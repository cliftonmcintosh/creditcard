package com.cliftonmcintosh.creditcard.errors;

import com.google.common.collect.ImmutableSet;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by cmcintosh on 9/19/15.
 */
public class AccountErrorService implements ErrorService {

    private final Set<AccountError> errors;

    public AccountErrorService() {
        errors = new HashSet<>();
    }

    @Override
    public void saveError(AccountError error) {
        if (!errors.contains(error)) {
            errors.add(error);
        }
    }

    @Override
    public void removeErrorForName(String name) {
        Optional<AccountError> existingErrorForName = errors.stream().filter(error -> name.equals(error.getName())).findFirst();
        if (existingErrorForName.isPresent()) {
            errors.remove(existingErrorForName.get());
        }
    }

    @Override
    public Set<AccountError> getErrors() {
        return ImmutableSet.copyOf(errors);
    }
}
