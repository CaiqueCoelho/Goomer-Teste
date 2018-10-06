package com.example.caique.goomer.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.example.caique.goomer.R
import com.example.caique.goomer.adapters.RestaurantsAdapter
import com.example.caique.goomer.client.RetrofitInitializer
import com.example.caique.goomer.entity.ApiGetRestaurants
import com.example.caique.goomer.entity.ApiRestaurant
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    var restaurantsGet = mutableListOf<ApiRestaurant>()
    var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = getString(R.string.restaurants)

        RetrofitInitializer()

        doQueryRestaurants()

    }

    private fun doQueryRestaurants() {
        val call = RetrofitInitializer().restaurantsService().listPage(page)

        executeCallToGetRestaurants(call)
    }

    private fun executeCallToGetRestaurants(call: Call<ApiGetRestaurants>) {
        call.enqueue(object : Callback<ApiGetRestaurants?> {
            override fun onResponse(call: Call<ApiGetRestaurants?>?,
                                    response: Response<ApiGetRestaurants?>?) {

                response?.body()?.let {
                    restaurantsGet.addAll( it.items)
                    setRestaurants(restaurantsGet)

                    if(it.links.next != null) {
                        page++
                        doQueryRestaurants()
                    }
                    else{
                        Log.i("TESTE", "sucesso")
                        main_progress.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                    }
                }
            }

            override fun onFailure(call: Call<ApiGetRestaurants?>?,
                                   t: Throwable?) {
                Log.i("TESTE", "falha")
                Log.e("TESTE", t?.message)
            }
        })
    }

    private fun setRestaurants(restaurants: List<ApiRestaurant>) {
        viewManager = LinearLayoutManager(this)
        viewAdapter = RestaurantsAdapter(restaurants, this)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {

            layoutManager = viewManager

            adapter = viewAdapter

        }
    }
}