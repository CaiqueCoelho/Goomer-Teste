package com.example.caique.goomer

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.SeekBar
import com.example.caique.goomer.entity.ApiItemReview
import com.example.caique.goomer.entity.ApiReviews
import kotlinx.android.synthetic.main.activity_reviews.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class ReviewsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private val RESTAURANT_ID = "RESTAURANT_ID"
    private var idRestaurant = ""

    private var avaliation = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reviews)

        idRestaurant = intent.extras.getString(RESTAURANT_ID)

        getReviews()

        reviews_make.setOnClickListener { showEditComment() }
    }

    private fun getReviews() {
        idRestaurant?.let {
            val call = RetrofitInitializer().restaurantsService().listReviews()
            executeCallToGetReviews(call)
        }
    }

    private fun executeCallToGetReviews(call: Call<ApiReviews>) {
        call.enqueue(object : Callback<ApiReviews?> {
            override fun onResponse(call: Call<ApiReviews?>,
                                    response: Response<ApiReviews?>?) {

                response?.body()?.let {
                    val reviewsGet = it.reviews
                    reviewsGet?.let {

                        val reviewsRestaurant = mutableListOf<ApiItemReview>()

                        for (review in reviewsGet) {
                            if (review.restaurant?.equals(idRestaurant) == true) {
                                reviewsRestaurant.add(review)
                            }
                        }

                        if (reviewsRestaurant.isNotEmpty()) {
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

    private fun showEditComment() {

        val viewReview = LayoutInflater.from(this).inflate(R.layout.form_review, window.decorView as ViewGroup, false)

        val editTextReview = viewReview.findViewById<EditText>(R.id.review_edittext)
        val avaliationReview = viewReview.findViewById<SeekBar>(R.id.review_seekBar)


        avaliationReview.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                avaliation = if (i != 1) {
                    i / 2
                } else {
                    1
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        var dialog: AlertDialog

        val builder = AlertDialog.Builder(this)
                .setTitle(R.string.write_avaliation)
                .setView(viewReview)
                .setPositiveButton(R.string.post
                ) { _, _ ->
                    if (!editTextReview.text.isEmpty()) {
                        postReview(avaliation.toDouble(), editTextReview.text.toString())
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

}
