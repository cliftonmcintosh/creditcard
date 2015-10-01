package com.cliftonmcintosh.creditcard.validation;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cmcintosh on 9/19/15.
 */
public class CreditCardAccountValidationService implements AccountValidationService {

    private static Pattern DIGITS_PATTERN = Pattern.compile("\\d+");

    private static Pattern MONEY_INPUT_FORMAT_PATTERN = Pattern.compile("^\\$[0-9]*");

    private static int MAXIMUM_ACCOUNT_NUMBER_LENGTH = 19;

    public boolean validateAccountCreationRequest(String name, String accountNumber, String limit) {
        return name != null && !name.trim().isEmpty() && ACCOUNT_NUMBER_CHECK.test(accountNumber) && MONEY_INPUT_CHECK.test(limit);
    }

    @Override
    public boolean validateAccountChargeRequest(String name, String charge) {
        return validateChargeAndCreditRequests(name, charge);
    }

    @Override
    public boolean validateAccountCreditRequest(String name, String credit) {
        return validateChargeAndCreditRequests(name, credit);
    }

    private static Predicate<String> ACCOUNT_NUMBER_LENGTH_CHECK = (String input) -> input != null && input.length() <= MAXIMUM_ACCOUNT_NUMBER_LENGTH;

    private static Predicate<String> IS_DIGITS_CHECK = (String input) -> {
        Matcher matcher = DIGITS_PATTERN.matcher(input);
        return matcher.matches();
    };

    private static Predicate<String> ACCOUNT_NUMBER_CHECK = (String input) -> ACCOUNT_NUMBER_LENGTH_CHECK.and(IS_DIGITS_CHECK).and(LuhnTest.INSTANCE::test).test(input);

    private static Predicate<String> MONEY_INPUT_CHECK = (String input) -> {
        boolean valid = input != null && !input.isEmpty();
        if (valid) {
            Matcher matcher = MONEY_INPUT_FORMAT_PATTERN.matcher(input);
            valid = matcher.matches();
        }
        return valid;
    };

    private boolean validateChargeAndCreditRequests(String name, String money) {
        return name != null && !name.trim().isEmpty() && MONEY_INPUT_CHECK.test(money);
    }
}
