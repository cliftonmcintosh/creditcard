package com.cliftonmcintosh.creditcard.accounts

import spock.lang.Specification
import spock.lang.Unroll


/**
 * A specification for Account.
 *
 * Created by cmcintosh on 9/19/15.
 */
class AccountSpec extends Specification {

    private static ALICE = 'Alice'

    private static ACCOUNT_NUMBER = '4111111111111111'

    private static ANOTHER_ACCOUNT_NUMBER = '79927398713'

    @Unroll
    def 'the constructor should not allow a null or empty name or account number nor a limit less than zero'() {
        when:
        new Account(name, accountNumber, limit)

        then:
        thrown(IllegalArgumentException)

        where:
        name  | accountNumber  | limit
        null  | ACCOUNT_NUMBER | 0
        ''    | ACCOUNT_NUMBER | 0
        ' '   | ACCOUNT_NUMBER | 1
        ALICE | ACCOUNT_NUMBER | -1
        ALICE | null           | 0
        ALICE | ''             | 0
        ALICE | ' '            | 0
    }

    def 'the account should be initialized using the parameters'() {
        when:
        def account = new Account(ALICE, ACCOUNT_NUMBER, 1)

        then:
        account.name == ALICE
        account.limit == 1
    }

    def 'the account balance should be initialized to zero'() {
        when:
        def account = new Account(ALICE, ACCOUNT_NUMBER, 1)

        then:
        account.balance == 0
    }

    def 'two accounts with the same name should be equal'() {
        when:
        def firstAccount = new Account(ALICE, ACCOUNT_NUMBER, 1)
        def secondAccount = new Account(ALICE, ANOTHER_ACCOUNT_NUMBER, 1)

        then:
        firstAccount == secondAccount
        firstAccount == firstAccount
    }

    def 'two accounts with different names should not be equal'() {
        when:
        def firstAccount = new Account(ALICE, ACCOUNT_NUMBER, 1)
        def secondAccount = new Account('alice', ACCOUNT_NUMBER, 1)

        then:
        firstAccount != secondAccount
    }

    def 'two accounts with the same names should have the same hash code'() {
        when:
        def firstAccount = new Account(ALICE, ACCOUNT_NUMBER, 1)
        def secondAccount = new Account(ALICE, ANOTHER_ACCOUNT_NUMBER, 1)

        then:
        firstAccount.hashCode() == secondAccount.hashCode()
    }

    def 'two accounts with different names should different hash codes'() {
        when:
        def firstAccount = new Account(ALICE, ACCOUNT_NUMBER, 1)
        def secondAccount = new Account('alice', ACCOUNT_NUMBER, 1)

        then:
        firstAccount.hashCode() != secondAccount.hashCode()
    }

    def 'credit should decrease the balance as long as the credit amount is positive'() {
        given:
        def account = new Account(ALICE, ACCOUNT_NUMBER, 1)

        when:
        account.credit(amountToCredit)

        then:
        account.balance == expectedBalance

        where:
        amountToCredit | expectedBalance
        1              | -1
        0              | 0
        -1             | 0
    }

    def 'charge should increase the balance as long as the credit amount is positive and the limit has not yet been reached'() {
        given:
        def account = new Account(ALICE, ACCOUNT_NUMBER, 1)

        when:
        account.charge(amountToCharge)

        then:
        account.balance == expectedBalance

        where:
        amountToCharge | expectedBalance
        1              | 1
        0              | 0
        -1             | 0
        2              | 0
    }

}