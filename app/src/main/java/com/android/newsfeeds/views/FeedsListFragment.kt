package com.android.newsfeeds.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.newsfeeds.MainActivity
import com.android.newsfeeds.R
import com.android.newsfeeds.databinding.FeedsListFragmentBinding
import com.android.newsfeeds.model.NewsFeed
import com.android.newsfeeds.network.ConnectionManager
import com.android.newsfeeds.utils.hide
import com.android.newsfeeds.utils.show
import com.android.newsfeeds.viewmodel.NewsFeedsViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by Afif Nadaf on 15/02/21.
 */
@AndroidEntryPoint
class FeedsListFragment : Fragment(), NewsFeedsAdapter.PageChangeListener {

    @Inject
    lateinit var newsFeedsAdapter: NewsFeedsAdapter

    private val mViewModel: NewsFeedsViewModel by hiltNavGraphViewModels(R.id.feeds_navigation)

    private lateinit var mBinding: FeedsListFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {

        @JvmStatic
        @BindingAdapter("profileImage")
        fun loadImage(view: ImageView, imageUrl: String?) {
            imageUrl?.let {
                Glide.with(view).load(it).into(view).apply { RequestOptions().centerCrop() }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mBinding = FeedsListFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
        initViewModel()
        onPageChanged()
    }

    private fun initViews() {
        mBinding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
    }

    private fun initObservers() {

        mViewModel.newsFeedsResponse.observe(requireActivity(), Observer {
            updateRecyclerView(it)
        })

        mViewModel.newsFeedError.observe(requireActivity(), Observer {
            showErrorMessage()
            newsFeedsAdapter.loadMoreData(false)
        })
    }

    private fun updateRecyclerView(list: List<NewsFeed>) {
        val pageNumber = mViewModel.pageNumber.value as Int
        if (list.isNotEmpty()) {
            if (pageNumber == 1) {
                showRecyclerView()
                mBinding.recyclerView.adapter = newsFeedsAdapter
            }
            newsFeedsAdapter.setAdapter(list, this)

            if (!ConnectionManager.isConnected(requireContext())){
                newsFeedsAdapter.loadMoreData(false)
            }

        } else {
            showErrorMessage()
        }
    }

    private fun showErrorMessage() {
        val pageNumber = mViewModel.pageNumber.value as Int
        if (pageNumber == 1) {
            mBinding.recyclerView.hide()
            mBinding.progressBarLayout.show()
            mBinding.progressBar.hide()
            mBinding.informationTextView.show()
            mBinding.informationTextView.text = getString(R.string.something_went_wrong)
        }
    }

    private fun showRecyclerView() {
        mBinding.recyclerView.show()
        mBinding.progressBarLayout.hide()
    }

    private fun initViewModel() {
        mViewModel.pageNumber.value = 0
    }

    override fun onPageChanged() {
        var pageNumber = mViewModel.pageNumber.value
        pageNumber?.let {
            mViewModel.pageNumber.value = ++pageNumber

            if (pageNumber == 1) {
                hideRecyclerView()
            }

            mViewModel.fetchNewsFeeds(pageNumber)
        }
    }

    override fun onProductClicked(id: Int) {
        val directions = FeedsListFragmentDirections.navListToDetailsScreen(id)
        findNavController().navigate(directions)
    }

    private fun hideRecyclerView() {
        mBinding.recyclerView.hide()
        mBinding.progressBarLayout.show()
        mBinding.progressBar.show()
        mBinding.informationTextView.show()
        mBinding.informationTextView.text = getString(R.string.loading_data_text)
    }

}