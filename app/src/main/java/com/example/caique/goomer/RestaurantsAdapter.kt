package com.example.caique.goomer

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.caique.goomer.entity.ApiRestaurant


class RestaurantsAdapter(private val myDataset: List<ApiRestaurant>,
                         private val context: Context) :
        RecyclerView.Adapter<RestaurantsAdapter.RestaurantsViewHolder>() {

    private val EXTRA_RESTAURANT = "EXTRA_RESTAURANT"

    class RestaurantsViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): RestaurantsAdapter.RestaurantsViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_restaurant, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return RestaurantsViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: RestaurantsViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        val name = holder.itemView.findViewById<TextView>(R.id.restaurant_name)
        val hour = holder.itemView.findViewById<TextView>(R.id.restaurant_hour)
        val rating = holder.itemView.findViewById<TextView>(R.id.restaurant_rating)
        val card = holder.itemView.findViewById<CardView>(R.id.restaurant_card)
        name.text = myDataset[position].name
        hour.text = myDataset[position].hours
        rating.text = context.getString(R.string.rating, myDataset[position].rating)
        card.setOnClickListener { onClick(position) }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size

    fun onClick(position: Int){
        val intent = Intent(context, RestaurantActivity::class.java)
        intent.putExtra(EXTRA_RESTAURANT, myDataset[position])
        context.startActivity(intent)
    }

}