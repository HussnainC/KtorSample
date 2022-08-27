package com.example.ktorsample.adaptor

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ktorsample.databinding.PostItemBinding
import com.example.ktorsample.modelclass.DataClass

class PostAdaptor(val context: Activity) : RecyclerView.Adapter<PostAdaptor.MyViewHolder>() {
    class MyViewHolder(val binding: PostItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(PostItemBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val singleItem = differList.currentList[position]
        bindData(holder.binding, singleItem)
    }

    private fun bindData(binding: PostItemBinding, singleItem: DataClass?) {
        val stringBuilder: StringBuilder = java.lang.StringBuilder()
        stringBuilder.append(
            HtmlCompat.fromHtml(
                "<b>User Id:</b> ${singleItem?.userId}",
                HtmlCompat.FROM_HTML_MODE_COMPACT
            )
        )
        stringBuilder.append(
            "\n"
        )
        stringBuilder.append(
            HtmlCompat.fromHtml(
                "<b>Body:</b> ${singleItem?.body}",
                HtmlCompat.FROM_HTML_MODE_COMPACT
            )
        )
        binding.tvDetails.text = stringBuilder.toString()
    }

    override fun getItemCount(): Int {
        return differList.currentList.size
    }

    // DifferCallBack
    private val diffUtil = object : DiffUtil.ItemCallback<DataClass>() {
        override fun areItemsTheSame(
            oldItem: DataClass,
            newItem: DataClass
        ): Boolean {
            return oldItem.id == oldItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: DataClass,
            newItem: DataClass
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differList = AsyncListDiffer(this, diffUtil)
}