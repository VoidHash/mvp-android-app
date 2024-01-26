package com.voidhash.mvp_android_app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.voidhash.mvp_android_app.R
import com.voidhash.mvp_android_app.databinding.FragmentArticleBinding
import com.voidhash.mvp_android_app.databinding.ItemNewsBinding
import com.voidhash.mvp_android_app.framework.model.ArticlesItem
import com.voidhash.mvp_android_app.framework.network.NewsDataSource
import com.voidhash.mvp_android_app.framework.presenter.FavoriteContract
import com.voidhash.mvp_android_app.framework.presenter.FavoritePresenter
import com.voidhash.mvp_android_app.framework.presenter.NewsContract


class ArticleFragment : Fragment() {

    private val args: ArticleFragmentArgs by navArgs()
    private lateinit var presenter: FavoritePresenter
    private lateinit var binding: FragmentArticleBinding
    private val dataSource by lazy {
        NewsDataSource(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val articles = args.articleParcelable

        binding.webView.apply {
            webViewClient = WebViewClient()
            articles.url?.let { url ->
                loadUrl(url)
            }
        }

        binding.fab.setOnClickListener {
            presenter.saveArticle(articles)
            Snackbar.make(
                it, "Article Saved",
                Snackbar.LENGTH_LONG
            ).show()
        }
    }
}