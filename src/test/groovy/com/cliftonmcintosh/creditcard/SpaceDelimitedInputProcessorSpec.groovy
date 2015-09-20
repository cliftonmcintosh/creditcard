package com.cliftonmcintosh.creditcard

import com.cliftonmcintosh.creditcard.input.SpaceDelimitedInputProcessor
import spock.lang.Specification


/**
 * Created by cmcintosh on 9/20/15.
 */
class SpaceDelimitedInputProcessorSpec extends Specification {

    private SpaceDelimitedInputProcessor process = new SpaceDelimitedInputProcessor()

    def 'processRawInput should convert a space-delimited string into a List without any surround whitespaces and without any null or empty elements'() {
        when:
        List<String> output = process.processRawInput(input)

        then:
        output != null
        output == expected

        where:
        input                               | expected
        ''                                  | []
        'Add'                               | ['Add']
        'Add Tom 4111111111111111 $1000'    | ['Add', 'Tom', '4111111111111111', '$1000']
        'Add  Tom  4111111111111111  $1000' | ['Add', 'Tom', '4111111111111111', '$1000']
        ' Add Tom 4111111111111111 $1000 '  | ['Add', 'Tom', '4111111111111111', '$1000']
    }

}