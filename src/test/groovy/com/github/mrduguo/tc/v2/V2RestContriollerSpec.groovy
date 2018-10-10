
package com.github.mrduguo.tc.v2

import com.github.mrduguo.tc.AbstractSpec
import com.github.mrduguo.tc.optimizer.Answer
import org.springframework.http.HttpStatus
import spock.lang.Unroll

class V2RestContriollerSpec extends AbstractSpec {

    @Unroll
    def "test case ##testCaseName with colors #colors customers #customers"(String testCaseName, int colors, int customers,
                                                                            def demands, def expectedResult) {
        when:
        def payload = [colors: colors, customers: customers, demands: demands]
        def entity = postForEntity('/v2/', payload, Answer.class)

        then:
        entity.statusCode == HttpStatus.OK
        if (expectedResult) {
            assert entity.body.impossible == false
            assert entity.body.solution.join(' ') == expectedResult
        } else {
            assert entity.body.impossible == true
        }

        where:
        [testCaseName, colors, customers, demands, expectedResult] << loadTestCases()
    }


    def loadTestCases() {
        [
                ['impossible', 1, 2, [[1, 1, 0], [1, 1, 1]], null],
                ['no_matte', 2, 2, [[1, 1, 0], [1, 2, 0]], '0 0'],
                ['all_matte', 3, 3, [[1, 1, 1], [2, 1, 0, 2, 1], [3, 1, 0, 2, 0, 3, 1]], '1 1 1'],
                ['color_not_requested', 5, 2, [[1, 5, 1], [2, 1, 0, 2, 1]], '0 0 0 0 1'],
        ]
    }

}