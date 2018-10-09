package com.github.mrduguo.tc.v2

import com.github.mrduguo.tc.AbstractSpec
import com.github.mrduguo.tc.optimizer.Answer
import org.springframework.http.HttpStatus

class V2RestContriollerSpec extends AbstractSpec {

    void "impossible to solve"() {
        when:
        def entity = postForEntity('/v2/',
                [
                        colors   : 5,
                        customers: 3,
                        demands  : [[1, 1, 1], [2, 1, 0, 2, 0], [1, 5, 0]]
                ]
                , Answer.class)

        then:
        entity.statusCode == HttpStatus.OK
        entity.body.impossible == true
    }

}