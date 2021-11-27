package com.romsper.firebase_authentication.repository


class AppRepository {
    suspend fun getCharacters(page: Int = 1) = CharactersRepository().getCharacters(page = page)
    suspend fun getCharacterById(characterId: Int) = CharactersRepository().getCharacterById(characterId = characterId)
    suspend fun searchCharacters(characterName: String, page: Int) = CharactersRepository().searchCharacters(characterName = characterName, page = page)
}