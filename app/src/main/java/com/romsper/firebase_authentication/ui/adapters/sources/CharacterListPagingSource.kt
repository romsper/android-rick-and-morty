package com.romsper.firebase_authentication.ui.adapters.sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.romsper.firebase_authentication.models.Result
import com.romsper.firebase_authentication.repository.AppRepository

class CharacterListPagingSource(private val appRepository: AppRepository) : PagingSource<Int, Result>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = appRepository.getCharacters(page = nextPageNumber)
            LoadResult.Page(
                data = response.results,
                prevKey = null,
                nextKey = response.info.next.filter { it.isDigit() }.toInt()
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

class SearchCharacterListPagingSource(private val appRepository: AppRepository, private val characterName: String) : PagingSource<Int, Result>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = appRepository.searchCharacters(characterName = characterName, page = nextPageNumber)
            LoadResult.Page(
                data = response.results,
                prevKey = null,
                nextKey = response.info.next.filter { it.isDigit() }.toInt()
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