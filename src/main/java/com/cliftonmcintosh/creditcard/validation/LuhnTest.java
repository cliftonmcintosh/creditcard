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
        return toSum < 10 ? toSum : 1 + (toSum - 10);
    }
}
