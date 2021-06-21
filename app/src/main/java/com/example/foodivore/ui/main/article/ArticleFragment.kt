package com.example.foodivore.ui.main.article

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodivore.R
import com.example.foodivore.databinding.FragmentArticleBinding
import com.example.foodivore.repository.datasource.remote.article.ArticleRepoImpl
import com.example.foodivore.repository.model.Article
import com.example.foodivore.ui.main.article.detail.ArticleDetailActivity
import com.example.foodivore.ui.main.article.domain.ArticleImpl
import com.example.foodivore.utils.viewobject.Resource
import com.google.gson.Gson

class ArticleFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel
    private lateinit var articleFragmentBinding: FragmentArticleBinding

    private lateinit var recyclerArticle: RecyclerView
    private lateinit var adapterArticle: ArticleAdapter

    private val sharedArticleViewModel: ArticleViewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            ArticleVMFactory(ArticleImpl(ArticleRepoImpl()))
        ).get(ArticleViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        articleFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_article, container, false)

        setupViews(articleFragmentBinding.root)

        fetchData(arguments?.getString(CATEGORY) ?: "")


        return articleFragmentBinding.root
    }


    private fun setupViews(view: View) {
        recyclerArticle = view.findViewById(R.id.recycler_article)
        recyclerArticle.layoutManager = LinearLayoutManager(requireContext())
        adapterArticle = ArticleAdapter(requireContext(), arrayListOf())
        adapterArticle.setOnItemClickListener(object : ArticleAdapter.OnItemClickListener {
            override fun onItemClick(blogModel: Article.Post) {
                intentToArticle(blogModel)
            }
        })
        recyclerArticle.adapter = adapterArticle

    }

    private fun fetchData(categoryName: String) {
        sharedArticleViewModel.getArticleByCategory(categoryName)
        sharedArticleViewModel.articleResult.observe(viewLifecycleOwner, { task ->
            when (task) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    adapterArticle.setPostData(task.data)
                    adapterArticle.notifyDataSetChanged()
                }

                is Resource.Failure -> {
                    Log.d("ArticleFragment", "error: ${task.throwable.message}")
                }

                else -> {

                }
            }
        })
    }

    companion object {

        private const val CATEGORY = "category_name"

        @JvmStatic
        fun newInstance(categoryName: String): ArticleFragment {
            return ArticleFragment().apply {
                arguments = Bundle().apply {
                    putString(CATEGORY, categoryName)
                }
            }
        }
    }

    private fun intentToArticle(value: Article.Post) {
        val intent = Intent(this.requireContext(), ArticleDetailActivity::class.java)
        intent.putExtra("ARTICLEDATA", Gson().toJson(value))
        requireContext().startActivity(intent)
    }


}