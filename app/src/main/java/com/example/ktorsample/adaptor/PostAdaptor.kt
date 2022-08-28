package com.example.ktorsample.adaptor

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ktorsample.activities.CommentsActivity
import com.example.ktorsample.activities.MainActivity
import com.example.ktorsample.databinding.PostItemBinding
import com.example.ktorsample.modelclass.CommentsDataClass
import com.example.ktorsample.modelclass.DataClass

class PostAdaptor(val context: Activity) : RecyclerView.Adapter<PostAdaptor.MyViewHolder>() {
    class MyViewHolder(val binding: PostItemBinding) : RecyclerView.ViewHolder(binding.root)

    var onPostClick: ((Int) -> Unit?)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(PostItemBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        when (context) {
            is MainActivity -> {
                val singleItem = differList.currentList[position]
                bindPostData(holder.binding, singleItem as DataClass)
            }
            is CommentsActivity -> {
                val singleItem = differList.currentList[position]
                bindCommentsData(holder.binding, singleItem as CommentsDataClass)
            }
        }

    }

    private fun bindCommentsData(binding: PostItemBinding, commentsDataClass: CommentsDataClass) {
        val stringBuilder: StringBuilder = java.lang.StringBuilder()
        stringBuilder.append(
            HtmlCompat.fromHtml(
                "<b>Email: </b> ${commentsDataClass.email}",
                HtmlCompat.FROM_HTML_MODE_COMPACT
            )
        )
        stringBuilder.append(
            "\n"
        )
        stringBuilder.append(
            HtmlCompat.fromHtml(
                "<b>Name: </b> ${commentsDataClass.name}",
                HtmlCompat.FROM_HTML_MODE_COMPACT
            )
        )
        stringBuilder.append(
            "\n"
        )
        stringBuilder.append(
            HtmlCompat.fromHtml(
                "<b>Post: </b> ${commentsDataClass.body}",
                HtmlCompat.FROM_HTML_MODE_COMPACT
            )
        )
        binding.tvDetails.text = stringBuilder.toString()
    }

    private fun bindPostData(binding: PostItemBinding, singleItem: DataClass?) {
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
        binding.mCard.setOnClickListener { onPostClick?.invoke(singleItem?.id ?: 1) }
        binding.tvDetails.text = stringBuilder.toString()
    }

    override fun getItemCount(): Int {
        return differList.currentList.size
    }

    // DifferCallBack
    private val diffUtil = object : DiffUtil.ItemCallback<Any>() {
        override fun areItemsTheSame(
            oldItem: Any,
            newItem: Any
        ): Boolean {
            return if (context is MainActivity) {
                (oldItem as DataClass).id == (newItem as DataClass).id
            } else {
                (oldItem as CommentsDataClass).id == (newItem as CommentsDataClass).id
            }
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: Any,
            newItem: Any
        ): Boolean {
            return if (context is MainActivity) {
                (oldItem as DataClass) == (newItem as DataClass)
            } else {
                (oldItem as CommentsDataClass) == (newItem as CommentsDataClass)
            }
        }
    }

    val differList = AsyncListDiffer(this, diffUtil)
}