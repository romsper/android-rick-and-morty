package com.romsper.android_rick_and_morty.repository

import com.romsper.android_rick_and_morty.network.Retrofit

open class CharactersRepository {

    suspend fun getCharacters(page: Int) = Retrofit.rickAndMortyApi.getCharacters(page = page)
    suspend fun getCharacterById(characterId: Int) = Retrofit.rickAndMortyApi.getCharacterById(characterId = characterId)
    suspend fun searchCharacters(characterName: String, page: Int) = Retrofit.rickAndMortyApi.searchCharacters(characterName = characterName, page = page)
}