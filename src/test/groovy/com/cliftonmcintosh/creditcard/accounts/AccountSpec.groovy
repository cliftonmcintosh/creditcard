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

}