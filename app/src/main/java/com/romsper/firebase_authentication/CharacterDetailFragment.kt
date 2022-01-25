package com.romsper.firebase_authentication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
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

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        existingIds = sharedPreferences.getString("KEY_FAVORITES", "")!!

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

        binding.btnRemoveFavorites.setOnClickListener {
            removeFavoriteItem(item = favoriteItem)
            binding.btnFavorites.visible()
            binding.btnRemoveFavorites.gone()
            appToast("${favoriteItem.name} removed", true)
        }

        initObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initObservers() {
        val characterId = safeArgs.characterId

        if (existingIds.contains(characterId.toString())) {
            binding.btnFavorites.gone()
            binding.btnRemoveFavorites.visible()
        }
        viewModel.getCharacterById(id = characterId).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.loading.gone()

                        favoriteItem = FavoriteItem(
                            id = resource.data?.id,
                            avatarUrl = resource.data?.image,
                            name = resource.data?.name
                        )
                        favoriteItemString = gson.toJson(favoriteItem).toString()

                        Glide.with(binding.blurAvatar.context)
                            .load(resource.data?.image)
                            .transform(BlurTransformation(5, 10))
                            .into(binding.blurAvatar)
                        Glide.with(binding.characterAvatar.context)
                            .load(resource.data?.image)
                            .apply(RequestOptions().transform(RoundedCorners(80)))
                            .into(binding.characterAvatar)

                        binding.characterName.text = resource.data?.name ?: "<UNKNOWN>"
                        binding.characterStatus.text = resource.data?.status ?: "<UNKNOWN>"
                        binding.characterGender.text = resource.data?.gender ?: "<UNKNOWN>"
                        binding.characterSpecies.text = resource.data?.species ?: "<UNKNOWN>"
                    }
                    Status.ERROR -> {
                        binding.loading.gone()
                        Log.d("[NETWORK]", it.message!!)
                        appToast("Network error")
                    }
                    Status.LOADING -> {
                        binding.loading.visible()
                    }
                }
            }
        })
    }
}