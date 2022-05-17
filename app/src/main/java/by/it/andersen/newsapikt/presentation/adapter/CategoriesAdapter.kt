package by.it.andersen.newsapikt.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.it.andersen.newsapikt.R
import by.it.andersen.newsapikt.data.model.*
import kotlinx.android.synthetic.main.categories_list_item.view.*
import javax.inject.Inject

@SuppressLint("NotifyDataSetChanged")
class CategoriesAdapter @Inject constructor() : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    private var mOnCategoryListener: OnCategoryListener? = null
    private var categoriesList: List<Categories>

    fun setData(list: List<Categories>, onCategoryListener: OnCategoryListener) {
        categoriesList = list
        mOnCategoryListener = onCategoryListener
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.categories_list_item, parent, false)
        return ViewHolder(view, mOnCategoryListener!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = categoriesList

        if (currentItem != null) {
            holder.categoryTitle.text = currentItem[position].name
            holder.categoryDescription.text = currentItem[position].description
        }
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    inner class ViewHolder(itemView: View, onCategoryListener: OnCategoryListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var categoryTitle: TextView = itemView.categoryTitle
        var categoryDescription: TextView = itemView.categoryDescription
        private val mOnCategoryListener = onCategoryListener

        override fun onClick(v: View?) {
            mOnCategoryListener.onCategoryClick(adapterPosition)
        }

        init {
            itemView.setOnClickListener(this)
        }
    }

    interface OnCategoryListener {
        fun onCategoryClick(position: Int)
    }

    init {
        categoriesList = ArrayList()
        notifyDataSetChanged()
    }
}