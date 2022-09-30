package com.romsper.android_rick_and_morty.ui.features.characterDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.romsper.android_rick_and_morty.R
import com.romsper.android_rick_and_morty.databinding.FragmentCharacterDetailBinding
import com.romsper.android_rick_and_morty.db.entities.Favorite
import com.romsper.android_rick_and_morty.ui.base.fragment.BaseFragment
import com.romsper.android_rick_and_morty.util.*
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.glide.transformations.BlurTransformation

@AndroidEntryPoint
class CharacterDetailFragment : BaseFragment(R.layout.fragment_character_detail) {
    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CharacterDetailViewModel by viewModels()
    private val safeArgs: CharacterDetailFragmentArgs by navArgs()
    private lateinit var favoriteItem: Favorite

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_character_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCharacterDetailBinding.bind(view)

        viewModel.favoriteList.observe(viewLifecycleOwner) { favoriteList ->
            if (favoriteList.firstOrNull { it.characterId == safeArgs.characterId } != null) {
                binding.btnFavorites.gone()
                binding.btnRemoveFavorites.visible()
            }
        }
        viewModel.fetchFavorites()

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        fetchCharacterById()
        addCharacterToFavoriteList()
        removeCharacterFromFavoriteList()
    }

    private fun fetchCharacterById() {
        viewModel.characterById.observe(viewLifecycleOwner) { character ->
            if (character == null) {
                binding.loading.visible()
                appToast("Network error")
                findNavController().popBackStack()
            } else {
                binding.loading.gone()
                favoriteItem = Favorite(id = null, characterId = character.id, avatarUrl = character.image, name = character.name)

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

    private fun addCharacterToFavoriteList() {
        binding.btnFavorites.setOnClickListener {
            viewModel.favoriteList.observe(viewLifecycleOwner) { favoriteList ->
                if (favoriteList.firstOrNull { it.characterId == favoriteItem.characterId } == null) {
                    viewModel.addFavorite(favorite = favoriteItem)
                    appToast("${favoriteItem.name} added")
                }
            }
            binding.btnFavorites.gone()
            binding.btnRemoveFavorites.visible()
        }
    }

    private fun removeCharacterFromFavoriteList() {
        binding.btnRemoveFavorites.setOnClickListener {
            viewModel.removeFavoriteItem(characterId = favoriteItem.characterId)
            binding.btnFavorites.visible()
            binding.btnRemoveFavorites.gone()
            appToast("${favoriteItem.name} removed", true)
        }
    }
}