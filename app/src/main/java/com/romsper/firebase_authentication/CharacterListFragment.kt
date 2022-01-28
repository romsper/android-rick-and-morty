package com.romsper.firebase_authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.romsper.firebase_authentication.databinding.FragmentCharacterListBinding
import com.romsper.firebase_authentication.models.Result
import com.romsper.firebase_authentication.ui.base.fragment.BaseFragment
import com.romsper.firebase_authentication.ui.features.main.adapter.CharactersItemClickListener
import com.romsper.firebase_authentication.ui.features.main.adapter.CharactersPagingAdapter
import com.romsper.firebase_authentication.ui.features.main.adapter.FavoritesAdapter
import com.romsper.firebase_authentication.ui.features.main.adapter.FavoritesItemClickListener
import com.romsper.firebase_authentication.util.FavoriteItem
import com.romsper.firebase_authentication.util.appToast
import com.romsper.firebase_authentication.util.findNavController
import com.romsper.firebase_authentication.util.gone

class CharacterListFragment : BaseFragment(R.layout.fragment_character_list),
    CharactersItemClickListener, FavoritesItemClickListener {

    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CharactersListViewModel by viewModels()
    private lateinit var charactersPagingAdapter: CharactersPagingAdapter
    private lateinit var favoritesAdapter: FavoritesAdapter
    var characterName: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_character_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCharacterListBinding.bind(view)

        initCharacterListPagingAdapter()
        fetchCharacterList()
        initFavoritesAdapter()
        collectFavorites(getFavorites())
        initSearch()

        binding.logout.setOnClickListener {
            firebaseAuth.signOut()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initCharacterListPagingAdapter() {
        binding.recyclerContacts.layoutManager = LinearLayoutManager(requireActivity())
        charactersPagingAdapter = CharactersPagingAdapter(this)
        binding.recyclerContacts.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerContacts.context,
                (binding.recyclerContacts.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.recyclerContacts.adapter = charactersPagingAdapter
    }

    private fun initFavoritesAdapter() {
        binding.recyclerFavorites.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        favoritesAdapter = FavoritesAdapter(arrayListOf(), this)
        binding.recyclerContacts.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerFavorites.context,
                (binding.recyclerFavorites.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.recyclerFavorites.adapter = favoritesAdapter
    }

    private fun fetchCharacterList(search: Boolean = false) {
        when (search) {
            false -> {
                viewModel.fetchCharacterList().observe(viewLifecycleOwner) { characterList ->
                    charactersPagingAdapter.submitData(lifecycle, characterList)
                }
            }
            true -> {
                viewModel.fetchSearchCharacterList(characterName = characterName)
                    .observe(viewLifecycleOwner) { searchCharacterList ->
                        charactersPagingAdapter.submitData(lifecycle, searchCharacterList)
                    }
            }
        }
    }

    private fun collectFavorites(favorites: List<FavoriteItem>) {
        if (favorites.isNullOrEmpty()) {
            binding.titleFavorites.gone()
            binding.recyclerFavorites.gone()
        } else {
            favoritesAdapter.addFavorites(favorites)
        }
    }


    override fun onCharacterListItemClickListener(item: Result) {
        findNavController().navigate(
            CharacterListFragmentDirections.actionCharacterListFragmentToCharacterDetailFragment(
                characterId = item.id
            )
        )
    }

    override fun onFavoriteListItemClickListener(item: FavoriteItem) {
        findNavController().navigate(
            CharacterListFragmentDirections.actionCharacterListFragmentToCharacterDetailFragment(
                characterId = item.id!!
            )
        )
    }

    override fun onRemoveFavoritesItemClickListener(item: FavoriteItem) {
        existingIds = sharedPreferences.getString("KEY_FAVORITES", "")!!
        removeFavoriteItem(item = item)
        initFavoritesAdapter()
        collectFavorites(getFavorites())
        appToast("${favoriteItem.name} removed", true)
    }

    private fun initSearch() {
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    characterName = query
                    fetchCharacterList(search = true)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrBlank()) {
                    characterName = newText
                    fetchCharacterList(search = true)
                } else {
                    characterName = ""
                    fetchCharacterList(search = false)
                }
                return true
            }
        })
    }

//    private fun fetchCharacterList(search: Boolean = false) {
//        when (search) {
//            false -> {
//                lifecycleScope.launch {
//                    viewModel.fetchCharacterList().collectLatest { characters ->
//                        charactersPagingAdapter.submitData(characters)
//                    }
//                }
//            }
//            true -> {
//                lifecycleScope.launch {
//                    viewModel.fetchSearchCharacterList(characterName = characterName)
//                        .collectLatest { characters ->
//                            charactersPagingAdapter.submitData(characters)
//                        }
//                }
//            }
//        }
//    }
}