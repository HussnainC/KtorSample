package com.example.ktorsample.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ktorsample.adaptor.PostAdaptor
import com.example.ktorsample.databinding.ActivityMainBinding
import com.example.ktorsample.modelclass.DataClass
import com.example.ktorsample.utils.Constants
import com.example.ktorsample.utils.States
import com.example.ktorsample.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mAdaptor: PostAdaptor
    private lateinit var binding: ActivityMainBinding
    private val mViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecycler()
        loadPostValues()
    }

    private fun loadPostValues() {
        mViewModel.loadPosts()
        lifecycleScope.launchWhenStarted {
            mViewModel.postList.collect {
                with(binding) {
                    when (it) {
                        is States.Success -> {
                            bindData(it.data)
                        }
                        is States.Failure -> {
                            mProgress.visibility = View.GONE
                            mRecycler.visibility = View.GONE
                            tvEmpty.visibility = View.GONE
                            Toast.makeText(this@MainActivity, it.msg.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                        is States.Loading -> {
                            mProgress.visibility = View.VISIBLE
                            mRecycler.visibility = View.GONE
                            tvEmpty.visibility = View.GONE
                        }
                        is States.Empty -> {
                            mProgress.visibility = View.GONE
                            mRecycler.visibility = View.GONE
                            tvEmpty.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun bindData(data: MutableList<DataClass>) {
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
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mAdaptor
        }
        mAdaptor.onPostClick = {
            startActivity(Intent(this, CommentsActivity::class.java).apply {
                putExtra(Constants.POST_TAG, it)
            })
        }
    }

}