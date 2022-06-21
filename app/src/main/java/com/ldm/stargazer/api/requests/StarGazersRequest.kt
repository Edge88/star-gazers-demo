package com.ldm.stargazer.api.requests

import com.ldm.stargazer.api.const.GitHubConst

data class StarGazersRequest(
    val owner: String,
    val repoName: String,
    val perPage: Int? = GitHubConst.PER_PAGE_DEFAULT,
    val page: Int? = GitHubConst.PAGE_DEFAULT

)
