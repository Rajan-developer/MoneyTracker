package com.nchl.moneytracker.presentation.dashboard.category

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.nchl.moneytracker.R
import com.nchl.moneytracker.databinding.EditCategoryDialogBinding
import com.nchl.moneytracker.extensions.hideKeyboard
import com.nchl.moneytracker.presentation.model.ExpenseCategory
import com.nchl.moneytracker.presentation.utils.log.Logger
import global.citytech.easydroid.core.extension.showToast
import global.citytech.finpos.merchant.extensions.handleDebounce

class EditCategoryDialog : DialogFragment() {
    val TAG = EditCategoryDialog::class.java.name
    private var category: ExpenseCategory? = null
    private var title: String? = null
    private var editing: Boolean? = true
    private var categoryType: String? = "0"
    private lateinit var listener: EditCategoryDialogListener
    private val logger = Logger(EditCategoryDialog::class.java)

    private lateinit var binding: EditCategoryDialogBinding

    companion object {
        const val TITLE = "title"
        const val CATEGORY = "category"
        const val EDITING = "editing"

        fun newInstance(
            category: ExpenseCategory,
            title: String,
            editing: Boolean = true
        ): EditCategoryDialog {
            val editCategoryDialog = EditCategoryDialog()
            val bundle = Bundle()
            bundle.putString(TITLE, title)
            bundle.putBoolean(EDITING, editing)
            bundle.putParcelable(CATEGORY, category)
            editCategoryDialog.arguments = bundle
            return editCategoryDialog
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        hideDialogTitle()
        binding = EditCategoryDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handlePageItems()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = parentFragment as EditCategoryDialogListener
        } catch (e: ClassCastException) {
            this.logger.log("ClassCastException ::: Activity Should implement EditCategoryDialogListener")
        }
    }

    override fun onResume() {
        super.onResume()
        setDialogLayoutParams()
    }

    private fun hideDialogTitle() {
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
    }

    private fun setDialogLayoutParams() {
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.dimAmount = 0.5f
        dialog?.window?.attributes = layoutParams
        dialog?.window?.decorView?.setBackgroundColor(Color.TRANSPARENT)
        dialog?.window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }

    private fun handlePageItems() {
        getBundleData()
        initViews()
    }

    private fun getBundleData() {
        val bundle = arguments
        checkIfBundleIsNullAndProceed(bundle)
    }

    private fun checkIfBundleIsNullAndProceed(bundle: Bundle?) {
        if (bundle != null) {
            getCategoryFromBundleData(bundle)
            getPageTitleFromBundleData(bundle)
            getEditingStatusFromBundleData(bundle)
        }
    }


    private fun getCategoryFromBundleData(bundle: Bundle) {
        this.category = bundle.getParcelable<ExpenseCategory>(CATEGORY)
        binding.textInputEtCategoryName.setText(this.category!!.name)
    }

    private fun getPageTitleFromBundleData(bundle: Bundle) {
        this.title = bundle.getString(TITLE)
    }

    private fun getEditingStatusFromBundleData(bundle: Bundle) {
        this.editing = bundle.getBoolean(EDITING)
    }


    private fun initViews() {
        initPageTitleTextView()
        binding.tvTitle.requestFocus()
        category!!.name?.let { binding.textInputEtCategoryName.setSelection(it.length) }
        initializeCategoryOption()
        initPageButtons()
    }

    private fun initPageTitleTextView() {
        binding.tvTitle.text = this.title
    }

    private fun initializeCategoryOption() {
        if (this.editing!!) {
            binding.rvCategoryOption.visibility = View.GONE
        } else {
            binding.rvCategoryOption.visibility = View.VISIBLE
            binding.expenseRadioButton.isChecked = true
            categoryType = "0"
        }

        binding.rvCategoryOption.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.expenseRadioButton -> {
                    categoryType = "0"
                }
                R.id.incomeRadioButton -> {
                    categoryType = "1"
                }
            }
        }
    }

    private fun initPageButtons() {
        initAndHandleCancelButton()
        initAndHandleConfirmButton()
    }

    private fun initAndHandleCancelButton() {
        binding.btnCancel.setOnClickListener {
            binding.btnCancel.handleDebounce()
            this.activity?.hideKeyboard(binding.textInputEtCategoryName)
            this.dismiss()
        }
    }

    private fun initAndHandleConfirmButton() {
        binding.btnConfirm.setOnClickListener {
            binding.btnConfirm.handleDebounce()
            this.validateCategoryName(binding.textInputEtCategoryName.text.toString())
        }
    }

    private fun validateCategoryName(categoryName: String) {
        if (categoryName.isNullOrEmpty()) {
            activity?.showToast("Category name is empty")
        } else {
            if (categoryName != this.category!!.name) {
                listener.onEditCategoryConfirm(
                    ExpenseCategory(
                        categoryName,
                        if (this.editing!!) {
                            this.category!!.type
                        } else {
                            categoryType
                        },
                        this.category!!.icon,
                        if (this.editing!!) {
                            this.category!!.id
                        } else {
                            ""
                        }
                    )
                )
            }
            this.activity?.hideKeyboard(binding.textInputEtCategoryName)
            this.dismiss()
        }
    }


    interface EditCategoryDialogListener {
        fun onEditCategoryConfirm(expenseCategory: ExpenseCategory)
    }
}