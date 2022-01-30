package com.romsper.firebase_authentication.ui.features.characterDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.romsper.firebase_authentication.R
import com.romsper.firebase_authentication.databinding.FragmentCharacterDetailBinding
import com.romsper.firebase_authentication.ui.base.fragment.BaseFragment
import com.romsper.firebase_authentication.util.*
import jp.wasabeef.glide.transformations.BlurTransformation


class CharacterDetailFragment : BaseFragment(R.layout.fragment_character_detail) {
    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoriteItemString: String

    private val viewModel: CharacterDetailViewModel by viewModels()
    private val safeArgs: CharacterDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_character_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCharacterDetailBinding.bind(view)

        existingIds = sharedPreferences.getString("KEY_FAVORITES", "")!!

        if (existingIds.contains(safeArgs.characterId.toString())) {
            binding.btnFavorites.gone()
            binding.btnRemoveFavorites.visible()
        }

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        fetchCharacterById()
        addCharacterToFavoriteList()
        removeCharacterFromFavoriteList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addCharacterToFavoriteList() {
        binding.btnFavorites.setOnClickListener {
            if (existingIds.contains(favoriteItemString)) appToast("Already added")
            else sharedPreferences.edit().putString(
                "KEY_FAVORITES",
                if (existingIds.isBlank()) favoriteItemString else existingIds.plus(",")
                    .plus(favoriteItemString)
            ).apply()
            appToast("${favoriteItem.name} added")

            binding.btnFavorites.gone()
            binding.btnRemoveFavorites.visible()
        }
    }

    private fun removeCharacterFromFavoriteList() {
        binding.btnRemoveFavorites.setOnClickListener {
            removeFavoriteItem(item = favoriteItem)
            binding.btnFavorites.visible()
            binding.btnRemoveFavorites.gone()
            appToast("${favoriteItem.name} removed", true)
        }
    }

    private fun fetchCharacterById() {
        viewModel.characterById.observe(viewLifecycleOwner) { character ->
            if (character == null) {
                binding.loading.visible()
                appToast("Network error")
                findNavController().navigateUp()
            } else {
                binding.loading.gone()
                favoriteItem = FavoriteItem(id = character.id, avatarUrl = character.image, name = character.name)
                favoriteItemString = gson.toJson(favoriteItem).toString()

                Glide.with(binding.blurAvatar.context)
                    .load(character.image)
                    .transform(BlurTransformation(5, 10))
                    .into(binding.blurAvatar)
                Glide.with(binding.characterAvatar.context)
                    .load(character.image)
                    .apply(RequestOptions().transform(RoundedCorners(80)))
                    .into(binding.characterAvatar)

                binding.characterName.text = character.name
                binding.characterStatus.text = character.status
                binding.characterGender.text = character.gender
                binding.characterSpecies.text = character.species
            }
        }
        viewModel.fetchCharacterById(characterId = safeArgs.characterId)
    }
}