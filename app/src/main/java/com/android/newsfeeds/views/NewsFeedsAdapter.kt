package com.android.newsfeeds.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.newsfeeds.BR
import com.android.newsfeeds.R
import com.android.newsfeeds.databinding.ItemNewsFeedListBinding
import com.android.newsfeeds.model.NewsFeed
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

/**
 * Created by Afif Nadaf on 14/02/21.
 */
class NewsFeedsAdapter @Inject constructor() : RecyclerView.Adapter<NewsFeedsAdapter.NewsFeedHolder>() {

    private var loadMoreData = true
    private lateinit var items : List<NewsFeed>
    private lateinit var pageChangeListener : PageChangeListener

    fun setAdapter(items : List<NewsFeed>, pageChangeListener: PageChangeListener){
        this.items = items
        this.pageChangeListener = pageChangeListener
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsFeedHolder {
         val binding   = ItemNewsFeedListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsFeedHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsFeedHolder, position: Int) {
        holder.bind(items[position])

        holder.itemView.setOnClickListener {
            pageChangeListener.onProductClicked(items[position].id)
        }

        if ((position > items.size - 4) && loadMoreData){
            pageChangeListener.onPageChanged()
        }
    }

    override fun getItemCount() = items.size


    class NewsFeedHolder(private val itemBinding : ItemNewsFeedListBinding) : RecyclerView.ViewHolder(itemBinding.root){
        fun bind(newsFeed: NewsFeed){
            itemBinding.setVariable(BR.newsFeed, newsFeed)
            itemBinding.executePendingBindings()
        }
    }


    fun loadMoreData(loadMore : Boolean){
        loadMoreData = loadMore
        notifyDataSetChanged()
    }

    interface PageChangeListener{
        fun onPageChanged()
        fun onProductClicked(id : Int)
    }

}