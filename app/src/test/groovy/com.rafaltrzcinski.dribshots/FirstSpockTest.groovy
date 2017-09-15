package com.rafaltrzcinski.dribshots

import spock.lang.Specification

class FirstSpockTest extends Specification {

    def "first test"() {
        expect:
        1 + 1 == 2
    }
}