package com.example.assessment.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.assessment.databinding.ActivityDetailBinding
import com.example.assessment.model.ResponseResult
import com.example.assessment.model.User
import com.example.assessment.model.UserDetailResponse
import com.example.assessment.model.UserRepoResponse
import com.example.assessment.viewmodel.DetailViewModel
import com.example.assessment.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var repositoryAdapter: RepositoryAdapter

    private val user by lazy {
        intent?.getParcelableExtra(USER_DATA) ?: User()
    }

    private val viewModel by lazy {
        ViewModelProvider(this).get(DetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)
        initView()
        observeState()
    }

    private fun initView() {
        repositoryAdapter = RepositoryAdapter()
        binding.repositoryRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@DetailActivity)
            adapter = repositoryAdapter
        }

        user.username?.let {
            viewModel.doGetRepo(it)
            viewModel.doGetUserDetail(it)
        }
    }

    private fun observeState() {
        viewModel.userDetailResponse.observe(this) {
            when (it) {
                is ResponseResult.Error -> showErrorDialog(it.httpStatus)
                is ResponseResult.Loading -> handleLoadingUserDetail(it.isLoading)
                is ResponseResult.Success -> handleSuccessUserDetail(it.data)
            }
        }
        viewModel.userRepositoryResponse.observe(this) {
            when (it) {
                is ResponseResult.Error -> showErrorDialog(it.httpStatus)
                is ResponseResult.Loading -> handleLoadingRepo(it.isLoading)
                is ResponseResult.Success -> handleSuccessRepo(it.data)
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

    private fun handleLoadingUserDetail(isVisible: Boolean) {
        binding.detailLayout.isInvisible = isVisible
        binding.loadingLayout.progressBar.isVisible = isVisible
    }

    private fun handleLoadingRepo(isVisible: Boolean) {
        binding.repositoryRecyclerView.isInvisible = isVisible
        binding.loadingLayout.progressBar.isVisible = isVisible
    }

    private fun handleSuccessRepo(data: List<UserRepoResponse>?) {
        data?.let {
            if (!it.isNullOrEmpty()) repositoryAdapter.setItems(ArrayList(it))
        }
    }

    private fun handleSuccessUserDetail(data: UserDetailResponse?) {
        binding.apply {
            data?.let {
                Glide.with(root)
                    .load(it.imageUrl)
                    .transform(CircleCrop())
                    .into(profileImage)

                fullNameTextView.text = it.fullName
                usernameTextView.text = it.username

                bioTextView.text = it.bio
                bioTextView.isVisible = it.bio?.isNotBlank() ?: false

                followerTextView.text = getCountTotal(it.followersTotal)
                followingTextView.text = getCountTotal(it.followingTotal)

                locationTextView.text = it.location
                locationTextView.isVisible = it.location?.isNotBlank() ?: false
                locationImageView.isVisible = it.location?.isNotBlank() ?: false

                emailTextView.text = it.email
                emailTextView.isVisible = it.email?.isNotBlank() ?: false
                emailImageView.isVisible = it.email?.isNotBlank() ?: false
            }
        }
    }

    private fun getCountTotal(count: Int?): String {
        count?.let {
            if (it < 1000) return it.toString()
            return "${(it / 1000)}K"
        }
        return "0"
    }

    companion object {
        private const val USER_DATA = "user-date"

        fun newInstance(user: User, context: Context): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(USER_DATA, user)
            return intent
        }
    }
}