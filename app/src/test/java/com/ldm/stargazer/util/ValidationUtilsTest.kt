package com.ldm.stargazer.util

import com.google.common.truth.Truth
import org.junit.Test


/**
 * Unit tests to validate owner and repository fields
 */
class ValidationUtilsTest{

    @Test
    fun `owner with a valid name`(){
        val result = ValidationUtils.validateOwner("android")
        Truth.assertThat(result.result).isEqualTo(ValidationUtils.ValidationResultEnum.OK)
    }

    @Test
    fun `owner with a space in the end`(){
        val result = ValidationUtils.validateOwner("android ")
        Truth.assertThat(result.result).isEqualTo(ValidationUtils.ValidationResultEnum.OK)
    }

    @Test
    fun `owner with a space in the middle`(){
        val result = ValidationUtils.validateOwner("android owner")
        Truth.assertThat(result.result).isEqualTo(ValidationUtils.ValidationResultEnum.SPACING)
    }
    @Test
    fun `owner with only a space and no text`(){
        val result = ValidationUtils.validateOwner(" ")
        Truth.assertThat(result.result).isEqualTo(ValidationUtils.ValidationResultEnum.EMPTY_FIELD)
    }

    @Test
    fun `owner blank`(){
        val result = ValidationUtils.validateOwner("")
        Truth.assertThat(result.result).isEqualTo(ValidationUtils.ValidationResultEnum.EMPTY_FIELD)
    }

    @Test
    fun `repository with a valid name`(){
        val result = ValidationUtils.validateRepository("architecture-samples")
        Truth.assertThat(result.result).isEqualTo(ValidationUtils.ValidationResultEnum.OK)
    }

    @Test
    fun `repository with a space`(){
        val result = ValidationUtils.validateRepository("architecture samples")
        Truth.assertThat(result.result).isEqualTo(ValidationUtils.ValidationResultEnum.SPACING)
    }

    @Test
    fun `repository with only a space and no text`(){
        val result = ValidationUtils.validateRepository(" ")
        Truth.assertThat(result.result).isEqualTo(ValidationUtils.ValidationResultEnum.EMPTY_FIELD)
    }

    @Test
    fun `repository blank`(){
        val result = ValidationUtils.validateRepository("")
        Truth.assertThat(result.result).isEqualTo(ValidationUtils.ValidationResultEnum.EMPTY_FIELD)
    }

}