package com.nchl.moneytracker.presentation.dashboard.chart

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.nchl.moneytracker.R
import com.nchl.moneytracker.databinding.FragmentExpenseChartBinding
import com.nchl.moneytracker.presentation.base.AppBaseFragment
import com.nchl.moneytracker.presentation.dashboard.DashViewModel
import com.nchl.moneytracker.presentation.model.CategorySum
import com.nchl.moneytracker.presentation.utils.AppUtility

@RequiresApi(Build.VERSION_CODES.O)
class ExpenseChartFragment : AppBaseFragment() {

    private lateinit var binding: FragmentExpenseChartBinding

    override fun getLayoutId(): Int = R.layout.fragment_expense_chart

    private fun getViewModel() = ViewModelProviders.of(requireActivity())[DashViewModel::class.java]

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExpenseChartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()
        hideSoftKeyboard(requireActivity(), requireView())
        getViewModel().getTransactionSumByCategory(AppUtility.getCurrentDate(), "0")
    }

    private fun initViews() {
        getViewModel().expenseChartList.observe(viewLifecycleOwner) {
            val colors = mutableListOf<Int>()
            for (i in 0 until it.size) {
                val color =
                    AppUtility.getRandomColor() // Implement this function to generate a random color
                colors.add(color)
            }
            if(it.size >0){
                binding.pieChartNoData.visibility =View.GONE
                binding.barchartNoData.visibility =View.GONE
                binding.expensePieChart.visibility =View.VISIBLE
                binding.expenseBarGraph.visibility =View.VISIBLE
                showPieChart(it, colors)
                showBarGraph(it, colors)
            }else{
                binding.pieChartNoData.visibility =View.VISIBLE
                binding.barchartNoData.visibility =View.VISIBLE
                binding.expensePieChart.visibility =View.GONE
                binding.expenseBarGraph.visibility =View.GONE
            }
        }
    }

    private fun showPieChart(categorySums: MutableList<CategorySum>, colors: MutableList<Int>) {
        val entries: ArrayList<PieEntry> = ArrayList()
        for (categorySum in categorySums) {
            entries.add(PieEntry(categorySum.transactionTotal.toFloat(), categorySum.categoryName))
        }

        val dataSet = PieDataSet(entries, "")
        dataSet.colors = colors
        dataSet.valueTextSize = 12f

        val descriptionText = "Expense Piechart"
        val description = Description().apply {
            text = descriptionText
            xOffset = 0f
            yOffset = 0f
        }

        binding.expensePieChart.description = description

        val pieData = PieData(dataSet)
        binding.expensePieChart.data = pieData
        binding.expensePieChart.invalidate()
    }

    private fun showBarGraph(categorySums: MutableList<CategorySum>, colors: MutableList<Int>) {
        val barEntries = ArrayList<BarEntry>()
        val labels = ArrayList<String>()

        categorySums.forEachIndexed { index, categorySum ->
            barEntries.add(
                BarEntry(
                    index.toFloat(),
                    categorySum.transactionTotal?.toFloat() ?: 0.0f
                )
            )
            labels.add(categorySum.categoryName ?: "")
        }

        val descriptionText = "Expense BarChart"
        val description = Description().apply {
            text = descriptionText
            xOffset = 0f
            yOffset = 0f
        }
        binding.expenseBarGraph.description = description

        val barDataSet = BarDataSet(barEntries, "")
        barDataSet.colors = colors

        val barData = BarData(barDataSet)
        binding.expenseBarGraph.data = barData
        binding.expenseBarGraph.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        binding.expenseBarGraph.invalidate()
    }

    private fun initObservers() {

    }
}