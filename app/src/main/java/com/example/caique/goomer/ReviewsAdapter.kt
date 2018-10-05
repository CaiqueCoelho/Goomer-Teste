package com.example.caique.goomer

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.caique.goomer.entity.ApiItemMenu
import com.example.caique.goomer.entity.ApiItemReview

class ReviewsAdapter (
        private val myDataset: List<ApiItemReview>,
        private val context: Context) :
        RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder>() {

    class ReviewsViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ReviewsAdapter.ReviewsViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_review, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return ReviewsViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ReviewsViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        val rating = holder.itemView.findViewById<TextView>(R.id.review_rating)
        val description = holder.itemView.findViewById<TextView>(R.id.review_description)
        rating.text = myDataset[position].rating
        description.text = myDataset[position].comments
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size

}