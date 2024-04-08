package com.nchl.moneytracker.presentation.dashboard.category

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import com.nchl.moneytracker.R
import com.nchl.moneytracker.databinding.FragmentCategoryBinding
import com.nchl.moneytracker.presentation.base.AppBaseFragment
import com.nchl.moneytracker.presentation.dashboard.DashViewModel
import com.nchl.moneytracker.presentation.model.ExpenseCategory


@RequiresApi(Build.VERSION_CODES.O)
class CategoryFragment : AppBaseFragment(), EditCategoryDialog.EditCategoryDialogListener {

    private lateinit var binding: FragmentCategoryBinding
    private var tabArray: ArrayList<String> = arrayListOf()
    private lateinit var editCategoryDialog: EditCategoryDialog

    override fun getLayoutId(): Int = R.layout.fragment_category

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
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

        initializeCategoryTabName()
        getViewModel().getAllCategoryFromDbTable()

        //binding.viewPager.offscreenPageLimit = 2
        binding.viewPager.addOnPageChangeListener(TabLayoutOnPageChangeListener(binding.tabLayout))

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    println("TAB changed to " + it.position)
                    binding.viewPager.currentItem = it.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        binding.btnAddCategory.setOnClickListener {
            this.editCategoryDialog = EditCategoryDialog.newInstance(
                ExpenseCategory(),
                "Add Category",
                false
            )
            this.editCategoryDialog.isCancelable = false
            this.editCategoryDialog.show(
                childFragmentManager,
                editCategoryDialog.TAG
            )
        }

        setDynamicFragmentToTabLayout()
    }

    private fun initializeCategoryTabName() {
        tabArray = arrayListOf()
        tabArray.add("Expense")
        tabArray.add("Income")
    }

    private fun setDynamicFragmentToTabLayout() {
        for (i in 0 until tabArray.size) {
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(tabArray[i]))
        }
        val mDynamicFragmentAdapter =
            DynamicFragmentAdapter(childFragmentManager, binding.tabLayout.tabCount)

        binding.viewPager.adapter = mDynamicFragmentAdapter
        binding.viewPager.currentItem = 0
    }

    fun getViewModel() = ViewModelProviders.of(requireActivity())[DashViewModel::class.java]

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onEditCategoryConfirm(category: ExpenseCategory) {
        getViewModel().addCategory(category)
    }
}




@RequiresApi(Build.VERSION_CODES.O)
class DynamicFragmentAdapter internal constructor(
    fm: FragmentManager?,
    private val mNumOfTabs: Int
) :
    FragmentStatePagerAdapter(fm!!) {
    override fun getItem(position: Int): Fragment {

        return when (position) {
            0 -> {
                ExpensesCategoryListFragment()
            }
            1 -> {
                IncomeCategoryListFragment()
            }
            else -> throw IllegalArgumentException("Invalid tab position: $position")
        }
    }

    override fun getCount(): Int {
        return mNumOfTabs
    }
}
