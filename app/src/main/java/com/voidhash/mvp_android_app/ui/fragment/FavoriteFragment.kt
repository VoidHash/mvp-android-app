package com.voidhash.mvp_android_app.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.voidhash.mvp_android_app.R
import com.voidhash.mvp_android_app.databinding.FragmentFavoriteBinding
import com.voidhash.mvp_android_app.framework.model.ArticlesItem
import com.voidhash.mvp_android_app.framework.network.NewsDataSource
import com.voidhash.mvp_android_app.framework.presenter.FavoritePresenter
import com.voidhash.mvp_android_app.framework.presenter.NewsContract
import com.voidhash.mvp_android_app.ui.adapter.MainAdapter


class FavoriteFragment : Fragment(), NewsContract.View, MainAdapter.MainAdapterListener {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var presenter: FavoritePresenter
    private lateinit var dataSource: NewsDataSource
    private val mainAdapter by lazy {
        MainAdapter()
    }

    private val itemTouchPerCallback = object  : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val article = mainAdapter.differ.currentList[position]
            presenter.deleteArticle(article)
            Snackbar.make(viewHolder.itemView, "Article deleted", Snackbar.LENGTH_LONG)
                .apply {
                    setAction("Desfazer") {
                        presenter.saveArticle(article)
                        mainAdapter.notifyDataSetChanged()
                    }
                }.show()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataSource = NewsDataSource(requireContext())
        presenter = FavoritePresenter(this, dataSource)

        binding.rvFavorite.apply {
            mainAdapter.listener = this@FavoriteFragment
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(), DividerItemDecoration.VERTICAL
                )
            )
        }

        ItemTouchHelper(itemTouchPerCallback).apply {
            attachToRecyclerView(binding.rvFavorite)
        }

        presenter.getAll()
    }

    override fun showArticleList(abstractList: List<ArticlesItem?>?) {
        mainAdapter.differ.submitList(abstractList)
    }

    override fun showProgressBar() {
        return
    }

    override fun showFailure(message: String) {
        return
    }

    override fun hideProgressBar() {
        return
    }

    override fun onItemClick(article: ArticlesItem) {
        val direction = FavoriteFragmentDirections.actionFavoriteFragmentToArticleFragment(article)
        findNavController().navigate(direction)
    }
}