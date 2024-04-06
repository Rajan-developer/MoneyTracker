package com.nchl.moneytracker.presentation.dashboard.home

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProviders
import com.nchl.moneytracker.R
import com.nchl.moneytracker.databinding.FragmentHomeBinding
import com.nchl.moneytracker.presentation.base.AppBaseFragment
import com.nchl.moneytracker.presentation.dashboard.DashViewModel



@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class HomeFragment: AppBaseFragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()

        hideSoftKeyboard(requireActivity(), requireView())
    }

    override fun onResume() {
        super.onResume()
    }

    private fun initObservers() {

    }

    private fun initViews() {

    }


    fun getViewModel() = ViewModelProviders.of(requireActivity())[DashViewModel::class.java]

    override fun onDestroyView() {
        super.onDestroyView()
    }
}