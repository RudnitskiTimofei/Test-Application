package by.it.andersen.newsapikt.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import by.it.andersen.newsapikt.R
import by.it.andersen.newsapikt.data.datasource.ArticleDao
import by.it.andersen.newsapikt.data.model.Article
import by.it.andersen.newsapikt.data.network.ApiInterface
import by.it.andersen.newsapikt.presentation.adapter.NewsAdapter
import by.it.andersen.newsapikt.presentation.viewmodel.NewsListViewModel
import by.it.andersen.newsapikt.presentation.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class NewsListFragment : DaggerFragment(), NewsAdapter.OnNewsListener {


    private lateinit var viewModel: NewsListViewModel
    private lateinit var navController: NavController
    private var recyclerView: RecyclerView? = null
    private var toolbar: Toolbar? = null
    private var manager: LinearLayoutManager? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var alertDialog: AlertDialog? = null
    private var mArticleList: List<Article?>? = null
    private lateinit var category: String

    @set:Inject
    var factory: ViewModelProviderFactory? = null

    @set:Inject
    var adapter: NewsAdapter? = null

    @set:Inject
    var apiInterface: ApiInterface? = null

    @set:Inject
    var articleDao: ArticleDao? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.news_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        init(view)
        pullToRefresh()
    }

    private fun init(view: View) {
        category = arguments?.getString("category").toString()
        viewModel = articleDao?.let { apiInterface?.let { it1 -> NewsListViewModel(it, it1) } }!!
        recyclerView = view.findViewById(R.id.recycle_view)
        manager = LinearLayoutManager(context)
        toolbar = view.findViewById(R.id.toolbar)
        toolbar?.title = category.uppercase()
        alertDialog =
            context?.let {
                AlertDialog.Builder(it).setTitle("Process downloading")
                    .create()
            }
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh)
        recyclerView?.layoutManager = manager
        recyclerView?.adapter = adapter
        setUpObservable(category)
    }

    private fun setUpObservable(message: String?) {
        viewModel.getArticles(message)?.observe(this, {
            mArticleList = it
            adapter?.setData(mArticleList!!, this)
            adapter?.notifyDataSetChanged()
        })

        viewModel.errorMessage.observe(this, { s ->
            if (s == "error") {
                showError()
            }
        })
        viewModel.processMessage.observe(this, { s ->
            if (s == "stop") {
                hideDialog()
            }
        })
    }

    private fun showDialog() {
        alertDialog!!.setMessage("content downloading")
        alertDialog!!.show()
    }

    private fun showError() {
        alertDialog!!.setMessage("Something Wrong!")
    }

    private fun hideDialog() {
        alertDialog!!.dismiss()
    }

    private fun pullToRefresh() {
        swipeRefreshLayout!!.setOnRefreshListener {
            showDialog()
            setUpObservable(category)
            recyclerView!!.adapter = adapter
            adapter?.notifyDataSetChanged()
            swipeRefreshLayout!!.isRefreshing = false
        }
    }

    override fun OnNewsClick(position: Int) {
        val bundle = bundleOf(
            "id" to mArticleList!![position]?.title,
        )
        view?.findNavController()
            ?.navigate(R.id.action_newsListFragment3_to_newDescriptionFragment, bundle)
    }

}