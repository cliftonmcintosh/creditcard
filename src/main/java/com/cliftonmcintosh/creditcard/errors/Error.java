package com.cliftonmcintosh.creditcard.errors;

import com.google.common.base.Preconditions;

/**
 * Created by cmcintosh on 9/19/15.
 */
public class Error {

    private final String name;

    private final String details;

    public Error(String name, String details) {
        Preconditions.checkArgument(name != null && !name.trim().isEmpty(), "The name of the error cannot be an empty.");
        Preconditions.checkArgument(details != null && !details.trim().isEmpty(), "The details for the error cannot be empty.");
        this.name = name;
        this.details = details;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Error)) return false;

        Error error = (Error) o;

        return name.equals(error.name) && details.equals(error.details);

    }

    @Override
    public int hashCode() {
        return 31 * name.hashCode() + (details.hashCode());
    }
}
