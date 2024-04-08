package com.nchl.moneytracker.presentation.dashboard.transaction

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nchl.moneytracker.R
import com.nchl.moneytracker.presentation.model.ExpenseCategory
import kotlin.random.Random

class CategorySelectionAdapter(
    private var categories: MutableList<ExpenseCategory>,
    private var listener: Listener
) : RecyclerView.Adapter<CategorySelectionAdapter.CategoryViewHolder>() {

    lateinit var context: Context

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {
        context = parent.context
        return CategoryViewHolder(
            LayoutInflater.from(context).inflate(R.layout.category_lit_item, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]

        val rlCategoryIcon: RelativeLayout = holder.itemView.findViewById(R.id.rlCategoryIcon)
        val categoryIndicator: TextView = holder.itemView.findViewById(R.id.categoryNameIndicator)
        val name: TextView = holder.itemView.findViewById(R.id.categoryName)
        val editIcon: ImageView = holder.itemView.findViewById(R.id.categoryEdit)
        val deleteIcon: ImageView = holder.itemView.findViewById(R.id.categoryDelete)

        editIcon.visibility = View.GONE
        deleteIcon.visibility = View.GONE

        val randomColor = getRandomColor()
        rlCategoryIcon.setBackgroundColor(randomColor)
        categoryIndicator.text = categories[position].name?.get(0).toString().uppercase()

        name.text = categories[position].name

        holder.itemView.setOnClickListener{
            listener.onCategorySelected(category)
        }
    }

    override fun getItemCount(): Int = categories.size

    @SuppressLint("NotifyDataSetChanged")
    fun update(categoryList: MutableList<ExpenseCategory>) {
        this.categories = categoryList
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAll(selectableCategories: MutableList<ExpenseCategory>) {
        this.categories = selectableCategories
        notifyDataSetChanged()
    }

    interface Listener {
        fun onCategorySelected(category: ExpenseCategory)
    }


    private fun getRandomColor(): Int {
        // Generate random RGB values for the color
        val r = Random.nextInt(256)
        val g = Random.nextInt(256)
        val b = Random.nextInt(256)

        // Combine RGB values into a single color integer
        return Color.rgb(r, g, b)
    }

}