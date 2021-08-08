package com.chumachenko.appforinterview.presentation.fragment

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chumachenko.appforinterview.R
import com.chumachenko.appforinterview.presentation.adapter.StayPlusAdapter
import com.chumachenko.appforinterview.presentation.viewmodel.Status
import com.chumachenko.appforinterview.presentation.viewmodel.StayPlusViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_stay_plus.*
import javax.inject.Inject

class StayPlusFragment : Fragment(R.layout.fragment_stay_plus) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: StayPlusViewModel

    private var stayPlusAdapter: StayPlusAdapter? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        initAdapter()
        initObservers()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)

        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(
            R.id.stayPlusFragmentContainer,
            SearchFragment.newInstance()
        )
        transaction.commitAllowingStateLoss()
        super.onCreate(savedInstanceState)
    }

    private fun initObservers() = viewModel.apply {
        getImagesLocal()
        stayPlusLocalItem.observe(viewLifecycleOwner) { local ->
            when (local.status) {
                Status.LOADING -> {
                    sflPostPlacesShimmer.startShimmer()
                }
                Status.SUCCESS -> {
                    local.data?.let { list ->
                        stayPlusAdapter?.updateList(list)
                    }
                    sflPostPlacesShimmer.stopShimmer()
                    sflPostPlacesShimmer.visibility = GONE
                }
                Status.ERROR -> {
                    local.throwable?.let { error ->
                        error.printStackTrace()
                        Snackbar.make(
                            tvSubTitle,
                            getString(R.string.loading_error),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        stayPlusResponseItem.observe(viewLifecycleOwner) { response ->
            when (response.status) {
                Status.LOADING -> {
                    sflPostPlacesShimmer.startShimmer()
                }
                Status.SUCCESS -> {
                    response.data?.let { list->
                        stayPlusAdapter?.updateList(list)
                    }
                    sflPostPlacesShimmer.stopShimmer()
                    sflPostPlacesShimmer.visibility = GONE
                }
                Status.ERROR -> {
                    response.throwable?.let { error ->
                        error.printStackTrace()
                        Snackbar.make(
                            tvSubTitle,
                            getString(R.string.loading_error),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun initAdapter(): RecyclerView = recyclerView.apply {
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        stayPlusAdapter = StayPlusAdapter(arrayListOf())
        adapter = stayPlusAdapter
        itemAnimator = null
    }

    private fun bindViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(StayPlusViewModel::class.java)
    }

    companion object {
        fun newInstance(): StayPlusFragment = StayPlusFragment()
    }
}