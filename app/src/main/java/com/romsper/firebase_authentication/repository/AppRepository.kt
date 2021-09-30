package com.romsper.firebase_authentication.repository


class AppRepository {
    suspend fun getCharacters() = ContactsRepository().getCharacters()
    suspend fun getCharacterById(characterId: Int) = ContactsRepository().getCharacterById(characterId = characterId)
}