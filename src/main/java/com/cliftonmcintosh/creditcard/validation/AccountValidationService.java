package com.cliftonmcintosh.creditcard.validation;

/**
 * Created by cmcintosh on 9/19/15.
 */
public interface AccountValidationService {

    boolean validateAccountCreationRequest(String name, String accountNumber, String limit);

    boolean validateAccountChargeRequest(String name, int charge);

    boolean validateAccountCreditRequest(String name, int credit);
}
