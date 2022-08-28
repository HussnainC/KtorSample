package com.example.ktorsample.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ktorsample.adaptor.PostAdaptor
import com.example.ktorsample.databinding.ActivityMainBinding
import com.example.ktorsample.modelclass.CommentsDataClass
import com.example.ktorsample.utils.CommentsStates
import com.example.ktorsample.utils.Constants
import com.example.ktorsample.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentsActivity : AppCompatActivity() {
    private lateinit var mAdaptor: PostAdaptor
    private lateinit var binding: ActivityMainBinding
    private val mViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecycler()
        loadComments(intent.extras?.getInt(Constants.POST_TAG, 1) ?: 1)
    }

    private fun loadComments(id: Int) {
        mViewModel.loadComments(id)
        lifecycleScope.launchWhenStarted {
            mViewModel.commentsList.collect {
                with(binding) {
                    when (it) {
                        is CommentsStates.Success -> {
                            bindData(it.data)
                        }
                        is CommentsStates.Failure -> {
                            mProgress.visibility = View.GONE
                            mRecycler.visibility = View.GONE
                            tvEmpty.visibility = View.GONE
                            Toast.makeText(
                                this@CommentsActivity,
                                it.msg.message,
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                        is CommentsStates.Loading -> {
                            mProgress.visibility = View.VISIBLE
                            mRecycler.visibility = View.GONE
                            tvEmpty.visibility = View.GONE
                        }
                        is CommentsStates.Empty -> {
                            mProgress.visibility = View.GONE
                            mRecycler.visibility = View.GONE
                            tvEmpty.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun bindData(data: MutableList<CommentsDataClass>) {
        mAdaptor.differList.submitList(data as MutableList<*>)
        with(binding) {
            mProgress.visibility = View.GONE
            mRecycler.visibility = View.VISIBLE
            tvEmpty.visibility = View.GONE
        }
    }

    private fun initRecycler() {
        mAdaptor = PostAdaptor(this)
        binding.mRecycler.apply {
            layoutManager = LinearLayoutManager(this@CommentsActivity)
            adapter = mAdaptor
        }
        mAdaptor.onPostClick = {
            startActivity(Intent(this, CommentsActivity::class.java).apply {
                putExtra(Constants.POST_TAG, it)
            })
        }
    }
}