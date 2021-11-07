package com.romsper.firebase_authentication.ui.contact.view

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.romsper.firebase_authentication.databinding.ActivityContactBinding
import com.romsper.firebase_authentication.ui.contact.viewModel.ContactViewModel
import com.romsper.firebase_authentication.ui.main.view.MainActivity
import com.romsper.firebase_authentication.util.*
import jp.wasabeef.glide.transformations.BlurTransformation

class ContactActivity : BaseActivity<ActivityContactBinding>(ActivityContactBinding::inflate) {
    private val viewModel: ContactViewModel by viewModels()
    lateinit var favoriteItem: String

    override fun onStart() {
        super.onStart()
        initObservers()

        binding.backButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.btnFavorites.setOnClickListener {
            val existingIds = sharedPreferences.getString("KEY_FAVORITES", "")!!
            if (existingIds.contains(favoriteItem)) Toast.makeText(
                this,
                "Already in Favorites",
                Toast.LENGTH_SHORT
            ).show()
            else sharedPreferences.edit().putString(
                "KEY_FAVORITES",
                if (existingIds.isBlank()) favoriteItem else existingIds.plus(",")
                    .plus(favoriteItem)
            ).apply()
            Toast.makeText(
                this,
                "Done",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun initObservers() {
        val contactId = intent.extras?.getInt("contactId")
        viewModel.getCharacterById(id = contactId!!).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.loading.visibility = View.GONE

                        favoriteItem = gson.toJson(
                            FavoriteItem(
                                id = resource.data?.id,
                                avatarUrl = resource.data?.image,
                                name = resource.data?.name
                            )
                        ).toString()

                        Glide.with(binding.blurAvatar.context)
                            .load(resource.data?.image)
                            .transform(BlurTransformation(5, 10))
                            .into(binding.blurAvatar)
                        Glide.with(binding.contactAvatar.context)
                            .load(resource.data?.image)
                            .apply(RequestOptions().transform(RoundedCorners(80)))
                            .into(binding.contactAvatar)

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