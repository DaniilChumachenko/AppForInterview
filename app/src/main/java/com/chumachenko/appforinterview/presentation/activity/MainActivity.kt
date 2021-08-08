package com.chumachenko.appforinterview.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chumachenko.appforinterview.R
import com.chumachenko.appforinterview.presentation.fragment.StayPlusFragment
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.main.appbar.*
import kotlinx.android.synthetic.main.fragment_stay_plus.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initListeners()
        startFragment()
    }

    private fun startFragment() = supportFragmentManager.beginTransaction().apply {
        replace(
            R.id.container,
            StayPlusFragment.newInstance()
        )
        commitAllowingStateLoss()
    }

    private fun initListeners() {
        llActionBarProfile.setOnClickListener {
            Snackbar.make(
                tvSubTitle,
                getString(R.string.not_added),
                Snackbar.LENGTH_SHORT
            ).show()
        }
        llActionBarChat.setOnClickListener {
            Snackbar.make(
                tvSubTitle,
                getString(R.string.not_added),
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector
}