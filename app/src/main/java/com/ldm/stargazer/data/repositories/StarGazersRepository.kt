package com.ldm.stargazer.data.repositories

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.ldm.stargazer.api.GithubApi
import com.ldm.stargazer.api.const.GitHubConst
import com.ldm.stargazer.api.requests.StarGazersRequest
import com.ldm.stargazer.api.response.StarGazersUser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StarGazersRepository @Inject constructor(private val githubApi: GithubApi) {

    /**
     * Retrieves paginated data from star gazers API
     * @param owner the owner of the repository
     * @param repoName the repository name
     * @return A [liveData] object for the associated view
     * */

    fun getStarGazersFromUserAndRepoName(owner: String, repoName: String): LiveData<PagingData<StarGazersUser>> {

        return Pager(
            config = PagingConfig(
                pageSize = GitHubConst.PER_PAGE_DEFAULT,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                GithubPagingSource(
                    githubApi,
                    StarGazersRequest(owner, repoName)
                )
            }

        ).liveData

    }
}