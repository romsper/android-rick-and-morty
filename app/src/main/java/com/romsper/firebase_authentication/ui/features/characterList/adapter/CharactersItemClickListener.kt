package com.romsper.firebase_authentication.ui.features.characterList.adapter

import com.romsper.firebase_authentication.models.Result

interface CharactersItemClickListener {
    fun onCharacterListItemClickListener(item: Result)
}