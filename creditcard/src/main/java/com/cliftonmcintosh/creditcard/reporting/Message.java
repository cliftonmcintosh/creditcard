package com.cliftonmcintosh.creditcard.reporting;

import com.google.common.base.Preconditions;

/**
 * Created by cmcintosh on 9/19/15.
 */
public class Message {

    private final String header;

    private final String details;

    public Message(String header, String details) {
        Preconditions.checkArgument(header != null && !header.trim().isEmpty(), "The header cannot be null or empty");
        Preconditions.checkArgument(details != null && !details.trim().isEmpty(), "The details cannot be null or empty");
        this.header = header;
        this.details = details;
    }

    public String getHeader() {
        return header;
    }

    public String getDetails() {
        return details;
    }
}
