package com.romsper.firebase_authentication.ui.main.view

import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.romsper.firebase_authentication.databinding.ActivityMainBinding
import com.romsper.firebase_authentication.ui.main.adapter.ContactsPagingAdapter
import com.romsper.firebase_authentication.ui.main.viewmodel.ContactsViewModel
import com.romsper.firebase_authentication.util.BaseActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val viewModel: ContactsViewModel by viewModels()
    private lateinit var contactsPagingAdapter: ContactsPagingAdapter

    override fun onStart() {
        super.onStart()
        initContactsPagingAdapter()
        collectContacts()
    }

    private fun initContactsPagingAdapter() {
        binding.recyclerContacts.layoutManager = LinearLayoutManager(this)
        contactsPagingAdapter = ContactsPagingAdapter()
        binding.recyclerContacts.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerContacts.context,
                (binding.recyclerContacts.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.recyclerContacts.adapter = contactsPagingAdapter
    }

    private fun collectContacts() {
        lifecycleScope.launch {
            viewModel.getCharactersPaging().collectLatest { characters ->
                contactsPagingAdapter.submitData(characters)
            }
        }
    }
}