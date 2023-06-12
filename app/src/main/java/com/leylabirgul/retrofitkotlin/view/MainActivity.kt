package com.leylabirgul.retrofitkotlin.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.leylabirgul.retrofitkotlin.R
import com.leylabirgul.retrofitkotlin.R.id
import com.leylabirgul.retrofitkotlin.adapter.RecyclerViewAdapter
import com.leylabirgul.retrofitkotlin.model.CryptoModel
import com.leylabirgul.retrofitkotlin.service.CryptoAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(),RecyclerViewAdapter.Listener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var itemView:RecyclerView
    private val BASE_URL = "https://raw.githubusercontent.com/"
    private var cryptoModels: ArrayList<CryptoModel>? = null
    private var recylerViewAdapter:RecyclerViewAdapter?=null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(id.recyclerView)
        itemView=findViewById(id.text_name)
        itemView=findViewById(id.text_price)
        val layoutManager : RecyclerView.LayoutManager=LinearLayoutManager(this)
        recyclerView.layoutManager=layoutManager


        loadData()


    }

    private fun loadData() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(CryptoAPI::class.java)
        val call = service.getData()
        call.enqueue(object : Callback<List<CryptoModel>> {

            override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
                t.printStackTrace()
            }


            override fun onResponse(
                call: Call<List<CryptoModel>>,
                response: Response<List<CryptoModel>>
            ) {
                if (response.isSuccessful) {

                    response.body()?.let {
                        cryptoModels=ArrayList(it)
                        recylerViewAdapter= RecyclerViewAdapter(cryptoModels!!,this@MainActivity)
                        recyclerView.adapter=recylerViewAdapter
                    }
                }

            }

        })

    }

    override fun onItemClick(cryptoModel: CryptoModel) {
     Toast.makeText(this,"Clicked:${cryptoModel.currency}",Toast.LENGTH_LONG).show()
    }
}