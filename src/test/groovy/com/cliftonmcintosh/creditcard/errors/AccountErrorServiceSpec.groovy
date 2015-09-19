package com.cliftonmcintosh.creditcard.errors

import spock.lang.Specification


/**
 * Created by cmcintosh on 9/19/15.
 */
class AccountErrorServiceSpec extends Specification {

    private AccountErrorService service

    def setup() {
        service = new AccountErrorService()
    }

    def 'saveError should add an error to the errors that are saved'() {
        given:
        def firstError = new Error('Ellen', 'invalid account number')
        def secondError = new Error('Dave', 'invalid account number')

        when:
        service.saveError(firstError)
        service.saveError(secondError)
        def savedErrors = service.getErrors()

        then:
        savedErrors
        savedErrors.size() == 2
        savedErrors.contains(firstError)
        savedErrors.contains(secondError)
    }

    def 'saveError should not add a duplicate error to the errors that are saved'() {
        given:
        def firstError = new Error('Ellen', 'invalid account number')
        def secondError = new Error('Ellen', 'invalid account number')

        when:
        service.saveError(firstError)
        service.saveError(secondError)
        def savedErrors = service.getErrors()

        then:
        savedErrors
        savedErrors.size() == 1
        savedErrors.contains(firstError)
    }



}