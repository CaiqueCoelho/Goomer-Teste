package com.example.caique.goomer

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.caique.goomer.entity.ApiRestaurant
import kotlinx.android.synthetic.main.activity_restaurant.*

class RestaurantActivity : AppCompatActivity() {

    private val EXTRA_RESTAURANT = "EXTRA_RESTAURANT"
    private val RESTAURANT_ID = "RESTAURANT_ID"
    private val EXTRA_MENU_ID = "EXTRA_MENU_ID"

    private lateinit var restaurant: ApiRestaurant

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)

        restaurant = intent.extras.getSerializable(EXTRA_RESTAURANT) as ApiRestaurant

        restaurant?.let {
            descriptiont_name.text = it.name
            descriptiont_rating.text = getString(R.string.rating, it.rating)
            descriptiont_time.text = getString(R.string.time, it.deliveryEstimate)
            descriptiont_hour.text = it.hours
            description_description.text = it.about
            description_menu.setOnClickListener { setButtonMenu() }
            description_reviews.setOnClickListener { setButtonReviews() }
        }

    }

    private fun setButtonMenu() {
        val intent = Intent(this, MenuActivity::class.java)
        intent.putExtra(EXTRA_MENU_ID, restaurant.idKey)
        startActivity(intent)
    }

    private fun setButtonReviews() {
        val intent = Intent(this, ReviewsActivity::class.java)
        intent.putExtra(RESTAURANT_ID, restaurant.idKey)
        startActivity(intent)
    }
}
