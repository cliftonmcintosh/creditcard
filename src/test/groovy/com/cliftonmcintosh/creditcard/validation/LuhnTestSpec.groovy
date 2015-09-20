package com.cliftonmcintosh.creditcard.validation

import spock.lang.Specification
import spock.lang.Unroll


/**
 * Created by cmcintosh on 9/19/15.
 */
class LuhnTestSpec extends Specification {

    @Unroll
    def 'test should pass for a valid number and fail for invalid ones'() {

        when:
        def validation = LuhnTest.INSTANCE.test(input)

        then:
        validation == expected

        where:
        input                  | expected
        '1'                    | false
        '79927398710'          | false
        '79927398711'          | false
        '79927398712'          | false
        '79927398713'          | true
        '79927398714'          | false
        '79927398715'          | false
        '79927398716'          | false
        '79927398717'          | false
        '79927398718'          | false
        '79927398719'          | false
        '1234567890123456'     | false
        '4111111111111111'     | true
        '5454545454545454'     | true
        '12345678912345678918' | true
        '0'                    | true
        '00000000000'          | true
    }

}