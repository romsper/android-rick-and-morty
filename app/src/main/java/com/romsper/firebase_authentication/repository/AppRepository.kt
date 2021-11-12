package com.romsper.firebase_authentication.repository


class AppRepository {
    suspend fun getCharacters(page: Int = 1) = ContactsRepository().getCharacters(page = page)
    suspend fun getCharacterById(characterId: Int) = ContactsRepository().getCharacterById(characterId = characterId)
    suspend fun searchCharacters(characterName: String, page: Int) = ContactsRepository().searchCharacters(characterName = characterName, page = page)
}