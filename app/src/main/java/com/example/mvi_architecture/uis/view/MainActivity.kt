package com.example.mvi_architecture.uis.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvi_architecture.data.api.ApiHelperImpl
import com.example.mvi_architecture.data.api.RetrofitBuilder
import com.example.mvi_architecture.data.model.User
import com.example.mvi_architecture.util.ViewModelFactory
import com.example.mvi_architecture.uis.adapter.MainAdapter
import com.example.mvi_architecture.uis.intent.DataIntent
import com.example.mvi_architecture.uis.viewmodel.DataViewModel
import com.example.mvi_architecture.uis.viewstate.DataState
import com.example.mvi_architecture.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class
MainActivity : AppCompatActivity() {

    private lateinit var dataViewModel: DataViewModel
    private var adapter = MainAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        setupViewModel()
        observeViewModel()
        setupClicks()
    }

    private fun setupClicks() {
        buttonShowUsers.setOnClickListener {
            lifecycleScope.launch {
                dataViewModel.dataIntent.send(DataIntent.FetchData)
            }
        }
    }


    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.run {
            addItemDecoration(
                DividerItemDecoration(
                    recyclerView.context,
                    (recyclerView.layoutManager as LinearLayoutManager).orientation
                )
            )
        }
        recyclerView.adapter = adapter
    }


    private fun setupViewModel() {
        dataViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(
                ApiHelperImpl(
                    RetrofitBuilder.apiService
                )
            )
        ).get(DataViewModel::class.java)
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            dataViewModel.dataState.collect {
                when (it) {
                    is DataState.Inactive -> {
                        Log.d("Inactive","Inactive State")
                    }
                    is DataState.Loading -> {
                        buttonShowUsers.visibility = View.GONE
                        progressBar.visibility = View.VISIBLE
                    }

                    is DataState.ResponseData -> {
                        progressBar.visibility = View.GONE
                        buttonShowUsers.visibility = View.GONE
                        renderList(it.data.data)
                    }
                    is DataState.Error -> {
                        progressBar.visibility = View.GONE
                        buttonShowUsers.visibility = View.VISIBLE
                        Toast.makeText(this@MainActivity, it.error, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun renderList(users: List<User>) {
        recyclerView.visibility = View.VISIBLE
        users.let { listOfUsers -> listOfUsers.let { adapter.addData(it) } }
        adapter.notifyDataSetChanged()
    }
}
