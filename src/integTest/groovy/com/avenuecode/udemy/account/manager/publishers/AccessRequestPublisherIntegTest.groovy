package com.avenuecode.udemy.account.manager.publishers

import com.avenuecode.udemy.account.manager.AbstractIntegTest
import com.avenuecode.udemy.account.manager.repository.RequestRepository
import com.avenuecode.udemy.account.manager.repository.domain.Request
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jms.core.JmsTemplate
import org.springframework.test.context.TestPropertySource

@TestPropertySource(properties = "avenue-code.udemy.jms.accessRequest.scheduler.enabled=true")
class AccessRequestPublisherIntegTest extends AbstractIntegTest {
    @Autowired
    RequestRepository requestRepository
    @SpringBean
    @Qualifier("udemyJmsTemplate") JmsTemplate jmsTemplate = Mock()

    def cleanup() {
        requestRepository.deleteAll()
    }

    def "A request not yet scheduled should be returned by findByScheduled()"() {
        def request = new Request()
        request.scheduled = false
        requestRepository.save(request)

        when:
        def requests = requestRepository.findByScheduled(Boolean.FALSE)

        then:
        assert requests.size() == 1
    }
}
