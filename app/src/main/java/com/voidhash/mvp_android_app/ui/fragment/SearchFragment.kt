package com.voidhash.mvp_android_app.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.voidhash.mvp_android_app.R
import com.voidhash.mvp_android_app.databinding.FragmentSearchBinding
import com.voidhash.mvp_android_app.framework.model.ArticlesItem
import com.voidhash.mvp_android_app.framework.network.NewsDataSource
import com.voidhash.mvp_android_app.framework.presenter.SearchContract
import com.voidhash.mvp_android_app.framework.presenter.SearchPresenter
import com.voidhash.mvp_android_app.framework.util.UtilQueryTextListener
import com.voidhash.mvp_android_app.ui.adapter.MainAdapter


class SearchFragment : Fragment(), SearchContract.View {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var presenter: SearchContract.Presenter
    private val mainAdapter = MainAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataSource = NewsDataSource()
        presenter = SearchPresenter(this, dataSource)
        binding.rvSearch.apply {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            )
        }
        binding.searchNews.setOnQueryTextListener(
            UtilQueryTextListener(
                this.lifecycle
            ) { newText ->
                newText?.let { query ->
                    if (query.isNotEmpty()) {
                        presenter.search(query)
                        binding.rvProgressBarSearch.visibility = View.VISIBLE
                    }
                }
            }
        )

        mainAdapter.setOnClickListener { article ->
            val direction = SearchFragmentDirections.actionSearchFragmentToArticleFragment(article)
            findNavController().navigate(direction)
        }
    }

    override fun showProgressBar() {
        binding.rvProgressBarSearch.visibility = View.VISIBLE
    }

    override fun showFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun hideProgressBar() {
        binding.rvProgressBarSearch.visibility = View.INVISIBLE
    }

    override fun showArticles(articles: List<ArticlesItem>) {
        mainAdapter.differ.submitList(articles.toList())
    }
}