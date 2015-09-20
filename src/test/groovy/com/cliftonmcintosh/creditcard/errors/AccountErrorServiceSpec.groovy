package com.cliftonmcintosh.creditcard.errors

import spock.lang.Specification


/**
 * Created by cmcintosh on 9/19/15.
 */
class AccountErrorServiceSpec extends Specification {

    private static final INVALID_ACCOUNT_NUMBER = 'invalid account number'

    private static final ELLEN = 'Ellen'

    private static final DAVE = 'Dave'

    private AccountErrorService service

    def setup() {
        service = new AccountErrorService()
    }

    def 'saveError should add an error to the errors that are saved'() {
        given:
        def firstError = new AccountError(ELLEN, INVALID_ACCOUNT_NUMBER)
        def secondError = new AccountError(DAVE, INVALID_ACCOUNT_NUMBER)

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
        def firstError = new AccountError(ELLEN, INVALID_ACCOUNT_NUMBER)
        def secondError = new AccountError(ELLEN, INVALID_ACCOUNT_NUMBER)

        when:
        service.saveError(firstError)
        service.saveError(secondError)
        def savedErrors = service.getErrors()

        then:
        savedErrors
        savedErrors.size() == 1
        savedErrors.contains(firstError)
    }

    def 'removeErrorForName should remove an error whose name matches the parameter'() {
        given:
        def firstError = new AccountError(ELLEN, INVALID_ACCOUNT_NUMBER)
        def secondError = new AccountError(DAVE, INVALID_ACCOUNT_NUMBER)
        service.saveError(firstError)
        service.saveError(secondError)

        when:
        service.removeErrorForName(ELLEN)
        def savedErrors = service.getErrors()

        then:
        savedErrors
        savedErrors.size() == 1
        !savedErrors.contains(firstError)
        savedErrors.contains(secondError)

    }



}