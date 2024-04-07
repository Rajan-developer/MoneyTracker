package com.nchl.moneytracker.presentation.dashboard.home

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nchl.moneytracker.R
import com.nchl.moneytracker.presentation.model.ExpenseTransaction
import com.nchl.moneytracker.presentation.utils.AppUtility
import com.nchl.moneytracker.presentation.utils.AppUtility.getRandomColor
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.O)
class SearchTransactionAdapter(
    private var transactions: MutableList<ExpenseTransaction>,
    private var listener: SearchTransactionAdapter.Listener
) : RecyclerView.Adapter<SearchTransactionAdapter.CategoryViewHolder>() {

    lateinit var context: Context

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {
        context = parent.context
        return CategoryViewHolder(
            LayoutInflater.from(context).inflate(R.layout.transaction_list_item, parent, false)
        )
    }


    @SuppressLint("SetTextI18n", "ResourceAsColor")
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val transaction = transactions[position]

        val rlCategoryIcon: RelativeLayout = holder.itemView.findViewById(R.id.rlCategoryIcon)
        val categoryNameIndicator: TextView =
            holder.itemView.findViewById(R.id.categoryNameIndicator)
        val categoryName: TextView = holder.itemView.findViewById(R.id.titleTextView)
        val transactionRemark: TextView = holder.itemView.findViewById(R.id.descriptionTextView)
        val transactionDate: TextView = holder.itemView.findViewById(R.id.dateTextView)
        val transactionTime: TextView = holder.itemView.findViewById(R.id.timeTextView)
        val transactionAmount: TextView = holder.itemView.findViewById(R.id.amountTextView)


        val randomColor = getRandomColor()
        rlCategoryIcon.setBackgroundColor(randomColor)
        categoryNameIndicator.text = transaction.categoryName?.get(0).toString().uppercase()
        categoryName.text = transaction.categoryName
        transactionRemark.text = transaction.description
        transactionDate.text = transaction.date?.let { AppUtility.dateFromDbToDisplayFormat(it,"dd MMMM yyyy") }
        transactionTime.text = transaction.time?.let { AppUtility.convertTimeToDisplayTime(it) }
        transactionAmount.text = "RS \n" + transaction.transactionAmount
        if (transaction.categoryType == "0") {
            val colorResId = R.color.expensesColor
            val textColor = ContextCompat.getColor(context, colorResId)
            transactionAmount.setTextColor(textColor)
        } else {
            val colorResId = R.color.incomeColor
            val textColor = ContextCompat.getColor(context, colorResId)
            transactionAmount.setTextColor(textColor)
        }

        holder.itemView.setOnClickListener {
            listener.onTransactionSelected(transaction)
        }
    }

    override fun getItemCount(): Int = transactions.size

    @SuppressLint("NotifyDataSetChanged")
    fun update(transactionList: MutableList<ExpenseTransaction>) {
        this.transactions = transactionList
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAll(transactions: MutableList<ExpenseTransaction>) {
        this.transactions = transactions
        notifyDataSetChanged()
    }

    interface Listener {
        fun onTransactionSelected(transaction: ExpenseTransaction)
    }


}