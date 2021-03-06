package com.cliftonmcintosh.creditcard.validation

import spock.lang.Specification
import spock.lang.Unroll


/**
 * A specification for CreditCardAccountValidationService
 * Created by cmcintosh on 9/19/15.
 */
class CreditCardAccountValidationServiceSpec extends Specification {

    private CreditCardAccountValidationService service = new CreditCardAccountValidationService();

    @Unroll
    def 'validateAccountCreationRequest should return true when parameters are valid and false when they are invalid'() {
        when:
        def isValid = service.validateAccountCreationRequest(name, accountNumber, limit)

        then:
        isValid == expected

        where:
        name   | accountNumber          | limit   | expected
        'Mike' | '4111111111111111'     | '$4567' | true
        null   | '4111111111111111'     | '$4567' | false
        ''     | '4111111111111111'     | '$4567' | false
        ' '    | '4111111111111111'     | '$4567' | false
        'Mike' | '4111111111111112'     | '$4567' | false
        'Mike' | null                   | '$4567' | false
        'Mike' | ''                     | '$4567' | false
        'Mike' | '411111111111111A'     | '$4567' | false
        'Mike' | '12345678912345678918' | '$4567' | false
        'Mike' | '4111111111111111'     | '4567'  | false
        'Mike' | '4111111111111111'     | '$D567' | false
        'Mike' | '4111111111111111'     | ''      | false
        'Mike' | '4111111111111111'     | null    | false
    }

    @Unroll
    def 'validateAccountChargeRequest should return true when the name exists and the amount is correctly formatted'() {
        when:
        def isValid = service.validateAccountChargeRequest(name, charge)

        then:
        isValid == expected

        where:
        name | charge | expected
        'A'  | '$1'   | true
        null | '$1'   | false
        ''   | '$1'   | false
        ' '  | '$1'   | false
        'A'  | '$A'   | false
    }

    @Unroll
    def 'validateAccountCreditRequest should return true when the name exists and the amount is correctly formatted'() {
        when:
        def isValid = service.validateAccountCreditRequest(name, credit)

        then:
        isValid == expected

        where:
        name | credit | expected
        'A'  | '$1'   | true
        null | '$1'   | false
        ''   | '$1'   | false
        ' '  | '$1'   | false
        'A'  | '$A'   | false
    }

}