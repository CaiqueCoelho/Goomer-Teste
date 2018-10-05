package com.example.caique.goomer.mappers

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.caique.goomer.R
import com.example.caique.goomer.entity.ApiItemMenu

class MenuAdapter(
        private val myDataset: List<ApiItemMenu>,
        private val context: Context) :
        RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    class MenuViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MenuAdapter.MenuViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_menu, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return MenuViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        val name = holder.itemView.findViewById<TextView>(R.id.menu_name)
        val description = holder.itemView.findViewById<TextView>(R.id.menu_description)
        val price = holder.itemView.findViewById<TextView>(R.id.menu_price)
        name.text = myDataset[position].name
        price.text = context.getString(R.string.price, myDataset[position].price.toString())
        description.text = myDataset[position].description
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size

}