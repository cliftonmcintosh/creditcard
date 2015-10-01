package com.cliftonmcintosh.creditcard.reporting

import com.cliftonmcintosh.creditcard.errors.AccountError
import com.cliftonmcintosh.creditcard.accounts.Account
import spock.lang.Specification
import spock.lang.Unroll


/**
 * Created by cmcintosh on 9/20/15.
 */
class MessagesSpec extends Specification {

    def 'ACCOUNT_MESSAGE_FUNCTION should create a message with the account name and balance'() {
        given:
        def account = new Account('Allegra', '79927398713', 1000)
        account.balance = 50

        when:
        def message = Messages.ACCOUNT_MESSAGE_FUNCTION.apply(account)

        then:
        message
        message.header == account.name
        message.details == new String("\$${account.balance}")
    }

    def 'ERROR_MESSAGE_FUNCTION should create a message with the erro name and details'() {
        given:
        def error = new AccountError('Faizi', 'error')

        when:
        def message = Messages.ERROR_MESSAGE_FUNCTION.apply(error)

        then:
        message
        message.header == error.name
        message.details == error.details
    }

    @Unroll
    def 'MESSAGE_HEADER_COMPARATOR should sort messages alphabetically based on the header'() {
        given:
        Comparator<Message> comparator = Messages.MESSAGE_HEADER_COMPARATOR

        when:
        int result = comparator.compare(first, second)

        then:
        result == expected

        where:
        first | second | expected
        new Message('A', 'z') | new Message('B', 'z') | -1
        new Message('A', 'z') | new Message('A', 'y') | 0
        new Message('B', 'z') | new Message('A', 'z') | 1
    }

}