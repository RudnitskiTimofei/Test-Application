package by.it.andersen.newsapikt.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import by.it.andersen.newsapikt.R
import by.it.andersen.newsapikt.data.datasource.ArticleDao
import by.it.andersen.newsapikt.data.model.Article
import by.it.andersen.newsapikt.data.network.ApiInterface
import by.it.andersen.newsapikt.presentation.viewmodel.NewsDescriptionViewModel
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class NewsDescriptionFragment : DaggerFragment() {

    private lateinit var navController: NavController
    private lateinit var viewModel: NewsDescriptionViewModel
    var article: Article? = null
    var titleView: TextView? = null
    var descriptionView: TextView? = null
    var sourceView: TextView? = null
    var imageView: ImageView? = null
    var url: TextView? = null
    private lateinit var title: String

    @set:Inject
    var mPicasso: Picasso? = null

    @set:Inject
    var apiInterface: ApiInterface? = null

    @set:Inject
    var articleDao: ArticleDao? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_description, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        init(view)
    }

    private fun init(view: View) {
        title = arguments?.getString("id").toString()
        viewModel =
            articleDao?.let { apiInterface?.let { it1 -> NewsDescriptionViewModel(it, it1) } }!!
        titleView = view.findViewById(R.id.title)
        descriptionView = view.findViewById(R.id.description)
        sourceView = view.findViewById(R.id.source_name)
        imageView = view.findViewById(R.id.imageView)
        url = view.findViewById(R.id.url)
        titleView?.text = article?.title
        descriptionView?.text = article?.author
        sourceView?.text = article?.source?.name
        mPicasso?.load(article?.urlToImage)?.resize(400, 400)?.into(imageView)
        url?.text = article?.url
        setupObservable(title)
    }

    private fun setupObservable(message: String) {
        viewModel.sendData(message)
        viewModel.news.observe(this, {
            titleView?.text = it.title
            descriptionView?.text = it?.description
            sourceView?.text = it?.publishedAt
            mPicasso?.load(it?.urlToImage)?.resize(400, 400)?.into(imageView)
            url?.text = it?.url
        })
    }
}