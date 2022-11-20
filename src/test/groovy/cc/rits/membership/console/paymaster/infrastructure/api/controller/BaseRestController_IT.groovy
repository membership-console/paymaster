package cc.rits.membership.console.paymaster.infrastructure.api.controller

import cc.rits.membership.console.paymaster.BaseDatabaseSpec
import cc.rits.membership.console.paymaster.exception.BaseException
import cc.rits.membership.console.paymaster.infrastructure.api.request.BaseRequest
import cc.rits.membership.console.paymaster.infrastructure.api.response.ErrorResponse
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.http.cookie.Cookie
import jakarta.inject.Inject
import spock.lang.Shared

class BaseRestController_IT extends BaseDatabaseSpec {

    @Shared
    private final Cookie cookie = Cookie.of("SessionId", "dummy")

    @Inject
    @Client("/")
    HttpClient client

    /**
     * GET request
     *
     * @param path path
     *
     * @return HTTP request
     */
    HttpRequest getRequest(final String path) {
        return HttpRequest.GET(path) //
            .cookie(cookie)
    }

    /**
     * POST request
     *
     * @param path
     * @param body
     *
     * @return HTTP request
     */
    HttpRequest postRequest(final String path, final BaseRequest body) {
        return HttpRequest.POST(path, body) //
            .cookie(cookie)
    }

    /**
     * PUT request
     *
     * @param path
     * @param body
     *
     * @return HTTP request
     */
    HttpRequest putRequest(final String path, final BaseRequest body) {
        return HttpRequest.PUT(path, body) //
            .cookie(cookie)
    }

    /**
     * DELETE request
     * @param path
     *
     * @return HTTP request
     */
    HttpRequest deleteRequest(final path) {
        return HttpRequest.DELETE(path) //
            .cookie(cookie)
    }

    /**
     * Execute request
     *
     * @param request HTTP request
     * @param status expected HTTP status
     */
    def execute(final HttpRequest request, final HttpStatus status) {
        try {
            final response = this.client.toBlocking() //
                .exchange(request)
            assert response.status() == status
            return response
        } catch (HttpClientResponseException httpClientResponseException) {
            assert httpClientResponseException.status == status
            return httpClientResponseException.response
        }
    }


    /**
     * Execute request / return response
     *
     * @param request HTTP request
     * @param status expected HTTP status
     * @param clazz response class
     *
     * @return response
     */
    def <T> T execute(final HttpRequest request, final HttpStatus status, final Class<T> clazz) {
        final response = this.client.toBlocking() //
            .exchange(request, clazz)

        assert response.status() == status

        return response.body()
    }

    /**
     * Execute request / verify exception
     *
     * @param request HTTP request
     * @param exception expected exception
     */
    def execute(final HttpRequest request, final BaseException exception) {
        try {
            this.client.toBlocking().exchange(request)
        } catch (HttpClientResponseException httpClientResponseException) {
            assert httpClientResponseException.status == exception.status

            final response = httpClientResponseException.response as ErrorResponse
            assert response.code == exception.errorCode.code
            assert response.message == exception.errorCode.message
            return response
        }
    }

}
