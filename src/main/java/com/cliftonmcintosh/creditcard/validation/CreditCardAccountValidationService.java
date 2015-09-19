package com.cliftonmcintosh.creditcard.validation;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cmcintosh on 9/19/15.
 */
public class CreditCardAccountValidationService implements AccountValidationService {

    private static Pattern DIGITS_PATTERN = Pattern.compile("\\d+");

    private static Pattern LIMIT_FORMAT_PATTERN = Pattern.compile("^\\$[0-9]*");

    public boolean validateAccountCreationRequest(String name, String accountNumber, String limit) {
        return name != null && !name.trim().isEmpty() && ACCOUNT_NUMBER_CHECK.test(accountNumber) && LIMIT_CHECK.test(limit);
    }

    @Override
    public boolean validateAccountChargeRequest(String name, int charge) {
        return false;
    }

    @Override
    public boolean validateAccountCreditRequest(String name, int credit) {
        return false;
    }

    private static Predicate<String> IS_DIGITS_CHECK = (String input) -> {
        Matcher matcher = DIGITS_PATTERN.matcher(input);
        return matcher.matches();
    };

    private static Predicate<String> ACCOUNT_NUMBER_CHECK = (String input) -> input.length() < 20 && IS_DIGITS_CHECK.test(input) && LuhnTest.INSTANCE.test(input);

    private static Predicate<String> LIMIT_CHECK = (String input) -> {
        Matcher matcher = LIMIT_FORMAT_PATTERN.matcher(input);
        return matcher.matches();
    };
}
