package com.romsper.android_rick_and_morty.ui.features.characterList.adapter

import com.romsper.android_rick_and_morty.models.Result

interface CharacterListItemClickListener {
    fun onCharacterListItemClickListener(item: Result)
}