package by.it.andersen.newsapikt.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import by.it.andersen.newsapikt.R
import by.it.andersen.newsapikt.data.datasource.ArticleDao
import by.it.andersen.newsapikt.data.model.Categories
import by.it.andersen.newsapikt.data.network.ApiInterface
import by.it.andersen.newsapikt.presentation.adapter.CategoriesAdapter
import by.it.andersen.newsapikt.presentation.viewmodel.NewsCategoriesViewModel
import by.it.andersen.newsapikt.presentation.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class NewsCategoriesFragment : DaggerFragment(), CategoriesAdapter.OnCategoryListener {

    private lateinit var viewModel: NewsCategoriesViewModel
    private lateinit var navController: NavController
    private lateinit var recycler: RecyclerView
    private lateinit var toolbar: Toolbar
    private var manager: LinearLayoutManager? = null
    private var alertDialog: AlertDialog? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private lateinit var categoriesList: List<Categories>

    @set:Inject
    var factory: ViewModelProviderFactory? = null

    @set:Inject
    var apiInterface: ApiInterface? = null

    @set:Inject
    var articleDao: ArticleDao? = null

    @set:Inject
    var categoriesAdapter: CategoriesAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.news_categories_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        init(view)
        pullToRefresh()
    }

    private fun init(view: View) {
        viewModel =
            articleDao?.let { apiInterface?.let { it1 -> NewsCategoriesViewModel(it, it1) } }!!
        toolbar = view.findViewById(R.id.toolbarCategories)
        toolbar.title = "Choose your category"
        manager = LinearLayoutManager(context)
        recycler = view.findViewById(R.id.categoriesRecycler)
        recycler.layoutManager = manager
        recycler.adapter = categoriesAdapter
        alertDialog =
            context?.let {
                AlertDialog.Builder(it).setTitle("Process downloading")
                    .create()
            }
        swipeRefreshLayout = view.findViewById(R.id.categoriesRefresh)
        setUpObservable()
    }

    private fun setUpObservable() {
        viewModel.sendData()
        viewModel.articles.observe(this, {
            if (it != null) {
                categoriesList = it
            }
            categoriesAdapter?.setData(categoriesList, this)
            categoriesAdapter?.notifyDataSetChanged()
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
            setUpObservable()
            recycler.adapter = categoriesAdapter
            categoriesAdapter?.notifyDataSetChanged()
            swipeRefreshLayout!!.isRefreshing = false
        }
    }

    override fun onCategoryClick(position: Int) {
        val bundle = bundleOf("category" to (categoriesList[position].id))
        Toast.makeText(context, categoriesList[position].id, Toast.LENGTH_SHORT).show()
        navController.navigate(
            R.id.action_newsCategoriesFragment_to_newsListFragment3,
            bundle
        )
    }
}