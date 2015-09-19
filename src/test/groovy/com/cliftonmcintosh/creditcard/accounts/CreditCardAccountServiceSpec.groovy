package com.cliftonmcintosh.creditcard.accounts

import com.cliftonmcintosh.creditcard.errors.ErrorService
import com.cliftonmcintosh.creditcard.validation.AccountValidationService
import spock.lang.Specification
import spock.lang.Unroll


/**
 * Created by cmcintosh on 9/19/15.
 */
class CreditCardAccountServiceSpec extends Specification {

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
        def name = 'Name'
        def accountNumber = 'ABC'
        def limit = 'DEF'

        when:
        service.createAccount(name, accountNumber, limit)

        then:
        1 * validationService.validateAccountCreationRequest(name, accountNumber, limit) >> false
        1 * errorService.saveError(_)
        service.getAccounts().size() == 0
    }

    def 'createAccount should save an account if the data is valid'() {
        given:
        def name = 'Name'
        def accountNumber = '79927398713'
        def limit = '$1'

        when:
        service.createAccount(name, accountNumber, limit)

        then:
        1 * validationService.validateAccountCreationRequest(name, accountNumber, limit) >> true
        0 * errorService.saveError(_)
        service.getAccounts().size() == 1
    }

    def 'createAccount should not save an account if an account with the same name already exists'() {
        given:
        def name = 'Name'
        def firstAccountNumber = '79927398713'
        def secondAccountNumber = '4111111111111111'
        def limit = '$1'

        when:
        service.createAccount(name, firstAccountNumber, limit)
        service.createAccount(name, secondAccountNumber, limit)

        then:
        2 * validationService.validateAccountCreationRequest(name, _, limit) >> true
        0 * errorService.saveError(_)
        Set allAccounts = service.getAccounts()
        allAccounts.size() == 1
        allAccounts.findAll { it.name == name }.size() == 1
        allAccounts.findAll { it.accountNumber == firstAccountNumber }.size() == 1
        allAccounts.findAll { it.accountNumber == secondAccountNumber }.size() == 0
    }

}