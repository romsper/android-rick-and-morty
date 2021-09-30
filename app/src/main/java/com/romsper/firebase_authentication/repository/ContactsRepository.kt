package com.romsper.firebase_authentication.repository

import com.romsper.firebase_authentication.network.Retrofit

class ContactsRepository {

    suspend fun getCharacters() = Retrofit.rickAndMortyApi.getCharacters()
    suspend fun getCharacterById(characterId: Int) = Retrofit.rickAndMortyApi.getCharacterById(characterId = characterId)
}