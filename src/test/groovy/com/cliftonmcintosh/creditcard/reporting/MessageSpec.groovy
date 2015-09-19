package com.cliftonmcintosh.creditcard.reporting

import spock.lang.Specification
import spock.lang.Unroll


/**
 * A spec for Message.
 * Created by cmcintosh on 9/19/15.
 */
class MessageSpec extends Specification {


    private static String ONE_THOUSAND = '$1000'
    private static final BOB = 'Bob'

    @Unroll
    def 'the constructor should not allow null or empty parameters'() {
        when:
        new Message(header, details)

        then:
        thrown(IllegalArgumentException)

        where:
        header | details
        null   | ONE_THOUSAND
        ''     | ONE_THOUSAND
        ' '    | ONE_THOUSAND
        BOB    | null
        BOB    | ''
        BOB    | ' '
    }

    def 'the message should be initialized using the constructor parameters'() {
        when:
        def message = new Message(BOB, ONE_THOUSAND)

        then:
        message.header == BOB
        message.details == ONE_THOUSAND
    }

}