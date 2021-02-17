package com.android.newsfeeds.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.android.newsfeeds.BR
import com.android.newsfeeds.R
import com.android.newsfeeds.databinding.FeedDetailsFragmentBinding
import com.android.newsfeeds.viewmodel.NewsFeedsViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import javax.inject.Inject

/**
 * Created by Afif Nadaf on 15/02/21.
 */
@AndroidEntryPoint
class FeedsDetailsFragment : Fragment() {

    private lateinit var mBinding : FeedDetailsFragmentBinding

    private val mViewModel : NewsFeedsViewModel by hiltNavGraphViewModels(R.id.feeds_navigation)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mBinding = FeedDetailsFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObserver()
        arguments?.let {
            mViewModel.fetchFeedDetails(it.getInt(getString(R.string.itemId)))
        }
    }

    private fun initViews() {
        mBinding.homeButton.setOnClickListener {
            val directions  = FeedsDetailsFragmentDirections.actionFeedsDetailsFragmentPop()
            findNavController().navigate(directions)
        }
    }

    private fun initObserver() {
        mViewModel.newsFeedDetail.observe(requireActivity(), Observer {
            mBinding.setVariable(BR.feedData, it)
        })
    }

}