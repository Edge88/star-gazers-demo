package com.ldm.stargazer.api

import com.ldm.stargazer.api.const.GitHubConst
import com.ldm.stargazer.api.response.StarGazersUser
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Retrofit interface to call stargazers api
 */

interface GithubApi{

    /**
     * Retrieve star gazers of a given repository.
     * The response is paginated
     *
     * @param owner the owner of the repo
     * @param repo the name of the repo
     * @param numPerPage the number of entry per page
     * @param page the current page requested
     * @return [List] of [StarGazersUser]
     */

    @GET("repos/{owner}/{repoName}/stargazers")
        suspend fun getUserRepoStargazers(
            @Path("owner") owner: String,
            @Path("repoName") repo: String,
            @Query("per_page") numPerPage: Int? = GitHubConst.PER_PAGE_DEFAULT,
            @Query("page") page: Int? = GitHubConst.PAGE_DEFAULT
        ): List<StarGazersUser>

}

