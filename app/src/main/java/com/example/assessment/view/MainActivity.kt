package com.example.assessment.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.core.widget.TextViewCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assessment.R
import com.example.assessment.databinding.ActivityMainBinding
import com.example.assessment.model.ResponseResult
import com.example.assessment.model.User
import com.example.assessment.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: UserAdapter
    private var isNewSearch = true

    private val viewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setupView()
        observeState()
    }

    private fun setupView() {
        userAdapter = UserAdapter {
            Toast.makeText(this, "test ${it.username}", Toast.LENGTH_SHORT).show()
        }

        binding.mainRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userAdapter
        }
        binding.searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        isNewSearch = true
                        viewModel.doSearchUser(it)
                        return true
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean = false
            })
        }
    }

    private fun observeState() {
        viewModel.response.observe(this) {
            when (it) {
                is ResponseResult.Error -> showErrorDialog(it.httpStatus)
                is ResponseResult.Loading -> handleLoading(it.isLoading)
                is ResponseResult.Success -> handleOnSuccess(it.data?.userList)
            }
        }
    }

    private fun showErrorDialog(httpStatus: Int) {
        Toast.makeText(
            this,
            "error $httpStatus",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun handleLoading(isShow: Boolean) {
        binding.mainRecyclerView.isVisible = !isShow
        binding.loadingLayout.progressBar.isVisible = isShow
    }

    private fun handleOnSuccess(data: List<User>?) {
        data?.let {
            if (!it.isNullOrEmpty()) userAdapter.setItems(isNewSearch, ArrayList(it))
        }
    }
}