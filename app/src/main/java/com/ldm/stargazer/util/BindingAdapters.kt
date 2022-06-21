package com.ldm.stargazer.util

import androidx.databinding.BindingAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import com.ldm.stargazer.R


/**
 * Used in databinding context to set error field in [TextInputLayout]
 */
@BindingAdapter(value = ["app:textError"])
fun setTextError(view: TextInputLayout, result:ValidationUtils.ValidationResultEnum?) {
    when(result){
        ValidationUtils.ValidationResultEnum.OK -> view.error = null
        ValidationUtils.ValidationResultEnum.SPACING -> view.error = view.resources.getString(R.string.spacing_err)
        ValidationUtils.ValidationResultEnum.EMPTY_FIELD -> view.error = view.resources.getString(R.string.empty_err)
        else -> view.error = null
    }
}