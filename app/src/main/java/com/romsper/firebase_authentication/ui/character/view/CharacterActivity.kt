package com.romsper.firebase_authentication.ui.character.view

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.romsper.firebase_authentication.databinding.ActivityCharacterBinding
import com.romsper.firebase_authentication.ui.character.viewModel.CharacterViewModel
import com.romsper.firebase_authentication.ui.main.view.MainActivity
import com.romsper.firebase_authentication.util.*
import jp.wasabeef.glide.transformations.BlurTransformation

class CharacterActivity :
    BaseActivity<ActivityCharacterBinding>(ActivityCharacterBinding::inflate) {
    private val viewModel: CharacterViewModel by viewModels()
    private lateinit var favoriteItemString: String

    @SuppressLint("CommitPrefEdits")
    override fun onStart() {
        super.onStart()

        binding.backButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        existingIds = sharedPreferences.getString("KEY_FAVORITES", "")!!

        binding.btnFavorites.setOnClickListener {
            if (existingIds.contains(favoriteItemString)) Toast.makeText(
                this,
                "Already added",
                Toast.LENGTH_SHORT
            ).show()
            else sharedPreferences.edit().putString(
                "KEY_FAVORITES",
                if (existingIds.isBlank()) favoriteItemString else existingIds.plus(",")
                    .plus(favoriteItemString)
            ).apply()
            Toast.makeText(
                this,
                "${favoriteItem.name} added",
                Toast.LENGTH_SHORT
            ).show()
            binding.btnFavorites.visibility = View.GONE
            binding.btnRemoveFavorites.visibility = View.VISIBLE
        }

        binding.btnRemoveFavorites.setOnClickListener {
            removeFavoriteItem(item = favoriteItem)
            binding.btnFavorites.visibility = View.VISIBLE
            binding.btnRemoveFavorites.visibility = View.GONE
            Toast.makeText(this, "${favoriteItem.name} removed", Toast.LENGTH_SHORT).show()
        }

        initObservers()
    }

    private fun initObservers() {
        val characterId = intent.extras?.getInt("characterId")
        if (existingIds.contains(characterId.toString())) {
            binding.btnFavorites.visibility = View.GONE
            binding.btnRemoveFavorites.visibility = View.VISIBLE
        }
        viewModel.getCharacterById(id = characterId!!).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.loading.visibility = View.GONE

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
                        binding.loading.visibility = View.GONE
                        Log.d("[NETWORK]", it.message!!)
                        Toast.makeText(this, "Network error", Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        binding.loading.visibility = View.VISIBLE
                    }
                }
            }
        })
    }
}