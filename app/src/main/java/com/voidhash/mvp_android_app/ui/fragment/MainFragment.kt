package com.voidhash.mvp_android_app.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.voidhash.mvp_android_app.R
import com.voidhash.mvp_android_app.databinding.FragmentMainBinding
import com.voidhash.mvp_android_app.framework.model.ArticlesItem
import com.voidhash.mvp_android_app.framework.network.NewsDataSource
import com.voidhash.mvp_android_app.framework.presenter.NewsContract
import com.voidhash.mvp_android_app.framework.presenter.NewsPresenter
import com.voidhash.mvp_android_app.ui.adapter.MainAdapter


class MainFragment : Fragment(), NewsContract.View, MainAdapter.MainAdapterListener {

    private lateinit var binding: FragmentMainBinding
    private lateinit var presenter: NewsContract.Presenter
    private val dataSource by lazy {
        NewsDataSource(requireContext())
    }
    private var mainAdapter = MainAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?) : View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvNews.apply {
            mainAdapter.listener = this@MainFragment
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(
                requireContext(), DividerItemDecoration.VERTICAL
                )
            )
        }
        presenter = NewsPresenter(this, dataSource)
        presenter.requestAll()
    }

    override fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun showFailure(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    override fun showArticleList(abstractList: List<ArticlesItem?>?) {
        mainAdapter.differ.submitList(abstractList?.toList())
    }

    override fun onItemClick(article: ArticlesItem) {
        val direction = MainFragmentDirections.actionMainFragmentToArticleFragment(article)
        findNavController().navigate(direction)
    }
}