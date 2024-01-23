package com.voidhash.mvp_android_app.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.voidhash.mvp_android_app.databinding.ItemNewsBinding
import com.voidhash.mvp_android_app.framework.model.ArticlesItem

class MainAdapter: RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private lateinit var context: Context

    private var onItemClickListener: ((ArticlesItem) -> Unit)? = null

    private val differCallback = object : DiffUtil.ItemCallback<ArticlesItem>() {
        override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, this.differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(differ.currentList[position]) {
                binding.txtTitle.text = this.author ?: this. source?.name
                binding.txtSource.text = this.source?.name ?: this.author
                binding.txtDescription.text = this.description ?: this.title
                binding.txtPublishedAt.text = this.publishedAt
                Picasso.get()
                    .load(this.urlToImage)
                    .into(binding.imgArticle)
            }
            setOnClickListener { article ->
                onItemClickListener?.let { click ->
                    click(article)
                }
            }
        }
    }

    fun setOnClickListener(listener: (ArticlesItem) -> Unit) {
        onItemClickListener = listener
    }

    inner class ViewHolder(val binding: ItemNewsBinding): RecyclerView.ViewHolder(binding.root)
}