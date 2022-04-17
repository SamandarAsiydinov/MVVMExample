package com.samsdk.mvvmarchexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.samsdk.mvvmarchexample.adapter.MainAdapter
import com.samsdk.mvvmarchexample.data.ApiHelper
import com.samsdk.mvvmarchexample.data.ApiServiceImpl
import com.samsdk.mvvmarchexample.databinding.ActivityMainBinding
import com.samsdk.mvvmarchexample.model.User
import com.samsdk.mvvmarchexample.util.Status
import com.samsdk.mvvmarchexample.util.Status.*
import com.samsdk.mvvmarchexample.viewmodel.MainViewModel
import com.samsdk.mvvmarchexample.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: MainAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

    }

    private fun initViews() {
        setupUI()
        setupViewModel()
        setupObserver()
    }

    private fun setupUI() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(arrayListOf())
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerView.context,
                (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.recyclerView.adapter = adapter
    }

    private fun setupObserver() {
        mainViewModel.getUsers().observe(this) {
            when (it.status) {
                SUCCESS -> {
                    binding.progressBar.isVisible = false
                    it.data?.let { users -> renderList(users) }
                    binding.recyclerView.isVisible = true
                }
                LOADING -> {
                    binding.progressBar.isVisible = true
                    binding.recyclerView.isVisible = false
                }
                ERROR -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun renderList(users: List<User>) {
        adapter.addData(users)
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(ApiServiceImpl()))
        )[MainViewModel::class.java]
    }
}