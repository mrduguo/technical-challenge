package com.github.mrduguo.gradle.sampleapp

import org.springframework.http.HttpStatus

class ProxySpec extends AbstractSpec {

    void "should contain rate limit from github api"() {
        when:
        def entity = getForEntity('/proxy/github/rate_limit', Map.class)

        then:
        entity.statusCode == HttpStatus.OK
        entity.body.rate.limit == 60
        entity.body.resources.search.limit == 10
    }

}