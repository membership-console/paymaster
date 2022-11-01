package cc.rits.membership.console.paymaster.annotation

import cc.rits.membership.console.paymaster.infrastructure.api.request.BaseRequest
import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.core.annotation.NonNull
import io.micronaut.core.annotation.Nullable
import io.micronaut.validation.validator.constraints.ConstraintValidator
import jakarta.inject.Singleton
import javax.validation.Constraint

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Constraint(validatedBy = [RequestValidator::class])
annotation class RequestValidation


@Singleton
class RequestValidator : ConstraintValidator<RequestValidation, BaseRequest> {

    override fun isValid(
        @Nullable value: BaseRequest?,
        @NonNull annotationMetadata: AnnotationValue<RequestValidation>,
        @NonNull context: io.micronaut.validation.validator.constraints.ConstraintValidatorContext
    ): Boolean {
        value?.validate()
        return true
    }

}
