package com.romsper.firebase_authentication.repository

import com.romsper.firebase_authentication.network.Retrofit

open class ContactsRepository {

    suspend fun getCharacters(page: Int) = Retrofit.rickAndMortyApi.getCharacters(page = page)
    suspend fun getCharacterById(characterId: Int) = Retrofit.rickAndMortyApi.getCharacterById(characterId = characterId)
}