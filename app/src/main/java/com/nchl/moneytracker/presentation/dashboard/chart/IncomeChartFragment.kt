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
import com.nchl.moneytracker.databinding.FragmentIncomeChartBinding
import com.nchl.moneytracker.presentation.base.AppBaseFragment
import com.nchl.moneytracker.presentation.dashboard.DashViewModel
import com.nchl.moneytracker.presentation.model.CategorySum
import com.nchl.moneytracker.presentation.utils.AppUtility

@RequiresApi(Build.VERSION_CODES.O)
class IncomeChartFragment : AppBaseFragment() {

    private lateinit var binding: FragmentIncomeChartBinding
    override fun getLayoutId(): Int = R.layout.fragment_income_chart

    private fun getViewModel() = ViewModelProviders.of(requireActivity())[DashViewModel::class.java]

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIncomeChartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
        hideSoftKeyboard(requireActivity(), requireView())
        getViewModel().getTransactionSumByCategory(AppUtility.getCurrentDate(), "1")
    }

    private fun initViews() {
        getViewModel().incomeChartList.observe(viewLifecycleOwner) {

            val colors = mutableListOf<Int>()
            for (i in 0 until it.size) {
                val color =
                    AppUtility.getRandomColor() // Implement this function to generate a random color
                colors.add(color)
            }

            if(it.size >0){
                binding.pieChartNoData.visibility =View.GONE
                binding.barChartNoData.visibility =View.GONE
                binding.incomePieChart.visibility =View.VISIBLE
                binding.incomeBarGraph.visibility =View.VISIBLE
                showPieChart(it, colors)
                showBarGraph(it, colors)
            }else{
                binding.pieChartNoData.visibility =View.VISIBLE
                binding.barChartNoData.visibility =View.VISIBLE
                binding.incomePieChart.visibility =View.GONE
                binding.incomeBarGraph.visibility =View.GONE
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

        val descriptionText = "Income Piechart"
        val description = Description().apply {
            text = descriptionText
            xOffset = 0f
            yOffset = 0f
        }

        binding.incomePieChart.description = description

        val pieData = PieData(dataSet)
        binding.incomePieChart.data = pieData
        binding.incomePieChart.invalidate()
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

        val descriptionText = "Income BarChart"
        val description = Description().apply {
            text = descriptionText
            xOffset = 0f
            yOffset = 0f
        }
        binding.incomeBarGraph.description = description

        val barDataSet = BarDataSet(barEntries, "")
        barDataSet.colors = colors

        val barData = BarData(barDataSet)
        binding.incomeBarGraph.data = barData
        binding.incomeBarGraph.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        binding.incomeBarGraph.invalidate()
    }

    private fun initObservers() {

    }

}