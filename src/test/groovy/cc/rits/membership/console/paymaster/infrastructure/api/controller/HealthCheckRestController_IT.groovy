package cc.rits.membership.console.paymaster.infrastructure.api.controller

import io.micronaut.http.HttpStatus

class HealthCheckRestController_IT extends BaseRestController_IT {
    // API PATH
    static final BASE_PATH = "/api/health"
    static final HEALTH_CHECK_PATH = BASE_PATH

    def "ヘルスチェックAPI: 200 OKを返す"() {
        expect:
        final request = this.getRequest(HEALTH_CHECK_PATH)
        this.execute(request, HttpStatus.OK)
    }

}
