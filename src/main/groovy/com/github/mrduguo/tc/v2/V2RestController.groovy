package com.github.mrduguo.tc.v2

import com.github.mrduguo.tc.optimizer.PaintBatchOptimizer
import com.github.mrduguo.tc.optimizer.Problem
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class V2RestController {

    @Autowired
    PaintBatchOptimizer paintBatchOptimizer

    @RequestMapping('/v2/')
    def optimize(@RequestBody Problem problem) {
        paintBatchOptimizer.solve(problem)
    }

}
