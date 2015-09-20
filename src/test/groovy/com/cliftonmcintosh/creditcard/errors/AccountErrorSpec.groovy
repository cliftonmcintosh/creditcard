package com.cliftonmcintosh.creditcard.errors

import spock.lang.Specification
import spock.lang.Unroll


/**
 * A specification for Error.
 *
 * Created by cmcintosh on 9/19/15.
 */
class AccountErrorSpec extends Specification {


    private static final ALICE = 'Alice'
    private static final ERROR = 'Error'

    @Unroll
    def 'the constructor should not allow null or empty parameters'() {
        when:
        new AccountError(name, error);

        then:
        thrown(IllegalArgumentException)

        where:
        name  | error
        null  | ERROR
        ''    | ERROR
        ' '   | ERROR
        ALICE | null
        ALICE | ''
        ALICE | ' '
    }

    def 'the error should be initialized with the constructor parameters'() {
        when:
        def error = new AccountError(ALICE, ERROR)

        then:
        error.name == ALICE
        error.details == ERROR
    }

    def 'two errors with the same values should be equal'() {
        when:
        def firstError = new AccountError(ALICE, ERROR)
        def secondError = new AccountError(ALICE, ERROR)

        then:
        firstError == secondError
        firstError == firstError
    }

    def 'two errors with different values should not be equal'() {
        when:
        def firstError = new AccountError(ALICE, ERROR)
        def secondError = new AccountError('alice', ERROR)
        def thirdError = new AccountError(ALICE, 'error')

        then:
        firstError != secondError
        firstError != thirdError
        secondError != thirdError
    }

    def 'two errors with the same values should have the same hash code'() {
        when:
        def firstError = new AccountError(ALICE, ERROR)
        def secondError = new AccountError(ALICE, ERROR)

        then:
        firstError.hashCode() == secondError.hashCode()
    }

    def 'two errors with different values should different hash codes'() {
        when:
        def firstError = new AccountError(ALICE, ERROR)
        def secondError = new AccountError('alice', ERROR)

        then:
        firstError.hashCode() != secondError.hashCode()
    }

}