package com.romsper.firebase_authentication.repository

import com.romsper.firebase_authentication.models.SingleCharacterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class AppRepository {
    suspend fun getCharacters(page: Int = 1) = CharactersRepository().getCharacters(page = page)
    suspend fun getCharacterById(characterId: Int) = CharactersRepository().getCharacterById(characterId = characterId)
    suspend fun searchCharacters(characterName: String, page: Int) = CharactersRepository().searchCharacters(characterName = characterName, page = page)

//    suspend fun getCharacterByIdFlow(characterId: Int) = flow {
//        val result = CharactersRepository().getCharacterById(characterId = characterId)
//        emit(result)
//    }.flowOn(Dispatchers.IO)
}