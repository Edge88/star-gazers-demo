package com.ldm.stargazer.util

import com.ldm.stargazer.ui.stargazers.STAR_GAZERS_LOG
import timber.log.Timber

/**
 * Set of function to validate input before call the Github API's
 */

const val  VALIDATION_UTILS_LOG = "VALIDATION_UTILS_LOG"
object ValidationUtils {

    /**
     * Enum to define validation state of a value
     * @property OK the validation is successful
     * @property EMPTY_FIELD the value is an empty string
     * @property SPACING the value contains at least one space
     * @property UNDEFINED initial state without validation
     */
    enum class ValidationResultEnum{OK,EMPTY_FIELD, SPACING, UNDEFINED}

    /**
     * Validate repository's name against some rules.
     * @return [ValidationResponse]
     */
    fun validateRepository(repoName: String?): ValidationResponse{
        Timber.tag(VALIDATION_UTILS_LOG).d("Validate repository input: $repoName")

        if(repoName.isNullOrBlank() )
           return ValidationResponse(ValidationResultEnum.EMPTY_FIELD)
        if(repoName.trim().split("\\s".toRegex()).toTypedArray().size > 1) return ValidationResponse(ValidationResultEnum.SPACING)

        return ValidationResponse(ValidationResultEnum.OK)
    }

    /**
     * Validate owner's name against some rules.
     * @return [ValidationResponse]
     */
    fun validateOwner(owner: String?): ValidationResponse{
        Timber.tag(VALIDATION_UTILS_LOG).d("Validate owner input: $owner")
        if(owner.isNullOrBlank())
            return ValidationResponse(ValidationResultEnum.EMPTY_FIELD)
        if(owner.trim().split("\\s".toRegex()).toTypedArray().size > 1)
            return ValidationResponse(ValidationResultEnum.SPACING)

        return ValidationResponse(ValidationResultEnum.OK)

    }

    data class ValidationResponse(val result: ValidationResultEnum)

}