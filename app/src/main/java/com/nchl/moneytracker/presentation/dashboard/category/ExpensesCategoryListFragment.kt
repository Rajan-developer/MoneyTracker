package com.nchl.moneytracker.presentation.dashboard.category

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.nchl.moneytracker.R
import com.nchl.moneytracker.databinding.FragmentCategoryListBinding
import com.nchl.moneytracker.presentation.base.AppBaseFragment
import com.nchl.moneytracker.presentation.dashboard.DashViewModel
import com.nchl.moneytracker.presentation.model.ExpenseCategory


@RequiresApi(Build.VERSION_CODES.O)
class ExpensesCategoryListFragment : AppBaseFragment(), CategoryListAdapter.Listener,EditCategoryDialog.EditCategoryDialogListener {


    private lateinit var binding: FragmentCategoryListBinding
    private lateinit var editCategoryDialog: EditCategoryDialog
    private val adapter = CategoryListAdapter(mutableListOf(), this)

    override fun getLayoutId(): Int = R.layout.fragment_category_list

    private fun getViewModel() = ViewModelProviders.of(requireActivity())[DashViewModel::class.java]

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()
    }

    override fun onResume() {
        super.onResume()
        getViewModel().getAllExpenseCategory(
            "0"
        )
    }

    private fun initViews() {
        val layoutManager = LinearLayoutManager(activity)
        binding.rvCategory.layoutManager = layoutManager
        binding.rvCategory.adapter = adapter
    }

    private fun initObservers() {
        getViewModel().expenseCategoryList.observe(viewLifecycleOwner) {
            adapter.updateAll(it)
        }
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        fun newInstance(): ExpensesCategoryListFragment {
            return ExpensesCategoryListFragment()
        }
    }

    override fun onEditClicked(category: ExpenseCategory) {
        this.editCategoryDialog = EditCategoryDialog.newInstance(
            category,
            "Edit Category"
        )
        this.editCategoryDialog.isCancelable = false
        this.editCategoryDialog.show(
            childFragmentManager,
            editCategoryDialog.TAG
        )
    }

    override fun onDeleteClicked(category: ExpenseCategory) {
        getViewModel().deleteCategoryById(categoryId = category.id)
    }

    override fun onEditCategoryConfirm(category: ExpenseCategory) {
        getViewModel().updateCategory(category)
    }
}