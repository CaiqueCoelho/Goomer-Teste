package com.example.caique.goomer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.example.caique.goomer.entity.ApiRestaurant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RetrofitInitializer()

        val call = RetrofitInitializer().restaurantsService().list()

        executeCallToGetRestaurants(call)

    }

    private fun executeCallToGetRestaurants(call: Call<ApiGetRestaurants>) {
        call.enqueue(object : Callback<ApiGetRestaurants?> {
            override fun onResponse(call: Call<ApiGetRestaurants?>?,
                                    response: Response<ApiGetRestaurants?>?) {

                response?.body()?.let {
                    val restaurants1 = it.items
                    setRestaurants(restaurants1)

                    //it.links.next?.let { it. }
                }

                Log.i("TESTE", "sucesso")
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