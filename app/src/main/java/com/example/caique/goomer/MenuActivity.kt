package com.example.caique.goomer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import com.example.caique.goomer.entity.ApiItemMenu
import com.example.caique.goomer.entity.ApiRestaurant
import com.example.caique.goomer.mappers.MenuAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private val EXTRA_MENU_ID = "EXTRA_MENU_ID"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        RetrofitInitializer()

        val idMenu = intent.extras.getString(EXTRA_MENU_ID)

        idMenu?.let {
            val call = RetrofitInitializer().restaurantsService().listItemsMenu(it)
            executeCallToGetRestaurants(call)
        }

    }

    private fun executeCallToGetRestaurants(call: Call<List<ApiItemMenu>>) {
        call.enqueue(object : Callback<List<ApiItemMenu>?> {
            override fun onResponse(call: Call<List<ApiItemMenu>?>,
                                    response: Response<List<ApiItemMenu>?>?) {

                response?.body()?.let {
                    val menuGet = it
                    setItemsMenu(menuGet)
                    Log.i("Teste", menuGet[0].toString())
                }

                Log.i("TESTE", response?.body().toString())
                Log.i("TESTE", "sucesso")
            }

            override fun onFailure(call: Call<List<ApiItemMenu>?>?,
                                   t: Throwable?) {
                Log.i("TESTE", "falha")
                Log.e("TESTE", t?.message)
            }
        })
    }

    private fun setItemsMenu(menu: List<ApiItemMenu>) {
        viewManager = LinearLayoutManager(this)
        viewAdapter = MenuAdapter(menu, this)

        recyclerView = findViewById<RecyclerView>(R.id.menu_recyclerview).apply {
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