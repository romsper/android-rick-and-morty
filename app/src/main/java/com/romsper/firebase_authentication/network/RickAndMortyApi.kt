package com.romsper.firebase_authentication.network

import com.romsper.firebase_authentication.model.CharactersResponse
import com.romsper.firebase_authentication.model.SingleCharacterResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApi {

    @GET("character")
    suspend fun getCharacters(): CharactersResponse

    @GET("character/{characterId}")
    suspend fun getCharacterById(@Query("characterId") characterId: Int): SingleCharacterResponse
}