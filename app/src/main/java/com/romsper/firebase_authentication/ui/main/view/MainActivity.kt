package com.romsper.firebase_authentication.ui.main.view

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.romsper.firebase_authentication.databinding.ActivityMainBinding
import com.romsper.firebase_authentication.model.Result
import com.romsper.firebase_authentication.ui.base.ContactsViewModelFactory
import com.romsper.firebase_authentication.ui.main.adapter.ContactsAdapter
import com.romsper.firebase_authentication.ui.main.viewmodel.ContactsViewModal
import com.romsper.firebase_authentication.util.BaseActivity
import com.romsper.firebase_authentication.util.Status

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private lateinit var viewModel: ContactsViewModal
    private lateinit var contactsAdapter: ContactsAdapter

    override fun onStart() {
        super.onStart()
        initContactsViewModel()
        initContactsAdapter()
        initObservers()
    }

    private fun initContactsViewModel() {
        viewModel = ViewModelProvider(
            this,
            ContactsViewModelFactory()
        ).get(ContactsViewModal::class.java)
    }

    private fun initContactsAdapter() {
        binding.recyclerContacts.layoutManager = LinearLayoutManager(this)
        contactsAdapter = ContactsAdapter(arrayListOf())
        binding.recyclerContacts.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerContacts.context,
                (binding.recyclerContacts.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.recyclerContacts.adapter = contactsAdapter
    }

    private fun initObservers() {
        viewModel.getCharacters().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.recyclerContacts.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        resource.data?.let { characters -> retrieveContacts(characters.results) }
                    }
                    Status.ERROR -> {
                        binding.recyclerContacts.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        Log.d("[NETWORK]", it.message!!)
                        Toast.makeText(this, "Network error", Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.recyclerContacts.visibility = View.GONE
                    }
                }
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun retrieveContacts(contacts: List<Result>) {
        contactsAdapter.apply {
            addContacts(contacts)
            notifyDataSetChanged()
        }
    }
}