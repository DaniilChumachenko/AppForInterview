package com.chumachenko.appforinterview.presentation.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
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
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_stay_plus.*
import javax.inject.Inject

class SearchFragment : Fragment(R.layout.fragment_search) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        llDestinationFieldSearch.setOnClickListener {
            Snackbar.make(
                btnSearch,
                getString(R.string.not_added),
                Snackbar.LENGTH_SHORT
            ).show()
        }
        llGuestsFieldSearch.setOnClickListener {
            Snackbar.make(
                btnSearch,
                getString(R.string.not_added),
                Snackbar.LENGTH_SHORT
            ).show()
        }
        llCalendarFieldSearch.setOnClickListener {
            Snackbar.make(
                btnSearch,
                getString(R.string.not_added),
                Snackbar.LENGTH_SHORT
            ).show()
        }
        btnSearch.setOnClickListener {
            Snackbar.make(
                btnSearch,
                getString(R.string.not_added),
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    companion object {
        fun newInstance(): SearchFragment = SearchFragment()
    }
}