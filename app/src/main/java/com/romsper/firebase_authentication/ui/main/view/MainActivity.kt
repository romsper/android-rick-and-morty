package com.romsper.firebase_authentication.ui.main.view

import android.content.Intent
import android.view.View
import android.widget.SearchView.OnQueryTextListener
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.romsper.firebase_authentication.databinding.ActivityMainBinding
import com.romsper.firebase_authentication.model.Result
import com.romsper.firebase_authentication.ui.contact.view.ContactActivity
import com.romsper.firebase_authentication.ui.main.adapter.ContactsItemClickListener
import com.romsper.firebase_authentication.ui.main.adapter.ContactsPagingAdapter
import com.romsper.firebase_authentication.ui.main.adapter.FavoritesAdapter
import com.romsper.firebase_authentication.ui.main.adapter.FavoritesItemClickListener
import com.romsper.firebase_authentication.ui.main.viewmodel.MainViewModel
import com.romsper.firebase_authentication.util.BaseActivity
import com.romsper.firebase_authentication.util.FavoriteItem
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate),
    ContactsItemClickListener, FavoritesItemClickListener {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var contactsPagingAdapter: ContactsPagingAdapter
    private lateinit var favoritesAdapter: FavoritesAdapter
    var characterName: String = ""

    override fun onStart() {
        super.onStart()
        initContactsPagingAdapter()
        collectContacts()
        initFavoritesAdapter()
        collectFavorites(getFavorites())
        initSearch()
    }

    private fun initContactsPagingAdapter() {
        binding.recyclerContacts.layoutManager = LinearLayoutManager(this)
        contactsPagingAdapter = ContactsPagingAdapter(this)
        binding.recyclerContacts.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerContacts.context,
                (binding.recyclerContacts.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.recyclerContacts.adapter = contactsPagingAdapter
    }

    private fun initFavoritesAdapter() {
        binding.recyclerFavorites.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        favoritesAdapter = FavoritesAdapter(arrayListOf(), this)
        binding.recyclerContacts.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerFavorites.context,
                (binding.recyclerFavorites.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.recyclerFavorites.adapter = favoritesAdapter
    }

    private fun collectContacts(search: Boolean = false) {

        when (search) {
            false -> {
                lifecycleScope.launch {
                    viewModel.getCharactersPaging().collectLatest { characters ->
                        contactsPagingAdapter.submitData(characters)
                    }
                }
            }
            true -> {
                lifecycleScope.launch {
                    viewModel.searchCharactersPaging(characterName = characterName)
                        .collectLatest { characters ->
                            contactsPagingAdapter.submitData(characters)
                        }
                }
            }

        }
    }

    private fun collectFavorites(favorites: List<FavoriteItem>) {
        if (favorites.isNullOrEmpty()) {
            binding.titleFavorites.visibility = View.GONE
            binding.recyclerFavorites.visibility = View.GONE
        } else {
            favoritesAdapter.apply {
                addFavorites(favorites)
            }
        }
    }


    override fun onContactsItemClickListener(item: Result) {
        startActivity(Intent(this, ContactActivity::class.java).putExtra("contactId", item.id))
    }

    override fun onFavoritesItemClickListener(item: FavoriteItem) {
        startActivity(Intent(this, ContactActivity::class.java).putExtra("contactId", item.id))
    }

    private fun initSearch() {
        binding.search.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    characterName = query
                    collectContacts(search = true)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrBlank()) {
                    characterName = newText
                    collectContacts(search = true)
                } else {
                    characterName = ""
                    collectContacts(search = false)
                }
                return true
            }
        })
    }
}