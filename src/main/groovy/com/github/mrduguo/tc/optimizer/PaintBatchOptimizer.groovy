
package com.github.mrduguo.tc.optimizer

import org.springframework.stereotype.Component

@Component
class PaintBatchOptimizer {

    Answer solve(Problem problem) {
        def mattes = []
        def glossy = [:]
        (0..<problem.customers).each { c ->
            mattes << []
            (0..<problem.demands[c][0]).each { i ->
                def color = problem.demands[c][2 * i + 1]
                def matte = problem.demands[c][2 * i + 2]
                if (matte == 1)
                    glossy[c] = color - 1
                else
                    mattes[c] << color - 1
            }
        }
        def (solved, solution) = start(problem.colors, problem.customers, mattes, glossy)
        new Answer(impossible: !solved, solution: solution)
    }

    def start(int colors, int customers, mattes, glossy) {
        def solution = [0] * colors
        if (check(solution, customers, mattes, glossy))
            return [true, solution]
        def result = null
        def solved = false
        (0..<colors).each { i ->
            if (solution[i] == 0) {
                def (solved_i, result_i) = reduce(solution, i, customers, mattes, glossy)
                if (solved_i) {
                    if (!solved) {
                        solved = true
                        result = result_i
                    }
                    if (result_i.sum() < result.sum()) {
                        result = result_i
                    }
                }

            }
        }
        return [solved, result]
    }


    def check(solution, customers, mattes, glossy) {
        for (int customer = 0; customer < customers; customer++) {
            def good = false
            (0..<solution.size()).each { i ->
                if (solution[i] == 0 && i in mattes[customer]) {
                    good = true
                }
                if (solution[i] == 1 && glossy.get(customer) == i) {
                    good = true
                }
            }
            if (!good) {
                return false
            }
        }
        true
    }


    def reduce(solution_on_stack, change, customers, mattes, glossy) {
        def solution = solution_on_stack.collect()
        solution[change] = 1
        if (check(solution, customers, mattes, glossy)) {
            return [true, solution]
        }
        if (solution.sum() == solution.size()) {
            return [false, null]
        }
        def result = null
        def solved = false
        (0..<solution.size()).each { i ->
            if (solution[i] == 0) {
                def (solved_i, result_i) = reduce(solution, i, customers, mattes, glossy)
                if (solved_i) {
                    if (!solved) {
                        solved = true
                        result = result_i
                    }
                    if (result_i.sum() < result.sum()) {
                        result = result_i
                    }
                }
            }
        }
        return [solved, result]
    }

}

