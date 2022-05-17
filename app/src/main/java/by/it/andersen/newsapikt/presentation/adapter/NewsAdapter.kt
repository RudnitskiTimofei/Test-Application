package by.it.andersen.newsapikt.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.it.andersen.newsapikt.R
import by.it.andersen.newsapikt.data.model.Article
import by.it.andersen.newsapikt.presentation.view.NewsListFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item.view.*
import java.util.*
import javax.inject.Inject

@SuppressLint("NotifyDataSetChanged")
class NewsAdapter @Inject constructor(var mPicasso: Picasso) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private var articles: List<Article?>
    private var mOnNewsListener: OnNewsListener? = null

    fun setData(
        list: List<Article?>,
        onNewsListener: NewsListFragment
    ) {
        articles = list
        mOnNewsListener = onNewsListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view, mOnNewsListener!!)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        articles[position]?.let { holder.bind(it) }

    }

    override fun getItemCount(): Int {
        return articles.size
    }

    inner class ViewHolder(
        itemView: View,
        onNewsListener: OnNewsListener
    ) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val author = itemView.author_item
        val title = itemView.title_item
        val publishedAt = itemView.publishedAt_item
        val imageView = itemView.image_item
        var mOnNewsListener: OnNewsListener = onNewsListener
        override fun onClick(v: View) {
            mOnNewsListener.OnNewsClick(adapterPosition)
        }

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(article: Article) {
            author.text = article.author
            title.text = article.title
            publishedAt.text = article.publishedAt
            mPicasso.load(article.urlToImage).into(imageView)
        }
    }

    interface OnNewsListener {
        fun OnNewsClick(position: Int)
    }

    init {
        articles = ArrayList<Article>()
        notifyDataSetChanged()
    }
}