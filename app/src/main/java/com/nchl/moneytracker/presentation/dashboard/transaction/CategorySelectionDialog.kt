package com.nchl.moneytracker.presentation.dashboard.transaction

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nchl.moneytracker.R
import com.nchl.moneytracker.databinding.CategorySelectionDialogBinding
import com.nchl.moneytracker.presentation.model.ExpenseCategory
import com.nchl.moneytracker.presentation.utils.log.Logger

class CategorySelectionDialog : DialogFragment(), CategorySelectionAdapter.Listener {

    private lateinit var listener: CategorySelectionDialogListener
    private val logger = Logger(CategorySelectionDialog::class.java)
    private lateinit var binding: CategorySelectionDialogBinding
    private var title: String? = ""
    private var categoryList = arrayListOf<ExpenseCategory>()
    private val adapter = CategorySelectionAdapter(mutableListOf(), this)

    companion object {
        const val TAG = "CategorySelectionDialog"
        const val BUNDLE_TITLE = "select category"
        const val BUNDLE_CATEGORY = "categories"

        fun newInstance(
            title: String,
            categories: ArrayList<ExpenseCategory>
        ): CategorySelectionDialog {
            val categorySelectionDialog = CategorySelectionDialog()
            val bundle = Bundle()
            bundle.putString(BUNDLE_TITLE, title)
            bundle.putParcelableArrayList(BUNDLE_CATEGORY, categories)
            categorySelectionDialog.arguments = bundle
            return categorySelectionDialog
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        hideDialogTitle()
        binding = CategorySelectionDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBundleData()
        binding.tvTitle.text = title
        val layoutManager = LinearLayoutManager(activity)
        binding.rvCategory.layoutManager = layoutManager
        binding.rvCategory.adapter = adapter
        adapter.update(categoryList)
        binding.ivBack.setOnClickListener {
            dismiss()
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as CategorySelectionDialogListener
        } catch (e: ClassCastException) {
            this.logger.log("ClassCastException ::: Activity Should implement ScreenSaverDialogListener")
        }
    }

    override fun onResume() {
        super.onResume()
        setDialogLayoutParams()
    }

    private fun getBundleData() {
        val bundle = arguments
        checkIfBundleIsNullAndProceed(bundle)
    }

    private fun checkIfBundleIsNullAndProceed(bundle: Bundle?) {
        if (bundle != null) {
            this.title = bundle.getString(BUNDLE_TITLE, "")
            this.categoryList = bundle.getParcelableArrayList(BUNDLE_CATEGORY)!!
        }
    }


    private fun hideDialogTitle() {
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
    }

    private fun setDialogLayoutParams() {
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        dialog?.window?.attributes = layoutParams
        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    override fun getTheme(): Int {
        return R.style.FullScreenDialog
    }

    interface CategorySelectionDialogListener {
        fun onCategorySelection(category: ExpenseCategory)
    }

    override fun onCategorySelected(category: ExpenseCategory) {
        listener.onCategorySelection(category)
    }
}