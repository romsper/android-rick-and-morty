package com.romsper.android_rick_and_morty.ui.features.characterList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.romsper.android_rick_and_morty.R
import com.romsper.android_rick_and_morty.databinding.FragmentCharacterListBinding
import com.romsper.android_rick_and_morty.db.entities.Favorite
import com.romsper.android_rick_and_morty.models.Result
import com.romsper.android_rick_and_morty.ui.base.fragment.BaseFragment
import com.romsper.android_rick_and_morty.ui.features.characterList.adapter.CharacterListItemClickListener
import com.romsper.android_rick_and_morty.ui.features.characterList.adapter.CharacterListPagingAdapter
import com.romsper.android_rick_and_morty.ui.features.characterList.adapter.FavoriteCharacterListAdapter
import com.romsper.android_rick_and_morty.ui.features.characterList.adapter.FavoriteCharacterListItemClickListener
import com.romsper.android_rick_and_morty.util.*
import kotlinx.coroutines.Job

class CharacterListFragment : BaseFragment(R.layout.fragment_character_list),
    CharacterListItemClickListener, FavoriteCharacterListItemClickListener {

    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CharactersListViewModel by viewModelsFactory { CharactersListViewModel(requireActivity()) }
    private lateinit var characterListPagingAdapter: CharacterListPagingAdapter
    private lateinit var favoriteCharacterListAdapter: FavoriteCharacterListAdapter
    private var characterName: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_character_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCharacterListBinding.bind(view)

        initSearch()
        initCharacterListPagingAdapter()
        initFavoritesAdapter()
        fetchCharacterList()
        fetchFavoriteList()

        binding.logout.setOnClickListener {

        }
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

    override fun onCharacterListItemClickListener(item: Result) {
        findNavController().navigate(
            CharacterListFragmentDirections.actionCharacterListFragmentToCharacterDetailFragment(
                characterId = item.id
            )
        )
    }

    override fun onFavoriteListItemClickListener(item: Favorite) {
        findNavController().navigate(
            CharacterListFragmentDirections.actionCharacterListFragmentToCharacterDetailFragment(
                characterId = item.characterId
            )
        )
    }

    override fun onRemoveFavoritesItemClickListener(item: Favorite) {
        viewModel.removeFavoriteItem(characterId = item.characterId)
        initFavoritesAdapter()
        fetchFavoriteList()
        appToast("${item.name} removed", true)
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

    private fun fetchFavoriteList() {
        viewModel.favoriteList.observe(viewLifecycleOwner) { favoriteList ->
            if (favoriteList.isNullOrEmpty()) {
                binding.titleFavorites.gone()
                binding.recyclerFavorites.gone()
            } else {
                favoriteCharacterListAdapter.addFavorites(favorites = favoriteList)
            }
        }
        viewModel.fetchFavorites()
    }
}