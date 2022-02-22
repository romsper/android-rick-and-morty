package com.romsper.android_rick_and_morty.ui.features.characterList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.romsper.android_rick_and_morty.R
import com.romsper.android_rick_and_morty.databinding.FragmentCharacterListBinding
import com.romsper.android_rick_and_morty.models.Result
import com.romsper.android_rick_and_morty.ui.base.fragment.BaseFragment
import com.romsper.android_rick_and_morty.ui.features.characterList.adapter.CharacterListItemClickListener
import com.romsper.android_rick_and_morty.ui.features.characterList.adapter.CharacterListPagingAdapter
import com.romsper.android_rick_and_morty.ui.features.characterList.adapter.FavoriteCharacterListAdapter
import com.romsper.android_rick_and_morty.ui.features.characterList.adapter.FavoriteCharacterListItemClickListener
import com.romsper.android_rick_and_morty.util.FavoriteItem
import com.romsper.android_rick_and_morty.util.appToast
import com.romsper.android_rick_and_morty.util.findNavController
import com.romsper.android_rick_and_morty.util.gone

class CharacterListFragment : BaseFragment(R.layout.fragment_character_list),
    CharacterListItemClickListener, FavoriteCharacterListItemClickListener {

    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CharactersListViewModel by viewModels()
    private lateinit var characterListPagingAdapter: CharacterListPagingAdapter
    private lateinit var favoriteCharacterListAdapter: FavoriteCharacterListAdapter
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
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initCharacterListPagingAdapter() {
        binding.recyclerContacts.layoutManager = LinearLayoutManager(requireActivity())
        characterListPagingAdapter = CharacterListPagingAdapter(this)
        binding.recyclerContacts.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerContacts.context,
                (binding.recyclerContacts.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.recyclerContacts.adapter = characterListPagingAdapter
    }

    private fun initFavoritesAdapter() {
        binding.recyclerFavorites.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        favoriteCharacterListAdapter = FavoriteCharacterListAdapter(arrayListOf(), this)
        binding.recyclerContacts.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerFavorites.context,
                (binding.recyclerFavorites.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.recyclerFavorites.adapter = favoriteCharacterListAdapter
    }

    private fun fetchCharacterList(search: Boolean = false) {
        when (search) {
            false -> {
                viewModel.fetchCharacterList().observe(viewLifecycleOwner) { characterList ->
                    characterListPagingAdapter.submitData(lifecycle, characterList)
                }
            }
            true -> {
                viewModel.fetchSearchCharacterList(characterName = characterName)
                    .observe(viewLifecycleOwner) { searchCharacterList ->
                        characterListPagingAdapter.submitData(lifecycle, searchCharacterList)
                    }
            }
        }
    }

    private fun collectFavorites(favorites: List<FavoriteItem>) {
        if (favorites.isNullOrEmpty()) {
            binding.titleFavorites.gone()
            binding.recyclerFavorites.gone()
        } else {
            favoriteCharacterListAdapter.addFavorites(favorites)
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