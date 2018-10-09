package com.github.mrduguo.tc

import groovy.json.JsonBuilder
import org.springframework.http.HttpStatus
import org.springframework.web.util.UriUtils
import spock.lang.Unroll

class DataFileBasedUseCasesSpec extends AbstractSpec {


    @Unroll
    def "test case ##testCaseId with colors #colors customers #customers"(int testCaseId, int colors, int customers,
                                                                          def demands, String expectedResult) {
        when:
        def payload = [colors: colors, customers: customers, demands: demands]
        def entity = getForEntity('/v1/?input=' + UriUtils.encodeQueryParam(new JsonBuilder(payload).toString(),'US-ASCII'), String.class)

        then:
        entity.statusCode == HttpStatus.OK
        entity.body == expectedResult


        where:
        [testCaseId, colors, customers, demands, expectedResult] << loadTestCases()
    }


    def loadTestCases() {
        def testCases = []
        def content = DataFileBasedUseCasesSpec.getResourceAsStream('/data/unit-tests.in.txt').readLines()
        def expectedResults = DataFileBasedUseCasesSpec.getResourceAsStream('/data/unit-tests.out.txt').readLines()
        def numberOfTestCases = content.remove(0).toInteger()
        (1..numberOfTestCases).each { testCaseId ->
            def testCase = [testCaseId, content.remove(0).toInteger(), content.remove(0).toInteger(), []]
            (1..testCase[2]).each { demand ->
                testCase[3] << content.remove(0).split(' ').collect { it.toInteger() }
            }
            testCase << expectedResults.remove(0).split(': ')[1]
            testCases << testCase
        }
        testCases
    }

}