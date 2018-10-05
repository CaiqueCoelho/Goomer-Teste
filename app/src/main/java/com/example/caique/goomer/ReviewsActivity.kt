package com.example.caique.goomer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.example.caique.goomer.entity.ApiItemMenu
import com.example.caique.goomer.entity.ApiItemReview
import com.example.caique.goomer.entity.ApiReviews
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private val RESTAURANT_ID = "RESTAURANT_ID"
    private var idRestaurant = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reviews)

        idRestaurant = intent.extras.getString(RESTAURANT_ID)

        idRestaurant?.let {
            val call = RetrofitInitializer().restaurantsService().listReviews()
            executeCallToGetRestaurants(call)
        }
    }

    private fun executeCallToGetRestaurants(call: Call<ApiReviews>) {
        call.enqueue(object : Callback<ApiReviews?> {
            override fun onResponse(call: Call<ApiReviews?>,
                                    response: Response<ApiReviews?>?) {

                response?.body()?.let {
                    val reviewsGet = it.reviews
                    reviewsGet?.let {

                        val reviewsRestaurant = mutableListOf<ApiItemReview>()

                        for (review in reviewsGet){
                            if(review.restaurant?.equals(idRestaurant) == true){
                                reviewsRestaurant.add(review)
                            }
                        }

                        if(reviewsRestaurant.isNotEmpty()){
                            setItemsReview(reviewsGet)
                        }

                        Log.i("Teste", reviewsGet[0].toString())
                    }
                }

                Log.i("TESTE", response?.body().toString())
                Log.i("TESTE", "sucesso")
            }

            override fun onFailure(call: Call<ApiReviews?>?,
                                   t: Throwable?) {
                Log.i("TESTE", "falha")
                Log.e("TESTE", t?.message)
            }
        })
    }

    private fun setItemsReview(reviews: List<ApiItemReview>) {
        viewManager = LinearLayoutManager(this)
        viewAdapter = ReviewsAdapter(reviews, this)

        recyclerView = findViewById<RecyclerView>(R.id.reviews_recyclerview).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            //setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }
    }
}
