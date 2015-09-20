package com.cliftonmcintosh.creditcard.accounts

import com.cliftonmcintosh.creditcard.errors.ErrorService
import com.cliftonmcintosh.creditcard.validation.AccountValidationService
import spock.lang.Specification
import spock.lang.Unroll


/**
 * Created by cmcintosh on 9/19/15.
 */
class CreditCardAccountServiceSpec extends Specification {

    private static final NAME = 'Name'

    private static final ACCOUNT_NUMBER = '79927398713'

    private static final ONE_DOLLAR = '$1'

    private static final ANOTHER_VALID_ACCOUNT_NUMBER = '4111111111111111'

    private static final ABC = 'ABC'

    private CreditCardAccountService service;

    private AccountValidationService validationService;

    private ErrorService errorService;

    def setup() {
        validationService = Mock(AccountValidationService)
        errorService = Mock(ErrorService)
        service = new CreditCardAccountService(validationService, errorService)
    }

    @Unroll
    def 'constructor should not allow null parameters'() {
        when:
        new CreditCardAccountService(vService, eService)

        then:
        thrown(NullPointerException)

        where:
        vService                       | eService
        null                           | Mock(ErrorService)
        Mock(AccountValidationService) | null
    }

    def 'createAccount should not save an account but should save an error if the data is invalid'() {
        given:
        def name = NAME
        def accountNumber = ABC
        def limit = 'DEF'

        when:
        service.createAccount(name, accountNumber, limit)

        then:
        1 * validationService.validateAccountCreationRequest(name, accountNumber, limit) >> false
        1 * errorService.saveError(_)
        service.getAccounts().size() == 0
    }

    def 'createAccount should not create an error if the data is invalid but an account with the same name already exists'() {
        given:
        def name = NAME
        def validAccountNumber = ACCOUNT_NUMBER
        def limit = ONE_DOLLAR
        def invalidAccountNumber = ABC

        when:
        service.createAccount(name, validAccountNumber, limit)
        service.createAccount(name, invalidAccountNumber, limit)

        then:
        1 * validationService.validateAccountCreationRequest(name, validAccountNumber, limit) >> true
        0 * validationService.validateAccountCreationRequest(name, invalidAccountNumber, limit)
        0 * errorService.saveError(_)
        service.getAccounts().size() == 1
    }

    def 'createAccount should save an account if the data is valid'() {
        given:
        def name = NAME
        def accountNumber = ACCOUNT_NUMBER
        def limit = ONE_DOLLAR

        when:
        service.createAccount(name, accountNumber, limit)

        then:
        1 * validationService.validateAccountCreationRequest(name, accountNumber, limit) >> true
        0 * errorService.saveError(_)
        service.getAccounts().size() == 1
    }

    def 'createAccount should request that any existing error be removed when an account is successfully created'() {
        given:
        def name = NAME
        def validAccountNumber = ANOTHER_VALID_ACCOUNT_NUMBER
        def validLimit = '$1000'

        when:
        service.createAccount(name, validAccountNumber, validLimit)

        then:
        1 * validationService.validateAccountCreationRequest(name, validAccountNumber, validLimit) >> true
        1 * errorService.removeErrorForName(name)
    }

    def 'createAccount should not save an account if an account with the same name already exists'() {
        given:
        def name = NAME
        def firstAccountNumber = ACCOUNT_NUMBER
        def secondAccountNumber = ANOTHER_VALID_ACCOUNT_NUMBER
        def limit = ONE_DOLLAR

        when:
        service.createAccount(name, firstAccountNumber, limit)
        service.createAccount(name, secondAccountNumber, limit)

        then:
        1 * validationService.validateAccountCreationRequest(name, _, limit) >> true
        0 * errorService.saveError(_)
        Set allAccounts = service.getAccounts()
        allAccounts.size() == 1
        allAccounts.findAll { it.name == name }.size() == 1
        allAccounts.findAll { it.accountNumber == firstAccountNumber }.size() == 1
        allAccounts.findAll { it.accountNumber == secondAccountNumber }.size() == 0
    }

}