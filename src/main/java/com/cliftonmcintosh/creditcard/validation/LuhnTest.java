package com.cliftonmcintosh.creditcard.validation;


/**
 * Created by cmcintosh on 9/19/15.
 */
public enum LuhnTest {

    INSTANCE;

    public boolean test(String input) {
        String reversed = new StringBuilder(input).reverse().toString();
        int sum = 0;

        for (int index = 0; index < reversed.length(); index++) {
            int currentNumber = Character.getNumericValue(reversed.charAt(index));
            if (index % 2 == 1) {
                sum += summedDigits(currentNumber * 2);
            } else {
                sum += currentNumber;
            }
        }
        return sum % 10 == 0;
    }

    private int summedDigits(int toSum) {
        final int summed;
        if (toSum < 10) {
            summed = toSum;
        } else {
            String digits = Integer.toString(toSum);
            summed = Character.getNumericValue(digits.charAt(0)) + Character.getNumericValue(digits.charAt(1));
        }
        return summed;
    }
}
