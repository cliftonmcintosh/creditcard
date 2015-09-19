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

    @Unroll
    def 'the constructor should not allow a null or empty name nor a limit less than zero'() {
        when:
        new Account(name, limit)

        then:
        thrown(IllegalArgumentException)

        where:
        name  | limit
        null  | 0
        ''    | 0
        ' '   | 1
        ALICE | -1
    }

    def 'the account should be initialized using the parameters'() {
        when:
        def account = new Account(ALICE, 1)

        then:
        account.name == ALICE
        account.limit == 1
    }

    def 'the account balance should be initialized to zero'() {
        when:
        def account = new Account(ALICE, 1)

        then:
        account.balance == 0
    }

}