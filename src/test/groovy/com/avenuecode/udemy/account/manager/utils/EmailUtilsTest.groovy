package com.avenuecode.udemy.account.manager.utils

import spock.lang.Specification
import spock.lang.Unroll

class EmailUtilsTest extends Specification{

    @Unroll
    def "test mask email"() {
        when:
        def maskedEmail = EmailUtils.maskEmail(email)

        then:
        maskedEmail == result

        where:
        email                               |  result
        "michel.fonseca@avenuecode.com"     |  "m..a@avenuecode.com"
        "abc@avenuecode.com"                |  "a..@avenuecode.com"
        "ab@avenuecode.com"                 |  "..@avenuecode.com"
        "notValidEmailFormat"               |  "notValidEmailFormat"
    }
}
