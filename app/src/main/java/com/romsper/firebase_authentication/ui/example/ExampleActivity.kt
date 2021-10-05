package com.romsper.firebase_authentication.ui.example

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.romsper.firebase_authentication.databinding.ActivityExampleBinding
import com.romsper.firebase_authentication.model.Result
import com.romsper.firebase_authentication.util.BaseActivity
import com.romsper.firebase_authentication.util.Status

class ExampleActivity : BaseActivity<ActivityExampleBinding>(ActivityExampleBinding::inflate) {
    private val viewModel: ExampleViewModel by viewModels()
    private lateinit var exampleAdapter: ExampleAdapter

    override fun onStart() {
        super.onStart()
        initExampleAdapter()
        initObservers()
    }

    private fun initExampleAdapter() {
        binding.recyclerContacts.layoutManager = LinearLayoutManager(this)
        exampleAdapter = ExampleAdapter(arrayListOf())
        binding.recyclerContacts.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerContacts.context,
                (binding.recyclerContacts.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.recyclerContacts.adapter = exampleAdapter
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
        exampleAdapter.apply {
            addContacts(contacts)
            notifyDataSetChanged()
        }
    }

}