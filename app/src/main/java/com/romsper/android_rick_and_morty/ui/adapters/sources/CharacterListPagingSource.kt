package com.romsper.android_rick_and_morty.ui.adapters.sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.romsper.android_rick_and_morty.models.Result
import com.romsper.android_rick_and_morty.repository.AppRepository

class CharacterListPagingSource(
    private val appRepository: AppRepository,
    private val name: String
) : PagingSource<Int, Result>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val page = params.key ?: 1
            val pageSize = params.loadSize.coerceAtMost(20)

            println(name)

            val response =
                if (name.isBlank())
                    appRepository.getCharacters(page = page)
                else
                    appRepository.searchCharacters(characterName = name, page = page)
            LoadResult.Page(
                data = response.results,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.results.size < pageSize) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}