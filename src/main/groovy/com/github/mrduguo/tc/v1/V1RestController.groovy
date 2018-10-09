package com.github.mrduguo.tc.v1

import com.github.mrduguo.tc.optimizer.Answer
import com.github.mrduguo.tc.optimizer.PaintBatchOptimizer
import com.github.mrduguo.tc.optimizer.Problem
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriUtils

@RestController
public class V1RestController {

    @Autowired
    PaintBatchOptimizer paintBatchOptimizer

    @RequestMapping('/v1/')
    def optimize(@RequestParam('input') String input) {
        Problem problem = parseProblem(input)
        def answer=paintBatchOptimizer.solve(problem)
        renderAnswer(answer)
    }

    String renderAnswer(Answer answer) {
        if (answer.isImpossible()) {
            'IMPOSSIBLE'
        } else {
            answer.solution.join(' ')
        }
    }

    Problem parseProblem(String input) {
        input = fixEncodingForTestOnly(input)
        def problem = new Problem(new JsonSlurper().parseText(input))
        problem
    }

    def fixEncodingForTestOnly(String input) {
        UriUtils.decode(input, 'US-ASCII')
    }

}
