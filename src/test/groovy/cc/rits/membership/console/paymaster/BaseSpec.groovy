package cc.rits.membership.console.paymaster

import cc.rits.membership.console.paymaster.exception.BaseException
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest
abstract class BaseSpec extends Specification {

    @Inject
    EmbeddedApplication<?> application

    /**
     * 例外を検証
     *
     * @param thrownException 発生した例外
     * @param expectedException 期待する例外
     */
    static void verifyException(final BaseException thrownException, final BaseException expectedException) {
        assert thrownException.status == expectedException.status
        assert thrownException.errorCode == expectedException.errorCode
    }

}
