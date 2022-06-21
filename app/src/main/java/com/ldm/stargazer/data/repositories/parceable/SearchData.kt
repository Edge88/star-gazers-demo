package com.ldm.stargazer.data.repositories.parceable

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Parcelable item passed trough the fragments used to call star gazers api
 * */
@Parcelize
data class SearchData(val owner: String, val repoName:String):Parcelable