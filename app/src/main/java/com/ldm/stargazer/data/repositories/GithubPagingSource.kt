package com.ldm.stargazer.data.repositories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ldm.stargazer.api.GithubApi
import com.ldm.stargazer.api.const.GitHubConst
import com.ldm.stargazer.api.requests.StarGazersRequest
import com.ldm.stargazer.api.response.StarGazersUser
import com.ldm.stargazer.ui.search.SEARCH_LOG
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.lang.Integer.max

/**
 * A [PagingSource] class to manage dynamically the star gazers API paginated results
 * @param githubApi the github API interface
 * @param request the request to call the API
 * */

const val GITHUB_PAGE_SOURCE_LOG = "GITHUB_PAGE_SOURCE_LOG"
class GithubPagingSource(
   private val githubApi: GithubApi,
   private val request: StarGazersRequest
): PagingSource<Int, StarGazersUser>() {

   override fun getRefreshKey(state: PagingState<Int, StarGazersUser>): Int? {
       val anchorPosition = state.anchorPosition ?: return null
       val article = state.closestItemToPosition(anchorPosition) ?: return null
       return max(article.id - (state.config.pageSize / 2),GitHubConst.PAGE_DEFAULT)
   }

   override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StarGazersUser> {
        val position = params.key ?: GitHubConst.PAGE_DEFAULT
       return try {

        val response = githubApi.getUserRepoStargazers(request.owner, request.repoName,page = position)

           Timber.tag(GITHUB_PAGE_SOURCE_LOG).d("getUserRepoStargazers response size: ${response.size}")

           LoadResult.Page(
                   data = response,
                   nextKey = if (response.isEmpty()) null else position + 1,
                   prevKey = if (position == GitHubConst.PAGE_DEFAULT) null else position - 1

               )

     }catch (e: IOException){
         Timber.e(e)
         LoadResult.Error(e)
     }catch (e: HttpException){
           Timber.e(e)
           LoadResult.Error(e)
     }
   }


}