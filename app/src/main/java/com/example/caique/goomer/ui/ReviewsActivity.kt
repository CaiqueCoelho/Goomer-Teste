package com.example.caique.goomer.ui

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.RatingBar
import android.widget.SeekBar
import android.widget.Toast
import com.example.caique.goomer.R
import com.example.caique.goomer.adapters.ReviewsAdapter
import com.example.caique.goomer.client.RetrofitInitializer
import com.example.caique.goomer.entity.ApiItemReview
import com.example.caique.goomer.entity.ApiReviews
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_reviews.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class ReviewsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private val RESTAURANT_ID = "RESTAURANT_ID"
    private var idRestaurant = ""

    private var avaliation = 0.0
    var page = 1
    var reviewsGet = mutableListOf<ApiItemReview>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reviews)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        title = getString(R.string.avaliations)

        idRestaurant = intent.extras.getString(RESTAURANT_ID)

        getReviews()

        reviews_make.setOnClickListener { showEditComment() }
    }

    private fun getReviews() {
        reviews_progress.visibility = View.VISIBLE
        idRestaurant?.let {
            val call = RetrofitInitializer().restaurantsService().listReviews(page)
            executeCallToGetReviews(call)
        }
    }

    private fun executeCallToGetReviews(call: Call<ApiReviews>) {
        call.enqueue(object : Callback<ApiReviews?> {
            override fun onResponse(call: Call<ApiReviews?>,
                                    response: Response<ApiReviews?>?) {

                response?.body()?.let {
                    it.reviews?.run {
                        forEach { reviewsGet.add(it) }
                    }

                    if(it.self.next != null){
                        page++
                        getReviews()
                    }
                    else {
                        reviewsGet?.let {

                            val reviewsRestaurant = mutableListOf<ApiItemReview>()
                            for (review in reviewsGet) {
                                if (review.restaurant?.equals(idRestaurant) == true) {
                                    reviewsRestaurant.add(review)
                                }
                            }

                            if (reviewsRestaurant.isNotEmpty()) {
                                setItemsReview(reviewsRestaurant)
                            }else{
                                review_nothing.visibility = View.VISIBLE
                            }
                            reviews_progress.visibility = View.GONE
                            reviews_recyclerview.visibility = View.VISIBLE

                            Log.i("Teste", reviewsGet[0].toString())
                        }
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

            layoutManager = viewManager

            adapter = viewAdapter

        }
    }

    private fun showEditComment() {

        val viewReview = LayoutInflater.from(this).inflate(R.layout.form_review, window.decorView as ViewGroup, false)

        val editTextReview = viewReview.findViewById<EditText>(R.id.review_edittext)

        val avaliationReview = viewReview.findViewById<RatingBar>(R.id.review_ratingbar)

        avaliationReview.rating = avaliation.toFloat()

        avaliationReview.onRatingBarChangeListener =
                RatingBar.OnRatingBarChangeListener { ratingBar, rating, fromUser -> avaliation = rating.toDouble() }


        var dialog: AlertDialog

        val builder = AlertDialog.Builder(this)
                .setTitle(R.string.write_avaliation)
                .setView(viewReview)
                .setPositiveButton(R.string.post
                ) { _, _ ->
                    if (!editTextReview.text.isEmpty()) {
                        postReview(avaliation.toDouble(), editTextReview.text.toString())
                        review_nothing.visibility = View.GONE
                        reviews_recyclerview.visibility = View.GONE
                        reviewsGet = mutableListOf()
                        page = 1
                        getReviews()
                    }else{
                        showEditComment()
                        Toast.makeText(this, "Descrição é obrigatória", Toast.LENGTH_LONG).show()
                    }
                }
                .setNegativeButton(R.string.cancel, null)

        editTextReview.requestFocus()
        dialog = builder.create()
        dialog.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        dialog.show()
    }

    fun postReview(rating: Double, comments: String) {

        RetrofitInitializer()

        val cal = Calendar.getInstance()

        val call = RetrofitInitializer().restaurantsService()
                .postReview("5b8a8f320bbac95a5425ff41",
                        "5b8a8f320bbac95a5425ff41",
                        cal.time.toString(), rating, comments, idRestaurant)

        executeCallToPostReview(call)

        Toast.makeText(this, "Avaliação Publicada", Toast.LENGTH_LONG).show()

    }

    private fun executeCallToPostReview(call: Call<String>) {
        call.enqueue(object : Callback<String?> {
            override fun onResponse(call: Call<String?>?,
                                    response: Response<String?>?) {


                getReviews()
                Log.i("TESTE", "sucesso")
            }

            override fun onFailure(call: Call<String?>?,
                                   t: Throwable?) {
                Log.e("TESTE", t?.message)
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

}
